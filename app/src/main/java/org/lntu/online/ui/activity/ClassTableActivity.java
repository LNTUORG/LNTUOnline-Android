package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.LocalDate;
import org.lntu.online.R;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.BackgroundCallback;
import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.storage.CacheShared;
import org.lntu.online.model.storage.LoginShared;
import org.lntu.online.ui.base.StatusBarActivity;
import org.lntu.online.ui.fragment.ClassTablePageFragment;
import org.lntu.online.ui.listener.NavigationFinishClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import retrofit.client.Response;

public class ClassTableActivity extends StatusBarActivity implements Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.spn_year_term)
    protected Spinner spnYearTerm;

    @BindView(R.id.layout_loading)
    protected View layoutLoading;

    @BindView(R.id.layout_empty)
    protected View layoutEmpty;

    @BindView(R.id.layout_fragment)
    protected ViewGroup layoutFragment;

    @BindView(R.id.icon_loading_anim)
    protected View iconLoadingAnim;

    @BindView(R.id.tv_load_failed)
    protected TextView tvLoadFailed;

    private BaseFragment fmPage;
    private BaseFragment fmList;

    private final LocalDate today = new LocalDate();
    private int currentYear;       // 当前查询的年条件
    private String currentTerm;    // 当前查询的学期条件
    private ClassTable classTable; // 当前的课表对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.class_table_page);
        toolbar.setOnMenuItemClickListener(this);

        Animation dataLoadAnim = AnimationUtils.loadAnimation(this, R.anim.data_loading);
        dataLoadAnim.setInterpolator(new LinearInterpolator());
        iconLoadingAnim.startAnimation(dataLoadAnim);

        fmPage = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragement_page);
        fmList = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragement_list);
        getSupportFragmentManager().beginTransaction().show(fmPage).hide(fmList).commit();

        ArrayAdapter spnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getYearTermList(today));
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYearTerm.setAdapter(spnAdapter);
    }

    /**
     * 获取年级学期数组
     */
    private List<String> getYearTermList(LocalDate today) {
        int startYear = 2000 + Integer.parseInt(LoginShared.getUserId(this).substring(0, 2));
        int endYear = today.getYear();
        endYear = today.getMonthOfYear() < 2 ? endYear - 1 : endYear;
        endYear = endYear < startYear ? startYear : endYear;
        String endTerm = (today.getMonthOfYear() >= 2 && today.getMonthOfYear() < 8) ? "春" : "秋";
        List<String> yearTermList = new ArrayList<>();
        for (int n = 0; n < endYear - startYear; n++) {
            if (!(endYear - n == endYear && endTerm.equals("春"))) {
                yearTermList.add(endYear - n + "年 " + "秋季");
            }
            yearTermList.add(endYear - n + "年 " + "春季");
        }
        yearTermList.add(startYear + "年 " + "秋季");
        return yearTermList;
    }

    /**
     * 设置年级和学期
     */
    private void setCurrentYearAndTerm(int year, String term) {
        fmPage.onDataSetInit(year, term, today);
        fmList.onDataSetInit(year, term, today);
        currentYear = year;
        currentTerm = term;
        classTable = CacheShared.getClassTable(this, year, term);
        if (classTable != null) {
            final Map<String, List<ClassTable.CourseWrapper>> classTableMap = classTable.getMap();
            fmPage.onDataSetUpdate(classTable, classTableMap);
            fmList.onDataSetUpdate(classTable, classTableMap);
            showLayoutFragment();
        } else {
            showLayoutLoading();
        }
        startNetwork(year, term);
    }

    /**
     * 当前年级和学期切换
     */
    @OnItemSelected(R.id.spn_year_term)
    protected void onSpnItemSelected() {
        String[] itemArr = spnYearTerm.getSelectedItem().toString().split(" ");
        int year = Integer.parseInt(itemArr[0].replace("年", ""));
        String term = itemArr[1].replace("季", "");
        setCurrentYearAndTerm(year, term);
    }

    /**
     * 菜单点击事件
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_class_table_page:
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.class_table_page);
                getSupportFragmentManager().beginTransaction().show(fmPage).hide(fmList).commit();
                return true;
            case R.id.action_class_table_list:
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.class_table_list);
                getSupportFragmentManager().beginTransaction().show(fmList).hide(fmPage).commit();
                return true;
            case R.id.action_class_table_today:
                if (classTable != null) {
                    ((ClassTablePageFragment) fmPage).onSetToday();
                }
                return true;
            default:
                return false;
        }
    }

    /**
     * 更新数据
     */
    private void startNetwork(final int year, final String term) {
        ApiClient.service.getClassTable(LoginShared.getLoginToken(this), year, term, new BackgroundCallback<ClassTable>(this) {

            @Override
            public void handleSuccess(ClassTable classTable, Response response) {
                CacheShared.setClassTable(ClassTableActivity.this, classTable);
                if (year == currentYear && term.equals(currentTerm)) { // 如果当前年级和学期没有改变
                    ClassTableActivity.this.classTable = classTable;
                    Map<String, List<ClassTable.CourseWrapper>> classTableMap = classTable.getMap();
                    fmPage.onDataSetUpdate(classTable, classTableMap);
                    fmList.onDataSetUpdate(classTable, classTableMap);
                    showLayoutFragment();
                }
            }

            @Override
            public void handleFailure(String message) {
                if (year == currentYear && term.equals(currentTerm) && classTable == null) { // 如果classTable为空，说明是第一次初始化
                    showLayoutEmpty(message);
                }
            }

        });
    }

    /**
     * 显示加载状态
     */
    private void showLayoutLoading() {
        layoutLoading.setVisibility(View.VISIBLE);
        layoutEmpty.setVisibility(View.GONE);
        layoutFragment.setVisibility(View.GONE);
    }

    /**
     * 显示错误状态
     */
    private void showLayoutEmpty(String message) {
        layoutLoading.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.VISIBLE);
        layoutFragment.setVisibility(View.GONE);
        tvLoadFailed.setText(message);
    }

    /**
     * 显示当前容器
     */
    private void showLayoutFragment() {
        layoutLoading.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);
        layoutFragment.setVisibility(View.VISIBLE);
    }

    /**
     * 点击重新加载数据
     */
    @OnClick(R.id.layout_empty)
    protected void onBtnIconEmptyClick() {
        showLayoutLoading();
        startNetwork(currentYear, currentTerm);
    }

    public static abstract class BaseFragment extends Fragment {

        public abstract void onDataSetInit(int year, String term, LocalDate today);

        public abstract void onDataSetUpdate(ClassTable classTable, Map<String, List<ClassTable.CourseWrapper>> classTableMap);

    }

}

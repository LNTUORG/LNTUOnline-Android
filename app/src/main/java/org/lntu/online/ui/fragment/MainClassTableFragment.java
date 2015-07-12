package org.lntu.online.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.activity.MainActivity;
import org.lntu.online.ui.adapter.MainClassTableListAdapter;
import org.lntu.online.ui.adapter.MainClassTablePageAdapter;
import org.lntu.online.ui.dialog.ClassTableTimeDialogHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import retrofit.client.Response;

public class MainClassTableFragment extends MainActivity.BaseFragment implements Toolbar.OnMenuItemClickListener {

    @InjectView(R.id.main_class_table_toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.class_table_spn_year_term)
    protected Spinner spnYearTerm;

    @InjectView(R.id.class_table_layout_loading)
    protected View layoutLoading;

    @InjectView(R.id.class_table_layout_empty)
    protected View layoutEmpty;

    @InjectView(R.id.class_table_layout_fragment)
    protected ViewGroup layoutFragment;

    @InjectView(R.id.class_table_icon_loading_anim)
    protected View iconLoadingAnim;

    @InjectView(R.id.class_table_tv_load_failed)
    protected TextView tvLoadFailed;

    // Page
    @InjectView(R.id.class_table_page_view_pager)
    protected ViewPager viewPager;

    private MainClassTablePageAdapter pageAdapter;

    private ClassTableTimeDialogHolder dialogHolder;

    // List
    @InjectView(R.id.class_table_list_recycler_view)
    protected RecyclerView recyclerView;

    private MainClassTableListAdapter listAdapter;

    // Other
    private final LocalDate today = new LocalDate();
    private int currentYear;       // 当前查询的年条件
    private String currentTerm;    // 当前查询的学期条件
    private ClassTable classTable; // 当前的课表对象

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_class_table, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        toolbar.setNavigationOnClickListener(getOpenNavigationClickListener());
        toolbar.inflateMenu(R.menu.class_table_page);
        toolbar.setOnMenuItemClickListener(this);

        Animation dataLoadAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.data_loading);
        dataLoadAnim.setInterpolator(new LinearInterpolator());
        iconLoadingAnim.startAnimation(dataLoadAnim);

        ArrayAdapter spnAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_class_table, getYearTermList(today));
        spnAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_class_table);
        spnYearTerm.setAdapter(spnAdapter);

        // Page
        dialogHolder = new ClassTableTimeDialogHolder(getActivity());
        dialogHolder.setOnTimeDialogFinishListener(new ClassTableTimeDialogHolder.OnDialogFinishListener() {

            @Override
            public void onDialogFinish(LocalDate currentDate) {
                if (pageAdapter != null) {
                    viewPager.setCurrentItem(pageAdapter.getPositionFromDate(currentDate), true);
                }
            }

        });

        // List
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new MainClassTableListAdapter(getActivity());
        recyclerView.setAdapter(listAdapter);

        // 显示Page隐藏List
        viewPager.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * 获取年级学期数组
     */
    private List<String> getYearTermList(LocalDate today) {
        int startYear = 2000 + Integer.parseInt(LoginShared.getUserId(getActivity()).substring(0, 2));
        int endYear = today.getYear() < startYear ? startYear : today.getYear();
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
        onDataSetInit(year, term, today);
        currentYear = year;
        currentTerm = term;
        classTable = LoginShared.getClassTable(getActivity(), year, term);
        if (classTable != null) {
            final Map<String, List<ClassTable.CourseWrapper>> classTableMap = classTable.getMap();
            onDataSetUpdate(classTable, classTableMap);
            showLayoutFragment();
        } else {
            showLayoutLoading();
        }
        startNetwork(year, term);
    }

    /**
     * 当前年级和学期切换
     */
    @OnItemSelected(R.id.class_table_spn_year_term)
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
            case R.id.action_class_table_page:
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.class_table_page);
                viewPager.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                return true;
            case R.id.action_class_table_list:
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.class_table_list);
                viewPager.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                return true;
            case R.id.action_class_table_today:
                if (classTable != null) {
                    if (pageAdapter != null) {
                        dialogHolder.showDialog(pageAdapter.getDateAt(viewPager.getCurrentItem()));
                    }
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
        ApiClient.with(getActivity()).apiService.getClassTable(LoginShared.getLoginToken(getActivity()), year, term, new BackgroundCallback<ClassTable>(getActivity()) {

            @Override
            public void handleSuccess(ClassTable classTable, Response response) {
                LoginShared.setClassTable(getActivity(), classTable);
                if (year == currentYear && term.equals(currentTerm)) { // 如果当前年级和学期没有改变
                    MainClassTableFragment.this.classTable = classTable;
                    Map<String, List<ClassTable.CourseWrapper>> classTableMap = classTable.getMap();
                    onDataSetUpdate(classTable, classTableMap);
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
    @OnClick(R.id.class_table_layout_empty)
    protected void onBtnIconEmptyClick() {
        showLayoutLoading();
        startNetwork(currentYear, currentTerm);
    }

    /**
     * 调用接口
     */
    public void onDataSetInit(int year, String term, LocalDate today) {
        // Page
        pageAdapter = new MainClassTablePageAdapter(getActivity(), year, term, today);
        viewPager.setAdapter(pageAdapter);
        String currentTerm = (today.getMonthOfYear() >= 2 && today.getMonthOfYear() < 8) ? "春" : "秋";
        if (today.getYear() == year && currentTerm.equals(term)) {
            viewPager.setCurrentItem(pageAdapter.getPositionFromDate(today), true);
        } else if (term.equals("春")) {
            viewPager.setCurrentItem(pageAdapter.getPositionFromDate(new LocalDate(year, 3, 1)), true);
        } else {
            viewPager.setCurrentItem(pageAdapter.getPositionFromDate(new LocalDate(year, 9, 1)), true);
        }
    }

    public void onDataSetUpdate(ClassTable classTable, Map<String, List<ClassTable.CourseWrapper>> classTableMap) {
        pageAdapter.updateDataSet(classTable, classTableMap);
        listAdapter.updateClassTable(classTable);
    }

}

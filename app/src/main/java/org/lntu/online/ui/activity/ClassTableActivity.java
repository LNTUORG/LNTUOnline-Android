package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.lntu.online.R;

import org.joda.time.DateTime;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.BackgroundCallback;
import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.ui.base.ClassTableFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.client.Response;

public class ClassTableActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

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

    private ClassTableFragment fmPage;
    private ClassTableFragment fmList;

    private Menu menu;

    private int year;
    private String term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Animation dataLoadAnim = AnimationUtils.loadAnimation(this, R.anim.data_loading);
        dataLoadAnim.setInterpolator(new LinearInterpolator());
        iconLoadingAnim.startAnimation(dataLoadAnim);

        // 初始化时间条件
        DateTime nowTime = new DateTime();
        year = nowTime.getYear();
        term = (nowTime.getMonthOfYear() >= 3 && nowTime.getMonthOfYear() < 9) ? "春" : "秋";

        // 初始化Fragment
        fmPage = (ClassTableFragment) getSupportFragmentManager().findFragmentById(R.id.class_table_fragement_page);
        fmList = (ClassTableFragment) getSupportFragmentManager().findFragmentById(R.id.class_table_fragement_list);
        getSupportFragmentManager().beginTransaction().show(fmPage).hide(fmList).commit();

        // 获取本地课表数据
        ClassTable classTable = LoginShared.getClassTable(this, year, term);
        if (classTable != null) {
            fmPage.updateDataView(classTable);
            fmList.updateDataView(classTable);
            showLayoutFragment();
        } else {
            showLayoutLoading();
        }

        // 更新数据
        startNetwork();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.class_table_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_class_table_list:
                menu.clear();
                getMenuInflater().inflate(R.menu.class_table_list, menu);
                getSupportFragmentManager().beginTransaction().show(fmList).hide(fmPage).commit();
                return true;
            case R.id.action_class_table_page:
                menu.clear();
                getMenuInflater().inflate(R.menu.class_table_page, menu);
                getSupportFragmentManager().beginTransaction().show(fmPage).hide(fmList).commit();
                return true;
            case R.id.action_class_table_today:

                // TODO

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 更新数据
     */
    private void startNetwork() {
        ApiClient.with(this).apiService.getClassTable(LoginShared.getLoginToken(this), year, term, new BackgroundCallback<ClassTable>(this) {

            @Override
            public void handleSuccess(ClassTable classTable, Response response) {
                LoginShared.setClassTable(ClassTableActivity.this, year, term, classTable);
                fmPage.updateDataView(classTable);
                fmList.updateDataView(classTable);
                showLayoutFragment();
            }

            @Override
            public void handleFailure(String message) {
                if (layoutFragment.getVisibility() != View.VISIBLE) {
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
        startNetwork();
    }

}

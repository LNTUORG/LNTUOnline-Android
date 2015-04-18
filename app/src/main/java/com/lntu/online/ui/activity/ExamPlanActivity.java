package com.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import com.lntu.online.R;
import com.lntu.online.model.api.ApiClient;
import com.lntu.online.model.api.BackgroundCallback;
import com.lntu.online.model.entity.ExamPlan;
import com.lntu.online.shared.LoginShared;
import com.lntu.online.ui.adapter.ExamPlanAdapter;
import com.lntu.online.ui.base.BaseActivity;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.client.Response;

public class ExamPlanActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.exam_plan_list_view)
    protected ListView listView;

    @InjectView(R.id.exam_plan_icon_loading)
    protected View iconLoading;

    @InjectView(R.id.exam_plan_icon_empty)
    protected View iconEmpty;

    @InjectView(R.id.exam_plan_icon_loading_anim)
    protected View iconLoadingAnim;

    @InjectView(R.id.exam_plan_tv_load_failed)
    protected TextView tvLoadFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_plan);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Animation dataLoadAnim = AnimationUtils.loadAnimation(this, R.anim.data_loading);
        dataLoadAnim.setInterpolator(new LinearInterpolator());
        iconLoadingAnim.startAnimation(dataLoadAnim);

        startNetwork();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 获取考试安排信息
     */
    private void startNetwork() {
        ApiClient.with(this).apiService.getExamPlanList(LoginShared.getLoginToken(this), new BackgroundCallback<List<ExamPlan>>(this) {

            @Override
            public void handleSuccess(List<ExamPlan> examPlanList, Response response) {
                if (examPlanList.size() == 0) {
                    showIconEmptyView("暂时没有考试信息，过一个月再来看看吧。");
                } else {
                    Collections.sort(examPlanList); // 排序
                    listView.setAdapter(new ExamPlanAdapter(ExamPlanActivity.this, examPlanList));
                    listView.setVisibility(View.VISIBLE);
                    iconLoading.setVisibility(View.GONE);
                    iconEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void handleFailure(String message) {
                showIconEmptyView(message);
            }

        });
    }

    /**
     * 显示错误面板
     */
    private void showIconEmptyView(String message) {
        iconLoading.setVisibility(View.GONE);
        iconEmpty.setVisibility(View.VISIBLE);
        tvLoadFailed.setText(message);
    }

    /**
     * 数据重新加载
     */
    @OnClick(R.id.exam_plan_icon_empty)
    public void onBtnIconEmptyClick() {
        iconLoading.setVisibility(View.VISIBLE);
        iconEmpty.setVisibility(View.GONE);
        startNetwork();
    }

}

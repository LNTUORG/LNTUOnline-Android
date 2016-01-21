package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.BackgroundCallback;
import org.lntu.online.model.entity.ExamPlan;
import org.lntu.online.storage.LoginShared;
import org.lntu.online.ui.adapter.ExamPlanAdapter;
import org.lntu.online.ui.base.StatusBarActivity;
import org.lntu.online.ui.listener.NavigationFinishClickListener;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;

public class ExamPlanActivity extends StatusBarActivity {

    @Bind(R.id.exam_plan_toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.exam_plan_list_view)
    protected ListView listView;

    @Bind(R.id.exam_plan_icon_loading)
    protected View iconLoading;

    @Bind(R.id.exam_plan_icon_empty)
    protected View iconEmpty;

    @Bind(R.id.exam_plan_icon_loading_anim)
    protected View iconLoadingAnim;

    @Bind(R.id.exam_plan_tv_load_failed)
    protected TextView tvLoadFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_plan);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        Animation dataLoadAnim = AnimationUtils.loadAnimation(this, R.anim.data_loading);
        dataLoadAnim.setInterpolator(new LinearInterpolator());
        iconLoadingAnim.startAnimation(dataLoadAnim);

        startNetwork();
    }

    private void startNetwork() {
        ApiClient.service.getExamPlanList(LoginShared.getLoginToken(this), new BackgroundCallback<List<ExamPlan>>(this) {

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

    private void showIconEmptyView(String message) {
        iconLoading.setVisibility(View.GONE);
        iconEmpty.setVisibility(View.VISIBLE);
        tvLoadFailed.setText(message);
    }

    @OnClick(R.id.exam_plan_icon_empty)
    protected void onBtnIconEmptyClick() {
        iconLoading.setVisibility(View.VISIBLE);
        iconEmpty.setVisibility(View.GONE);
        startNetwork();
    }

}

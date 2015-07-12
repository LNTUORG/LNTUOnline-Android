package org.lntu.online.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.BackgroundCallback;
import org.lntu.online.model.entity.ExamPlan;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.activity.MainActivity;
import org.lntu.online.ui.adapter.ExamPlanAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.client.Response;

public class MainExamPlanFragment extends MainActivity.BaseFragment {

    @InjectView(R.id.main_exam_plan_toolbar)
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_exam_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        toolbar.setNavigationOnClickListener(getOpenNavigationClickListener());

        Animation dataLoadAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.data_loading);
        dataLoadAnim.setInterpolator(new LinearInterpolator());
        iconLoadingAnim.startAnimation(dataLoadAnim);

        startNetwork();
    }

    private void startNetwork() {
        ApiClient.with(getActivity()).apiService.getExamPlanList(LoginShared.getLoginToken(getActivity()), new BackgroundCallback<List<ExamPlan>>(getActivity()) {

            @Override
            public void handleSuccess(List<ExamPlan> examPlanList, Response response) {
                if (examPlanList.size() == 0) {
                    showIconEmptyView("暂时没有考试信息，过一个月再来看看吧。");
                } else {
                    Collections.sort(examPlanList); // 排序
                    listView.setAdapter(new ExamPlanAdapter(getActivity(), examPlanList));
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

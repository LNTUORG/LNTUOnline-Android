package org.lntu.online.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import org.lntu.online.ui.adapter.MainExamPlanAdapter;
import org.lntu.online.util.ToastUtils;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.client.Response;

public class MainExamPlanFragment extends MainActivity.BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.main_exam_plan_toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.main_exam_plan_refresh_layout)
    protected SwipeRefreshLayout refreshLayout;

    @InjectView(R.id.main_exam_plan_list_view)
    protected ListView listView;

    @InjectView(R.id.main_exam_plan_layout_loading)
    protected ViewGroup layoutLoading;

    @InjectView(R.id.main_exam_plan_layout_empty)
    protected ViewGroup layoutEmpty;

    @InjectView(R.id.main_exam_plan_icon_loading_anim)
    protected View iconLoadingAnim;

    @InjectView(R.id.main_exam_plan_tv_load_failed)
    protected TextView tvLoadFailed;

    private MainExamPlanAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new MainExamPlanAdapter(inflater);
        return inflater.inflate(R.layout.activity_main_exam_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        toolbar.setNavigationOnClickListener(getOpenNavigationClickListener());

        refreshLayout.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light, R.color.orange_light);
        refreshLayout.setOnRefreshListener(this);

        listView.setAdapter(adapter);

        Animation dataLoadAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.data_loading);
        dataLoadAnim.setInterpolator(new LinearInterpolator());
        iconLoadingAnim.startAnimation(dataLoadAnim);

        getExamPlanListAsyncTask();
    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onRefresh() {
        getExamPlanListAsyncTask();
    }

    private void getExamPlanListAsyncTask() {
        ApiClient.with(getActivity()).apiService.getExamPlanList(LoginShared.getLoginToken(getActivity()), new BackgroundCallback<List<ExamPlan>>(getActivity()) {

            @Override
            public void handleSuccess(List<ExamPlan> examPlanList, Response response) {
                if (examPlanList.size() == 0) {
                    showEmptyLayout("暂时没有考试信息，过一个月再来看看吧。");
                } else {
                    Collections.sort(examPlanList); // 排序
                    adapter.setExamPlanList(examPlanList);
                    adapter.notifyDataSetChanged();
                    refreshLayout.setVisibility(View.VISIBLE);
                    layoutLoading.setVisibility(View.GONE);
                    layoutEmpty.setVisibility(View.GONE);
                }
                onFinish();
            }

            @Override
            public void handleFailure(String message) {
                if (adapter.getCount() == 0) {
                    showEmptyLayout(message);
                } else {
                    ToastUtils.with(getActivity()).show(message);
                }
                onFinish();
            }

            private void onFinish() {
                refreshLayout.setRefreshing(false);
            }

        });
    }

    private void showEmptyLayout(String message) {
        refreshLayout.setVisibility(View.GONE);
        layoutLoading.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.VISIBLE);
        tvLoadFailed.setText(message);
    }

    @OnClick(R.id.main_exam_plan_layout_empty)
    protected void onBtnLayoutEmptyClick() {
        refreshLayout.setVisibility(View.GONE);
        layoutLoading.setVisibility(View.VISIBLE);
        layoutEmpty.setVisibility(View.GONE);
        getExamPlanListAsyncTask();
    }

}

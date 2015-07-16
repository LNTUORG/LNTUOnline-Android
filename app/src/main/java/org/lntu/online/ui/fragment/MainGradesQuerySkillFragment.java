package org.lntu.online.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import org.lntu.online.model.entity.SkillTestScore;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.adapter.MainGradesQuerySkillAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import retrofit.client.Response;

public class MainGradesQuerySkillFragment extends Fragment {

    @Bind(R.id.main_grades_query_skill_list_view)
    protected ListView listView;

    @Bind(R.id.main_grades_query_skill_icon_loading)
    protected View iconLoading;

    @Bind(R.id.main_grades_query_skill_icon_empty)
    protected View iconEmpty;

    @Bind(R.id.main_grades_query_skill_icon_loading_anim)
    protected View iconLoadingAnim;

    @Bind(R.id.main_grades_query_skill_tv_load_failed)
    protected TextView tvLoadFailed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_grades_query_skill, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Animation dataLoadAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.data_loading);
        dataLoadAnim.setInterpolator(new LinearInterpolator());
        iconLoadingAnim.startAnimation(dataLoadAnim);

        startNetwork();
    }

    private void startNetwork() {
        ApiClient.with(getActivity()).apiService.getSkillTestScoreList(LoginShared.getLoginToken(getActivity()), new BackgroundCallback<List<SkillTestScore>>(getActivity()) {

            @Override
            public void handleSuccess(List<SkillTestScore> skillTestScoreList, Response response) {
                if (skillTestScoreList.size() == 0) {
                    showIconEmptyView("您还没有参加过技能考试呢，赶快去报名啊~~");
                } else {
                    listView.setAdapter(new MainGradesQuerySkillAdapter(getActivity(), skillTestScoreList));
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

    @OnClick(R.id.main_grades_query_skill_icon_empty)
    protected void onBtnIconEmptyClick() {
        iconLoading.setVisibility(View.VISIBLE);
        iconEmpty.setVisibility(View.GONE);
        startNetwork();
    }

}

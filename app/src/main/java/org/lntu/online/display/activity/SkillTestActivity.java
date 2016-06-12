package org.lntu.online.display.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.display.adapter.SkillTestAdapter;
import org.lntu.online.display.base.StatusBarActivity;
import org.lntu.online.display.listener.NavigationFinishClickListener;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.BackgroundCallback;
import org.lntu.online.model.entity.SkillTestScore;
import org.lntu.online.model.storage.LoginShared;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;

public class SkillTestActivity extends StatusBarActivity {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.list_view)
    protected ListView listView;

    @BindView(R.id.icon_loading)
    protected View iconLoading;

    @BindView(R.id.icon_empty)
    protected View iconEmpty;

    @BindView(R.id.icon_loading_anim)
    protected View iconLoadingAnim;

    @BindView(R.id.tv_load_failed)
    protected TextView tvLoadFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_test);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        Animation dataLoadAnim = AnimationUtils.loadAnimation(this, R.anim.data_loading);
        dataLoadAnim.setInterpolator(new LinearInterpolator());
        iconLoadingAnim.startAnimation(dataLoadAnim);

        startNetwork();
    }

    private void startNetwork() {
        ApiClient.service.getSkillTestScoreList(LoginShared.getLoginToken(this), new BackgroundCallback<List<SkillTestScore>>(this) {

            @Override
            public void handleSuccess(List<SkillTestScore> skillTestScoreList, Response response) {
                if (skillTestScoreList.size() == 0) {
                    showIconEmptyView("您还没有参加过技能考试呢，赶快去报名啊~~");
                } else {
                    listView.setAdapter(new SkillTestAdapter(SkillTestActivity.this, skillTestScoreList));
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

    @OnClick(R.id.icon_empty)
    protected void onBtnIconEmptyClick() {
        iconLoading.setVisibility(View.VISIBLE);
        iconEmpty.setVisibility(View.GONE);
        startNetwork();
    }

}

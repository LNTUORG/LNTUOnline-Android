package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.lntu.online.R;

import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.BackgroundCallback;
import org.lntu.online.model.entity.UnpassCourse;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.adapter.UnpassCourseAdapter;
import org.lntu.online.ui.base.BaseActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import retrofit.client.Response;

public class UnpassCourseActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.unpass_course_ex_list_view)
    protected ExpandableListView exListView;

    @Bind(R.id.unpass_course_icon_loading)
    protected View iconLoading;

    @Bind(R.id.unpass_course_icon_empty)
    protected View iconEmpty;

    @Bind(R.id.unpass_course_icon_loading_anim)
    protected View iconLoadingAnim;

    @Bind(R.id.unpass_course_tv_load_failed)
    protected TextView tvLoadFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpass_course);
        ButterKnife.bind(this);

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

    private void startNetwork() {
        ApiClient.with(this).apiService.getUnpassCourseList(LoginShared.getLoginToken(this), new BackgroundCallback<List<UnpassCourse>>(this) {

            @Override
            public void handleSuccess(List<UnpassCourse> unpassCourseList, Response response) {
                if (unpassCourseList.size() == 0) {
                    showIconEmptyView("目前没有未通过的课程。");
                } else {
                    exListView.setAdapter(new UnpassCourseAdapter(UnpassCourseActivity.this, unpassCourseList));
                    exListView.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.unpass_course_icon_empty)
    protected void onBtnIconEmptyClick() {
        iconLoading.setVisibility(View.VISIBLE);
        iconEmpty.setVisibility(View.GONE);
        startNetwork();
    }

}

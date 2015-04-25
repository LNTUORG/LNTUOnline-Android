package org.lntu.online.ui.activity;

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

import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.BackgroundCallback;
import org.lntu.online.model.entity.Grades;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.adapter.GradesAdapter;
import org.lntu.online.ui.base.BaseActivity;

import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.client.Response;

public class GradesActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.grades_list_view)
    protected ListView listView;

    @InjectView(R.id.grades_icon_loading)
    protected View iconLoading;

    @InjectView(R.id.grades_icon_empty)
    protected View iconEmpty;

    @InjectView(R.id.grades_icon_loading_anim)
    protected View iconLoadingAnim;

    @InjectView(R.id.grades_tv_load_failed)
    protected TextView tvLoadFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
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

    private void startNetwork() {
        ApiClient.with(this).apiService.getGrades(LoginShared.getLoginToken(this), new BackgroundCallback<Grades>(this) {

            @Override
            public void handleSuccess(Grades grades, Response response) {
                if (grades.getCourseScores().size() == 0) {
                    showIconEmptyView("暂时没有成绩信息。");
                } else {
                    Collections.sort(grades.getCourseScores()); // 排序
                    listView.setAdapter(new GradesAdapter(GradesActivity.this, grades.getCourseScores()));
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

    @OnClick(R.id.grades_icon_empty)
    public void onBtnIconEmptyClick() {
        iconLoading.setVisibility(View.VISIBLE);
        iconEmpty.setVisibility(View.GONE);
        startNetwork();
    }

}

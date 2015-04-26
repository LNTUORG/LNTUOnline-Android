package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lntu.online.R;
import com.melnykov.fab.FloatingActionButton;

import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.BackgroundCallback;
import org.lntu.online.model.entity.Grades;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.adapter.GradesAdapter;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.util.ToastUtils;

import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import retrofit.client.Response;

public class GradesActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.grades_layout_content)
    protected ViewGroup layoutContent;

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

    @InjectView(R.id.grades_tv_ava_credit)
    protected TextView tvAvaCredit;

    @InjectView(R.id.grades_fab)
    protected FloatingActionButton fab;

    @InjectView(R.id.grades_layout_condition)
    protected ViewGroup layoutCondition;

    @InjectView(R.id.grades_spn_year)
    protected Spinner spnYear;

    @InjectView(R.id.grades_spn_term)
    protected Spinner spnTerm;

    @InjectView(R.id.grades_spn_level)
    protected Spinner spnLevel;

    @InjectView(R.id.grades_spn_display)
    protected Spinner spnDisplay;

    private GradesAdapter adapter;



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

        fab.attachToListView(listView);

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
                    showLayoutContent(grades);
                }
            }

            @Override
            public void handleFailure(String message) {
                showIconEmptyView(message);
            }

        });
    }

    private void showLayoutContent(Grades grades) {
        tvAvaCredit.setText(grades.getAverageCredit().getSummary());
        Collections.sort(grades.getCourseScores()); // 排序
        adapter = new GradesAdapter(this, grades.getCourseScores());
        listView.setAdapter(adapter);

        int firstYear = grades.getCourseScores().get(0).getYear();
        int lastYear = grades.getCourseScores().get(grades.getCourseScores().size() - 1).getYear();
        String[] years = new String[firstYear - lastYear + 2];
        years[0] = "全部";
        for (int n = 1; n < years.length; n++) {
            years[n] = firstYear - n + 1 + "";
        }
        ArrayAdapter<String> spnYearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        spnYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYear.setAdapter(spnYearAdapter);

        layoutContent.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        iconLoading.setVisibility(View.GONE);
        iconEmpty.setVisibility(View.GONE);
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

    @OnClick(R.id.grades_layout_condition)
    public void onBtnLayoutConditionClick() {
        layoutCondition.setVisibility(View.GONE);
        fab.setImageResource(R.drawable.ic_search_white_24dp);
    }

    @Override
    public void onBackPressed() {
        if (layoutCondition.getVisibility() == View.VISIBLE) {
            layoutCondition.setVisibility(View.GONE);
            fab.setImageResource(R.drawable.ic_search_white_24dp);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.grades_fab)
    public void onBtnFabClick() {
        if (layoutCondition.getVisibility() == View.GONE) {
            layoutCondition.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.ic_done_white_24dp);
        } else {
            layoutCondition.setVisibility(View.GONE);
            fab.setImageResource(R.drawable.ic_search_white_24dp);
        }
    }

}

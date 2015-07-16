package org.lntu.online.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import org.lntu.online.R;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.BackgroundCallback;
import org.lntu.online.model.entity.Grades;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.adapter.MainGradesQueryCourseAdapter;

import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import retrofit.client.Response;

public class MainGradesQueryCourseFragment extends Fragment {

    @Bind(R.id.main_grades_query_course_recycler_view)
    protected RecyclerView recyclerView;

    @Bind(R.id.main_grades_query_course_icon_loading)
    protected View iconLoading;

    @Bind(R.id.main_grades_query_course_icon_empty)
    protected View iconEmpty;

    @Bind(R.id.main_grades_query_course_icon_loading_anim)
    protected View iconLoadingAnim;

    @Bind(R.id.main_grades_query_course_tv_load_failed)
    protected TextView tvLoadFailed;

    @Bind(R.id.main_grades_query_course_fab)
    protected FloatingActionButton fab;

    @Bind(R.id.main_grades_query_course_layout_condition)
    protected ViewGroup layoutCondition;

    @Bind(R.id.main_grades_query_course_spn_year)
    protected Spinner spnYear;

    @Bind(R.id.main_grades_query_course_spn_term)
    protected Spinner spnTerm;

    @Bind(R.id.main_grades_query_course_spn_level)
    protected Spinner spnLevel;

    @Bind(R.id.main_grades_query_course_spn_display)
    protected Spinner spnDisplay;

    private MainGradesQueryCourseAdapter adapter;

    // 最后一次使用的过滤条件-默认条件为全部
    private int lastYear = 0;
    private String lastTerm = "";
    private Grades.Level lastLevel = null;
    private boolean lastDisplayMax = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_grades_query_course, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Animation dataLoadAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.data_loading);
        dataLoadAnim.setInterpolator(new LinearInterpolator());
        iconLoadingAnim.startAnimation(dataLoadAnim);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MainGradesQueryCourseAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        fab.attachToRecyclerView(recyclerView);

        startNetwork();
    }

    private void startNetwork() {
        ApiClient.with(getActivity()).apiService.getGrades(LoginShared.getLoginToken(getActivity()), new BackgroundCallback<Grades>(getActivity()) {

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
        Collections.sort(grades.getCourseScores()); // 排序
        adapter.setGrades(grades);

        int topYear = grades.getCourseScores().get(0).getYear();
        int bottomYear = grades.getCourseScores().get(grades.getCourseScores().size() - 1).getYear();
        String[] years = new String[topYear - bottomYear + 2];
        years[0] = "全部";
        for (int n = 1; n < years.length; n++) {
            years[n] = topYear - n + 1 + "";
        }
        ArrayAdapter<String> spnYearAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
        spnYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYear.setAdapter(spnYearAdapter);

        recyclerView.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        iconLoading.setVisibility(View.GONE);
        iconEmpty.setVisibility(View.GONE);
    }

    private void showIconEmptyView(String message) {
        iconLoading.setVisibility(View.GONE);
        iconEmpty.setVisibility(View.VISIBLE);
        tvLoadFailed.setText(message);
    }

    @OnClick(R.id.main_grades_query_course_icon_empty)
    protected void onBtnIconEmptyClick() {
        iconLoading.setVisibility(View.VISIBLE);
        iconEmpty.setVisibility(View.GONE);
        startNetwork();
    }

    @OnClick(R.id.main_grades_query_course_layout_condition_center)
    protected void onBtnLayoutConditionCenterClick() {
        // 屏蔽条件面板中间事件
    }

    @OnClick({
            R.id.main_grades_query_course_fab,
            R.id.main_grades_query_course_layout_condition
    })
    protected void onBtnFabClick() {
        if (layoutCondition.getVisibility() == View.GONE) {
            layoutCondition.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.ic_done_white_24dp);
        } else {
            layoutCondition.setVisibility(View.GONE);
            fab.setImageResource(R.drawable.ic_search_white_24dp);
            if (needUpdateListView()) {
                updateListView();
            }
        }
    }

    private Grades.Level getCurrentLevel() {
        switch (spnLevel.getSelectedItemPosition()) {
            case 1:
                return Grades.Level.GREAT;
            case 2:
                return Grades.Level.NORMAL;
            case 3:
                return Grades.Level.UNPASS;
            default:
                return null;
        }
    }

    private boolean needUpdateListView() {
        if (lastYear != (spnYear.getSelectedItemPosition() == 0 ? 0 : Integer.parseInt(spnYear.getSelectedItem().toString()))) {
            return true;
        }
        else if (!lastTerm.equals(spnTerm.getSelectedItemPosition() == 0 ? "" : spnTerm.getSelectedItem().toString())) {
            return true;
        }
        else if (lastLevel != getCurrentLevel()) {
            return true;
        }
        else {
            return lastDisplayMax == (spnDisplay.getSelectedItemPosition() == 0);
        }
    }

    private void updateListView() {
        lastYear = spnYear.getSelectedItemPosition() == 0 ? 0 : Integer.parseInt(spnYear.getSelectedItem().toString());
        lastTerm = spnTerm.getSelectedItemPosition() == 0 ? "" : spnTerm.getSelectedItem().toString();
        lastLevel = getCurrentLevel();
        lastDisplayMax = spnDisplay.getSelectedItemPosition() != 0;
        adapter.updateListView(lastYear, lastTerm, lastLevel, lastDisplayMax);
        recyclerView.scrollToPosition(0);
    }

}

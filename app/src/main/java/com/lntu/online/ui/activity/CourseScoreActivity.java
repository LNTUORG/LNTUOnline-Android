package com.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.reflect.TypeToken;
import com.lntu.online.R;
import com.lntu.online.ui.adapter.CourseScoreAdapter;
import com.lntu.online.model.http.BaseListener;
import com.lntu.online.model.http.HttpUtil;
import com.lntu.online.model.http.NormalAuthListener;
import com.lntu.online.model.http.RetryAuthListener;
import com.lntu.online.config.NetworkInfo;
import com.lntu.online.shared.UserInfo;
import com.lntu.online.model.entity.CourseScore;
import com.lntu.online.util.JsonUtil;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CourseScoreActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.grades_spn_year)
    protected Spinner spnYear;

    @InjectView(R.id.grades_spn_term)
    protected Spinner spnTerm;

    @InjectView(R.id.grades_tv_ava_of_credit)
    protected TextView tvAvaOfCredit;

    @InjectView(R.id.grades_tv_title)
    protected TextView tvTitle;

    @InjectView(R.id.grades_list_view)
    protected ListView listView;

    private BaseAdapter adapter;
    private List<CourseScore> scoreList;

    private Time time;
    private String[] strsYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_score);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidgets();
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

    private void initWidgets() {
        //初始化时间
        time = new Time();
        time.setToNow();
        //绑定下拉控件
        spnTerm.setSelection((time.month >= 2 && time.month < 8 ? 0 : 1)); //选择春还是秋
        //计算年数
        String userId = UserInfo.getSavedUserId(this);
        int startYear = Integer.parseInt("20" + userId.substring(0, 2));
        int endYear = time.year;
        if (time.month >= 0 && time.month < 2) {
            endYear--;
        }
        if (endYear < startYear) { //容错处理
            endYear = 3000;
        }
        strsYear = new String[endYear - startYear + 2];
        strsYear[0] = "全部";
        for (int n = 0; n < endYear - startYear + 1; n++) {
            strsYear[n + 1] = endYear - n + "";
        }
        ArrayAdapter<String> spnYearAdp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strsYear);
        spnYearAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYear.setAdapter(spnYearAdp);
        spnYear.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spnTerm.setEnabled(false);
                }
                else if (position == strsYear.length - 1) {
                    spnTerm.setSelection(1);
                    spnTerm.setEnabled(false);
                } else {
                    spnTerm.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        scoreList = new ArrayList<CourseScore>();
        adapter = new CourseScoreAdapter(this, scoreList);
        listView.setAdapter(adapter);
    }

    private void startNetwork() {
        HttpUtil.get(this, NetworkInfo.serverUrl + "grades/averageOfCreditPointInfo", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                String[] msgs = responseString.split("\n");
                if ((msgs[0] + "").equals("0x00000000")) {
                    tvAvaOfCredit.setText(msgs[1] + "");
                }
                else if ((msgs[0] + "").equals("0x01050004")) { //需要评课
                    new MaterialDialog.Builder(getContext())
                            .title("提示")
                            .content("您本学期课程没有参加评教，不能查看成绩。马上去评课？")
                            .positiveText("评课")
                            .negativeText("取消")
                            .positiveColorRes(R.color.colorPrimary)
                            .negativeColorRes(R.color.textColorPrimary)
                            .callback(new MaterialDialog.ButtonCallback() {

                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    startActivity(new Intent(getContext(), OneKeyActivity.class));
                                    finish();
                                }

                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    finish();
                                }

                            })
                            .cancelable(false)
                            .show();
                } else {
                    showErrorDialog("提示", msgs[0], msgs[1]);
                }
            }

            @Override
            public void onBtnRetry() {
                startNetwork();
            }

        });
    }

    @OnClick(R.id.grades_btn_query)
    public void onBtnQuery(View view) {
        //构建监听器
        BaseListener listener = new NormalAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    List<CourseScore> newList = JsonUtil.fromJson(responseString, new TypeToken<List<CourseScore>>(){}.getType());
                    scoreList.clear();
                    scoreList.addAll(newList);
                    adapter.notifyDataSetChanged();
                    listView.setSelection(0); //返回ListView顶部
                    if (spnYear.getSelectedItemPosition() == 0) {
                        tvTitle.setText("全部课程成绩");
                    } else {
                        tvTitle.setText(spnYear.getSelectedItem().toString() + "年 " + spnTerm.getSelectedItem().toString() + " 课程成绩");
                    }
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    showErrorDialog("提示", msgs[0], msgs[1]);
                }
            }

        };
        //判断是否选择全部
        if (spnYear.getSelectedItemPosition() == 0) { //选择全部
            HttpUtil.get(this, NetworkInfo.serverUrl + "grades/allCourseScoresInfo", listener);
        } else { //没选择全部
            RequestParams params = new RequestParams();
            params.put("year", spnYear.getSelectedItem().toString());
            params.put("term", (spnTerm.getSelectedItemPosition() == 0 ? 1 : 2) + "");
            HttpUtil.get(this, NetworkInfo.serverUrl + "grades/courseScoresInfo", params, listener);
        }
    }

}

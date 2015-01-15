package com.lntu.online;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lntu.online.http.BaseListener;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.NormalAuthListener;
import com.lntu.online.http.RetryAuthListener;
import com.lntu.online.info.NetworkInfo;
import com.lntu.online.info.UserInfo;
import com.lntu.online.model.ClientCourseScore;
import com.lntu.online.util.JsonUtil;
import com.loopj.android.http.RequestParams;

public class CourseScoreActivity extends Activity {

    private Spinner spnYear;
    private Spinner spnTerm;
    private Spinner spnTermNon;
    private TextView tvAvaOfCredit;
    private TextView tvTitle;
    private ListView lvInfo;

    private Time time;
    private String[] strsYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_score);
        initWidgets();
        startNetwork();
    }

    private void initWidgets() {
        //初始化时间
        time = new Time();
        time.setToNow();
        //绑定下拉控件
        spnTerm = (Spinner) findViewById(R.id.grades_spn_term);
        spnTerm.setSelection((time.month >= 2 && time.month < 8 ? 0 : 1)); //选择春还是秋
        spnTermNon = (Spinner) findViewById(R.id.grades_spn_term_non);
        spnTermNon.setVisibility(View.GONE);
        spnTermNon.setEnabled(false);
        //计算年数
        String userId = UserInfo.getSavedUserId();
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
        spnYear = (Spinner) findViewById(R.id.grades_spn_year);
        ArrayAdapter<String> spnYearAdp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strsYear);
        spnYearAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYear.setAdapter(spnYearAdp);
        spnYear.setSelection(1);
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

                if (position == 0) {
                    spnTermNon.setVisibility(View.VISIBLE);
                    spnTerm.setVisibility(View.GONE);
                } else {
                    spnTermNon.setVisibility(View.GONE);
                    spnTerm.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
        tvAvaOfCredit = (TextView) findViewById(R.id.grades_tv_ava_of_credit);
        tvTitle = (TextView) findViewById(R.id.grades_tv_title);
        lvInfo = (ListView) findViewById(R.id.grades_lv_info);
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
                    new AlertDialog.Builder(getContext())
                    .setTitle("提示")
                    .setMessage("您本学期课程没有参加评教，不能查看成绩。马上去评课？")
                    .setPositiveButton("评课", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getContext(), OneKeyActivity.class));
                            finish();
                        }

                    })
                    .setNegativeButton("取消", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setCancelable(false)
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

    public void onBtnQuery(View view) {
        //构建监听器
        BaseListener listener = new NormalAuthListener(this) {
            
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {                    
                    List<ClientCourseScore> ccss = JsonUtil.fromJson(responseString, new TypeToken<List<ClientCourseScore>>(){}.getType());
                    lvInfo.setAdapter(new ListViewAdapter(getContext(), ccss));
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

    private class ListViewAdapter extends BaseAdapter {

        private List<View> itemViews;

        public ListViewAdapter(Context context, List<ClientCourseScore> ccss) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemViews = new ArrayList<View>();
            for (int n = 0; n < ccss.size(); n++) {
                ClientCourseScore ccs = ccss.get(n);
                //布局
                View itemView = inflater.inflate(R.layout.activity_course_score_item, null);
                TextView tvNum = (TextView) itemView.findViewById(R.id.course_score_item_tv_num);
                TextView tvName = (TextView) itemView.findViewById(R.id.course_score_item_tv_name);
                TextView tvIndex = (TextView) itemView.findViewById(R.id.course_score_item_tv_index);
                TextView tvScore = (TextView) itemView.findViewById(R.id.course_score_item_tv_score);
                TextView tvCredit = (TextView) itemView.findViewById(R.id.course_score_item_tv_credit);
                TextView tvTestMode = (TextView) itemView.findViewById(R.id.course_score_item_tv_test_mode);
                TextView tvSelectType = (TextView) itemView.findViewById(R.id.course_score_item_tv_select_type);
                TextView tvRemarks = (TextView) itemView.findViewById(R.id.course_score_item_tv_remarks);
                TextView tvExamType = (TextView) itemView.findViewById(R.id.course_score_item_tv_exam_type);
                TextView tvSemester = (TextView) itemView.findViewById(R.id.course_score_item_tv_semester);
                tvNum.setText(ccs.getNum() + "");
                tvName.setText(ccs.getName() + "");
                tvIndex.setText(ccs.getIndex() + "");
                tvScore.setText(ccs.getScore() + "");
                { //得分红色标记
                    try {
                        float s = Float.valueOf(ccs.getScore());
                        if (s < 60.0f) {
                            tvScore.setTextColor(0xFFFF0000);
                        }
                    } catch(Exception e) {    
                    }
                }
                tvCredit.setText(ccs.getCredit() + "");
                tvTestMode.setText(ccs.getTestMode() + "");
                tvSelectType.setText(ccs.getSelectType() + "");
                tvRemarks.setText(ccs.getRemarks() + "");
                tvExamType.setText(ccs.getExamType() + "");
                tvSemester.setText(ccs.getSemester() + "");
                //填充布局
                itemViews.add(itemView);
            }
        }

        @Override
        public int getCount() {
            return itemViews.size();
        }

        @Override
        public Object getItem(int position) {
            return itemViews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return itemViews.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return itemViews.get(position);
        }

    }

    public void onActionBarBtnLeft(View view) {
        finish();
    }

}

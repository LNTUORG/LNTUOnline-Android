package com.lntu.online.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lntu.online.R;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.RetryAuthListener;
import com.lntu.online.info.NetworkInfo;
import com.lntu.online.model.ClientUnpassCourse;
import com.lntu.online.util.JsonUtil;
import com.melnykov.fab.FloatingActionButton;

public class UnpassCourseActivity extends ActionBarActivity {

    private Toolbar toolbar;

    private ListView listView;
    private List<ClientUnpassCourse> cucs;

    private FloatingActionButton fab;
    private boolean isUnfold = true; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpass_course);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        listView = (ListView) findViewById(R.id.unpass_course_lv_root);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(listView);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (cucs != null) {
                    isUnfold = !isUnfold;
                    listView.setAdapter(new ListViewAdapter(UnpassCourseActivity.this, cucs, !isUnfold));
                    fab.setImageResource(isUnfold ? R.drawable.ic_unfold_less_white_24dp : R.drawable.ic_unfold_more_white_24dp);
                    if (!isUnfold) {
                    	Toast.makeText(UnpassCourseActivity.this, "已合并相同科目", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

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
        HttpUtil.get(this, NetworkInfo.serverUrl + "grades/unpassCoursesInfo", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    cucs = JsonUtil.fromJson(responseString, new TypeToken<List<ClientUnpassCourse>>(){}.getType());
                    listView.setAdapter(new ListViewAdapter(getContext(), cucs, false));
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    if (msgs[0].equals("0x01050003")) { //没信息
                        showNothingDialog();
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
            }

            @Override
            public void onBtnRetry() {
                startNetwork();
            }

        });
    }

    private void showNothingDialog() {
        new AlertDialog.Builder(this)
        .setTitle("提示")
        .setMessage("暂时没有挂科信息，暂时")
        .setCancelable(false)
        .setPositiveButton("确定", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        })
        .show();
    }

    private class ListViewAdapter extends BaseAdapter {

        private List<View> itemViews;

        public ListViewAdapter(Context context, List<ClientUnpassCourse> cucs, boolean isSimple) {
            if (isSimple) {
                initSimpleViews(context, cucs);
            } else {
                initViews(context, cucs);
            }
        }

        private void initViews(Context context, List<ClientUnpassCourse> cucs) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemViews = new ArrayList<View>();
            for (int n = 0; n < cucs.size(); n++) {
                ClientUnpassCourse cuc = cucs.get(n);
                //布局
                View itemView = inflater.inflate(R.layout.activity_unpass_course_item, null);
                TextView tvNum = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_num);
                TextView tvName = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_name);
                TextView tvIndex = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_index);
                TextView tvScore = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_score);
                TextView tvCredit = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_credit);
                TextView tvCreditPoint = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_credit_point);
                TextView tvSelectType = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_select_type);
                TextView tvRemarks = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_remarks);
                TextView tvExamType = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_exam_type);
                TextView tvSemester = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_semester);
                tvNum.setText(cuc.getNum() + "");
                tvName.setText(cuc.getName() + "");
                tvIndex.setText(cuc.getIndex() + "");
                tvScore.setText(cuc.getScore() + "");
                tvCredit.setText(cuc.getCredit() + "");
                tvCreditPoint.setText(cuc.getCreditPoint() + "");
                tvSelectType.setText(cuc.getSelectType() + "");
                tvRemarks.setText(cuc.getRemarks() + "");
                tvExamType.setText(cuc.getExamType() + "");
                tvSemester.setText(cuc.getSemester() + "");
                //填充布局
                itemViews.add(itemView);
            }
        }

        private void initSimpleViews(Context context, List<ClientUnpassCourse> cucs) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemViews = new ArrayList<View>();
            for (int n = 0; n < cucs.size(); n++) {
                ClientUnpassCourse cuc = cucs.get(n);
                if (!(cuc.getExamType() + "").equals("正常考试")) {
                    continue;
                }
                //布局
                View itemView = inflater.inflate(R.layout.activity_unpass_course_item_simple, null);
                TextView tvNum = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_num);
                TextView tvName = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_name);
                TextView tvCredit = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_credit);
                TextView tvSelectType = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_select_type);
                tvNum.setText(cuc.getNum() + "");
                tvName.setText(cuc.getName() + "");
                tvCredit.setText(cuc.getCredit() + "");
                tvSelectType.setText(cuc.getSelectType() + "");
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

}

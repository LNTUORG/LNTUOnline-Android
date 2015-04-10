package com.lntu.online.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.reflect.TypeToken;
import com.lntu.online.R;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.RetryAuthListener;
import com.lntu.online.config.NetworkConfig;
import com.lntu.online.model.UnpassCourse;
import com.lntu.online.util.JsonUtil;
import com.melnykov.fab.FloatingActionButton;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UnpassCourseActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.unpass_course_lv_root)
    protected ListView listView;
    private List<UnpassCourse> cucs;

    @InjectView(R.id.fab)
    protected FloatingActionButton fab;
    private boolean isUnfold = true;

    @InjectView(R.id.unpass_course_icon_unfold_less)
    protected View iconUnfoldLess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpass_course);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        HttpUtil.get(this, NetworkConfig.serverUrl + "grades/unpassCoursesInfo", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    cucs = JsonUtil.fromJson(responseString, new TypeToken<List<UnpassCourse>>(){}.getType());
                    listView.setAdapter(new ListViewAdapter(getContext(), cucs, false));
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    if (msgs[0].equals("0x01050003")) { //没信息
                        showNothingDialog();
                    }
                    else if ((msgs[0] + "").equals("0x01050004")) { //需要评课
                        new MaterialDialog.Builder(getContext())
                                .title("提示")
                                .content("您本学期课程没有参加评教，不能查看成绩。马上去评课？")
                                .cancelable(false)
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
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("暂时没有挂科信息，暂时")
                .cancelable(false)
                .positiveText("确定")
                .positiveColorRes(R.color.colorPrimary)
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        finish();
                    }

                })
                .show();
    }

    @OnClick(R.id.fab)
    public void onBtnUnfoldClick(View v) {
        if (cucs != null) {
            isUnfold = !isUnfold;
            listView.setAdapter(new ListViewAdapter(UnpassCourseActivity.this, cucs, !isUnfold));
            fab.setImageResource(isUnfold ? R.drawable.ic_unfold_less_white_24dp : R.drawable.ic_unfold_more_white_24dp);
            iconUnfoldLess.setVisibility(isUnfold ? View.GONE : View.VISIBLE);
        }
    }

    private class ListViewAdapter extends BaseAdapter {

        private List<View> itemViews;

        public ListViewAdapter(Context context, List<UnpassCourse> cucs, boolean isSimple) {
            if (isSimple) {
                initSimpleViews(context, cucs);
            } else {
                initViews(context, cucs);
            }
        }

        private void initViews(Context context, List<UnpassCourse> cucs) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemViews = new ArrayList<View>();
            for (int n = 0; n < cucs.size(); n++) {
                UnpassCourse cuc = cucs.get(n);
                //布局
                View itemView = inflater.inflate(R.layout.activity_unpass_course_item, null);
                TextView tvNum = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_num);
                TextView tvName = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_name);
                TextView tvScore = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_score);
                TextView tvCredit = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_credit);
                TextView tvSelectType = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_select_type);
                TextView tvRemarks = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_remarks);
                TextView tvExamType = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_exam_type);
                TextView tvSemester = (TextView) itemView.findViewById(R.id.unpass_course_item_tv_semester);
                tvNum.setText(cuc.getNum() + "");
                tvName.setText(cuc.getName() + "");
                tvScore.setText(TextUtils.isEmpty(cuc.getScore()) ? "无成绩" : cuc.getScore());
                tvCredit.setText(cuc.getCredit() + "");
                tvSelectType.setText(cuc.getSelectType() + "");
                tvRemarks.setText(cuc.getRemarks() + "");
                tvExamType.setText(cuc.getExamType() + "");
                tvSemester.setText(cuc.getSemester() + "");
                //填充布局
                itemViews.add(itemView);
            }
        }

        private void initSimpleViews(Context context, List<UnpassCourse> cucs) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemViews = new ArrayList<View>();
            for (int n = 0; n < cucs.size(); n++) {
                UnpassCourse cuc = cucs.get(n);
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

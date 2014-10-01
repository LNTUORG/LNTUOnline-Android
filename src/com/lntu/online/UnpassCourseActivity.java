package com.lntu.online;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.RetryAuthListener;
import com.lntu.online.info.NetworkInfo;
import com.lntu.online.model.client.ClientUnpassCourse;
import com.lntu.online.util.JsonUtil;

public class UnpassCourseActivity extends Activity {

    private ListView lvRoot;
    private CheckBox cbMerge;
    private List<ClientUnpassCourse> cucs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpass_course);
        lvRoot = (ListView) findViewById(R.id.unpass_course_lv_root);
        cbMerge = (CheckBox) findViewById(R.id.unpass_course_cb_merge);
        cbMerge.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (cucs != null) {
                    lvRoot.setAdapter(new ListViewAdapter(UnpassCourseActivity.this, cucs, isChecked));
                }
            }

        });
        startNetwork();
    }

    private void startNetwork() {
        HttpUtil.get(this, NetworkInfo.serverUrl + "grades/unpassCoursesInfo", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {                    
                    cucs = JsonUtil.fromJson(responseString, new TypeToken<List<ClientUnpassCourse>>(){}.getType());
                    lvRoot.setAdapter(new ListViewAdapter(getContext(), cucs, false));
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    if (msgs[0].equals("0x01050003")) {
                    	showNothingDialog();
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
        .setMessage("没有挂科，学霸啊，给你跪了~~ Orz")
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

    public void onActionBarBtnLeft(View view) {
    	finish();
    }

}

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
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.RetryAuthListener;
import com.lntu.online.info.NetworkInfo;
import com.lntu.online.model.client.ClientExamPlan;
import com.lntu.online.util.JsonUtil;

public class ExamPlanActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_plan);
        startNetwork();
    }

    private void startNetwork() {
        HttpUtil.get(this, NetworkInfo.serverUrl + "examPlan/info", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {                    
                    List<ClientExamPlan> ceps = JsonUtil.fromJson(responseString, new TypeToken<List<ClientExamPlan>>(){}.getType());
                    ListView lvRoot = (ListView) findViewById(R.id.exam_plan_lv_root);
                    lvRoot.setAdapter(new ListViewAdapter(getContext(), ceps));
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    if (msgs[0].equals("0x01040003")) {
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
        .setMessage("哇塞，没有考试耶~~")
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

        public ListViewAdapter(Context context, List<ClientExamPlan> ceps) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemViews = new ArrayList<View>();
            for (int n = 0; n < ceps.size(); n++) {
                ClientExamPlan cep = ceps.get(n);
                //布局
                View itemView = inflater.inflate(R.layout.activity_exam_plan_item, null);
                TextView tvCourse = (TextView) itemView.findViewById(R.id.exam_plan_item_tv_course);
                TextView tvTime = (TextView) itemView.findViewById(R.id.exam_plan_item_tv_time);
                TextView tvLocation = (TextView) itemView.findViewById(R.id.exam_plan_item_tv_location);
                tvCourse.setText(cep.getCourse() + "");
                tvTime.setText(cep.getTime() + "");
                tvLocation.setText(cep.getLocation() + "");
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

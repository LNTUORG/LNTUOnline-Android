package com.lntu.online.activity;

import java.util.Collections;
import java.util.List;

import org.apache.http.Header;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.lntu.online.R;
import com.lntu.online.adapter.ExamPlanAdapter;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.RetryAuthListener;
import com.lntu.online.info.NetworkInfo;
import com.lntu.online.model.ClientExamPlan;
import com.lntu.online.util.JsonUtil;

public class ExamPlanActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_plan);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

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
        HttpUtil.get(this, NetworkInfo.serverUrl + "examPlan/info", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    List<ClientExamPlan> ceps = JsonUtil.fromJson(responseString, new TypeToken<List<ClientExamPlan>>(){}.getType());
                    Collections.sort(ceps);
                    ListView lvRoot = (ListView) findViewById(R.id.exam_plan_lv_root);
                    lvRoot.setAdapter(new ExamPlanAdapter(getContext(), ceps));
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
        .setMessage("暂时没有考试信息，过一个月再看吧")
        .setCancelable(false)
        .setPositiveButton("确定", new OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        })
        .show();
    }

}

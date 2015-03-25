package com.lntu.online.activity;

import org.apache.http.Header;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lntu.online.R;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.NormalAuthListener;
import com.lntu.online.info.NetworkConfig;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AdviceActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.feedback_edt_info)
    protected EditText edtInfo;

    @InjectView(R.id.feedback_edt_contact)
    protected EditText edtContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
        	showExitTip();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        showExitTip();
    }

    public void showExitTip() {
        if (edtInfo.getText().toString().equals("")) {
            finish();
            return;
        }
        new AlertDialog.Builder(this)    
        .setTitle("提示")
        .setMessage("亲，您的建议还没有提交呢！确定要离开吗？")
        .setPositiveButton("确定", new OnClickListener() {
                
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

         })
         .setNegativeButton("取消", null)
         .show();
    }

    @OnClick(R.id.advice_btn_submit)
    public void onBtnSubmit(View view) {
        if (edtInfo.getText().toString().equals("")) {
            Toast.makeText(this, "亲，您还没吐槽呢~~", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams();
        params.put("info", edtInfo.getText().toString() + "");
        params.put("contact", edtContact.getText().toString() + "");
        HttpUtil.post(this, NetworkConfig.serverUrl + "feedback/advice", params, new NormalAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if ((responseString + "").equals("OK")) {
                    new AlertDialog.Builder(getContext())    
                    .setTitle("提示")
                    .setMessage("您的建议已经提交成功了，非常感谢呢亲~~")
                    .setCancelable(false)
                    .setPositiveButton("确定", new OnClickListener() {
                            
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                     }).show();
                } else {
                    String[] msgs = responseString.split("\n");
                    showErrorDialog("提示", msgs[0], msgs[1]);
                }
            }

        });
    }

}

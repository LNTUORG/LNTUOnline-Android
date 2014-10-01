package com.lntu.online;

import org.apache.http.Header;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.NormalAuthListener;
import com.lntu.online.info.NetworkInfo;
import com.loopj.android.http.RequestParams;

public class AdviceActivity extends Activity {

	private EditText edtInfo;
	private EditText edtContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        edtInfo = (EditText) findViewById(R.id.feedback_edt_info);
        edtContact = (EditText) findViewById(R.id.feedback_edt_contact);
    }

    public void onActionBarBtnLeft(View view) {
    	showExitTip();
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

	public void onBtnSubmit(View view) {
    	if (edtInfo.getText().toString().equals("")) {
    		Toast.makeText(this, "亲，您还没吐槽呢~~", Toast.LENGTH_SHORT).show();
    		return;
    	}
        RequestParams params = new RequestParams();
        params.put("info", edtInfo.getText().toString() + "");
        params.put("contact", edtContact.getText().toString() + "");
    	HttpUtil.post(this, NetworkInfo.serverUrl + "feedback/advice", params, new NormalAuthListener(this) {

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

package com.lntu.online;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.http.Header;

import com.lntu.online.R;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.NormalAuthListener;
import com.lntu.online.info.AppInfo;
import com.lntu.online.info.NetworkInfo;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;

public class CrashShowActivity extends Activity {

	private String sorry = "" +
		"非常抱歉，程序运行过程中出现了一个无法避免的错误。" +
		"您可以将该问题发送给我们，此举将有助于我们改善应用体验。" +
		"由此给您带来的诸多不便，我们深表歉意，敬请谅解。\n" +
		"----------------\n";

	private String crashLog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crash_show);
		//接收异常对象
		Intent intent = getIntent();
		Throwable e = (Throwable) intent.getSerializableExtra("e");
		//构建字符串
		StringBuffer sb = new StringBuffer();
		sb.append("生产厂商：\n");
        sb.append(Build.MANUFACTURER + "\n\n");
        sb.append("手机型号：\n");
        sb.append(Build.MODEL + "\n\n");
        sb.append("系统版本：\n");
        sb.append(Build.VERSION.RELEASE + "\n\n");
		sb.append("异常时间：\n");
		Time time = new Time();
		time.setToNow();
		sb.append(time.toString() + "\n\n");
		sb.append("异常类型：\n");
		sb.append(e.getClass().getName() + "\n\n");
		sb.append("异常信息：\n");
		sb.append(e.getMessage() + "\n\n");
		sb.append("异常堆栈：\n" );
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		Throwable cause = e.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		sb.append(writer.toString());
		crashLog = sb.toString();
		//显示信息
		TextView tvInfo = (TextView) findViewById(R.id.crash_show_tv_info);
		tvInfo.setText(sorry + crashLog);
	}

	public void onBtnSend(View view) {
		RequestParams params = new RequestParams();
        params.put("info", crashLog);
        params.put("platform", "android");
        params.put("version", AppInfo.getVersionCode());
        params.put("osVer", Build.VERSION.RELEASE);
        params.put("manufacturer", Build.MANUFACTURER);
        params.put("model", Build.MODEL);
    	HttpUtil.post(this, NetworkInfo.serverUrl + "feedback/crashLog", params, new NormalAuthListener(this) {

			@Override
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				if ((responseString + "").equals("OK")) {
					new AlertDialog.Builder(getContext())    
			        .setTitle("提示")
			        .setMessage("问题已经提交，非常感谢")
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

	@Override
	public void onBackPressed() {
		finish();
	}

}

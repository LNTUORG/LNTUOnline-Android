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
		"�ǳ���Ǹ���������й����г�����һ���޷�����Ĵ���" +
		"�����Խ������ⷢ�͸����ǣ��˾ٽ����������Ǹ���Ӧ�����顣" +
		"�ɴ˸�����������಻�㣬�������Ǹ�⣬�����½⡣\n" +
		"----------------\n";

	private String crashLog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crash_show);
		//�����쳣����
		Intent intent = getIntent();
		Throwable e = (Throwable) intent.getSerializableExtra("e");
		//�����ַ���
		StringBuffer sb = new StringBuffer();
		sb.append("�������̣�\n");
        sb.append(Build.MANUFACTURER + "\n\n");
        sb.append("�ֻ��ͺţ�\n");
        sb.append(Build.MODEL + "\n\n");
        sb.append("ϵͳ�汾��\n");
        sb.append(Build.VERSION.RELEASE + "\n\n");
		sb.append("�쳣ʱ�䣺\n");
		Time time = new Time();
		time.setToNow();
		sb.append(time.toString() + "\n\n");
		sb.append("�쳣���ͣ�\n");
		sb.append(e.getClass().getName() + "\n\n");
		sb.append("�쳣��Ϣ��\n");
		sb.append(e.getMessage() + "\n\n");
		sb.append("�쳣��ջ��\n" );
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
		//��ʾ��Ϣ
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
			        .setTitle("��ʾ")
			        .setMessage("�����Ѿ��ύ���ǳ���л")
			        .setCancelable(false)
			        .setPositiveButton("ȷ��", new OnClickListener() {
			                
			        	@Override
			            public void onClick(DialogInterface dialog, int which) {
			                finish();
			            }

			         }).show();
				} else {
                    String[] msgs = responseString.split("\n");
                    showErrorDialog("��ʾ", msgs[0], msgs[1]);
                }
			}

    	});
	}

	@Override
	public void onBackPressed() {
		finish();
	}

}

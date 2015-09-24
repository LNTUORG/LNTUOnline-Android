package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.lntu.online.R;

import org.joda.time.DateTime;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import butterknife.ButterKnife;
import butterknife.Bind;

public class CrashLogActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.crash_show_tv_info)
    protected TextView tvInfo;

    private String crashLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_log);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_error_white_24dp);

        //接收异常对象
        Intent intent = getIntent();
        Throwable e = (Throwable) intent.getSerializableExtra("e");

        //构建字符串
        StringBuilder sb = new StringBuilder();
        sb.append("生产厂商：\n");
        sb.append(Build.MANUFACTURER + "\n\n");
        sb.append("手机型号：\n");
        sb.append(Build.MODEL + "\n\n");
        sb.append("系统版本：\n");
        sb.append(Build.VERSION.RELEASE + "\n\n");
        sb.append("异常时间：\n");
        sb.append(new DateTime()).append("\n\n");
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
        tvInfo.setText(crashLog);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crash_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO
                return true;
            case R.id.action_send:
                // TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

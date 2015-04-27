package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.widget.TextView;

import com.lntu.online.R;

import org.lntu.online.model.gson.GsonWrapper;
import org.lntu.online.ui.base.BaseActivity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CrashShowActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.crash_show_tv_info)
    protected TextView tvInfo;

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
        ButterKnife.inject(this);

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
        sb.append(GsonWrapper.gson.toJson(new Date())).append("\n\n");
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
        tvInfo.setText(sorry + crashLog);
    }

    @OnClick(R.id.crash_show_btn_send)
    protected void onBtnSendClick() {

    }

}

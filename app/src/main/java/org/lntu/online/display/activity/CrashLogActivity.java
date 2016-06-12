package org.lntu.online.display.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.lntu.online.R;
import org.lntu.online.display.base.StatusBarActivity;
import org.lntu.online.display.listener.NavigationFinishClickListener;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.CallbackAdapter;
import org.lntu.online.model.storage.LoginShared;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CrashLogActivity extends StatusBarActivity {

    private static final String EXTRA_E = "e";

    public static void start(@NonNull Context context, @NonNull Throwable e) {
        Intent intent = new Intent(context, CrashLogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_E, e);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.tv_info)
    protected TextView tvInfo;

    private String crashLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_log);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        Throwable e = (Throwable) getIntent().getSerializableExtra(EXTRA_E);

        StringBuilder sb = new StringBuilder();
        sb.append("生产厂商：\n");
        sb.append(Build.MANUFACTURER).append("\n\n");
        sb.append("手机型号：\n");
        sb.append(Build.MODEL).append("\n\n");
        sb.append("系统版本：\n");
        sb.append(Build.VERSION.RELEASE).append("\n\n");
        sb.append("异常时间：\n");
        sb.append(new DateTime()).append("\n\n");
        sb.append("异常类型：\n");
        sb.append(e.getClass().getName()).append("\n\n");
        sb.append("异常信息：\n");
        sb.append(e.getMessage()).append("\n\n");
        sb.append("异常堆栈：\n");
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

        tvInfo.setText(crashLog);

        crashLogAsyncTask();
    }

    private void crashLogAsyncTask() {
        ApiClient.service.crashLog(LoginShared.getUserId(this), crashLog, new CallbackAdapter<Void>());
    }

}

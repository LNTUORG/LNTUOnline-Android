package com.lntu.online.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lntu.online.R;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.NormalAuthListener;
import com.lntu.online.info.NetworkConfig;

import com.loopj.android.http.RequestParams;
import com.takwolf.android.util.AppUtils;

import org.apache.http.Header;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CrashShowActivity extends ActionBarActivity {

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
        tvInfo.setText(sorry + crashLog);
    }

    @OnClick(R.id.crash_show_btn_send)
    public void onBtnSend(final View view) {
        RequestParams params = new RequestParams();
        params.put("info", crashLog);
        params.put("platform", "android");
        params.put("version", AppUtils.getVersionCode(this));
        params.put("osVer", Build.VERSION.RELEASE);
        params.put("manufacturer", Build.MANUFACTURER);
        params.put("model", Build.MODEL);
        HttpUtil.post(this, NetworkConfig.serverUrl + "feedback/crashLog", params, new NormalAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (view == null) {
                    return; //强制提交的不提示
                }
                if ((responseString + "").equals("OK")) {
                    new MaterialDialog.Builder(getContext())
                            .title("提示")
                            .content("问题已经提交，非常感谢")
                            .cancelable(false)
                            .positiveText("确定")
                            .positiveColorRes(R.color.colorPrimary)
                            .callback(new MaterialDialog.ButtonCallback() {

                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    finish();
                                }

                            })
                            .show();
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

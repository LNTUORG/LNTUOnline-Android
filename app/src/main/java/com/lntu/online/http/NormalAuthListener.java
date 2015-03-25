package com.lntu.online.http;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lntu.online.R;
import com.lntu.online.activity.MainActivity;

import org.apache.http.Header;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class NormalAuthListener extends BaseListener {

    public NormalAuthListener(Context context, String message) {
        super(context, true, message);
    }

    public NormalAuthListener(Context context) {
        super(context, true);
    }

    @Override
    public void onCancel() {
        Toast.makeText(getContext(), "网络任务被取消", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        if (statusCode == 500) {
            showErrorDialog("网络错误", "0x03010500", "服务器内部错误，请重试");
        }
        else if (statusCode == 502) {
            showErrorDialog("网络错误", "0x03010502", "服务器网关错误，请重试");
        }
        else if (throwable instanceof SocketTimeoutException) {
            showErrorDialog("网络错误", "0x03010002", "服务器连接超时，请重试");
        }
        else if (throwable instanceof IOException) {
            showErrorDialog("网络错误", "0x03010001", "网络通信失败，请检查网络连接");
        } else {
            showErrorDialog("网络错误", "0x03010" + statusCode, "网络访问错误，请重试");
        }
    }

    protected void showErrorDialog(String title, String code, String message) {
        if (code.equals("0x02010001")) { //用户会话未激活
            new MaterialDialog.Builder(getContext())
                    .title(title)
                    .content("用户会话已过期，请重新登录" + "\n" + "错误代码：" + code)
                    .cancelable(false)
                    .positiveText("确定")
                    .positiveColorRes(R.color.colorPrimary)
                    .callback(new MaterialDialog.ButtonCallback() {

                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("is_goback_login", true);
                            getContext().startActivity(intent);
                        }

                    })
                    .show();
        } else {
            new MaterialDialog.Builder(getContext())
                    .title(title)
                    .content(message + "\n" + "错误代码：" + code)
                    .positiveText("确定")
                    .positiveColorRes(R.color.colorPrimary)
                    .show();
        }
    }

}

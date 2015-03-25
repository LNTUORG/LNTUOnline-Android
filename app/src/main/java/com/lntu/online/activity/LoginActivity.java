package com.lntu.online.activity;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.lntu.online.R;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.NormalAuthListener;
import com.lntu.online.info.NetworkConfig;
import com.lntu.online.info.UserInfo;
import com.loopj.android.http.RequestParams;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.takwolf.android.util.AppUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.login_edt_user_id)
    protected MaterialEditText edtUserId;

    @InjectView(R.id.login_edt_pwd)
    protected MaterialEditText edtPwd;

    @InjectView(R.id.login_cb_auto_login)
    protected CheckBox cbAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_account_circle_white_24dp);

        cbAutoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserInfo.setAutoLogin(isChecked);
            }

        });
        //获取用户ID
        String userId = UserInfo.getSavedUserId();
        String pwd = UserInfo.getSavedPwd();
        if (UserInfo.isAutoLogin() && !userId.equals("") && !pwd.equals("")) {
            cbAutoLogin.setChecked(UserInfo.isAutoLogin());
            edtUserId.setText(userId);
            edtPwd.setText(pwd);
            if (getIntent().getBooleanExtra("autoLogin", false) == true) {    
                onBtnLogin(null);
            }
        }
    }

    @OnClick(R.id.login_btn_login)
    public void onBtnLogin(View view) {
        if (edtUserId.getText().toString().equals("")) {
            edtUserId.setError("学号不能为空");
        } 
        else if (edtPwd.getText().toString().equals("")) {
            edtPwd.setError("密码不能为空");
        } else {
            RequestParams params = new RequestParams();
            params.put("userId", edtUserId.getText().toString());
            params.put("pwd", edtPwd.getText().toString());
            params.put("platform", "android"); //平台参数
            params.put("version", AppUtils.getVersionCode(this)); //版本信息
            params.put("osVer", Build.VERSION.RELEASE); //系统版本
            params.put("manufacturer", Build.MANUFACTURER); //生产厂商
            params.put("model", Build.MODEL); //手机型号
            HttpUtil.post(this, NetworkConfig.serverUrl + "user/login", params, new NormalAuthListener(this) {

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if ((responseString + "").equals("OK")) {
                        Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                        UserInfo.setSavedUserId(edtUserId.getText().toString());
                        if (cbAutoLogin.isChecked()) {
                            UserInfo.setSavedPwd(edtPwd.getText().toString());
                        } else {
                            UserInfo.setSavedPwd("");
                        }
                        startActivity(new Intent(getContext(), MainActivity.class));
                        finish();
                    } else {
                        String[] msgs = responseString.split("\n");
                        if ("0x02020002".equals(msgs[0])) {
                            edtPwd.setError("密码未通过验证");
                        } else {
                            showErrorDialog("提示", msgs[0], msgs[1]);
                        }
                    }
                }

            });
        }
    }

    @OnClick(R.id.login_btn_agreement)
    public void onBtnAgreement(View view) {
        startActivity(new Intent(this, AgreementActivity.class));
    }

}

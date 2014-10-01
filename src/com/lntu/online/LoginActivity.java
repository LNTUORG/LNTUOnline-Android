package com.lntu.online;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.NormalAuthListener;
import com.lntu.online.info.AppInfo;
import com.lntu.online.info.NetworkInfo;
import com.lntu.online.info.UserInfo;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends Activity {

    private EditText edtUserId;
    private EditText edtPwd;
    private CheckBox cbAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUserId = (EditText) findViewById(R.id.login_edt_user_id);
        edtPwd = (EditText) findViewById(R.id.login_edt_pwd);
        cbAutoLogin = (CheckBox) findViewById(R.id.login_cb_auto_login);
        cbAutoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserInfo.setAutoLogin(isChecked);
            }
        });
        //��ȡ�û�ID
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

    public void onBtnLogin(View view) {
        if (edtUserId.getText().toString().equals("")) {
            Toast.makeText(this, "�û�������Ϊ��", Toast.LENGTH_SHORT).show();
        } 
        else if (edtPwd.getText().toString().equals("")) {
            Toast.makeText(this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
        } else {
            RequestParams params = new RequestParams();
            params.put("userId", edtUserId.getText().toString());
            params.put("pwd", edtPwd.getText().toString());
            params.put("platform", "android"); //ƽ̨����
            params.put("version", AppInfo.getVersionCode()); //�汾��Ϣ
            params.put("osVer", Build.VERSION.RELEASE); //ϵͳ�汾
            params.put("manufacturer", Build.MANUFACTURER); //��������
            params.put("model", Build.MODEL); //�ֻ��ͺ�
            HttpUtil.post(this, NetworkInfo.serverUrl + "user/login", params, new NormalAuthListener(this) {

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if ((responseString + "").equals("OK")) {
                        Toast.makeText(getContext(), "��¼�ɹ�", Toast.LENGTH_SHORT).show();
                        UserInfo.setSavedUserId(edtUserId.getText().toString());
                        if (cbAutoLogin.isChecked()) {
                            UserInfo.setSavedPwd(edtPwd.getText().toString());
                        } else {
                        	UserInfo.setSavedPwd("");
                        }
                        if (getIntent().getBooleanExtra("gotoMain", false) == true) {
                            startActivity(new Intent(getContext(), MainActivity.class));    
                        }
                        finish();
                    } else {
                        String[] msgs = responseString.split("\n");
                        showErrorDialog("��ʾ", msgs[0], msgs[1]);
                    }
                }

            });
        }
    }

}

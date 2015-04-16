package com.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lntu.online.R;
import com.lntu.online.model.http.HttpUtil;
import com.lntu.online.model.http.NormalAuthListener;
import com.lntu.online.config.NetworkInfo;
import com.lntu.online.ui.base.BaseActivity;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AdviceActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.feedback_edt_info)
    protected EditText edtInfo;

    @InjectView(R.id.feedback_edt_contact)
    protected EditText edtContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
        	showExitTip();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
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
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("您的建议还没有提交呢！确定要离开吗？")
                .positiveText("确定")
                .negativeText("取消")
                .positiveColorRes(R.color.colorPrimary)
                .negativeColorRes(R.color.textColorPrimary)
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        finish();
                    }

                })
                .show();
    }

    @OnClick(R.id.advice_btn_submit)
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
                    new MaterialDialog.Builder(getContext())
                            .title("提交成功")
                            .content("非常感谢您的建议")
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

}

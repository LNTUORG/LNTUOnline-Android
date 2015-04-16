package com.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.reflect.TypeToken;
import com.lntu.online.R;
import com.lntu.online.ui.adapter.SkillTestAdapter;
import com.lntu.online.model.http.HttpUtil;
import com.lntu.online.model.http.RetryAuthListener;
import com.lntu.online.config.NetworkInfo;
import com.lntu.online.model.entityOld.SkillTestScore;
import com.lntu.online.ui.base.BaseActivity;
import com.lntu.online.util.JsonUtil;

import org.apache.http.Header;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SkillTestActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_test);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startNetwork();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void startNetwork() {
        HttpUtil.get(this, NetworkInfo.serverUrl + "grades/skillTestScoresInfo", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {                    
                    List<SkillTestScore> cstss = JsonUtil.fromJson(responseString, new TypeToken<List<SkillTestScore>>(){}.getType());
                    ListView lvRoot = (ListView) findViewById(R.id.skill_test_lv_root);
                    lvRoot.setAdapter(new SkillTestAdapter(getContext(), cstss));
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    if (msgs[0].equals("0x01050003")) {
                        showNothingDialog();
                    } else {
                        showErrorDialog("提示", msgs[0], msgs[1]);
                    }
                }
            }

            @Override
            public void onBtnRetry() {
                startNetwork();
            }

        });
    }
    
    private void showNothingDialog() {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("您还没有参加过技能考试呢，赶快去报名啊~~")
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
    }

}

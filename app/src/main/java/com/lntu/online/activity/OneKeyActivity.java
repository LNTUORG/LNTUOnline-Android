package com.lntu.online.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.reflect.TypeToken;
import com.lntu.online.R;
import com.lntu.online.adapter.OneKeyAdapter;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.NormalAuthListener;
import com.lntu.online.http.RetryAuthListener;
import com.lntu.online.info.NetworkConfig;
import com.lntu.online.model.EvaInfo;
import com.lntu.online.util.JsonUtil;
import com.lntu.online.util.ShipUtils;
import com.loopj.android.http.RequestParams;
import com.melnykov.fab.FloatingActionButton;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class OneKeyActivity extends ActionBarActivity {

    private Toolbar toolbar;

    private FloatingActionButton fab;
    private ListView lvRoot;
    private List<View> itemViews;
    private OneKeyAdapter adapter;
    private List<EvaInfo> evaInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvRoot = (ListView) findViewById(R.id.one_key_lv_root);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(lvRoot);

        itemViews = new ArrayList<View>();
        adapter = new OneKeyAdapter(itemViews);
        lvRoot.setAdapter(adapter);
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
        HttpUtil.get(this, NetworkConfig.serverUrl + "oneKey/info", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    evaInfos = JsonUtil.fromJson(responseString, new TypeToken<List<EvaInfo>>(){}.getType());

                    // TODO 测试代码
                    /*
                    for (ClientEvaInfo info : evaInfos) {
                        info.setState("未评估");
                        info.setUrl("http://takwolf.com");
                    }
                    */

                    updateListView(evaInfos);
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    showErrorDialog("提示", msgs[0], msgs[1]);
                }
            }

            @Override
            public void onBtnRetry() {
                startNetwork();
            }

        });
    }

    private void updateListView(List<EvaInfo> evaInfos) {
        LayoutInflater inflater = LayoutInflater.from(this);
        itemViews.clear();
        for (int n = 0; n < evaInfos.size(); n++) {
            EvaInfo evaInfo = evaInfos.get(n);
            //布局
            View itemView = inflater.inflate(R.layout.activity_one_key_item, null);
            TextView tvTeacher = (TextView) itemView.findViewById(R.id.one_key_item_tv_teacher);
            TextView tvCourse = (TextView) itemView.findViewById(R.id.one_key_item_tv_course);
            TextView tvState = (TextView) itemView.findViewById(R.id.one_key_item_tv_state);
            tvTeacher.setText(evaInfo.getTeacher() + "");
            tvCourse.setText(evaInfo.getCourse() + "");
            tvState.setText(evaInfo.getState() + "");
            if ("已评估".equals(evaInfo.getState())) {
                tvState.setTextColor(0xFF4CAF50);
            } else {
                tvState.setTextColor(0xFFF44336);
            }
            //填充布局
            itemViews.add(itemView);
        }
        adapter.notifyDataSetChanged();
    }

    public void onBtnStart(View view) {
        int n = 0;
        for (EvaInfo evaInfo : evaInfos) {
            if ("未评估".equals(evaInfo.getState())) {
                n++;
            }
        }
        if (n <= 0) { //不需要评估
            new MaterialDialog.Builder(this)
                    .title("提示")
                    .content("您的课程都已经评价完成了，棒棒哒~")
                    .positiveText("确定")
                    .positiveColorRes(R.color.colorPrimary)
                    .show();
        } else { //需要评估
            new MaterialDialog.Builder(this)
                    .title("提示")
                    .content("" +
                            "您有" + n + "门课程需要评价，评价之后才能够正常查询成绩信息。点击【评价】按钮将会授权应用为您自动全部评价为好评。\n" +
                            "您也可以在浏览器登录教务在线手动评价。\n\n" +
                            "您是否授权应用为您自动评价呢？" +
                            "")
                    .positiveText("评价")
                    .negativeText("取消")
                    .positiveColorRes(R.color.colorPrimary)
                    .negativeColorRes(R.color.textColorSecondary)
                    .callback(new MaterialDialog.ButtonCallback() {

                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            startEvaAsyncTask(evaInfos, 0);
                        }

                    })
                    .show();
        }
    }

    private void startEvaAsyncTask(final List<EvaInfo> evaInfos, final int current) {
        if (current == evaInfos.size()) { //评价已经完成
            int n = 0;
            for (EvaInfo evaInfo : evaInfos) {
                if ("未评估".equals(evaInfo.getState())) {
                    n++;
                }
            }
            if (n <= 0) { //不需要评估
                new MaterialDialog.Builder(this)
                        .title("提示")
                        .content("您的课程都已经评价完成了，棒棒哒~\n不给我们一个好评吗？")
                        .cancelable(false)
                        .positiveText("给好评")
                        .negativeText("不评价")
                        .positiveColorRes(R.color.colorPrimary)
                        .negativeColorRes(R.color.textColorSecondary)
                        .callback(new MaterialDialog.ButtonCallback() {

                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                youAreGood();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                youAreBad();
                            }

                        })
                        .show();
            } else { //需要评估
                new MaterialDialog.Builder(this)
                        .title("提示")
                        .content("您有" + n + "门课程评价失败，您可以再试一次。")
                        .positiveText("确定")
                        .positiveColorRes(R.color.colorPrimary)
                        .show();
            }
        } else { //没评完
            final EvaInfo evaInfo = evaInfos.get(current);
            if (TextUtils.isEmpty(evaInfo.getUrl())) { //不需要评价，跳过
                startEvaAsyncTask(evaInfos, current + 1);
            } else { //需要评价
                RequestParams params = new RequestParams();
                params.put("url", evaInfo.getUrl());
                HttpUtil.post(this, NetworkConfig.serverUrl + "oneKey/evaluateOne", params, new NormalAuthListener(this, "正在评价：\n" + evaInfo.getCourse()) {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        if ("OK".equals(responseString)) { //评价成功，进行下一个
                            //改变布局
                            View view = itemViews.get(current);
                            TextView tvState = (TextView) view.findViewById(R.id.one_key_item_tv_state);
                            tvState.setText("已评估");
                            tvState.setTextColor(0xff00ff00);
                            //改变实例
                            evaInfo.setState("已评估");
                            evaInfo.setUrl(null);
                        }
                        startEvaAsyncTask(evaInfos, current + 1);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        startEvaAsyncTask(evaInfos, current + 1);
                    }

                });
            }
        }
    }

    private void youAreGood() {
        ShipUtils.appStore(this);
    }

    private void youAreBad() {
        new MaterialDialog.Builder(this)
                .title("~~o(>_<)o ~~")
                .content("呜呜呜呜呜~~")
                .positiveText(".....")
                .positiveColorRes(R.color.colorPrimary)
                .show();
    }

}

package com.lntu.online.activity;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lntu.online.R;
import com.lntu.online.adapter.MainAdapter;
import com.lntu.online.adapter.MainItemClickListener;
import com.lntu.online.util.AppUtil;
import com.xiaomi.market.sdk.UpdateResponse;
import com.xiaomi.market.sdk.UpdateStatus;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;
import com.xiaomi.market.sdk.XiaomiUpdateListener;

import butterknife.ButterKnife;

public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private TextView tvVersion;

    private GridView gridView;
    private long firstBackKeyTime = 0; //首次返回键按下时间戳

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu, R.string.app_name);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setDrawerShadow(R.drawable.navigation_drawer_shadow, GravityCompat.START);

        tvVersion = (TextView) findViewById(R.id.main_left_tv_version);
        tvVersion.setText("v" + AppUtil.getVersionName(this));

        //GridView
        gridView = (GridView) findViewById(R.id.main_grid_view);
        gridView.setAdapter(new MainAdapter(this));
        gridView.setOnItemClickListener(new MainItemClickListener());

        //checkUpdate
        XiaomiUpdateAgent.setUpdateAutoPopup(false);
        XiaomiUpdateAgent.setUpdateListener(new XiaomiUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.STATUS_UPDATE:
                        // 有更新， UpdateResponse为本次更新的详细信息
                    	
                    	DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    	Uri uri = Uri.parse(updateInfo.path);
                    	Request request = new Request(uri);
                    	downloadManager.enqueue(request);
                    	
                    	
                        break;
                    case UpdateStatus.STATUS_NO_UPDATE:
                        // 无更新， UpdateResponse为null
                        break;
                    case UpdateStatus.STATUS_NO_WIFI:
                        // 设置了只在WiFi下更新，且WiFi不可用时， UpdateResponse为null
                        break;
                    case UpdateStatus.STATUS_NO_NET:
                        // 没有网络， UpdateResponse为null
                        break;
                    case UpdateStatus.STATUS_FAILED:
                        // 检查更新与服务器通讯失败，可稍后再试， UpdateResponse为null
                        break;
                    case UpdateStatus.STATUS_LOCAL_APP_FAILED:
                        // 检查更新获取本地安装应用信息失败， UpdateResponse为null
                        break;
                    default:
                        break;
                }
            }

        });
        XiaomiUpdateAgent.update(this);

    }

    public void onDrawerItemSelected(View view) {
        switch (view.getId()) {
        case R.id.action_browser: {
            Uri uri = Uri.parse("http://60.18.131.131:11180/academic/index.html");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            break;
        }
        case R.id.action_logout:
            showLogoutDialog();
            break;
        case R.id.action_market: {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + getPackageName()));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                intent.setData(Uri.parse("http://zhushou.360.cn/detail/index/soft_id/1964733?recrefer=SE_D_%E8%BE%BD%E5%B7%A5%E5%A4%A7%E6%95%99%E5%8A%A1%E5%9C%A8%E7%BA%BF"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "您的手机没有安装应用商店程序", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
        case R.id.action_feedback:
            startActivity(new Intent(this, AdviceActivity.class));
            break;
        case R.id.action_share: {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TITLE, "辽工大教务在线客户端");
            intent.putExtra(Intent.EXTRA_SUBJECT, "辽工大教务在线客户端");
            intent.putExtra(Intent.EXTRA_TEXT, "辽工大的童鞋，推荐给你一个APP：辽工大教务在线客户端，查课表、查成绩、一键评课没有验证码，还有更多好玩的功能！我们工大人自己的掌上教务在线，下载地址：http://app.pupboss.com");
            startActivity(Intent.createChooser(intent, "分享给好友"));
            break;
        }
        case R.id.action_update:
            XiaomiUpdateAgent.update(this);
            break;
        case R.id.action_settings:
            // TODO
            break;
        case R.id.action_about:
            startActivity(new Intent(this, AboutActivity.class));
            break;
        case R.id.action_help:
            // TODO 这里应该是帮助，暂时链接为用户协议
            startActivity(new Intent(this, AgreementActivity.class));
            break;
        case R.id.action_exit:
            showExitDialog();
            break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            long secondBackKeyTime = System.currentTimeMillis();
            if (secondBackKeyTime - firstBackKeyTime > 2000) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                firstBackKeyTime = secondBackKeyTime;
            } else {
                moveTaskToBack(true);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getBooleanExtra("is_goback_login", false)) { //返回登陆页面
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public void showLogoutDialog() {
        new MaterialDialog.Builder(this)
                .title("注销")
                .content("您确定要注销当前用户吗？")
                .positiveText("确定")
                .negativeText("取消")
                .positiveColorRes(R.color.colorPrimary)
                .negativeColorRes(R.color.textColorSecondary)
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.putExtra("gotoMain", true);
                        startActivity(intent);
                        finish();
                    }

                })
                .show();
    }

    public void showExitDialog() {
        new MaterialDialog.Builder(this)
                .title("退出")
                .content("您确定要退出应用吗？")
                .positiveText("确定")
                .negativeText("取消")
                .positiveColorRes(R.color.colorPrimary)
                .negativeColorRes(R.color.textColorSecondary)
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        finish();
                    }

                })
                .show();
    }

}

package com.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lntu.online.R;
import com.lntu.online.ui.adapter.MainAdapter;
import com.lntu.online.util.ShipUtils;
import com.lntu.online.util.UpdateUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.main_drawer_layout)
    protected DrawerLayout drawerLayout;

    @InjectView(R.id.main_recycler_view)
    protected RecyclerView recyclerView;

    private long firstBackKeyTime = 0; //首次返回键按下时间戳

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu, R.string.app_name);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setDrawerShadow(R.drawable.navigation_drawer_shadow, GravityCompat.START);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new MainAdapter(this));

        UpdateUtils.update(this);
    }

    @OnClick({
            R.id.action_browser,
            R.id.action_logout,
            R.id.action_market,
            R.id.action_feedback,
            R.id.action_share,
            R.id.action_update,
            R.id.action_settings,
            R.id.action_about,
            R.id.action_help,
            R.id.action_exit
    })
    public void onDrawerItemSelected(View view) {
        switch (view.getId()) {
            case R.id.action_browser:
                ShipUtils.webOnline(this);
                break;
            case R.id.action_logout:
                showLogoutDialog();
                break;
            case R.id.action_market:
                ShipUtils.appStore(this);
                break;
            case R.id.action_feedback:
                startActivity(new Intent(this, AdviceActivity.class));
                break;
            case R.id.action_share:
                ShipUtils.share(this);
                break;
            case R.id.action_update:
                UpdateUtils.forceUpdate(this);
                break;
            case R.id.action_settings:
                // TODO
                break;
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.action_help:
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
                .negativeColorRes(R.color.textColorPrimary)
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
                .negativeColorRes(R.color.textColorPrimary)
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        finish();
                    }

                })
                .show();
    }

}

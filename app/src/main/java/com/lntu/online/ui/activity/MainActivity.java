package com.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lntu.online.R;
import com.lntu.online.shared.LoginShared;
import com.lntu.online.ui.adapter.MainAdapter;
import com.lntu.online.ui.base.BaseActivity;
import com.lntu.online.util.ShipUtils;
import com.lntu.online.util.ToastUtils;
import com.lntu.online.util.UpdateUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    public final static String KEY_BACK_TO_ENTRY = "backToEntry";

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.main_drawer_layout)
    protected DrawerLayout drawerLayout;

    @InjectView(R.id.main_recycler_view)
    protected RecyclerView recyclerView;

    private long firstBackKeyTime = 0;

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

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent.getBooleanExtra(KEY_BACK_TO_ENTRY, false)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @OnClick({
            R.id.main_action_browser,
            R.id.main_action_logout,
            R.id.main_action_market,
            R.id.main_action_feedback,
            R.id.main_action_share,
            R.id.main_action_settings,
            R.id.main_action_help,
            R.id.main_action_about
    })
    public void onDrawerItemSelected(View view) {
        switch (view.getId()) {
            case R.id.main_action_browser:
                ShipUtils.webOnline(this);
                break;
            case R.id.main_action_logout:
                showLogoutDialog();
                break;
            case R.id.main_action_market:
                ShipUtils.appStore(this);
                break;
            case R.id.main_action_feedback:
                // startActivity(new Intent(this, AdviceActivity.class));
                break;
            case R.id.main_action_share:
                ShipUtils.share(this);
                break;
            case R.id.main_action_settings:
                // TODO
                break;
            case R.id.main_action_help:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
            case R.id.main_action_about:
                // startActivity(new Intent(this, AboutActivity.class));
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
                ToastUtils.with(this).show(R.string.press_back_again_to_exit);
                firstBackKeyTime = secondBackKeyTime;
            } else {
                finish();
            }
        }
    }

    public void showLogoutDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.logout)
                .content(R.string.logout_tip)
                .contentColorRes(R.color.text_color_primary)
                .positiveText(R.string.confirm)
                .positiveColorRes(R.color.color_primary)
                .negativeText(R.string.cancel)
                .negativeColorRes(R.color.text_color_primary)
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        LoginShared.logout(MainActivity.this);
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }

                })
                .show();
    }

}

package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lntu.online.R;
import com.rey.material.widget.Switch;

import org.lntu.online.model.local.NavMenuHeaderBackgroundType;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements Switch.OnCheckedChangeListener {

    @InjectView(R.id.setting_toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.setting_rg_nav_menu_header_bg)
    protected RadioGroup rgNavMenuHeaderBg;

    @InjectView(R.id.setting_switch_enable_notification)
    protected Switch switchEnableNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switch (LoginShared.getNavMenuHeaderBackgroundType(this)) {
            case color:
                rgNavMenuHeaderBg.check(R.id.setting_rb_nav_menu_header_bg_color);
                break;
            case picture:
                rgNavMenuHeaderBg.check(R.id.setting_rb_nav_menu_header_bg_picture);
                break;
            default:
                rgNavMenuHeaderBg.check(R.id.setting_rb_nav_menu_header_bg_color);
                break;
        }
        switchEnableNotification.setChecked(LoginShared.isEnableNotification(this));
        switchEnableNotification.setOnCheckedChangeListener(this);
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

    /**
     * 背景图切换
     */
    @OnCheckedChanged({
            R.id.setting_rb_nav_menu_header_bg_color,
            R.id.setting_rb_nav_menu_header_bg_picture
    })
    void onRbNavMenuHeaderBgChecked(CompoundButton cb, boolean checked) {
        if (checked) {
            switch(cb.getId()) {
                case R.id.setting_rb_nav_menu_header_bg_color:
                    LoginShared.setNavMenuHeaderBackgroundType(this, NavMenuHeaderBackgroundType.color);
                    break;
                case R.id.setting_rb_nav_menu_header_bg_picture:
                    LoginShared.setNavMenuHeaderBackgroundType(this, NavMenuHeaderBackgroundType.picture);
                    break;
                default:
                    LoginShared.setNavMenuHeaderBackgroundType(this, NavMenuHeaderBackgroundType.color);
                    break;
            }
        }
    }

    /**
     * 切换开启通知的switch
     */
    @OnClick(R.id.setting_btn_enable_notification)
    protected void onBtnEnableNotificationClick() {
        switchEnableNotification.toggle();
    }

    /**
     * 开关切换回调
     */
    @Override
    public void onCheckedChanged(Switch switchView, boolean b) {
        if (switchView.getId() == R.id.setting_switch_enable_notification) {
            LoginShared.setEnableNotification(this, b);
        }
    }

}

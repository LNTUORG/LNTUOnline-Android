package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lntu.online.R;
import com.squareup.picasso.Picasso;

import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.DefaultCallback;
import org.lntu.online.model.entity.Student;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.adapter.MainAdapter;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.util.ShipUtils;
import org.lntu.online.util.ToastUtils;
import org.lntu.online.util.UpdateUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.client.Response;

public class MainActivity extends BaseActivity {

    public static final String KEY_BACK_TO_ENTRY = "backToEntry";

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.main_drawer_layout)
    protected DrawerLayout drawerLayout;

    @InjectView(R.id.main_left_img_avatar)
    protected ImageView imgAvatar;

    @InjectView(R.id.main_left_tv_name)
    protected TextView tvName;

    @InjectView(R.id.main_left_tv_college)
    protected TextView tvCollege;

    @InjectView(R.id.main_left_tv_class_info)
    protected TextView tvClassInfo;

    @InjectView(R.id.main_left_anim)
    protected View iconAnim;

    @InjectView(R.id.main_recycler_view)
    protected RecyclerView recyclerView;

    private boolean asyncStudentFlag = false;
    private long firstBackKeyTime = 0;

    private Animation anim;
    private boolean animPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        drawerLayout.setDrawerShadow(R.drawable.navigation_drawer_shadow, GravityCompat.START);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new MainAdapter(this));

        anim = AnimationUtils.loadAnimation(this, R.anim.data_loading);
        anim.setInterpolator(new LinearInterpolator());

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);
                    return true;
                } else {
                    return super.onOptionsItemSelected(item);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        if (!asyncStudentFlag) {
            Student student = LoginShared.getStudent(this);
            if (student == null) {
                getStudentAsyncTask();
            } else {
                updateStudentView(student);
            }
        }
        super.onResume();
    }

    private void updateStudentView(Student student) {
        Picasso.with(this).load(student.getPhotoUrl()).error(R.drawable.icon_image_default).into(imgAvatar);
        tvName.setText(student.getName());
        tvCollege.setText(student.getCollege());
        tvClassInfo.setText(student.getClassInfo());
        asyncStudentFlag = true;
    }

    private void getStudentAsyncTask() {
        ApiClient.with(this).apiService.getStudent(LoginShared.getLoginToken(this), new DefaultCallback<Student>(this) {

            @Override
            public void success(Student student, Response response) {
                LoginShared.setStudent(MainActivity.this, student);
                updateStudentView(student);
            }

        });
    }

    @OnClick({
            R.id.main_action_browser,
            R.id.main_action_market,
            R.id.main_action_feedback,
            R.id.main_action_share,
            R.id.main_action_settings,
            R.id.main_action_faq,
            R.id.main_action_agreement,
            R.id.main_action_about
    })
    protected void onDrawerItemSelected(View view) {
        switch (view.getId()) {
            case R.id.main_action_browser:
                ShipUtils.webOnline(this);
                break;
            case R.id.main_action_market:
                ShipUtils.appStore(this);
                break;
            case R.id.main_action_feedback:
                startActivity(new Intent(this, AdviceActivity.class));
                break;
            case R.id.main_action_share:
                ShipUtils.share(this);
                break;
            case R.id.main_action_settings:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.main_action_faq:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.main_action_agreement:
                startActivity(new Intent(this, TermsOfServiceActivity.class));
                break;
            case R.id.main_action_about:
                startActivity(new Intent(this, AboutActivity.class));
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

    @OnClick(R.id.main_left_btn_logout)
    protected void onBtnLogoutClick() {
        new MaterialDialog.Builder(this)
                .content(R.string.logout_tip)
                .contentColorRes(R.color.text_color_primary)
                .positiveText(R.string.logout)
                .positiveColorRes(R.color.color_primary)
                .negativeText("不好意思，我点错了")
                .negativeColorRes(R.color.color_primary)
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

    @OnClick(R.id.main_left_btn_anim)
    protected void onBtnAnimClick() {
        if (!animPlaying) {
            iconAnim.startAnimation(anim);
            animPlaying = true;
        } else {
            iconAnim.clearAnimation();
            animPlaying = false;
        }
    }

}

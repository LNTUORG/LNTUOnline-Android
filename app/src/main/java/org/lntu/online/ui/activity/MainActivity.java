package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import org.lntu.online.R;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.DefaultCallback;
import org.lntu.online.model.entity.Student;
import org.lntu.online.storage.LoginShared;
import org.lntu.online.ui.adapter.MainAdapter;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.ui.listener.NavigationOpenClickListener;
import org.lntu.online.util.ToastUtils;
import org.lntu.online.util.UpdateUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;

public class MainActivity extends BaseActivity {

    public static final String KEY_BACK_TO_ENTRY = "backToEntry";

    // 抽屉导航布局
    @Bind(R.id.main_drawer_layout)
    protected DrawerLayout drawerLayout;

    // 导航部分的个人信息
    @Bind(R.id.main_nav_img_avatar)
    protected ImageView imgAvatar;

    @Bind(R.id.main_nav_tv_name)
    protected TextView tvName;

    @Bind(R.id.main_nav_tv_college)
    protected TextView tvCollege;

    @Bind(R.id.main_nav_tv_class_info)
    protected TextView tvClassInfo;

    // 主界面部分
    @Bind(R.id.main_toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.main_recycler_view)
    protected RecyclerView recyclerView;

    // 首次按下返回键时间戳
    private long firstBackPressedTime = 0;

    // 是否同步用户信息flag
    private boolean asyncStudentFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        drawerLayout.setDrawerShadow(R.drawable.navigation_drawer_shadow, GravityCompat.START);

        toolbar.setNavigationOnClickListener(new NavigationOpenClickListener(drawerLayout));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new MainAdapter(this));

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
        Picasso.with(this).load(student.getPhotoUrl()).error(R.drawable.image_default).into(imgAvatar);
        tvName.setText(student.getName());
        tvCollege.setText(student.getCollege());
        tvClassInfo.setText(student.getClassInfo());
        asyncStudentFlag = true;
    }

    private void getStudentAsyncTask() {
        ApiClient.service.getStudent(LoginShared.getLoginToken(this), new DefaultCallback<Student>(this) {

            @Override
            public void success(Student student, Response response) {
                LoginShared.setStudent(MainActivity.this, student);
                updateStudentView(student);
            }

        });
    }















    /**
     * 返回键关闭导航
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            long secondBackPressedTime = System.currentTimeMillis();
            if (secondBackPressedTime - firstBackPressedTime > 2000) {
                Toast.makeText(this, R.string.press_back_again_to_exit, Toast.LENGTH_SHORT).show();
                firstBackPressedTime = secondBackPressedTime;
            } else {
                finish();
            }
        }
    }

    /**
     * 退出登录按钮事件
     */
    @OnClick(R.id.main_nav_btn_logout)
    protected void onBtnLogoutClick() {
        new MaterialDialog.Builder(this)
                .content(R.string.logout_tip)
                .positiveText(R.string.logout)
                .negativeText(R.string.cancel)
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

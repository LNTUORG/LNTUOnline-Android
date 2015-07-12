package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import org.lntu.online.R;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.DefaultCallback;
import org.lntu.online.model.entity.Student;
import org.lntu.online.model.local.NavMenuHeaderBackgroundType;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.util.ToastUtils;
import org.lntu.online.util.UpdateUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import retrofit.client.Response;

public class MainActivity extends BaseActivity {

    public static final String KEY_BACK_TO_ENTRY = "backToEntry";

    // 抽屉导航布局
    @InjectView(R.id.main_drawer_layout)
    protected DrawerLayout drawerLayout;

    // 导航项的消息数
    @InjectView(R.id.main_left_tv_badger_my_class_table)
    protected TextView tvBadgerMyClassTable;

    @InjectView(R.id.main_left_tv_badger_grades_query)
    protected TextView tvBadgerGradesQuery;

    @InjectView(R.id.main_left_tv_badger_exam_plan)
    protected TextView tvBadgerExamPlan;

    @InjectView(R.id.main_left_tv_badger_senate_notice)
    protected TextView tvBadgerSenateNotice;

    @InjectView(R.id.main_left_tv_badger_notification)
    protected TextView tvBadgerNotification;

    // 导航顶部控件
    @InjectView(R.id.main_left_img_avatar)
    protected ImageView imgAvatar;

    @InjectView(R.id.main_left_tv_name)
    protected TextView tvName;

    @InjectView(R.id.main_left_tv_college)
    protected TextView tvCollege;

    @InjectView(R.id.main_left_tv_class_info)
    protected TextView tvClassInfo;

    @InjectView(R.id.main_left_img_nav_header_photo)
    protected ImageView imgNavHeaderPhoto;

    // 主要导航项
    @InjectViews({
            R.id.main_left_btn_my_class_table,
            R.id.main_left_btn_grades_query,
            R.id.main_left_btn_exam_plan,
            R.id.main_left_btn_senate_notice
    })
    protected List<CheckedTextView> navItemList;

    // Fragment
    private BaseFragment fmClassTable;
    private BaseFragment fmGradesQuery;
    private BaseFragment fmExamPlan;
    private BaseFragment fmSenateNotice;

    // Flag
    private boolean asyncStudentFlag = false;
    private long firstBackKeyTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        drawerLayout.setDrawerShadow(R.drawable.navigation_drawer_shadow, GravityCompat.START);

        fmClassTable = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_my_class_table);
        fmGradesQuery = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_grades_query);
        fmExamPlan = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_exam_plan);
        fmSenateNotice = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_senate_notice);
        getSupportFragmentManager()
                .beginTransaction()
                .show(fmClassTable)
                .hide(fmGradesQuery)
                .hide(fmExamPlan)
                .hide(fmSenateNotice)
                .commit();

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
    protected void onResume() {
        if (!asyncStudentFlag) {
            Student student = LoginShared.getStudent(this);
            if (student == null) {
                getStudentAsyncTask();
            } else {
                updateStudentView(student);
            }
        }
        imgNavHeaderPhoto.setVisibility(LoginShared.getNavMenuHeaderBackgroundType(this) == NavMenuHeaderBackgroundType.picture ? View.VISIBLE : View.GONE);
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
        ApiClient.with(this).apiService.getStudent(LoginShared.getLoginToken(this), new DefaultCallback<Student>(this) {

            @Override
            public void success(Student student, Response response) {
                LoginShared.setStudent(MainActivity.this, student);
                updateStudentView(student);
            }

        });
    }

    /**
     * 注销按钮事件
     */
    @OnClick(R.id.main_left_btn_logout)
    protected void onBtnLogoutClick() {
        new MaterialDialog.Builder(this)
                .content(R.string.logout_tip)
                .positiveText(R.string.logout)
                .negativeText("不好意思，我点错了")
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

    /**
     * 根据id选中主导航中的项，id不匹配则不选中
     */
    public void checkNavigationItem(int id) {
        for (CheckedTextView navItem : navItemList) {
            navItem.setChecked(navItem.getId() == id);
        }
    }

    /**
     * 主导航项单击事件
     */
    @OnClick({
            R.id.main_left_btn_my_class_table,
            R.id.main_left_btn_grades_query,
            R.id.main_left_btn_exam_plan,
            R.id.main_left_btn_senate_notice
    })
    public void onNavigationItemMainClick(CheckedTextView itemView) {
        switch (itemView.getId()) {
            case R.id.main_left_btn_my_class_table:
                drawerLayout.setDrawerListener(myClassTableDrawerListener);
                break;
            case R.id.main_left_btn_grades_query:
                drawerLayout.setDrawerListener(gradesQueryDrawerListener);
                break;
            case R.id.main_left_btn_exam_plan:
                drawerLayout.setDrawerListener(examPlanDrawerListener);
                break;
            case R.id.main_left_btn_senate_notice:
                drawerLayout.setDrawerListener(senateNoticeDrawerListener);
                break;
            default:
                drawerLayout.setDrawerListener(null);
                break;
        }
        checkNavigationItem(itemView.getId());
        drawerLayout.closeDrawers();
    }

    /**
     * 次要导航项单击事件
     */
    @OnClick({
            R.id.main_left_img_avatar,
            R.id.main_left_btn_notification,
            R.id.main_left_btn_setting,
            R.id.main_left_btn_about
    })
    public void onNavigationItemOtherClick(View itemView) {
        switch (itemView.getId()) {
            case R.id.main_left_img_avatar:
                drawerLayout.setDrawerListener(studentInfoDrawerListener);
                break;
            case R.id.main_left_btn_notification:
                drawerLayout.setDrawerListener(notificationDrawerListener);
                break;
            case R.id.main_left_btn_setting:
                drawerLayout.setDrawerListener(settingDrawerListener);
                break;
            case R.id.main_left_btn_about:
                drawerLayout.setDrawerListener(aboutDrawerListener);
                break;
            default:
                drawerLayout.setDrawerListener(null);
                break;
        }
        drawerLayout.closeDrawers();
    }

    /**
     * 全部的Drawer事件监听器，这么做的目的是让Drawer关闭之后再触发事件
     */
    private DrawerLayout.DrawerListener myClassTableDrawerListener = new DrawerLayout.SimpleDrawerListener() {

        @Override
        public void onDrawerClosed(View drawerView) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(fmClassTable) //*
                    .hide(fmGradesQuery)
                    .hide(fmExamPlan)
                    .hide(fmSenateNotice)
                    .commit();
            drawerLayout.setDrawerListener(null);
        }

    };

    private DrawerLayout.DrawerListener gradesQueryDrawerListener = new DrawerLayout.SimpleDrawerListener() {

        @Override
        public void onDrawerClosed(View drawerView) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(fmClassTable)
                    .show(fmGradesQuery) //*
                    .hide(fmExamPlan)
                    .hide(fmSenateNotice)
                    .commit();
            drawerLayout.setDrawerListener(null);
        }

    };

    private DrawerLayout.DrawerListener examPlanDrawerListener = new DrawerLayout.SimpleDrawerListener() {

        @Override
        public void onDrawerClosed(View drawerView) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(fmClassTable)
                    .hide(fmGradesQuery)
                    .show(fmExamPlan) //*
                    .hide(fmSenateNotice)
                    .commit();
            drawerLayout.setDrawerListener(null);
        }

    };

    private DrawerLayout.DrawerListener senateNoticeDrawerListener = new DrawerLayout.SimpleDrawerListener() {

        @Override
        public void onDrawerClosed(View drawerView) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(fmClassTable)
                    .hide(fmGradesQuery)
                    .hide(fmExamPlan)
                    .show(fmSenateNotice) //*
                    .commit();
            drawerLayout.setDrawerListener(null);
        }

    };

    private DrawerLayout.DrawerListener studentInfoDrawerListener = new DrawerLayout.SimpleDrawerListener() {

        @Override
        public void onDrawerClosed(View drawerView) {
            startActivity(new Intent(MainActivity.this, StudentInfoActivity.class));
            drawerLayout.setDrawerListener(null);
        }

    };

    private DrawerLayout.DrawerListener notificationDrawerListener = new DrawerLayout.SimpleDrawerListener() {

        @Override
        public void onDrawerClosed(View drawerView) {
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
            drawerLayout.setDrawerListener(null);
        }

    };

    private DrawerLayout.DrawerListener settingDrawerListener = new DrawerLayout.SimpleDrawerListener() {

        @Override
        public void onDrawerClosed(View drawerView) {
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
            drawerLayout.setDrawerListener(null);
        }

    };

    private DrawerLayout.DrawerListener aboutDrawerListener = new DrawerLayout.SimpleDrawerListener() {

        @Override
        public void onDrawerClosed(View drawerView) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            drawerLayout.setDrawerListener(null);
        }

    };

    /**
     * 返回键关闭导航
     */
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

    /**
     * 打开导航的监听器
     */
    private View.OnClickListener openNavigationClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

    };

    /**
     * Fragment模板
     */
    public static abstract class BaseFragment extends Fragment {

        protected void setOpenNavigationListen(Toolbar toolbar) {
            toolbar.setNavigationOnClickListener(((MainActivity) getActivity()).openNavigationClickListener);
        }

    }

}

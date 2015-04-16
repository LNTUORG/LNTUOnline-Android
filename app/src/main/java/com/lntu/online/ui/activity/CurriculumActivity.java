package com.lntu.online.ui.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.lntu.online.R;

import com.lntu.online.model.entityOld.Curriculum;
import com.lntu.online.model.http.HttpUtil;
import com.lntu.online.model.http.NormalAuthListener;
import com.lntu.online.model.http.RetryAuthListener;
import com.lntu.online.shared.SharedWrapper;
import com.lntu.online.shared.UserInfoShared;
import com.lntu.online.ui.adapter.CurriculumAdapter;
import com.lntu.online.ui.base.BaseActivity;

import org.apache.http.Header;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class CurriculumActivity extends BaseActivity {

    private final static String TAG = CurriculumActivity.class.getSimpleName();

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.curriculum_vp_root)
    protected ViewPager viewPager;

    private Time time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);
        ButterKnife.inject(this);

        //定义时间控件
        time = new Time();
        time.setToNow();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        //读取本地课表
        Curriculum cc = SharedWrapper.with(this, TAG).getObject(UserInfoShared.getSavedUserId(this), Curriculum.class);
        if (cc != null) {
            viewPager.setAdapter(new CurriculumAdapter(this, cc));
            viewPager.setCurrentItem(time.weekDay);
        } else {
            startNetwork();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.curriculum, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        case R.id.action_refresh:
            updateNetwork();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.curriculum_tv_date)
    public void onBtnDateClick() {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(CurriculumActivity.this, "new date:" + year + "-" + monthOfYear + "-" + dayOfMonth, Toast.LENGTH_LONG).show();
            }

        };
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void startNetwork() {
        HttpUtil.get(this, "curriculum/info", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Curriculum cc = Curriculum.dao.fromJson(responseString);
                    viewPager.setAdapter(new CurriculumAdapter(getContext(), cc));
                    viewPager.setCurrentItem(time.weekDay);
                    SharedWrapper.with(getContext(), TAG).setObject(UserInfoShared.getSavedUserId(getContext()), cc);
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
    
    private void updateNetwork() {
        HttpUtil.get(this, "curriculum/info", new NormalAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Curriculum cc = Curriculum.dao.fromJson(responseString);
                    viewPager.setAdapter(new CurriculumAdapter(getContext(), cc));
                    viewPager.setCurrentItem(time.weekDay);
                    SharedWrapper.with(getContext(), TAG).setObject(UserInfoShared.getSavedUserId(getContext()), cc);
                    Toast.makeText(getContext(), "数据更新成功", Toast.LENGTH_SHORT).show();
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    showErrorDialog("提示", msgs[0], msgs[1]);
                }
            }

        });
    }

}

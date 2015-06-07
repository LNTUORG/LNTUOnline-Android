package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lntu.online.R;

import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.WeekMode;
import org.lntu.online.model.gson.GsonWrapper;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.util.AppUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ClassTableCourseActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.class_table_course_tv_name)
    protected TextView tvName;

    @InjectView(R.id.class_table_course_tv_num)
    protected TextView tvNum;

    @InjectView(R.id.class_table_course_tv_credit)
    protected TextView tvCredit;

    @InjectView(R.id.class_table_course_tv_teacher)
    protected TextView tvTeacher;

    @InjectView(R.id.class_table_course_tv_time)
    protected TextView tvTime;

    @InjectView(R.id.class_table_course_tv_select_type)
    protected TextView tvSelectType;

    @InjectView(R.id.class_table_course_tv_test_mode)
    protected TextView tvTestMode;

    @InjectView(R.id.class_table_course_tv_exam_type)
    protected TextView tvExamType;

    @InjectView(R.id.class_table_course_tv_times_and_places)
    protected TextView tvTimesAndPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table_course);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ClassTable.Course course = GsonWrapper.gson.fromJson(getIntent().getStringExtra("course"), ClassTable.Course.class);
        tvName.setText(course.getName());
        tvNum.setText(course.getNum());
        tvCredit.setText(course.getCredit() + "");
        tvTeacher.setText(course.getTeacher());
        tvTime.setText(getIntent().getIntExtra("year", 1000) + "年 " + getIntent().getStringExtra("term") + "季学期");
        tvSelectType.setText(course.getSelectType());
        tvTestMode.setText(course.getTestMode());
        tvExamType.setText(course.getExamType());
        if (course.getTimesAndPlaces().size() > 0) {
            StringBuilder sbTimesAndPlaces = new StringBuilder();
            for (int n = 0; n < course.getTimesAndPlaces().size(); n++) {
                ClassTable.TimeAndPlace timeAndPlace = course.getTimesAndPlaces().get(n);
                sbTimesAndPlaces.append("· ").append(timeAndPlace.getStartWeek()).append("-").append(timeAndPlace.getEndWeek());
                if (timeAndPlace.getWeekMode() == WeekMode.ODD) {
                    sbTimesAndPlaces.append("单");
                } else if (timeAndPlace.getWeekMode() == WeekMode.EVEN) {
                    sbTimesAndPlaces.append("双");
                }
                sbTimesAndPlaces
                        .append("  ").append(timeAndPlace.getDayInWeek().value())
                        .append("  ").append("第").append(timeAndPlace.getStage()).append("大节")
                        .append("  ").append(timeAndPlace.getRoom());
                if (n != course.getTimesAndPlaces().size() - 1) {
                    sbTimesAndPlaces.append("\n");
                }
            }
            tvTimesAndPlaces.setText(sbTimesAndPlaces.toString());
        } else {
            tvTimesAndPlaces.setText("时间、地点未定");
        }
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

}

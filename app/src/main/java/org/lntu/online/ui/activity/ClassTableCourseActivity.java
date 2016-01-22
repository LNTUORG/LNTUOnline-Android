package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.WeekMode;
import org.lntu.online.ui.base.StatusBarActivity;
import org.lntu.online.ui.listener.NavigationFinishClickListener;
import org.lntu.online.util.gson.GsonWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClassTableCourseActivity extends StatusBarActivity {

    @Bind(R.id.class_table_course_toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.class_table_course_tv_name)
    protected TextView tvName;

    @Bind(R.id.class_table_course_tv_num)
    protected TextView tvNum;

    @Bind(R.id.class_table_course_tv_credit)
    protected TextView tvCredit;

    @Bind(R.id.class_table_course_tv_teacher)
    protected TextView tvTeacher;

    @Bind(R.id.class_table_course_tv_time)
    protected TextView tvTime;

    @Bind(R.id.class_table_course_tv_select_type)
    protected TextView tvSelectType;

    @Bind(R.id.class_table_course_tv_test_mode)
    protected TextView tvTestMode;

    @Bind(R.id.class_table_course_tv_exam_type)
    protected TextView tvExamType;

    @Bind(R.id.class_table_course_tv_times_and_places)
    protected TextView tvTimesAndPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table_course);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

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
                sbTimesAndPlaces.append("· ").append(timeAndPlace.getStartWeek()).append("-").append(timeAndPlace.getEndWeek()).append("周");
                if (timeAndPlace.getWeekMode() == WeekMode.ODD) {
                    sbTimesAndPlaces.append("（单周）");
                } else if (timeAndPlace.getWeekMode() == WeekMode.EVEN) {
                    sbTimesAndPlaces.append("（双周）");
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

}

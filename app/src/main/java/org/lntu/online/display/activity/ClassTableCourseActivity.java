package org.lntu.online.display.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.display.base.StatusBarActivity;
import org.lntu.online.display.listener.NavigationFinishClickListener;
import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.WeekMode;
import org.lntu.online.model.util.EntityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassTableCourseActivity extends StatusBarActivity {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.tv_name)
    protected TextView tvName;

    @BindView(R.id.tv_num)
    protected TextView tvNum;

    @BindView(R.id.tv_credit)
    protected TextView tvCredit;

    @BindView(R.id.tv_teacher)
    protected TextView tvTeacher;

    @BindView(R.id.tv_time)
    protected TextView tvTime;

    @BindView(R.id.tv_select_type)
    protected TextView tvSelectType;

    @BindView(R.id.tv_test_mode)
    protected TextView tvTestMode;

    @BindView(R.id.tv_exam_type)
    protected TextView tvExamType;

    @BindView(R.id.tv_times_and_places)
    protected TextView tvTimesAndPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table_course);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        ClassTable.Course course = EntityUtils.gson.fromJson(getIntent().getStringExtra("course"), ClassTable.Course.class);
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

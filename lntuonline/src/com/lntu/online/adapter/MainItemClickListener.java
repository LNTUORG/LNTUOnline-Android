package com.lntu.online.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.lntu.online.activity.CourseScoreActivity;
import com.lntu.online.activity.CurriculumActivity;
import com.lntu.online.activity.ExamPlanActivity;
import com.lntu.online.activity.MottoActivity;
import com.lntu.online.activity.NoticeActivity;
import com.lntu.online.activity.OneKeyActivity;
import com.lntu.online.activity.SkillTestActivity;
import com.lntu.online.activity.StudentInfoActivity;
import com.lntu.online.activity.UnpassCourseActivity;

public class MainItemClickListener implements OnItemClickListener {

    private static final Class<?>[] clzs = {
        StudentInfoActivity.class,
        CurriculumActivity.class,
        ExamPlanActivity.class,
        CourseScoreActivity.class,
        OneKeyActivity.class,
        UnpassCourseActivity.class,
        SkillTestActivity.class,
        NoticeActivity.class,
        MottoActivity.class
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.getContext().startActivity(new Intent(view.getContext(), clzs[position]));
    }

}
package com.lntu.online.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lntu.online.R;
import com.lntu.online.model.entityOld.Curriculum;

import java.util.ArrayList;
import java.util.List;


public class CurriculumAdapter extends PagerAdapter {

    public static final String[] weekdayNames = {
            "周日", "周一", "周二", "周三（今天）", "周四", "周五", "周六"
    };

    private List<View> views;

    public CurriculumAdapter(Context context, Curriculum cc) {
        LayoutInflater inflater = LayoutInflater.from(context);
        views = new ArrayList<View>();
        for (int n = 0; n < weekdayNames.length; n++) {
            View view = inflater.inflate(R.layout.activity_curriculum_item, null);
            //时间
            TextView tvTime1 = (TextView) view.findViewById(R.id.curriculum_tv_time1);
            TextView tvTime2 = (TextView) view.findViewById(R.id.curriculum_tv_time2);
            TextView tvTime3 = (TextView) view.findViewById(R.id.curriculum_tv_time3);
            TextView tvTime4 = (TextView) view.findViewById(R.id.curriculum_tv_time4);
            TextView tvTime5 = (TextView) view.findViewById(R.id.curriculum_tv_time5);
            tvTime1.setText("第1节 " + cc.getTimes().get(1) + "\n\n第2节 " + cc.getTimes().get(2));
            tvTime2.setText("第3节 " + cc.getTimes().get(3) + "\n\n第4节 " + cc.getTimes().get(4));
            tvTime3.setText("第5节 " + cc.getTimes().get(5) + "\n\n第6节 " + cc.getTimes().get(6));
            tvTime4.setText("第7节 " + cc.getTimes().get(7) + "\n\n第8节 " + cc.getTimes().get(8));
            tvTime5.setText("第9节 " + cc.getTimes().get(9) + "\n\n第10节 " + cc.getTimes().get(10));
            //课程
            TextView tvCourse1 = (TextView) view.findViewById(R.id.curriculum_tv_course1);
            TextView tvCourse2 = (TextView) view.findViewById(R.id.curriculum_tv_course2);
            TextView tvCourse3 = (TextView) view.findViewById(R.id.curriculum_tv_course3);
            TextView tvCourse4 = (TextView) view.findViewById(R.id.curriculum_tv_course4);
            TextView tvCourse5 = (TextView) view.findViewById(R.id.curriculum_tv_course5);
            int w = (n == 0 ? 7 : n);
            tvCourse1.setText(getClassName(cc.getCourses().get(w + "-1")));
            tvCourse2.setText(getClassName(cc.getCourses().get(w + "-2")));
            tvCourse3.setText(getClassName(cc.getCourses().get(w + "-3")));
            tvCourse4.setText(getClassName(cc.getCourses().get(w + "-4")));
            tvCourse5.setText(getClassName(cc.getCourses().get(w + "-5")));
            //填充布局
            views.add(view);
        }
    }

    private String getClassName(String old) {
        if (TextUtils.isEmpty(old)) {
            return "\n\n\n";
        } else {
            return old;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        return weekdayNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return weekdayNames[position];
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

}

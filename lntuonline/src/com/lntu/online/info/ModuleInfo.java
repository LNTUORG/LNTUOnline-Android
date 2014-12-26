package com.lntu.online.info;

import com.lntu.online.OneKeyActivity;
import com.lntu.online.CurriculumActivity;
import com.lntu.online.ExamPlanActivity;
import com.lntu.online.CourseScoreActivity;
import com.lntu.online.MottoActivity;
import com.lntu.online.NoticeActivity;
import com.lntu.online.R;
import com.lntu.online.SkillTestScoreActivity;
import com.lntu.online.StudentInfoActivity;
import com.lntu.online.UnpassCourseActivity;

public class ModuleInfo {

    private static final int[] icons = { 
        R.drawable.main_icon_profle,
        R.drawable.main_icon_clock2,
        R.drawable.main_icon_calendar,
        R.drawable.main_icon_right2,
        R.drawable.main_icon_gps,
        R.drawable.main_icon_stop,
        R.drawable.main_icon_trophy,
        R.drawable.main_icon_megaphone,
        R.drawable.main_icon_chat
    };

    private static final String[] titles = { 
        "学籍信息",
        "学期课表",
        "考试安排",
        "成绩查询",
        "一键评课",
        "挂科查询",
        "技能考试",
        "教务公告",
        "牢记校训"
    };

    private static final Class<?>[] clzs = {
        StudentInfoActivity.class,
        CurriculumActivity.class,
        ExamPlanActivity.class,
        CourseScoreActivity.class,
        OneKeyActivity.class,
        UnpassCourseActivity.class,
        SkillTestScoreActivity.class,
        NoticeActivity.class,
        MottoActivity.class
    };

    public static int getCount() {
        return titles.length;
    }

    public static int getIconResAt(int n) {
        return icons[n];
    }

    public static String getTitleAt(int n) {
        return titles[n];
    }

    public static Class<?> getClassAt(int n) {
        return clzs[n];
    }

}

package com.lntu.online.info;

import com.lntu.online.CommunityActivity;
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
        R.drawable.profle,
        R.drawable.clock2,
        R.drawable.calendar,
        R.drawable.right2,
        R.drawable.colorwheel,
        R.drawable.stop,
        R.drawable.trophy,
        R.drawable.megaphone,
        R.drawable.chat
    };

    private static final String[] titles = { 
        "ѧ����Ϣ",
        "ѧ�ڿα�",
        "���԰���",
        "�ɼ���ѯ",
        "���Ŵ�����",
        "�ҿƲ�ѯ",
        "���ܿ���",
        "���񹫸�",
        "�μ�Уѵ"
    };

    private static final Class<?>[] clzs = {
        StudentInfoActivity.class,
        CurriculumActivity.class,
        ExamPlanActivity.class,
        CourseScoreActivity.class,
        CommunityActivity.class,
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

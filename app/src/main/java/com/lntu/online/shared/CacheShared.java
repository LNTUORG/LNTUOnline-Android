package com.lntu.online.shared;


import android.content.Context;

import com.lntu.online.model.entityOld.Student;

public final class CacheShared {

    private CacheShared() {}

    private final static String TAG = CacheShared.class.getSimpleName();
    private final static String KEY_STUDENT = "student";
    private final static String KEY_AVA_OF_CREDIT = "avaOfCredit";

    private static String getSharedName(String userId) {
        return TAG + "-" + userId;
    }

    public static Student getStudent(Context context, String userId) {
        return SharedWrapper.with(context, getSharedName(userId)).getObject(KEY_STUDENT, Student.class);
    }

    public static void setStudent(Context context, Student student) {
        if (student != null) {
            SharedWrapper.with(context, getSharedName(student.getUserId())).setObject(KEY_STUDENT, student);
        }
    }

    public static String getAvaOfCredit(Context context, String userId) {
        return SharedWrapper.with(context, getSharedName(userId)).getString(KEY_AVA_OF_CREDIT, "暂时无平均学分绩信息。");
    }

    public static void setAvaOfCredit(Context context, String userId, String info) {
        SharedWrapper.with(context, getSharedName(userId)).setString(KEY_AVA_OF_CREDIT, info);
    }

}

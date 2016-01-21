package org.lntu.online.storage;

import android.content.Context;

import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.Student;

public final class CacheShared {

    private CacheShared() {}

    private static final String TAG = "CacheShared";

    private static String getSharedName(Context context) {
        return TAG + "@" + LoginShared.getUserId(context);
    }

    private static final String KEY_STUDENT = "student";
    private static final String KEY_CLASS_TABLE = "classTable:"; // 这里年级和学期要作为后缀

    public static Student getStudent(Context context) {
        return SharedWrapper.with(context, getSharedName(context)).getObject(KEY_STUDENT, Student.class);
    }

    public static void setStudent(Context context, Student student) {
        SharedWrapper.with(context, getSharedName(context)).setObject(KEY_STUDENT, student);
    }

    public static ClassTable getClassTable(Context context, int year, String term) {
        return SharedWrapper.with(context, getSharedName(context)).getObject(KEY_CLASS_TABLE + year + term, ClassTable.class);
    }

    public static void setClassTable(Context context, ClassTable classTable) {
        SharedWrapper.with(context, getSharedName(context)).setObject(KEY_CLASS_TABLE + classTable.getYear() + classTable.getTerm(), classTable);
    }

}

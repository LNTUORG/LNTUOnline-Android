package org.lntu.online.storage;

import android.content.Context;

import org.joda.time.DateTime;
import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.LoginInfo;
import org.lntu.online.model.entity.Student;
import org.lntu.online.model.entity.UserType;

import java.util.Date;

public final class LoginShared {

    private LoginShared() {}

    private static final String TAG = LoginShared.class.getSimpleName();

    //============
    // 用户登录状态
    //============

    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_TYPE = "userType";
    private static final String KEY_LOGIN_TOKEN = "loginToken";
    private static final String KEY_EXPIRES_AT = "expiresAt";
    private static final String KEY_HOLD_ONLINE = "holdOnline";

    public static void login(Context context, LoginInfo info, boolean isHoldOnline) {
        SharedWrapper sharedWrapper = SharedWrapper.with(context, TAG);
        sharedWrapper.setString(KEY_USER_ID, info.getUserId());
        sharedWrapper.setString(KEY_USER_TYPE, info.getUserType().name());
        sharedWrapper.setString(KEY_LOGIN_TOKEN, info.getLoginToken());
        sharedWrapper.setString(KEY_EXPIRES_AT, new DateTime(info.getExpiresAt()).toString());
        sharedWrapper.setBoolean(KEY_HOLD_ONLINE, isHoldOnline); // 是否保持在线状态
    }

    public static void logout(Context context) {
        SharedWrapper.with(context, TAG).clear();
    }

    public static String getUserId(Context context) {
        return SharedWrapper.with(context, TAG).getString(KEY_USER_ID, null);
    }

    public static UserType getUserType(Context context) {
        return UserType.valueOf(SharedWrapper.with(context, TAG).getString(KEY_USER_TYPE, UserType.STUDENT.name()));
    }

    public static String getLoginToken(Context context) {
        return SharedWrapper.with(context, TAG).getString(KEY_LOGIN_TOKEN, null);
    }

    public static Date getExpiresAt(Context context) {
        return new DateTime(SharedWrapper.with(context, TAG).getString(KEY_EXPIRES_AT, "1900-1-1")).toDate();
    }

    public static boolean isHoldOnline(Context context) {
        return SharedWrapper.with(context, TAG).getBoolean(KEY_HOLD_ONLINE, false);
    }

    //=========
    // 缓存数据
    //=========

    private static final String KEY_STUDENT = "student";
    private static final String KEY_CLASS_TABLE = "classTable-"; // 这里年级和学期要作为后缀

    public static Student getStudent(Context context) {
        return SharedWrapper.with(context, TAG).getObject(KEY_STUDENT, Student.class);
    }

    public static void setStudent(Context context, Student student) {
        SharedWrapper.with(context, TAG).setObject(KEY_STUDENT, student);
    }

    public static ClassTable getClassTable(Context context, int year, String term) {
        return SharedWrapper.with(context, TAG).getObject(KEY_CLASS_TABLE + year + term, ClassTable.class);
    }

    public static void setClassTable(Context context, ClassTable classTable) {
        SharedWrapper.with(context, TAG).setObject(KEY_CLASS_TABLE + classTable.getYear() + classTable.getTerm(), classTable);
    }

}

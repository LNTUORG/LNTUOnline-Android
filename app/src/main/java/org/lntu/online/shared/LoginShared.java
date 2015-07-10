package org.lntu.online.shared;

import android.content.Context;

import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.LoginInfo;
import org.lntu.online.model.entity.Student;
import org.lntu.online.model.entity.UserType;

import org.joda.time.DateTime;
import org.lntu.online.model.local.NavMenuHeaderBackgroundType;

import java.util.Date;

public final class LoginShared {

    private LoginShared() {}

    private static final String TAG = LoginShared.class.getSimpleName();

    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_TYPE = "userType";
    private static final String KEY_LOGIN_TOKEN = "loginToken";
    private static final String KEY_EXPIRES_AT = "expiresAt";
    private static final String KEY_HOLD_ONLINE = "holdOnline";

    private static final String KEY_STUDENT = "student";
    private static final String KEY_CLASS_TABLE = "classTable-"; // 这里年级和学期要作为后缀

    private static final String SETTING_NAV_MENU_HEADER_BACKGROUND = "settingNavMenuHeaderBackground"; // 导航顶部图片
    private static final String SETTING_ENABLE_NOTIFICATION = "settingEnableNotification"; // 开启系统通知

    public static void login(Context context, LoginInfo info, boolean isHoldOnline) {
        SharedWrapper.with(context, TAG).setString(KEY_USER_ID, info.getUserId());
        SharedWrapper.with(context, TAG).setString(KEY_USER_TYPE, info.getUserType().name());
        SharedWrapper.with(context, TAG).setString(KEY_LOGIN_TOKEN, info.getLoginToken());
        SharedWrapper.with(context, TAG).setString(KEY_EXPIRES_AT, new DateTime(info.getExpiresAt()).toString());
        SharedWrapper.with(context, TAG).setBoolean(KEY_HOLD_ONLINE, isHoldOnline); // 是否保持在线状态
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

    public static NavMenuHeaderBackgroundType getNavMenuHeaderBackgroundType(Context context) {
        try {
            return NavMenuHeaderBackgroundType.valueOf(SharedWrapper.with(context, TAG).getString(SETTING_NAV_MENU_HEADER_BACKGROUND, null));
        } catch (Exception e) {
            return NavMenuHeaderBackgroundType.color;
        }
    }

    public static void setNavMenuHeaderBackgroundType(Context context, NavMenuHeaderBackgroundType type) {
        SharedWrapper.with(context, TAG).setString(SETTING_NAV_MENU_HEADER_BACKGROUND, type == null ? NavMenuHeaderBackgroundType.color.name() : type.name());
    }

    public static boolean isEnableNotification(Context context) {
        return SharedWrapper.with(context, TAG).getBoolean(SETTING_ENABLE_NOTIFICATION, true);
    }

    public static void setEnableNotification(Context context, boolean enable) {
        SharedWrapper.with(context, TAG).setBoolean(SETTING_ENABLE_NOTIFICATION, enable);
    }


}

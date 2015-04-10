package com.lntu.online.shared;

import android.content.Context;


public final class UserInfoShared {

    private UserInfoShared() {}

    private final static String TAG = UserInfoShared.class.getSimpleName();
    private final static String KEY_AUTO_LOGIN = "auto_login";
    private final static String KEY_USER_ID = "user_id";
    private final static String KEY_PWD = "pwd";

    public static boolean isAutoLogin(Context context) {
        return SharedWrapper.with(context, TAG).getBoolean(KEY_AUTO_LOGIN, false);
    }

    public static void setAutoLogin(Context context, boolean b) {
        SharedWrapper.with(context, TAG).setBoolean(KEY_AUTO_LOGIN, b);
    }

    public static String getSavedUserId(Context context) {
        return SharedWrapper.with(context, TAG).getString(KEY_USER_ID, "");
    }

    public static void setSavedUserId(Context context, String userId) {
        SharedWrapper.with(context, TAG).setString(KEY_USER_ID, userId);
    }

    public static String getSavedPwd(Context context) {
        return SharedWrapper.with(context, TAG).getString(KEY_PWD, "");
    }

    public static void setSavedPwd(Context context, String pwd) {
        SharedWrapper.with(context, TAG).setString(KEY_PWD, pwd);
    }

    public static void logout(Context context) {
        SharedWrapper.with(context, TAG).clear();
    }

}

package com.lntu.online.shared;

import android.content.Context;

import com.lntu.online.model.entity.LoginInfo;
import com.lntu.online.model.entity.UserType;

import org.joda.time.DateTime;

import java.util.Date;

public final class LoginShared {

    private LoginShared() {}

    private final static String TAG = LoginShared.class.getSimpleName();

    private final static String KEY_USER_ID = "userId";
    private final static String KEY_USER_TYPE = "userType";
    private final static String KEY_LOGIN_TOKEN = "loginToken";
    private final static String KEY_EXPIRES_AT = "expiresAt";

    public static void login(Context context, LoginInfo info) {
        SharedWrapper.with(context, TAG).setString(KEY_USER_ID, info.getUserId());
        SharedWrapper.with(context, TAG).setString(KEY_USER_TYPE, info.getUserType().name());
        SharedWrapper.with(context, TAG).setString(KEY_LOGIN_TOKEN, info.getLoginToken());
        SharedWrapper.with(context, TAG).setString(KEY_EXPIRES_AT, new DateTime(info.getExpiresAt()).toString());
    }

    public static void logout(Context context) {
        SharedWrapper.with(context, TAG).clear();
    }

    public static String getUserId(Context context) {
        return SharedWrapper.with(context, TAG).getString(KEY_USER_ID, null);
    }

    public static UserType getUserType(Context context) {
        return UserType.valueOf(SharedWrapper.with(context, TAG).getString(KEY_USER_ID, UserType.STUDENT.name()));
    }

    public static String getLoginToken(Context context) {
        return SharedWrapper.with(context, TAG).getString(KEY_LOGIN_TOKEN, null);
    }

    public static Date getExpiresAt(Context context) {
        return new DateTime(SharedWrapper.with(context, TAG).getString(KEY_EXPIRES_AT, "1900-1-1")).toDate();
    }

}

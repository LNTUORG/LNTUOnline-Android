package com.lntu.online.info;

import com.lntu.online.app.App;
import com.lntu.online.util.Des3Util;
import com.lntu.online.util.Md5Util;

import android.content.Context;
import android.content.SharedPreferences;

public class UserInfo {

    private static SharedPreferences getSharedPreferences() {
        return App.getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
    }

    public static boolean isAutoLogin() {
        return getSharedPreferences().getBoolean("auto_login", false);
    }

    public static void setAutoLogin(boolean flag) {
        getSharedPreferences().edit().putBoolean("auto_login", flag).commit();
    }

    public static void setSavedUserId(String userId) {
        try {
            getSharedPreferences().edit().putString("user_id", Des3Util.encode(Md5Util.getDigest(SecretKey.SP_KEY), userId)).commit();
        } catch(Exception e) {
            getSharedPreferences().edit().putString("user_id", "").commit();
        }
    }

    public static void setSavedPwd(String pwd) {
        try {
            getSharedPreferences().edit().putString("pwd", Des3Util.encode(Md5Util.getDigest(SecretKey.SP_KEY), pwd)).commit();
        } catch(Exception e) {
            getSharedPreferences().edit().putString("pwd", "").commit();
        }
    }

    public static String getSavedUserId() {
        try {
            return Des3Util.decode(Md5Util.getDigest(SecretKey.SP_KEY), getSharedPreferences().getString("user_id", ""));
        } catch(Exception e) {
            return "";
        }
    }

    public static String getSavedPwd() {
        try {
            return Des3Util.decode(Md5Util.getDigest(SecretKey.SP_KEY), getSharedPreferences().getString("pwd", ""));
        } catch(Exception e) {
            return "";
        }
    }

}

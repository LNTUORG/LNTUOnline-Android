package com.lntu.online.info;

import android.content.Context;
import android.content.SharedPreferences;

import com.lntu.online.app.AppController;
import com.takwolf.util.crypto.DES3;
import com.takwolf.util.digest.MD5;

public class UserInfo {

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
    }

    public static boolean isAutoLogin(Context context) {
        return getSharedPreferences(context).getBoolean("auto_login", false);
    }

    public static void setAutoLogin(Context context, boolean flag) {
        getSharedPreferences(context).edit().putBoolean("auto_login", flag).commit();
    }

    public static void setSavedUserId(Context context, String userId) {
        try {
            getSharedPreferences(context).edit().putString("user_id", DES3.encrypt(MD5.getMessageDigest(SecretKey.SP_KEY), userId)).commit();
        } catch(Exception e) {
            getSharedPreferences(context).edit().putString("user_id", "").commit();
        }
    }

    public static void setSavedPwd(Context context, String pwd) {
        try {
            getSharedPreferences(context).edit().putString("pwd", DES3.encrypt(MD5.getMessageDigest(SecretKey.SP_KEY), pwd)).commit();
        } catch(Exception e) {
            getSharedPreferences(context).edit().putString("pwd", "").commit();
        }
    }

    public static String getSavedUserId(Context context) {
        try {
            return DES3.decrypt(MD5.getMessageDigest(SecretKey.SP_KEY), getSharedPreferences(context).getString("user_id", ""));
        } catch(Exception e) {
            return "";
        }
    }

    public static String getSavedPwd(Context context) {
        try {
            return DES3.decrypt(MD5.getMessageDigest(SecretKey.SP_KEY), getSharedPreferences(context).getString("pwd", ""));
        } catch(Exception e) {
            return "";
        }
    }

    public static void logout(Context context) {
        getSharedPreferences(context).edit().clear().commit();
    }

}

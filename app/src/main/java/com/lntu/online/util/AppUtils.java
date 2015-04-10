package com.lntu.online.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.takwolf.util.digest.MD5;


public final class AppUtils {

    private AppUtils() {}

    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSignatureMD5(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            return MD5.getMessageDigest(info.signatures[0].toByteArray());
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

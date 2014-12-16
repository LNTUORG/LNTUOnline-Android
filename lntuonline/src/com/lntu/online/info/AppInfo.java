package com.lntu.online.info;

import com.lntu.online.app.App;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppInfo {

    public static String getVersionName() {
        PackageManager manager = App.getContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(App.getContext().getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            return "-ver not found-";
        }
    }

    public static int getVersionCode() {
        PackageManager manager = App.getContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(App.getContext().getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            return -1;
        }
    }

}

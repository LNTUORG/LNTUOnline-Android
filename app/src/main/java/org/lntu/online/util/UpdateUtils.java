package org.lntu.online.util;

import android.content.Context;

import com.umeng.update.UmengUpdateAgent;

public final class UpdateUtils {

    private UpdateUtils() {}

    public static void update(Context context) {
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(context);
    }

    public static void forceUpdate(Context context) {
        UmengUpdateAgent.forceUpdate(context);
    }

    public static void silentUpdate(Context context) {
        UmengUpdateAgent.silentUpdate(context);
    }

}

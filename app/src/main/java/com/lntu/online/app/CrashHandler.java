package com.lntu.online.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lntu.online.ui.activity.CrashShowActivity;

import java.lang.Thread.UncaughtExceptionHandler;


public final class CrashHandler implements UncaughtExceptionHandler {

    private volatile static CrashHandler singleton;

    private static CrashHandler getInstance() {
        if (singleton == null) {
            synchronized (CrashHandler.class) {
                if (singleton == null) {
                    singleton = new CrashHandler();
                }
            }
        }
        return singleton;
    }

    public static void active(Context context) {
        CrashHandler handler = getInstance();
        handler.context = context;
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    private Context context;

    private CrashHandler() {}

    @Override
    public void uncaughtException(Thread thread, final Throwable e) {
        //启动ErrorShowActivity
        Intent intent = new Intent(context, CrashShowActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable("e", e);
        intent.putExtras(bundle);
        context.startActivity(intent);

        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}

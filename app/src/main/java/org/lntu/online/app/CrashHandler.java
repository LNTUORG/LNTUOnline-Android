package org.lntu.online.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.lntu.online.ui.activity.CrashLogActivity;

import java.lang.Thread.UncaughtExceptionHandler;

public final class CrashHandler implements UncaughtExceptionHandler {

    private volatile static CrashHandler singleton;

    private static CrashHandler getInstance(Context context) {
        if (singleton == null) {
            synchronized (CrashHandler.class) {
                if (singleton == null) {
                    singleton = new CrashHandler(context);
                }
            }
        }
        return singleton;
    }

    public static void active(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(getInstance(context));
    }

    private Context context;

    private CrashHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable e) {
        Intent intent = new Intent(context, CrashLogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable("e", e);
        intent.putExtras(bundle);
        context.startActivity(intent);
        System.exit(1);
    }

}

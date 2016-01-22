package org.lntu.online.app;

import android.app.Application;
import android.content.Context;

import org.lntu.online.BuildConfig;

public class AppController extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        if (!BuildConfig.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler(new AppExceptionHandler(this));
        }
    }

}

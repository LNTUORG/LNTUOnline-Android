package com.lntu.online.app;

import android.app.Application;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.active(this);
    }

}

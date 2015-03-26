package com.lntu.online.app;

import android.app.Application;
import android.content.Context;

public class AppController extends Application {

    private static AppController context;

    @Override
    public void onCreate() {
        super.onCreate();
        //注入全局上下文
        context = this;
        //注册全局异常捕获器
        CrashHandler.active(this);
    }

    public static Context getContext() {
        return context;
    }

    public static Application getApplication() {
        return context;
    }

}

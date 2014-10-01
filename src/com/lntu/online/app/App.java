package com.lntu.online.app;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        //ע��ȫ��������
        context = this;
        //ע��ȫ���쳣������
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.active(this);
    }

    public static Context getContext() {
        return context;
    }
    
    public static Application getApplication() {
    	return context;
    }

}

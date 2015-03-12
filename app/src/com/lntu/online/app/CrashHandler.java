package com.lntu.online.app;

import java.lang.Thread.UncaughtExceptionHandler;

import com.lntu.online.activity.CrashShowActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CrashHandler implements UncaughtExceptionHandler {

    private static CrashHandler instance; //当前异常处理器-单例模式
    @SuppressWarnings("unused")
    private UncaughtExceptionHandler defaultHandler; //系统默认未捕获异常处理器
    private Context context; //应用上下文

    /**
     * 隐藏构造器
     */
    private CrashHandler() {}

    /**
     * 获取异常处理器单例
     */
    public static synchronized CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    /**
     * 激活处理器
     * @param context
     */
    public void active(Context context) {
        this.context = context; 
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 未被捕获的异常，会调用该方法处理
     */
    @Override
    public void uncaughtException(Thread thread, final Throwable e) {
        //系统默认处理异常
        //defaultHandler.uncaughtException(thread, e);

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

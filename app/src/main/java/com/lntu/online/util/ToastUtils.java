package com.lntu.online.util;

import android.content.Context;
import android.widget.Toast;

public final class ToastUtils {

    private volatile static ToastUtils singleton;

    public static ToastUtils with(Context context) {
        if (singleton == null) {
            synchronized (ToastUtils.class) {
                if (singleton == null) {
                    singleton = new ToastUtils(context);
                }
            }
        }
        return singleton;
    }

    private final Context context;

    private ToastUtils(Context context) {
        this.context = context;
    }

    public void show(CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void show(int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

}

/*
 * Copyright (C) 2014-2016 ColoShine Inc. All Rights Reserved.
 */

package com.lntu.online.util;

import android.content.Context;
import android.os.Environment;


public final class FileUtils {

    private FileUtils() {}

    public static String getDiskCacheDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

}

package org.lntu.online.util;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;

public final class FormatUtils {

    private FormatUtils() {}

    public static String getTimeFormat(@Nullable DateTime dateTime) {
        if (dateTime == null) {
            return null;
        } else {
            return dateTime.toString("yyyy-MM-dd");
        }
    }

}

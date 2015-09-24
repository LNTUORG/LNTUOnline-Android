package org.lntu.online.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class FormatUtils {

    private FormatUtils() {}

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    public static String getTimeFormat(Date date) {
        if (date == null) {
            return null;
        } else {
            return dateFormat.format(date);
        }
    }

}

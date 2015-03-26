package com.lntu.online.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    /**
     * Format 1991-5-13
     */
    public static Date getTime(String time) {
        if (time == null || time.equals("")) {
            return null;
        }
        String[] arr = time.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) - 1, Integer.parseInt(arr[2]), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getExpireTime(Date updateTime, int timeOfValidity) {
        if (updateTime == null) {
            return null;
        } else {
            Calendar validityTime = Calendar.getInstance();
            validityTime.setTime(updateTime);
            validityTime.add(Calendar.SECOND, timeOfValidity);
            validityTime.set(Calendar.MILLISECOND, 0);
            return validityTime.getTime();
        }
    }

    public static boolean isOutDate(Date updateTime, int timeOfValidity) {
        if (updateTime == null) {
            return true;
        } else {
            Calendar nowTime = Calendar.getInstance();
            Calendar validityTime = Calendar.getInstance();
            validityTime.setTime(updateTime);
            validityTime.add(Calendar.SECOND, timeOfValidity);
            return nowTime.after(validityTime);
        }
    }

}

package com.ycj.student.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class DateUtils {

    public static String getCurrentFormatDate() {
        Date date = getCurrentDate();
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
    }
    public static Date getCurrentDate() {
        // 指定中国时区
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        return new Date();
    }

}

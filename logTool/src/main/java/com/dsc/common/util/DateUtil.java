package com.dsc.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    public final static String DATEFORMAT = "yyyy-MM-dd";
    public final static String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String TIME_PATTERN = "[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}[ ][0-9]{1,2}[:][0-9]{1,2}[:][0-9]{1,2}";

    public static String getCurrentDateTime() {
        return format(new Date(), DATEFORMAT);
    }

    public static String formatDate2Date(Date date) {
        return format(date, DATEFORMAT);
    }

    public static String formatDate2Time(Date date) {
        return format(date, TIMEFORMAT);
    }

    public static Date formatStr2Date(String date) {
        return format(date, DATEFORMAT);
    }

    public static Date formatStr2Time(String date) {
        return format(date, TIMEFORMAT);
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date format(String strDate, String format) {
        if (StringUtil.isNotEmpty(strDate)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(strDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 功能：通过毫秒数获取时间
    public static String getTimeByMills(long time) {
        SimpleDateFormat sdf= new SimpleDateFormat(TIMEFORMAT);
        java.util.Date date = new Date(time);
        return sdf.format(date);
    }

    public static String getLineFirstTime(String str){
        Pattern pattern = Pattern.compile(TIME_PATTERN);
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
            return matcher.group();
        }
        return null;
    }

    public static Long getTimeMills(String time) {
        Date date = formatStr2Time(time);
        if (date != null) {
            return date.getTime();
        }
        return 0L;
    }
}
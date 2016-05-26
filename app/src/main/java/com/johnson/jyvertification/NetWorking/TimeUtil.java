package com.johnson.jyvertification.NetWorking;

import android.annotation.SuppressLint;
import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SuppressLint("SimpleDateFormat")
public class TimeUtil {

    public static String ddateToString(Date date, String type) {
        String str = null;
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        if (type.equals("SHORT")) {
            format = DateFormat.getDateInstance(DateFormat.SHORT);
            str = format.format(date);
        } else if (type.equals("MEDIUM")) {
            format = DateFormat.getDateInstance(DateFormat.MEDIUM);
            str = format.format(date);
        } else if (type.equals("FULL")) {
            format = DateFormat.getDateInstance(DateFormat.FULL);
            str = format.format(date);
        }
        return str;
    }

    public static String dateToStringSimple(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String StringToDateString(String time) {
        DateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(stringToDate(time));

    }

    public static Date stringToDate(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {

            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date stringToDateNoS(String str) {
        // ParsePosition position = new ParsePosition(0);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        // date = format.parse(str, position);
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }

    public static String getStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        return format.format(new Date(System.currentTimeMillis()));
    }

    @SuppressWarnings("static-access")
    public static String getTYdate(int type) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, type);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);
    }

    public static String getPreDateByDate(String str) {
        Date date = stringToDateNoS(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String updateRefresh() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return formatter.format(new Date());

    }

    public static boolean isOverOneHour(long time) {
        long intervalTime = System.currentTimeMillis() - time;
        if (intervalTime > 600000) {
            return true;
        }
        return false;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentUtcTime() {
        Date l_datetime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSS");
        TimeZone l_timezone = TimeZone.getTimeZone("GMT-8:00");
        formatter.setTimeZone(l_timezone);
        String l_utc_date = formatter.format(l_datetime);
        return l_utc_date;
    }

    public static int compare_date(String DATE1, String DATE2) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String dateStringtoString(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(stringToDate(time));
    }

    public static String utc2Local(String utcTime) {
        SimpleDateFormat utcFormater = new SimpleDateFormat("yyyyMMddHHmmss");
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm");
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }

    public static boolean campareinside(String s, String e) {
        Date sdate = stringToDateNoS(s);
        Date edate = stringToDateNoS(e);
        long times = 24 * 60 * 60 * 31 * 1000L;

        if ((edate.getTime() - sdate.getTime()) > times) {
            return false;
        }
        return true;
    }

    public static Integer StringToTimestamp(String time) {

        int times = 0;
        try {
            times = (int) ((Timestamp.valueOf(time).getTime()) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (times == 0) {
            System.out.println("String转10位时间戳失败");
        }
        Log.i("MyLog", "转换的完成的时间是  " + times);
        return times;

    }

//	public static Integer getTimestamp() {
//		Log.i("MyLog","转换的时间是  "+getStartTime());
//		return StringToTimestamp(getStartTime());//只能转换 的是yyyy-MM-dd HH:这种类型的
//
//	}

}

package utils.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author pan.sun
 * @Description 日期操作类
 * @Date 11:18 2021/12/10
 * @Param No such property: code for class: Script1
 * @return
 **/
@Slf4j
public class DateUtil {
    public final static String PATTERN_1 = "yyyy-MM-dd HH:mm:ss";
    public final static String PATTERN_2 = "yyyy-MM-dd";
    public final static String PATTERN_3 = "yyyyMMddHHmmss";
    public final static String PATTERN_4 = "yyyy/M/d H:m:s";
    public final static String PATTERN_5 = "yyyy-MM";
    public final static String PATTERN_6 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public final static String PATTERN_7 = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String format(Date date, String pattern) {
        if (date == null || StringUtils.isBlank(pattern)) {
            return null;
        }
        DateFormat df = null;
        try {
            df = new SimpleDateFormat(pattern);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getTimeZoneTime(String timeZone){
        Date time = null;
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZone), Locale.CHINESE);
            Calendar day = Calendar.getInstance();
            day.set(Calendar.YEAR, cal.get(Calendar.YEAR));
            day.set(Calendar.MONTH, cal.get(Calendar.MONTH));
            day.set(Calendar.DATE, cal.get(Calendar.DATE));
            day.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
            day.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
            day.set(Calendar.SECOND, cal.get(Calendar.SECOND));
            time = day.getTime();
        } catch (Exception e) {
            log.info("获取"+timeZone+"时间error !");
            e.printStackTrace();
            time = null;
        }
        return time;
    }
    public static Date parse(String dateStr, String pattern) {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(pattern)) {
            return null;
        }
        DateFormat df;
        try {
            df = new SimpleDateFormat(pattern);
            return df.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parse4(String dateStr, String pattern) {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(pattern)) {
            return null;
        }
        DateFormat df;
        try {
            df = new SimpleDateFormat(pattern);
            return df.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parse2(Date date, String pattern) {
        DateFormat df;
        try {
            df = new SimpleDateFormat(pattern);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parse3(Date date, String pattern) {
        DateFormat df;
        try {
            df = new SimpleDateFormat(pattern);
            df.setTimeZone(TimeZone.getTimeZone("CCT"));//设置时区为东八区
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseByPattern1(String dateStr) {
        return parse(dateStr, PATTERN_1);
    }

    public static Date parseByPattern2(String dateStr) {
        return parse(dateStr, PATTERN_2);
    }

    public static Date parseByPattern5(String dateStr) {
        return parse(dateStr, PATTERN_5);
    }

    public static String parseByPattern6(Date date) {
        return parse2(date, PATTERN_6);
    }

    public static String parseByPattern7(Date date) {
        return parse2(date, PATTERN_1);
    }
    public static String parseByTimeZone7(Date date) {
        return parse3(date, PATTERN_2);
    }

    public static Date parseByTimeZone8(Date date) {
        String datetime = parse3(date, PATTERN_1);
        Date d = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_1);
            d = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String formatByPattern2(Date date) {
        return format(date, PATTERN_2);
    }

    public static Date parseByPattern4(String dateStr) {
        return parse(dateStr, PATTERN_4);
    }

    public static String formatByPattern1(Date date) {
        return format(date, PATTERN_1);
    }

    public static String formatByPattern3(Date date) {
        return format(date, PATTERN_3);
    }

    public static String formatByPattern5(Date date) {
        return format(date, PATTERN_5);
    }

    public static Date adjustDate(Date date, long mills) {
        long time = date.getTime();
        Date newDate = new Date();
        newDate.setTime(time + mills);
        return newDate;
    }

    public static Date curDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();
    }

    public static Date preDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return calendar.getTime();
    }


    public static Date endOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        return calendar.getTime();
    }

    public static Date startOfNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
    }

    public static Date startTimeOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar.getTime();
    }

    public static Date startDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }


    public static Date startDayOfNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        return calendar.getTime();
    }


    public static Date endDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return calendar.getTime();
    }

    public static Date endDayOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return calendar.getTime();
    }


    public static Date endTimeOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return calendar.getTime();
    }

    public static int compareByPattern1(String a, String b) {
        return compare(parseByPattern1(a), parseByPattern1(b));
    }

    public static int compare(Date a, Date b) {
        if (a == null || b == null) {
            return 0;
        }
        return a.compareTo(b);
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }


    /**
     * 获取昨天开始时间
     *
     * @return
     */
    public static Long getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTimeInMillis();
    }

    /**
     * 获取昨天的结束时间
     *
     * @return
     */
    public static Long getEndDayOfYesterDay() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTimeInMillis();
    }

    /**
     * main.
     *
     * @param args
     */
    public static void main(String[] args) {
    }
}

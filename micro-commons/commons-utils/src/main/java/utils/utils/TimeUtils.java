package utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-03-28 14:09 星期二
 * @ClassName com.zyc.commons.utils.utils.TimeUtils
 * @Description: 时间工具类
 */
@SuppressWarnings("all")
public class TimeUtils {

    private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    public static String date2Str(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-01-06 11:01:12
     * @Description 获取耗费时间
     * @Return java.lang.String
     */
    public static String getTimeResuming(Date startDate, Date endDate) {
        StringBuffer systemOutLog = new StringBuffer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        long between = 0;
        try {
            // 得到两者的毫秒数
            between = (endDate.getTime() - startDate.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        // 天
        if (day > 0) {
            systemOutLog.append(day);
            systemOutLog.append("天");
        }
        // 小时
        if (hour > 0) {
            systemOutLog.append(hour);
            systemOutLog.append("小时");
        }
        // 分钟
        if (min > 0) {
            systemOutLog.append(min);
            systemOutLog.append("分");
        }
        // 秒
        if (s > 0) {
            systemOutLog.append(s);
            systemOutLog.append("秒");
        }
        // 毫秒
        if (ms > 0) {
            systemOutLog.append(ms);
            systemOutLog.append("毫秒");
        } else {
            systemOutLog.append("0");
            systemOutLog.append("毫秒");
        }
        return systemOutLog.toString();
    }

    /**
     * 获取当天开始时间
     *
     * @return xxxx年xx月xx日 00:00:00
     */
    public static String getThisDayStartTime() {
        String now = cn.hutool.core.date.DateUtil.today();
        return now + " 00:00:00";
    }

    /**
     * 获取当天结束时间
     *
     * @return xxxx年xx月xx日 23:59:59
     */
    public static String getThisDayEndTime() {
        String now = cn.hutool.core.date.DateUtil.today();
        return now + " 23:59:59";
    }

    /**
     * 获取本周的第一天或最后一天
     *
     * @param today
     * @param isFirst
     * @return
     */
    public static String getStartOrEndDayOfWeek(LocalDate today, Boolean isFirst) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int dayOfWeek = now.getDayOfWeek().getValue();
        return isFirst ? now.minusDays(dayOfWeek - 1).with(LocalTime.MIN).toString().substring(0, 10) : now.plusDays(7 - dayOfWeek).with(LocalTime.MAX).toString().substring(0, 10);
    }

    /**
     * 获取本月的第一天或最后一天
     * @param today
     * @param isFirst
     * @return
     */
    public static String getStartOrEndDayOfMonth(LocalDate today, Boolean isFirst){
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        Month month = today.getMonth();
        int length = month.length(today.isLeapYear());
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), month, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), month, length);
        }
        return resDate.toString();
    }


    /**
     * 数据库查询出来的统计数据有时候日期是不连续的.
     * 但是前端展示要补全缺失的日期.
     * 此方法返回一个给定日期期间的所有日期字符串列表.
     * 具体在业务逻辑中去判断补全.
     *
     * @param strat 开始日期
     * @param end   结束日期
     * @return list
     */
    public static List<String> completionDate(Date strat, Date end) {
        LocalDateTime startDate = LocalDateTime.ofInstant(strat.toInstant(), ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());
        // 日期格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> dateList = new ArrayList<>();
        // 遍历给定的日期期间的每一天
        for (int i = 0; !Duration.between(startDate.plusDays(i), endDate).isNegative(); i++) {
            // 添加日期
            dateList.add(startDate.plusDays(i).format(formatter));
        }
        return dateList;
    }



    public static void main(String[] args) {
        try {
            Date startTime = new Date(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(5);
            Date endTime = new Date(System.currentTimeMillis());
            System.out.println(getTimeResuming(startTime, endTime));

            System.out.println(getStartOrEndDayOfWeek(LocalDate.now(), true));
            System.out.println(getStartOrEndDayOfWeek(LocalDate.now(), false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

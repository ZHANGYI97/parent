package com.ziyi.common.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间格式化
 */
public class DateTimeUtils {

    private static String ZONE_ID_ASIA_SHANGHAI = "Asia/Shanghai";

    public static String format(Date date, String format) {
        return format(date2LocalDateTime(date), format);
    }

    /**
     * 对LocalDateTime类型时间格式化
     * @param date
     * @param format
     * @return
     */
    public static String format(LocalDateTime date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 对LocalDate格式时间格式化
     * @param date
     * @param format
     * @return
     */
    public static String format(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 将指定时间转成yyyyMMddHHmmss形式
     * @param dateTime
     * @return
     */
    public static String toSystemFormat(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    /**
     * 将指定时间格式化为yyyy-MM-dd HH:mm:ss形式
     * @param dateTime
     * @return
     */
    public static String toReadableFormat(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取当前自然日时间，格式为：yyyyMMddHHmmss
     * @return
     */
    public static String toSystemFormatAtNow(){
        return toSystemFormat(now());
    }

    /**
     * 将yyyyMMddHHmmss格式转成yyyy-MM-dd HH:mm:ss
     * @param dateTime
     * @return
     */
    public static String fromSystemToReadableFormat(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取当前自然日前的第<code>days</code>天
     * @param days
     * @return
     */
    public static LocalDate beforeDays(int days) {
        return nowDate().plusDays(-1 * days);
    }

    /**
     * 获取当前自然日后的第<code>days</code>天
     * @param days
     * @return
     */
    public static LocalDate afterDays(int days) {
        return nowDate().plusDays(days);
    }

    /**
     * 当前自然日日期距离1970-01-01T00:00:00Z的秒数
     * @return
     */
    public static long toEpochSecond(){
        return now().atZone(ZoneId.of(ZONE_ID_ASIA_SHANGHAI)).toEpochSecond();
    }

    /**
     * 当前自然日日期距离1970-01-01T00:00:00Z的毫秒数
     * @return
     */
    public static long toEpochMilli() {
        return now().atZone(ZoneId.of(ZONE_ID_ASIA_SHANGHAI)).toInstant().toEpochMilli();
    }

    /**
     * 当前自然日日期(UTC时区)距离1970-01-01T00:00:00Z的毫秒数
     * @return
     */
    public static long toUTCEpochMilli() {
        return now().atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
    }

    /**
     * 将date对象转成LocalDate对象
     * @param date
     * @return
     */
    public static LocalDate date2LocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.of(ZONE_ID_ASIA_SHANGHAI)).toLocalDate();
    }

    /**
     * 将date对象转成LocalDateTime对象
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.of(ZONE_ID_ASIA_SHANGHAI)).toLocalDateTime();
    }

    /**
     * 将localDate转成date
     * @param localDate
     * @return
     */
    public static Date localDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.of(ZONE_ID_ASIA_SHANGHAI)).toInstant());
    }

    /**
     * 将LocalDateTime转成date
     * @param localDateTime
     * @return
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.of(ZONE_ID_ASIA_SHANGHAI);
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 日期对象转成格式化后的字符串
     * @param date 日期对象
     * @param style 转化的格式（如：yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String formatDateTime(Date date, String style) {
        if (style == null || style.trim().length()<0) {
            style = "yyyy-MM-dd HH:mm:ss";
        }
        return date.toInstant().atZone(ZoneId.of(ZONE_ID_ASIA_SHANGHAI)).toLocalDateTime()
                .format(DateTimeFormatter.ofPattern(style));
    }

    /**
     * 获取当前自然日0时0分0秒的时间对象
     * @return
     */
    public static Date getZeroPointDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    /**
     * 获取当前日期
     * @return
     */
    public static LocalDateTime now(){
        return LocalDateTime.now(ZoneId.of(ZONE_ID_ASIA_SHANGHAI));
    }

    /**
     * 当前自然日期
     * @return
     */
    public static LocalDate nowDate() {
        return LocalDate.now(ZoneId.of(ZONE_ID_ASIA_SHANGHAI));
    }

    /**
     * 返回两天相隔时间
     * @param smdate
     * @param bdate
     * @return
     */
    public static int daysBetween(String smdate, String bdate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        int day = 0;
        try {
            Date date1 = simpleDateFormat.parse(smdate);
            Date date2 = simpleDateFormat.parse(bdate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            long time1 = calendar.getTimeInMillis();
            calendar.setTime(date2);
            long time2 = calendar.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000*3600*24);
            day = Integer.parseInt(String.valueOf(betweenDays));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 判断nowTime是否在begintime之后
     * @param nowTime
     * @param begintime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date begintime) {
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(nowTime);

        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(begintime);

        if (nowCalendar.after(beginCalendar)) {
            return true;
        } else {
            return false;
        }
    }
}

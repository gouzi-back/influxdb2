package com.example.demo.utils;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeUtils {
    private static final String OFFSET_ID = "+08:00";

    /**
     * 获取当前时间 在ISO-8601日历系统中的UTC /格林威治偏移的日期时间，如2007-12-03T10:15:30+08:00
     *
     * @return
     */
    public static OffsetDateTime getDateTimeNow() {
        return OffsetDateTime.now();
    }

    /**
     * 获取当前日期（不设置时分秒） 在ISO-8601日历系统中的UTC /格林威治偏移的日期时间，如2007-12-03T10:15:30+08:00
     *
     * @return
     */
    public static OffsetDateTime getDateNow() {
        OffsetDateTime now = OffsetDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        return getDateTime(year, month, day);
    }

    /**
     * 获取时间（只设置年月日）
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static OffsetDateTime getDateTime(int year, int month, int day) {
        return getDateTime(year, month, day, 0, 0, 0, 0, ZoneOffset.of(OFFSET_ID));
    }

    /**
     * 获取时间（只设置年月日时分秒）
     *
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @return
     */
    public static OffsetDateTime getDateTime(int year, int month, int day, int hour, int minute, int second) {
        return getDateTime(year, month, day, hour, minute, second, 0, ZoneOffset.of(OFFSET_ID));
    }

    /**
     * 获取时间
     *
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @param hour
     *            时
     * @param minute
     *            分
     * @param second
     *            秒
     * @param nanoOfSecond
     *            纳秒
     * @param zoneOffset
     *            时区
     * @return
     */
    public static OffsetDateTime getDateTime(int year, int month, int day, int hour, int minute, int second,
                                             int nanoOfSecond, ZoneOffset zoneOffset) {
        OffsetDateTime dateTime = OffsetDateTime.of(year, month, day, 0, 0, 0, 0, zoneOffset);
        return dateTime;
    }

    /**
     * 根据字符串设置日期(yyyy-MM-dd HH:mm:ss)
     *
     * @param text
     * @param formatter
     * @return
     */
    public static OffsetDateTime parseByYmdhms(CharSequence text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return parse(text, formatter);
    }

    /**
     * 根据字符串设置日期(yyyy-MM-dd)
     *
     * @param text
     * @param formatter
     * @return
     */
    public static OffsetDateTime parseByYmd(CharSequence text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return parse(text, formatter);
    }

    /**
     * 根据字符串设置日期
     *
     * @param text
     * @param formatter
     * @return
     */
    public static OffsetDateTime parse(CharSequence text, DateTimeFormatter formatter) {
        if (StringUtils.isEmpty(text) || formatter == null) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.parse(text, formatter);
        OffsetDateTime dateTime = OffsetDateTime.of(localDateTime, ZoneOffset.of(OFFSET_ID));
        return dateTime;
    }

    /**
     * 格式化时间（默认格式yyyy-MM-dd HH:mm:ss）
     *
     * @param dateTime
     * @return
     */
    public static String formatDateTimeToYmdhms(OffsetDateTime dateTime) {
        return formatDateTime(dateTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化时间（默认格式yyyy-MM-dd）
     *
     * @param dateTime
     * @return
     */
    public static String formatDateTimeToYmd(OffsetDateTime dateTime) {
        return formatDateTime(dateTime, "yyyy-MM-dd");
    }

    /**
     * 格式化时间
     *
     * @param dateTime
     * @param patten
     * @return
     */
    public static String formatDateTime(OffsetDateTime dateTime, String patten) {
        if (dateTime == null) {
            return null;
        }
        if (StringUtils.isEmpty(patten)) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(patten));
    }
}

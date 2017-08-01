package com.changhf.utils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * DateUtils
 * 
 * @author changhf
 *
 */
public class DateUtils {

	/**
	 * 获取指定格式的当前日期字符串
	 * 
	 * @param format
	 * @return
	 */
	public static String formatDate(LocalDate date, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return formatter.format(date);
	}
	/**
	 * 没办法，项目中如果不是强制jdk8，Date就得一直用
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
	/**
	 * 获取指定格式的当前日期字符串
	 * 
	 * @param format
	 * @return
	 */
	public static LocalDate parseDate(String dateStr, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return LocalDate.parse(dateStr, formatter);
	}

	/**
	 * 获取两个日期相差多少天
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getIntervalDays(LocalDate startDate, LocalDate endDate) {
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("时间先后顺序不对!");
		}
		long days = Math.abs(endDate.toEpochDay() - startDate.toEpochDay());
		return days;

	}

	/**
	 * 获取时分秒字符串
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String getTimeShort(LocalTime time, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		String dateString = formatter.format(time);
		return dateString;
	}

	/**
	 * 当前时间是否在17点00分之前
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isTimesmorning() {
		LocalTime now = LocalTime.now();
		boolean flag = now.isBefore(LocalTime.of(17, 0));
		return flag;

	}

	/**
	 * 得到几天前/后的时间
	 * 
	 * @param d
	 *            传入的日期
	 * @param day
	 *            相隔几天 day > 0 几天后，day < 0 几天前
	 * @return
	 */
	public static LocalDate getDateAfter(LocalDate d, int day) {
		return d.plusDays(day);
	}

	/**
	 * 获取五分钟之前的时间
	 * 
	 * @param minute
	 * @return
	 */
	public static LocalTime getTimeByMinute(int minute) {
		LocalTime time = LocalTime.now().withNano(0);
		return time.plusMinutes(minute);
	}

	/**
	 * 第一天 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate firstDay(LocalDate date) {
		return date.with(TemporalAdjusters.firstDayOfMonth());
	}

	/**
	 * 最后一天 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate lastDay(LocalDate date) {
		return date.with(TemporalAdjusters.lastDayOfMonth());
	}

	/**
	 * 获取小时
	 * 
	 * @param recommendTime
	 * @return
	 */
	public static int getHourTime(LocalDateTime time) {
		return time.getHour();
	}

	/**
	 * 根据时间获取当前周的开始和结束时间 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static Map<String, LocalDate> getWeekStartAndEnd(LocalDate date) {

		Map<String, LocalDate> map = Maps.newHashMap();
		// 开始日期
		map.put("start", getDateMonday(date));
		// 结束日期
		map.put("end", getDateSunday(date));

		return map;
	}

	/**
	 * 获取指定日期周一的时间
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate getDateMonday(LocalDate date) {
		if (date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
			return date;
		} else {
			return date.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
		}
	}

	/**
	 * 获得指定日期周日的时间
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate getDateSunday(LocalDate date) {
		if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			return date;
		} else {
			return date.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		}
	}

	/**
	 * 得到几个月前/后的时间
	 * 
	 * @param d
	 *            传入的日期
	 * @param month
	 *            相隔几个月 month > 0 几个月后，month < 0 几个月前
	 * @return
	 */
	public static LocalDate getMonthAfter(LocalDate d, int month) {
		return d.plusMonths(month);

	}

	public static void main(String[] args) {
		LocalDate date = LocalDate.now();
		System.out.println(getMonthAfter(date, 2));
	}

}

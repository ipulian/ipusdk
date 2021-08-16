package com.ipusoft.utils;

/**
 * author : GWFan
 * time   : 2020/5/18 13:26
 * desc   :
 */

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.ipusoft.context.constant.DateTimePattern;
import com.ipusoft.context.constant.TimeConstants;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class DateTimeUtils {
    private static final String TAG = "DateTimeUtils";

    //获取当天的开始时间
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String getDayBeginStr() {
        return date2String(getDayBegin());
    }

    public static String getDayBeginStr(String pattern) {
        return date2String(getDayBegin(), pattern);
    }

    //获取当天的结束时间
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static String getDayEndStr() {
        return date2String(getDayEnd());
    }

    public static String getDayEndStr(String pattern) {
        return date2String(getDayEnd(), pattern);
    }

    //获取昨天的开始时间
    public static Date getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static String getBeginDayOfYesterdayStr() {
        return date2String(getBeginDayOfYesterday());
    }

    public static String getBeginDayOfYesterdayStr(String pattern) {
        return date2String(getBeginDayOfYesterday(), pattern);
    }

    //获取昨天的结束时间
    public static Date getEndDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static String getEndDayOfYesterdayStr() {
        return date2String(getEndDayOfYesterday());
    }

    public static String getEndDayOfYesterdayStr(String pattern) {
        return date2String(getEndDayOfYesterday(), pattern);
    }

    //获取明天的开始时间
    public static Date getBeginDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    //获取明天的结束时间
    public static Date getEndDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    //获取本周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    public static String getBeginDayOfWeekStr() {
        return date2String(getBeginDayOfWeek());
    }

    public static String getBeginDayOfWeekStr(String pattern) {
        return date2String(getBeginDayOfWeek(), pattern);
    }

    //获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    public static String getEndDayOfWeekStr() {
        return date2String(getEndDayOfWeek());
    }

    public static String getEndDayOfWeekStr(String pattern) {
        return date2String(getEndDayOfWeek(), pattern);
    }

    //获取上周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }

    public static String getBeginDayOfLastWeekStr() {
        return date2String(getBeginDayOfLastWeek());
    }

    public static String getBeginDayOfLastWeekStr(String pattern) {
        return date2String(getBeginDayOfLastWeek(), pattern);
    }

    //获取上周的结束时间
    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    public static String getEndDayOfLastWeekStr() {
        return date2String(getEndDayOfLastWeek());
    }

    public static String getEndDayOfLastWeekStr(String pattern) {
        return date2String(getEndDayOfLastWeek(), pattern);
    }

    //获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        return millis2Date(getMonthStartTime());
    }

    //获取本月的结束时间
    public static Date getEndDayOfMonth() {
        return millis2Date(getMonthEndTime());
    }

    public static String date2String(final Date date, @NonNull final String pattern) {
        if (date == null) {
            return "";
        }
        return DateTimePattern.getDateFormat(pattern).format(date);
    }

    public static String date2String(final Date date) {
        return date2String(date, DateTimePattern.getDateTimeWithSecondFormat());
    }

    public static String date2String(final Date date, @NonNull final DateFormat format) {
        return format.format(date);
    }

    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    public static long string2Millis(final String time) {
        return string2Millis(time, DateTimePattern.getDateTimeWithSecondFormat());
    }

    /**
     * Formatted time string to the milliseconds.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the milliseconds
     */
    public static long string2Millis(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取当月开始时间戳
     *
     * @return
     */
    public static Long getMonthStartTime() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        // calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当月的结束时间戳
     *
     * @return
     */
    public static Long getMonthEndTime() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        //  calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));// 获取当前月最后一天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    //获取六个月前的开始时间
    public static Date getBeginDayOfSixMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 3, 1);
        return getDayStartTime(calendar.getTime());
    }


    //获取上月的开始时间
    public static Date getBeginDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取上月的结束时间
    public static Date getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        int day = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(getNowYear(), getNowMonth() - 2, day);
        return getDayEndTime(calendar.getTime());
    }

    //获取本年的开始时间
    public static java.util.Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    //获取本年的结束时间
    public static java.util.Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(Calendar.YEAR);
    }

    //获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(Calendar.MONTH) + 1;
    }

    //两个日期相减得到的天数
    public static int getDiffDays(Date beginDate, Date endDate) {

        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }

        long diff = (endDate.getTime() - beginDate.getTime())
                / (1000 * 60 * 60 * 24);

        int days = new Long(diff).intValue();

        return days;
    }

    //两个日期相减得到的毫秒数
    public static long dateDiff(Date beginDate, Date endDate) {
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }

    //获取两个日期中的最大日期
    public static Date max(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return beginDate;
        }
        return endDate;
    }

    //获取两个日期中的最小日期
    public static Date min(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return endDate;
        }
        return beginDate;
    }

    //返回某月该季度的第一个月
    public static Date getFirstSeasonDate(Date date) {
        final int[] SEASON = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3 - 3);
        return cal.getTime();
    }

    //返回某个日期下几天的日期
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }

    //返回某个日期前几天的日期
    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
        return cal.getTime();
    }

    //获取某年某月到某年某月按天的切片日期集合（间隔天数的集合）
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List getTimeList(int beginYear, int beginMonth, int endYear,
                                   int endMonth, int k) {
        List list = new ArrayList();
        if (beginYear == endYear) {
            for (int j = beginMonth; j <= endMonth; j++) {
                list.add(getTimeList(beginYear, j, k));

            }
        } else {
            {
                for (int j = beginMonth; j < 12; j++) {
                    list.add(getTimeList(beginYear, j, k));
                }

                for (int i = beginYear + 1; i < endYear; i++) {
                    for (int j = 0; j < 12; j++) {
                        list.add(getTimeList(i, j, k));
                    }
                }
                for (int j = 0; j <= endMonth; j++) {
                    list.add(getTimeList(endYear, j, k));
                }
            }
        }
        return list;
    }

    //获取某年某月按天切片日期集合（某个月间隔多少天的日期集合）
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List getTimeList(int beginYear, int beginMonth, int k) {
        List list = new ArrayList();
        Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
        int max = begincal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i < max; i = i + k) {
            list.add(begincal.getTime());
            begincal.add(Calendar.DATE, k);
        }
        begincal = new GregorianCalendar(beginYear, beginMonth, max);
        list.add(begincal.getTime());
        return list;
    }

    public static String formatDateTime(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if (isToday(dateTime)) {
            return "今天 " + dateTime.split(" ")[1].substring(0, 5);
        } else if (isYesterday(string2Date(dateTime))) {
            return "昨天 " + dateTime.split(" ")[1].substring(0, 5);
        }
        try {
            if (String.valueOf(year).equals(dateTime.split("-")[0])) {
                return dateTime.substring(5, 16);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateTime.substring(0, 16);
    }

    public static String formatDateTime1(long dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm");
        return simpleDateFormat.format(new Date(dateTime));
    }

    public static boolean isToday(final String time) {
        return isToday(string2Millis(time, DateTimePattern.getDateTimeWithSecondFormat()));
    }

    public static boolean isToday(final long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + TimeConstants.DAY;
    }

    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), DateTimePattern.getDateTimeWithSecondFormat());
    }

    public static String millis2String(final long millis, @NonNull final DateFormat format) {
        return format.format(new Date(millis));
    }

    public static String millis2String(final long millis) {
        return millis2String(millis, DateTimePattern.getDateTimeWithSecondFormat());
    }

    public static boolean isToday(final String time, @NonNull final DateFormat format) {
        return isToday(string2Millis(time, format));
    }

    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static String formatDateTime2(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if (isToday(dateTime)) {
            return "今天 " + dateTime.split(" ")[1].substring(0, 5);
        }
        try {
            if (String.valueOf(year).equals(dateTime.split("-")[0])) {
                return dateTime.substring(5, 16);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateTime.substring(0, 10);
    }

    public static String formatDateTime3(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
//        if (TimeUtils.isToday(dateTime)) {
//            return "今天 " + dateTime.split(" ")[1].substring(0, 5);
//        }
        String s = dateTime.replaceAll("-", "/");
        try {
            if (String.valueOf(year).equals(s.split("/")[0])) {
                return s.substring(5, 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s.substring(0, 10);
    }


    public static String formatDuration(Integer m) {
        if (m == null) {
            m = 0;
        }
        if (m < 60) {
            return m + "秒";
        }
        if (m < 3600) {
            String min = NumFormat(m / 60), second = NumFormat(m % 60);
            String result = min + "分";
            //if (m % 60 != 0) {
            result = result + second + "秒";
            //}
            return result;
        }
        if (m < 3600 * 24) {
            String hour = NumFormat(m / 60 / 60), min = NumFormat(m / 60 % 60), second = NumFormat(m % 60);
            String result = hour + "时";
            //if (m / 60 % 60 != 0) {
            result = result + min + "分";
            // }
            // if (m % 60 != 0) {
            result = result + second + "秒";
            // }
            return result;
        }
        return NumFormat(m / 60 / 60 / 24) + "天" + NumFormat(m / 60 / 60 % 24) + "时" + NumFormat(m / 60 % 60) + "分" + NumFormat(m % 60) + "秒";
    }


    public static String formatVideoDuration(long m) {
        if (m < 60) {
            return m + "秒";
        }
        if (m < 3600) {
            String min = NumFormat(m / 60), second = NumFormat(m % 60);
            String result = min + ":";
            //if (m % 60 != 0) {
            result = result + second + "";
            //}
            return result;
        }
        if (m < 3600 * 24) {
            String hour = NumFormat(m / 60 / 60), min = NumFormat(m / 60 % 60), second = NumFormat(m % 60);
            String result = hour + ":";
            //if (m / 60 % 60 != 0) {
            result = result + min + ":";
            // }
            // if (m % 60 != 0) {
            result = result + second + "";
            // }
            return result;
        }
        return NumFormat(m / 60 / 60 / 24) + "天" + NumFormat(m / 60 / 60 % 24) + ":" + NumFormat(m / 60 % 60) + ":" + NumFormat(m % 60) + "";
    }


    public static String formatVideoDuration2(long m) {
        if (m < 60) {
            String str = m + "";
            if (m < 10) {
                str = "0" + str;
            }
            return "00:" + str;
        }
        if (m < 3600) {
            String min = NumFormat(m / 60), second = NumFormat(m % 60);
            if (min.length() < 2) {
                min = "0" + min;
            }
            if (second.length() < 2) {
                second = "0" + second;
            }
            return min + ":" + second;
        }
        if (m < 3600 * 24) {
            String hour = NumFormat(m / 60 / 60), min = NumFormat(m / 60 % 60), second = NumFormat(m % 60);
            if (hour.length() < 2) {
                hour = "0" + hour;
            }
            if (min.length() < 2) {
                min = "0" + min;
            }
            if (second.length() < 2) {
                second = "0" + second;
            }
            return hour + ":" + min + ":" + second;
        }
        return NumFormat(m / 60 / 60 / 24) + "天" + NumFormat(m / 60 / 60 % 24) + ":" + NumFormat(m / 60 % 60) + ":" + NumFormat(m % 60) + "";
    }

    public static String NumFormat(long i) {
        if (String.valueOf(i).length() < 2) {
            return "" + i;
        } else {
            return String.valueOf(i);
        }
    }

    /**
     * 根据日期获取该日期是该年的多少天
     *
     * @param date
     * @return
     */
    public static Integer getDayNumForYear(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(Calendar.DAY_OF_YEAR);
    }


    public static boolean isYesterday(Date date) {
        boolean flag = false;
        // 先获取年份
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        // 获取当前年份 和 一年中的第几天
        int day = getDayNumForYear(date);
        int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int currentDay = getDayNumForYear(new Date());
        // 计算 如果是去年的
        if (currentYear - year == 1) {
            // 如果当前正好是 1月1日 计算去年有多少天，指定时间是否是一年中的最后一天
            if (currentDay == 1) {
                int yearDay;
                if (year % 400 == 0) {
                    // 世纪闰年
                    yearDay = 366;
                } else if (year % 4 == 0 && year % 100 != 0) {
                    // 普通闰年
                    yearDay = 366;
                } else {
                    // 平年
                    yearDay = 365;
                }
                if (day == yearDay) {
                    flag = true;
                }
            }
        } else {
            if (currentDay - day == 1) {
                flag = true;
            }
        }
        return flag;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    public static int timeCompare(String t1, String t2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            if (StringUtils.isNotEmpty(t1) && StringUtils.isNotEmpty(t2)) {
                Date parse1 = dateFormat.parse(t1);
                Date parse2 = dateFormat.parse(t2);
                if (parse1 != null && parse2 != null) {
                    c1.setTime(parse1);
                    c2.setTime(parse2);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c1.compareTo(c2);
    }


    /**
     * 日期格式化
     *
     * @return
     */
    public static String formatWeChatMessageTime(String str) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = string2Date(str);
        //转换为日期
        long time = date.getTime();
        if (isThisYear(date)) {//今年
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            if (isToday(date)) { //今天
                int minute = minutesAgo(time);
                if (minute < 60) {//1小时之内
                    if (minute <= 1) {//一分钟之内，显示刚刚
                        return "刚刚";
                    } else {
                        return minute + "分钟前";
                    }
                } else {
                    return simpleDateFormat.format(date);
                }
            } else {
                String t = "";
                int hours = date.getHours();
                if (hours < 6) {
                    t = "早晨";
                } else if (hours < 12) {
                    t = "上午";
                } else if (hours < 18) {
                    t = "下午";
                } else {
                    t = "晚上";
                }
                if (isYestYesterday(date)) {//昨天，显示昨天
                    return "昨天 " + t + " " + simpleDateFormat.format(date);
                } else if (isThisWeek(date)) {//本周,显示周几

                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = "周一";
                    }
                    if (date.getDay() == 2) {
                        weekday = "周二";
                    }
                    if (date.getDay() == 3) {
                        weekday = "周三";
                    }
                    if (date.getDay() == 4) {
                        weekday = "周四";
                    }
                    if (date.getDay() == 5) {
                        weekday = "周五";
                    }
                    if (date.getDay() == 6) {
                        weekday = "周六";
                    }
                    if (date.getDay() == 0) {
                        weekday = "周日";
                    }
                    return weekday + " " + t + " " + simpleDateFormat.format(date);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
                    return sdf.format(date);
                }
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        }
    }

    private static int minutesAgo(long time) {
        return (int) ((System.currentTimeMillis() - time) / (60000));
    }

    private static boolean isToday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() == now.getDate());
    }

    private static boolean isYestYesterday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() + 1 == now.getDate());
    }

    private static boolean isThisWeek(Date date) {
        Date now = new Date();
        if ((date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth())) {
            if (now.getDay() - date.getDay() < now.getDay() && now.getDate() - date.getDate() > 0 && now.getDate() - date.getDate() < 7) {
                return true;
            }
        }
        return false;
    }

    private static boolean isThisYear(Date date) {
        Date now = new Date();
        return date.getYear() == now.getYear();
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            return false;
        }
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static Date string2Date(final String time) {
        return string2Date(time, DateTimePattern.getDateTimeWithSecondFormat());
    }

    public static Date string2Date(final String time, @NonNull final String pattern) {
        return string2Date(time, DateTimePattern.getDateFormat(pattern));
    }

    public static Date string2Date(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

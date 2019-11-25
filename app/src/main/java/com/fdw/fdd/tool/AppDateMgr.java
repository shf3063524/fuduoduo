package com.fdw.fdd.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by kayroc on 2017/8/16.
 */

public class AppDateMgr {
    public AppDateMgr() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static final SimpleDateFormat YYYYMMDD_DIAN = new SimpleDateFormat("yyyy.MM.dd");
    public static final SimpleDateFormat YYYYMMDD_CHINESE = new SimpleDateFormat("yyyy年MM月dd日");
    public static final SimpleDateFormat YYYYMMDDHHMMSS_CHINESE = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    public static final SimpleDateFormat YYYYMMDDHHMMSS_DIAN = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    public static final SimpleDateFormat YYYYMMDDHHMM_GANG = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat YYYYMMDD_GANG = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat HHMM_COLON = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat YYYYMM_GANG = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat YYYYMM_DIAN = new SimpleDateFormat("yyyy.MM");
    public static final SimpleDateFormat YYYYMM_SLASH = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat YYYYMMEE_SLASH = new SimpleDateFormat("yyyy年MM月dd EE");
    public static final SimpleDateFormat YYYYMD_CHINESE = new SimpleDateFormat("yyyy年M月d日");

    /**
     * 从时间戳中获取年
     *
     * @param dateTime 时间戳
     * @return
     */
    public static String parseDate2Year(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(new Date(Long.parseLong(dateTime)));
    }

    /**
     * 从时间戳中获取月
     *
     * @param dateTime 时间戳
     * @return
     */
    public static String parseDate2Month(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("M");
        return format.format(new Date(Long.parseLong(dateTime)));
    }

    /**
     * 从时间戳中获取日
     *
     * @param dateTime 时间戳
     * @return
     */
    public static String parseDate2Day(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("d");
        return format.format(new Date(Long.parseLong(dateTime)));
    }

    /**
     * 将时间戳转化为指定格式
     *
     * @param format   指定格式
     * @param dateTime 时间戳
     * @return
     */
    public static String parseDate(SimpleDateFormat format, String dateTime) {
        return format.format(new Date(Long.parseLong(dateTime)));
    }

    /**
     * 将指定格式的字符串转换为时间戳
     *
     * @param format 指定格式
     * @param str    字符串
     * @return
     * @throws ParseException
     */
    public static String dateToStamp(SimpleDateFormat format, String str) {
        Date date = null;
        try {
            date = format.parse(str);
            return String.valueOf(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本周的日期集合（第一天为星期日）
     *
     * @return 2017-12-09
     */
    public static ArrayList<String> getWeekDateList() {
        ArrayList<String> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        for (int i = 0; i < 7; i++) {
            System.out.println(YYYYMMDD_GANG.format(calendar.getTime()));
            dateList.add(YYYYMMDD_GANG.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
        return dateList;
    }

    /**
     * 从"2017-12-09"这样的字符串中提取天
     *
     * @param date 像"20160103"格式的字符串
     */
    public static String getDayFromString(String date) {
        String day = date.substring(8);
        if (day.startsWith("0")) {
            day = day.substring(1);
        }
        return day;
    }

    /**
     * 当天的年月日
     *
     * @return "2017/12/07"
     */
    public static String todayYyyyMmDd() {
        return YYYYMM_SLASH.format(new Date());
    }

    /**
     * 当天的年月日
     *
     * @return "2018年5月8日"
     */
    public static String todayYyyyMD() {
        return YYYYMD_CHINESE.format(new Date());
    }

    /**
     * 将 “yyyy-MM-dd” 格式的日期转为毫秒值
     *
     * @param date yyyy-mm-dd
     * @return
     */
    public static String getMSForDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse(date);
            return String.valueOf(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取某天(格式xxxx年xx月xx日)
     *
     * @param index -1是昨天，0是今天，1是明天依次类推
     * @return
     */
    public static String getOneDayMD(int index) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String dateString = "";
        try {
            calendar.add(calendar.DATE, index);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime(); //这个时间就是日期往后推一天的结果
            dateString = YYYYMMDD_GANG.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     * 获取本周的日期区间（周一和周日日期）
     *
     * @return 本周一 + "-" + 本周日
     */
    public static String getThisWeekTimeInterval() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = YYYYMMDD_GANG.format(cal.getTime());
        // System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = YYYYMMDD_GANG.format(cal.getTime());
        // System.out.println("所在周星期日的日期：" + imptimeEnd);
        return imptimeBegin + "~" + imptimeEnd;
    }


    /**
     * 获取上周的日期区间
     *
     * @return 上周一 + "-" + 上周日
     */
    public static String getLastWeekTimeInterval() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println("dayOfWeek = " + dayOfWeek);
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        String lastBeginDate = YYYYMMDD_GANG.format(calendar1.getTime());
        String lastEndDate = YYYYMMDD_GANG.format(calendar2.getTime());
        return lastBeginDate + "~" + lastEndDate;
    }

    /**
     * 获取下周的日期区间
     *
     * @return 下周一 + "-" + 下周日
     */
    public static String getNextWeekTimeInterval() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println("dayOfWeek = " + dayOfWeek);
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 + 7);
        calendar2.add(Calendar.DATE, offset2 + 7);
        String lastBeginDate = YYYYMMDD_GANG.format(calendar1.getTime());
        String lastEndDate = YYYYMMDD_GANG.format(calendar2.getTime());
        return lastBeginDate + "~" + lastEndDate;
    }

    /**
     * 获取年月
     *
     * @param index -1是上月，0是本月，1是下月依次类推
     * @return
     */
    public static String getYearMonth(int index) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, index);
        return YYYYMM_GANG.format(cal.getTime());
    }

    /**
     * 获取某月的天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(SimpleDateFormat format, String date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(date));
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 当前季度的开始时间
     *
     * @return
     */
    public static String getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return YYYYMMDD_GANG.format(c.getTime());
    }

    /**
     * 获取startDate至endDate的所有日期(用于X轴显示数据)
     * note:若 endDate < startDate，则endDate为开始时间，startDate为截止时间
     *
     * @param startDate 开始时间戳
     * @param endDate   截止时间戳
     *                  // @return ... 2018.10.17 2018.10.18
     * @return 2018年, ..., 31, 2月, ..., 12月, 2, ..., 31
     */
    public static ArrayList<String> getXAxisDate(Long startDate, Long endDate) {
        if (startDate > endDate) {
            long tempDate = startDate;
            endDate = startDate;
            startDate = tempDate;
        }
        ArrayList<String> tempList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(startDate));
        for (long d = cal.getTimeInMillis(); d <= endDate; d = cal.getTimeInMillis()) {
            String date = parseDate(YYYYMMDD_DIAN, String.valueOf(d));
            if (date.endsWith(".01.01")) {
                tempList.add(parseDate2Year(String.valueOf(d)) + "年");
            } else if (date.endsWith(".01")) {
                tempList.add(parseDate2Month(String.valueOf(d)) + "月");
            } else {
                tempList.add(parseDate2Day(String.valueOf(d)));
            }
            // tempList.add(parseDate(YYYYMMDD_DIAN, String.valueOf(d)));
            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        }
        return tempList;
    }

    /**
     * 获取startDate至endDate的所有日期(用于比较服务器返回的X轴数据)
     * note:若 endDate < startDate，则endDate为开始时间，startDate为截止时间
     *
     * @param startDate 开始时间戳
     * @param endDate   截止时间戳
     * @return ... 2018-10-17 2018-10-18
     */
    public static ArrayList<String> getCompareDate(Long startDate, Long endDate) {
        if (startDate > endDate) {
            long tempDate = startDate;
            endDate = startDate;
            startDate = tempDate;
        }
        ArrayList<String> tempList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(startDate));
        for (long d = cal.getTimeInMillis(); d <= endDate; d = cal.getTimeInMillis()) {
            // System.out.println(sdf.format(d));
            tempList.add(parseDate(YYYYMMDD_GANG, String.valueOf(d)));
            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        }
        return tempList;
    }

    /**
     * 获取startDate至endDate的所有日期(用于比较服务器返回的X轴数据)
     *
     * @param startDate 开始日期 2018-7
     * @param endDate   结束日期 2018-10
     * @return 2018-7 2018-8 2018-9 2018-10
     */
    public static ArrayList<String> getCompareDate(String startDate, String endDate) {
        ArrayList<String> tempList = new ArrayList<>();
        tempList.add(startDate);
        if (startDate.equals(endDate)) {
            return tempList;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(YYYYMM_GANG.parse(startDate));
            for (; ; ) {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.MONTH, 1);
                String date = AppDateMgr.parseDate(AppDateMgr.YYYYMM_GANG, String.valueOf(calendar.getTimeInMillis()));
                System.out.println(calendar.getTime());
                if (endDate.equals(date)) {
                    tempList.add(endDate);
                    break;
                }
                tempList.add(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tempList;
    }
}

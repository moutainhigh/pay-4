package com.pay.poss.util;


import com.pay.poss.ipayconst.IpayConst;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式化共通
 * Created by songyilin on 2015/5/11.
 */
public class DateUtil {

    public final static String YYYYMMDD = "yyyyMMdd";
    public final static String YYYYMMDD2 = "yyyy/MM/dd";
    public final static String YYYYMMDD3 = "yyyy-MM-dd";
    public final static String YYYYMMDD4 = "yyyy年M月d日";
    public final static String MMDD = "MM/dd";
    public final static String HHMM = "HH:mm";
    public final static String HHMMSS = "HH:mm:ss";
    public final static String HHMMSSSSS = "HH:mm:ss.SSS";
    public final static String HHMMSS2 = "hh:mm:ss a";
    public final static String HHMMSSSSS2 = "hh:mm:ss.SSS a";
    public final static String YYYYMMDDHH = "yyyyMMdd HH";
    public final static String YYYYMMDDHHMM = "yyyyMMdd HH:mm";
    public final static String YYYYMMDDHHMM2 = "yyyy/MM/dd HH:mm";
    public final static String YYYYMMDDHHMM3 = "yyyyMMddHHmm";
    public final static String YYYYMMDDHHMM4 = "yyyy年M月d日 HH:mm";
    public final static String YYYYMMDDHHMMSS = "yyyyMMdd HH:mm:ss";
    public final static String YYYYMMDDHHMMSS2 = "yyyy/MM/dd HH:mm:ss";
    public final static String YYYYMMDDHHMMSS3 = "yyyy-MM-dd HH:mm:ss";
    public final static String YYYYMMDDHHMMSS4 = "yyyyMMddHHmmss";
    public final static String YYYYMMDDHHMMSSMS = "yyyyMMdd HH:mm:ss.SSS";
    public final static String YYYYMMDDHHMMSSMS2 = "yyyy-MM-dd HH:mm:ss.S";
    public static final String YYYYMMDDHHMMSSMS3 = "yyyy-MM-dd_HH:mm:ss.SSS";
    public static int ONE_DATE_MI = 1000 * 60 * 60 * 24;
    public static int ONE_YEAR = 365;
    private static Logger logger = Logger.getLogger(DateUtil.class);

    /**
     * 日期格式化
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null || pattern == null) {
            return IpayConst.EMPTY;
        }

        SimpleDateFormat simpleDateFordmat = new SimpleDateFormat(pattern);
        return simpleDateFordmat.format(date);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Object date, String pattern) {
        if (date == null || pattern == null) {
            return IpayConst.EMPTY;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(IpayUtil.toDate(date));
    }

    /**
     * 字符转日期
     *
     * @param source
     * @param pattern
     * @return
     */
    public static Date parseDate(String source, String pattern) {
        if (source == null || pattern == null) {
            return null;
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 日期转日历
     *
     * @param date
     * @return
     */
    public static Calendar convertToCalendar(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 获取年龄
     *
     * @param birthday
     * @return
     */
    public static int getAge(Date birthday) {
        if (birthday == null) {
            return 0;
        }

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(birthday);

        Calendar calendar2 = Calendar.getInstance();

        int value = calendar2.compareTo(calendar1);

        if (value % ONE_DATE_MI == 0) {
            value = value / ONE_DATE_MI;
        } else {
            value = value / ONE_DATE_MI + 1;
        }

        if (value % ONE_YEAR == 0) {
            value = value / ONE_YEAR;
        } else {
            value = value / ONE_YEAR + 1;
        }

        return value;
    }

    /**
     * 获取现在的日时
     *
     * @param pattern
     * @return
     */
    public static Date getNow(String pattern) {
        return parseDate(format(new Date(), pattern), pattern);
    }

    /**
     * 获取现在的日时
     *
     * @return
     */
    public static Date getNow() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 增加分钟
     *
     * @param time
     * @param amount
     * @return
     */
    public static Date addMinutes(Date time, int amount) {
        return DateUtils.addMinutes(time, amount);
    }

    /**
     * 增加小时
     *
     * @param time
     * @param amount
     * @return
     */
    public static Date addHour(Date time, int amount) {
        return DateUtils.addHours(time, amount);
    }

    /**
     * 增加秒
     *
     * @param time
     * @param amount
     * @return
     */
    public static Date addSeconds(Date time, int amount) {
        return DateUtils.addSeconds(time, amount);
    }

    /**
     * 增加天
     *
     * @param time
     * @param amount
     * @return
     */
    public static Date addDay(Date time, int amount) {
        return DateUtils.addDays(time, amount);
    }

    /**
     * 获取相差几分钟
     *
     * @param first
     * @param secend
     * @return
     */
    public static long minusMinute(Date first, Date secend) {
        return (long) Math.ceil((first.getTime() - secend.getTime()) / (1000.0 * 60));
    }

    /**
     * 获取相差几秒
     *
     * @param first
     * @param secend
     * @return
     */
    public static long minusSecond(Date first, Date secend) {
        return (long) Math.ceil((first.getTime() - secend.getTime()) / 1000.0);
    }

    /**
     * 获取星期
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 将日期的时间设置为凌晨0点0分0秒
     * @param date
     * @return
     */
    public static Date getDateAM(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    /**
     * 计算两个日期间隔的天数
     * 当 startDate小于endDate时，结果为负值
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getDayGap(Date startDate, Date endDate){
        if (startDate == null || endDate == null){
            return null;
        }
        startDate = getDateAM(startDate);
        endDate = getDateAM(endDate);
        long millisecondGap = endDate.getTime() - startDate.getTime();
        int dayGap = (int)(millisecondGap/ONE_DATE_MI);
        return dayGap;
    }


    public static String getLastDayOfMonth(int year,int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH, month-1);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }



    public static void main(String [] args){
        Date date1 = DateUtil.parseDate("2015-10-29 10:45:21", DateUtil.YYYYMMDDHHMMSS3);
        Date date2 = DateUtil.parseDate("2015-10-30 20:33:21", DateUtil.YYYYMMDDHHMMSS3);
        System.out.println(DateUtil.getDayGap(date1, date2));

//        BigDecimal interestSum = new BigDecimal(400);
//        interestSum = interestSum.multiply(new BigDecimal(0.54));
//        System.out.println("interestSum:"+interestSum);
//        //interestSum = interestSum.setScale(2,BigDecimal.ROUND_HALF_UP);
//        //System.out.println("interestSum:"+interestSum);
//        interestSum = interestSum.divide(new BigDecimal(365), 4,BigDecimal.ROUND_HALF_UP);
//        System.out.println("interestSum:"+interestSum);
//       // interestSum = interestSum.setScale(2,BigDecimal.ROUND_HALF_UP);
//        //System.out.println("interestSum:"+interestSum);
    }


}

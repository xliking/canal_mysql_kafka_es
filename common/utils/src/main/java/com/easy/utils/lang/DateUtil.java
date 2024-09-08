package com.easy.utils.lang;

import cn.hutool.core.date.DatePattern;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static cn.hutool.core.date.DatePattern.PURE_TIME_FORMATTER;

/**
 * 时间工具
 * </p>
 *
 * @author muchi
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {
    public static final String DATE_PATH = "yyyy/MM/dd";
    public static final String[] PARSE_PATTERNS =
            {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
                    "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    public static final String[] WEEK = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    public static final String[] HOUR_INDEX = {"0-6", "6-8", "8-10", "10-12", "12-14", "14-16", "16-18", "18-20", "20-22", "22-24"};

    private DateUtil() {
        throw new IllegalAccessError("DateUtils.class");
    }

    /**
     * 获取当前年月日
     *
     * @return 日期路径 即年/月/日 如2018/08/08
     */
    public static String datePath() {
        return format(new Date(), DATE_PATH);
    }

    /**
     * 获取当前 时 分 秒 毫秒
     *
     * @return 日期数字  如154320846
     */
    public static String timeNum() {
        return format(new Date(), PURE_TIME_FORMATTER);
    }

    /**
     * 传入两个时间范围，返回这两个时间范围内的所有日期，并保存在一个集合中
     */
    public static List<String> findEveryDay(String beginTime, String endTime) {
        // 1.创建一个放所有日期的集合
        List<String> dates = new ArrayList<>();
        // 2.创建时间解析对象规定解析格式
        String datePattern = DatePattern.NORM_DATE_PATTERN;
        // 3.将传入的时间解析成Date类型,相当于格式化
        Date dBegin = parse(beginTime, datePattern);
        Date dEnd = parse(endTime, datePattern);
        // 4.将格式化后的第一天添加进集合
        dates.add(format(dBegin, datePattern));
        // 5.使用本地的时区和区域获取日历
        Calendar calBegin = Calendar.getInstance();
        // 6.传入起始时间将此日历设置为起始日历
        calBegin.setTime(dBegin);
        // 8.判断结束日期是否在起始日历的日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 9.根据日历的规则:月份中的每一天，为起始日历加一天
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            // 10.得到的每一天就添加进集合
            dates.add(format(calBegin.getTime(), datePattern));
            // 11.如果当前的起始日历超过结束日期后,就结束循环
        }
        return dates;
    }

    /**
     * 日期转星期
     */
    public static String dateToWeek(String date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        // 获得一个日历
        Calendar cal = Calendar.getInstance();
        Date datet;
        try {
            datet = f.parse(date);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 指示一个星期中的某天。
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 根据星期获取下标 inex
     * 星期一为 0  以此类推
     */
    public static int getWeekIndex(String week) {
        if (StringUtils.isBlank(week)) {
            throw new RuntimeException("时间不能为空");
        }
        for (int i = 0; i < WEEK.length; i++) {
            if (WEEK[i].equals(week)) {
                return i;
            }
        }
        throw new RuntimeException("数据错误");
    }

    /**
     * 获取小时的下标 index
     * 0-6时     0
     * 6-8时     1
     * 8-10时    2
     * 10-12时   3
     * 12-14时   4
     * 14-16时   5
     * 16-18时   6
     * 18-20时   7
     * 20-22时   8
     * 22-24时   9
     */
    public static int getHourIndex(String date) {
        if (StringUtils.isBlank(date)) {
            throw new RuntimeException("时间不能为空");
        }
        // 截取小时
        int hour = Integer.parseInt(date.substring(11, 13));
        for (int i = 0; i < HOUR_INDEX.length; i++) {
            if (Integer.parseInt(HOUR_INDEX[i].split("-")[0]) <= hour && hour < Integer.parseInt(HOUR_INDEX[i].split("-")[1])) {
                return i;
            }
        }
        throw new RuntimeException("数据错误");
    }

    /**
     * 计算2时间差值
     *
     * @param st
     * @param end
     * @return
     * @throws ParseException
     */
    public static long getTimeDifference(String st, String end) throws ParseException {
        if (StringUtils.isBlank(st) || StringUtils.isBlank(end)) {
            return 0;
        }
        if (st.equals(end)) {
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat(PARSE_PATTERNS[1]);
        Date stDate = format.parse(st);
        Date endDate = format.parse(end);
        if (stDate.getTime() > endDate.getTime()) {
            return 0;
        }
        long between = endDate.getTime() - stDate.getTime();
        return (between / 1000);
    }


}
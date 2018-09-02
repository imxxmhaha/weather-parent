package cn.xxm.utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 */
public class DateUtil implements Serializable {
    public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// hh12灏忔椂鍒�
    public final static SimpleDateFormat sdf24 = new SimpleDateFormat("yyyyMMddHHmmss");// HH
    // 24灏忔椂鍒�

    /**
     * 日期转换周几
     *
     * @param dateTime
     * @param partten
     * @return
     * @throws Exception
     */
    public static int dayForWeek(String dateTime, String partten) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(getFormat(partten).parse(dateTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 验证是否日期
     *
     * @param format
     * @param date
     * @return
     */
    public static boolean isValidDate(String format, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 日期运算
     *
     * @param date
     * @param format
     * @param type
     * @param num
     * @return
     */
    public static String dateAdd(String date, String format, String type, int num) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        GregorianCalendar gc = new GregorianCalendar();
        try {
            gc.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if ("D".equals(type)) {
            gc.add(Calendar.DATE, num);// 指示一个月中的某天
        } else if ("Y".equals(type)) {
            gc.add(Calendar.YEAR, num);// 年
        } else if ("M".equals(type)) {
            gc.add(Calendar.MONTH, num);// 月
        } else if ("H".equals(type)) {
            gc.add(Calendar.HOUR, num);// 时
        } else if ("m".equals(type)) {
            gc.add(Calendar.MINUTE, num);// 分
        } else if ("S".equals(type)) {
            gc.add(Calendar.SECOND, num);// 秒
        }
        return new SimpleDateFormat(format).format(gc.getTime());
    }

    /**
     * 日期运算
     *
     * @param date
     * @param type 传参D,年+num
     * @param num
     * @return
     */
    public static Date dateAdd(Date date, String type, int num) {
        // GregorianCalendar 是 Calendar 的一个具体子类，提供了世界上大多数国家/地区使用的标准日历系统。
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        if ("D".equals(type)) {
            // .add() 根据日历规则，将指定的（有符号的）时间量添加到给定的日历字段中
            gc.add(Calendar.DATE, num);// 日
        } else if ("Y".equals(type)) {
            gc.add(Calendar.YEAR, num);// 年
        } else if ("M".equals(type)) {
            gc.add(Calendar.MONTH, num);// 月
        } else if ("H".equals(type)) {
            gc.add(Calendar.HOUR, num);// 时
        } else if ("m".equals(type)) {
            gc.add(Calendar.MINUTE, num);// 分
        } else if ("S".equals(type)) {
            gc.add(Calendar.SECOND, num);// 秒
        }
        return gc.getTime();
    }

    /**
     * 字符串转日期
     *
     * @param time
     * @param partten
     * @return
     */
    public static Date stringToDate(String time, String partten) {
        try {
            return getFormat(partten).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTime() {
        return sdf.format(new Date());
    }

    public static String getTime2() {
        return sdf24.format(new Date());
    }

    /**
     * 获取当前日期，返回指定格式
     *
     * @param format
     * @return
     */
    public static String getFormatDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date());
    }

    public static SimpleDateFormat getFormat(String partten) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(partten);
        return dateFormat;
    }

    /**
     * 获取当前星期
     *
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * date 转换成年月日时分秒 的String格式
     *
     * @param date
     * @return
     */
    public static String dateToStringYMDHMS(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String created = formatter.format(date);
        return created;
    }

    /**
     * date 转换成年月日 的String 格式
     *
     * @param date
     * @return
     */
    public static String dateToStringYMD(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String created = formatter.format(date);
        return created;
    }

    // 根据成为黑卡的时间,以及返润的时间,判断黑卡是否有效

    /**
     * @param startDate : 成为黑卡的时间
     * @param endDate   : 计算返润的时间
     * @return
     */
    public static boolean isBlackEffective(Date startDate, Date endDate) {
        boolean flag = false;
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("星期一", 1);
        map.put("星期二", 2);
        map.put("星期三", 3);
        map.put("星期四", 4);
        map.put("星期五", 5);
        map.put("星期六", 6);
        map.put("星期日", 7);
        // 获得成为分销商的时间是星期几
        String weekOfStart = getWeekOfDate(startDate);
        // 获得计算返润的时间是星期几
        String weekOfEnd = getWeekOfDate(endDate);
        // i,j 星期数对应的序号
        Integer i = map.get(weekOfStart);
        Integer j = map.get(weekOfEnd);
        // 计算返润的时间与成为黑卡的时间的毫秒值
        long timeGap = getTimeGap(startDate, endDate);
        // 1天的时间毫秒值的差距
        long oneGap = getWeekGap(1);
        // 2天的时间毫秒值的差距
        long twoGap = getWeekGap(2);
        // 7天的时间毫秒值的差距
        long weekGap = getWeekGap(7);
        if (i <= 2) {//成为黑卡的时间为星期1或者星期2
            if (j >= 3) { //做日终的时间为星期三之后
                flag = true;
            } else {//如果做日终的时间 到了下个星期,用timeGap > twoGap 表示做日终的时间至少是比成为黑卡的时间多2天时间,
                //所以做日终的时间是到了下个星期的星期一或者星期2
                if (timeGap > twoGap) {
                    flag = true;
                }
            }
        } else { //成为黑卡的时间在星期三之后(包括星期三)
            if (timeGap > weekGap) {  //如果做日终的时间比成为黑卡的时间超过一个星期
                flag = true;
            } else {    //如果不超过一个星期
                if (timeGap > oneGap) {   //timeGap > oneGap  用来判断两者是不是同一个星期的相同的星期数
                    if (j >= 3 && j <= i) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    public static void main(String[] args) throws Exception {
        Date date = new Date();
        Date start = DateUtil.dateAdd(date, "D", 6);
        Date end = null;

        for (int i = 0; i <= 7; i++) {
            end = DateUtil.dateAdd(date, "D", (6 + i));

            System.out.println(getWeekOfDate(start));
            System.out.println(getWeekOfDate(end));
            boolean b = isBlackEffective(start, end);
            System.out.println(b);
            System.out.println("----------------");
            System.out.println("----------------");

        }
    }

    public static long getTimeGap(Date startDate, Date endDate) {
        long time = startDate.getTime();
        long time2 = endDate.getTime();
        return time2 - time;
    }

    // 多少天的时间差
    public static long getWeekGap(int num) {
        Date date = new Date();
        Date dateAdd = DateUtil.dateAdd(date, "D", num);
        long time = date.getTime();
        long time2 = dateAdd.getTime();
        return time2 - time;
    }

}

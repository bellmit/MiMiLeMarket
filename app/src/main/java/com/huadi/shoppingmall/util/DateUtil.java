package com.huadi.shoppingmall.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by smartershining on 16-7-15.
 */

/**
 * 日期管理类
 */
public class DateUtil {

    public static String  dateToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }


    public static Date stringToDate(String s) {
        DateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date  add(Date date, int hour) {
        date.setHours(date.getHours() + hour);
        return date;
    }


}

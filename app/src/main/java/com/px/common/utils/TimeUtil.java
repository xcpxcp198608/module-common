package com.px.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by patrick on 13/10/2017.
 * create time : 11:32 AM
 */

public class TimeUtil {

    public static long getUnixFromStr(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                new Locale("en"));
        Date date;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            return 0;
        }
        return date.getTime();
    }

    public static String getStringTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                new Locale("en"));
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }
}

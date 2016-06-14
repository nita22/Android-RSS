package com.scnu.nita22.androidrss.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nita22 on 2016/6/14.
 */

public class TimeUtils {

    public static String getFormatTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try {
            long millisecond = sdf.parse(time).getTime();
            long difference = new Date().getTime() - millisecond;
            int minutes = (int) (difference / (1000 * 60));

            if (minutes < 1) {
                return "刚刚发布";
            } else if (minutes < 60) {
                return minutes + "分钟前发布";
            } else if (minutes < 60 * 24) {
                return minutes / 60 + "小时前发布";
            } else if (minutes < 60 * 24 * 30) {
                return minutes / (60 * 24) + "天前发布";
            } else {
                return "很久以前发布";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return " ";
    }
}

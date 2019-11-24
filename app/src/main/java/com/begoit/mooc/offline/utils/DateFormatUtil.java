package com.begoit.mooc.offline.utils;

import java.text.SimpleDateFormat;

/**
 * @Description:日期格式化工具
 * @Author:gxj
 * @Time 2019/5/6
 */

public class DateFormatUtil {

    public static String longToString(long timeMillis){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(timeMillis);
    }
}

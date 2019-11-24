package com.begoit.mooc.offline.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;

import com.begoit.mooc.BegoitMoocApplication;

/**
 * @Description:网络情况综合工具类
 * @Author:gxj
 * @Time 2019/4/2
 */

public class NetworkUtils {

    /**
     * 获取活动网络信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    public static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * 判断网络是否可用
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @return {@code true}: 可用<br>{@code false}: 不可用
     */
    public static boolean isAvailable() {
        NetworkInfo info = getActiveNetworkInfo(BegoitMoocApplication.Companion.getContextInstance());
        return info != null && info.isAvailable();
    }

    /**
     * 判断网络是否连接
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo(BegoitMoocApplication.Companion.getContextInstance());
        return info != null && info.isConnected();
    }


    /**
     * 获取当前下载速度
     */
    public static long lastTotalRxBytes = 0;
    public static long lastTimeStamp = 0;
    public static long nowTotalRxBytes;
    public static long nowTimeStamp;
    public static long speed;
    public static long speed2;
    public static String getRxBytes() {
        try {
            lastTotalRxBytes = getTotalRxBytes();
            lastTimeStamp = System.currentTimeMillis();

            nowTotalRxBytes = getTotalRxBytes();
            nowTimeStamp = System.currentTimeMillis();
            speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
            speed2 = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 % (nowTimeStamp - lastTimeStamp));//毫秒转换

            lastTimeStamp = nowTimeStamp;
            lastTotalRxBytes = nowTotalRxBytes;
            return String.valueOf(speed) + "." + String.valueOf(speed2) + " kb/s";
        }catch (Exception e){
            e.printStackTrace();
            return "0 kb/s";
        }

    }

    public static long getTotalRxBytes() {
        return TrafficStats.getUidRxBytes(BegoitMoocApplication.Companion.getContextInstance().getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 1 :(TrafficStats.getTotalRxBytes()/1024);//转为KB
    }
}

package com.begoit.mooc.offline.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;

import com.begoit.mooc.BegoitMoocApplication;

/**
 * @Description:设备和应用基础信息的工具类
 * @Author:gxj
 * @Time 2019/2/25
 */

public class DeviceInfoUtil {
    /**
     * 获取当前本地apk的版本
     * @return
     */
    public static int getVersionCode() {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = BegoitMoocApplication.Companion.getContextInstance().getPackageManager().
                    getPackageInfo(BegoitMoocApplication.Companion.getContextInstance().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取设备sn
     * @return
     */
    public static String getDeviceSN(){
        return android.os.Build.SERIAL;
    }

    /**
     * 获取版本号名称
     * @return
     */
    public static String getVerName() {
        String verName = "";
        try {
            verName = BegoitMoocApplication.Companion.getContextInstance().getPackageManager().
                    getPackageInfo(BegoitMoocApplication.Companion.getContextInstance().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
    //获取设备屏幕宽度
    public static int getDevicesWidth(){
        DisplayMetrics dm = BegoitMoocApplication.Companion.getContextInstance().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public static int getDeviceHeight(){
        DisplayMetrics dm = BegoitMoocApplication.Companion.getContextInstance().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 根据手机分辨率从DP转成PX
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        float scale = BegoitMoocApplication.Companion.getContextInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = BegoitMoocApplication.Companion.getContextInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        float scale = BegoitMoocApplication.Companion.getContextInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue
     * @return
     */

    public static int px2sp(float pxValue) {
        final float fontScale = BegoitMoocApplication.Companion.getContextInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}

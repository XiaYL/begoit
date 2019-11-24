package com.begoit.mooc.offline.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.widget.Toast;

import com.begoit.mooc.BegoitMoocApplication;

/**
 * @Description:Toast工具类封装
 * @Author:gxj
 * @Time 2019/2/18
 */

public class ToastUtil {
    private static  Context mContext = BegoitMoocApplication.Companion.getContextInstance();
    private static  Resources mResources;
    private static  Toast toast;

    public static void showShortToast(String msg) {
        showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    public static  void showShortToast(int strRes) {
        showShortToast(mResources.getString(strRes));
    }

    public static  void showLongToast(String msg) {
        showToast(mContext, msg, Toast.LENGTH_LONG);
    }

    public static  void showLongToast(int strRes) {
        showLongToast(mResources.getString(strRes));
    }

    public static  void showToast(Context context, String msg, int duration){
        showToast(context, msg, duration, Gravity.BOTTOM);
    }
    public static  void showToast(Context context, String msg, int duration,int gravity){
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, duration);
//        toast.setGravity(gravity, 0, 0);
        toast.show();
    }
}

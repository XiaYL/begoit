package com.begoit.mooc.offline.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.begoit.mooc.offline.ui.activity.login.LoginActivity;

import java.util.ArrayList;

/**
 * @author gxj
 * @date 2019/03/05 17:12.
 * Desc：Activity 工具
 */

public class ActivityUtils {
    public static void logout(String msg) {
        if (msg != null && msg.trim().length() > 0){
            ToastUtil.showShortToast(msg);
        }
        Activity lastActivity= ActivityTaskUtils.getAppManager().currentActivity();
        if (null != lastActivity)
        {
            lastActivity.finish();
        }
        ActivityTaskUtils.getAppManager().finishAllActivity();
        Intent intent = new Intent(lastActivity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        lastActivity.startActivity(intent);
    }

    /**
     * 判断下载课程服务是否开启
     * @param context 上下文
     * @return
     */
    public static boolean isDownLoadServiceRunning(Context context) {
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals("com.begoit.mooc.offline.ui.service.DownloadCourseService")) {
                return true;
            }
        }
        return false;
    }

}

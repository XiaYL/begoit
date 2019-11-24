package com.begoit.mooc.offline.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.begoit.mooc.BegoitMoocApplication
import com.begoit.mooc.offline.event.NetChangeEvent
import com.begoit.mooc.offline.requests.HeaderConfig
import com.begoit.mooc.offline.utils.NetworkUtils
import com.begoit.mooc.offline.utils.StudyLogUploadUtil
import org.greenrobot.eventbus.EventBus

/**
 *@Description:网络切换时的业务处理
 *@Author:gxj
 *@Time 2019/5/29
 */
class NetReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        EventBus.getDefault().post(NetChangeEvent())
        if (NetworkUtils.isAvailable() && !TextUtils.isEmpty(HeaderConfig.deviceNo)) {
            StudyLogUploadUtil.upload(null)
        }
    }
}
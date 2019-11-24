package com.begoit.mooc

import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.support.multidex.MultiDexApplication
import android.text.TextUtils

import com.begoit.mooc.offline.entity.user.UserEntity
import com.begoit.mooc.offline.receiver.NetReceiver
import com.begoit.mooc.offline.utils.ActivityTaskUtils
import com.begoit.mooc.offline.utils.GsonUtil
import com.begoit.mooc.offline.utils.LogUtils
import com.begoit.mooc.offline.utils.SharedPreferencesUtils
import com.begoit.mooc.offline.utils.db.DaoManager
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import kotlin.properties.Delegates

/**
 * @Description:BegoitMoocApplication
 * @Author:gxj
 * @Time 2019/2/19
 */

class BegoitMoocApplication : MultiDexApplication() {

    private var netReceiver: NetReceiver? = null
    private val preferences by lazy { SharedPreferencesUtils(this) }

    fun setCurrentUser(user: UserEntity) {
        preferences.user = GsonUtil.getInstance().toJson(user)
    }

    fun getCurrentUser(): UserEntity? {
        return if (TextUtils.isEmpty(preferences.user)) {
            null
        } else {
            GsonUtil.getInstance().fromJson(preferences.user, UserEntity::class.java)
        }
    }

    fun getCurrentAccound(): String {
        return if (getCurrentUser() == null) {
            ""
        } else {
            getCurrentUser()!!.userAccount
        }
    }

    companion object {
        //获取Application实例
        var contextInstance: BegoitMoocApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        contextInstance = this
        /**
         * 数据库查看
         */
        Stetho.initializeWithDefaults(this)
        OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        //******************************************************
        DaoManager.getInstance().setDebug(true)
        netReceiver = NetReceiver()
        val inflater = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(netReceiver, inflater)
    }

    override fun onTerminate() {
        // 程序终止的时候执行
        LogUtils.d("onTerminate")
        if (netReceiver != null) {
            unregisterReceiver(netReceiver)
        }
        super.onTerminate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        // 配置改变时，应用程序不会被终止和重启，而是回调方法
        super.onConfigurationChanged(newConfig)
        LogUtils.d("onConfigurationChanged running...")
    }

    override fun onLowMemory() {
        // 可用于清空缓存或者释放不必要的资源
        super.onLowMemory()
        LogUtils.i("onLowMemory")
    }

    override fun onTrimMemory(level: Int) {
        // 系统认为需要减少应用程序内存开销时调用
        super.onTrimMemory(level)
        LogUtils.i("onTrimMemory level  $level")
    }
}

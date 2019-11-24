package com.begoit.mooc.offline.utils

import com.begoit.mooc.BegoitMoocApplication
import com.begoit.mooc.offline.entity.course.user_download.AppLogEntity
import com.begoit.mooc.offline.utils.db.DaoManager
import java.util.*

/**
 * @Description:用户操作日志工具类
 * @Author:gxj
 * @Time 2019/5/17
 */

object AppLogUtil {
    val TPYE_SIGN_IN = 1 //登录
    val TPYE_SIGN_OUT = 2 //退出
    val TYPE_USER_DOWNLOAD = 3 //用户下载
    val TYPE_USER_UNBIND = 4 //用户解绑

    val TYPE_COURSE_DOWNLOAD = 5 //课程下载
    val TYPE_COURSE_DELETE = 6 //课程删除
    val TYPE_LEARNING_VIDEO = 7 //视频学习
    val TYPE_LEARNING_UPLOAD = 8 //学习记录上传

    var appLogEntity: AppLogEntity? = null

    fun setLog(type: Int, vararg args: String) {
        try {
            appLogEntity = AppLogEntity()
            appLogEntity!!.id = UUID.randomUUID().toString()
            appLogEntity!!.isChange = 1
            appLogEntity!!.userAccount = BegoitMoocApplication.contextInstance.getCurrentAccound()
            appLogEntity!!.userName = BegoitMoocApplication.Companion.contextInstance?.getCurrentUser()?.userName
            appLogEntity!!.createTime = DateFormatUtil.longToString(System.currentTimeMillis())
            //插入数据库表
            when (type) {
                TPYE_SIGN_IN -> {
                    appLogEntity!!.type = "登录"
                    appLogEntity!!.content = "用户" + appLogEntity!!.userName + "登录"
                }
                TPYE_SIGN_OUT -> {
                    appLogEntity!!.type = "退出"
                    appLogEntity!!.content = "用户" + appLogEntity!!.userName + "退出登录"
                }
                TYPE_USER_DOWNLOAD -> {
                    appLogEntity!!.type = "用户下载"
                    appLogEntity!!.content = "用户" + appLogEntity!!.userName + "下载学习数据"
                }
                TYPE_USER_UNBIND -> {
                    appLogEntity!!.type = "用户解绑"
                    appLogEntity!!.content = "用户" + appLogEntity!!.userName + "解除绑定"
                }
                TYPE_COURSE_DOWNLOAD -> {
                    appLogEntity!!.type = "课程下载"
                    appLogEntity!!.content = "用户" + appLogEntity!!.userName + "下载课程" + args?.get(0)
                }
                TYPE_COURSE_DELETE -> {
                    appLogEntity!!.type = "课程删除"
                    appLogEntity!!.content = "用户" + appLogEntity!!.userName + "删除课程" + args?.get(0)
                }
                TYPE_LEARNING_VIDEO -> {
                    appLogEntity!!.type = "视频学习"
                    appLogEntity!!.content = "用户" + appLogEntity!!.userName + "学习了" + args?.get(0) + "课程下" + args?.get(1) + "视频"
                }
                TYPE_LEARNING_UPLOAD -> {
                    appLogEntity!!.type = "学习记录上传"
                    appLogEntity!!.content = "用户" + appLogEntity!!.userName + "上传学习记录"
                }
            }
            LogUtils.i(appLogEntity!!.content)
            DaoManager.getInstance().daoSession.appLogEntityDao.insertInTx(appLogEntity)
        } catch (e: Exception) {
            LogUtils.e("学习记录上传失败 ${e.message}")
            e.printStackTrace()
        }
    }
}

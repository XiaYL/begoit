package com.begoit.mooc.offline.utils

import android.content.Context
import com.begoit.mooc.BegoitMoocApplication
import com.begoit.mooc.db.AppLogEntityDao
import com.begoit.mooc.db.UserChannelEntityDao
import com.begoit.mooc.db.VideoTestLogEntityDao
import com.begoit.mooc.db.VideoTestScoreEntityDao
import com.begoit.mooc.offline.entity.course.user_download.AppLogEntity
import com.begoit.mooc.offline.entity.course.user_download.UserChannelEntity
import com.begoit.mooc.offline.entity.course.user_download.VideoTestLogEntity
import com.begoit.mooc.offline.entity.course.user_download.VideoTestScoreEntity
import com.begoit.mooc.offline.event.UploadingStudyLogsEvent
import com.begoit.mooc.offline.requests.Api
import com.begoit.mooc.offline.requests.ApiConstants
import com.begoit.mooc.offline.requests.HostType
import com.begoit.mooc.offline.utils.db.DaoManager
import com.begoit.mooc.offline.widget.LoadingProgressDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 *@Description:学习记录上传逻辑
 *@Author:gxj
 *@Time 2019/5/28
 */
object StudyLogUploadUtil {
    private var userChannelList: List<UserChannelEntity>? = null
    private var videoAndTestScoreList: List<VideoTestScoreEntity>? = null
    private var videoTestLogList: List<VideoTestLogEntity>? = null
    private var appLogList: List<AppLogEntity>? = null
    open var isDoUpload: Boolean = false

    /**
     * 上传学习记录业务处理
     */
    fun upload(context: Context?) {
        if (!isDoUpload) {
//            if (context != null) {
//                LoadingProgressDialog.showLoading(context, "学习记录上传中...")
//            }
            isDoUpload = true
            EventBus.getDefault().post(UploadingStudyLogsEvent(isDoUpload, 0))
            val requestBody = getNewData()
            if (requestBody != null) {
                doLoad(requestBody).subscribe({ s ->
                    LogUtils.i("学习记录上传 $s")
                    var json = JSONObject(s)
                    if (json.optInt("status") == ApiConstants.SUCCESSCODE) {
                        fomartData()
//                        LoadingProgressDialog.stopLoading()
                        isDoUpload = false
                        EventBus.getDefault().post(UploadingStudyLogsEvent(isDoUpload, 1))
                        ToastUtil.showShortToast("学习记录上传成功")
                    } else {
//                        LoadingProgressDialog.stopLoading()
                        isDoUpload = false
                        EventBus.getDefault().post(UploadingStudyLogsEvent(isDoUpload, 2))
                        ToastUtil.showShortToast("${json.opt("error")}")
                    }
                }, { t: Throwable? ->
//                    LoadingProgressDialog.stopLoading()
                    isDoUpload = false
                    EventBus.getDefault().post(UploadingStudyLogsEvent(isDoUpload, 2))
                    ToastUtil.showShortToast("学习记录上传失败")
                })
            } else {
//                LoadingProgressDialog.stopLoading()
                ToastUtil.showShortToast("学习记录上传成功")
                isDoUpload = false
                EventBus.getDefault().post(UploadingStudyLogsEvent(isDoUpload, 1))
            }
        } else {
            ToastUtil.showShortToast("学习记录正在上传，请稍等...")
        }
    }

    /**
     * 执行上传
     */
    private fun doLoad(requestBody: RequestBody): Observable<String> {
        return Api.getDefault(HostType.TYPE_APP)
                .courseStudy(requestBody)//
                .map { responseBody -> responseBody.string() }.subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 获取有变动的数据
     */
    private fun getNewData(): RequestBody? {
        userChannelList = DaoManager.getInstance().daoSession.userChannelEntityDao.queryBuilder()
                .where(UserChannelEntityDao.Properties.IsChange.`in`(1)).list()
        videoAndTestScoreList = DaoManager.getInstance().daoSession.videoTestScoreEntityDao.queryBuilder()
                .where(VideoTestScoreEntityDao.Properties.IsChange.`in`(1)).list()
        videoTestLogList = DaoManager.getInstance().daoSession.videoTestLogEntityDao.queryBuilder()
                .where(VideoTestLogEntityDao.Properties.IsChange.`in`(1)).list()
        appLogList = DaoManager.getInstance().daoSession.appLogEntityDao.queryBuilder()
                .where(AppLogEntityDao.Properties.IsChange.`in`(1)).list()
        return if ((userChannelList == null || userChannelList!!.isEmpty()) && (videoAndTestScoreList == null || videoAndTestScoreList!!.isEmpty())
                && (videoTestLogList == null || videoTestLogList!!.isEmpty()) && (appLogList == null || appLogList!!.isEmpty())) {
            null
        } else {
            val qryMap = mutableMapOf("channeleid" to userChannelList, "videoandtestresult" to videoAndTestScoreList
                    , "videoTestLog" to videoTestLogList, "log" to appLogList)
            RequestBody.create(okhttp3.MediaType.parse("Content-Type:application/json"), GsonUtil.getInstance().toJson(qryMap))
        }
    }

    /**
     * 学习记录上传成功，将数据变动状态修改为未变动
     */
    private fun fomartData() {
        if (userChannelList != null && userChannelList!!.isNotEmpty()) {
            for (item in userChannelList!!) {
                item.isChange = 0
            }
            DaoManager.getInstance().daoSession.userChannelEntityDao.insertOrReplaceInTx(userChannelList)
        }
        if (videoAndTestScoreList != null && videoAndTestScoreList!!.isNotEmpty()) {
            for (item in videoAndTestScoreList!!) {
                item.isChange = 0
            }
            DaoManager.getInstance().daoSession.videoTestScoreEntityDao.insertOrReplaceInTx(videoAndTestScoreList)
        }
        if (videoTestLogList != null && videoTestLogList!!.isNotEmpty()) {
            for (item in videoTestLogList!!) {
                item.isChange = 0
            }
            DaoManager.getInstance().daoSession.videoTestLogEntityDao.insertOrReplaceInTx(videoTestLogList)
        }
        if (appLogList != null && appLogList!!.isNotEmpty()) {
            for (item in appLogList!!) {
                item.isChange = 0
            }
            DaoManager.getInstance().daoSession.appLogEntityDao.insertOrReplaceInTx(appLogList)
        }
    }
}
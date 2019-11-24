package com.begoit.mooc.offline.ui.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.begoit.mooc.db.CourseDetailFilesEntityDao
import com.begoit.mooc.db.CourseDownLoadedEntityDao
import com.begoit.mooc.offline.entity.course.user_download.CourseDownLoadedEntity
import com.begoit.mooc.offline.event.NetChangeEvent
import com.begoit.mooc.offline.utils.AppLogUtil
import com.begoit.mooc.offline.utils.LogUtils
import com.begoit.mooc.offline.utils.NetworkUtils
import com.begoit.mooc.offline.utils.db.DaoManager
import com.begoit.mooc.offline.utils.download.DownloadInfo
import com.begoit.mooc.offline.utils.download.downloadwithlimit.DownloadLimitManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 *@Description:课程下载服务，处理具体的课程下载业务，如维护下载队列下载顺序，调度下载器
 *@Author:gxj
 *@Time 2019/6/18
 */
class DownloadCourseService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.i("DownloadCourseService onStartCommand")
        LogUtils.i("DownloadCourseService 下载器状态; ${DownloadLimitManager.mDownloadLimitManager}")
        if (DownloadLimitManager.mDownloadLimitManager == null) {
            restartDownloadCourse()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 网络切换事件执行
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: NetChangeEvent) {
        if (NetworkUtils.isConnected()) {
            restartDownloadCourse()
        }
    }

    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
        LogUtils.i("DownloadCourseService onCreate")
    }

    override fun onBind(intent: Intent?): IBinder {
        LogUtils.i("DownloadCourseService onBind")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        LogUtils.i("DownloadCourseService onDestroy")
    }

    private fun restartDownloadCourse() {
        var downLoadedEntities = DaoManager.getInstance().daoSession.courseDownLoadedEntityDao
                .queryBuilder().list() ?: return
        var forDownloadEntities: ArrayList<CourseDownLoadedEntity> = ArrayList()
        if (downLoadedEntities != null && downLoadedEntities.size > 0) {

            downLoadedEntities.filterTo(forDownloadEntities) { it.courseDownloadedFilesCound != it.courseFilesCount }

            for (item in forDownloadEntities) {
                var courseFilesEntity = DaoManager.getInstance().daoSession.courseDetailFilesEntityDao.queryBuilder()
                        .where(CourseDetailFilesEntityDao.Properties.Id.eq(item.courseId)).build().unique()
                if (courseFilesEntity != null && courseFilesEntity.getFileInformation().isNotEmpty()) {
                    var mCourseDownLoadedEntity = DaoManager.getInstance().daoSession.courseDownLoadedEntityDao
                            .queryBuilder().where(CourseDownLoadedEntityDao.Properties.CourseId.eq(courseFilesEntity.id)).build().unique()
                    DownloadLimitManager.getInstance().removeCourse(mCourseDownLoadedEntity.courseId)
                    if (mCourseDownLoadedEntity != null) {
                        mCourseDownLoadedEntity.courseId = courseFilesEntity.id
                        mCourseDownLoadedEntity.isDownloadError = 0
                        mCourseDownLoadedEntity.courseName = courseFilesEntity.channelName
                        mCourseDownLoadedEntity.courseDownloadedFilesCound = 0
                        mCourseDownLoadedEntity.total = 0
                        mCourseDownLoadedEntity.progress = 0
                    }
                    //更新数据库内已下载课程，避免重复下载出现的未知问题
                    DaoManager.getInstance().daoSession.courseDownLoadedEntityDao.insertOrReplaceInTx(mCourseDownLoadedEntity)
                    for (it in courseFilesEntity.getFileInformation()) {
                        DownloadLimitManager.getInstance().download(it)
                    }
                }
            }
        }
    }

    //课程下载状态通知
    var mCourseDownLoadedEntity: CourseDownLoadedEntity? = null

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(info: DownloadInfo) {
        if (info.downloadStatus == DownloadInfo.DOWNLOAD) {
            return
        }
        //数据库已下载课程表格对应对象，用于标识课程下载注册状态，
        mCourseDownLoadedEntity = DaoManager.getInstance().daoSession.courseDownLoadedEntityDao
                .queryBuilder().where(CourseDownLoadedEntityDao.Properties.CourseId.eq(info.channelId)).build().unique()
        //如果从数据库获取下载对象为空或者下载已经标识出错的直接略过
        mCourseDownLoadedEntity?.run {
            if (info.downloadStatus == DownloadInfo.DOWNLOAD_ERROR) {
                LogUtils.e("课程 $courseName 文件下载出错")
                DownloadLimitManager.getInstance().removeCourse(courseId)
                if (isDownloadError != 1) {
                    isDownloadError = 1
                    isAddedFile = true
                    DaoManager.getInstance().daoSession.courseDownLoadedEntityDao.updateInTx(this)
                    EventBus.getDefault().post(this)
                }
                return
            }

            if (info.downloadStatus == DownloadInfo.DOWNLOAD) {
                isAddedFile = false
                total += info.len
                EventBus.getDefault().post(this)
                return
            }

            if (info.downloadStatus == DownloadInfo.DOWNLOAD_OVER) {
                courseDownloadedFilesCound += 1
                if (courseDownloadedFilesCound > courseFilesCount) {
                    isDownloadError = 1
                }
                DaoManager.getInstance().daoSession.courseDownLoadedEntityDao.updateInTx(this)
                isAddedFile = true
                EventBus.getDefault().post(this)
                LogUtils.d("课程 $courseName  ${info.fileName} 文件下载成功 $courseDownloadedFilesCound / $courseFilesCount")
                if (courseDownloadedFilesCound == courseFilesCount) {
                    AppLogUtil.setLog(AppLogUtil.TYPE_COURSE_DOWNLOAD, courseName)
                }
            }
        }
        LogUtils.d("onEventMainThread : " + info.downloadStatus + "  " + info.fileName + " " + info.progress)
    }

}
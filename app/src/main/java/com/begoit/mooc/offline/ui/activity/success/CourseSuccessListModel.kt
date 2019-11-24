package com.begoit.mooc.offline.ui.activity.success

import com.begoit.mooc.BegoitMoocApplication
import com.begoit.mooc.db.CourseDetailFilesEntityDao
import com.begoit.mooc.db.UserChannelEntityDao
import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesEntity
import com.begoit.mooc.offline.entity.course.user_download.UserChannelEntity
import com.begoit.mooc.offline.utils.db.DaoManager

/**
 *@Description:成绩列表数据
 *@Author:gxj
 *@Time 2019/5/22
 */
class CourseSuccessListModel: CourseSuccessListContract.model {
    /**
     * 获取用户已注册的课程列表
     */
    var channelList: ArrayList<UserChannelEntity>? = null
    override fun getSuccessList(): List<UserChannelEntity>? {
        channelList = DaoManager.getInstance().daoSession.userChannelEntityDao.queryBuilder()
                .where(UserChannelEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.Companion.contextInstance.getCurrentAccound())).list() as ArrayList<UserChannelEntity>?
        if (channelList != null && channelList!!.isNotEmpty()){
            channelList!!
                    .filterNot { isDownload(it?.channelId) }
                    .forEach { channelList!! -= it }
        }
        return channelList
    }

    override fun getCourseSuccess(channelId: String?): List<UserChannelEntity>?{
        return DaoManager.getInstance().daoSession.userChannelEntityDao.queryBuilder()
                .where(UserChannelEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.Companion.contextInstance.getCurrentAccound()),
                        UserChannelEntityDao.Properties.ChannelId.eq(channelId)).list() as ArrayList<UserChannelEntity>?
    }

    var courseDetailFilesEntity: CourseDetailFilesEntity? = null
    fun isDownload(channelId: String) : Boolean{
        courseDetailFilesEntity = DaoManager.getInstance().daoSession.courseDetailFilesEntityDao.queryBuilder()
                .where(CourseDetailFilesEntityDao.Properties.Id.eq(channelId)).unique()
        return courseDetailFilesEntity != null

    }
}
package com.begoit.mooc.offline.utils

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import com.begoit.mooc.BegoitMoocApplication
import com.begoit.mooc.db.CourseDownLoadedEntityDao
import com.begoit.mooc.db.UserChannelEntityDao
import com.begoit.mooc.offline.entity.course.course_list.CourseListItemEntity
import com.begoit.mooc.offline.entity.course.user_download.CourseDownLoadedEntity
import com.begoit.mooc.offline.entity.course.user_download.UserChannelEntity
import com.begoit.mooc.offline.utils.db.DaoManager
import java.text.NumberFormat

/**
 *@Description:数字格式化
 *@Author:gxj
 *@Time 2019/5/16
 */
class CourseComputeUtil {
    companion object {
        @SuppressLint("NewApi")
                /**
                 * 保留两位小数
                 */
        fun hasTwoLength(number: Double): String {
            val df = DecimalFormat("#.00")

            return NumberFormat.getInstance().format(df.format(number).toDouble())
        }

        /**
         * 计算视频观看进度
         */
        private var userChannelEntity: UserChannelEntity? = null

        fun computeFinishVideo(channelId: String): String {
            userChannelEntity = DaoManager.getInstance().daoSession.userChannelEntityDao.queryBuilder()
                    .where(UserChannelEntityDao.Properties.ChannelId.eq(channelId),
                            UserChannelEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.contextInstance.getCurrentAccound())).unique()
            if (userChannelEntity == null) {
                return "未学习"
            }
            if (userChannelEntity!!.videoFinishScore <= 0 || userChannelEntity!!.videoFinishSumCount <= 0) {
                return "未学习"
            }
            return if (userChannelEntity?.videoFinishScore == userChannelEntity?.videoFinishSumCount) {
                "已学完"
            } else "已学习" + CourseComputeUtil.hasTwoLength((userChannelEntity?.videoFinishScore)!!.toDouble() / userChannelEntity?.videoFinishSumCount!!.toDouble() * 100) + "%"
        }

        private var downloadingCourses: ArrayList<CourseDownLoadedEntity>? = null
        fun computeDoloadingCourseCount(): Int {
            downloadingCourses = DaoManager.getInstance().daoSession.courseDownLoadedEntityDao
                    .queryBuilder().list() as ArrayList<CourseDownLoadedEntity>? ?: return 0
            downloadingCourses!!
                    .filter { it.courseDownloadedFilesCound == it.courseFilesCount }
                    .forEach { downloadingCourses!!.remove(it) }

            return downloadingCourses!!.size
        }

    }

}
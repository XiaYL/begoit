package com.begoit.mooc.offline.ui.adapter

import com.begoit.mooc.db.CourseDetailFilesEntityDao
import com.begoit.mooc.offline.R
import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesEntity
import com.begoit.mooc.offline.entity.course.user_download.UserChannelEntity
import com.begoit.mooc.offline.entity.kindMenu.MenuItemEntity
import com.begoit.mooc.offline.utils.ImagePlaceHolderUtil
import com.begoit.mooc.offline.utils.CourseComputeUtil
import com.begoit.mooc.offline.utils.CourseScoreUtil
import com.begoit.mooc.offline.utils.db.DaoManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *@Description: 成绩列表适配器
 *@Author:gxj
 *@Time 2019/5/22
 */
class CourseSuccessListAdapter(data: List<UserChannelEntity>?) : BaseQuickAdapter<UserChannelEntity, BaseViewHolder>(R.layout.item_success_list, data) {
    private var courseEntity: CourseDetailFilesEntity? = null
    override fun convert(helper: BaseViewHolder?, item: UserChannelEntity?) {
        courseEntity = DaoManager.getInstance().daoSession.courseDetailFilesEntityDao.queryBuilder()
                .where(CourseDetailFilesEntityDao.Properties.Id.eq(item?.channelId)).unique()

        helper?.setImageResource(R.id.iv_icon, ImagePlaceHolderUtil.getRandomImgForSuccess(getItemPosition(item!!)))
        helper?.setText(R.id.tv_channel_name, courseEntity?.channelName)
        helper?.setText(R.id.tv_sum_score, CourseScoreUtil.getSumScore(courseEntity!!, item!!))
        helper?.setText(R.id.tv_video_finish, "视频得分" + courseEntity?.videoPercent + "%")
        helper?.setText(R.id.tv_video_finish_score, item?.videoFinishScore.toString())
        helper?.setText(R.id.tv_exchange, "讨论得分" + courseEntity?.discussionPercent + "%")
        helper?.setText(R.id.tv_exchange_score, item?.exchangeScore.toString())
        helper?.setText(R.id.tv_video_test, "测试得分" + courseEntity?.videoTestPercent + "%")
        helper?.setText(R.id.tv_video_test_score, item?.videoTestScore.toString())
        helper?.setText(R.id.tv_homework, "作业得分" + courseEntity?.homeworkPercent + "%")
        helper?.setText(R.id.tv_homework_score, item?.homeworkScore.toString())
        helper?.setText(R.id.tv_teacher, "教师调节分")
        helper?.setText(R.id.tv_teacher_score, courseEntity?.teacherScore.toString())
        helper?.setText(R.id.tv_exam, "考试得分" + courseEntity?.finalTestPercent + "%")
        helper?.setText(R.id.tv_exam_score, item?.examScore.toString())
    }

    private fun getItemPosition(item: UserChannelEntity?): Int {
        return if (item != null && mData != null && !mData.isEmpty()) mData.indexOf(item) else -1
    }
}
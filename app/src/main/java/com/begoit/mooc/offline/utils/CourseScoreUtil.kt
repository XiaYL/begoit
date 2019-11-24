package com.begoit.mooc.offline.utils

import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesEntity
import com.begoit.mooc.offline.entity.course.user_download.UserChannelEntity

/**
 *@Description:课程分数计算
 *@Author:gxj
 *@Time 2019/5/24
 */
class CourseScoreUtil {
    companion object {
        fun getSumScore(courseDetailFilesEntity: CourseDetailFilesEntity, courseRegistedEntity: UserChannelEntity): String{
            //平时总成绩
            val normalSumScore = CourseComputeUtil.hasTwoLength(
                    (computeEndScore(courseRegistedEntity.videoFinishScore, courseRegistedEntity.videoFinishSumCount, courseDetailFilesEntity.videoPercent)//视频得分/视频总分*视频百分比

                            + computeEndScore(courseRegistedEntity.videoTestScore , courseRegistedEntity.videoTestSumScore , courseDetailFilesEntity.videoTestPercent)//测试题得分/测试题总分*测试题百分比


                            + computeEndScore(courseRegistedEntity.homeworkScore.toInt(), courseRegistedEntity.homeworkSumScore, courseDetailFilesEntity.homeworkPercent)//作业得分/作业总分*作业百分比

                            + courseRegistedEntity.exchangeScore.toFloat()//讨论得分

                            + courseDetailFilesEntity.teacherScore.toFloat()).toDouble())//教师调分

            //总分计算(平时总成绩+(结业考试得分/100*结业考试百分比))
            courseRegistedEntity.sumScore = if (courseRegistedEntity.examStatus == -1)
                0f.toString()
            else
                CourseComputeUtil.hasTwoLength((normalSumScore.toFloat() + computeEndScore(courseRegistedEntity.examScore, 100, courseDetailFilesEntity.finalTestPercent)).toDouble())
            //如果最后总分大于100.00取100.00
            //SUMSCORE = SUMSCORE > 100.00?100.00:SUMSCORE;
            courseRegistedEntity.sumScore = if (courseRegistedEntity.sumScore.toFloat() > 100.00f) 100.00f.toString() else courseRegistedEntity.sumScore

            return courseRegistedEntity.sumScore
        }

        /**
         * 根据占比情况计算最终得分
         * @param hasScore  得分
         * @param sumScore  总分
         * @param percent   占比
         * @return
         */
        private fun computeEndScore(hasScore: Int, sumScore: Int, percent: Int): Float {
            return if (hasScore == 0 || sumScore == 0 || percent == 0) {
                0.0f
            } else (hasScore.toFloat() / sumScore.toFloat() * percent.toFloat())
        }
    }
}
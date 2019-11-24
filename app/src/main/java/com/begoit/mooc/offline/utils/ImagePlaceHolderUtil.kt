package com.begoit.mooc.offline.utils

import com.begoit.mooc.db.CourseListItemPreImgPlaceHolderEntityDao
import com.begoit.mooc.offline.R
import com.begoit.mooc.offline.entity.course.course_list.CourseListItemPreImgPlaceHolderEntity
import com.begoit.mooc.offline.utils.db.DaoManager
import java.util.*

/**
 *@Description:图片占位图随机匹配
 *@Author:gxj
 *@Time 2019/6/17
 */
object ImagePlaceHolderUtil {
    //课程列表占位图
    private val imgs = intArrayOf(R.drawable.placeholder1, R.drawable.placeholder2, R.drawable.placeholder3, R.drawable.placeholder4, R.drawable.placeholder5, R.drawable.placeholder6, R.drawable.placeholder7, R.drawable.placeholder8, R.drawable.placeholder9, R.drawable.placeholder10, R.drawable.placeholder11, R.drawable.placeholder12)
    //成绩列表小图标
    private val imgsForSuccess = intArrayOf(R.mipmap.ic_success_yellow, R.mipmap.ic_success_blue, R.mipmap.ic_success_grenn)
    //主页侧边栏类型图标
    private val imgsForLeftMenuIcon = intArrayOf(R.mipmap.ic_left_menu_3, R.mipmap.ic_left_menu_2, R.mipmap.ic_left_menu_1)

    private val random = Random()

    private var entity: CourseListItemPreImgPlaceHolderEntity? = null
    //为无预览图片的课程设置一个随机默认图片
    private fun setPlaceholderPreImg(courseId: String, imgId: Int) {
        entity = CourseListItemPreImgPlaceHolderEntity(courseId, imgId)
        DaoManager.getInstance().daoSession.courseListItemPreImgPlaceHolderEntityDao
                .insertOrReplace(entity)
    }

    //获取无预览图片的课程随机默认图片
    private var placeholderPreImgId: Int = 0

    fun getPlaceholderPreImg(courseId: String): Int {
        entity = DaoManager.getInstance().daoSession.courseListItemPreImgPlaceHolderEntityDao.queryBuilder()
                .where(CourseListItemPreImgPlaceHolderEntityDao.Properties.Id.eq(courseId)).build().unique()
        if (entity != null) {
            placeholderPreImgId = entity!!.preImgFileid
        } else {
            placeholderPreImgId = ImagePlaceHolderUtil.getPlaceImg()
            setPlaceholderPreImg(courseId, placeholderPreImgId)
        }
        return placeholderPreImgId
    }

    //课程列表占位图
    fun getPlaceImg(): Int {
        val i = random.nextInt(imgs.size)
        return imgs[i]
    }

    //成绩列表小图标
    fun getRandomImgForSuccess(position: Int): Int {
        val i: Int
        if (position == -1) {
            i = 0
        } else {
            i = position % imgsForSuccess.size
        }
        return imgsForSuccess[i]
    }

    //主页侧边栏类型图标
    fun getLeftMenuIcon(position: Int): Int {
        val i: Int
        if (position == -1) {
            i = 0
        } else {
            i = position % imgsForLeftMenuIcon.size
        }
        return imgsForLeftMenuIcon[i]
    }
}
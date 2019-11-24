package com.begoit.mooc.offline.ui.fragment.baseCourseKind

import com.begoit.mooc.offline.base.BasePresenter
import com.begoit.mooc.offline.base.IBaseModel
import com.begoit.mooc.offline.base.IBaseView
import com.begoit.mooc.offline.entity.course.course_list.CourseListItemEntity
import io.reactivex.Observable

/**
 * @Description:分类课程列表综合协议
 * @Author:gxj
 * @Time 2019/3/4
 */

interface KindCourseContract {
    interface View : IBaseView {
        fun showCourses(courseList: List<CourseListItemEntity>)
        fun showErrorView(icon: Int, msg: String)
    }

    interface Model : IBaseModel {
        val remoteRecommendCourses: Observable<String>
        fun getCourses(params: Map<String, String>): Observable<String>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getCourses(params: Map<String, String>)
        abstract fun getRemoteRecommendCourses()
    }
}

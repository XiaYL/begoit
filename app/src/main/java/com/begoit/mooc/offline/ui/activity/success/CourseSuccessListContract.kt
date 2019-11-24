package com.begoit.mooc.offline.ui.activity.success

import com.begoit.mooc.offline.base.BasePresenter
import com.begoit.mooc.offline.base.IBaseModel
import com.begoit.mooc.offline.base.IBaseView
import com.begoit.mooc.offline.entity.course.user_download.UserChannelEntity

/**
 *@Description:成绩列表协议类
 *@Author:gxj
 *@Time 2019/5/22
 */
interface CourseSuccessListContract {
    interface view: IBaseView{
        fun showList(channelEntityList: List<UserChannelEntity>?)
        fun showErrorView(icon: Int, msg: String)
    }

    interface model: IBaseModel{
        fun getSuccessList(): List<UserChannelEntity>?
        fun getCourseSuccess(channelId: String?): List<UserChannelEntity>?
    }

    abstract class Presenter: BasePresenter<view, model>() {
        abstract fun getSuccessList(channelId: String?)
    }
}
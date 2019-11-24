package com.begoit.mooc.offline.ui.activity.success

/**
 *@Description:成绩列表业务
 *@Author:gxj
 *@Time 2019/5/22
 */
class CourseSuccessListPresenter: CourseSuccessListContract.Presenter() {
    override fun getSuccessList(channelId: String?) {
        mView.showList(if (channelId == null) mModel.getSuccessList() else mModel.getCourseSuccess(channelId))
    }

}
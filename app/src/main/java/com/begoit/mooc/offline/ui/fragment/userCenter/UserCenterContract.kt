package com.begoit.mooc.offline.ui.fragment.userCenter

import com.begoit.mooc.offline.base.BasePresenter
import com.begoit.mooc.offline.base.IBaseModel
import com.begoit.mooc.offline.base.IBaseView
import io.reactivex.Observable

/**
 *@Description:个人中心综合合约类
 *@Author:gxj
 *@Time 2019/6/11
 */
interface UserCenterContract {
    interface View : IBaseView {
        fun unbindStatus(status: Int)
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun unbind()
    }

    interface Model : IBaseModel {
        fun doUnbind(): Observable<String>
    }
}
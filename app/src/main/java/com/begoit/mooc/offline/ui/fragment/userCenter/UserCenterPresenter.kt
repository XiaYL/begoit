package com.begoit.mooc.offline.ui.fragment.userCenter

import com.begoit.mooc.BegoitMoocApplication
import com.begoit.mooc.offline.R
import com.begoit.mooc.offline.utils.ToastUtil
import org.json.JSONObject

/**
 *@Description:个人中心业务处理
 *@Author:gxj
 *@Time 2019/6/11
 */
class UserCenterPresenter : UserCenterContract.Presenter() {
    override fun unbind() {
        mView.showLoading()
        mRxManage.add(mModel.doUnbind().subscribe({ s ->
            mView.cancelLoading()
            val jsonObject = JSONObject(s)
            when (jsonObject.optInt("status")) {
                1 or 3 -> mView.unbindStatus(jsonObject.optInt("status"))
                else -> {
                    ToastUtil.showShortToast(jsonObject.getString("error"))
                    mView.unbindStatus(jsonObject.optInt("status"))
                }
            }
        }, { e ->
            e.printStackTrace()
            mView.unbindStatus(0)
            ToastUtil.showShortToast("解绑失败 ${e.message}")
        }))
    }
}
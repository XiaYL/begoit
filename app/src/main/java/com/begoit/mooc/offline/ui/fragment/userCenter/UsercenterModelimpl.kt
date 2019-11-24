package com.begoit.mooc.offline.ui.fragment.userCenter

import com.begoit.mooc.BegoitMoocApplication
import com.begoit.mooc.offline.requests.Api
import com.begoit.mooc.offline.requests.HostType
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *@Description:个人中心数据处理
 *@Author:gxj
 *@Time 2019/6/11
 */
class UsercenterModelimpl : UserCenterContract.Model {
    //执行解绑
    override fun doUnbind(): Observable<String> {
        var params = HashMap<String, String>()
        params.put("eids", BegoitMoocApplication.contextInstance.getCurrentAccound())
        return Api.getDefault(HostType.TYPE_APP)
                .unbind(params)
                .map { responseBody -> responseBody.string() }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
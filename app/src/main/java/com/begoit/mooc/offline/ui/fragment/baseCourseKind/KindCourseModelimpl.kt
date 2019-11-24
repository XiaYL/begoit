package com.begoit.mooc.offline.ui.fragment.baseCourseKind

import com.begoit.mooc.offline.requests.Api
import com.begoit.mooc.offline.requests.HostType

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @Description:分类课程数据实现
 * @Author:gxj
 * @Time 2019/3/4
 */

class KindCourseModelimpl : KindCourseContract.Model {

    override val remoteRecommendCourses: io.reactivex.Observable<String>
        get() = Api.getDefault(HostType.TYPE_APP)
                .channelRecommendList()
                .map { responseBody -> responseBody.string() }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    override fun getCourses(params: Map<String, String>): io.reactivex.Observable<String> {
        return Api.getDefault(HostType.TYPE_APP)
                .channelList(params)
                .map { responseBody -> responseBody.string() }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}

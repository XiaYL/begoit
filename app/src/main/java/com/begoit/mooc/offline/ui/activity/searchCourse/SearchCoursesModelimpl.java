package com.begoit.mooc.offline.ui.activity.searchCourse;

import com.begoit.mooc.offline.requests.Api;
import com.begoit.mooc.offline.requests.HostType;

import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Description:搜索课程数据处理
 * @Author:gxj
 * @Time 2019/3/8
 */

public class SearchCoursesModelimpl implements SearchCourseContract.Model {
    @Override
    public Observable<String> doSearch(Map<String, String> params) {
        return Api.getDefault(HostType.TYPE_APP)
                .channelList(params)
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        return responseBody.string();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

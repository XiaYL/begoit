package com.begoit.mooc.offline.ui.activity.searchCourse;

import android.text.TextUtils;

import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.requests.ApiConstants;
import com.begoit.mooc.offline.entity.course.course_list.CourseListData;
import com.begoit.mooc.offline.utils.GsonUtil;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @Description:搜索课程业务处理
 * @Author:gxj
 * @Time 2019/3/8
 */

public class SearchCoursesPresenter extends SearchCourseContract.Presenter {
    private CourseListData courseListData;
    @Override
    void doSearch(Map<String,String> params) {
        mRxManage.add(mModel.doSearch(params).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mView.cancelLoading();
                courseListData = GsonUtil.getInstance().fromJson(s,CourseListData.class);
                if (courseListData.getStatus() == ApiConstants.SUCCESSCODE){
                    mView.showCourse(courseListData.data);
                }else {
                    if (!TextUtils.isEmpty(courseListData.getError())){
                        mView.showErrorView(R.mipmap.ic_empty_search, courseListData.getError());
                    }else{
                        mView.showErrorView(R.mipmap.ic_empty_search,
                                BegoitMoocApplication.Companion.getContextInstance().getText(R.string.error_server).toString());
                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.cancelLoading();
                mView.showErrorView(R.mipmap.ic_no_net,
                        BegoitMoocApplication.Companion.getContextInstance().getText(R.string.error_net).toString());
            }
        }));
    }
}

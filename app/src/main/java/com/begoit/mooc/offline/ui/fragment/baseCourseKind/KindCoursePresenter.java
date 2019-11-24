package com.begoit.mooc.offline.ui.fragment.baseCourseKind;

import android.text.TextUtils;

import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.requests.ApiConstants;
import com.begoit.mooc.offline.entity.course.course_list.CourseListData;
import com.begoit.mooc.offline.utils.GsonUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @Description:
 * @Author:gxj
 * @Time 2019/3/4
 */

public class KindCoursePresenter extends KindCourseContract.Presenter {
    private CourseListData courseListData;
    @Override
    public void getCourses(@NotNull Map<String, String> params) {
        mRxManage.add(mModel.getCourses(params).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mView.cancelLoading();
                courseListData = GsonUtil.getInstance().fromJson(s,CourseListData.class);
                if (courseListData.getStatus() == ApiConstants.SUCCESSCODE){
                    mView.showCourses(courseListData.data);
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

    @Override
    public void getRemoteRecommendCourses() {
        mRxManage.add(mModel.getRemoteRecommendCourses().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mView.cancelLoading();
                courseListData = GsonUtil.getInstance().fromJson(s,CourseListData.class);
                if (courseListData.getStatus() == ApiConstants.SUCCESSCODE){
                    mView.showCourses(courseListData.data);
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

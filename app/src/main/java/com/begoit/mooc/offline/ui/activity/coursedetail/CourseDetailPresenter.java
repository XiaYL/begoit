package com.begoit.mooc.offline.ui.activity.coursedetail;

import android.text.TextUtils;

import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesData;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesEntity;
import com.begoit.mooc.offline.requests.ApiConstants;
import com.begoit.mooc.offline.entity.course.course_detail.CourseDetailData;
import com.begoit.mooc.offline.entity.course.course_detail.CourseDetailEntity;
import com.begoit.mooc.offline.utils.CourseFormatUtils;
import com.begoit.mooc.offline.utils.GsonUtil;
import com.begoit.mooc.offline.utils.ToastUtil;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @Description:课程详情Presenter
 * @Author:gxj
 * @Time 2019/3/14
 */

public class CourseDetailPresenter extends CourseDetailContract.Preaenter {
    private CourseDetailData responseData;
    private CourseDetailEntity courseDetailEntity;
    @Override
    void getCourseDetail(Map<String,String> params) {
        courseDetailEntity = mModel.getLocalCourseDetail(params.get("channelId"));
        if (courseDetailEntity != null){
            mView.showCourseDetail(courseDetailEntity,true);
            mView.cancelLoading();
            return;
        }

        mRxManage.add(mModel.getCourseDetail(params).subscribe(new Consumer<String>() {
             @Override
             public void accept(String s) throws Exception {
                 responseData = GsonUtil.getInstance().fromJson(s,CourseDetailData.class);
                 if (responseData != null){
                     if (responseData.getStatus() == ApiConstants.SUCCESSCODE){
                         if (responseData.data != null) {
                             mView.showCourseDetail(responseData.data,false);
                             mModel.setCourseDetailToDb(responseData.data);
                         }else {
                             mView.showErrorView(R.mipmap.ic_empty_search,
                                     BegoitMoocApplication.Companion.getContextInstance().getText(R.string.error_data_error).toString());
                         }
                     }else {
                         mView.showErrorView(R.mipmap.ic_empty_search,
                                 TextUtils.isEmpty(responseData.getError())
                                         ? BegoitMoocApplication.Companion.getContextInstance().getText(R.string.error_server).toString(): responseData.getError());
                     }
                 }else{
                     mView.showErrorView(R.mipmap.ic_empty_search,
                             BegoitMoocApplication.Companion.getContextInstance().getText(R.string.error_server).toString());
                 }
                 mView.cancelLoading();
             }
         }, new Consumer<Throwable>() {
             @Override
             public void accept(Throwable throwable) throws Exception {
                 mView.showErrorView(R.mipmap.ic_no_net,
                         BegoitMoocApplication.Companion.getContextInstance().getText(R.string.error_net).toString());
                 mView.cancelLoading();
             }
         }));
    }

    private CourseDetailFilesData responseFilesData;//接收课程文件相关响应数据
    private boolean isSuccess;//接收数据库操作状态，成功表示可以进入下载逻辑
    @Override
    void getCourseDetailFiles(Map<String, String> params) {
        mRxManage.add(mModel.getCourseDetailFiles(params).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                responseFilesData = CourseFormatUtils.addPosition(GsonUtil.getInstance().fromJson(s,CourseDetailFilesData.class));
                mView.cancelLoading();
                if (responseFilesData != null){
                    if (responseFilesData.getStatus() == ApiConstants.SUCCESSCODE){
                        if (responseFilesData.data != null) {
                            isSuccess = mModel.setCourseDetailFilesToDb(responseFilesData.data);
                            if (isSuccess){
                                mView.startDownLoadFiles(responseFilesData.data);
                            }else {
                                ToastUtil.showShortToast("数据存储失败");
                            }
                        }else {
                            ToastUtil.showShortToast("服务器数据异常");
                        }
                    }else {
                        ToastUtil.showShortToast(responseFilesData.getError());
                    }
                }else{
                    ToastUtil.showShortToast("服务器数据异常,请重试");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtil.showShortToast(throwable.getMessage());
                mView.cancelLoading();
            }
        }));
    }

    @Override
    void registCourse(String courseId) {
       mView.registStatus(mModel.registCourse(courseId));
    }

    @Override
    boolean isCoureRegist(String courseId) {
        return mModel.isCoureRegist(courseId);
    }

    @Override
    void reviewRegistedCourse(String coursedId) {
        mModel.reviewRegistedCourse(coursedId);
    }

    @Override
    boolean isCanRigist(String courseId) {
        return mModel.isCanRigist(courseId);
    }
}

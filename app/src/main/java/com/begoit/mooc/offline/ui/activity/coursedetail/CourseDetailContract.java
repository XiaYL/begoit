package com.begoit.mooc.offline.ui.activity.coursedetail;

import com.begoit.mooc.offline.base.BasePresenter;
import com.begoit.mooc.offline.base.IBaseModel;
import com.begoit.mooc.offline.base.IBaseView;
import com.begoit.mooc.offline.entity.course.course_detail.CourseDetailEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesEntity;
import java.util.Map;
import io.reactivex.Observable;

/**
 * @Description:课程详情综合协议
 * @Author:gxj
 * @Time 2019/3/11
 */

public interface CourseDetailContract {
    interface View extends IBaseView {
        void showCourseDetail(CourseDetailEntity entity,boolean isLocal);//展示课程详情
        void showErrorView(int icon,String msg);// 0.数据问题 1.网络问题
        void startDownLoadFiles(CourseDetailFilesEntity courseDetailFilesEntity);//课程相关文件路径准备完毕，开始下载流程
        void registStatus(boolean isRegist);//注册状态反馈
    }

    interface Model extends IBaseModel {
        Observable<String> getCourseDetail(Map<String,String> params);
        void setCourseDetailToDb(CourseDetailEntity entity);
        CourseDetailEntity getLocalCourseDetail(String courseId);
        Observable<String>  getCourseDetailFiles(Map<String,String> params);
        boolean setCourseDetailFilesToDb(CourseDetailFilesEntity entity);
        boolean registCourse(String courseId);
        boolean isCoureRegist(String courseId);
        void reviewRegistedCourse(String coursedId);
        boolean isCanRigist(String courseId);
    }

    abstract class Preaenter extends BasePresenter<View,Model> {
         abstract void getCourseDetail(Map<String,String> params);
         abstract void getCourseDetailFiles(Map<String,String> params);
         abstract void registCourse(String courseId);
         abstract boolean isCoureRegist(String courseId);
         abstract void reviewRegistedCourse(String coursedId);
         abstract boolean isCanRigist(String courseId);
    }
}

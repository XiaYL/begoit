package com.begoit.mooc.offline.ui.activity.searchCourse;

import com.begoit.mooc.offline.base.BasePresenter;
import com.begoit.mooc.offline.base.IBaseModel;
import com.begoit.mooc.offline.base.IBaseView;
import com.begoit.mooc.offline.entity.course.course_list.CourseListItemEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @Description:课程搜索综合协议
 * @Author:gxj
 * @Time 2019/3/8
 */

public interface SearchCourseContract {
    interface View extends IBaseView {
       void showCourse(List<CourseListItemEntity> courseList);
        void showErrorView(int icon,String msg);
    }

    interface Model extends IBaseModel {
        Observable<String> doSearch(Map<String,String> params);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        abstract void doSearch(Map<String,String> params);
    }
}

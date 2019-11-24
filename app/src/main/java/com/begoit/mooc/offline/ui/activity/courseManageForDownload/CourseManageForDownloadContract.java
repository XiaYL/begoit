package com.begoit.mooc.offline.ui.activity.courseManageForDownload;

import com.begoit.mooc.offline.base.BasePresenter;
import com.begoit.mooc.offline.base.IBaseModel;
import com.begoit.mooc.offline.base.IBaseView;
import com.begoit.mooc.offline.entity.course.user_download.CourseDownLoadedEntity;

import java.util.List;

/**
 * @Description:课程管理综合协议
 * @Author:gxj
 * @Time 2019/3/7
 */

public interface CourseManageForDownloadContract {
    interface View extends IBaseView {
        void showCourses(List<CourseDownLoadedEntity> dataList);
        void showEmptyView();
        void deleteStatus(boolean isDeleted);
    }

    interface Model extends IBaseModel {
        List<CourseDownLoadedEntity> getDownloadedCourses();
        boolean deleteCourse(List<CourseDownLoadedEntity> items);
    }

    abstract class Preaenter extends BasePresenter<View,Model> {
        abstract void getCourses();
        abstract void deleteCourse(List<CourseDownLoadedEntity> items);
    }
}

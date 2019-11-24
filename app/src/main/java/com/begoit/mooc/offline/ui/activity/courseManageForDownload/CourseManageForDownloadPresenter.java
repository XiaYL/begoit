package com.begoit.mooc.offline.ui.activity.courseManageForDownload;

import com.begoit.mooc.offline.entity.course.user_download.CourseDownLoadedEntity;

import java.util.List;

/**
 * @Description:课程管理业务处理
 * @Author:gxj
 * @Time 2019/3/7
 */

public class CourseManageForDownloadPresenter extends CourseManageForDownloadContract.Preaenter {
    @Override
    void getCourses() {
         mView.showCourses(mModel.getDownloadedCourses());
    }

    @Override
    void deleteCourse(List<CourseDownLoadedEntity> items) {
          mView.deleteStatus(mModel.deleteCourse(items));
    }


}

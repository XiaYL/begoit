package com.begoit.mooc.offline.ui.activity.learning;

import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideoTestEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideonobEntity;
import com.begoit.mooc.offline.ui.activity.learning.event.ShowScoreEvent;

import java.util.List;

/**
 * @Description:学习页面业务管理
 * @Author:gxj
 * @Time 2019/3/4
 */

public class LearningPresenter extends LearningContract.Presenter{
    @Override
    String getSumScore(CourseDetailFilesEntity entity) {
        return mModel.getSumScore(entity);
    }

    @Override
    void getVideos(String courseId) {
        mView.showLoading();
        mView.showVideos(mModel.getCurrentCourse(courseId));
        mModel.setHistoryCourse(courseId);
        mView.cancelLoading();
    }

    @Override
    void getCurrentVideo(VideonobEntity videonobEntity) {
         mView.playVideo(mModel.getCurrentVideo(videonobEntity.videoFileId),videonobEntity);
    }

    @Override
    void getTestList(int limitVideoTestNum,String videoId,VideonobEntity videonobEntity) {
         mView.makeTest(mModel.getTestList(limitVideoTestNum,videoId),videonobEntity);
    }

    @Override
    void setScore(String videoId, CourseDetailFilesEntity currentCourse, ShowScoreEvent event, boolean isVideo) {
        mModel.setScore(videoId,currentCourse,event,isVideo);
    }
}

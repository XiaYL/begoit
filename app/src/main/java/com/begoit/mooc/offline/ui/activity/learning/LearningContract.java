package com.begoit.mooc.offline.ui.activity.learning;

import com.begoit.mooc.offline.base.BasePresenter;
import com.begoit.mooc.offline.base.IBaseModel;
import com.begoit.mooc.offline.base.IBaseView;
import com.begoit.mooc.offline.entity.course.CourseDetailLearningEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.FileInformationEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideoTestEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideonobEntity;
import com.begoit.mooc.offline.ui.activity.learning.event.ShowScoreEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:学习中综合协议
 * @Author:gxj
 * @Time 2019/3/4
 */

public interface LearningContract {
    interface View extends IBaseView{
        void showVideos(CourseDetailFilesEntity currentCourse);
        void playVideo(FileInformationEntity fileInformationEntity,VideonobEntity videonobEntity);
        void makeTest(List<VideoTestEntity> testList,VideonobEntity videonobEntity);
    }
    interface Model extends IBaseModel {
        String getSumScore(CourseDetailFilesEntity entity);
        CourseDetailFilesEntity getCurrentCourse(String courseId);
        FileInformationEntity getCurrentVideo(String videoId);
        List<VideoTestEntity> getTestList(int limitVideoTestNum,String videoId);
        void setHistoryCourse(String courseId);
        void setScore(String videoId,CourseDetailFilesEntity currentCourse,ShowScoreEvent event,boolean isVideo);
    }

    abstract class Presenter extends BasePresenter<View,Model>{
        abstract String getSumScore(CourseDetailFilesEntity entity);
        abstract void getVideos(String courseId);
        abstract void getCurrentVideo(VideonobEntity videonobEntity);
        abstract void getTestList(int limitVideoTestNum,String videoId,VideonobEntity videonobEntity);
        abstract void setScore(String videoId,CourseDetailFilesEntity currentCourse,ShowScoreEvent event,boolean isVideo);
    }

}

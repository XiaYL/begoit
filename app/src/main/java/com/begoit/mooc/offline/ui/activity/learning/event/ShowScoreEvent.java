package com.begoit.mooc.offline.ui.activity.learning.event;

import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideoTestEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:测试题分数展示事件
 * @Author:gxj
 * @Time 2019/4/24
 */

public class ShowScoreEvent {
    public int hasScore;
    public int totalScore;
    public List<VideoTestEntity> testList;

    public ShowScoreEvent(int hasScore,int totalScore,List<VideoTestEntity> testList){
        this.hasScore = hasScore;
        this.totalScore = totalScore;
        this.testList = new ArrayList<>();
        this.testList.addAll(testList);
    }
}

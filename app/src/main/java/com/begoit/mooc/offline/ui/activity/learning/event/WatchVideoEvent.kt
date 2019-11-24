package com.begoit.mooc.offline.ui.activity.learning.event

import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideonobEntity

/**
 *@Description:视频观看时间达标时间
 *@Author:gxj
 *@Time 2019/5/20
 */
class WatchVideoEvent {
    var targetVideo: VideonobEntity? = null

    constructor(targetVideo: VideonobEntity){
        this.targetVideo = targetVideo
    }

}
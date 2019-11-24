package com.begoit.mooc.offline.entity.course;

/**
 * @Description:课程管理实体
 * @Author:gxj
 * @Time 2019/3/7
 */

public class CourseManageItemEntity {
    public String courseId;
    public String courseName;
    public String schoolName;
    public int loadStatus;//1.正在下载 2.待下载 3.已下载
    public String progress;
}

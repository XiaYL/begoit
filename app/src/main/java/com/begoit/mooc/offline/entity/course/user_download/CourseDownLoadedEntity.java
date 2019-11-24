package com.begoit.mooc.offline.entity.course.user_download;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * @Description:已选下载课程标记类
 * @Author:gxj
 * @Time 2019/3/26
 */
@Entity
public class CourseDownLoadedEntity {
    @Id
    public String courseId;//课程id
    public String courseName;//课程名称
    public String courseWithSchool;//课程所属学校
    public String preImgFileid;//预览图片地址
    public long courseTotal;//课程文件总大小
    public int courseFilesCount;//课程文件总数量
    public int courseDownloadedFilesCound;//已下载文件数
    public long progress;//当前课程下载进度
    public long total;//当前课程已下载总长
    public String currentTotalRxBytes;//当前流量
    public int isDownloadError; // 判断下载过程是否有错误 0 ：无错误 1：有错误
    @Transient
    public boolean isAddedFile;//用于标记当前时间是否有新的文件下载完成
@Generated(hash = 639217855)
public CourseDownLoadedEntity(String courseId, String courseName,
        String courseWithSchool, String preImgFileid, long courseTotal,
        int courseFilesCount, int courseDownloadedFilesCound, long progress,
        long total, String currentTotalRxBytes, int isDownloadError) {
    this.courseId = courseId;
    this.courseName = courseName;
    this.courseWithSchool = courseWithSchool;
    this.preImgFileid = preImgFileid;
    this.courseTotal = courseTotal;
    this.courseFilesCount = courseFilesCount;
    this.courseDownloadedFilesCound = courseDownloadedFilesCound;
    this.progress = progress;
    this.total = total;
    this.currentTotalRxBytes = currentTotalRxBytes;
    this.isDownloadError = isDownloadError;
}
@Generated(hash = 865313771)
public CourseDownLoadedEntity() {
}
public String getCourseId() {
    return this.courseId;
}
public void setCourseId(String courseId) {
    this.courseId = courseId;
}
public String getCourseName() {
    return this.courseName;
}
public void setCourseName(String courseName) {
    this.courseName = courseName;
}
public String getCourseWithSchool() {
    return this.courseWithSchool;
}
public void setCourseWithSchool(String courseWithSchool) {
    this.courseWithSchool = courseWithSchool;
}
public String getPreImgFileid() {
    return this.preImgFileid;
}
public void setPreImgFileid(String preImgFileid) {
    this.preImgFileid = preImgFileid;
}
public long getCourseTotal() {
    return this.courseTotal;
}
public void setCourseTotal(long courseTotal) {
    this.courseTotal = courseTotal;
}
public int getCourseFilesCount() {
    return this.courseFilesCount;
}
public void setCourseFilesCount(int courseFilesCount) {
    this.courseFilesCount = courseFilesCount;
}
public int getCourseDownloadedFilesCound() {
    return this.courseDownloadedFilesCound;
}
public void setCourseDownloadedFilesCound(int courseDownloadedFilesCound) {
    this.courseDownloadedFilesCound = courseDownloadedFilesCound;
}
public int getIsDownloadError() {
    return this.isDownloadError;
}
public void setIsDownloadError(int isDownloadError) {
    this.isDownloadError = isDownloadError;
}
public long getProgress() {
    return this.progress;
}
public void setProgress(long progress) {
    this.progress = progress;
}
public long getTotal() {
    return this.total;
}
public void setTotal(long total) {
    this.total = total;
}
public String getCurrentTotalRxBytes() {
    return this.currentTotalRxBytes;
}
public void setCurrentTotalRxBytes(String currentTotalRxBytes) {
    this.currentTotalRxBytes = currentTotalRxBytes;
}
}

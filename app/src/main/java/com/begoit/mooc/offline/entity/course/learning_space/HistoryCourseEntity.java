package com.begoit.mooc.offline.entity.course.learning_space;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Description:本机学习历史课程
 * @Author:gxj
 * @Time 2019/5/6
 */
@Entity
public class HistoryCourseEntity {
    @Id
    public String id;//主键
    public String channelId;//课程id
    public String courseName;//课程名称
    public String preImgFileid;//预览图片
    public String courseWithSchool;//课程所属学校
    public String userAccount;//用户账号
    public int isDelete;//0:未删除 1：已删除
    @Generated(hash = 1582215146)
    public HistoryCourseEntity(String id, String channelId, String courseName,
            String preImgFileid, String courseWithSchool, String userAccount,
            int isDelete) {
        this.id = id;
        this.channelId = channelId;
        this.courseName = courseName;
        this.preImgFileid = preImgFileid;
        this.courseWithSchool = courseWithSchool;
        this.userAccount = userAccount;
        this.isDelete = isDelete;
    }
    @Generated(hash = 519804123)
    public HistoryCourseEntity() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getChannelId() {
        return this.channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    public String getCourseName() {
        return this.courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getPreImgFileid() {
        return this.preImgFileid;
    }
    public void setPreImgFileid(String preImgFileid) {
        this.preImgFileid = preImgFileid;
    }
    public String getCourseWithSchool() {
        return this.courseWithSchool;
    }
    public void setCourseWithSchool(String courseWithSchool) {
        this.courseWithSchool = courseWithSchool;
    }
    public String getUserAccount() {
        return this.userAccount;
    }
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    public int getIsDelete() {
        return this.isDelete;
    }
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}

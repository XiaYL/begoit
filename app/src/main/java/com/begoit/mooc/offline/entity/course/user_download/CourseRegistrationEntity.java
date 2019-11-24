package com.begoit.mooc.offline.entity.course.user_download;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description:先修课（作用于注册课程逻辑，注册课程需先修完先修课）
 * @Author:gxj
 * @Time 2019/5/5
 */
@Entity
public class CourseRegistrationEntity {
    @Id
    public String id;// 必有字段  备注：id
    public String groupName;// 必有字段  备注：先修课程组名
    public String groupId;//必有字段  备注：先修课程组ID
    public String channelId;// 必有字段  备注：配置课程ID
    public String preChannelId;// 必有字段  备注：先修课程ID
    public String addTime;// 必有字段  备注：创建时间
    @Generated(hash = 448204543)
    public CourseRegistrationEntity(String id, String groupName, String groupId,
            String channelId, String preChannelId, String addTime) {
        this.id = id;
        this.groupName = groupName;
        this.groupId = groupId;
        this.channelId = channelId;
        this.preChannelId = preChannelId;
        this.addTime = addTime;
    }
    @Generated(hash = 1765226804)
    public CourseRegistrationEntity() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getGroupName() {
        return this.groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getGroupId() {
        return this.groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getChannelId() {
        return this.channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    public String getPreChannelId() {
        return this.preChannelId;
    }
    public void setPreChannelId(String preChannelId) {
        this.preChannelId = preChannelId;
    }
    public String getAddTime() {
        return this.addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}

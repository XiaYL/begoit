package com.begoit.mooc.offline.entity.course.user_download;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description:已注册课程（对应课程选课表）
 * @Author:gxj
 * @Time 2019/4/28
 */
@Entity
public class UserChannelEntity {
    @Id
    public String id;//主键
    public int isChange;//0:没变动  1：有变动  同步学习记录上传有变动记录
    public String channelId;//课程id
    public String userAccount;//用户账号 用户ID
    public String addTime;//注册时间
    public String updateTime;//更新时间
    public int videoTestScore;//测试题得分
    public int videoTestSumScore;//测试题总分
    public int videoFinishScore;//视频得分
    public int videoFinishSumCount;//视频总分
    public float homeworkScore;//作业得分
    public int homeworkSumScore;//作业总分
    public int exchangeScore;//讨论得分
    public String normalSumScore;//平时总成绩
    public int examScore;//考试得分
    public String sumScore;//总分
    public int channelStyle;//课程风格（0为教学课，1为公开课 2微课）
    public int isTeacher;//是否老师 0学生 1老师
    public String teacherApproveTime;//老师审核时间(不需要审核时为注册时间)
    public int examStatus;//	-1 作弊 0未考 1正考 2考完
    public int channelTerm;//课程期数
    public String collect;//是否收藏
    public String collectTime;//收藏时间
    @Generated(hash = 1623782845)
    public UserChannelEntity(String id, int isChange, String channelId,
            String userAccount, String addTime, String updateTime,
            int videoTestScore, int videoTestSumScore, int videoFinishScore,
            int videoFinishSumCount, float homeworkScore, int homeworkSumScore,
            int exchangeScore, String normalSumScore, int examScore,
            String sumScore, int channelStyle, int isTeacher,
            String teacherApproveTime, int examStatus, int channelTerm,
            String collect, String collectTime) {
        this.id = id;
        this.isChange = isChange;
        this.channelId = channelId;
        this.userAccount = userAccount;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.videoTestScore = videoTestScore;
        this.videoTestSumScore = videoTestSumScore;
        this.videoFinishScore = videoFinishScore;
        this.videoFinishSumCount = videoFinishSumCount;
        this.homeworkScore = homeworkScore;
        this.homeworkSumScore = homeworkSumScore;
        this.exchangeScore = exchangeScore;
        this.normalSumScore = normalSumScore;
        this.examScore = examScore;
        this.sumScore = sumScore;
        this.channelStyle = channelStyle;
        this.isTeacher = isTeacher;
        this.teacherApproveTime = teacherApproveTime;
        this.examStatus = examStatus;
        this.channelTerm = channelTerm;
        this.collect = collect;
        this.collectTime = collectTime;
    }
    @Generated(hash = 928582184)
    public UserChannelEntity() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getIsChange() {
        return this.isChange;
    }
    public void setIsChange(int isChange) {
        this.isChange = isChange;
    }
    public String getChannelId() {
        return this.channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    public String getUserAccount() {
        return this.userAccount;
    }
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    public String getAddTime() {
        return this.addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public int getVideoTestScore() {
        return this.videoTestScore;
    }
    public void setVideoTestScore(int videoTestScore) {
        this.videoTestScore = videoTestScore;
    }
    public int getVideoTestSumScore() {
        return this.videoTestSumScore;
    }
    public void setVideoTestSumScore(int videoTestSumScore) {
        this.videoTestSumScore = videoTestSumScore;
    }
    public int getVideoFinishScore() {
        return this.videoFinishScore;
    }
    public void setVideoFinishScore(int videoFinishScore) {
        this.videoFinishScore = videoFinishScore;
    }
    public int getVideoFinishSumCount() {
        return this.videoFinishSumCount;
    }
    public void setVideoFinishSumCount(int videoFinishSumCount) {
        this.videoFinishSumCount = videoFinishSumCount;
    }
    public float getHomeworkScore() {
        return this.homeworkScore;
    }
    public void setHomeworkScore(float homeworkScore) {
        this.homeworkScore = homeworkScore;
    }
    public int getHomeworkSumScore() {
        return this.homeworkSumScore;
    }
    public void setHomeworkSumScore(int homeworkSumScore) {
        this.homeworkSumScore = homeworkSumScore;
    }
    public int getExchangeScore() {
        return this.exchangeScore;
    }
    public void setExchangeScore(int exchangeScore) {
        this.exchangeScore = exchangeScore;
    }
    public String getNormalSumScore() {
        return this.normalSumScore;
    }
    public void setNormalSumScore(String normalSumScore) {
        this.normalSumScore = normalSumScore;
    }
    public int getExamScore() {
        return this.examScore;
    }
    public void setExamScore(int examScore) {
        this.examScore = examScore;
    }
    public String getSumScore() {
        return this.sumScore;
    }
    public void setSumScore(String sumScore) {
        this.sumScore = sumScore;
    }
    public int getChannelStyle() {
        return this.channelStyle;
    }
    public void setChannelStyle(int channelStyle) {
        this.channelStyle = channelStyle;
    }
    public int getIsTeacher() {
        return this.isTeacher;
    }
    public void setIsTeacher(int isTeacher) {
        this.isTeacher = isTeacher;
    }
    public String getTeacherApproveTime() {
        return this.teacherApproveTime;
    }
    public void setTeacherApproveTime(String teacherApproveTime) {
        this.teacherApproveTime = teacherApproveTime;
    }
    public int getExamStatus() {
        return this.examStatus;
    }
    public void setExamStatus(int examStatus) {
        this.examStatus = examStatus;
    }
    public int getChannelTerm() {
        return this.channelTerm;
    }
    public void setChannelTerm(int channelTerm) {
        this.channelTerm = channelTerm;
    }
    public String getCollect() {
        return this.collect;
    }
    public void setCollect(String collect) {
        this.collect = collect;
    }
    public String getCollectTime() {
        return this.collectTime;
    }
    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }
   
}

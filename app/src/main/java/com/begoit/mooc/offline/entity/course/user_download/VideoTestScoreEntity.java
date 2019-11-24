package com.begoit.mooc.offline.entity.course.user_download;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description:视频和测试题成绩
 * @Author:gxj
 * @Time 2019/4/25
 */
@Entity
public class VideoTestScoreEntity {
    @Id
    public String id;//主键
    public int isChange;//0:没变动  1：有变动  同步学习记录上传有变动记录
    public String userAccount;//用户账号
    @Unique
    public String videoId;//视频id
    public String addTime;//新增时间
    public String updateTime;//更新时间
    public int score;//测试得分
    public int answerCount;//测试题回答次数
    public int answerTotalScore;//	测试题回答累计总分
    public int finalTest;//是否开始结业测试
    public String videoTestId;//做测试题时生成的id用于记录日志
    public int watch;//视频是否观看 0:未看 1：已看
    public int videoCompleteTime;//视频完成时间点(单位秒s)
    public int videoLength;//视频长度(单位秒s)
    public String channelId;//课程ID(channel中channelid)
    public String chapterId;//课程章ID(articleclass中classid)
    public String sectionId;//课程节(artic中id)
    public String ip;//学习端ip(app默认本地地址127.0.0.1)
    public String finalTestEnd;//是否完成结业考试
    public String channelTerm;//课程期数(channel中term)
    @Generated(hash = 1568800901)
    public VideoTestScoreEntity(String id, int isChange, String userAccount,
            String videoId, String addTime, String updateTime, int score,
            int answerCount, int answerTotalScore, int finalTest,
            String videoTestId, int watch, int videoCompleteTime, int videoLength,
            String channelId, String chapterId, String sectionId, String ip,
            String finalTestEnd, String channelTerm) {
        this.id = id;
        this.isChange = isChange;
        this.userAccount = userAccount;
        this.videoId = videoId;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.score = score;
        this.answerCount = answerCount;
        this.answerTotalScore = answerTotalScore;
        this.finalTest = finalTest;
        this.videoTestId = videoTestId;
        this.watch = watch;
        this.videoCompleteTime = videoCompleteTime;
        this.videoLength = videoLength;
        this.channelId = channelId;
        this.chapterId = chapterId;
        this.sectionId = sectionId;
        this.ip = ip;
        this.finalTestEnd = finalTestEnd;
        this.channelTerm = channelTerm;
    }
    @Generated(hash = 1069036405)
    public VideoTestScoreEntity() {
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
    public String getUserAccount() {
        return this.userAccount;
    }
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    public String getVideoId() {
        return this.videoId;
    }
    public void setVideoId(String videoId) {
        this.videoId = videoId;
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
    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getAnswerCount() {
        return this.answerCount;
    }
    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }
    public int getAnswerTotalScore() {
        return this.answerTotalScore;
    }
    public void setAnswerTotalScore(int answerTotalScore) {
        this.answerTotalScore = answerTotalScore;
    }
    public int getFinalTest() {
        return this.finalTest;
    }
    public void setFinalTest(int finalTest) {
        this.finalTest = finalTest;
    }
    public String getVideoTestId() {
        return this.videoTestId;
    }
    public void setVideoTestId(String videoTestId) {
        this.videoTestId = videoTestId;
    }
    public int getWatch() {
        return this.watch;
    }
    public void setWatch(int watch) {
        this.watch = watch;
    }
    public int getVideoCompleteTime() {
        return this.videoCompleteTime;
    }
    public void setVideoCompleteTime(int videoCompleteTime) {
        this.videoCompleteTime = videoCompleteTime;
    }
    public int getVideoLength() {
        return this.videoLength;
    }
    public void setVideoLength(int videoLength) {
        this.videoLength = videoLength;
    }
    public String getChannelId() {
        return this.channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    public String getChapterId() {
        return this.chapterId;
    }
    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }
    public String getSectionId() {
        return this.sectionId;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getFinalTestEnd() {
        return this.finalTestEnd;
    }
    public void setFinalTestEnd(String finalTestEnd) {
        this.finalTestEnd = finalTestEnd;
    }
    public String getChannelTerm() {
        return this.channelTerm;
    }
    public void setChannelTerm(String channelTerm) {
        this.channelTerm = channelTerm;
    }

}

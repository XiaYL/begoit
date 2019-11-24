package com.begoit.mooc.offline.entity.course.user_download;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description:视频测试题日志
 * @Author:gxj
 * @Time 2019/5/16
 */
@Entity
public class VideoTestLogEntity {
    @Id
    public String id; //主键
    public int isChange;//0:没变动  1：有变动  同步学习记录上传有变动记录
    public String videoTestId;//测试题id
    public String userAccount;//用户id
    public String addTime;//添加时间
    public String answer;//回答答案
    public int score;//分数
    public String ip;//ip(app端都是本地操作,默认127.0.0.1)
    public String uuid;//uuid(videoandtestresult中的uuid,每次更新成绩生成)
    public int times; //测试题答题次数
    @Generated(hash = 1334290218)
    public VideoTestLogEntity(String id, int isChange, String videoTestId,
            String userAccount, String addTime, String answer, int score, String ip,
            String uuid, int times) {
        this.id = id;
        this.isChange = isChange;
        this.videoTestId = videoTestId;
        this.userAccount = userAccount;
        this.addTime = addTime;
        this.answer = answer;
        this.score = score;
        this.ip = ip;
        this.uuid = uuid;
        this.times = times;
    }
    @Generated(hash = 114734995)
    public VideoTestLogEntity() {
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
    public String getVideoTestId() {
        return this.videoTestId;
    }
    public void setVideoTestId(String videoTestId) {
        this.videoTestId = videoTestId;
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
    public String getAnswer() {
        return this.answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public int getTimes() {
        return this.times;
    }
    public void setTimes(int times) {
        this.times = times;
    }
}

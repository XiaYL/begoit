package com.begoit.mooc.offline.entity.course.course_detail.course_files;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 * @Description:课->章->节->视频->测试题 实体
 * @Author:gxj
 * @Time 2019/3/21
 */
@Entity
public class VideoTestEntity {
    @Id
    public String id; //id
    @NotNull
    public String videoId;//视频id 外键
    public int testType;//试题类型 0:判断 1：单选 2：多选 3：填空
    public String testTitle;//题干
    public String questionA;//选项内容
    public String questionB;//选项内容
    public String questionC; //选项内容
    public String questionD;//选项内容
    public String questionE;//选项内容
    public String questionF;//选项内容
    public String questionG;//选项内容
    public String questionH;//选项内容
    public String questionI;//选项内容
    public String questionJ;//选项内容
    public String answer;//官方答案
    @Transient
    public String userAnswer;// 用户答案
    public int score;//分数
    public String testStyle;
    public String audioFileid;//听力文件id
    public int isAnswerChange;//答案是否能交换
    public int blankSpaceNum;//填空题空格数
    @Generated(hash = 590512703)
    public VideoTestEntity(String id, @NotNull String videoId, int testType,
            String testTitle, String questionA, String questionB, String questionC,
            String questionD, String questionE, String questionF, String questionG,
            String questionH, String questionI, String questionJ, String answer,
            int score, String testStyle, String audioFileid, int isAnswerChange,
            int blankSpaceNum) {
        this.id = id;
        this.videoId = videoId;
        this.testType = testType;
        this.testTitle = testTitle;
        this.questionA = questionA;
        this.questionB = questionB;
        this.questionC = questionC;
        this.questionD = questionD;
        this.questionE = questionE;
        this.questionF = questionF;
        this.questionG = questionG;
        this.questionH = questionH;
        this.questionI = questionI;
        this.questionJ = questionJ;
        this.answer = answer;
        this.score = score;
        this.testStyle = testStyle;
        this.audioFileid = audioFileid;
        this.isAnswerChange = isAnswerChange;
        this.blankSpaceNum = blankSpaceNum;
    }
    @Generated(hash = 804423653)
    public VideoTestEntity() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getVideoId() {
        return this.videoId;
    }
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    public int getTestType() {
        return this.testType;
    }
    public void setTestType(int testType) {
        this.testType = testType;
    }
    public String getTestTitle() {
        return this.testTitle;
    }
    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }
    public String getQuestionA() {
        return this.questionA;
    }
    public void setQuestionA(String questionA) {
        this.questionA = questionA;
    }
    public String getQuestionB() {
        return this.questionB;
    }
    public void setQuestionB(String questionB) {
        this.questionB = questionB;
    }
    public String getQuestionC() {
        return this.questionC;
    }
    public void setQuestionC(String questionC) {
        this.questionC = questionC;
    }
    public String getQuestionD() {
        return this.questionD;
    }
    public void setQuestionD(String questionD) {
        this.questionD = questionD;
    }
    public String getQuestionE() {
        return this.questionE;
    }
    public void setQuestionE(String questionE) {
        this.questionE = questionE;
    }
    public String getQuestionF() {
        return this.questionF;
    }
    public void setQuestionF(String questionF) {
        this.questionF = questionF;
    }
    public String getQuestionG() {
        return this.questionG;
    }
    public void setQuestionG(String questionG) {
        this.questionG = questionG;
    }
    public String getQuestionH() {
        return this.questionH;
    }
    public void setQuestionH(String questionH) {
        this.questionH = questionH;
    }
    public String getQuestionI() {
        return this.questionI;
    }
    public void setQuestionI(String questionI) {
        this.questionI = questionI;
    }
    public String getQuestionJ() {
        return this.questionJ;
    }
    public void setQuestionJ(String questionJ) {
        this.questionJ = questionJ;
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
    public String getTestStyle() {
        return this.testStyle;
    }
    public void setTestStyle(String testStyle) {
        this.testStyle = testStyle;
    }
    public String getAudioFileid() {
        return this.audioFileid;
    }
    public void setAudioFileid(String audioFileid) {
        this.audioFileid = audioFileid;
    }
    public int getIsAnswerChange() {
        return this.isAnswerChange;
    }
    public void setIsAnswerChange(int isAnswerChange) {
        this.isAnswerChange = isAnswerChange;
    }
    public int getBlankSpaceNum() {
        return this.blankSpaceNum;
    }
    public void setBlankSpaceNum(int blankSpaceNum) {
        this.blankSpaceNum = blankSpaceNum;
    }
}

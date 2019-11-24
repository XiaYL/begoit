package com.begoit.mooc.offline.entity.course.course_detail.course_files;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @Description:测试题答案选项
 * @Author:gxj
 * @Time 2019/4/22
 */

public class VideoTestAnswerOptionEntity implements MultiItemEntity {
    public int id;//答案标识
    public int testType;//类型 0:判断 1：单选 2：多选 3：填空
    public String option;// 选项 A. B. ...
    public String content;//选项内容
    public String userAnswer;// 选择答案
    public String answer;//标准答案

    public VideoTestAnswerOptionEntity(int id,int testType,String option,String content){
        this.id = id;
        this.testType = testType;
        this.option = option;
        this.content = content;
    }

    @Override
    public int getItemType() {
        return testType;
    }
}

package com.begoit.mooc.offline.entity.course.teacher;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description:讲师信息
 * @Author:gxj
 * @Time 2019/3/13
 */
@Entity
public class TeacherEntity {
    @Id
    public String id;
    public String teacherName;//教师名
    public String posTitle;//教师职称
    public String imgFileid;//头像地址
    @Generated(hash = 1316933578)
    public TeacherEntity(String id, String teacherName, String posTitle,
            String imgFileid) {
        this.id = id;
        this.teacherName = teacherName;
        this.posTitle = posTitle;
        this.imgFileid = imgFileid;
    }
    @Generated(hash = 979429349)
    public TeacherEntity() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTeacherName() {
        return this.teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public String getPosTitle() {
        return this.posTitle;
    }
    public void setPosTitle(String posTitle) {
        this.posTitle = posTitle;
    }
    public String getImgFileid() {
        return this.imgFileid;
    }
    public void setImgFileid(String imgFileid) {
        this.imgFileid = imgFileid;
    }
}

package com.begoit.mooc.offline.entity.course.course_list;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description:课程列表数据实体类
 * @Author:gxj
 * @Time 2019/2/25
 */
@Entity
public class CourseListEntity {
    public long id;
    public int imgUrl;
    public String name;
    public String schoolName;
    @Generated(hash = 2137743697)
    public CourseListEntity(long id, int imgUrl, String name, String schoolName) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.schoolName = schoolName;
    }
    @Generated(hash = 1221559686)
    public CourseListEntity() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getImgUrl() {
        return this.imgUrl;
    }
    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSchoolName() {
        return this.schoolName;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}

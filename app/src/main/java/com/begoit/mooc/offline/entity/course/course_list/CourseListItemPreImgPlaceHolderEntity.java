package com.begoit.mooc.offline.entity.course.course_list;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description:为无预览图片的课程设置一个随机默认图片，且这个默认随机图片后期不可变且此功能要纯在客户端实现 （...）
 * @Author:gxj
 * @Time 2019/3/13
 */
@Entity
public class CourseListItemPreImgPlaceHolderEntity{
    @Id
    public String id;//课程id
    public int preImgFileid;//预览图片
    @Generated(hash = 473055042)
    public CourseListItemPreImgPlaceHolderEntity(String id, int preImgFileid) {
        this.id = id;
        this.preImgFileid = preImgFileid;
    }
    @Generated(hash = 700062349)
    public CourseListItemPreImgPlaceHolderEntity() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getPreImgFileid() {
        return this.preImgFileid;
    }
    public void setPreImgFileid(int preImgFileid) {
        this.preImgFileid = preImgFileid;
    }

}

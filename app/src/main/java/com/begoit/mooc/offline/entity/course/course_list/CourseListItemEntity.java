package com.begoit.mooc.offline.entity.course.course_list;

import android.os.Parcel;
import android.os.Parcelable;

import com.begoit.mooc.offline.entity.course.teacher.TeacherConfigEntity;

import java.util.List;

/**
 * @Description:课程列表数据
 * @Author:gxj
 * @Time 2019/3/13
 */

public class CourseListItemEntity implements Parcelable{
    public String id;//课程id
    public String channelTypeId;//课程类别id
    public String channelName;//课程名
    public String schoolName;//开课学校
    public String preImgFileid;//预览图片
    public String channelLogoFileid;//课程LOGO
    public List<TeacherConfigEntity> teacherConfig;//讲师列表

    public CourseListItemEntity(){}

    protected CourseListItemEntity(Parcel in) {
        id = in.readString();
        channelTypeId = in.readString();
        channelName = in.readString();
        schoolName = in.readString();
        preImgFileid = in.readString();
        channelLogoFileid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(channelTypeId);
        dest.writeString(channelName);
        dest.writeString(schoolName);
        dest.writeString(preImgFileid);
        dest.writeString(channelLogoFileid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseListItemEntity> CREATOR = new Creator<CourseListItemEntity>() {
        @Override
        public CourseListItemEntity createFromParcel(Parcel in) {
            return new CourseListItemEntity(in);
        }

        @Override
        public CourseListItemEntity[] newArray(int size) {
            return new CourseListItemEntity[size];
        }
    };
}

package com.begoit.mooc.offline.entity.course.course_detail;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.begoit.mooc.db.DaoSession;
import com.begoit.mooc.db.TeacherConfigEntityDao;
import com.begoit.mooc.db.CourseDetailEntityDao;
import com.begoit.mooc.offline.entity.course.teacher.TeacherConfigEntity;

/**
 * @Description:课程详情实体
 * @Author:gxj
 * @Time 2019/3/15
 */
@Entity
public class CourseDetailEntity {
    @Id
    public String id;//课程id
    public String typeName; //课程类别名
    public String channelTypeId;//课程类别id
    public String channelName;//课程名称
    public String schoolName;//开课学校
    public String expectTime;//学习时长
    public String hardLevel; //难度
    public String preImgFileid; //预览图片地址
    public String preVideoFileid; //预览视频地址
    public String preVideoSubtitleFileid;//预览视频字幕
    public String channelLogoFileid; //课程LOGO地址
    public String introduction; //课程简介　
    public String knowlage;//所需知识
    public String program; //教学大纲
    @ToMany(referencedJoinProperty = "channelId")
    public List<TeacherConfigEntity> teacherConfig;//讲师列表
    public String channelSize;//课程大小
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1590291439)
    private transient CourseDetailEntityDao myDao;
    @Generated(hash = 871720364)
    public CourseDetailEntity(String id, String typeName, String channelTypeId, String channelName,
            String schoolName, String expectTime, String hardLevel, String preImgFileid,
            String preVideoFileid, String preVideoSubtitleFileid, String channelLogoFileid,
            String introduction, String knowlage, String program, String channelSize) {
        this.id = id;
        this.typeName = typeName;
        this.channelTypeId = channelTypeId;
        this.channelName = channelName;
        this.schoolName = schoolName;
        this.expectTime = expectTime;
        this.hardLevel = hardLevel;
        this.preImgFileid = preImgFileid;
        this.preVideoFileid = preVideoFileid;
        this.preVideoSubtitleFileid = preVideoSubtitleFileid;
        this.channelLogoFileid = channelLogoFileid;
        this.introduction = introduction;
        this.knowlage = knowlage;
        this.program = program;
        this.channelSize = channelSize;
    }
    @Generated(hash = 866919786)
    public CourseDetailEntity() {
    }
    public String getTypeName() {
        return this.typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getChannelTypeId() {
        return this.channelTypeId;
    }
    public void setChannelTypeId(String channelTypeId) {
        this.channelTypeId = channelTypeId;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getChannelName() {
        return this.channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    public String getSchoolName() {
        return this.schoolName;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    public String getExpectTime() {
        return this.expectTime;
    }
    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }
    public String getHardLevel() {
        return this.hardLevel;
    }
    public void setHardLevel(String hardLevel) {
        this.hardLevel = hardLevel;
    }
    public String getPreImgFileid() {
        return this.preImgFileid;
    }
    public void setPreImgFileid(String preImgFileid) {
        this.preImgFileid = preImgFileid;
    }
    public String getPreVideoFileid() {
        return this.preVideoFileid;
    }
    public void setPreVideoFileid(String preVideoFileid) {
        this.preVideoFileid = preVideoFileid;
    }
    public String getPreVideoSubtitleFileid() {
        return this.preVideoSubtitleFileid;
    }
    public void setPreVideoSubtitleFileid(String preVideoSubtitleFileid) {
        this.preVideoSubtitleFileid = preVideoSubtitleFileid;
    }
    public String getChannelLogoFileid() {
        return this.channelLogoFileid;
    }
    public void setChannelLogoFileid(String channelLogoFileid) {
        this.channelLogoFileid = channelLogoFileid;
    }
    public String getIntroduction() {
        return this.introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getKnowlage() {
        return this.knowlage;
    }
    public void setKnowlage(String knowlage) {
        this.knowlage = knowlage;
    }
    public String getProgram() {
        return this.program;
    }
    public void setProgram(String program) {
        this.program = program;
    }
    public String getChannelSize() {
        return this.channelSize;
    }
    public void setChannelSize(String channelSize) {
        this.channelSize = channelSize;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 945969372)
    public List<TeacherConfigEntity> getTeacherConfig() {
        if (teacherConfig == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TeacherConfigEntityDao targetDao = daoSession
                    .getTeacherConfigEntityDao();
            List<TeacherConfigEntity> teacherConfigNew = targetDao
                    ._queryCourseDetailEntity_TeacherConfig(id);
            synchronized (this) {
                if (teacherConfig == null) {
                    teacherConfig = teacherConfigNew;
                }
            }
        }
        return teacherConfig;
    }

    /**
     * 获取教师配置列表，greendao生成的get方法有问题
     * @return
     */
    public List<TeacherConfigEntity> getTeacherConfigs() {
        return teacherConfig;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 20662012)
    public synchronized void resetTeacherConfig() {
        teacherConfig = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1216959733)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCourseDetailEntityDao() : null;
    }

}

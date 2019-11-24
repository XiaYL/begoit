package com.begoit.mooc.offline.entity.course.teacher;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.begoit.mooc.db.DaoSession;
import com.begoit.mooc.db.TeacherEntityDao;
import com.begoit.mooc.db.TeacherConfigEntityDao;

/**
 * @Description:课程讲师中间类
 * @Author:gxj
 * @Time 2019/3/13
 */
@Entity
public class TeacherConfigEntity {
    @Id
    public String id;
    public String channelId;
    public String teacherId;
    @ToOne(joinProperty = "teacherId")
    public TeacherEntity teachers;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1553369642)
    private transient TeacherConfigEntityDao myDao;
    @Generated(hash = 1747588256)
    public TeacherConfigEntity(String id, String channelId, String teacherId) {
        this.id = id;
        this.channelId = channelId;
        this.teacherId = teacherId;
    }
    @Generated(hash = 641814648)
    public TeacherConfigEntity() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getChannelId() {
        return this.channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    public String getTeacherId() {
        return this.teacherId;
    }
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
    @Generated(hash = 1672140357)
    private transient String teachers__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 830749530)
    public TeacherEntity getTeachers() {
        String __key = this.teacherId;
        if (teachers__resolvedKey == null || teachers__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TeacherEntityDao targetDao = daoSession.getTeacherEntityDao();
            TeacherEntity teachersNew = targetDao.load(__key);
            synchronized (this) {
                teachers = teachersNew;
                teachers__resolvedKey = __key;
            }
        }
        return teachers;
    }
    /**
     * 获取教师配置列表，greendao生成的get方法有问题
     * @return
     */
    public TeacherEntity getTeacher(){
            return teachers;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2055427212)
    public void setTeachers(TeacherEntity teachers) {
        synchronized (this) {
            this.teachers = teachers;
            teacherId = teachers == null ? null : teachers.getId();
            teachers__resolvedKey = teacherId;
        }
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
    @Generated(hash = 1706405679)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTeacherConfigEntityDao() : null;
    }
}

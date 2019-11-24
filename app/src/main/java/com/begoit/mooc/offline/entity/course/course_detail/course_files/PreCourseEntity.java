package com.begoit.mooc.offline.entity.course.course_detail.course_files;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.begoit.mooc.db.DaoSession;
import com.begoit.mooc.db.PreCourseGroupEntityDao;
import com.begoit.mooc.db.PreCourseEntityDao;

/**
 * @Description:
 * @Author:gxj
 * @Time 2019/5/27
 */
@Entity
public class PreCourseEntity {
    @Id
    public String channelId;
    @ToMany(referencedJoinProperty = "channelId")
    public List<PreCourseGroupEntity> preCourseGroup;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1940214751)
    private transient PreCourseEntityDao myDao;
    @Generated(hash = 1832835172)
    public PreCourseEntity(String channelId) {
        this.channelId = channelId;
    }
    @Generated(hash = 1836206656)
    public PreCourseEntity() {
    }
    public String getChannelId() {
        return this.channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 499686493)
    public List<PreCourseGroupEntity> getPreCourseGroup() {
        if (preCourseGroup == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PreCourseGroupEntityDao targetDao = daoSession
                    .getPreCourseGroupEntityDao();
            List<PreCourseGroupEntity> preCourseGroupNew = targetDao
                    ._queryPreCourseEntity_PreCourseGroup(channelId);
            synchronized (this) {
                if (preCourseGroup == null) {
                    preCourseGroup = preCourseGroupNew;
                }
            }
        }
        return preCourseGroup;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 691989267)
    public synchronized void resetPreCourseGroup() {
        preCourseGroup = null;
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
    @Generated(hash = 909494166)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPreCourseEntityDao() : null;
    }
}

package com.begoit.mooc.offline.entity.course.course_detail.course_files;

import com.begoit.mooc.offline.entity.course.user_download.CourseRegistrationEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.begoit.mooc.db.DaoSession;
import com.begoit.mooc.db.CourseRegistrationEntityDao;
import com.begoit.mooc.db.PreCourseGroupEntityDao;

/**
 * @Description:
 * @Author:gxj
 * @Time 2019/5/27
 */
@Entity
public class PreCourseGroupEntity {
    @Id
    public String groupId;
    public String groupName;
    public String channelId;
    @ToMany(referencedJoinProperty = "groupId")
    public List<CourseRegistrationEntity> channelPrerequisite;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1153476846)
    private transient PreCourseGroupEntityDao myDao;
    @Generated(hash = 1360576363)
    public PreCourseGroupEntity(String groupId, String groupName,
            String channelId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.channelId = channelId;
    }
    @Generated(hash = 1581188144)
    public PreCourseGroupEntity() {
    }
    public String getGroupId() {
        return this.groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getGroupName() {
        return this.groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
    @Generated(hash = 695085496)
    public List<CourseRegistrationEntity> getChannelPrerequisite() {
        if (channelPrerequisite == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CourseRegistrationEntityDao targetDao = daoSession
                    .getCourseRegistrationEntityDao();
            List<CourseRegistrationEntity> channelPrerequisiteNew = targetDao
                    ._queryPreCourseGroupEntity_ChannelPrerequisite(groupId);
            synchronized (this) {
                if (channelPrerequisite == null) {
                    channelPrerequisite = channelPrerequisiteNew;
                }
            }
        }
        return channelPrerequisite;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 98530363)
    public synchronized void resetChannelPrerequisite() {
        channelPrerequisite = null;
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
    @Generated(hash = 1158916665)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPreCourseGroupEntityDao() : null;
    }

}

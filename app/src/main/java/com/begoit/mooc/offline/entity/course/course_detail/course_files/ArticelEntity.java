package com.begoit.mooc.offline.entity.course.course_detail.course_files;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.begoit.mooc.db.DaoSession;
import com.begoit.mooc.db.VideonobEntityDao;
import com.begoit.mooc.db.ArticelEntityDao;
import com.begoit.mooc.offline.ui.activity.learning.courseTreeListDataBind.ArtcleDataBinder;
import com.begoit.treerecyclerview.annotation.TreeDataType;

/**
 * @Description:课->章->节 实体
 * @Author:gxj
 * @Time 2019/3/21
 */
@TreeDataType(iClass = ArtcleDataBinder.class)
@Entity
public class ArticelEntity {
    @Id
    public String id;//课节id
    @NotNull
    public String chapterId;//章id 数据表外键
    public String sectionName;//节名称
    public String sectionPosition;//节号
    public String sort;//排序

    @ToMany(referencedJoinProperty = "sectionId")
    public List<VideonobEntity> videonob;//节下视频列表
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 264832195)
    private transient ArticelEntityDao myDao;

    @Generated(hash = 879293227)
    public ArticelEntity(String id, @NotNull String chapterId, String sectionName,
            String sectionPosition, String sort) {
        this.id = id;
        this.chapterId = chapterId;
        this.sectionName = sectionName;
        this.sectionPosition = sectionPosition;
        this.sort = sort;
    }

    @Generated(hash = 1093102756)
    public ArticelEntity() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapterId() {
        return this.chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getSectionName() {
        return this.sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1695427757)
    public List<VideonobEntity> getVideonob() {
        if (videonob == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            VideonobEntityDao targetDao = daoSession.getVideonobEntityDao();
            List<VideonobEntity> videonobNew = targetDao
                    ._queryArticelEntity_Videonob(id);
            synchronized (this) {
                if (videonob == null) {
                    videonob = videonobNew;
                }
            }
        }
        return videonob;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1429862391)
    public synchronized void resetVideonob() {
        videonob = null;
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
    @Generated(hash = 74469908)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getArticelEntityDao() : null;
    }

    public String getSectionPosition() {
        return this.sectionPosition;
    }

    public void setSectionPosition(String sectionPosition) {
        this.sectionPosition = sectionPosition;
    }
}

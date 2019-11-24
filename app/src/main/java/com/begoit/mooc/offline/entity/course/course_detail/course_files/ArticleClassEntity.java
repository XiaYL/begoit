package com.begoit.mooc.offline.entity.course.course_detail.course_files;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.begoit.mooc.db.DaoSession;
import com.begoit.mooc.db.ArticelEntityDao;
import com.begoit.mooc.db.ArticleClassEntityDao;
import com.begoit.mooc.offline.ui.activity.learning.courseTreeListDataBind.ArtcleClassDataBinder;
import com.begoit.treerecyclerview.annotation.TreeDataType;

/**
 * @Description:课->章 实体
 * @Author:gxj
 * @Time 2019/3/21
 */
@TreeDataType(iClass = ArtcleClassDataBinder.class)
@Entity
public class ArticleClassEntity  {
    @Id
    public String id;//课章 ID
    @NotNull
    public String channelId;//所属课程ID 外键
    public String chapterName;//章名称
    public String chapterPosition;//章号
    public String sort;//排序
    @ToMany(referencedJoinProperty = "chapterId")
    public List<ArticelEntity> articel;//章下课节列表
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 43718333)
    private transient ArticleClassEntityDao myDao;
    @Generated(hash = 571068883)
    public ArticleClassEntity(String id, @NotNull String channelId, String chapterName,
            String chapterPosition, String sort) {
        this.id = id;
        this.channelId = channelId;
        this.chapterName = chapterName;
        this.chapterPosition = chapterPosition;
        this.sort = sort;
    }
    @Generated(hash = 277851102)
    public ArticleClassEntity() {
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
    public String getChapterName() {
        return this.chapterName;
    }
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
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
    @Generated(hash = 1050244999)
    public List<ArticelEntity> getArticel() {
        if (articel == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ArticelEntityDao targetDao = daoSession.getArticelEntityDao();
            List<ArticelEntity> articelNew = targetDao
                    ._queryArticleClassEntity_Articel(id);
            synchronized (this) {
                if (articel == null) {
                    articel = articelNew;
                }
            }
        }
        return articel;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 215683592)
    public synchronized void resetArticel() {
        articel = null;
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
    @Generated(hash = 1962715950)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getArticleClassEntityDao() : null;
    }
    public String getChapterPosition() {
        return this.chapterPosition;
    }
    public void setChapterPosition(String chapterPosition) {
        this.chapterPosition = chapterPosition;
    }

}

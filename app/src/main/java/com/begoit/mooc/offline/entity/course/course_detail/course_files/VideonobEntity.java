package com.begoit.mooc.offline.entity.course.course_detail.course_files;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.begoit.mooc.db.DaoSession;
import com.begoit.mooc.db.VideoTestEntityDao;
import com.begoit.mooc.db.VideonobEntityDao;
import com.begoit.mooc.offline.ui.activity.learning.courseTreeListDataBind.VideonobDatabinger;
import com.begoit.treerecyclerview.annotation.TreeDataType;

/**
 * @Description:课->章->节->视频 实体
 * @Author:gxj
 * @Time 2019/3/21
 */
@TreeDataType(iClass = VideonobDatabinger.class)
@Entity
public class VideonobEntity  implements Parcelable {
    @Id
    public String id;//id
    @NotNull
    public String sectionId;//课程节id 外键
    public String videoTitle;//视频标题
    public String videoFileId;//视频对应文件ID
    public String videoSubtitleFileId;//视频字幕
    public String videoLength;//视频长度
    public String sort;//排序
    public int isVideo;// 0:视频 1：测试题

    @ToMany(joinProperties = { @JoinProperty(name = "id", referencedName = "videoId") })
    public List<VideoTestEntity> videoTestb;//视频相关测试题列表
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1995638584)
    private transient VideonobEntityDao myDao;

    @Generated(hash = 447418198)
    public VideonobEntity(String id, @NotNull String sectionId, String videoTitle, String videoFileId,
            String videoSubtitleFileId, String videoLength, String sort, int isVideo) {
        this.id = id;
        this.sectionId = sectionId;
        this.videoTitle = videoTitle;
        this.videoFileId = videoFileId;
        this.videoSubtitleFileId = videoSubtitleFileId;
        this.videoLength = videoLength;
        this.sort = sort;
        this.isVideo = isVideo;
    }

    @Generated(hash = 658304547)
    public VideonobEntity() {
    }

    protected VideonobEntity(Parcel in) {
        id = in.readString();
        sectionId = in.readString();
        videoTitle = in.readString();
        videoFileId = in.readString();
        videoSubtitleFileId = in.readString();
        videoLength = in.readString();
        sort = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(sectionId);
        dest.writeString(videoTitle);
        dest.writeString(videoFileId);
        dest.writeString(videoSubtitleFileId);
        dest.writeString(videoLength);
        dest.writeString(sort);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideonobEntity> CREATOR = new Creator<VideonobEntity>() {
        @Override
        public VideonobEntity createFromParcel(Parcel in) {
            return new VideonobEntity(in);
        }

        @Override
        public VideonobEntity[] newArray(int size) {
            return new VideonobEntity[size];
        }
    };

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getVideoTitle() {
        return this.videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoFileId() {
        return this.videoFileId;
    }

    public void setVideoFileId(String videoFileId) {
        this.videoFileId = videoFileId;
    }

    public String getVideoSubtitleFileId() {
        return this.videoSubtitleFileId;
    }

    public void setVideoSubtitleFileId(String videoSubtitleFileId) {
        this.videoSubtitleFileId = videoSubtitleFileId;
    }

    public String getVideoLength() {
        return this.videoLength;
    }

    public void setVideoLength(String videoLength) {
        this.videoLength = videoLength;
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
    @Generated(hash = 1466220214)
    public List<VideoTestEntity> getVideoTestb() {
        if (videoTestb == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            VideoTestEntityDao targetDao = daoSession.getVideoTestEntityDao();
            List<VideoTestEntity> videoTestbNew = targetDao._queryVideonobEntity_VideoTestb(id);
            synchronized (this) {
                if (videoTestb == null) {
                    videoTestb = videoTestbNew;
                }
            }
        }
        return videoTestb;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1131347931)
    public synchronized void resetVideoTestb() {
        videoTestb = null;
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
    @Generated(hash = 1990777238)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getVideonobEntityDao() : null;
    }

    public int getIsVideo() {
        return this.isVideo;
    }

    public void setIsVideo(int isVideo) {
        this.isVideo = isVideo;
    }

}

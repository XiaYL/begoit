package com.begoit.mooc.offline.entity.course.course_detail.course_files;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.ToOne;

import com.begoit.mooc.db.DaoSession;
import com.begoit.mooc.db.FileInformationEntityDao;
import com.begoit.mooc.db.ArticleClassEntityDao;
import com.begoit.mooc.db.CourseDetailFilesEntityDao;
import com.begoit.mooc.db.PreCourseEntityDao;

/**
 * @Description:课程相关文件实体
 * @Author:gxj
 * @Time 2019/3/21
 * 与课程详情相同的字段 值不相同 详情为地址  这里是ID 用于数据库联查操作
 */
@Entity
public class CourseDetailFilesEntity  {
    @Id
    public String id;//课程id
    public String channelName;//课程名称
    public String expectTime;//学习时长
    public String preImgFileid;//预览图片ID
    public String preVideoFileid;//预览视频ID
    public String channelLogoFileid;//课程LOGO ID
    public int videoTestPercent;//平时测试百分比
    public int videoPercent;//视频学习百分比
    public int passScore;//课程及格分数
    public int isNeedapply;//课程是否需要申请
    public String schoolName;//开课学校
    public String channelSize;//课程大小(channel_offline_info-channelsize)
    public int channelStyle;//课程风格，0为教学，1为公开课
    public int channelTerm;//课程期数
    public String vilidDate;//课程时效
    public String hardLevel;//难度
    public int discussionPercent;//讨论百分比
    public int homeworkPercent;//作业得分百分比
    public int finalTestPercent;//结业考试百分比
    public int videoNumber;//课程视频个数
    public int limitVideoTestNum;//视频测试题最多答题次数
    public int teacherScore;//教师调节分

    @ToOne(joinProperty = "id")
    public PreCourseEntity preCourse;//先修课

    @ToMany(referencedJoinProperty = "channelId")
    public List<ArticleClassEntity> articleClass;//课程下课章列表

    @ToMany(referencedJoinProperty = "channelId")
    public List<FileInformationEntity> fileInformation;//课程相关文件总成
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1365514855)
    private transient CourseDetailFilesEntityDao myDao;

    @Generated(hash = 950607456)
    public CourseDetailFilesEntity(String id, String channelName, String expectTime,
            String preImgFileid, String preVideoFileid, String channelLogoFileid,
            int videoTestPercent, int videoPercent, int passScore, int isNeedapply,
            String schoolName, String channelSize, int channelStyle,
            int channelTerm, String vilidDate, String hardLevel,
            int discussionPercent, int homeworkPercent, int finalTestPercent,
            int videoNumber, int limitVideoTestNum, int teacherScore) {
        this.id = id;
        this.channelName = channelName;
        this.expectTime = expectTime;
        this.preImgFileid = preImgFileid;
        this.preVideoFileid = preVideoFileid;
        this.channelLogoFileid = channelLogoFileid;
        this.videoTestPercent = videoTestPercent;
        this.videoPercent = videoPercent;
        this.passScore = passScore;
        this.isNeedapply = isNeedapply;
        this.schoolName = schoolName;
        this.channelSize = channelSize;
        this.channelStyle = channelStyle;
        this.channelTerm = channelTerm;
        this.vilidDate = vilidDate;
        this.hardLevel = hardLevel;
        this.discussionPercent = discussionPercent;
        this.homeworkPercent = homeworkPercent;
        this.finalTestPercent = finalTestPercent;
        this.videoNumber = videoNumber;
        this.limitVideoTestNum = limitVideoTestNum;
        this.teacherScore = teacherScore;
    }

    @Generated(hash = 1270080477)
    public CourseDetailFilesEntity() {
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

    public String getExpectTime() {
        return this.expectTime;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
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

    public String getChannelLogoFileid() {
        return this.channelLogoFileid;
    }

    public void setChannelLogoFileid(String channelLogoFileid) {
        this.channelLogoFileid = channelLogoFileid;
    }

    public int getVideoTestPercent() {
        return this.videoTestPercent;
    }

    public void setVideoTestPercent(int videoTestPercent) {
        this.videoTestPercent = videoTestPercent;
    }

    public int getVideoPercent() {
        return this.videoPercent;
    }

    public void setVideoPercent(int videoPercent) {
        this.videoPercent = videoPercent;
    }

    public int getPassScore() {
        return this.passScore;
    }

    public void setPassScore(int passScore) {
        this.passScore = passScore;
    }

    public int getIsNeedapply() {
        return this.isNeedapply;
    }

    public void setIsNeedapply(int isNeedapply) {
        this.isNeedapply = isNeedapply;
    }

    public String getSchoolName() {
        return this.schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getChannelSize() {
        return this.channelSize;
    }

    public void setChannelSize(String channelSize) {
        this.channelSize = channelSize;
    }

    public int getChannelStyle() {
        return this.channelStyle;
    }

    public void setChannelStyle(int channelStyle) {
        this.channelStyle = channelStyle;
    }

    public int getChannelTerm() {
        return this.channelTerm;
    }

    public void setChannelTerm(int channelTerm) {
        this.channelTerm = channelTerm;
    }

    public String getVilidDate() {
        return this.vilidDate;
    }

    public void setVilidDate(String vilidDate) {
        this.vilidDate = vilidDate;
    }

    public String getHardLevel() {
        return this.hardLevel;
    }

    public void setHardLevel(String hardLevel) {
        this.hardLevel = hardLevel;
    }

    public int getDiscussionPercent() {
        return this.discussionPercent;
    }

    public void setDiscussionPercent(int discussionPercent) {
        this.discussionPercent = discussionPercent;
    }

    public int getHomeworkPercent() {
        return this.homeworkPercent;
    }

    public void setHomeworkPercent(int homeworkPercent) {
        this.homeworkPercent = homeworkPercent;
    }

    public int getFinalTestPercent() {
        return this.finalTestPercent;
    }

    public void setFinalTestPercent(int finalTestPercent) {
        this.finalTestPercent = finalTestPercent;
    }

    public int getVideoNumber() {
        return this.videoNumber;
    }

    public void setVideoNumber(int videoNumber) {
        this.videoNumber = videoNumber;
    }

    public int getLimitVideoTestNum() {
        return this.limitVideoTestNum;
    }

    public void setLimitVideoTestNum(int limitVideoTestNum) {
        this.limitVideoTestNum = limitVideoTestNum;
    }

    public int getTeacherScore() {
        return this.teacherScore;
    }

    public void setTeacherScore(int teacherScore) {
        this.teacherScore = teacherScore;
    }

    @Generated(hash = 2091996383)
    private transient String preCourse__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1259578902)
    public PreCourseEntity getPreCourse() {
        String __key = this.id;
        if (preCourse__resolvedKey == null || preCourse__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PreCourseEntityDao targetDao = daoSession.getPreCourseEntityDao();
            PreCourseEntity preCourseNew = targetDao.load(__key);
            synchronized (this) {
                preCourse = preCourseNew;
                preCourse__resolvedKey = __key;
            }
        }
        return preCourse;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1973728050)
    public void setPreCourse(PreCourseEntity preCourse) {
        synchronized (this) {
            this.preCourse = preCourse;
            id = preCourse == null ? null : preCourse.getChannelId();
            preCourse__resolvedKey = id;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 517867558)
    public List<ArticleClassEntity> getArticleClass() {
        if (articleClass == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ArticleClassEntityDao targetDao = daoSession.getArticleClassEntityDao();
            List<ArticleClassEntity> articleClassNew = targetDao
                    ._queryCourseDetailFilesEntity_ArticleClass(id);
            synchronized (this) {
                if (articleClass == null) {
                    articleClass = articleClassNew;
                }
            }
        }
        return articleClass;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 752539582)
    public synchronized void resetArticleClass() {
        articleClass = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 840991620)
    public List<FileInformationEntity> getFileInformation() {
        if (fileInformation == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FileInformationEntityDao targetDao = daoSession
                    .getFileInformationEntityDao();
            List<FileInformationEntity> fileInformationNew = targetDao
                    ._queryCourseDetailFilesEntity_FileInformation(id);
            synchronized (this) {
                if (fileInformation == null) {
                    fileInformation = fileInformationNew;
                }
            }
        }
        return fileInformation;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1681923290)
    public synchronized void resetFileInformation() {
        fileInformation = null;
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
    @Generated(hash = 1664022709)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCourseDetailFilesEntityDao()
                : null;
    }

}

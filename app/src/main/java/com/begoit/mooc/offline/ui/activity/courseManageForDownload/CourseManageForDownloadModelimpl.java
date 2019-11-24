package com.begoit.mooc.offline.ui.activity.courseManageForDownload;

import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.db.CourseDownLoadedEntityDao;
import com.begoit.mooc.db.HistoryCourseEntityDao;
import com.begoit.mooc.offline.entity.course.learning_space.HistoryCourseEntity;
import com.begoit.mooc.offline.entity.course.user_download.CourseDownLoadedEntity;
import com.begoit.mooc.offline.utils.AppLogUtil;
import com.begoit.mooc.offline.utils.FileHelper;
import com.begoit.mooc.offline.utils.LogUtils;
import com.begoit.mooc.offline.utils.db.DaoManager;
import com.begoit.mooc.offline.utils.download.downloadwithlimit.DownloadLimitManager;

import java.io.File;
import java.util.List;

/**
 * @Description:课程管理数据处理
 * @Author:gxj
 * @Time 2019/3/7
 */

public class CourseManageForDownloadModelimpl implements CourseManageForDownloadContract.Model {
    private List<CourseDownLoadedEntity> downLoadedEntities;
    @Override
    public List<CourseDownLoadedEntity> getDownloadedCourses() {
        downLoadedEntities = DaoManager.getInstance().getDaoSession().getCourseDownLoadedEntityDao()
                .queryBuilder().list();
        return downLoadedEntities;
    }

    @Override
    public boolean deleteCourse(List<CourseDownLoadedEntity> items) {
         boolean isDeleted = false;
        CourseDownLoadedEntity entity;
        HistoryCourseEntity historyCourseEntity;
         try {
             for (CourseDownLoadedEntity item:items) {
                 DownloadLimitManager.getInstance().removeCourse(item.courseId);
                 entity = DaoManager.getInstance().getDaoSession().getCourseDownLoadedEntityDao().queryBuilder()
                         .where(CourseDownLoadedEntityDao.Properties.CourseId.eq(item.courseId)).build().unique();
                 DaoManager.getInstance().getDaoSession().getCourseDownLoadedEntityDao().deleteInTx(entity);

                 historyCourseEntity = DaoManager.getInstance().getDaoSession().getHistoryCourseEntityDao().queryBuilder()
                         .where(HistoryCourseEntityDao.Properties.ChannelId.eq(item.courseId)
                                 ,HistoryCourseEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.Companion.getContextInstance().getCurrentAccound()))
                         .build().unique();
                 if (historyCourseEntity != null) {
                     historyCourseEntity.isDelete = 1;
                     DaoManager.getInstance().getDaoSession().getHistoryCourseEntityDao().insertOrReplaceInTx(historyCourseEntity);
                 }
                 File dir = new File(FileHelper.ABSOLUTEPATH + item.courseId);
                 FileHelper.deleteDirWihtFile(dir);
                 AppLogUtil.INSTANCE.setLog(AppLogUtil.INSTANCE.getTYPE_COURSE_DELETE(),new String[]{item.courseName});
             }
             isDeleted = true;
         }catch (Exception e){
             LogUtils.e(e.getMessage());
             isDeleted = false;
         }finally {
             return isDeleted;
         }
    }
}

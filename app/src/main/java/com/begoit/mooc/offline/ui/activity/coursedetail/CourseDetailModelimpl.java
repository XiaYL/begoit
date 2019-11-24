package com.begoit.mooc.offline.ui.activity.coursedetail;

import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.db.CourseDetailEntityDao;
import com.begoit.mooc.db.CourseDetailFilesEntityDao;
import com.begoit.mooc.db.CourseRegistrationEntityDao;
import com.begoit.mooc.db.HistoryCourseEntityDao;
import com.begoit.mooc.db.UserChannelEntityDao;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.ArticelEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.ArticleClassEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.PreCourseGroupEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideoTestEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideonobEntity;
import com.begoit.mooc.offline.entity.course.learning_space.HistoryCourseEntity;
import com.begoit.mooc.offline.entity.course.user_download.CourseRegistrationEntity;
import com.begoit.mooc.offline.entity.course.user_download.UserChannelEntity;
import com.begoit.mooc.offline.requests.Api;
import com.begoit.mooc.offline.requests.HostType;
import com.begoit.mooc.offline.entity.course.course_detail.CourseDetailEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesEntity;
import com.begoit.mooc.offline.entity.course.teacher.TeacherEntity;
import com.begoit.mooc.offline.utils.DateFormatUtil;
import com.begoit.mooc.offline.utils.db.DaoManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Description:课程详情
 * @Author:gxj
 * @Time 2019/3/14
 */

public class CourseDetailModelimpl implements CourseDetailContract.Model {

    @Override
    public Observable<String> getCourseDetail(Map<String, String> params) {
        return Api.getDefault(HostType.TYPE_APP)
                .channelDetails(params)
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        return responseBody.string();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //讲师数据抽取转换
    private List<TeacherEntity> teacherEntities = new ArrayList<>();
    private TeacherEntity teacherEntity;
    @Override
    public void setCourseDetailToDb(CourseDetailEntity entity) {
        DaoManager.getInstance().getDaoSession().getCourseDetailEntityDao().insertOrReplaceInTx(entity);
        DaoManager.getInstance().getDaoSession().getTeacherConfigEntityDao().insertOrReplaceInTx(entity.getTeacherConfigs());
        if (entity.getTeacherConfigs() != null && entity.getTeacherConfigs().size() > 0){
            teacherEntities.clear();
            for (int i = 0;i < entity.getTeacherConfigs().size();i++){
                teacherEntity = entity.getTeacherConfigs().get(i).getTeacher();
                if (teacherEntity != null) {
                    teacherEntities.add(teacherEntity);
                }
            }
            DaoManager.getInstance().getDaoSession().getTeacherEntityDao().insertOrReplaceInTx(teacherEntities);
        }
    }

    @Override
    public CourseDetailEntity getLocalCourseDetail(String courseId) {
        return DaoManager.getInstance().getDaoSession().getCourseDetailEntityDao()
                .queryBuilder().where(CourseDetailEntityDao.Properties.Id.eq(courseId)).build().unique();
    }

    @Override
    public Observable<String> getCourseDetailFiles(Map<String, String> params) {
        return Api.getDefault(HostType.TYPE_APP)
                .download(params)
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        return responseBody.string();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public boolean setCourseDetailFilesToDb(CourseDetailFilesEntity entity) {
        if (entity == null){
            return false;
        }
        //存储课程基本信息
        DaoManager.getInstance().getDaoSession().getCourseDetailFilesEntityDao().insertOrReplaceInTx(entity);
        //课程相关文件列表 图片、视频等
        if (entity.fileInformation != null){
           DaoManager.getInstance().getDaoSession().getFileInformationEntityDao().insertOrReplaceInTx(entity.fileInformation);
        }

        if (entity.preCourse != null && entity.preCourse.preCourseGroup != null && entity.preCourse.preCourseGroup.size() > 0) {
            DaoManager.getInstance().getDaoSession().getPreCourseEntityDao().insertOrReplaceInTx(entity.preCourse);
            DaoManager.getInstance().getDaoSession().getPreCourseGroupEntityDao().insertOrReplaceInTx(entity.preCourse.preCourseGroup);
            for (PreCourseGroupEntity item:entity.preCourse.preCourseGroup){
                if (item.channelPrerequisite != null && item.channelPrerequisite.size() > 0){
                    DaoManager.getInstance().getDaoSession().getCourseRegistrationEntityDao().insertOrReplaceInTx(item.channelPrerequisite);
                }
            }
        }

        if (entity.articleClass != null) {
            //存储 课程->章 列表
            DaoManager.getInstance().getDaoSession().getArticleClassEntityDao().insertOrReplaceInTx(entity.articleClass);
            for (ArticleClassEntity classEntity:entity.articleClass){
                if (classEntity != null && classEntity.articel != null){
                    //存储 课程->章->节 列表
                    DaoManager.getInstance().getDaoSession().getArticelEntityDao().insertOrReplaceInTx(classEntity.articel);
                    for (ArticelEntity articelEntity:classEntity.articel){
                        //存储 课程->章->节->视频 列表
                        if (articelEntity != null && articelEntity.videonob != null) {
                            for (int i = 0;i < articelEntity.videonob.size();i++) {
                                VideonobEntity videonobEntity = articelEntity.videonob.get(i);
                                videonobEntity.isVideo = 0;
                                DaoManager.getInstance().getDaoSession().getVideonobEntityDao().insertOrReplaceInTx(videonobEntity);
                                if (videonobEntity.videoTestb != null
                                        && videonobEntity.videoTestb.size() > 0){
                                    DaoManager.getInstance().getDaoSession().getVideoTestEntityDao().insertOrReplaceInTx(videonobEntity.videoTestb);
                                    VideonobEntity testVideonobEntity = new VideonobEntity();
                                    testVideonobEntity.id = videonobEntity.id + "*" + videonobEntity.videoTitle;
                                    testVideonobEntity.sectionId = articelEntity.videonob.get(i).sectionId;
                                    testVideonobEntity.isVideo = 1;
                                    testVideonobEntity.videoTitle = "考核";
                                    DaoManager.getInstance().getDaoSession().getVideonobEntityDao().insertOrReplaceInTx(testVideonobEntity);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private UserChannelEntity courseRegistedEntity;
    @Override
    public boolean registCourse(String courseId) {
        try {
            DaoManager.getInstance().getDaoSession().clear();
            List<CourseRegistrationEntity> courseRegistrationEntityList = DaoManager.getInstance().getDaoSession().getCourseRegistrationEntityDao()
                    .queryBuilder().where(CourseRegistrationEntityDao.Properties.ChannelId.eq(courseId)).list();
            if (courseRegistrationEntityList != null && courseRegistrationEntityList.size() > 0){
                for (CourseRegistrationEntity item:courseRegistrationEntityList){

                }
            }

            CourseDetailFilesEntity entity = DaoManager.getInstance().getDaoSession().getCourseDetailFilesEntityDao().queryBuilder()
                    .where(CourseDetailFilesEntityDao.Properties.Id.eq(courseId)).build().unique();
            if (!isCoureRegist(entity.id)) {
                courseRegistedEntity = new UserChannelEntity();
                courseRegistedEntity.id = UUID.randomUUID().toString();
                courseRegistedEntity.channelId = entity.id;
                courseRegistedEntity.isChange = 1;
                courseRegistedEntity.userAccount = BegoitMoocApplication.Companion.getContextInstance().getCurrentAccound();
                courseRegistedEntity.addTime = DateFormatUtil.longToString(System.currentTimeMillis());
                courseRegistedEntity.updateTime = DateFormatUtil.longToString(System.currentTimeMillis());
                courseRegistedEntity.videoTestScore = 0;
                courseRegistedEntity.videoTestSumScore = getVideoTestSumScore(entity);
                courseRegistedEntity.videoFinishScore = 0;
                courseRegistedEntity.videoFinishSumCount = entity.videoNumber;
                courseRegistedEntity.normalSumScore = String.valueOf(0);
                courseRegistedEntity.channelStyle = entity.channelStyle;
                courseRegistedEntity.teacherApproveTime = DateFormatUtil.longToString(System.currentTimeMillis());
                courseRegistedEntity.channelTerm = entity.channelTerm;
                DaoManager.getInstance().getDaoSession().getUserChannelEntityDao().insertOrReplaceInTx(courseRegistedEntity);
                return true;
            } else {
                return true;
            }
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 遍历课程测试题统计总分
     * @param entity
     */
    private int getVideoTestSumScore(CourseDetailFilesEntity entity){
        int sumScore = 0;
        for (ArticleClassEntity item:entity.getArticleClass()){
            if (item.getArticel() != null){
                for (ArticelEntity articelEntity:item.getArticel()){
                    if (articelEntity.getVideonob() != null){
                        for (VideonobEntity videonobEntity:articelEntity.getVideonob()){
                            if(videonobEntity.getVideoTestb() != null && videonobEntity.getVideoTestb().size() > 0){
                                for (VideoTestEntity videoTestEntity:videonobEntity.getVideoTestb()){
                                    sumScore = sumScore + videoTestEntity.score;
                                }
                            }
                        }
                    }
                }
            }
        }
        return sumScore;
    }

    /**
     * 判断课程是否已注册
     * @param courseId
     * @return
     */
    @Override
    public boolean isCoureRegist(String courseId) {

        courseRegistedEntity = DaoManager.getInstance().getDaoSession().getUserChannelEntityDao().queryBuilder()
                .where(UserChannelEntityDao.Properties.ChannelId.eq(courseId)
                        ,UserChannelEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.Companion.getContextInstance().getCurrentAccound()))
                .build().unique();
        if (courseRegistedEntity == null){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 判断课程是否达到注册条件
     * @param courseId
     * @return
     */
    private CourseDetailFilesEntity courseDetailFilesEntity;
    @Override
    public boolean isCanRigist(String courseId){
        courseDetailFilesEntity = DaoManager.getInstance().getDaoSession().getCourseDetailFilesEntityDao().queryBuilder()
                .where(CourseDetailFilesEntityDao.Properties.Id.eq(courseId)).build().unique();
        if (courseDetailFilesEntity == null){
            return false;
        }
        if (courseDetailFilesEntity.getPreCourse() != null && courseDetailFilesEntity.getPreCourse().getPreCourseGroup() != null
                && courseDetailFilesEntity.getPreCourse().getPreCourseGroup().size() > 0){
            for (PreCourseGroupEntity item: courseDetailFilesEntity.getPreCourse().getPreCourseGroup()){
                if (item.getChannelPrerequisite() != null && item.getChannelPrerequisite().size() > 0){
                    if (isChannelPrerequisiteSuccess(item.getChannelPrerequisite())){
                            return true;
                    }
                }
            }
            return false;
        }else {
            return true;
        }
    }
    private UserChannelEntity userChannelEntity;
    private CourseDetailFilesEntity preCourseEntity;

    /**
     * 判断先修课学习情况
     * @param channelPrerequisite
     * @return
     */
    private boolean isChannelPrerequisiteSuccess(List<CourseRegistrationEntity> channelPrerequisite){
        for (CourseRegistrationEntity item: channelPrerequisite){
            userChannelEntity = DaoManager.getInstance().getDaoSession().getUserChannelEntityDao().queryBuilder()
                    .where(UserChannelEntityDao.Properties.ChannelId.eq(item.preChannelId)
                    ,UserChannelEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.Companion.getContextInstance().getCurrentAccound())).build().unique();
            if (userChannelEntity == null){
                return false;
            }else {
                preCourseEntity = DaoManager.getInstance().getDaoSession().getCourseDetailFilesEntityDao().queryBuilder()
                        .where(CourseDetailFilesEntityDao.Properties.Id.eq(item.preChannelId)).build().unique();
                if (preCourseEntity == null){
                    return false;
                }else {
                    if (Float.parseFloat(userChannelEntity.sumScore) < preCourseEntity.passScore) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 在课程下载成功后，将已注册课程标记的已删除标签改为未删除
     * @param coursedId
     */
    @Override
    public void reviewRegistedCourse(String coursedId) {
         List<HistoryCourseEntity> list = DaoManager.getInstance().getDaoSession().getHistoryCourseEntityDao()
                 .queryBuilder().where(HistoryCourseEntityDao.Properties.ChannelId.eq(coursedId)).list();
         if (list != null && list.size() > 0) {
             for (HistoryCourseEntity item : list) {
                 item.isDelete = 0;
                 DaoManager.getInstance().getDaoSession().getHistoryCourseEntityDao().updateInTx(item);
             }
         }

    }

}

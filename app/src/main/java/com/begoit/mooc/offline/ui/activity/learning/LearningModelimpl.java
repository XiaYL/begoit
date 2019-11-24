package com.begoit.mooc.offline.ui.activity.learning;

import android.text.TextUtils;

import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.db.ArticelEntityDao;
import com.begoit.mooc.db.ArticleClassEntityDao;
import com.begoit.mooc.db.CourseDetailEntityDao;
import com.begoit.mooc.db.CourseDetailFilesEntityDao;
import com.begoit.mooc.db.FileInformationEntityDao;
import com.begoit.mooc.db.HistoryCourseEntityDao;
import com.begoit.mooc.db.UserChannelEntityDao;
import com.begoit.mooc.db.VideoTestEntityDao;
import com.begoit.mooc.db.VideoTestScoreEntityDao;
import com.begoit.mooc.db.VideonobEntityDao;
import com.begoit.mooc.offline.entity.course.course_detail.CourseDetailEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.ArticelEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.ArticleClassEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.FileInformationEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideoTestEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideonobEntity;
import com.begoit.mooc.offline.entity.course.learning_space.HistoryCourseEntity;
import com.begoit.mooc.offline.entity.course.user_download.UserChannelEntity;
import com.begoit.mooc.offline.entity.course.user_download.VideoTestLogEntity;
import com.begoit.mooc.offline.entity.course.user_download.VideoTestScoreEntity;
import com.begoit.mooc.offline.ui.activity.learning.event.ShowScoreEvent;
import com.begoit.mooc.offline.utils.CourseScoreUtil;
import com.begoit.mooc.offline.utils.DateFormatUtil;
import com.begoit.mooc.offline.utils.LogUtils;
import com.begoit.mooc.offline.utils.CourseComputeUtil;
import com.begoit.mooc.offline.utils.db.DaoManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Description:学习页面数据处理
 * @Author:gxj
 * @Time 2019/3/4
 */

public class LearningModelimpl implements LearningContract.Model {

    @Override
    public String getSumScore(CourseDetailFilesEntity entity) {
        UserChannelEntity userChannelEntity =  DaoManager.getInstance().getDaoSession().getUserChannelEntityDao().queryBuilder()
                .where(UserChannelEntityDao.Properties.ChannelId.eq(entity.id)
                        ,UserChannelEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.Companion.getContextInstance().getCurrentAccound())).build().unique();
       if (entity != null && userChannelEntity != null){
           return CourseScoreUtil.Companion.getSumScore(entity,userChannelEntity);
       }
        return String.valueOf(0);
    }

    @Override
    public CourseDetailFilesEntity getCurrentCourse(String courseId) {
        DaoManager.getInstance().getDaoSession().clear();
        return DaoManager.getInstance().getDaoSession().getCourseDetailFilesEntityDao().queryBuilder()
                .where(CourseDetailFilesEntityDao.Properties.Id.eq(courseId)).build().unique();
    }
    private CourseDetailEntity courseDetailEntity;
    private HistoryCourseEntity historyCourseEntity;
    @Override
    public void setHistoryCourse(String courseId) {
        historyCourseEntity = DaoManager.getInstance().getDaoSession().getHistoryCourseEntityDao().queryBuilder()
                .where(HistoryCourseEntityDao.Properties.Id.eq(courseId + BegoitMoocApplication.Companion.getContextInstance().getCurrentUser().userId))
                .build().unique();
        if (historyCourseEntity == null){
            courseDetailEntity = DaoManager.getInstance().getDaoSession().getCourseDetailEntityDao().queryBuilder()
                    .where(CourseDetailEntityDao.Properties.Id.eq(courseId)).build().unique();
            if (courseDetailEntity != null) {
                historyCourseEntity = new HistoryCourseEntity();
                historyCourseEntity.id = courseId + BegoitMoocApplication.Companion.getContextInstance().getCurrentUser().userId;
                historyCourseEntity.channelId = courseId;
                historyCourseEntity.courseName = courseDetailEntity.channelName;
                historyCourseEntity.preImgFileid = courseDetailEntity.preImgFileid;
                historyCourseEntity.courseWithSchool = courseDetailEntity.schoolName;
                historyCourseEntity.userAccount = BegoitMoocApplication.Companion.getContextInstance().getCurrentAccound();
                historyCourseEntity.isDelete = 0;
                DaoManager.getInstance().getDaoSession().getHistoryCourseEntityDao().insertOrReplaceInTx(historyCourseEntity);
            }
        }
    }
    @Override
    public FileInformationEntity getCurrentVideo(String videoId) {
        DaoManager.getInstance().getDaoSession().clear();
        return DaoManager.getInstance().getDaoSession().getFileInformationEntityDao().queryBuilder()
                .where(FileInformationEntityDao.Properties.FileId.eq(videoId)).build().unique();
    }

    @Override
    public List<VideoTestEntity> getTestList(int limitVideoTestNum,String videoId) {
        DaoManager.getInstance().getDaoSession().clear();
        currentVideoTestScoreEntity = DaoManager.getInstance().getDaoSession().getVideoTestScoreEntityDao().queryBuilder()
                .where(VideoTestScoreEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.Companion.getContextInstance().getCurrentAccound())
                        ,VideoTestScoreEntityDao.Properties.VideoId.eq(videoId)).build().unique();
        if (currentVideoTestScoreEntity != null && !(currentVideoTestScoreEntity.answerCount < limitVideoTestNum)){
            return null;
        }else {
            return DaoManager.getInstance().getDaoSession().getVideoTestEntityDao().queryBuilder()
                    .where(VideoTestEntityDao.Properties.VideoId.eq(videoId)).list();
        }
    }

    //视频与测试记分入数据库
    private VideoTestScoreEntity currentVideoTestScoreEntity;//视频与测试记分表
    private ArticleClassEntity currentChapter;//当前所属章
    private ArticelEntity currentSection;//当前所属节
    private VideonobEntity currentVideo;//当前操作所属Video
    @Override
    public void setScore(String videoId,CourseDetailFilesEntity currentCourse, ShowScoreEvent event,boolean isVideo) {
        DaoManager.getInstance().getDaoSession().clear();
        currentVideo = DaoManager.getInstance().getDaoSession().getVideonobEntityDao()
                .queryBuilder().where(VideonobEntityDao.Properties.Id.eq(TextUtils.isEmpty(videoId)?event.testList.get(0).videoId:videoId)).unique();

        currentSection = DaoManager.getInstance().getDaoSession().getArticelEntityDao()
                .queryBuilder().where(ArticelEntityDao.Properties.Id.eq(currentVideo.sectionId)).unique();

        currentChapter = DaoManager.getInstance().getDaoSession().getArticleClassEntityDao()
                .queryBuilder().where(ArticleClassEntityDao.Properties.Id.eq(currentSection.chapterId)).unique();

        currentVideoTestScoreEntity = DaoManager.getInstance().getDaoSession().getVideoTestScoreEntityDao().queryBuilder()
                .where(VideoTestScoreEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.Companion.getContextInstance().getCurrentAccound())
                        ,VideoTestScoreEntityDao.Properties.VideoId.eq(currentVideo.id)).build().unique();

        if (currentVideoTestScoreEntity == null){
            currentVideoTestScoreEntity = new VideoTestScoreEntity();
            currentVideoTestScoreEntity.id = UUID.randomUUID().toString();
            currentVideoTestScoreEntity.isChange = 1;
            currentVideoTestScoreEntity.userAccount = BegoitMoocApplication.Companion.getContextInstance().getCurrentAccound();
            currentVideoTestScoreEntity.videoId = currentVideo.id;
            currentVideoTestScoreEntity.sectionId = currentSection.id;
            currentVideoTestScoreEntity.chapterId = currentChapter.id;
            currentVideoTestScoreEntity.channelId = currentCourse.id;
            currentVideoTestScoreEntity.addTime = DateFormatUtil.longToString(System.currentTimeMillis());
            currentVideoTestScoreEntity.updateTime = currentVideoTestScoreEntity.addTime;
            currentVideoTestScoreEntity.ip = "127.0.0.1";
            currentVideoTestScoreEntity.channelTerm = String.valueOf(currentCourse.channelTerm);
            currentVideoTestScoreEntity.videoLength = Integer.parseInt(currentVideo.videoLength);
            if (isVideo){
                currentVideoTestScoreEntity.watch = 1;
                currentVideoTestScoreEntity.videoCompleteTime = 0;
            }else{
                currentVideoTestScoreEntity.score = event.hasScore;
                currentVideoTestScoreEntity.answerCount = 1;
                currentVideoTestScoreEntity.answerTotalScore = event.hasScore;
                currentVideoTestScoreEntity.videoTestId = UUID.randomUUID().toString();
                setVideoTestLog(event.testList,currentVideoTestScoreEntity);
            }
        }else {
            currentVideoTestScoreEntity.isChange = 1;
            currentVideoTestScoreEntity.updateTime = DateFormatUtil.longToString(System.currentTimeMillis());
            if (isVideo){
                currentVideoTestScoreEntity.watch = 1;
                currentVideoTestScoreEntity.videoCompleteTime = 0;
            }else{
                currentVideoTestScoreEntity.score = event.hasScore;
                currentVideoTestScoreEntity.answerCount = currentVideoTestScoreEntity.answerCount + 1;
                currentVideoTestScoreEntity.answerTotalScore = currentVideoTestScoreEntity.answerTotalScore + event.hasScore;
                currentVideoTestScoreEntity.videoTestId = UUID.randomUUID().toString();
                setVideoTestLog(event.testList,currentVideoTestScoreEntity);
            }
        }
        DaoManager.getInstance().getDaoSession().getVideoTestScoreEntityDao().insertOrReplace(currentVideoTestScoreEntity);
        //视频或测试题成绩计入后，重置用户选课表记分
        computeScoreInChannel(currentVideoTestScoreEntity);
    }

    /**
     * 测试题回答日志计入数据库
     * @param currentVideoTestScoreEntity
     */
    private VideoTestLogEntity videoTestLogEntity;
    private List<VideoTestLogEntity> videoTestLogEntityList = null;
    private void setVideoTestLog(List<VideoTestEntity> testList,VideoTestScoreEntity currentVideoTestScoreEntity){
        if (videoTestLogEntityList == null){
            videoTestLogEntityList = new ArrayList<>();
        }
        videoTestLogEntityList.clear();

        for (VideoTestEntity item:testList) {
            videoTestLogEntity = new VideoTestLogEntity();
            videoTestLogEntity.id = UUID.randomUUID().toString();
            videoTestLogEntity.isChange = 1;
            videoTestLogEntity.videoTestId = item.id;
            videoTestLogEntity.userAccount = currentVideoTestScoreEntity.userAccount;
            videoTestLogEntity.addTime = currentVideoTestScoreEntity.addTime;
            videoTestLogEntity.answer = item.userAnswer;
            if (item.answer.equals(item.userAnswer)){
                videoTestLogEntity.score = item.score;
            }else {
                videoTestLogEntity.score = 0;
            }
            videoTestLogEntity.ip = currentVideoTestScoreEntity.ip;
            videoTestLogEntity.uuid = UUID.randomUUID().toString();
            videoTestLogEntity.times = currentVideoTestScoreEntity.answerCount;
            videoTestLogEntityList.add(videoTestLogEntity);
        }

        DaoManager.getInstance().getDaoSession().getVideoTestLogEntityDao().insertInTx(videoTestLogEntityList);
    }

    /**
     *用户选课表中成绩计算
     * @param entity
     */
    private UserChannelEntity courseRegistedEntity;
    private CourseDetailFilesEntity courseDetailFilesEntity;
    private void computeScoreInChannel(VideoTestScoreEntity entity){
        DaoManager.getInstance().getDaoSession().clear();
        courseRegistedEntity = DaoManager.getInstance().getDaoSession().getUserChannelEntityDao().queryBuilder()
                .where(UserChannelEntityDao.Properties.ChannelId.eq(entity.channelId)
                        ,UserChannelEntityDao.Properties.UserAccount.eq(entity.userAccount))
                .build().unique();
        courseDetailFilesEntity = DaoManager.getInstance().getDaoSession().getCourseDetailFilesEntityDao().queryBuilder()
                .where(CourseDetailFilesEntityDao.Properties.Id.eq(entity.channelId)).build().unique();
        if (courseRegistedEntity != null && courseDetailFilesEntity != null){
            List<VideoTestScoreEntity> videoTestScoreEntityList = DaoManager.getInstance().getDaoSession().getVideoTestScoreEntityDao().queryBuilder()
                    .where(VideoTestScoreEntityDao.Properties.UserAccount.eq(entity.userAccount)
                            ,VideoTestScoreEntityDao.Properties.ChannelId.eq(entity.channelId)).list();
            if (videoTestScoreEntityList !=null && videoTestScoreEntityList.size() > 0){
                int videoTestScore = 0;
                int videoFinishScore = 0;
                for (VideoTestScoreEntity item:videoTestScoreEntityList){
                    videoTestScore = videoTestScore + item.score;
                    videoFinishScore = videoFinishScore + item.watch;
                }
                courseRegistedEntity.videoTestScore = videoTestScore;
                courseRegistedEntity.videoFinishScore = videoFinishScore;
                courseRegistedEntity.isChange = 1;

                //平时总成绩
                float normalSumScore = (
                        (computeEndScore(videoFinishScore,courseRegistedEntity.videoFinishSumCount,courseDetailFilesEntity.videoPercent))//视频得分/视频总分*视频百分比
                              + (computeEndScore(videoTestScore,courseRegistedEntity.videoTestSumScore,courseDetailFilesEntity.videoTestPercent))//测试题得分/测试题总分*测试题百分比
                              + (computeEndScore((int)courseRegistedEntity.homeworkScore,courseRegistedEntity.homeworkSumScore,courseDetailFilesEntity.homeworkPercent))//作业得分/作业总分*作业百分比
                              + ((float)courseRegistedEntity.exchangeScore)//讨论得分
                              + ((float)courseDetailFilesEntity.teacherScore));//教师调分

                //平时总成绩大于满分(100-channel.FINALTESTPERCENT)时以满分为准
                //SUMSCORESTUDY = SUMSCORESTUDY > (100-channel.FINALTESTPERCENT)?(100-channel.FINALTESTPERCENT):SUMSCORESTUDY;
                courseRegistedEntity.normalSumScore = String.valueOf(normalSumScore > (100 - courseRegistedEntity.examScore)?
                        (100 - courseRegistedEntity.examScore):normalSumScore);

                //总分计算(平时总成绩+(结业考试得分/100*结业考试百分比))
                courseRegistedEntity.sumScore = String.valueOf(courseRegistedEntity.examStatus == -1 ?
                        0 : (Float.parseFloat(courseRegistedEntity.normalSumScore)
                        + computeEndScore(courseRegistedEntity.examScore,100 , courseDetailFilesEntity.finalTestPercent)));
                //如果最后总分大于100.00取100.00
                //SUMSCORE = SUMSCORE > 100.00?100.00:SUMSCORE;
                courseRegistedEntity.sumScore = Float.parseFloat(courseRegistedEntity.sumScore) > 100.00f ? String.valueOf(100.00) :  courseRegistedEntity.sumScore;

                courseRegistedEntity.normalSumScore = CourseComputeUtil.Companion.hasTwoLength(Double.parseDouble(courseRegistedEntity.normalSumScore));
                courseRegistedEntity.sumScore = CourseComputeUtil.Companion.hasTwoLength(Double.parseDouble(courseRegistedEntity.sumScore));
                LogUtils.i("平时总成绩 " + courseRegistedEntity.normalSumScore + "  总分  " + courseRegistedEntity.sumScore) ;
                courseRegistedEntity.updateTime = DateFormatUtil.longToString(System.currentTimeMillis());
                //更新到数据库
                DaoManager.getInstance().getDaoSession().getUserChannelEntityDao().updateInTx(courseRegistedEntity);

            }
        }else {
            LogUtils.e("计算课程得分异常");
        }
    }

    /**
     * 根据占比情况计算最终得分
     * @param hasScore  得分
     * @param sumScore  总分
     * @param percent   占比
     * @return
     */
    private float computeEndScore(int hasScore,int sumScore,int percent){
        if (hasScore == 0 || sumScore == 0 || percent == 0 ){
              return 0.0f;
        }
        return (float) hasScore / (float)sumScore * (float)percent;
    }

}

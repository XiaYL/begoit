package com.begoit.mooc.offline.ui.activity.coursedetail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.db.CourseDownLoadedEntityDao;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.FileInformationEntity;
import com.begoit.mooc.offline.entity.course.user_download.CourseDownLoadedEntity;
import com.begoit.mooc.offline.requests.ApiConstants;
import com.begoit.mooc.offline.base.BaseActivity;
import com.begoit.mooc.offline.entity.course.course_detail.CourseDetailEntity;
import com.begoit.mooc.offline.entity.course.teacher.TeacherConfigEntity;
import com.begoit.mooc.offline.entity.course.teacher.TeacherEntity;
import com.begoit.mooc.offline.ui.activity.courseManageForDownload.CourseManageForDownloadActivity;
import com.begoit.mooc.offline.ui.activity.learning.LearningActivity;
import com.begoit.mooc.offline.ui.service.DownloadCourseService;
import com.begoit.mooc.offline.utils.AppLogUtil;
import com.begoit.mooc.offline.utils.CourseComputeUtil;
import com.begoit.mooc.offline.utils.FileHelper;
import com.begoit.mooc.offline.utils.ImagePlaceHolderUtil;
import com.begoit.mooc.offline.utils.LogUtils;
import com.begoit.mooc.offline.utils.db.DaoManager;
import com.begoit.mooc.offline.utils.download.DownloadInfo;
import com.begoit.mooc.offline.utils.download.downloadwithlimit.DownloadLimitManager;
import com.begoit.mooc.offline.ui.fragment.courseLector.CourseLectorsFragment;
import com.begoit.mooc.offline.ui.fragment.courseSummary.CourseSummaryFragment;
import com.begoit.mooc.offline.utils.ToastUtil;
import com.begoit.mooc.offline.widget.BaseEmptyView;
import com.begoit.mooc.offline.widget.LoadingProgressDialog;
import com.begoit.mooc.offline.widget.basedialog.DialogUtils;
import com.begoit.mooc.offline.widget.imageView.GlideCircleTransform;
import com.begoit.mooc.offline.widget.videoview.VideoPlayer;
import com.begoit.mooc.offline.widget.viewpager.ColorBar;
import com.begoit.mooc.offline.widget.viewpager.IndicatorViewPager;
import com.begoit.mooc.offline.widget.viewpager.OnTransitionTextListener;
import com.begoit.mooc.offline.widget.viewpager.ScrollIndicatorView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 课程详情
 */
public class CourseDetailActivity extends BaseActivity<CourseDetailPresenter,CourseDetailModelimpl>
        implements DialogUtils.ButtonClickCallBack
        ,CourseDetailContract.View {
    //标题
    @BindView(R.id.tv_title)
    TextView title;
    //数据异常页面
    @BindView(R.id.empty)
    BaseEmptyView emptyView;
    //悬挂View参考线
    @BindView(R.id.v_contrast_line)
    View contrastLine;
    //可滑动View，用于监听悬挂时机
    @BindView(R.id.nsv_container)
    NestedScrollView nsvContainer;
    //悬挂View
    @BindView(R.id.suspension_view)
    LinearLayout suspensionView;
    //悬挂view垂直位置
    private int suspensionLocationY;
     //顶部导航栏
    @BindView(R.id.sivMoretabIndicator)
     ScrollIndicatorView myScrollIndicatorView;
    //悬挂导航栏
    @BindView(R.id.suspension_moretabindicator)
    ScrollIndicatorView suspensionMretabindicator;
     //底部内容
    @BindView(R.id.vp_content)
    public ViewPager myViewPager;
    //联动适配器
    private IndicatorViewPager mIndicatorViewPager;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private CourseSummaryFragment courseSummaryFragment;//课程简介
    private CourseLectorsFragment courseLectorsFragment;//授课老师
    private CourseSummaryFragment knowlageFragment;//所需知识
    private CourseSummaryFragment programFragment;//教学大纲

    //当前tab index
    private int previousPosition = 0;
    private ArrayList<String> columnArrayList = new ArrayList();
    private MyAdapter myAdapter;
    //课程名称
    @BindView(R.id.tv_course_name)
    TextView tvCourseName;
    //授课学校名称
    @BindView(R.id.tv_course_school)
    TextView tvCourseSchool;
    //课程难度
    @BindView(R.id.tv_course_level)
    TextView tvCourseLevel;
    //课程时长
    @BindView(R.id.tv_course_duration)
    TextView tvCourseDuration;
    //学习人数
    @BindView(R.id.tv_course_selected_number_of_people)
    TextView tvCourseWithStudyCount;

    //视频播放组件
    @BindView(R.id.video_view)
    VideoPlayer videoView;

    @BindView(R.id.iv_video_placeImg)
    ImageView ivVideoPlaceImg;

    //退出页面
    @OnClick(R.id.iv_back)
    public void clickBack(){
        finish();
    }

    //课程状态按钮 下载注册或者学习
    @BindView(R.id.tv_course_status)
    TextView tvCourseStatus;

    private boolean isGotoLearning = false;

    //至课程管理页面按钮
    @OnClick(R.id.fl_download_container)
    public void toManage(){
        toManager();
    }
    @BindView(R.id.download_count)
    TextView tvDownloaCount;
    private int downloaCount;
    //数据库已下载课程表格对应对象，用于标识课程下载注册状态，
    private CourseDownLoadedEntity mCourseDownLoadedEntity;
    //点击下载课程
    @OnClick(R.id.tv_course_status)
    public void onClickDownload(){
        if (tvCourseStatus.getText().toString().equals("下载课程") || tvCourseStatus.getText().toString().equals("下载失败，重新下载")){
            if (FileHelper.isFree()) {
                showLoading();
                if (targetCourseId != null) {
                    Map<String, String> params = new HashMap();
                    params.put("channelId", targetCourseId);
                    mPresenter.getCourseDetailFiles(params);
                }
            }else {
                DialogUtils.getInstance()
                     .showGenericDialogForTwoButtonCommon(this, "无法下载", "可用空间不足，无法下载该课程"
                             , "取消", "删除", new DialogUtils.ButtonClickCallBack() {
                                 @Override
                                 public void clickCallBack(View view) {
                                     if (view.getId() == R.id.tv_sure){
                                         Intent intent = new Intent(mContext, CourseManageForDownloadActivity.class);
                                         startActivity(intent);
                                     }
                                 }
                             });
            }
        }else if (tvCourseStatus.getText().toString().equals("已注册，开始学习")){
            isGotoLearning = true;
            Intent intent = new Intent(this, LearningActivity.class);
            intent.putExtra("currentId", currentEntity.id);
            intent.putExtra("preImgFileid",currentEntity.getPreImgFileid());
            startActivity(intent);
        } else if (tvCourseStatus.getText().toString().equals("已下载，注册学习")){
            if (mPresenter.isCanRigist(targetCourseId)) {
                DialogUtils.getInstance()
                        .showGenericDialogForTwoButtonCommon(this, "注册课程", "课程已下载，注册开始学习"
                                , "取消", "注册", new DialogUtils.ButtonClickCallBack() {
                                    @Override
                                    public void clickCallBack(View view) {
                                        if (view.getId() == R.id.tv_sure) {
                                            mPresenter.registCourse(targetCourseId);
                                        }
                                    }
                                });
            }else {
                ToastUtil.showShortToast("课程所含先修课未学完，不能注册");
            }
        }else if (tvCourseStatus.getText().toString().equals("下载中，查看下载进度")){
            toManager();
        }
    }

    @Override
    public void registStatus(boolean isRegist) {
        if (isRegist){
            tvCourseStatus.setBackgroundResource(R.drawable.bg_green_round_no_line);
            tvCourseStatus.setText("已注册，开始学习");
            isGotoLearning = true;
            Intent intent = new Intent(CourseDetailActivity.this, LearningActivity.class);
            intent.putExtra("currentId", mCourseDownLoadedEntity.courseId);
            startActivity(intent);
        }else {
            ToastUtil.showShortToast("注册失败，请稍后再试");
        }
    }

    private String targetCourseId;
    private CourseDetailEntity currentEntity;
    //标记是否打开自课程管理
    private boolean isFromManage;
    /**
     * 转场课程管理
     */
    private void toManager(){
        if (isFromManage){
            finish();
        }else {
            Intent intent = new Intent(mContext, CourseManageForDownloadActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected int getLyoutId() {
        return R.layout.activity_course_detail;
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this,mModel);
    }

    @Override
    protected void initView()
    {
        EventBus.getDefault().register(this);
        isFromManage = getIntent().getBooleanExtra("isFromManage",false);
        targetCourseId = getIntent().getStringExtra("courseId");

        initCourseInfoTab();
        initVideoView("","");
        loadData();
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    /**
     * 课程数据获取
     */
    private void loadData(){
        showLoading();
        if (targetCourseId != null) {
            Map<String,String> params = new HashMap();
            params.put("channelId",targetCourseId);
            mPresenter.getCourseDetail(params);
        }
    }


     //初始化播放器
    private void initVideoView(String videoUrl,String thumbImageUrl) {
        if (TextUtils.isEmpty(videoUrl)){
            ivVideoPlaceImg.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            if (TextUtils.isEmpty(thumbImageUrl)) {
                ivVideoPlaceImg.setImageResource(ImagePlaceHolderUtil.INSTANCE.getPlaceholderPreImg(targetCourseId));
            } else {
                Glide.with(this)
                        .load(ApiConstants.getFileUrl(thumbImageUrl))
                        .apply(RequestOptions
                                .diskCacheStrategyOf(DiskCacheStrategy.ALL)
                                .bitmapTransform(new GlideCircleTransform(mContext, 1f))
                                )
                        .into(ivVideoPlaceImg);
            }
        }else {
            videoView.setVisibility(View.VISIBLE);
            ivVideoPlaceImg.setVisibility(View.GONE);
            videoView.titleTextView.setVisibility(View.GONE);
            videoView.setUp(videoUrl
                    , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, videoUrl);
            videoView.fullscreenButton.setVisibility(View.INVISIBLE);
            videoView.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if (TextUtils.isEmpty(thumbImageUrl)) {
                videoView.thumbImageView.setImageResource(ImagePlaceHolderUtil.INSTANCE.getPlaceholderPreImg(targetCourseId));
            } else {
                Glide.with(this)
                        .load(ApiConstants.getFileUrl(thumbImageUrl))
                        .apply(RequestOptions
                                .diskCacheStrategyOf(DiskCacheStrategy.ALL)
                                .bitmapTransform(new GlideCircleTransform(mContext, 1f))
                                .error(ImagePlaceHolderUtil.INSTANCE.getPlaceImg()))
                        .into(videoView.thumbImageView);
            }
        }
    }

//    private long lastTotalRxBytes = 0;
//    private long lastTimeStamp = 0;
    @Override
    protected void onResume() {
        super.onResume();
        resetDownloaCount();
        if (currentEntity != null) {
            setButtonStatus();
        }

        if (isGotoLearning){
            isGotoLearning = false;
        }else{
            JZVideoPlayer.goOnPlayOnResume();
        }
//        lastTotalRxBytes = getTotalRxBytes();
//        lastTimeStamp = System.currentTimeMillis();
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (!isFinishing()){
//                    long nowTotalRxBytes = getTotalRxBytes();
//                    long nowTimeStamp = System.currentTimeMillis();
//                    final long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
//                    final long speed2 = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 % (nowTimeStamp - lastTimeStamp));//毫秒转换
//
//                    lastTimeStamp = nowTimeStamp;
//                    lastTotalRxBytes = nowTotalRxBytes;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            title.setText(String.valueOf(speed) + "." + String.valueOf(speed2) + " kb/s");
//                        }
//                    });
//                }
//            }
//        },500,500);

    }

    /**
     * 即时重置下载状态按钮
     */
    private void resetDownloaCount(){
        if (!isFinishing() && tvDownloaCount != null) {
            downloaCount = CourseComputeUtil.Companion.computeDoloadingCourseCount();
            if (downloaCount > 0) {
                tvDownloaCount.setVisibility(View.VISIBLE);
                tvDownloaCount.setText(downloaCount + "");
            }else {
                tvDownloaCount.setVisibility(View.GONE);
            }
        }
    }

    private long getTotalRxBytes() {
        return TrafficStats.getUidRxBytes(this.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024);//转为KB
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            JZVideoPlayer.goOnPlayOnPause();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        JZVideoPlayer.releaseAllVideos();
        EventBus.getDefault().unregister(this);
    }

    private void initCourseInfoTab(){
        nsvContainer.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int[] locations = new int[2];
                myScrollIndicatorView.getLocationOnScreen(locations);
                if (locations[1] <= suspensionLocationY) {
                    if (mIndicatorViewPager.getIndicatorView() != suspensionMretabindicator){
                        mIndicatorViewPager.changeindicatorView(suspensionMretabindicator,previousPosition);
                        refreshTabItem(previousPosition);
                        suspensionView.setVisibility(View.VISIBLE);
                    }
                }else {
                    if (mIndicatorViewPager.getIndicatorView() != myScrollIndicatorView) {
                        mIndicatorViewPager.changeindicatorView(myScrollIndicatorView,previousPosition);
                        refreshTabItem(previousPosition);
                        suspensionView.setVisibility(View.GONE);
                    }
                }
            }
        });

        columnArrayList.add("课程简介");
        columnArrayList.add("授课老师");
        columnArrayList.add("所需知识");
        columnArrayList.add("教学大纲");

        courseSummaryFragment = new CourseSummaryFragment();
        courseLectorsFragment = new CourseLectorsFragment();
        knowlageFragment = new CourseSummaryFragment();
        programFragment = new CourseSummaryFragment();

        mFragmentList.add(courseSummaryFragment);
        mFragmentList.add(courseLectorsFragment);
        mFragmentList.add(knowlageFragment);
        mFragmentList.add(programFragment);

        myScrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener(0, 0, 0, 0));
        ColorBar colorBar = new ColorBar(BegoitMoocApplication.Companion.getContextInstance(), Color.parseColor("#F5A623"), 6);
        colorBar.setWidth(4 * 42);
        myScrollIndicatorView.setScrollBar(colorBar);

        suspensionMretabindicator.setOnTransitionListener(new OnTransitionTextListener(0, 0, 0, 0));
        suspensionMretabindicator.setScrollBar(colorBar);

        mIndicatorViewPager = new IndicatorViewPager(myScrollIndicatorView, myViewPager);

        myAdapter = new MyAdapter(getSupportFragmentManager());
        mIndicatorViewPager.setAdapter(myAdapter);

        mIndicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                previousPosition = currentItem;
                refreshTabItem(currentItem);
            }});
        mIndicatorViewPager.setCurrentItem(0,true);

        previousPosition = 0;
    }


    //刷新tab的选中状态
    private void refreshTabItem(int currentItem)
    {
        for (int i = 0; i < columnArrayList.size(); i++)
        {
            TextView view = (TextView)mIndicatorViewPager.getIndicatorView().getItemView(i).findViewById(R.id.iv_tab_item);
            if (currentItem != i)
            {
                view.setTextColor(Color.parseColor("#333333"));
            }
            else
            {
                view.setTextColor(Color.parseColor("#F5A623"));
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Rect rootRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rootRect);
        int[] locations = new int[2];
        contrastLine.getLocationOnScreen(locations);
        suspensionLocationY = locations[1];
    }

    @Override
    public void clickCallBack(View view) {

    }

    @Override
    public void showLoading() {
        LoadingProgressDialog.showLoading(this,"加载中");
    }

    @Override
    public void cancelLoading() {
        LoadingProgressDialog.stopLoading();
    }

    @Override
    public void showCourseDetail(CourseDetailEntity entity,boolean isLocal) {
        emptyView.setVisibility(View.GONE);
        currentEntity = entity;
        setButtonStatus();
        title.setText(entity.getChannelName());
        tvCourseName.setText(entity.getChannelName());
        tvCourseSchool.setText("授课学校：" + entity.getSchoolName());
        tvCourseLevel.setText("课程难度：" + (TextUtils.isEmpty(entity.getHardLevel()) ? "":entity.getHardLevel()));
        tvCourseDuration.setText("课程时长：" + entity.getExpectTime());

        initVideoView(entity.getPreVideoFileid(),entity.getPreImgFileid());

        courseSummaryFragment.setTvSummary(entity.getIntroduction());
        if (isLocal) {
            courseLectorsFragment.refreshData(formatTeachers(entity.getTeacherConfig(),isLocal));
        }else {
            courseLectorsFragment.refreshData(formatTeachers(entity.getTeacherConfigs(),isLocal));
        }
        knowlageFragment.setTvSummary(entity.getKnowlage());
        programFragment.setTvSummary(entity.getProgram());

    }

    /**
     * 设置下载按钮状态
     */
    private void setButtonStatus(){
        mCourseDownLoadedEntity = DaoManager.getInstance().getDaoSession().getCourseDownLoadedEntityDao()
                .queryBuilder().where(CourseDownLoadedEntityDao.Properties.CourseId.eq(targetCourseId)).build().unique();

        if (mCourseDownLoadedEntity == null){
            tvCourseStatus.setBackgroundResource(R.drawable.bg_yellow_round_no_line);
            tvCourseStatus.setText("下载课程");
        }else if (mCourseDownLoadedEntity.courseDownloadedFilesCound == mCourseDownLoadedEntity.courseFilesCount){
            if (mPresenter.isCoureRegist(currentEntity.id)){
                tvCourseStatus.setBackgroundResource(R.drawable.bg_green_round_no_line);
                tvCourseStatus.setText("已注册，开始学习");
            }else{
                tvCourseStatus.setBackgroundResource(R.drawable.bg_green_round_no_line);
                tvCourseStatus.setText("已下载，注册学习");
            }
        }else if (mCourseDownLoadedEntity.courseDownloadedFilesCound < mCourseDownLoadedEntity.courseFilesCount){
            if (mCourseDownLoadedEntity.isDownloadError == 1){
                tvCourseStatus.setBackgroundResource(R.drawable.bg_red_round_no_line);
                tvCourseStatus.setText("下载失败，重新下载");
            }else {
                tvCourseStatus.setBackgroundResource(R.drawable.bg_blue_round_no_line);
                tvCourseStatus.setText("下载中，查看下载进度");
            }
        }else if (mCourseDownLoadedEntity.courseDownloadedFilesCound > mCourseDownLoadedEntity.courseFilesCount){
            tvCourseStatus.setBackgroundResource(R.drawable.bg_red_round_no_line);
            tvCourseStatus.setText("下载失败，重新下载");
        }
    }

    /**
     * 从教师配置列表获取教师列表
     */
    private List<TeacherEntity> teacherEntities;
    private List<TeacherEntity> formatTeachers(List<TeacherConfigEntity> teacherConfigEntities,boolean isLocal){
        if (teacherEntities == null){
            teacherEntities = new ArrayList<>();
        }else {
            teacherEntities.clear();
        }
        if (teacherConfigEntities == null){
            return teacherEntities;
        }
        for (TeacherConfigEntity item:teacherConfigEntities){
            if (isLocal){
                teacherEntities.add(item.getTeachers());
            }else {
                teacherEntities.add(item.getTeacher());
            }
        }
        return teacherEntities;
    }

    @Override
    public void startDownLoadFiles(CourseDetailFilesEntity courseDetailFilesEntity) {
        if (courseDetailFilesEntity == null){
            ToastUtil.showShortToast("课程数据异常");
            return;
        }
        List<FileInformationEntity> fileInformation = courseDetailFilesEntity.fileInformation;
        if (fileInformation != null && fileInformation.size() > 0) {
            tvCourseStatus.setBackgroundResource(R.drawable.bg_blue_round_no_line);
            tvCourseStatus.setText("下载中，查看下载进度");

            if (mCourseDownLoadedEntity == null){
                mCourseDownLoadedEntity = new CourseDownLoadedEntity();
            }
            mCourseDownLoadedEntity.courseId = currentEntity.id;
            mCourseDownLoadedEntity.isDownloadError = 0;
            mCourseDownLoadedEntity.total = 0;
            mCourseDownLoadedEntity.progress = 0;
            mCourseDownLoadedEntity.courseName = currentEntity.channelName;
            mCourseDownLoadedEntity.courseWithSchool = currentEntity.schoolName;
            mCourseDownLoadedEntity.preImgFileid = currentEntity.preImgFileid;
            mCourseDownLoadedEntity.courseTotal = Long.parseLong(TextUtils.isEmpty(courseDetailFilesEntity.channelSize) ? "0" : courseDetailFilesEntity.channelSize);
            mCourseDownLoadedEntity.courseFilesCount = fileInformation.size();
            mCourseDownLoadedEntity.courseDownloadedFilesCound = 0;

            //更新数据库内已下载课程，避免重复下载出现的未知问题
            DaoManager.getInstance().getDaoSession().getCourseDownLoadedEntityDao().insertOrReplaceInTx(mCourseDownLoadedEntity);

            Intent intent = new Intent(this, DownloadCourseService.class);
            startService(intent);
            resetDownloaCount();
            for (FileInformationEntity item : fileInformation) {
                DownloadLimitManager.getInstance().download(item);
            }
        }else {
            ToastUtil.showShortToast("没有课程文件");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CourseDownLoadedEntity eventEntity){
        if (eventEntity.courseId.equals(currentEntity.id)){
            if (eventEntity.isDownloadError == 1){
                tvCourseStatus.setBackgroundResource(R.drawable.bg_red_round_no_line);
                tvCourseStatus.setText("下载失败，重新下载");
            }else {
                resetDownloaCount();
                if (tvCourseStatus != null) {
                    if (eventEntity.courseDownloadedFilesCound == eventEntity.courseFilesCount) {
                        mPresenter.reviewRegistedCourse(mCourseDownLoadedEntity.courseId);
                        if (mPresenter.isCoureRegist(mCourseDownLoadedEntity.courseId)) {
                            tvCourseStatus.setBackgroundResource(R.drawable.bg_green_round_no_line);
                            tvCourseStatus.setText("已注册，开始学习");
                        } else {
                            tvCourseStatus.setBackgroundResource(R.drawable.bg_green_round_no_line);
                            tvCourseStatus.setText("已下载，注册学习");
                        }
                    } else if (eventEntity.courseDownloadedFilesCound > eventEntity.courseFilesCount) {
                        tvCourseStatus.setBackgroundResource(R.drawable.bg_red_round_no_line);
                        tvCourseStatus.setText("下载失败，重新下载");
                    }
                }
            }
        }
    }

    /**
     * 异常页面
     * @param icon 异常图标
     * @param msg  异常信息
     */
    @Override
    public void showErrorView(int icon,String msg) {
        ToastUtil.showShortToast(msg);
        if (currentEntity != null){
            return;
        }
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setIcon(icon);
        emptyView.setContent(msg);
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter
    {
        public MyAdapter(FragmentManager fragmentManager)
        {
            super(fragmentManager);
        }

        @Override
        public int getCount()
        {

            return mFragmentList.size();
        }

        @Override
        public View getViewForTab(final int position, View convertView, ViewGroup container)
        {
            if (convertView == null)
            {
                convertView = LayoutInflater.from(CourseDetailActivity.this).inflate(R.layout.common__tab_item_text, null);
            }
            TextView textView = (TextView) convertView;
            textView.setTextSize(16);
            if (previousPosition == position)
            {
                textView.setTextColor(Color.parseColor("#F5A623"));
            }else{
                textView.setTextColor(Color.parseColor("#333333"));
            }
            textView.setText(columnArrayList.get(position));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(columnArrayList.get(position).length() * 62, LinearLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            textView.setLayoutParams(params);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position)
        {

            return mFragmentList.get(position);
        }

    }

}

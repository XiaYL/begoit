package com.begoit.mooc.offline.ui.activity.courseManageForDownload;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.begoit.mooc.db.CourseDetailFilesEntityDao;
import com.begoit.mooc.db.CourseDownLoadedEntityDao;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.base.BaseActivity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.FileInformationEntity;
import com.begoit.mooc.offline.entity.course.user_download.CourseDownLoadedEntity;
import com.begoit.mooc.offline.ui.activity.coursedetail.CourseDetailActivity;
import com.begoit.mooc.offline.utils.FileHelper;
import com.begoit.mooc.offline.utils.ToastUtil;
import com.begoit.mooc.offline.utils.db.DaoManager;
import com.begoit.mooc.offline.utils.download.DownloadInfo;
import com.begoit.mooc.offline.utils.download.downloadwithlimit.DownloadLimitManager;
import com.begoit.mooc.offline.widget.BaseEmptyView;
import com.begoit.mooc.offline.widget.basedialog.DialogUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description:课程管理
 * @Author:gxj
 * @Time 2019/3/6
 */

public class CourseManageForDownloadActivity extends BaseActivity<CourseManageForDownloadPresenter,CourseManageForDownloadModelimpl>
        implements CourseManageForDownloadContract.View,ManageCoursesForDownloadAdapter.OnCourseItemClickListener {

    private ManageCoursesForDownloadAdapter coursesAdapter;
    private GridLayoutManager gridLayoutManager;
    @OnClick(R.id.iv_back)
    public void onBackClick(){
        finish();
    }
    //课程列表
    @BindView(R.id.rlv_courses)
    RecyclerView recyclerView;
    //存储空间详情
    @BindView(R.id.tv_storage)
    TextView storage;
   //异常页面
    @BindView(R.id.empty)
    BaseEmptyView emptyView;
    //管理模式切换按钮
    @BindView(R.id.tv_manage)
    TextView tvManage;
    //删除全选view
    @BindView(R.id.ll_manage_courses)
    LinearLayout manageLayout;
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll;
    //切换管理模式
    @OnClick({R.id.tv_manage,R.id.tv_delete,R.id.tv_select_all})
    public void manage(View view){
        switch (view.getId())
        {
            case R.id.tv_manage:
                if (coursesAdapter != null && coursesAdapter.getData().size() > 0) {
                    if (tvManage.getText().toString().equals("管理")) {
                        tvManage.setText("取消");
                        manageLayout.setVisibility(View.VISIBLE);
                        coursesAdapter.checkManageMode(ManageCoursesForDownloadAdapter.MANAGEMODEDELETE);
                    } else {
                        tvManage.setText("管理");
                        manageLayout.setVisibility(View.GONE);
                        coursesAdapter.checkManageMode(ManageCoursesForDownloadAdapter.MANAGEMODELEARNING);
                    }
                }
                break;
            case R.id.tv_delete:
                if (coursesAdapter.getPickedCourse() != null && coursesAdapter.getPickedCourse().size() > 0) {
                    DialogUtils.getInstance().showGenericDialogForTwoButtonCommon(this,"删除", "确定删除选中的课程吗？"
                            , "取消", "确定", new DialogUtils.ButtonClickCallBack() {
                                @Override
                                public void clickCallBack(View view) {
                                    if (view.getId() == R.id.tv_sure){
                                        mPresenter.deleteCourse(coursesAdapter.getPickedCourse());
                                    }
                                }
                            });
                }else {
                    ToastUtil.showShortToast("请选择要删除的课程");
                }
                break;
            case R.id.tv_select_all:
                if (tvSelectAll.getText().toString().equals("全选")){
                    if (coursesAdapter.pickAll()){
                        tvSelectAll.setText("取消");
                    }
                }else {
                    if (coursesAdapter.cancelAll()){
                        tvSelectAll.setText("全选");
                    }
                }
                break;
        }

    }

    private Timer mTimer;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(runnable);
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (recyclerView != null && coursesAdapter != null) {
                for (int i = 0; i < coursesAdapter.getItemCount(); i++) {
                    if (coursesAdapter.getData().get(i).courseDownloadedFilesCound < coursesAdapter.getData().get(i).courseFilesCount) {
                        //得到要更新的item的view
                        View view = recyclerView.getChildAt(i);
                        if (view != null && null != recyclerView.getChildViewHolder(view)) {
                            BaseViewHolder helper = (BaseViewHolder) recyclerView.getChildViewHolder(view);
                            if (coursesAdapter.getData().get(i).isDownloadError == 0) {
                                TextView textView = helper.getView(R.id.tv_learned_count);
                                if (!(textView.getText().toString().equals("等待中") && coursesAdapter.getData().get(i).total -
                                        coursesAdapter.getData().get(i).progress == 0)) {
                                    textView.setText("下载中  " + FileHelper.formatFileSize(coursesAdapter.getData().get(i).total -
                                            coursesAdapter.getData().get(i).progress, false) + "/s");
                                    coursesAdapter.getData().get(i).progress = coursesAdapter.getData().get(i).total;
                                }
                            }
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mTimer == null){
            mTimer = new Timer();
            mTimer.schedule(timerTask,0,1000);
        }
    }

    @Override
    protected int getLyoutId() {
        return R.layout.activity_course_manage;
    }

    @Override
    protected void initPresenter() {
       mPresenter.attachView(this,mModel);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mPresenter.getCourses();
        storage.setText("已下载" + FileHelper.getDownloadedCoursesSize() + "，可用空间" + FileHelper.getAvailableInternalMemorySize());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showCourses(List<CourseDownLoadedEntity> dataList) {
        if (dataList != null && dataList.size() > 0) {
            if (coursesAdapter == null) {
                coursesAdapter = new ManageCoursesForDownloadAdapter(dataList, this);
                coursesAdapter.setOnCourseItemClickListener(this);
                coursesAdapter.setSelectAllButton(tvSelectAll);
                gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(coursesAdapter);
            } else {
                coursesAdapter.setNewData(dataList);
            }
        }else {
            showEmptyView();
        }
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setContent("没有下载记录");
        emptyView.setIcon(R.mipmap.ic_no_downloaded);
    }

    /**
     * 删除课程
     * @param isDeleted
     */
    @Override
    public void deleteStatus(boolean isDeleted) {
        if (isDeleted){
            coursesAdapter.deleteCourses();
            tvManage.setText("管理");
            manageLayout.setVisibility(View.GONE);
            storage.setText("已下载" + FileHelper.getDownloadedCoursesSize() + "，可用空间" + FileHelper.getAvailableInternalMemorySize());
            coursesAdapter.getPickedCourse().clear();
        }else {
            ToastUtil.showShortToast("删除课程失败，请重新操作");
        }
    }

    @Override
    public void onCourseClick(CourseDownLoadedEntity entity) {
        if (entity.isDownloadError == 1){
            restartDownloadCourse(entity);
        }else {
            Intent intent = new Intent(mContext, CourseDetailActivity.class);
            intent.putExtra("isFromManage", true);
            intent.putExtra("courseId", entity.courseId);
            startActivity(intent);
        }
    }

    private CourseDetailFilesEntity redownloadEntity;
    private CourseDownLoadedEntity courseDownLoadedEntity;
    private void restartDownloadCourse(CourseDownLoadedEntity entity) {
        redownloadEntity = DaoManager.getInstance().getDaoSession().getCourseDetailFilesEntityDao()
                .queryBuilder().where(CourseDetailFilesEntityDao.Properties.Id.eq(entity.courseId)).build().unique();
        if (redownloadEntity != null && redownloadEntity.getFileInformation() != null && redownloadEntity.getFileInformation().size() > 0) {
            courseDownLoadedEntity = DaoManager.getInstance().getDaoSession().getCourseDownLoadedEntityDao()
                    .queryBuilder().where(CourseDownLoadedEntityDao.Properties.CourseId.eq(redownloadEntity.id)).build().unique();
            DownloadLimitManager.getInstance().removeCourse(courseDownLoadedEntity.courseId);
            if (courseDownLoadedEntity != null) {
                courseDownLoadedEntity.courseId = redownloadEntity.id;
                courseDownLoadedEntity.isDownloadError = 0;
                courseDownLoadedEntity.courseName = redownloadEntity.channelName;
                courseDownLoadedEntity.courseDownloadedFilesCound = 0;
                courseDownLoadedEntity.total = 0;
                courseDownLoadedEntity.progress = 0;
            }
            notifyItem(courseDownLoadedEntity);
            //更新数据库内已下载课程，避免重复下载出现的未知问题
            DaoManager.getInstance().getDaoSession().getCourseDownLoadedEntityDao().insertOrReplaceInTx(courseDownLoadedEntity);
            for (FileInformationEntity it:redownloadEntity.getFileInformation()) {
                DownloadLimitManager.getInstance().download(it);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null){
            timerTask.cancel();
            mTimer.cancel();
        }
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DownloadInfo info) {
        if (info.getDownloadStatus() == DownloadInfo.DOWNLOAD) {
            if (coursesAdapter != null){
                coursesAdapter.setDataChangeForId(info.getChannelId(),info.getLen());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CourseDownLoadedEntity eventEntity){
        notifyItem(eventEntity);
    }

    public void notifyItem(CourseDownLoadedEntity item){
        for (int i = 0;i < coursesAdapter.getItemCount();i++){
            if (item.courseId.equals(coursesAdapter.getData().get(i).courseId)){
                coursesAdapter.getData().get(i).courseDownloadedFilesCound = item.courseDownloadedFilesCound;
                coursesAdapter.getData().get(i).total = item.total;
                if (item.isAddedFile) {
                    storage.setText("已下载" + FileHelper.getDownloadedCoursesSize() + "，可用空间" + FileHelper.getAvailableInternalMemorySize());
                    doAnim(i, coursesAdapter.getData().get(i));
                }
                return;
            }
        }
    }

    /**
     * 根据下标找到指定view item更新
     * @param position
     * @param item
     */
    private void doAnim(int position,CourseDownLoadedEntity item) {
            //得到要更新的item的view
            View view = recyclerView.getChildAt(position);
            if (null != recyclerView.getChildViewHolder(view)) {
                BaseViewHolder helper = (BaseViewHolder) recyclerView.getChildViewHolder(view);
                if (item.isDownloadError == 1){
                    helper.setText(R.id.tv_learned_count,"下载失败，点击重试");
                    helper.setTextColor(R.id.tv_learned_count, Color.RED);
                    helper.setVisible(R.id.progress, true);
                    ProgressBar progressBar = helper.getView(R.id.progress);
                    progressBar.setProgressDrawable(getDrawable(R.drawable.bg_progress_error));
                }else {
                    if (item.courseDownloadedFilesCound == item.courseFilesCount) {
                        helper.setText(R.id.tv_learned_count, "已完成");
                        helper.setVisible(R.id.progress, false);
                    } else {
                        helper.setVisible(R.id.progress, true);
                        helper.setText(R.id.tv_learned_count, "下载中  0K/s");
                    }
                    helper.setTextColor(R.id.tv_learned_count, Color.parseColor("#999999"));
                    ProgressBar progressBar = helper.getView(R.id.progress);
                    progressBar.setProgressDrawable(getDrawable(R.drawable.bg_progress_loading));
                    int progress = item.courseDownloadedFilesCound == 0? 0:(int)((double)item.courseDownloadedFilesCound / (double)item.courseFilesCount * 100);
                    helper.setProgress(R.id.progress,progress);
                }
            }
    }
}

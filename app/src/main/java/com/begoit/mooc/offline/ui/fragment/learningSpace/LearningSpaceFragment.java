package com.begoit.mooc.offline.ui.fragment.learningSpace;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.base.BaseFragment;
import com.begoit.mooc.offline.entity.course.learning_space.HistoryCourseEntity;
import com.begoit.mooc.offline.event.NetChangeEvent;
import com.begoit.mooc.offline.event.UploadingStudyLogsEvent;
import com.begoit.mooc.offline.ui.activity.courseManageForDownload.CourseManageForDownloadActivity;
import com.begoit.mooc.offline.ui.activity.coursedetail.CourseDetailActivity;
import com.begoit.mooc.offline.ui.adapter.HistoryCoursesAdapter;
import com.begoit.mooc.offline.utils.NetworkUtils;
import com.begoit.mooc.offline.utils.StudyLogUploadUtil;
import com.begoit.mooc.offline.utils.ToastUtil;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description:学习空间
 * @Author:gxj
 * @Time 2019/2/23
 */

public class LearningSpaceFragment extends BaseFragment<LearningSpacePresenter,LearningSpaceModelimpl>
        implements LearningSpaceContract.View
        ,HistoryCoursesAdapter.OnCourseItemClickListener{

    //课程列表适配器
    HistoryCoursesAdapter coursesAdapter;
    //课程列表
    @BindView(R.id.rlv_courses)
    RecyclerView rlvCourses;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    //下载的课程数
    @BindView(R.id.iv_download_number)
    TextView downLoadNumber;
    //上传学习记录按钮
    @BindView(R.id.layout_upload)
    AutoRelativeLayout layoutUpload;
    @BindView(R.id.iv_upload_icon)
    ImageView ivUploadIcon;
    @BindView(R.id.tv_upload)
    TextView tvUpload;

    //跳转至课程管理
    @OnClick({R.id.course_manage,R.id.layout_upload})
    public void onManageClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.course_manage:
                intent = new Intent(mContext, CourseManageForDownloadActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_upload:
                if (NetworkUtils.isConnected()) {
                    StudyLogUploadUtil.INSTANCE.upload(mContext);
                }else {
                    ToastUtil.showShortToast("当前网络不可用，请检查后重试");
                }
                break;
        }
    }

    //获取实体
    public static LearningSpaceFragment newInstance() {
        Bundle bundle = new Bundle();
        LearningSpaceFragment fragment = new LearningSpaceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLyoutId() {
        return R.layout.fragment_learning_space;
    }

    @Override
    protected void initPresenter() {
         mPresenter.attachView(this,mModel);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        rlvCourses.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onVisible() {
        if (mPresenter != null) {
            getRemoteData();
            setUploadStatus();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NetChangeEvent event){
        setUploadStatus();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UploadingStudyLogsEvent event){
        setUploadStatus();
    }

    private void setUploadStatus(){
        if (layoutUpload != null && ivUploadIcon != null && tvUpload != null) {
            if (NetworkUtils.isConnected()) {
                layoutUpload.setBackgroundResource(R.drawable.bg_white_round_main_yellow_line);
                ivUploadIcon.setImageResource(R.mipmap.ic_study_log_normal);
                tvUpload.setTextColor(Color.parseColor("#F5A624"));
                if (StudyLogUploadUtil.INSTANCE.isDoUpload()){
                    ivUploadIcon.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    ivUploadIcon.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            } else {
                layoutUpload.setBackgroundResource(R.drawable.bg_white_round_main_gray_line);
                ivUploadIcon.setImageResource(R.mipmap.ic_upload_normal);
                tvUpload.setTextColor(Color.parseColor("#8A8A8A"));
            }
        }
    }

    private void getRemoteData(){
        mPresenter.getLearningSpaceCourses(BegoitMoocApplication.Companion.getContextInstance().getCurrentAccound());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            getRemoteData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showLearningSpaceCourses(List<HistoryCourseEntity> entityList) {
        if (coursesAdapter == null){
            coursesAdapter = new HistoryCoursesAdapter(entityList,mContext);
            coursesAdapter.setOnCourseItemClickListener(this);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,4,GridLayoutManager.VERTICAL,false);
            rlvCourses.setLayoutManager(gridLayoutManager);
            rlvCourses.setAdapter(coursesAdapter);
        }else {
            coursesAdapter.setNewData(entityList);
        }
    }

    @Override
    public void loadRemoteError(int code) {

    }

    @Override
    public void onCourseClick(HistoryCourseEntity entity) {
        if (entity.isDelete == 1) {
            ToastUtil.showShortToast("课程已删除");
        }else {
            Intent intent = new Intent(mContext, CourseDetailActivity.class);
            intent.putExtra("courseId", entity.channelId);
            startActivity(intent);
        }
    }

}

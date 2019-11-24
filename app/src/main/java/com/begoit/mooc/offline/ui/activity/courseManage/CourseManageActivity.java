package com.begoit.mooc.offline.ui.activity.courseManage;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.base.BaseActivity;
import com.begoit.mooc.offline.entity.course.user_download.CourseDownLoadedEntity;
import com.begoit.mooc.offline.ui.activity.coursedetail.CourseDetailActivity;
import com.begoit.mooc.offline.ui.adapter.ManageCoursesAdapter;
import com.begoit.mooc.offline.utils.FileHelper;
import com.begoit.mooc.offline.utils.ToastUtil;
import com.begoit.mooc.offline.widget.BaseEmptyView;
import com.begoit.mooc.offline.widget.basedialog.DialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description:课程管理
 * @Author:gxj
 * @Time 2019/3/6
 */

public class CourseManageActivity extends BaseActivity<CourseManagePresenter,CourseManageModelimpl>
        implements CourseManageContract.View,ManageCoursesAdapter.OnCourseItemClickListener {

    private ManageCoursesAdapter coursesAdapter;

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
                        coursesAdapter.checkManageMode(ManageCoursesAdapter.MANAGEMODEDELETE);
                    } else {
                        tvManage.setText("管理");
                        manageLayout.setVisibility(View.GONE);
                        coursesAdapter.checkManageMode(ManageCoursesAdapter.MANAGEMODELEARNING);
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
                        tvSelectAll.setText("反选");
                    }
                }else {
                    if (coursesAdapter.cancelAll()){
                        tvSelectAll.setText("全选");
                    }
                }
                break;
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
                coursesAdapter = new ManageCoursesAdapter(dataList, this);
                coursesAdapter.setOnCourseItemClickListener(this);
                coursesAdapter.setSelectAllButton(tvSelectAll);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
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
            coursesAdapter.getData().removeAll(coursesAdapter.getPickedCourse());
            tvManage.setText("管理");
            manageLayout.setVisibility(View.GONE);
            //延迟一秒刷新页面，避免数据更新不及时的问题
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    coursesAdapter.checkManageMode(ManageCoursesAdapter.MANAGEMODELEARNING);
                }
            }, 1000);
            coursesAdapter.getPickedCourse().clear();
            storage.setText("已下载" + FileHelper.getDownloadedCoursesSize() + "，可用空间" + FileHelper.getAvailableInternalMemorySize());
        }else {
            ToastUtil.showShortToast("删除课程失败，请重新操作");
        }
    }

    @Override
    public void onCourseClick(CourseDownLoadedEntity entity) {
        Intent intent = new Intent(mContext, CourseDetailActivity.class);
        intent.putExtra("courseId",entity.courseId);
        startActivity(intent);
    }
}

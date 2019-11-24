package com.begoit.mooc.offline.ui.fragment.recommendCourses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.base.BaseFragment;
import com.begoit.mooc.offline.entity.course.user_download.CourseDownLoadedEntity;
import com.begoit.mooc.offline.entity.kindMenu.MenuItemEntity;
import com.begoit.mooc.offline.ui.activity.courseManageForDownload.CourseManageForDownloadActivity;
import com.begoit.mooc.offline.ui.activity.searchCourse.SearchCourseActivity;
import com.begoit.mooc.offline.ui.fragment.baseCourseKind.KindCourseFragment;
import com.begoit.mooc.offline.utils.CourseComputeUtil;
import com.begoit.mooc.offline.utils.NetworkUtils;
import com.begoit.mooc.offline.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description:推荐课程
 * @Author:gxj
 * @Time 2019/2/23
 */

public class RecommendCoursesFragment extends BaseFragment<RecommendCoursesPresenter,RecommendCoursesModelimpl>
        implements RecommendCoursesContract.View {
    //用户名显示
    @BindView(R.id.tv_user_name)
    TextView userName;
    //标题
    @BindView(R.id.tv_title)
    TextView title;
    //侧边栏开关
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    @OnClick(R.id.iv_expand)
    public void onClickExpand(){
        if (onDrawerSwitchClickListener!= null) {
            onDrawerSwitchClickListener.onSwitchClick();
        }
    }
    //至课程管理页面按钮
    @OnClick(R.id.fl_download_container)
    public void toManage(){
        Intent intent = new Intent(mContext, CourseManageForDownloadActivity.class);
        startActivity(intent);
    }
    @BindView(R.id.download_count)
    TextView tvDownloaCount;
    private int downloaCount;
    //侧边栏开关点击监听
    private OnDrawerSwitchClickListener onDrawerSwitchClickListener;

    //搜索
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @OnClick(R.id.iv_search)
    public void deSearch(){
        Intent intent = new Intent(mContext, SearchCourseActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ivExpand.setVisibility(NetworkUtils.isAvailable() ? View.VISIBLE:View.INVISIBLE);
        ivSearch.setVisibility(NetworkUtils.isAvailable() ? View.VISIBLE:View.GONE);
        resetDownloaCount();
    }

    /**
     * 即时重置下载状态按钮
     */
    private void resetDownloaCount(){
        if (isAdded() && tvDownloaCount != null) {
            downloaCount = CourseComputeUtil.Companion.computeDoloadingCourseCount();
            if (downloaCount > 0) {
                tvDownloaCount.setVisibility(View.VISIBLE);
                tvDownloaCount.setText(downloaCount + "");
            }else {
                tvDownloaCount.setVisibility(View.GONE);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CourseDownLoadedEntity eventEntity){
        resetDownloaCount();
    }
    //当前分类
    private MenuItemEntity currentItem;

    //获取实体
    public static RecommendCoursesFragment newInstance() {
        Bundle bundle = new Bundle();
        RecommendCoursesFragment fragment = new RecommendCoursesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    //设置首页侧边栏开关监听
    public void setOnDrawerSwitchClickListener(OnDrawerSwitchClickListener onDrawerSwitchClickListener){
        this.onDrawerSwitchClickListener = onDrawerSwitchClickListener;
    }

    @Override
    protected int getLyoutId() {
        return R.layout.fragment_reconmmend_courses;
    }

    @Override
    protected void initPresenter() {
         mPresenter.attachView(this,mModel);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        if (userName != null && BegoitMoocApplication.Companion.getContextInstance().getCurrentUser() != null) {
            userName.setText(BegoitMoocApplication.Companion.getContextInstance().getCurrentUser().getUserName());
            initCourseData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //初始化课程数据，首次为推荐课程
    private void initCourseData(){
        MenuItemEntity leftNavItemEntity102 = new MenuItemEntity();
        leftNavItemEntity102.level = 3;
        leftNavItemEntity102.id = "102";
        leftNavItemEntity102.typeName = "推荐课程";

        refreshTitleAndContent(leftNavItemEntity102);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    public void refreshTitleAndContent(MenuItemEntity currentItem){
        if (currentItem == null){
            ToastUtil.showShortToast("请重新选择");
            return;
        }
        if (this.currentItem == null || !this.currentItem.getId().equals(currentItem.getId())) {
            this.currentItem = currentItem;
            initContent(currentItem);
        }
    }

    private void initContent(MenuItemEntity currentItem){
        title.setText(currentItem.getTypeName());
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, KindCourseFragment.Companion.newInstance(currentItem));
        transaction.commitNow();
    }

    public interface OnDrawerSwitchClickListener{
        void onSwitchClick();
    }
}

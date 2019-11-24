package com.begoit.mooc.offline.ui.activity.courseKind;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.base.BaseActivity;
import com.begoit.mooc.offline.entity.kindMenu.MenuItemEntity;
import com.begoit.mooc.offline.ui.adapter.LeftNavMenuOfCourseKindAdapter;
import com.begoit.mooc.offline.ui.fragment.baseCourseKind.KindCourseFragment;
import com.begoit.mooc.offline.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description:分类课程容器
 * @Author:gxj
 * @Time 2019/3/4
 */

public class CourseKindActivity extends BaseActivity<CourseKindPresenter,CourseKindModelimpl>
        implements CourseKindContract.View,LeftNavMenuOfCourseKindAdapter.OnMenuItemClickListener {
    //侧边菜单管理
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    //侧边菜单
    @BindView(R.id.nav_view)
    NavigationView navView;
    //类别标题显示
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //侧边菜单栏目
    private RecyclerView rlMenu;
    //侧边栏目适配器
    private LeftNavMenuOfCourseKindAdapter leftNavMenuOfCourseKindAdapter;
    //侧边栏容器
    private View navViewHeader;
    //数据绑定Intent
    private Intent fromIntent;
    private MenuItemEntity fromItem;

    //fragment管理类，通过替换fragment展示不同类别课程
    private FragmentManager mFragmentManager;
    //返回监听
    private FragmentManager.OnBackStackChangedListener mBackStackChangedListener;
    //当前分类
    private MenuItemEntity currentItem;

    //侧边栏开关
    @OnClick(R.id.iv_expand)
    public void click(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @OnClick(R.id.iv_back)
    public void clickBack(){
        finish();
    }
    @Override
    protected int getLyoutId() {
        return R.layout.activity_course_kind;
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this,mModel);
    }

    @Override
    protected void initView() {
        mFragmentManager = getSupportFragmentManager();
        initFragmentListener();
        fromIntent = getIntent();
        fromItem = fromIntent.getParcelableExtra("item");
        navViewHeader = navView.getHeaderView(0);
        rlMenu = navViewHeader.findViewById(R.id.rl_menu);
        mPresenter.getLeftNavItems();
        rlMenu.setFocusableInTouchMode(false);
        rlMenu.requestFocus();
        refreshTitleAndContent(fromItem);
    }

    private void initFragmentListener(){
        mBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // 嵌套的fragment数量
                int numFrags = mFragmentManager.getBackStackEntryCount();
                if (numFrags == 0){
                    return;
                }
                tvTitle.setText(mFragmentManager.getBackStackEntryAt(numFrags - 1).getBreadCrumbTitle());
            }
        };
        mFragmentManager.addOnBackStackChangedListener(mBackStackChangedListener);
    }

    private void refreshTitleAndContent(MenuItemEntity currentItem){
        if (currentItem == null){
            ToastUtil.showShortToast("请重新选择");
            return;
        }
        this.currentItem = currentItem;
        initContent(currentItem);
    }

    private void initContent(MenuItemEntity currentItem){
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fl_container, KindCourseFragment.Companion.newInstance(currentItem),currentItem.getId());
        transaction.setBreadCrumbTitle(currentItem.getTypeName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void initLeftNav(List<MenuItemEntity> leftNavItemEntityList) {
        if (leftNavItemEntityList == null){
            leftNavItemEntityList = new ArrayList<>();
        }
        MenuItemEntity leftNavItemEntity101 = new MenuItemEntity();
        leftNavItemEntity101.setId("101");
        leftNavItemEntity101.typeName = "全部课程";
        leftNavItemEntity101.level = 3;
//        leftNavItemEntity101.fIcon = R.mipmap.ic_course_type_all;
        leftNavItemEntityList.add(0,leftNavItemEntity101);

        MenuItemEntity leftNavItemEntity102 = new MenuItemEntity();
        leftNavItemEntity102.level = 3;
        leftNavItemEntity102.id = "102";
        leftNavItemEntity102.typeName = "推荐课程";
//        leftNavItemEntity102.fIcon = R.mipmap.ic_course_type_recommend;
        leftNavItemEntityList.add(1,leftNavItemEntity102);

        if (leftNavMenuOfCourseKindAdapter == null){
            leftNavMenuOfCourseKindAdapter = new LeftNavMenuOfCourseKindAdapter(leftNavItemEntityList);
            leftNavMenuOfCourseKindAdapter.setOnMenuItemClickListener(this);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
            rlMenu.setLayoutManager(gridLayoutManager);
            rlMenu.setAdapter(leftNavMenuOfCourseKindAdapter);
        }else {
            leftNavMenuOfCourseKindAdapter.setNewData(leftNavItemEntityList);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() == 1){
            finish();
        }else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMenuItemClick(MenuItemEntity item) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (currentItem != null && !currentItem.getId().equals(item.getId())) {
            currentItem = item;
            refreshTitleAndContent(item);
        }
    }
}

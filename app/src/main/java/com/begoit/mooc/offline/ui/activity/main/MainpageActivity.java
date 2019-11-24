package com.begoit.mooc.offline.ui.activity.main;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.base.BaseActivity;
import com.begoit.mooc.offline.entity.kindMenu.MenuItemEntity;
import com.begoit.mooc.offline.ui.adapter.LeftNavMenuOfCourseKindAdapter;
import com.begoit.mooc.offline.ui.fragment.learningSpace.LearningSpaceFragment;
import com.begoit.mooc.offline.ui.fragment.recommendCourses.RecommendCoursesFragment;
import com.begoit.mooc.offline.ui.activity.main.MainpageContract.MainpageView;
import com.begoit.mooc.offline.ui.fragment.userCenter.UserCenterFragment;
import com.begoit.mooc.offline.utils.ActivityTaskUtils;
import com.begoit.mooc.offline.utils.AppLogUtil;
import com.begoit.mooc.offline.utils.DeviceInfoUtil;
import com.begoit.mooc.offline.utils.NetworkUtils;
import com.begoit.mooc.offline.utils.ToastUtil;
import com.begoit.mooc.offline.widget.CenterAlignImageSpan;
import com.begoit.mooc.offline.widget.viewpager.IndicatorViewPager;
import com.begoit.mooc.offline.widget.viewpager.OnTransitionTextListener;
import com.begoit.mooc.offline.widget.viewpager.ScrollIndicatorView;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.begoit.mooc.offline.ui.adapter.LeftNavMenuOfCourseKindAdapter.*;

public class MainpageActivity extends BaseActivity<MainpagePresenter,MainpageModelimpl> implements OnClickListener
        ,MainpageView,OnMenuItemClickListener,RecommendCoursesFragment.OnDrawerSwitchClickListener{
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    //侧拉菜单容器
    @BindView(R.id.nav_view)
    NavigationView navView;
    //底部导航选项卡容器
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    //底部导航栏
    @BindView(R.id.sivMoretabIndicator)
    public ScrollIndicatorView myScrollIndicatorView;
    //底部导航栏与导航页面联动器
    private IndicatorViewPager mIndicatorViewPager;
    //底部导航包含页面
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private RecommendCoursesFragment recommendCoursesFragment;//推荐课程
    private LearningSpaceFragment learningSpaceFragment;//学习空间
    private UserCenterFragment userCenterFragment;//个人中心
    private int previousPosition = 0; //底部导航当前tab index
    //底部导航栏目名称
    private ArrayList<String> columnArrayList = new ArrayList();
    //底部导航栏与页面适配器
    private BottomTabAdapter myAdapter;

    //左边导航菜单列表
    private RecyclerView rlMenu;

    private LeftNavMenuOfCourseKindAdapter leftNavMenuOfCourseKindAdapter;

    private View navViewHeader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected int getLyoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this,mModel);
    }

    @Override
    protected void initView() {
        navViewHeader = navView.getHeaderView(0);
        rlMenu = navViewHeader.findViewById(R.id.rl_menu);

        mPresenter.loadLeftNavViewData();
        initBottomNavView();
    }

    private  long firstExitTime;
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long currentTime = System.currentTimeMillis();
            if (currentTime - firstExitTime < 2000) {
                ActivityTaskUtils.getAppManager().AppExit(true);
                AppLogUtil.INSTANCE.setLog(AppLogUtil.INSTANCE.getTPYE_SIGN_OUT());
            } else {
                firstExitTime = currentTime;
                ToastUtil.showShortToast("再按一次退出程序");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawer.closeDrawer(GravityCompat.START);
                }
            },600);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void initLeftNavView(List<MenuItemEntity> leftNavItemEntityList) {
        if (leftNavItemEntityList == null){
            leftNavItemEntityList = new ArrayList<>();
        }
        MenuItemEntity leftNavItemEntity101 = new MenuItemEntity();
        leftNavItemEntity101.setId("-1");
        leftNavItemEntity101.typeName = "全部课程";
        leftNavItemEntity101.level = 3;
        leftNavItemEntityList.add(0,leftNavItemEntity101);

        MenuItemEntity leftNavItemEntity102 = new MenuItemEntity();
        leftNavItemEntity102.level = 3;
        leftNavItemEntity102.id = "102";
        leftNavItemEntity102.typeName = "推荐课程";
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
    public void initBottomNavView() {
        columnArrayList.add("   在线课程");
        columnArrayList.add("   学习空间");
        columnArrayList.add("   个人中心");

        recommendCoursesFragment = RecommendCoursesFragment.newInstance();
        recommendCoursesFragment.setOnDrawerSwitchClickListener(this);

        learningSpaceFragment = LearningSpaceFragment.newInstance();
        userCenterFragment = UserCenterFragment.Companion.newInstance();

        mFragmentList.add(recommendCoursesFragment);
        mFragmentList.add(learningSpaceFragment);
        mFragmentList.add(userCenterFragment);
        // 未选中和选中字体颜色
        int selectColor = getResources().getColor(R.color.main_yellow);
        int unSelectColor = getResources().getColor(R.color.black);

        myScrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener(0, 0, selectColor, unSelectColor));

        mIndicatorViewPager = new IndicatorViewPager(myScrollIndicatorView, vpContent);
        myAdapter = new BottomTabAdapter(getSupportFragmentManager());
        mIndicatorViewPager.setAdapter(myAdapter);
        vpContent.setOffscreenPageLimit(2);
        mIndicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                previousPosition = currentItem;
                if (currentItem == 0){
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }else{
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
                setTabResources((TextView) mIndicatorViewPager.getIndicatorView().getItemView(0).findViewById(R.id.iv_tab_item)
                        ,0,R.mipmap.ic_tab_recommend_selectsd,R.mipmap.ic_tab_recommend_normal);
                setTabResources((TextView) mIndicatorViewPager.getIndicatorView().getItemView(1).findViewById(R.id.iv_tab_item)
                        ,1,R.mipmap.ic_tab_learning_space_selected,R.mipmap.ic_tab_learning_space_normal);
                setTabResources((TextView) mIndicatorViewPager.getIndicatorView().getItemView(2).findViewById(R.id.iv_tab_item)
                        ,2,R.mipmap.ic_tab_user_center_selected,R.mipmap.ic_tab_user_center_normal);
            }
        });

        if (NetworkUtils.isConnected()) {
            mIndicatorViewPager.setCurrentItem(0, true);
            previousPosition = 0;
        }else {
            mIndicatorViewPager.setCurrentItem(1, true);
            previousPosition = 1;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    @Override
    public void onMenuItemClick(MenuItemEntity item) {
        drawer.closeDrawer(GravityCompat.START);
        recommendCoursesFragment.refreshTitleAndContent(item);
    }

    @Override
    public void onSwitchClick() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    private class BottomTabAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        public BottomTabAdapter(FragmentManager fragmentManager)
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
                convertView = LayoutInflater.from(MainpageActivity.this).inflate(R.layout.common__tab_item_text, null);
            }
            TextView textView = (TextView) convertView;
            textView.setTextSize(14);

            switch (position){
                case 0:
                    setTabResources(textView,position,R.mipmap.ic_tab_recommend_selectsd,R.mipmap.ic_tab_recommend_normal);
                    break;
                case 1:
                    setTabResources(textView,position,R.mipmap.ic_tab_learning_space_selected,R.mipmap.ic_tab_learning_space_normal);
                    break;
                case 2:
                    setTabResources(textView,position,R.mipmap.ic_tab_user_center_selected,R.mipmap.ic_tab_user_center_normal);
                    break;
            }

            return convertView;
        }
        @Override
        public Fragment getFragmentForPage(int position)
        {
            return mFragmentList.get(position);
        }
    }

    /**
     * 底部导航的item不同状态设置文本和图标
     * @param tabItem 目标控件
     * @param position 目标编号
     * @param selectResource 选中状态
     * @param unSelectResource 未选中状态
     */
    private SpannableString spannableString;
    private CenterAlignImageSpan imageSpan;
    private Drawable drawable;
    private void setTabResources(TextView tabItem,int position,int selectResource,int unSelectResource){
        if (previousPosition == position)
        {
            tabItem.setTextColor(Color.parseColor("#000000"));
            tabItem .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            drawable = getResources().getDrawable(selectResource);
        }else{
            tabItem.setTextColor(Color.parseColor("#000000"));
            tabItem.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            drawable = getResources().getDrawable(unSelectResource);
        }
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        spannableString = new SpannableString(columnArrayList.get(position));
        imageSpan = new CenterAlignImageSpan(drawable);
        spannableString.setSpan(imageSpan,0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tabItem.setText(spannableString);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DeviceInfoUtil.getDevicesWidth() / 3, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        tabItem.setLayoutParams(params);
    }

}

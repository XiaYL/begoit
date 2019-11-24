package com.begoit.mooc.offline.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.begoit.mooc.offline.utils.TUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description: Fragment 基类
 * @Author:gxj
 * @Time 2019/2/18
 */

public abstract class BaseFragment<P extends BasePresenter,M extends IBaseModel> extends Fragment {
    protected View rootView;
    protected P mPresenter;
    protected M mModel;
    protected Unbinder unbinder;
    protected Activity mContext;

    private boolean isPrepared;                 //标志位，View已经初始化完成。
    private boolean isFirstLoad = true;         //是否第一次加载

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(getLyoutId(),container,false);
        }

        mContext = getActivity();

        unbinder = ButterKnife.bind(this,rootView);

        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    protected void lazyLoad() {//只加载一次
        if (!isPrepared || !isFirstLoad)
        {
            return;
        }
        isFirstLoad = false;
        initPresenter();
        initView();
        initListener();
    }

    protected void initListener() {

    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//setUserVisibleHint()方法先于onCreateView执行
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInvisible();
        }
    }
    /**
     * 当前fragment可见时候加载
     */
    protected void onVisible() {
        if (isFirstLoad) {
            lazyLoad();
        }
    }
    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 绑定layout
     * @return layout
     */
    protected abstract int getLyoutId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 绑定presenter
     */
    protected abstract void initPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}

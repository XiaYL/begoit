package com.begoit.mooc.offline.base;

/**
 * @Description:presenter基类
 * @Author:gxj
 * @Time 2019/2/18
 */

public interface IBasePresenter<V extends IBaseView,M extends IBaseModel> {

    /**
     * 绑定View
     * @param view
     */
    void attachView(V view,M m);

    /**
     * 解绑View
     */
    void detachView();

    boolean isAttach();
}



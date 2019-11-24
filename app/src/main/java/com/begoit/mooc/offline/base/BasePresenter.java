package com.begoit.mooc.offline.base;

import com.begoit.base.baserx.RxManager;
import com.begoit.mooc.offline.utils.LogUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Description:Presenter 基类
 * @Author:gxj
 * @Time 2019/2/18
 */

public abstract class BasePresenter<V extends IBaseView,M extends IBaseModel> implements IBasePresenter<V,M> {
    public M mModel;
    public V mView;
    public RxManager mRxManage;
    /**
     * 绑定view
     * @param view
     */
    @Override
    public void attachView(V view,M model) {
        this.mView = view;
        this.mModel = model;
        mRxManage = new RxManager();
        LogUtils.d(mModel.getClass().getName() + "  " + mView.getClass().getName());
    }

    /**
     * 解绑view
     */
    @Override
    public void detachView() {
        mRxManage.clear();
        this.mView = null;
        this.mModel = null;
    }

    //将字符串类型转换为okhttp请求体
    public RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    /**
     * view是否绑定
     * @return
     */
    @Override
    public boolean isAttach() {
        return this.mView != null;
    }
}

package com.begoit.base.network.callback;

/**
 * Project name:  begoit_base
 * Package name:  com.begoit.base.network.callback
 * Description:   网络响应回调
 * Copyright:     Copyright(C) 2017-2018
 * All rights Reserved, Designed By gaoxiaohui
 * Company        Elitech.
 *
 * @author gxj
 *          <p>
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          ${date}        gxh          1.0             1.0
 *          Why & What is modified: <修改原因描述>
 */
public class AbstractResponseCallback<T> implements OnResponseListener<T> {
    @Override
    public void onSucceed(int what, T result) {
        // do something
    }

    @Override
    public void onFailed(int what, Exception e) {
        // do something
    }

    @Override
    public void onStart(int what) {
        // do something
    }

    @Override
    public void onFinish(int what) {
        // do something
    }
}

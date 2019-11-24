package com.begoit.base.network;

import android.content.Context;

import com.begoit.base.network.callback.OnDefaultResponseListener;
import com.begoit.base.network.request.AbstractRequest;
import com.begoit.base.network.request.HttpMethod;
import com.begoit.base.network.request.OkHttpRequest;

import java.util.Map;

/**
 * Project name:  begoit_base
 * Package name:  com.begoit.base.network
 * Description:   ${todo}(用一句话描述该文件做什么)
 * Copyright:     Copyright(C) 2017-2018
 * Company        Elitech.
 *
 * @author Administrator
 * @version V1.0
 *          Createdate:    2019/2/20 9:30
 *          <p>
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          ${date}        gxh          1.0             1.0
 *          Why & What is modified: <修改原因描述>
 */
public class NetworkFly {

    private static NetworkFly mNetworkFlyInstance;

    private AbstractRequest mNetworkRequest;

    private NetworkFly() {
    }

    /**
     * get okHttp helper instance
     *
     * @return okHttpHelper
     */
    private static NetworkFly getInstance() {
        if (mNetworkFlyInstance == null) {
            synchronized (NetworkFly.class) {
                if (mNetworkFlyInstance == null) {
                    mNetworkFlyInstance = new NetworkFly();
                }
            }
        }
        return mNetworkFlyInstance;
    }

    private void setNetworkPolicy(AbstractRequest networkRequest) {
        this.mNetworkRequest = networkRequest;
    }

    public void callRequest(OnDefaultResponseListener listener) {
        if (null == this.mNetworkRequest) {
            this.mNetworkRequest = new OkHttpRequest();
        }
        this.mNetworkRequest.execute(listener);
    }

    // **************内部类 构建器 *************//
    public static class Builder {

        private Context mContext;

        private AbstractRequest mNetworkRequest;

        //    private NetworkConfiguration mConfigurationImpl;

        private int what = -1;

        private String url;

        private Object tag;

        private Map<String, String> params;

        private Map<String, String> headers;

        public Builder(Context context) {
            this.mContext = context;
        }

        //****************** Builder 的各种set方法 ******************//

        public Builder networkPolicy(AbstractRequest absRequest) {
            this.mNetworkRequest = absRequest;
            return this;
        }

        public Builder what(int what) {
            this.what = what;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * //get()
         * //post()
         * //put
         * //delete
         * //head
         * //patch
         * //options
         * //trace
         */
        private HttpMethod httpMethod;

        public Builder get() {
            this.httpMethod = HttpMethod.GET;
            return this;
        }

        public Builder post() {
            this.httpMethod = HttpMethod.POST;
            return this;
        }

        /**
         * create by builder
         *
         * @return okFly instance
         */
        public NetworkFly build() {
            final NetworkFly networkFly = NetworkFly.getInstance();

            // network request policy,default use OkHttp
            if (null == this.mNetworkRequest) {
                this.mNetworkRequest = new OkHttpRequest();
            }
            networkFly.setNetworkPolicy(this.mNetworkRequest);

            // setter methods
            this.mNetworkRequest.what(this.what);
            this.mNetworkRequest.url(this.url);
            this.mNetworkRequest.tag(this.tag);
            this.mNetworkRequest.params(this.params);
            this.mNetworkRequest.headers(this.headers);
            this.mNetworkRequest.httpMethod(this.httpMethod);

            return networkFly;
        }

    }

}

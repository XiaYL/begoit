package com.begoit.base.network.request;

import com.begoit.base.network.NetworkConfiguration;
import com.begoit.base.network.callback.OnDefaultResponseListener;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;

/**
 * Project name:  begoit_base
 * Package name:  com.begoit.base.network
 * Description:   请求头部、参数设置
 * Copyright:     Copyright(C) 2017-2018
 * All rights Reserved, Designed By gaoxiaohui
 * Company        Elitech.
 *          <p>
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          ${date}        gxh          1.0             1.0
 *          Why & What is modified: <修改原因描述>
 */
public abstract class AbstractRequest {

    protected NetworkConfiguration configsImpl;

    protected OnDefaultResponseListener listener;

    protected AbstractRequest(NetworkConfiguration impl) {
        this.configsImpl = impl;
    }

    protected AbstractRequest() {
    }

    protected HttpMethod httpMethod;

    public AbstractRequest httpMethod(HttpMethod method) {
        this.httpMethod = method;
        return this;
    }

    protected int what;

    protected String url;

    protected Object tag;

    protected Map<String, String> params;

    protected Map<String, String> headers;

    public AbstractRequest what(int what) {
        this.what = what;
        return this;
    }

    public AbstractRequest url(String url) {
        this.url = url;
        return this;
    }

    public AbstractRequest tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public AbstractRequest params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public AbstractRequest headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * append params
     *
     * @param builder builder
     * @param params  params
     */
    protected FormBody appendParams(FormBody.Builder builder, Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        return builder.build();
    }

    /**
     * append params
     *
     * @param url    url
     * @param params params
     * @return string result of url with params
     */
    protected String appendParams(String url, Map<String, String> params) {

        if (params == null) {
            return url;
        }

        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();

        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("?");

        for (int i = 0; i < params.size(); i++) {
            String value = null;
            try {
                value = URLEncoder.encode(values.next(), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            stringBuffer.append(keys.next()).append("=").append(value);
            if (i != params.size() - 1) {
                stringBuffer.append("&");
            }
        }
        return url + stringBuffer.toString();
    }

    /**
     * 主要请求方法
     *
     * @param listener listener
     */
    public abstract void execute(OnDefaultResponseListener listener);

    //protected abstract AbstractResponse<T> parseNetworkResponse();

}

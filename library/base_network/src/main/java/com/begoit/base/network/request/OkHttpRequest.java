package com.begoit.base.network.request;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.begoit.base.network.NetworkConfiguration;
import com.begoit.base.network.NetworkLogger;
import com.begoit.base.network.callback.OnDefaultResponseListener;
import com.begoit.base.network.exception.HttpException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Project name:  begoit_base
 * Package name:  com.begoit.base.network
 * Description:   执行请求
 * Copyright:     Copyright(C) 2017-2018
 * All rights Reserved, Designed By gaoxiaohui
 * Company        Elitech.
 *
 * @author Administrator
 * @version V1.0
 *          Createdate:    2019/2/19 9:26
 *          <p>
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          ${date}        gxj         1.0             1.0
 *          Why & What is modified: <修改原因描述>
 */
public class OkHttpRequest extends AbstractRequest {

    public OkHttpRequest() {
        super();
    }

    public OkHttpRequest(NetworkConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void execute(final OnDefaultResponseListener listener) {

        this.listener = listener;
        try{
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //        if (null != configsImpl){
            //
            //        }
            OkHttpClient okHttpClient = builder.build();

            Request.Builder requestBuilder = new Request.Builder();
            if (null == this.httpMethod) {
                this.httpMethod = HttpMethod.GET;
            }

            if (TextUtils.isEmpty(this.url)) {
                throw new IllegalArgumentException("url can not be null !");
            }

            if (this.httpMethod.hasBody()) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                FormBody formBody = appendParams(formBodyBuilder, this.params);
                requestBuilder.post(formBody);
            } else {
                requestBuilder.get();

                // append params to url
                if (this.params != null && this.params.size() > 0) {
                    this.url = appendParams(this.url, this.params);
                }
            }
            requestBuilder.url(this.url);

            // set tag while tag is not null
            if (this.tag != null) {
                requestBuilder.tag(this.tag);
            }

            if (this.headers != null) {
                for (String key : this.headers.keySet()) {
                    requestBuilder.addHeader(key, this.headers.get(key));
                }
            }

            Request request = requestBuilder.build();

            // new call
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    NetworkLogger.e(e, e.getLocalizedMessage());
                    listener.onFailed(what, e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    //ResponseBody responseBody = response.body();
                    if (response.isSuccessful()) {
                        listener.onSucceed(what, response);
                    } else {
                        listener.onFailed(what, new HttpException(response.message(), response.code()));
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e("OKHttp Error","okhttp is error");
        }
    }

    //    @Override
    //    protected AbstractResponse parseNetworkResponse() {
    //        return null;
    //    }

}

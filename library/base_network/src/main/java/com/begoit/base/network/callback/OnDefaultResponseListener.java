package com.begoit.base.network.callback;

import okhttp3.Response;

/**
 * Description: OnDefaultResponseListener 监听返回状态
 * <p>
 * User: gxj <br>
 */
public interface OnDefaultResponseListener {

    /**
     * Server correct response to callback when an HTTP request.
     *
     * @param what   the credit of the incoming request is used to distinguish between multiple requests.
     * @param result successful callback.
     */
    void onSucceed(int what, Response result);

    /**
     * When there was an error correction.
     *
     * @param what the credit of the incoming request is used to distinguish between multiple requests.
     * @param e    e.
     */
    void onFailed(int what, Exception e);

}

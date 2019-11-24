package com.begoit.base.network.callback;

/**
 * Description: OnDefaultResponseListener
 * <p>
 * User: gxj <br>
 * Date: 2019/2/19 下午2:05 <br>
 */
public interface OnResponseListener<T> {

    /**
     * Server correct response to callback when an HTTP request.
     *
     * @param what   the credit of the incoming request is used to distinguish between multiple requests.
     * @param result successful callback.
     */
    void onSucceed(int what, T result);

    /**
     * When there was an error correction.
     *
     * @param what the credit of the incoming request is used to distinguish between multiple requests.
     * @param e    e.
     */
    void onFailed(int what, Exception e);

    /**
     * When the request starts.
     *
     * @param what the credit of the incoming request is used to distinguish between multiple requests.
     */
    void onStart(int what);

    /**
     * When the request finish.
     *
     * @param what the credit of the incoming request is used to distinguish between multiple requests.
     */
    void onFinish(int what);

}

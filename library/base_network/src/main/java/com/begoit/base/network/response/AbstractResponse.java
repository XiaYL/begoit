package com.begoit.base.network.response;

import java.io.Closeable;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Project name:  begoit_base
 * Package name:  com.begoit.base.network.response
 * Description:   响应抽象
 * Copyright:     Copyright(C) 2017-2018
 * All rights Reserved, Designed By gaoxiaohui
 * Company        Elitech.
 *
 * @author Administrator
 * @version V1.0
 *          Createdate:    2019/2/19 11:40
 *          <p>
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          ${date}        gxh          1.0             1.0
 *          Why & What is modified: <修改原因描述>
 */
public class AbstractResponse<T> implements Serializable, Closeable {

    private T data;

    private int code;

    private String message;

    private Map<String, List<String>> headers;

    //    public abstract MediaType contentType();
    //    public abstract long contentLength();

    AbstractResponse(Builder builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.headers = builder.headers;
    }

    /**
     * Returns true if the code is in [200..300), which means the request was successfully received,
     * understood, and accepted.
     */
    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    /**
     * Returns the HTTP status code.
     */
    public int code() {
        return code;
    }

    /**
     * Returns the HTTP status message.
     */
    public String message() {
        return message;
    }

    public List<String> headers(String name) {
        return headers.get(name);
    }

    public Map<String, List<String>> headers() {
        return headers;
    }


    /**
     * Closes the response body. Equivalent to {@code body().close()}.
     */
    @Override
    public void close() {
    }

    @Override
    public String toString() {
        return "AbstractResponse{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", headers=" + headers +
                '}';
    }


    public static class Builder {
        int code = -1;
        String message;
        private Map headers;

        public Builder() {
        }

        Builder(AbstractResponse response) {
            this.code = response.code;
            this.message = response.message;
            this.headers = response.headers;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder headers(Map headers) {
            this.headers = headers;
            return this;
        }

        public AbstractResponse build() {
            //if (request == null) throw new IllegalStateException("request == null");
            if (code < 0) throw new IllegalStateException("code < 0: " + code);
            if (message == null) throw new IllegalStateException("message == null");
            return new AbstractResponse(this);
        }

    }

}

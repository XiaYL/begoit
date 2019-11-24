package com.begoit.base.network;

/**
 * Project name:  begoit_base
 * Package name:  com.begoit.base.network
 * Description:   ${todo}(用一句话描述该文件做什么)
 * Copyright:     Copyright(C) 2017-2018
 * All rights Reserved, Designed By gaoxiaohui
 * Company        Elitech.
 *
 * @author Administrator
 * @version V1.0
 *          Createdate:    2019/2/20 9:35
 *          <p>
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          ${date}        gxh          1.0             1.0
 *          Why & What is modified: <修改原因描述>
 */
public abstract class NetworkConfiguration {

    // read/write/connect timeout
    // cache  cachePolicy
    // priority
    // retry  retryPolicy
    //

    private static final int DEFAULT_CONNECT_TIMEOUT = 10;
    private static final int DEFAULT_READ_TIMEOUT = 10;
    private static final int DEFAULT_WRITE_TIMEOUT = 30;

    private static final int DEFAULT_CACHE_SIZE = 10 * 1024 * 1024;


    public abstract void initialize();

}

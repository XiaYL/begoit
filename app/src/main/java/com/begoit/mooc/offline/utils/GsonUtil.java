package com.begoit.mooc.offline.utils;

import com.google.gson.Gson;

/**
 * @author gxj
 * @date 2019/03/11
 * Desc：Gson工具
 */

public class GsonUtil {
    private static Gson gson;

    public static Gson getInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

}

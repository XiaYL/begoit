package com.begoit.mooc.offline.utils.download;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Description:IO 流关闭
 * @Author:gxj
 * @Time 2019/3/22
 */
public class DownloadIO {
    public static void closeAll(Closeable... closeables){
        if(closeables == null){
            return;
        }
        for (Closeable closeable : closeables) {
            if(closeable!=null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

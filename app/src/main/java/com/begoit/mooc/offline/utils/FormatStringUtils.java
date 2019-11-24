package com.begoit.mooc.offline.utils;

import com.begoit.mooc.offline.requests.ApiConstants;

/**
 * @Description:字符串处理工具类
 * @Author:gxj
 * @Time 2019/4/4
 */

public class FormatStringUtils {

    /**
     * 将包含不完整图片地址的html补全图片网络地址
     * @param s
     * @return
     */
    public static String fillAllHtmlForImgFromNet(String s){
        StringBuilder builder = new StringBuilder(s);

        while (true) {
            int index = builder.indexOf("<img src=\"/kinduploadfiles");
            if (index != -1) {
                builder.insert(index + 10, ApiConstants.FILE_HOST);
            }else {
                break;
            }
        }
        return builder.toString();
    }
    /**
     * 将包含不完整图片地址的html补全图片本地地址
     * @param html
     * * @param courseID 本地图片仓根据课程ID定位
     * @return
     */
    public static String fillAllHtmlForImgFromLocal(String html, String courseID) {
        StringBuilder builder = new StringBuilder(html);

        while (true) {
            int index = builder.indexOf("<img src=\"/kinduploadfiles");
            if (index != -1) {
                builder.replace(index + 10,builder.indexOf(".",index + 10) - 19,"file://" + FileHelper.getFilePath(courseID));
//                builder.replace(index + 10,builder.indexOf("\"",index + 10) ,"file:///storage/emulated/0/31.jpg");
            }else {
                break;
            }
        }
        return builder.toString();
    }

}

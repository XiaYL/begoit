package com.begoit.mooc.offline.utils;

import android.os.Environment;
import android.os.StatFs;

import com.begoit.mooc.BegoitMoocApplication;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @Description:存储辅助类
 * @Author:gxj
 * @Time 2019/3/21
 */

public class FileHelper {
    public static String ABSOLUTEPATH = BegoitMoocApplication.Companion.getContextInstance().getExternalFilesDir("").getAbsolutePath() + File.separator ;

    /**
     * 生成课程相关文件地址
     * @param channelId 课程ID 作唯一指向
     * @param fileName
     * @return
     */
   public static String getCourseFilePath(String channelId,String fileName){
        return ABSOLUTEPATH + channelId +  File.separator + fileName;
   }

    /**
     * webview加载本地图片的第一个参数
     * @param courseID
     * @return
     */
    public static String formatBaseUrl(String courseID){
        return "file://" + ABSOLUTEPATH + courseID + File.separator;
    }

    public static String getFilePath(String courseId){
        return ABSOLUTEPATH + courseId;
    }
    /**
      * 获取手机内部剩余存储空间
      * @return 转换后字符串，用于下显示
      */
     public static String getAvailableInternalMemorySize() {
        return formatFileSize(getMemoryBlockSize(),false);

     }
    /**
      * 获取手机内部剩余存储空间
      * @return 原始大小
      */
     public static long getMemoryBlockSize(){
         File path = Environment.getDataDirectory();
         StatFs stat = new StatFs(path.getPath());
         long blockSize = stat.getBlockSize();
         long availableBlocks = stat.getAvailableBlocks();
         return availableBlocks * blockSize;
     }

    /**
     * 判断内存空间是否充足
     * @return
     */
    public static boolean isFree(){
         if (getMemoryBlockSize() > 10 * 1024 * 1024 * 1024){
             return true;
         }else {
             return false;
         }
     }

    /**
     * 单位换算
     *  @param size 单位为B
     * @param isInteger 是否返回取整的单位
     * @return 转换后的单位
     */
    private static DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
    private static DecimalFormat fileDecimalFormat = new DecimalFormat("#0.##");

    public static String formatFileSize(long size, boolean isInteger) {
        DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
        String fileSizeString = "0M";
        if (size < 1024 && size > 0) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1024 * 1024) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }

    /**
     * 删除文件
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();
        File file = new File(ABSOLUTEPATH + fileName);
        if (file.exists()){
            checker.checkDelete(file.toString());
            if (file.isFile()) {
                try {
                    file.delete();
                    status = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else
                status = false;
        }else
            status = false;
        return status;
    }

    //删除文件夹及以下文件
    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 获取已下载课程大小
     * @return
     */
    public static String getDownloadedCoursesSize(){
        File file = new File(ABSOLUTEPATH);
        return formatFileSize(getFolderSize(file),false);
    }

    /**
     * 获取文件夹大小
     * @return long
     */
    public static long getFolderSize(File file){
        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++)
            {
                if (fileList[i].isDirectory())
                {
                    size = size + getFolderSize(fileList[i]);

                }else{
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return size;
    }
}

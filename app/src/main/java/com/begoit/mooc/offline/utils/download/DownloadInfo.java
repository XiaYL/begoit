package com.begoit.mooc.offline.utils.download;

/**
 * @Description:文件下载信息
 * @Author:gxj
 * @Time 2019/3/22
 */
public class DownloadInfo {

    /**
     * 下载状态
     */
    public static final String DOWNLOAD = "download";    // 下载中
    public static final String DOWNLOAD_PAUSE = "pause"; // 下载暂停
    public static final String DOWNLOAD_WAIT = "wait";  // 等待下载
    public static final String DOWNLOAD_CANCEL = "cancel"; // 下载取消
    public static final String DOWNLOAD_OVER = "over";    // 下载结束
    public static final String DOWNLOAD_ERROR = "error";  // 下载出错

    public static final long TOTAL_ERROR = -1;//获取进度失败

    private String url;//文件地址
    private String fileName;//文件名
    private String downloadStatus;//下载状态
    private long total;//文件总长
    private long progress;
    private long len;//单次存储的数据量
    private String channelId;//下载对象所属课程ID

    public DownloadInfo(String url) {
        this.url = url;
    }

    public DownloadInfo(String url, String downloadStatus) {
        this.url = url;
        this.downloadStatus = downloadStatus;
    }

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public String getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
}

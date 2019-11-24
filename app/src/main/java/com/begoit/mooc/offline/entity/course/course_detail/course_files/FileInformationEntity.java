package com.begoit.mooc.offline.entity.course.course_detail.course_files;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description:课程相关文件实体
 * @Author:gxj
 * @Time 2019/3/21
 */
@Entity
public class FileInformationEntity{
    public String fileName;//文件名
    public long fileSize;//文件大小
    public String fileUrl;//文件下载路径
    @Id
    public String fileId;//文件id (课程视频相关表中videoFileId字段)
    public int downloadStatus;//下载状态(默认:0,下载中:1,下载完成:2)
    @NotNull
    public String channelId;//课程id
    @Generated(hash = 369704858)
    public FileInformationEntity(String fileName, long fileSize, String fileUrl,
            String fileId, int downloadStatus, @NotNull String channelId) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
        this.fileId = fileId;
        this.downloadStatus = downloadStatus;
        this.channelId = channelId;
    }
    @Generated(hash = 597844432)
    public FileInformationEntity() {
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public long getFileSize() {
        return this.fileSize;
    }
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    public String getFileUrl() {
        return this.fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public String getFileId() {
        return this.fileId;
    }
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    public int getDownloadStatus() {
        return this.downloadStatus;
    }
    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
    public String getChannelId() {
        return this.channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}

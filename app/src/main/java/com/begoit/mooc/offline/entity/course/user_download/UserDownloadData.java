package com.begoit.mooc.offline.entity.course.user_download;

import com.begoit.mooc.offline.entity.BaseEntity;

import java.util.List;

/**
 * @Description:用户下载响应数据
 * @Author:gxj
 * @Time 2019/5/5
 */

public class UserDownloadData extends BaseEntity {
    public UserDownloadEntity data;

    public class UserDownloadEntity {
       public List<UserChannelEntity> userChannel;
       public List<VideoTestScoreEntity> videoTestScore;
    }
}

package com.begoit.mooc.offline.event

/**
 *@Description:上传学习记录事件
 *@Author:gxj
 *@Time 2019/6/3
 */
class UploadingStudyLogsEvent(var isUploading: Boolean, var isSuccess: Int) // isSuccess 0:开始 1：成功 2：失败
package com.begoit.mooc.offline.entity.course.user_download;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description:系统日志表:用于记录本机用户的一些操作 如登录、学习等
 * @Author:gxj
 * @Time 2019/5/17
 */
@Entity
public class AppLogEntity {
    @Id
    public String id;//主键
    public int isChange;//0:没变动  1：有变动  同步学习记录上传有变动记录
    public String userAccount;//用户账号
    public String userName;//用户姓名
    public String type;//行为类型
    public String content;//行为内容
    public String createTime;//写入时间
    @Generated(hash = 485262624)
    public AppLogEntity(String id, int isChange, String userAccount,
            String userName, String type, String content, String createTime) {
        this.id = id;
        this.isChange = isChange;
        this.userAccount = userAccount;
        this.userName = userName;
        this.type = type;
        this.content = content;
        this.createTime = createTime;
    }
    @Generated(hash = 1564696764)
    public AppLogEntity() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getIsChange() {
        return this.isChange;
    }
    public void setIsChange(int isChange) {
        this.isChange = isChange;
    }
    public String getUserAccount() {
        return this.userAccount;
    }
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

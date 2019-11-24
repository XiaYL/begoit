package com.begoit.mooc.offline.entity.user;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * @Description:用户信息
 * @Author:gxj
 * @Time 2019/3/5
 */
@Entity
public class UserEntity {
    public String userName;
    @Id
    public String userId;
    public String passWord;
    public String userAccount;
    public String token;
    public String offlineSn;
    @Generated(hash = 1223311850)
    public UserEntity(String userName, String userId, String passWord,
            String userAccount, String token, String offlineSn) {
        this.userName = userName;
        this.userId = userId;
        this.passWord = passWord;
        this.userAccount = userAccount;
        this.token = token;
        this.offlineSn = offlineSn;
    }
    @Generated(hash = 1433178141)
    public UserEntity() {
    }

    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPassWord() {
        return this.passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserAccount() {
        return this.userAccount;
    }
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getOfflineSn() {
        return this.offlineSn;
    }
    public void setOfflineSn(String offlineSn) {
        this.offlineSn = offlineSn;
    }
}

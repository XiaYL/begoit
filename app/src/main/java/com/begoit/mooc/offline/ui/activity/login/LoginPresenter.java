package com.begoit.mooc.offline.ui.activity.login;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.begoit.mooc.offline.entity.course.user_download.UserDownloadData;
import com.begoit.mooc.offline.requests.ApiConstants;
import com.begoit.mooc.offline.requests.HeaderConfig;
import com.begoit.mooc.offline.entity.user.UserEntity;
import com.begoit.mooc.offline.utils.DeviceInfoUtil;
import com.begoit.mooc.offline.utils.GsonUtil;
import com.begoit.mooc.offline.utils.NetworkUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @Description:登录模块业务处理
 * @Author:gxj
 * @Time 2019/2/18
 */

public class LoginPresenter extends LoginContract.Presenter {
    private Map<String,String> requestMap;//参数集合
    private UserEntity userEntity;//登录用户

    @Override
    public void login(final String userAccount, final String passWord) {
        mView.showLoading();
        if (NetworkUtils.isAvailable()) {
            requestMap = new HashMap();
            requestMap.put("userAccount", userAccount);
            requestMap.put("userPassword", passWord);
            requestMap.put("appSn", DeviceInfoUtil.getDeviceSN());
            requestMap.put("offlineSn","");// mModel.getOfflineSnWithUserAccount(userAccount));//mModel.getOfflineSnWithUserAccount(userAccount));
            mRxManage.add(mModel.login(requestMap).subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    JSONObject backJO = JSON.parseObject(s);
                    if (ApiConstants.SUCCESSCODE == backJO.getIntValue("status")) {
                        userEntity = GsonUtil.getInstance().fromJson(backJO.getString("data"), UserEntity.class);
                        userEntity.setPassWord(passWord);
                        HeaderConfig.setToken(userEntity.token);
                        mModel.addUserToLocal(userEntity);
                        userDownload(userEntity.token,userEntity.userAccount);
                    } else {
                        if (!TextUtils.isEmpty(backJO.getString("error"))) {
                            mView.loginError(backJO.getString("error"));
                        }
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    mView.loginError("网络异常，请稍后再试");
                }
            }));
        }else {
            userEntity = mModel.loginWithLocal(userAccount);
            if (userEntity == null){
                mView.loginError("未在此设备登录过");
            }else if(!userEntity.passWord.equals(passWord)){
                mView.loginError("密码错误，请重新输入");
            }else{
                mModel.addUserToLocal(userEntity);
                mView.loginSuccess(userEntity);
            }
        }
    }

    private UserDownloadData mUserDownloadData;
    @Override
    void userDownload(String token, String userAccount) {
         mRxManage.add(mModel.userDownload(token,userAccount).subscribe(new Consumer<String>() {
             @Override
             public void accept(String s) throws Exception {
                 mUserDownloadData = GsonUtil.getInstance().fromJson(s,UserDownloadData.class);
                 if (ApiConstants.SUCCESSCODE == mUserDownloadData.getStatus()){
                     if (mModel.insertUserData(mUserDownloadData.data)) {
                         mView.loginSuccess(userEntity);
                     }else {
                         mView.loginError("用户数据下载失败，请重新登录");
                     }
                 }else {
                     if (!TextUtils.isEmpty(mUserDownloadData.getError())) {
                         mView.loginError(mUserDownloadData.getError());
                     }
                 }
             }
         }, new Consumer<Throwable>() {
             @Override
             public void accept(Throwable throwable) throws Exception {
                 mView.loginError("网络异常，请稍后再试");
             }
         }));
    }

    @Override
    void getPopUsers() {
        mView.showPopUsers(mModel.getPopUsers());
    }
}

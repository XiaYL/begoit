package com.begoit.mooc.offline.ui.activity.login;

import com.begoit.mooc.offline.base.BasePresenter;
import com.begoit.mooc.offline.base.IBaseView;
import com.begoit.mooc.offline.base.IBaseModel;
import com.begoit.mooc.offline.entity.course.user_download.UserDownloadData;
import com.begoit.mooc.offline.entity.user.UserEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @Description:登录综合协议
 * @Author:gxj
 * @Time 2019/2/18
 */

public interface LoginContract {
    interface LoginView extends IBaseView{
        void showEditError(String msg);
        void loginSuccess(UserEntity userEntity);
        void loginError(String msg);
        void showPopUsers(List<UserEntity> dataList);
    }

    abstract class Presenter extends BasePresenter<LoginView,Model> {
         abstract void login(String userName, String passWord);
         abstract void userDownload(String token,String userAccount);
         abstract void getPopUsers();
    }

    interface Model extends IBaseModel {
        Observable<String> login(Map<String, String> request);
        Observable<String> userDownload(String token,String userAccount);
        boolean insertUserData(UserDownloadData.UserDownloadEntity data);
        UserEntity loginWithLocal(String userName);
        List<UserEntity> getPopUsers();
        void addUserToLocal(UserEntity userEntity);
        String getOfflineSnWithUserAccount(String userAccount);
    }
}

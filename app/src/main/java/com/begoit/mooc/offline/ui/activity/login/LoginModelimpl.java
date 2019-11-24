package com.begoit.mooc.offline.ui.activity.login;

import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.db.UserChannelEntityDao;
import com.begoit.mooc.db.UserEntityDao;
import com.begoit.mooc.db.VideoTestScoreEntityDao;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideoTestEntity;
import com.begoit.mooc.offline.entity.course.user_download.UserChannelEntity;
import com.begoit.mooc.offline.entity.course.user_download.UserDownloadData;
import com.begoit.mooc.offline.entity.course.user_download.VideoTestScoreEntity;
import com.begoit.mooc.offline.requests.Api;
import com.begoit.mooc.offline.requests.HostType;
import com.begoit.mooc.offline.entity.user.UserEntity;
import com.begoit.mooc.offline.utils.LogUtils;
import com.begoit.mooc.offline.utils.db.DaoManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Description:登录数据代理
 * @Author:gxj
 * @Time 2019/2/18
 */

public class LoginModelimpl implements LoginContract.Model {

    @Override
    public Observable<String> login(Map<String, String> requestMap) {
        return Api.getDefault(HostType.TYPE_APP)//
                .login(requestMap)//
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        return responseBody.string();
                    }
                }).subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> userDownload(String token, String userAccount) {
        Map<String,String> requestMap = new HashMap();
        requestMap.put("token",token);
        requestMap.put("userAccount",userAccount);
        return Api.getDefault(HostType.TYPE_APP)//
                .userDownload(requestMap)//
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        return responseBody.string();
                    }
                }).subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public boolean insertUserData(UserDownloadData.UserDownloadEntity data) {
        try {
            if (data != null) {
                if (data.userChannel != null && data.userChannel.size() > 0) {
                    for (int i = 0;i < data.userChannel.size();i++) {
                        if (DaoManager.getInstance().getDaoSession().getUserChannelEntityDao().queryBuilder()
                                .where(UserChannelEntityDao.Properties.ChannelId.eq(data.userChannel.get(i).channelId)
                                        , UserChannelEntityDao.Properties.UserAccount.eq(data.userChannel.get(i).userAccount)).build().unique() == null) {
                            DaoManager.getInstance().getDaoSession().getUserChannelEntityDao().insertOrReplaceInTx(data.userChannel.get(i));
                        }
                    }
                }

                if (data.videoTestScore != null && data.videoTestScore.size() > 0) {
                    for (int i = 0;i < data.videoTestScore.size();i++) {
                        if (DaoManager.getInstance().getDaoSession().getVideoTestScoreEntityDao().queryBuilder()
                                .where(VideoTestScoreEntityDao.Properties.UserAccount.eq(data.videoTestScore.get(i).userAccount)
                                        , VideoTestScoreEntityDao.Properties.VideoId.eq(data.videoTestScore.get(i).videoId)).build().unique() == null) {
                            DaoManager.getInstance().getDaoSession().getVideoTestScoreEntityDao().insertOrReplaceInTx(data.videoTestScore.get(i));
                        }
                    }
                }

            }
        } catch (Exception e){
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public UserEntity loginWithLocal(String userAccount) {
        try {
            return DaoManager.getInstance().getDaoSession().getUserEntityDao().queryBuilder()
                    .where(UserEntityDao.Properties.UserAccount.eq(userAccount)).unique();
        }catch (Exception e){
            LogUtils.e(e.getMessage());
            return null;
        }

    }

    @Override
    public List<UserEntity> getPopUsers() {
        return DaoManager.getInstance().getDaoSession().getUserEntityDao().queryBuilder().list();
    }

    @Override
    public void addUserToLocal(UserEntity userEntity) {
        if (!(DaoManager.getInstance().getDaoSession().getUserEntityDao().queryBuilder()
                .where(UserEntityDao.Properties.UserId.eq(userEntity.getUserId())).count() > 0)) {
            long count = DaoManager.getInstance().getDaoSession().getUserEntityDao().count();
            if (count >= 5){
                for (int i = 0;i <= count - 5;i++) {
                    DaoManager.getInstance().getDaoSession().getUserEntityDao().delete(DaoManager.getInstance().getDaoSession().getUserEntityDao().loadAll().get(i));
                }
            }
            DaoManager.getInstance().getDaoSession().getUserEntityDao().insertOrReplaceInTx(userEntity);
        }else {
            DaoManager.getInstance().getDaoSession().getUserEntityDao().insertOrReplaceInTx(userEntity);
        }
    }
    private List<UserEntity> userEntities;
    @Override
    public String getOfflineSnWithUserAccount(String userAccount) {
        userEntities = DaoManager.getInstance().getDaoSession().getUserEntityDao().queryBuilder().where(UserEntityDao.Properties.UserAccount.eq(userAccount)).list();
        if (userEntities != null && userEntities.size() > 0){
            return userEntities.get(0).getOfflineSn();
        }else {
            return "";
        }
    }


}

package com.begoit.mooc.offline.ui.activity.main;

import android.text.TextUtils;

import com.begoit.mooc.offline.requests.ApiConstants;
import com.begoit.mooc.offline.entity.kindMenu.CourseKindMenuData;
import com.begoit.mooc.offline.utils.GsonUtil;

import io.reactivex.functions.Consumer;

/**
 * @Description:主页逻辑业务处理
 * @Author:gxj
 * @Time 2019/2/22
 */

public class MainpagePresenter extends MainpageContract.MainpagePresenter {
    private CourseKindMenuData courseKindMenuItemEntity;//侧边菜单响应数据
    @Override
    void loadLeftNavViewData() {
        mRxManage.add(mModel.getLeftMenuItem().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                courseKindMenuItemEntity = GsonUtil.getInstance().fromJson(s,CourseKindMenuData.class);
                if (ApiConstants.SUCCESSCODE == courseKindMenuItemEntity.status) {
                    mView.initLeftNavView(courseKindMenuItemEntity.data);
                } else {
                    if (!TextUtils.isEmpty(courseKindMenuItemEntity.error))
                    {

                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }));
//        mView.initLeftNavView(mModel.getLeftMenuItem());
    }

    @Override
    void leftNavResult() {

    }

    @Override
    void loadBottomNavViewData() {

    }
}

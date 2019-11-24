package com.begoit.mooc.offline.ui.activity.courseKind;

import android.text.TextUtils;

import com.begoit.mooc.offline.requests.ApiConstants;
import com.begoit.mooc.offline.entity.kindMenu.CourseKindMenuData;
import com.begoit.mooc.offline.utils.GsonUtil;

import io.reactivex.functions.Consumer;

/**
 * @Description:分类课程业务管理
 * @Author:gxj
 * @Time 2019/3/4
 */

public class CourseKindPresenter extends CourseKindContract.Presenter {
    private CourseKindMenuData courseKindMenuItemEntity;//侧边菜单响应数据
    @Override
    void getLeftNavItems() {
        mRxManage.add(mModel.getLeftNavItems().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                courseKindMenuItemEntity = GsonUtil.getInstance().fromJson(s,CourseKindMenuData.class);
                if (ApiConstants.SUCCESSCODE == courseKindMenuItemEntity.status) {
                    mView.initLeftNav(courseKindMenuItemEntity.data);
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
//        mView.initLeftNav(mModel.getLeftNavItems());
    }
}

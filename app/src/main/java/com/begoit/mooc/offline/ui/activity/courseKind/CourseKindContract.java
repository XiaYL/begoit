package com.begoit.mooc.offline.ui.activity.courseKind;

import com.begoit.mooc.offline.base.BasePresenter;
import com.begoit.mooc.offline.base.IBaseModel;
import com.begoit.mooc.offline.base.IBaseView;
import com.begoit.mooc.offline.entity.kindMenu.MenuItemEntity;
import java.util.List;
import io.reactivex.Observable;

/**
 * @Description:课程类别综合协议
 * @Author:gxj
 * @Time 2019/3/4
 */

public interface CourseKindContract {

    interface View extends IBaseView {
        void initLeftNav(List<MenuItemEntity> leftNavItemEntityList);
    }

    interface Model extends IBaseModel {
        Observable<String> getLeftNavItems();
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        abstract void getLeftNavItems();
    }
}

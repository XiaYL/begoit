package com.begoit.mooc.offline.ui.activity.main;

import com.begoit.mooc.offline.base.BasePresenter;
import com.begoit.mooc.offline.base.IBaseView;
import com.begoit.mooc.offline.base.IBaseModel;
import com.begoit.mooc.offline.entity.kindMenu.MenuItemEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Description:主页综合协议
 * @Author:gxj
 * @Time 2019/2/22
 */

public interface MainpageContract {
    interface MainpageView extends IBaseView{
        void initLeftNavView(List<MenuItemEntity> leftNavItemEntities);
        void initBottomNavView();
    }

    interface MainpageModel extends IBaseModel {
        Observable<String> getLeftMenuItem();
    }

     abstract class MainpagePresenter extends BasePresenter<MainpageView,MainpageModel> {
         abstract void loadLeftNavViewData();
         abstract void leftNavResult();
         abstract void loadBottomNavViewData();
     }
}

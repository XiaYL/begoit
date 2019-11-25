package com.begoit.mooc.offline.ui.activity.setip;

import com.begoit.mooc.offline.base.BasePresenter;
import com.begoit.mooc.offline.base.IBaseModel;
import com.begoit.mooc.offline.base.IBaseView;

/**
 * Created by user on 2019/11/25.
 */

public interface SetIpContract {
    interface SetIpView extends IBaseView {
        void setHosts(String appHost, String fileHost);
    }

    interface SetIpModel extends IBaseModel {
        String[] readHosts();

        void saveHosts(String appHost, String fileHost);
    }

    abstract class SetIpPresenter extends BasePresenter<SetIpView, SetIpModel> {
        abstract void setDefaultHosts();

        abstract void saveHosts(String appHost, String fileHost);
    }
}

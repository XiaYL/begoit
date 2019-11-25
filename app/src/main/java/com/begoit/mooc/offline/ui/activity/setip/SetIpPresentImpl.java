package com.begoit.mooc.offline.ui.activity.setip;

/**
 * Created by user on 2019/11/25.
 */

public class SetIpPresentImpl extends SetIpContract.SetIpPresenter {

    @Override
    void setDefaultHosts() {
        String[] hosts = mModel.readHosts();
        mView.setHosts(hosts[0], hosts[1]);
    }

    @Override
    void saveHosts(String appHost, String fileHost) {
        mModel.saveHosts(appHost, fileHost);
    }
}

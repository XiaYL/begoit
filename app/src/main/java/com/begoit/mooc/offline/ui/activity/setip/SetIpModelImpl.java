package com.begoit.mooc.offline.ui.activity.setip;

import com.begoit.mooc.offline.requests.ApiConstants;

/**
 * Created by user on 2019/11/25.
 */

public class SetIpModelImpl implements SetIpContract.SetIpModel {
    @Override
    public String[] readHosts() {
        return new String[]{ApiConstants.getAppHost(), ApiConstants.getFileHost()};
    }

    @Override
    public void saveHosts(String appHost, String fileHost) {
        ApiConstants.setAppHost(appHost);
        ApiConstants.setFileHost(fileHost);
    }
}

package com.begoit.mooc.offline.ui.activity.setip;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2019/11/25.
 */

public class SetIpActivity extends BaseActivity<SetIpPresentImpl, SetIpModelImpl> implements SetIpContract.SetIpView {

    @BindView(R.id.APP_HOST)
    EditText APP_HOST;
    @BindView(R.id.FILE_HOST)
    EditText FILE_HOST;

    @Override
    protected int getLyoutId() {
        return R.layout.activity_set_ip;
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this, mModel);
    }

    @Override
    protected void initView() {
        mPresenter.setDefaultHosts();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @OnClick({R.id.save_button})
    public void saveData(View v) {
        String appHost = APP_HOST.getText().toString();
        String fileHost = FILE_HOST.getText().toString();
        if (TextUtils.isEmpty(appHost)) {

        }
        if (TextUtils.isEmpty(fileHost)) {

        }
        mPresenter.saveHosts(appHost, fileHost);
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void setHosts(String appHost, String fileHost) {
        APP_HOST.setText(appHost);
        FILE_HOST.setText(fileHost);
    }
}

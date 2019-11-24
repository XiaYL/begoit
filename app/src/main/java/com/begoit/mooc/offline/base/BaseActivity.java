package com.begoit.mooc.offline.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.offline.ui.activity.login.LoginActivity;
import com.begoit.mooc.offline.utils.ActivityTaskUtils;
import com.begoit.mooc.offline.utils.TUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description:Activity 基类
 * @Author:gxj
 * @Time 2019/2/18
 */

public abstract class BaseActivity<P extends BasePresenter,M extends IBaseModel> extends AutoLayoutActivity {

    protected P mPresenter;
    protected M mModel;
    protected Unbinder unbinder;
    protected Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTaskUtils.getAppManager().addActivity(this);
        setContentView(getLyoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mContext = BegoitMoocApplication.Companion.getContextInstance();
        unbinder = ButterKnife.bind(this);

        initStatusBar("#ffffff");

        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);

        initPresenter();

        initView();

    }

    /**
     * 设置沉浸式状态栏样式
     */
    public void initStatusBar(String color){
        //在BaseActivity里初始化
       ImmersionBar.with(this)
                .transparentStatusBar()
//               .statusBarColor(color)//原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .statusBarDarkFont(true, 0.0f)
                //使用该属性必须指定状态栏的颜色，不然状态栏透明，很难看
//                .fitsSystemWindows(true)
                //所有子类都将继承这些相同的属性
                .init();
    }

    /**
     * 绑定layout
     * @return layout
     */
    protected abstract int getLyoutId();

    /**
     * 绑定presenter
     */
    protected abstract void initPresenter();

    /**
     * 初始化view
     */
    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 外部存储权限申请 (需要调用的地方动态申请)
     */
    public boolean requestPermission(String[] permission) {
        if (!AndPermission.hasPermission(BaseActivity.this, permission)) {
            AndPermission.with(this)
                    .permission(permission)
                    .requestCode(REQUEST_CODE_SETTING)
                    .callback(permissionListener)
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                            AndPermission.rationaleDialog(BaseActivity.this, rationale).show();
                        }
                    })
                    .start();
            return false;
        }
        return true;
    }

    public static final int REQUEST_CODE_SETTING = 300;
    /**
     * 回调监听。
     */
    public PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            Toast.makeText(BaseActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(BaseActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(BaseActivity.this, REQUEST_CODE_SETTING).show();
            }
        }
    };
}

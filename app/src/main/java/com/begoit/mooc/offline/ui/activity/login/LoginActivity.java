package com.begoit.mooc.offline.ui.activity.login;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.base.BaseActivity;
import com.begoit.mooc.offline.entity.user.UserEntity;
import com.begoit.mooc.offline.ui.activity.main.MainpageActivity;
import com.begoit.mooc.offline.ui.adapter.UsersPopAdapter;
import com.begoit.mooc.offline.ui.service.DownloadCourseService;
import com.begoit.mooc.offline.utils.AppLogUtil;
import com.begoit.mooc.offline.utils.DeviceInfoUtil;
import com.begoit.mooc.offline.utils.LogUtils;
import com.begoit.mooc.offline.utils.SharedPreferencesUtils;
import com.begoit.mooc.offline.utils.ToastUtil;
import com.begoit.mooc.offline.widget.LoadingProgressDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity<LoginPresenter,LoginModelimpl>
        implements LoginContract.LoginView,UsersPopAdapter.OnItemSelectListener {
    // UI references.
    @BindView(R.id.email)
    EditText mEmailView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.email_sign_in_button)
    TextView mEmailSignInButton;
    @BindView(R.id.container)
    AutoRelativeLayout rootView;

    //历史登录用户
    private UsersPopAdapter usersPopAdapter;
    private PopupWindow popUsers;
    private View popView;
    private ListView users;

    @OnClick({R.id.email, R.id.login_form, R.id.container})
    public void showUsers(View v){
        switch (v.getId()){
            case R.id.email:
                if (popUsers != null && popUsers.isShowing()){
                    popUsersDismiss();
                    return;
                }
                mPresenter.getPopUsers();
                break;
            case R.id.container:
            case R.id.login_form:
                if (popUsers != null && popUsers.isShowing()){
                    popUsersDismiss();
                }
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) { // 当前类不是该Task的根部，那么之前启动
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) { // 当前类是从桌面启动的
                    finish(); // finish掉该类，直接打开该Task中现存的Activity
                    return;
                }
            }
        }

        LogUtils.i("height " + DeviceInfoUtil.getDeviceHeight() + "  width  " + DeviceInfoUtil.getDevicesWidth());
        Intent intent = new Intent(this, DownloadCourseService.class);
        startService(intent);
    }

    @Override
    protected int getLyoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        requestPermission();
        // Set up the login form.
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        controlKeyboardLayout(rootView,mEmailSignInButton);
    }
    /**
     * @param root
     *            最外层布局，需要调整的布局
     * @param scrollToView
     *            被键盘遮挡的scrollToView(需要保证可见的view)，滚动root,使scrollToView在root可视区域的底部
     */
    private void controlKeyboardLayout(final View root, final View scrollToView) {
        // 注册一个回调函数，当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时调用这个回调函数。
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
//                        Rect rect = new Rect();
//                        // 获取root在窗体的可视区域
//                        root.getWindowVisibleDisplayFrame(rect);
//                        // 当前视图最外层的高度减去现在所看到的视图的最底部的y坐标
//                        int rootInvisibleHeight = root.getRootView()
//                                .getHeight() - rect.bottom;
//                        LogUtils.i("可见的高度" + rootInvisibleHeight);
                        // 若rootInvisibleHeight高度大于100，则说明当前视图上移了
                        popUsersDismiss();
//                        if (rootInvisibleHeight > 100) {
//                            //软键盘弹出来的时候
//                            int[] location = new int[2];
//                            // 获取scrollToView在窗体的坐标
//                            scrollToView.getLocationInWindow(location);
//                            // 计算root滚动高度，使scrollToView在可见区域的底部
//                            int srollHeight = (location[1] + scrollToView
//                                    .getHeight()) - rect.bottom;
////                            LogUtils.i("y轴的偏移量" + srollHeight);
//                            //避免下移
//                            if (srollHeight > 0) {
//                                root.scrollTo(0, 0);
//                            }else {
//                                root.scrollTo(0, 0);
//                            }
//                        } else {
//                            // 软键盘没有弹出来的时候
//                            root.scrollTo(0, 0);
//                        }
                    }
                });
    }


    @Override
    protected void initPresenter() {
        mPresenter.attachView(this,mModel);
    }

    /**
     * 执行登录
     */
    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        LogUtils.d(email);
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            showEditError("输入有误");
        } else {
            mPresenter.login(mEmailView.getText().toString(),mPasswordView.getText().toString());
        }

    }
    @Override
    public void showLoading() {
        LoadingProgressDialog.showLoading(this,"登录中");
    }

    @Override
    public void cancelLoading() {
        LoadingProgressDialog.stopLoading();
    }

    @Override
    public void showEditError(String msg) {

    }


    @Override
    public void loginSuccess(UserEntity userEntity) {
        cancelLoading();
        BegoitMoocApplication.Companion.getContextInstance().setCurrentUser(userEntity);

        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainpageActivity.class);
        startActivity(intent);
        finish();
        AppLogUtil.INSTANCE.setLog(AppLogUtil.INSTANCE.getTPYE_SIGN_IN());
        AppLogUtil.INSTANCE.setLog(AppLogUtil.INSTANCE.getTYPE_USER_DOWNLOAD());
    }

    @Override
    public void loginError(String msg) {
        cancelLoading();
        ToastUtil.showShortToast(msg);
    }

    @Override
    public void showPopUsers(List<UserEntity> dataList) {
        if (dataList != null && dataList.size() > 0) {
            if (popUsers == null || popView == null || usersPopAdapter== null) {
                popView = LayoutInflater.from(mContext).inflate(R.layout.pop_login_users, null);
                users = popView.findViewById(R.id.users);
                popUsers = new PopupWindow(popView, 684, 216, true);
                usersPopAdapter = new UsersPopAdapter(mContext, dataList,this);
                popUsers.setFocusable(false);
                usersPopAdapter = new UsersPopAdapter(mContext, dataList,this);
                users.setAdapter(usersPopAdapter);
            }
            usersPopAdapter.setNewData(dataList);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    popUsers.showAsDropDown(mEmailView, 0, 10);
                }
            }, 100);

        }
    }


    /**
     * 外部存储权限申请
     */
    private void requestPermission() {
        if (!AndPermission.hasPermission(LoginActivity.this, Permission.STORAGE)
               || !AndPermission.hasPermission(LoginActivity.this, Permission.PHONE)) {
            AndPermission.with(this)
                    .permission(Permission.STORAGE, Permission.PHONE)
                    .requestCode(REQUEST_CODE_SETTING)
                    .callback(permissionListener)
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                            if (!AndPermission.hasPermission(LoginActivity.this, Permission.STORAGE)
                                    || !AndPermission.hasPermission(LoginActivity.this, Permission.PHONE))
                            {
                                AndPermission.rationaleDialog(LoginActivity.this, rationale).show();
                            }
                        }
                    })
                    .start();
        }
    }

    private static final int REQUEST_CODE_SETTING = 300;
    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            Toast.makeText(LoginActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(LoginActivity.this, deniedPermissions)) {
                if (!AndPermission.hasPermission(LoginActivity.this, Permission.STORAGE)
                        || !AndPermission.hasPermission(LoginActivity.this, Permission.PHONE) ) {
                    // 第一种：用默认的提示语。
                    AndPermission.defaultSettingDialog(LoginActivity.this, REQUEST_CODE_SETTING).show();
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (popUsers != null && popUsers.isShowing()){
            popUsersDismiss();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemSelected(UserEntity item) {
        popUsersDismiss();
        if (mEmailView != null && item != null) {
            mEmailView.setText(item.getUserAccount());
            mPasswordView.setText("");
        }
    }

    //隐藏历史用户选择窗口
    private void popUsersDismiss(){
        if (popUsers != null && popUsers.isShowing()){
            popUsers.dismiss();
        }
    }
}


package com.begoit.mooc.offline.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.begoit.mooc.offline.R;

/**
 * @Description:数据加载通用ProgressDialog
 * @Author:gxj
 * @Time 2019/2/20
 */

public class LoadingProgressDialog  extends Dialog implements DialogInterface.OnCancelListener {

    private volatile static LoadingProgressDialog sDialog;
    //提示信息可
    private static TextView tvMessage;

    private LoadingProgressDialog(Context context, CharSequence message) {
        super(context, R.style.LoadingProgressDialog);

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_progress, null);
        tvMessage = (TextView) view.findViewById(R.id.tv_message);
        if (!TextUtils.isEmpty(message)) {
            tvMessage.setText(message);
        }
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, lp);

        setOnCancelListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // 点手机返回键等触发Dialog消失，应该取消正在进行的网络请求等

    }

    public static synchronized void showLoading(Context context) {
        showLoading(context, "loading...");
    }

    public static synchronized void showLoading(Context context, CharSequence message) {
        showLoading(context, message, false);
    }

    public static void changeMsg(CharSequence message){
        if (tvMessage != null){
            tvMessage.setText(message);
        }
    }

    public static synchronized void showLoading(Context context, CharSequence message, boolean cancelable) {
        try {
            if (sDialog != null && sDialog.isShowing()) {
                sDialog.dismiss();
            }

            if (context == null || !(context instanceof Activity)) {
                return;
            }
            sDialog = new LoadingProgressDialog(context, message);
            sDialog.setCancelable(cancelable);

            if (sDialog != null && !sDialog.isShowing() && !((Activity) context).isFinishing()) {
                sDialog.show();
            }
        }catch (Exception e){
            if (context == null || !(context instanceof Activity)) {
                return;
            }
            sDialog = new LoadingProgressDialog(context, message);
            sDialog.setCancelable(cancelable);

            if (sDialog != null && !sDialog.isShowing() && !((Activity) context).isFinishing()) {
                sDialog.show();
            }
        }

    }

    public static synchronized void stopLoading() {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }
        sDialog = null;
    }
}

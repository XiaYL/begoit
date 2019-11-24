package com.begoit.mooc.offline.widget.basedialog;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.begoit.mooc.offline.R;

/**.
 * 对话框工具类
 */
public class DialogUtils{
    public static DialogUtils mInstance;
    Dialog dialog = null;

    /**
     * 双重检查锁定
     * @return 在getInstance中做了两次null检查，确保了只有第一次调用单例的时候才会做同步，这样也是线程安全的，同时避免了每次都同步的性能损耗
     */
    public static DialogUtils getInstance() {
        if (mInstance == null) {
            synchronized (DialogUtils.class) {
                if (mInstance == null) {
                    mInstance = new DialogUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 底部2个按钮的通用对话框
     * @param activity
     * @param title 标题
     * @param content 内容
     * @param leftButton 左侧按钮
     * @param rightButton 右侧按钮
     */
    public void showGenericDialogForTwoButtonCommon(final Activity activity, final String title, final String content, String leftButton, String rightButton, final ButtonClickCallBack buttonClickCallBack) {

        if (activity == null)
        {
            return;
        }
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        dialog = new Dialog(activity, R.style.Transparent);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager wmanager = window.getWindowManager();
        Display display = wmanager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        window.setAttributes(layoutParams);
        window.setContentView(R.layout.dialog_generic_two_button);
        TextView cancelBtn = window.findViewById(R.id.tv_cancel);
        cancelBtn.setText(leftButton);
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                dialog.dismiss();
            }
        });

        TextView sureBtn = window.findViewById(R.id.tv_sure);
        sureBtn.setText(rightButton);
        sureBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
                if (buttonClickCallBack != null) {
                    buttonClickCallBack.clickCallBack(view);
                }
            }
        });
        TextView titleTextView = window.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title))
        {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }
        else
        {
            titleTextView.setVisibility(View.GONE);
        }
        TextView contentTextView = window.findViewById(R.id.tv_content);
        if (!TextUtils.isEmpty(content))
        {
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setText(content);
        }
        else
        {
            contentTextView.setVisibility(View.GONE);
        }
    }

    /** 底部1个按钮的通用对话框
     * @param activity
     * @param title 标题
     * @param content 内容
     * @param button 左侧按钮
     */
    public void showGenericDialogForOneButtonCommon(final Activity activity, final String title, final String content, String button)
    {

        if (activity == null)
        {
            return;
        }
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        dialog = new Dialog(activity, R.style.Transparent);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager wmanager = window.getWindowManager();
        Display display = wmanager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        window.setAttributes(layoutParams);
        window.setContentView(R.layout.dialog_generic_one_button);
        TextView cancelBtn = window.findViewById(R.id.tv_cancel);
        cancelBtn.setText(button);
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                dialog.dismiss();
            }
        });

        TextView titleTextView = window.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title))
        {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }
        else
        {
            titleTextView.setVisibility(View.GONE);
        }
        TextView contentTextView = window.findViewById(R.id.tv_content);
        if (!TextUtils.isEmpty(content))
        {
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setText(content);
        }
        else
        {
            contentTextView.setVisibility(View.GONE);
        }
    }

    /** 底部无按钮的通用对话框
     * @param activity
     * @param title 标题
     * @param content 内容
     * @param isAnimator 带动态省略号结尾
     */
    public void showGenericDialogForNoButtonCommon(final Activity activity, final String title, final String content,boolean isAnimator)
    {

        if (activity == null)
        {
            return;
        }
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        dialog = new Dialog(activity, R.style.Transparent);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager wmanager = window.getWindowManager();
        Display display = wmanager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        dialog.setCanceledOnTouchOutside(false);
        window.setAttributes(layoutParams);
        window.setContentView(R.layout.dialog_generic_no_button);

        TextView titleTextView = window.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title))
        {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }
        else
        {
            titleTextView.setVisibility(View.GONE);
        }
        final TextView contentTextView = window.findViewById(R.id.tv_content);
        if (!TextUtils.isEmpty(content))
        {
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setText(content);
            if (isAnimator){
                final String[] scoreText = { ".    ", ". .  ", ". . ."};
                ValueAnimator animator = ValueAnimator.ofInt(0,3)
                        .setDuration(1000);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int i = (int) animation.getAnimatedValue();
                        contentTextView.setText(content + scoreText[i % scoreText.length]);
                    }
                });
                animator.start();
            }
        }
        else
        {
            contentTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 取消对话框
     */
    public void dismissDialog(){
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }


    public interface ButtonClickCallBack
    {
        void clickCallBack(View view);
    }
}

package com.begoit.mooc.offline.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.begoit.mooc.offline.R;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * @Description:
 * @Author:gxj
 * @Time 2019/3/13
 */

public class BaseEmptyView extends AutoLinearLayout {

    private ImageView icon;
    private TextView content;

    public BaseEmptyView(Context context) {
        super(context);
        initView(context);
    }

    public BaseEmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public BaseEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.base_layout_empty,this);
        icon = (ImageView)findViewById(R.id.iv_img);
        content = (TextView) findViewById(R.id.tv_content);
    }

    public void setIcon(int resourceId){
        if (this.icon != null) {
            this.icon.setImageResource(resourceId);
        }
    }
    public void setContent(String content){
        if (this.content != null) {
            this.content.setText(content);
        }
    }
}

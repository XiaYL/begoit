package com.begoit.mooc.offline.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;

import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideoTestEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @Description:考核试题完成与否状态展示
 * @Author:gxj
 * @Time 2019/4/24
 */

public class TestListStatusAdapter extends BaseQuickAdapter<VideoTestEntity,BaseViewHolder> {
    private int position = 1;
    private OnItemClickListener onItemClickListener;

    public TestListStatusAdapter(@Nullable List<VideoTestEntity> data, Context mContext) {
        super(R.layout.item_test_status, data);
        this.mContext = mContext;
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void changeData(@Nullable List<VideoTestEntity> data){
        position = 1;
        setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final VideoTestEntity item) {
        if (item.userAnswer == null) {
            helper.setBackgroundRes(R.id.tv_position, R.drawable.bg_light_yellow_round_no_line);
            helper.setTextColor(R.id.tv_position, Color.parseColor("#F5A623"));
        }else {
            helper.setBackgroundRes(R.id.tv_position, R.drawable.bg_yellow_round_no_line);
            helper.setTextColor(R.id.tv_position, Color.parseColor("#ffffff"));
        }
        helper.setText(R.id.tv_position,String.valueOf(position++));


        helper.setOnClickListener(R.id.tv_position, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(getPosition(item));
            }
        });
    }

    private int getPosition(VideoTestEntity item){
        for (int i = 0;i < mData.size();i++){
            if (mData.get(i).id.equals(item.id)){
                return i;
            }
        }
        return -1;
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }

}

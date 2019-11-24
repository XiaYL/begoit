package com.begoit.mooc.offline.ui.activity.learning.courseTreeListDataBind;

import android.support.annotation.NonNull;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideonobEntity;
import com.begoit.treerecyclerview.base.ViewHolder;
import com.begoit.treerecyclerview.item.TreeItem;
import org.greenrobot.eventbus.EventBus;

/**
 * @Description:
 * @Author:gxj
 * @Time 2019/3/29
 */

public class VideonobDatabinger extends TreeItem<VideonobEntity> {
    @Override
    public int getLayoutId() {
        return R.layout.rv_item_discount_third;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder) {
        holder.setText(R.id.tv_video_name, data.videoTitle);
        if (data.isVideo == 0) {
            holder.setImageResource(R.id.iv_video_status,R.drawable.ic_video_nomal);
        }else {
            holder.setImageResource(R.id.iv_video_status,R.drawable.ic_test);
        }
    }

    @Override
    public int getSpanSize(int maxSpan) {
        return maxSpan / 6;
    }

    @Override
    public void onClick(ViewHolder viewHolder) {
        EventBus.getDefault().post(data);
    }
}

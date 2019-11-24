package com.begoit.mooc.offline.ui.activity.learning.courseTreeListDataBind;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.ArticelEntity;
import com.begoit.treerecyclerview.base.ViewHolder;
import com.begoit.treerecyclerview.factory.ItemHelperFactory;
import com.begoit.treerecyclerview.item.TreeItem;
import com.begoit.treerecyclerview.item.TreeItemGroup;
import java.util.List;

/**
 * @Description:课程 章->节 item数据绑定
 * @Author:gxj
 * @Time 2019/3/29
 */

public class ArtcleDataBinder extends TreeItemGroup<ArticelEntity> {

    @Override
    public int getLayoutId() {
        return R.layout.rv_item_discount_second;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        viewHolder.setText(R.id.tv_position,data.sectionPosition);
        viewHolder.setText(R.id.tv_title,data.sectionName);
    }

    @Nullable
    @Override
    protected List<TreeItem> initChildList(ArticelEntity data) {
        return ItemHelperFactory.createTreeItemList(data.getVideonob(),null,this);
    }
}

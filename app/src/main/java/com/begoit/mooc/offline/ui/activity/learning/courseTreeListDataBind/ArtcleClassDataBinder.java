package com.begoit.mooc.offline.ui.activity.learning.courseTreeListDataBind;

import android.support.annotation.NonNull;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.ArticleClassEntity;
import com.begoit.treerecyclerview.base.ViewHolder;
import com.begoit.treerecyclerview.factory.ItemHelperFactory;
import com.begoit.treerecyclerview.item.TreeItem;
import com.begoit.treerecyclerview.item.TreeItemGroup;
import com.begoit.treerecyclerview.manager.ItemManager;
import java.util.List;

/**
 * @Description:
 * @Author:gxj
 * @Time 2019/3/29
 */

public class ArtcleClassDataBinder extends TreeItemGroup<ArticleClassEntity> {

    @Override
    public int getLayoutId() {
        return R.layout.rv_item_discount_first;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder) {
        holder.setText(R.id.tv_position,data.chapterPosition);
        holder.setText(R.id.tv_title, data.chapterName);
    }
    @Override
    public List<TreeItem> initChildList(ArticleClassEntity data) {
        return ItemHelperFactory.createTreeItemList(data.getArticel(),ArtcleDataBinder.class, this);
    }

    @Override
    protected void onExpand() {
        ItemManager itemManager = getItemManager();
        if (itemManager != null) {
            int itemPosition = itemManager.getItemPosition(this);
            List datas = itemManager.getAdapter().getDatas();
            datas.addAll(itemPosition + 1, getExpandChild());
            itemManager.getAdapter().notifyItemRangeInserted(itemPosition + 1, getExpandChild().size());
        }
    }
}

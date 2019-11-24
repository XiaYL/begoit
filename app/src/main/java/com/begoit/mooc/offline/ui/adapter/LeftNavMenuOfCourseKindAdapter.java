package com.begoit.mooc.offline.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.kindMenu.MenuItemEntity;
import com.begoit.mooc.offline.utils.ImagePlaceHolderUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

/**
 * @Description:主页左侧拉菜单适配器
 * @Author:gxj
 * @Time 2019/2/22
 */

public class LeftNavMenuOfCourseKindAdapter extends BaseMultiItemQuickAdapter<MenuItemEntity,BaseViewHolder>{

    //菜单单元点击事件监听
    private OnMenuItemClickListener onMenuItemClickListener;
    private int typeSecond;//二级菜单边距标记
    private int iconPosition;

    public LeftNavMenuOfCourseKindAdapter(List<MenuItemEntity> data) {
        super(data);
        addItemType(MenuItemEntity.TYPE_COMMON, R.layout.item_mainpage_leftmenu_common);
        addItemType(MenuItemEntity.TYPE_FIRST, R.layout.item_mainpage_leftmenu_first);
        addItemType(MenuItemEntity.TYPE_SECOND,R.layout.item_mainpage_leftmenu_second);
        iconPosition = 0;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener){
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MenuItemEntity item) {
         switch (item.getLevel()){
             case MenuItemEntity.TYPE_COMMON:
                 typeSecond = 0;
                 helper.setImageResource(R.id.iv_icon,ImagePlaceHolderUtil.INSTANCE.getLeftMenuIcon(iconPosition));
                 helper.setText(R.id.tv_menu_name,item.getTypeName());
                 helper.setOnClickListener(R.id.rl_container, new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         onMenuItemClickListener.onMenuItemClick(item);
                     }
                 });
                 iconPosition++;
                 break;
             case MenuItemEntity.TYPE_FIRST:
                 typeSecond = 0;
                 helper.setImageResource(R.id.iv_icon,ImagePlaceHolderUtil.INSTANCE.getLeftMenuIcon(iconPosition));
                 helper.setText(R.id.tv_menu_name,item.getTypeName());
                 helper.setOnClickListener(R.id.rl_container, new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         onMenuItemClickListener.onMenuItemClick(item);
                     }
                 });
                 iconPosition++;
                 break;
             case MenuItemEntity.TYPE_SECOND:
                 typeSecond++;
                 helper.setText(R.id.tv_menu_name,item.getTypeName());
                 helper.setOnClickListener(R.id.tv_menu_name, new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         onMenuItemClickListener.onMenuItemClick(item);
                     }
                 });
                 if (typeSecond % 2 == 0){
                     helper.setGone(R.id.line,true);
                 }else{
                     helper.setGone(R.id.line,false);
                 }
                 break;
         }
    }

    private int getItemPosition(MenuItemEntity item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getLevel();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    /**
                     * 根据GridLayoutManager的getSpanSize方法可以动态的设置item跨列数
                     * 需要设置：4个参数的GridLayoutManager
                     * new GridLayoutManager(getActivity(),6,GridLayoutManager.VERTICAL,false);
                     * 这里的6（自己设置的最好设置成偶数）就相当于分母，6默认显示一整行（1列），下面的3 和2 就相当于分子，返回3就是（1/2）所以此类型对应的是2列，返回2就是（1/3）所以此类型对应的是3列
                     * */
                    switch (type) {
                        case MenuItemEntity.TYPE_FIRST:
                            //一级菜单占用整行，返回总共的列数，也就是占用整个的宽度
                            return gridManager.getSpanCount();
                        case MenuItemEntity.TYPE_SECOND:
                            //这里返回1，也就是占用 1/2的宽度
                            return 1;
                        default:
                            //默认显示2列
                            return 2;
                    }
                }
            });
        }
    }

    public interface OnMenuItemClickListener{
        void onMenuItemClick(MenuItemEntity item);
    }
}

package com.begoit.mooc.offline.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.user.UserEntity;
import java.util.List;

/**
 * @Description:历史用户列表适配器
 * @Author:gxj
 * @Time 2019/3/7
 */
public class UsersPopAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<UserEntity> dataList;
    private OnItemSelectListener onItemSelectListener;
    private UserEntity selectedUser;
    public UsersPopAdapter(Context mContext,List<UserEntity> dataList,OnItemSelectListener onItemSelectListener){
        this.dataList = dataList;
        inflater = LayoutInflater.from(mContext);
        this.onItemSelectListener = onItemSelectListener;
    }

    public void setNewData(List<UserEntity> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0:dataList.size();
    }

    @Override
    public UserEntity getItem(int position) {
        return dataList == null ? null:dataList.get(position);
    }

    public void setSelectedUser(UserEntity item){
        selectedUser = item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_pop_user,null);
            viewHolder = new ViewHolder();
            viewHolder.userName = convertView.findViewById(R.id.tv_user_name);
            viewHolder.llContainer = convertView.findViewById(R.id.ll_container);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (selectedUser != null){
            if (selectedUser.userId.equals(dataList.get(position).userId)){
                viewHolder.llContainer.setBackgroundColor(Color.parseColor("#FAFAFA"));
            }else {
                viewHolder.llContainer.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }
        viewHolder.userName.setText(dataList.get(position).getUserAccount());
        viewHolder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedUser(dataList.get(position));
                onItemSelectListener.onItemSelected(dataList.get(position));
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView userName;
        LinearLayout llContainer;
    }

    public interface OnItemSelectListener{
        void onItemSelected(UserEntity item);
    }
}

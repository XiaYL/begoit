package com.begoit.treerecyclerview.adpater.wrapper;

import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.begoit.treerecyclerview.base.BaseRecyclerAdapter;
import com.begoit.treerecyclerview.base.ViewHolder;
import com.begoit.treerecyclerview.manager.ItemManager;

/**
 * Created by begoit on 2017/4/30.
 * 在最后一个，优先级高于loadwrapper,
 */
public class HeaderAndFootWrapper<T> extends BaseWrapper<T> {
    private static final int HEAD_ITEM = 1000;
    private SparseArray<View> mHeaderViews = new SparseArray<>();
    private boolean headShow = true;
    private int mHeaderSize;

    public HeaderAndFootWrapper(BaseRecyclerAdapter<T> adapter) {
        super(adapter);
        getItemManager().addCheckItemInterfaces(new ItemManager.CheckItemInterface() {
            @Override
            public int itemToDataPosition(int position) {
                return position - getHeadersCount();
            }

            @Override
            public int dataToItemPosition(int index) {
                return index + getHeadersCount();
            }
        });
    }

    @Override
    public T getData(int position) {
        return mAdapter.getData(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return ViewHolder.createViewHolder(mHeaderViews.get(viewType));
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolderClick(@NonNull ViewHolder holder, View view) {
        int layoutPosition = holder.getLayoutPosition();
        if (isHeaderViewPos(layoutPosition)) {
            return;
        }
        super.onBindViewHolderClick(holder, view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        super.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + mAdapter.getItemCount();
    }


    @Override
    public int getItemSpanSize(int position, int maxSpan) {
        if (isHeaderViewPos(position)) {
            return maxSpan;
        }
        return super.getItemSpanSize(position, maxSpan);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        }
        return super.getItemViewType(position - getHeadersCount());
    }


    public void addHeaderView(View view) {
        int size = mHeaderViews.size();
        mHeaderViews.put(HEAD_ITEM + size, view);
        mHeaderSize++;
    }

    @Deprecated
    public void addFootView(View view) {
    }

    protected boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    @Deprecated
    protected boolean isFooterViewPos(int position) {
        return false;
    }


    public void setShowHeadView(boolean show) {
        this.headShow = show;
        int size = mHeaderViews.size();
        for (int i = 0; i < size; i++) {
            View view = mHeaderViews.valueAt(i);
            view.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public int getHeadersCount() {
        if (!headShow) {
            return 0;
        }
        return mHeaderSize;
    }
}

package com.begoit.mooc.offline.widget;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * @Description:应用于RecyclerView的GridLayoutManager，水平方向上固定间距大小，从而使条目宽度自适应
 * Item的宽度应设为MATCH_PARAENT
 * @Author:gxj
 * @Time 2019/2/26
 */
public class GridAverageGapItemDecoration extends RecyclerView.ItemDecoration  {
    private float gapHorizontalDp;
    private float gapVerticalDp;
    private float edgePaddingDp;
    private int gapHSizePx = -1;
    private int gapVSizePx = -1;
    private int edgePaddingPx = -1;
    private int eachItemPaddingH = -1; //每个条目应该在水平方向上加的padding 总大小，即=paddingLeft+paddingRight
    private Rect preRect = new Rect();

    /**
     * @param gapHorizontalDp 水平间距
     * @param gapVerticalDp   垂直间距
     * @param edgePaddingDp   两端的padding大小
     */
    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public GridAverageGapItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}

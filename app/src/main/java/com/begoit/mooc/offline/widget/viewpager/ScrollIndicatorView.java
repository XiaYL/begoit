package com.begoit.mooc.offline.widget.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;


/**
 * Created by gxj
 *  主要用于多个tab可以进行滑动
 */
public class ScrollIndicatorView extends HorizontalScrollView implements Indicator {
    private FixedIndicatorView fixedIndicatorView;

    public ScrollIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        fixedIndicatorView = new FixedIndicatorView(context);
        fixedIndicatorView.setSplitMethod(FixedIndicatorView.SPLITMETHOD_EQUALS);
        addView(fixedIndicatorView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        setHorizontalScrollBarEnabled(false);
    }

    @Override
    public void setAdapter(IndicatorAdapter adapter) {
        if (getAdapter() != null) {
            getAdapter().unRegistDataSetObserver(dataSetObserver);
        }
        fixedIndicatorView.setAdapter(adapter);
        adapter.registDataSetObserver(dataSetObserver);
    }

    @Override
    public void setOnItemSelectListener(OnItemSelectedListener onItemSelectedListener) {
        fixedIndicatorView.setOnItemSelectListener(onItemSelectedListener);
    }

    @Override
    public IndicatorAdapter getAdapter() {
        return fixedIndicatorView.getAdapter();
    }

    private DataSetObserver dataSetObserver = new DataSetObserver() {

        @Override
        public void onChange() {
            setCurrentItem(0, false);
        }
    };

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

    private Runnable mTabSelector;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        animateToTab(fixedIndicatorView.getCurrentItem());
    }

    private void animateToTab(final int position) {
        if (position < 0 && position > fixedIndicatorView.getChildCount() - 1) {
            return;
        }
        if (position == -1) {
            return;
        }
        final View tabView = fixedIndicatorView.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                smoothScrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    @Override
    public void setCurrentItem(int item, boolean anim) {
        fixedIndicatorView.setCurrentItem(item, anim);
        if (anim) {
            animateToTab(item);
        } else {
            final View tabView = fixedIndicatorView.getChildAt(item);
            final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
            scrollTo(scrollPos, 0);
        }
    }

    @Override
    public int getCurrentItem() {
        return fixedIndicatorView.getCurrentItem();
    }

    @Override
    public OnItemSelectedListener getOnItemSelectListener() {
        return fixedIndicatorView.getOnItemSelectListener();
    }

    @Override
    public void setOnTransitionListener(OnTransitionListener onPageScrollListener) {
        fixedIndicatorView.setOnTransitionListener(onPageScrollListener);
    }

    @Override
    public OnTransitionListener getOnTransitionListener() {
        return fixedIndicatorView.getOnTransitionListener();
    }

    @Override
    public void setScrollBar(ScrollBar scrollBar) {
        fixedIndicatorView.setScrollBar(scrollBar);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        fixedIndicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public int getPreSelectItem() {
        return fixedIndicatorView.getPreSelectItem();
    }

    @Override
    public View getItemView(int item) {
        return fixedIndicatorView.getItemView(item);
    }
}
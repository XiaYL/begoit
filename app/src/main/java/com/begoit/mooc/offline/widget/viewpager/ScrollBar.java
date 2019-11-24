package com.begoit.mooc.offline.widget.viewpager;

import android.view.View;

/**
 * @author gxj
 * Indicator的滑动块
 */
public interface ScrollBar {
	public static enum Gravity {
		/** 顶部占位 */
		TOP,
		/** 顶部覆盖在Indicator?*/
		TOP_FLOAT,
		/** 底部占位 */
		BOTTOM,
		/** 底部覆盖在Indicator?*/
		BOTTOM_FLOAT,
		/** 中间覆盖在Indicator?*/
		CENTENT,
		/** 中间，被Indicator覆盖 */
		CENTENT_BACKGROUND
	}

	/**
	 * SlideView显示的高?
	 *
	 * @return
	 */
	public int getHeight(int tabHeight);

	/**
	 * SlideView 显示的宽?
	 *
	 * @param tabWidth
	 * @return
	 */
	public int getWidth(int tabWidth);

	/**
	 * 滑动显示的view
	 *
	 * @return
	 */
	public View getSlideView();

	/**
	 * 位置
	 *
	 * @return
	 */
	public Gravity getGravity();

	/**
	 * 当page滑动的时候调?
	 * @param position
	 * @param positionOffset
	 */
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
}

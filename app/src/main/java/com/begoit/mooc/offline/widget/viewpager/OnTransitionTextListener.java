package com.begoit.mooc.offline.widget.viewpager;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;


/**
 * Created by gxj
 *
 */
public class OnTransitionTextListener implements Indicator.OnTransitionListener {
	private float selectSize = -1;
	private float unSelectSize = -1;
	private int selectColor = -1;
	private int unSelectColor = -1;
	private ColorGradient gradient;
	private float dFontFize = -1;

	private boolean isPxSize = false;

	public OnTransitionTextListener() {
		super();
	}

	public OnTransitionTextListener(float selectSize, float unSelectSize, int selectColor, int unSelectColor) {
		super();
		setColor(selectColor, unSelectColor);
		setSize(selectSize, unSelectSize);
	}

	public final OnTransitionTextListener setSize(float selectSize, float unSelectSize) {
		isPxSize = false;
		this.selectSize = selectSize;
		this.unSelectSize = unSelectSize;
		this.dFontFize = selectSize - unSelectSize;
		return this;
	}

	public final OnTransitionTextListener setValueFromRes(Context context, int selectColorId, int unSelectColorId, int selectSizeId,
                                                          int unSelectSizeId) {
		setColorId(context, selectColorId, unSelectColorId);
		setSizeId(context, selectSizeId, unSelectSizeId);
		return this;
	}

	public final OnTransitionTextListener setColorId(Context context, int selectColorId, int unSelectColorId) {
		Resources res = context.getResources();
		setColor(res.getColor(selectColorId), res.getColor(unSelectColorId));
		return this;
	}

	public final OnTransitionTextListener setSizeId(Context context, int selectSizeId, int unSelectSizeId) {
		Resources res = context.getResources();
		setSize(res.getDimensionPixelSize(selectSizeId), res.getDimensionPixelSize(unSelectSizeId));
		isPxSize = true;
		return this;
	}

	public final OnTransitionTextListener setColor(int selectColor, int unSelectColor) {
		this.selectColor = selectColor;
		this.unSelectColor = unSelectColor;
		gradient = new ColorGradient(unSelectColor, selectColor, 100);
		return this;
	}

	/**
	 * 如果tabItemView 不是目标的TextView，那么你可以重写该方法返回实际要变化的TextView
	 *
	 * @param tabItemView
	 *            Indicator的每个TAB的view
	 * @param position
	 *            view在Indicator的位置索引
	 * @return
	 */
	public TextView getTextView(View tabItemView, int position) {
		return (TextView) tabItemView;
	}

	@Override
	public void onTransition(View view, int position, float selectPercent) {
		TextView selectTextView = getTextView(view, position);
		if (null != selectTextView)
		{
			if (selectColor != -1 && unSelectColor != -1) {
				/*****************************设置颜色**************************************************/
				//                                                                                    //
				//                                                                                    //
				//                                                                                    //
				//                                                                                    //
				//                                                                                    //
				//                                                                                    //
				//                                                                                    //
				//                                                                                    //
				/*****************************设置颜色**************************************************/
//				selectTextView.setTextColor(gradient.getColor((int) (selectPercent * 100)));
			}
			if (unSelectSize > 0 && selectSize > 0) {
				if (isPxSize) {
					selectTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, unSelectSize + dFontFize * selectPercent);
				} else {
					selectTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,unSelectSize + dFontFize * selectPercent);
				}

			}
		}
	}

}
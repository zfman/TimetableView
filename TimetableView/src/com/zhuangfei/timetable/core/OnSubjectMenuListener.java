package com.zhuangfei.timetable.core;

import android.view.View;

/**
 * Menu点击监听器
 * @author Administrator
 *
 */
public interface OnSubjectMenuListener {

	/**
	 * 头部左侧按钮被点击
	 * @param v
	 */
	public void onLeftClick(View v);
	
	/**
	 * 头部右侧按钮被点击
	 * @param v
	 */
	public void onRightClick(View v);
}

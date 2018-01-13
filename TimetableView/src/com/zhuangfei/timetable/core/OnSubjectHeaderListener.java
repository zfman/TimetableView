package com.zhuangfei.timetable.core;

import android.view.View;

/**
 * Menu点击监听器
 * @author Administrator
 *
 */
public interface OnSubjectHeaderListener {

	/**
	 * 头部左侧按钮被点击
	 * @param v
	 */
	public void onShowHeader();
	
	/**
	 * 头部右侧按钮被点击
	 * @param v
	 */
	public void onCloseHeader();
}

package com.zhuangfei.timetable.core;

import java.util.List;

import android.view.View;

/**
 * 课表项点击监听器
 * @author Administrator
 *
 */
public interface OnSubjectItemClickListener {

	/**
	 * 课表项被点击时触发
	 * @param v
	 * @param subjectList
	 */
	public void onItemClick(View v,List<SubjectBean> subjectList);

}

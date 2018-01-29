package com.zhuangfei.timetable.core;

/**
 * 数据源转换监听器
 * @author Administrator
 *
 * @param <T>
 */
public interface OnSubjectTransformListener{
	
	/**
	 * 调用该方法进行数据格式转换
	 * @param obj
	 * @return
	 */
	public SubjectBean onTransform(Object obj);
}

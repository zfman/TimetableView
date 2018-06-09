package com.zhuangfei.timetable.core;

import java.util.List;

import android.widget.TextView;

public interface OnSubjectBindViewListener {

	public void onBindTitleView(TextView titleTextView,int curWeek,String curTerm,List<SubjectBean> subjectBeans);
}

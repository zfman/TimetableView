package com.example.timetablesample;

import java.util.ArrayList;
import java.util.List;

import com.zhuangfei.timetable.core.SubjectBean;

public class DataModel {

	public static List<SubjectBean> getExampleSubjectSource(){
		List<SubjectBean> subjectList=new ArrayList<>();
		List<Integer> weekList=new ArrayList<>();
		weekList.add(1);
		weekList.add(3);
		weekList.add(5);
		SubjectBean temp=new SubjectBean("课程1", "1001", "教师1", weekList,1, 2,1,1);
		SubjectBean temp2=new SubjectBean("课程2", "1002", "教师2", weekList,1, 2,2,2);
		
		SubjectBean temp4=new SubjectBean("课程4", "1004", "教师4", weekList,1, 2,3,4);
		
		SubjectBean temp5=new SubjectBean("课程5", "1005", "教师5", weekList,1, 2,4,5);
		SubjectBean temp6=new SubjectBean("课程6", "1006", "教师6", weekList,1, 2,5,6);
		
		SubjectBean temp7=new SubjectBean("课程3", "1003", "教师3", weekList,1, 2,6,3);
		
		subjectList.add(temp);
		subjectList.add(temp2);
		subjectList.add(temp4);
		subjectList.add(temp5);
		subjectList.add(temp6);
		subjectList.add(temp7);
		
		return subjectList;
	}
}

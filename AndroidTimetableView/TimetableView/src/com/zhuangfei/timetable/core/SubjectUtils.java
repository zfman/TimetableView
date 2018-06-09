package com.zhuangfei.timetable.core;

import android.widget.LinearLayout;

import com.zhuangfei.android_timetableview.sample.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.security.auth.Subject;

/**
 * Subject工具包
 * @author Administrator
 *
 */
public class SubjectUtils {

	public static List<SubjectBean>[] getSubjectInitial(List<SubjectBean> dataSource){
		List<SubjectBean>[] data = new ArrayList[7];
		if(dataSource==null) return data;
		for (int i = 0; i < data.length; i++) {
			data[i] = new ArrayList<>();
		}
		for (int i = 0; i < dataSource.size(); i++) {
			SubjectBean bean = dataSource.get(i);
			if (bean.getDay() != -1)
				data[bean.getDay() - 1].add(bean);
		}
		SubjectUtils.sortList(data);
		return data;
	}

	public static  List<SubjectBean> getTodaySubjects(List<SubjectBean> subjectBeans,int curWeek,int day){
		List<SubjectBean> subjectBeanList=getTodayAllSubjects(subjectBeans, day);
		List<SubjectBean> result=new ArrayList<>();
		for(SubjectBean bean:subjectBeanList){
			if(SubjectUtils.isThisWeek(bean,curWeek)){
				result.add(bean);
			}
		}
		return result;
	}

	public static  List<SubjectBean> getTodayAllSubjects(List<SubjectBean> subjectBeans,int day){
		List<SubjectBean> subjectBeanList=getSubjectInitial(subjectBeans)[day];
		return subjectBeanList;
	}

	/**
	 * 在data中查找与subject的start相同的课程集合
	 * @param subject
	 * @param data
	 * @return
	 */
	public static List<SubjectBean> findSubjects(SubjectBean subject,List<SubjectBean> data){
		List<SubjectBean> result=new ArrayList<>();
		for(int i=0;i<data.size();i++){
			SubjectBean bean=data.get(i);
			if(bean.getStart()>=subject.getStart()&&bean.getStart()<(subject.getStart()+subject.getStep())) result.add(data.get(i));
			
		}
		return result;
	}
	
	public static List<SubjectBean> findThisWeekSubjects(SubjectBean subject,List<SubjectBean> data,int curWeek){
		List<SubjectBean> result=new ArrayList<>();
		for(int i=0;i<data.size();i++){
			SubjectBean bean=data.get(i);
			if(isThisWeek(bean, curWeek)&&bean.getStart()>=subject.getStart()&&bean.getStart()<(subject.getStart()+subject.getStep())) result.add(data.get(i));
			
		}
		return result;
	}
	
	/**
	 * 返回index处的课程，注意他的真实位置可能并不是index
	 * @param curWeek
	 * @return
	 */
	public static SubjectBean findRealSubject(int start,int curWeek,List<SubjectBean> subjectList){
		List<SubjectBean> list=new ArrayList<>();
		for(int i=0;i<subjectList.size();i++){
			SubjectBean subject=subjectList.get(i);
			if(subject.getStart()==start){
				list.add(subject);
			}
		}
		
		for(int i=0;i<list.size();i++){
			SubjectBean temp=list.get(i);
			if(isThisWeek(temp,curWeek)){
				return temp;
			}
		}
		if(list.size()>0) return list.get(0);
		return null;
	}
	
	/**
	 * 按照上课节次排序
	 * @param data
	 */
	public static void sortList(List<SubjectBean>[] data) {
		for (int i = 0; i < data.length; i++)
			for (int m = 0; m < data[i].size(); m++)
				for (int k = m + 1; k < data[i].size(); k++)
					if (data[i].get(m).getStart() > data[i].get(k).getStart()) {
						SubjectBean tmp=data[i].get(m);
						data[i].set(m, data[i].get(k));
						data[i].set(k, tmp);
					}
	}
	
	/**
	 * 判断该课是否为本周的
	 * @param cur_week
	 * @return
	 */
	public static boolean isThisWeek(SubjectBean subject,int cur_week) {
		List<Integer> weekList=subject.getWeekList();
		if(weekList.indexOf(cur_week)!=-1) return true;
		return false;
	}
	
	/**
	 * 在data中计算以start开始的课程的个数，1<=start<=10
	 * @param data
	 * @param curWeek
	 * @return 数组
	 */
	public static int[] getCount(List<SubjectBean> data,int curWeek){
		int[] count=new int[12];
		for(int i=0;i<data.size();i++){
			List<SubjectBean> result = findSubjects(data.get(i), data);
			int num=0;
			for(SubjectBean bean:result){
				if(isThisWeek(bean,curWeek)){
					num++;
				}
			}
			if(data.get(i).getStart()>=1&&data.get(i).getStart()<=12){
				count[data.get(i).getStart()-1]=num;
			}
		}

//		int[] count=new int[12];
//		boolean[] isHandle=new boolean[data.size()];
//		for(int i=0;i<data.size();i++){
//			isHandle[i]=false;
//		}
//		for(int i=0;i<count.length;i++) count[i]=0;
//		for(int i=0;i<data.size();i++){
//			List<SubjectBean> result = findThisWeekSubjects(data.get(i), data, curWeek);
//			if(!isHandle[i]){
//				count[data.get(i).getStart()-1]=result.size();
//				isHandle[i]=true;
//			}
//		}
//		
//		for(int i=0;i<data.size();i++){
//			if(isThisWeek(data.get(i), curWeek)){
//				count[data.get(i).getStart()-1]++;
//			}
//		}
		return count;
	}
	
	public static List<String> getWeekDate(){
		List<String> result=new ArrayList<>();
		
        Calendar calendar1 = Calendar.getInstance();  
        calendar1.setFirstDayOfWeek(Calendar.MONDAY);  
          
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayOfWeek) {  
            calendar1.add(Calendar.DAY_OF_MONTH, -1);  
        }  
  
        int day = calendar1.get(Calendar.DAY_OF_WEEK);  
        calendar1.add(Calendar.DATE, 0);  
        calendar1.add(Calendar.DATE, calendar1.getFirstDayOfWeek() - day);  
  
        Date beginDate = calendar1.getTime();  
        calendar1.add(Calendar.DATE, 6);  
        Date endDate = calendar1.getTime();  
		return getBetweenDates(beginDate, endDate);
	}
	
	private static List<String> getBetweenDates(Date start, Date end) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
	    List<String> result = new ArrayList<String>();
	    result.add(sdf2.format(new Date()));
	    result.add(sdf.format(start));
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	 
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
	    while (tempStart.before(tempEnd)) {
	        result.add(sdf.format(tempStart.getTime()));
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	       
	    }
	    result.add(sdf.format(end));
	    return result;
	}
	
	/**
	 * 计算当前周
	 * @param startTime
	 * @return
	 */
	public static int timeTransfrom(String startTime){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			
			long start=sdf.parse(startTime).getTime();
			long end=new Date().getTime();
			long seconds=(end-start)/1000;
			long day=seconds/(24*3600);
			int week=(int) (Math.floor(day/7)+1);
			return week;
		} catch (ParseException e) {
			return -1;
		}
	}
}

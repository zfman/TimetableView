package com.zhuangfei.timetable.model;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程表的工具包，主要提供几个便捷的方法
 *
 */
public class ScheduleSupport {

	/**
	 * 模拟分配颜色，将源数据的colorRandom属性赋值，
	 * 然后根据该属性值在颜色池中查找颜色即可
	 * @param schedules 源数据
	 * @return colorRandom属性已有值
	 */
	public static List<Schedule> getColorReflect(List<Schedule> schedules){
		if(schedules==null||schedules.size()==0) return null;

		//保存课程名、颜色的对应关系
		Map<String, Integer> colorMap = new HashMap<>();
		int colorCount = 1;

		//开始转换
		for (int i = 0; i < schedules.size(); i++) {
			Schedule mySubject = schedules.get(i);
			//计算课程颜色
			int color;
			if (colorMap.containsKey(mySubject.getName())) {
				color = colorMap.get(mySubject.getName());
			} else {
				colorMap.put(mySubject.getName(), colorCount);
				color = colorCount;
				colorCount++;
			}
			mySubject.setColorRandom(color);
		}

		return schedules;
	}

	public static int getPx(Context context,int dp){
		return context.getResources().getDimensionPixelSize(dp);
	}

	/**
	 * 转换，将自定义类型转换为List<Schedule>
	 * @param dataSource 源数据集合
	 * @return
	 */
	public static List<Schedule> transform(List<? extends ScheduleEnable> dataSource){
		List<Schedule> data=new ArrayList<>();
		for(int i=0;i<dataSource.size();i++){
			if(dataSource.get(i)!=null) data.add(dataSource.get(i).getSchedule());
		}
		return data;
	}

	/**
	 * 将源数据拆分为数组的七个元素，每个元素为一个集合，
	 * 依次为周一-周日的课程集合
	 * @param dataSource 源数据
	 * @return
	 */
	public static List<Schedule>[] getSubjectInitial(List<Schedule> dataSource){
		List<Schedule>[] data = new ArrayList[7];
		if(dataSource==null) return data;
		for (int i = 0; i < data.length; i++) {
			data[i] = new ArrayList<>();
		}
		for (int i = 0; i < dataSource.size(); i++) {
			Schedule bean = dataSource.get(i);
			if (bean.getDay() != -1)
				data[bean.getDay() - 1].add(bean);
		}
		sortList(data);
		return data;
	}

	/**
	 * 获取某天有课的课程
	 * @param scheduleList 数据集合
	 * @param curWeek 当前周，以1开始
	 * @param day 星期几，0：周一，1：周二，依次类推..周日：6
	 * @return
	 */
	public static  List<Schedule> getTodaySubjects(List<Schedule> scheduleList,int curWeek,int day){
		List<Schedule> subjectBeanList=getTodayAllSubjects(scheduleList, day);
		List<Schedule> result=new ArrayList<>();
		for(Schedule bean:subjectBeanList){
			if(isThisWeek(bean,curWeek)){
				result.add(bean);
			}
		}
		return result;
	}

	/**
	 * 获取某天的所有课程
	 * @param scheduleList 数据集合
	 * @param day 星期几，0：周一，1：周二，依次类推..周日：6
	 * @return
	 */
	public static  List<Schedule> getTodayAllSubjects(List<Schedule> scheduleList,int day){
		List<Schedule> subjectBeanList=getSubjectInitial(scheduleList)[day];
		return subjectBeanList;
	}

	/**
	 * 在data中查找与subject的start相同的课程集合
	 * @param subject
	 * @param data
	 * @return
	 */
	public static List<Schedule> findSubjects(Schedule subject,List<Schedule> data){
		List<Schedule> result=new ArrayList<>();
		for(int i=0;i<data.size();i++){
			Schedule bean=data.get(i);
			if(bean.getStart()>=subject.getStart()&&bean.getStart()<(subject.getStart()+subject.getStep())) result.add(data.get(i));

		}
		return result;
	}

	/**
	 * 返回index处的课程，注意他的真实位置可能并不是index
	 * @param curWeek
	 * @return
	 */
	public static Schedule findRealSubject(int start,int curWeek,List<Schedule> scheduleList){
		List<Schedule> list=new ArrayList<>();
		for(int i=0;i<scheduleList.size();i++){
			Schedule subject=scheduleList.get(i);
			if(subject.getStart()==start){
				list.add(subject);
			}
		}

		for(int i=0;i<list.size();i++){
			Schedule temp=list.get(i);
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
	public static void sortList(List<Schedule>[] data) {
		for (int i = 0; i < data.length; i++)
			for (int m = 0; m < data[i].size(); m++)
				for (int k = m + 1; k < data[i].size(); k++)
					if (data[i].get(m).getStart() > data[i].get(k).getStart()) {
						Schedule tmp=data[i].get(m);
						data[i].set(m, data[i].get(k));
						data[i].set(k, tmp);
					}
	}

	/**
	 * 判断该课是否为本周的
	 * @param cur_week
	 * @return
	 */
	public static boolean isThisWeek(Schedule subject,int cur_week) {
		List<Integer> weekList=subject.getWeekList();
		if(weekList.indexOf(cur_week)!=-1) return true;
		return false;
	}

	/**
	 * 获取本周的周一-周日的所有日期
	 * @return 8个元素的集合，第一个为月份，之后7个依次为周一-周日
	 */
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
	 * 根据开学时间计算当前周
	 * @param startTime 满足"yyyy-MM-dd HH:mm:ss"模式的字符串
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
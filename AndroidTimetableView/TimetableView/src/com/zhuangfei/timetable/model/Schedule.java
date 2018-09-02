package com.zhuangfei.timetable.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程实体类<br/>
 * 1.增加了extras,可以保存一些自己需要的东西<br/>
 * @author Administrator 刘壮飞
 *
 */
public class Schedule implements Serializable,Comparable<Schedule>{

	/**
	 * 课程名
	 */
	private String name="";
	
	/**
	 * 教室
	 */
	private String room="";
	
	/**
	 * 教师
	 */
	private String teacher="";
	
	/**
	 * 第几周至第几周上
	 */
	private List<Integer> weekList=new ArrayList<>();
	
	/**
	 * 开始上课的节次
	 */
	private int start=0;
	
	/**
	 * 上课节数
	 */
	private int step=0;
	
	/**
	 * 周几上
	 */
	private int day=0;
	
	/**
	 *  一个随机数，用于对应课程的颜色
	 */
	private int colorRandom = 0;

	/**
	 * 额外信息
	 */
	private Map<String,Object> extras=new HashMap<>();
	
	public Schedule(String name, String room, String teacher,
					List<Integer> weekList, int start, int step, int day,
					int colorRandom) {
		super();
		this.name = name;
		this.room = room;
		this.teacher = teacher;
		this.weekList = weekList;
		this.start = start;
		this.step = step;
		this.day = day;
		this.colorRandom = colorRandom;
	}

	public Map<String,Object> getExtras(){
		return extras;
	}

	public void setExtras(Map<String,Object> map){
		this.extras=map;
	}

	public void putExtras(String key,Object val){
		getExtras().put(key,val);
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public void setWeekList(List<Integer> weekList) {
		this.weekList = weekList;
	}
	
	public List<Integer> getWeekList() {
		return weekList;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getColorRandom() {
		return colorRandom;
	}

	public void setColorRandom(int colorRandom) {
		this.colorRandom = colorRandom;
	}

	public Schedule() {
		super();
	}

	@Override
	public int compareTo(Schedule o) {
		if(getStart()<o.getStart()){
			return -1;
		}else if(getStart()==o.getStart()){
			return 0;
		}else{
			return 1;
		}
	}
}

package com.zhuangfei.timetable.model;

import java.io.Serializable;
import java.util.List;

/**
 * 课程实体
 * @author Administrator 刘壮飞
 *
 */
public class Schedule implements Serializable{

	/**
	 * 课程名
	 */
	private String name;
	
	/**
	 * 教室
	 */
	private String room;
	
	/**
	 * 教师
	 */
	private String teacher;
	
	/**
	 * 第几周至第几周上
	 */
	private List<Integer> weekList;
	
	/**
	 * 开始上课的节次
	 */
	private int start;
	
	/**
	 * 上课节数
	 */
	private int step;
	
	/**
	 * 周几上
	 */
	private int day;
	
	/**
	 *  一个随机数，用于对应课程的颜色
	 */
	private int colorRandom = 0;

	public Schedule(String name, String room, String teacher,
					List<Integer> weekList, int start, int step, int day,
					int colorRandom, String time) {
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

}

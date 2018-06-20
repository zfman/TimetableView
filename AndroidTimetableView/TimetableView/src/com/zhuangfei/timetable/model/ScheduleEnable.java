package com.zhuangfei.timetable.model;

/**
 * 该接口定义数据转换的规范,
 * 用户自定义课程实体类需要实现该接口以及实现其中的转换方法
 */

public interface ScheduleEnable {

    /**
     * 获取Schedule
     * @return
     */
    Schedule getSchedule();

}

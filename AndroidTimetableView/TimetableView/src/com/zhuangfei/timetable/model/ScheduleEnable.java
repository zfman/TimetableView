package com.zhuangfei.timetable.model;

/**
 * 该接口定义数据转换的规范,
 * 用户自定义课程实体类需要实现该接口以及实现其中的转换方法,
 * 在设置数据源时可以使用自定义的课程实体，但是必须实现该接口
 */

public interface ScheduleEnable {

    /**
     * 获取Schedule
     * @return
     */
    Schedule getSchedule();

}

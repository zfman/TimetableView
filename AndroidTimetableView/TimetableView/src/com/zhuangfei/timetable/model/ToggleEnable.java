package com.zhuangfei.timetable.model;

/**
 * 实现它，你可以在对象与对象之间实现切换。
 *
 * 在方法中将obj返回即可
 */

public interface ToggleEnable {
    public <T> T toggle(T obj);
}

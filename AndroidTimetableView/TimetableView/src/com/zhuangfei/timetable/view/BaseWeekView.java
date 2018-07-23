package com.zhuangfei.timetable.view;

import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu ZhuangFei on 2018/7/20.
 */

public abstract class BaseWeekView<T> {

    protected boolean isShow;

    protected List<Schedule> dataSource;

    protected int curWeek=1;

    public abstract T setCurWeek(int curWeek);

    public abstract T setSource(List<? extends ScheduleEnable> list);

    public abstract T setData(List<Schedule> scheduleList);

    public abstract List<Schedule> getDataSource();

    public abstract void showView();

    public abstract T updateView();

    public abstract T isShow(boolean isShow);

    public abstract boolean isShowing();

}

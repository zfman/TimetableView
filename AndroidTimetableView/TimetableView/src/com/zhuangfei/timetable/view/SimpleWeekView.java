package com.zhuangfei.timetable.view;

import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;
import com.zhuangfei.timetable.model.ScheduleSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu ZhuangFei on 2018/7/20.
 */

public class SimpleWeekView extends BaseWeekView<SimpleWeekView> {
    @Override
    public SimpleWeekView setCurWeek(int curWeek) {
        this.curWeek=curWeek;
        return this;
    }

    @Override
    public SimpleWeekView setSource(List<? extends ScheduleEnable> list) {
        setData(ScheduleSupport.transform(list));
        return this;
    }

    @Override
    public SimpleWeekView setData(List<Schedule> scheduleList) {
        dataSource=scheduleList;
        return this;
    }

    @Override
    public List<Schedule> getDataSource() {
        if(dataSource==null) dataSource=new ArrayList<>();
        return dataSource;
    }

    @Override
    public void showView() {

    }

    @Override
    public SimpleWeekView updateView() {
        return null;
    }

    @Override
    public SimpleWeekView isShow(boolean isShow) {
        this.isShow=isShow;
        return this;
    }

    @Override
    public boolean isShowing() {
        return isShow;
    }
}

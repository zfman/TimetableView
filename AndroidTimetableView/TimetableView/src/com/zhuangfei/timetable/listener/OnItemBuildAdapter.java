package com.zhuangfei.timetable.listener;

import android.graphics.drawable.GradientDrawable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhuangfei.timetable.model.Schedule;

/**
 * Item构建监听器的默认实现.
 */

public class OnItemBuildAdapter implements ISchedule.OnItemBuildListener {
    @Override
    public String getItemText(Schedule schedule, boolean isThisWeek) {
        if(isThisWeek){
            return schedule.getName()+"@"+schedule.getRoom();
        }

        return schedule.getName();
    }

    @Override
    public boolean interceptItemBuild(Schedule schedule) {
        return false;
    }

    @Override
    public void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView, Schedule schedule, GradientDrawable gd) {

    }
}

package com.zhuangfei.timetable.listener;

import android.util.Log;
import android.view.View;

import com.zhuangfei.timetable.model.Schedule;

import java.util.List;

/**
 * Item点击的默认实现.
 */

public class OnItemClickAdapter implements ISchedule.OnItemClickListener {
    private static final String TAG = "OnItemClickAdapter";
    @Override
    public void onItemClick(View v, List<Schedule> scheduleList) {
    }
}

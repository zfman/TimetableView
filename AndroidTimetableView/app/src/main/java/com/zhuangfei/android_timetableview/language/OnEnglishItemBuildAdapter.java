package com.zhuangfei.android_timetableview.language;

import android.text.TextUtils;

import com.zhuangfei.timetable.listener.OnItemBuildAdapter;
import com.zhuangfei.timetable.model.Schedule;

/**
 * 英文的课程文本设置
 */
public class OnEnglishItemBuildAdapter extends OnItemBuildAdapter {
    @Override
    public String getItemText(Schedule schedule, boolean isThisWeek) {
        if (schedule == null || TextUtils.isEmpty(schedule.getName())) return "Unknow";
        if (schedule.getRoom() == null) {
            if (!isThisWeek)
                return "[Non]" + schedule.getName();
            return schedule.getName();
        }

        String r = schedule.getName() + "@" + schedule.getRoom();
        if (!isThisWeek) {
            r = "[Non]" + r;
        }
        return r;
    }
}

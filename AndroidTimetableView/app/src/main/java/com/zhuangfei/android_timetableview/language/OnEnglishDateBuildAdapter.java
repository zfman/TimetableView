package com.zhuangfei.android_timetableview.language;

import com.zhuangfei.timetable.listener.OnDateBuildAapter;
import com.zhuangfei.timetable.model.ScheduleSupport;

/**
 * 英语日期栏
 */
public class OnEnglishDateBuildAdapter extends OnDateBuildAapter {
    @Override
    public String[] getStringArray() {
        return new String[]{null, "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    }

    @Override
    public void onUpdateDate(int curWeek, int targetWeek) {
        if (textViews == null || textViews.length < 8) return;

        String[] monthArray={
                "Jan","Feb","Mar","Apr",
                "May","Jun","Jul","Aug",
                "Sept","Oct","Nov","Dec"
        };
        weekDates = ScheduleSupport.getDateStringFromWeek(curWeek, targetWeek);
        int month = Integer.parseInt(weekDates.get(0));
        textViews[0].setText(monthArray[month-1]);
        for (int i = 1; i < 8; i++) {
            if (textViews[i] != null) {
                textViews[i].setText(weekDates.get(i));
            }
        }
    }
}

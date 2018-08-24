package com.zhuangfei.android_timetableview.adapter;

import android.widget.LinearLayout;

import com.zhuangfei.timetable.listener.OnDateBuildAapter;
import com.zhuangfei.timetable.model.ScheduleSupport;

import java.util.List;

/**
 * 自定义日期栏
 * Created by Liu ZhuangFei on 2018/8/24.
 */
public class OnDateDelayAdapter extends OnDateBuildAapter {

    /**
     * 阈值，即超过这个时间戳后开始更新日期
     * 否则将一直显示initDates中的日期
     */
    protected long startTime;

    /**
     * 日期集合,8个元素，当前时间小于等于阈值时使用
     */
    List<String> initDates=null;

    /**
     * 设置日期集合
     * @param dates 元素个数必须大于等于8，第一个为月份数值，第2-8为周一至周日的日期数值（不带中文）
     */
    public void setDateList(List<String> dates){
        if(dates.size()>=8){
            this.initDates=dates;
        }
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public void onInit(LinearLayout layout, float alpha) {
        super.onInit(layout, alpha);

        //增加的
        long curTime=System.currentTimeMillis();
        if(curTime<=startTime){
            weekDates=initDates;
        }
    }

    @Override
    public void onUpdateDate(int curWeek,int targetWeek) {
        if (textViews == null || textViews.length < 8) return;
        long curTime=System.currentTimeMillis();

        //增加的
        if(curTime<=startTime){
            weekDates=initDates;
        }else{
            weekDates = ScheduleSupport.getDateStringFromWeek(curWeek,targetWeek);
        }

        int month = Integer.parseInt(weekDates.get(0));
        textViews[0].setText(month + "\n月");
        for (int i = 1; i < 8; i++) {
            if(textViews[i]!=null){
                textViews[i].setText(weekDates.get(i) + "日");
            }
        }
    }
}

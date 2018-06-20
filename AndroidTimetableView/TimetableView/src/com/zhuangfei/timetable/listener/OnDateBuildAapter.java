package com.zhuangfei.timetable.listener;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.sample.R;
import com.zhuangfei.timetable.model.ScheduleSupport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期栏的构建过程.
 */

public class OnDateBuildAapter implements ISchedule.OnDateBuildListener {

    private static final String TAG = "OnDateBuildAapter";

    //第一个：月份，之后7个表示周一至周日
    TextView[] textViews=new TextView[8];
    LinearLayout[] layouts=new LinearLayout[8];

    @Override
    public View[] getDateViews(LayoutInflater mInflate,float perWidth,int height) {
        View[] views=new View[8];
        View first=mInflate.inflate(R.layout.item_dateview_first,null,false);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams((int) perWidth,height);
        List<String> weekDays = ScheduleSupport.getWeekDate();

        //月份设置
        textViews[0]=first.findViewById(R.id.id_week_month);
        layouts[0]=null;
        views[0]=first;
        int month=Integer.parseInt(weekDays.get(0));
        first.setLayoutParams(lp);
        textViews[0].setText(month+"\n月");

        //星期设置
        lp=new LinearLayout.LayoutParams((int)(perWidth*1.5),height);
        String[] dateArray=new String[]{"","周一","周二","周三","周四","周五","周六","周日"};
        for(int i=1;i<8;i++){
            View v=mInflate.inflate(R.layout.item_dateview,null,false);
            TextView dayTextView=v.findViewById(R.id.id_week_day);
            textViews[i]=v.findViewById(R.id.id_week_date);
            layouts[i]=v.findViewById(R.id.id_week_layout);
            views[i]=v;
            layouts[i].setLayoutParams(lp);
            dayTextView.setText(dateArray[i]);
            textViews[i].setText(weekDays.get(i)+"日");
        }
        return views;
    }

    @Override
    public void onHighLight() {
        //初始化背景色
        int color=Color.parseColor("#F4F8F8");
        for(int i=1;i<8;i++){
            layouts[i].setBackgroundColor(color);
        }

        //高亮
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek<0) dayOfWeek=7;
        layouts[dayOfWeek].setBackgroundColor(Color.parseColor("#BFF6F4"));
    }

    @Override
    public void onUpdateDate() {
        if(textViews==null||textViews.length<8) return;

        List<String> weekDays = ScheduleSupport.getWeekDate();
        int month=Integer.parseInt(weekDays.get(0));
        textViews[0].setText(month+"\n月");
        for(int i=1;i<8;i++){
            textViews[i].setText(weekDays.get(i)+"日");
        }
    }
}

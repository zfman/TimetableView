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
import com.zhuangfei.timetable.utils.ColorUtils;

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
    protected TextView[] textViews = new TextView[8];
    protected LinearLayout[] layouts = new LinearLayout[8];

    protected int background = Color.parseColor("#F4F8F8");
    protected float alpha = 1;

    protected String[] dateArray;
    protected List<String> weekDates;
    protected LinearLayout layout;

    public OnDateBuildAapter setBackground(int background) {
        this.background = background;
        return this;
    }

    @Override
    public void onInit(LinearLayout layout, float alpha) {
        this.alpha = alpha;
        this.layout = layout;
        //星期设置
        dateArray = getStringArray();
        weekDates = ScheduleSupport.getWeekDate();
        int alphaColor = ColorUtils.alphaColor(background, alpha);
        if (layout != null) layout.setBackgroundColor(alphaColor);
    }

    @Override
    public View[] getDateViews(LayoutInflater mInflate, float monthWidth, float perWidth, int height) {
        View[] views = new View[8];
        views[0] = onBuildMonthLayout(mInflate, (int) monthWidth, height);
        for (int i = 1; i < 8; i++) {
            views[i] = onBuildDayLayout(mInflate, i, (int) perWidth, height);
        }
        return views;
    }

    @Override
    public void onHighLight() {
        initDateBackground();

        //获取周几，1->7
        Calendar now = Calendar.getInstance();
        //一周第一天是否为星期天
        boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
        int weekDay = now.get(Calendar.DAY_OF_WEEK);
        //若一周第一天为星期天，则-1
        if (isFirstSunday) {
            weekDay = weekDay - 1;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }

        activeDateBackground(weekDay);
    }


    @Override
    public void onUpdateDate(int curWeek, int targetWeek) {
        if (textViews == null || textViews.length < 8) return;

        weekDates = ScheduleSupport.getDateStringFromWeek(curWeek, targetWeek);
        int month = Integer.parseInt(weekDates.get(0));
        textViews[0].setText(month + "\n月");
        for (int i = 1; i < 8; i++) {
            if (textViews[i] != null) {
                textViews[i].setText(weekDates.get(i) + "日");
            }
        }
    }

    /**
     * 构建月份，也就是日期栏的第一格.<br/>
     * 宽度、高度均为px
     *
     * @param mInflate
     * @param width    宽度
     * @param height   默认高度
     * @return
     */
    protected View onBuildMonthLayout(LayoutInflater mInflate, int width, int height) {
        View first = mInflate.inflate(R.layout.item_dateview_first, null, false);
        //月份设置
        textViews[0] = first.findViewById(R.id.id_week_month);
        layouts[0] = null;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);

        int month = Integer.parseInt(weekDates.get(0));
        first.setLayoutParams(lp);
        textViews[0].setText(month + "\n月");
        return first;
    }

    protected View onBuildDayLayout(LayoutInflater mInflate, int pos, int width, int height) {
        View v = mInflate.inflate(R.layout.item_dateview, null, false);
        TextView dayTextView = v.findViewById(R.id.id_week_day);
        dayTextView.setText(dateArray[pos]);

        textViews[pos] = v.findViewById(R.id.id_week_date);
        layouts[pos] = v.findViewById(R.id.id_week_layout);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        layouts[pos].setLayoutParams(lp);
        textViews[pos].setText(weekDates.get(pos) + "日");

        return v;
    }

    /**
     * 返回一个长度为8的数组，第0个位置为null
     *
     * @return
     */
    public String[] getStringArray() {
        return new String[]{null, "周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    }

    protected void initDateBackground() {
        for (int i = 1; i < 8; i++) {
            if (layouts[i] != null) layouts[i].setBackgroundColor(Color.TRANSPARENT);
        }
    }

    protected void activeDateBackground(int weekDay) {
        if (layouts.length > weekDay && layouts[weekDay] != null) {
            layouts[weekDay].setBackgroundColor(
                    ColorUtils.alphaColor(Color.parseColor("#BFF6F4"), alpha));
        }
    }
}

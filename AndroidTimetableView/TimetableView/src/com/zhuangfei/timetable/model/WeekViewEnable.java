package com.zhuangfei.timetable.model;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.sample.R;
import com.zhuangfei.timetable.view.PerWeekView;
import com.zhuangfei.timetable.view.WeekView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu ZhuangFei on 2018/7/28.
 */

public interface WeekViewEnable<T> {
    /**
     * 设置当前周
     * @param curWeek
     * @return
     */
    T curWeek(int curWeek);

    /**
     * 设置项数
     * @param count
     * @return
     */
    T itemCount(int count);

    /**
     * 设置数据源
     * @param list
     * @return
     */
    T source(List<? extends ScheduleEnable> list);

    /**
     * 设置数据源
     * @param scheduleList
     * @return
     */
    public T data(List<Schedule> scheduleList);

    /**
     * 获取数据源
     * @return
     */
    List<Schedule> dataSource();

    /**
     * 初次构建时调用，显示周次选择布局
     */
    void showView();

    /**
     * 当前周被改变后可以调用该方式修正一下底部的文本
     * @return
     */
    T updateView();

    /**
     * 设置控件的可见性
     * @param isShow true:显示，false:隐藏
     */
    WeekView isShow(boolean isShow);

    /**
     * 判断该控件是否显示
     * @return
     */
    boolean isShowing();
}

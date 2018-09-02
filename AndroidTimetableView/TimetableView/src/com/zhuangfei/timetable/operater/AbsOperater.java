package com.zhuangfei.timetable.operater;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.zhuangfei.timetable.TimetableView;

/**
 * 抽象的业务逻辑
 * Created by Liu ZhuangFei on 2018/9/2.
 */
public abstract class AbsOperater {
    public abstract void init(Context context, AttributeSet attrs, TimetableView view);

    public abstract void showView();

    public abstract void updateDateView();

    public abstract void updateSlideView();

    public abstract void changeWeek(int week, boolean isCurWeek);

    public abstract LinearLayout getFlagLayout();

    public abstract LinearLayout getDateLayout();
}

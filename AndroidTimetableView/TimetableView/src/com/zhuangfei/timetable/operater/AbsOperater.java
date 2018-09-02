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
    public void init(Context context, AttributeSet attrs, TimetableView view){};

    public void showView(){};

    public void updateDateView(){};

    public void updateSlideView(){};

    public void changeWeek(int week, boolean isCurWeek){};

    public LinearLayout getFlagLayout(){return null;};

    public LinearLayout getDateLayout(){return null;};

    public void setWeekendsVisiable(boolean isShow){};
}

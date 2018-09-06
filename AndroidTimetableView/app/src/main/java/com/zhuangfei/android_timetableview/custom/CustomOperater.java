package com.zhuangfei.android_timetableview.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.sample.R;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleSupport;
import com.zhuangfei.timetable.operater.AbsOperater;
import com.zhuangfei.timetable.operater.SimpleOperater;
import com.zhuangfei.timetable.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的课表业务操作者，以实现列宽设置。
 *
 * @version 2.0.6
 * Created by Liu ZhuangFei on 2018/9/1.
 */
public class CustomOperater extends SimpleOperater{
    private static final String TAG = "CustomOperater";
    private float[] weights;//宽度权重

    public CustomOperater(){
        weights=new float[7];
        for(int i=0;i<weights.length;i++){
            weights[i]=1;
        }
    }

    @Override
    public void applyWidthConfig() {
        super.applyWidthConfig();
        if(weights==null) return;
        for(int i=0;i<panels.length;i++){
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,weights[i]);
            panels[i].setLayoutParams(lp);
        }
    }

    public void setWidthWeights(float[] weights){
        if(weights==null||weights.length<7) return;
        this.weights=weights;
    }

    public float[] getWeights() {
        return weights;
    }
}

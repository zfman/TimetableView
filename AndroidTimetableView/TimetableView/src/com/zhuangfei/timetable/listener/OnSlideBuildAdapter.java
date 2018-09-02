package com.zhuangfei.timetable.listener;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.sample.R;
import com.zhuangfei.timetable.utils.ColorUtils;

/**
 * 控件实现的一个可以显示时间的侧边栏适配器
 * Created by Liu ZhuangFei on 2018/6/18.
 */

public class OnSlideBuildAdapter implements ISchedule.OnSlideBuildListener {

    //时刻，每个元素保存每节课的开始时间
    protected String[] times;

    //节次文本的颜色、字号
    protected int textColor= Color.BLACK;
    protected float textSize=14;

    //时刻文本的颜色、字号
    protected float timeTextSize=12;
    protected int timeTextColor=Color.GRAY;

    //侧边栏背景色
    protected int background=Color.WHITE;
    protected float alpha=1;

    /**
     * 设置时刻数组
     * @param times
     * @return
     */
    public OnSlideBuildAdapter setTimes(String[] times) {
        this.times = times;
        return this;
    }

    public OnSlideBuildAdapter setBackground(int backgroundColor) {
        this.background=backgroundColor;
        return this;
    }

    /**
     * 获取时刻数组
     * @return
     */
    public String[] getTimes() {
        return times;
    }


    /**
     * 设置节次文本颜色
     * @param textColor 指定颜色
     * @return
     */
    public OnSlideBuildAdapter setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    /**
     * 设置节次文本的大小
     * @param textSize 指定字号
     * @return
     */
    public OnSlideBuildAdapter setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    /**
     * 设置节次时间的文本颜色
     * @param timeTextColor 颜色
     * @return
     */
    public OnSlideBuildAdapter setTimeTextColor(int timeTextColor) {
        this.timeTextColor = timeTextColor;
        return this;
    }

    /**
     * 设置节次时间的文本大小
     * @param timeTextSize 字号
     * @return
     */
    public OnSlideBuildAdapter setTimeTextSize(float timeTextSize) {
        this.timeTextSize = timeTextSize;
        return this;
    }

    @Override
    public View getView(int pos, LayoutInflater inflater, int itemHeight, int marTop) {
        View view=inflater.inflate(R.layout.item_slide_time,null,false);
        TextView numberTextView=view.findViewById(R.id.item_slide_number);
        TextView timeTextView=view.findViewById(R.id.item_slide_time);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                itemHeight);
        lp.setMargins(0,marTop,0,0);
        view.setLayoutParams(lp);

        numberTextView.setText((pos+1)+"");
        numberTextView.setTextSize(textSize);
        numberTextView.setTextColor(textColor);

        if(times==null) timeTextView.setText("");
        if(times!=null&&pos>=0&&pos<times.length){
            timeTextView.setText(times[pos]);
            timeTextView.setTextColor(timeTextColor);
            timeTextView.setTextSize(timeTextSize);
        }
        return view;
    }

    @Override
    public void onInit(LinearLayout layout, float alpha) {
        this.alpha=alpha;
        int alphaColor=ColorUtils.alphaColor(background,alpha);
        if(layout!=null) layout.setBackgroundColor(alphaColor);
    }
}

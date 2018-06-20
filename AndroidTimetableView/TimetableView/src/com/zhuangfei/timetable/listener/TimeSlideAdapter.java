package com.zhuangfei.timetable.listener;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.sample.R;

/**
 * 控件实现的一个可以显示时间的侧边栏适配器
 * Created by Liu ZhuangFei on 2018/6/18.
 */

public class TimeSlideAdapter implements ISchedule.OnSlideBuildListener {

    //时刻，每个元素保存每节课的开始时间
    private String[] times;

    //节次文本的颜色、字号
    private int textColor= Color.BLACK;
    private float textSize=14;

    //时刻文本的颜色、字号
    private float timeTextSize=12;
    private int timeTextColor=Color.GRAY;

    //每项的背景、侧边栏背景色
    private int itemBackground=Color.TRANSPARENT;
    private int background=Color.WHITE;

    /**
     * 设置时刻数组
     * @param times
     * @return
     */
    public TimeSlideAdapter setTimes(String[] times) {
        this.times = times;
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
     * 设置侧边栏的背景
     * @param bgcolor 颜色
     * @return
     */
    public TimeSlideAdapter setBackground(int bgcolor){
        background=bgcolor;
        return this;
    }

    /**
     * 设置侧边栏的背景
     * @param layout 侧边栏View
     */
    @Override
    public void setBackground(LinearLayout layout) {
        layout.setBackgroundColor(background);
    }

    /**
     * 获取侧边项的个数
     * @return
     */
    @Override
    public int getSlideItemSize() {
        return 12;
    }

    /**
     * 设置节次文本颜色
     * @param textColor 指定颜色
     * @return
     */
    public TimeSlideAdapter setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    /**
     * 设置节次文本的大小
     * @param textSize 指定字号
     * @return
     */
    public TimeSlideAdapter setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    /**
     * 设置节次时间的文本颜色
     * @param timeTextColor 颜色
     * @return
     */
    public TimeSlideAdapter setTimeTextColor(int timeTextColor) {
        this.timeTextColor = timeTextColor;
        return this;
    }

    /**
     * 设置节次时间的文本大小
     * @param timeTextSize 字号
     * @return
     */
    public TimeSlideAdapter setTimeTextSize(float timeTextSize) {
        this.timeTextSize = timeTextSize;
        return this;
    }

    /**
     * 设置侧边项的背景
     * @param background 颜色
     * @return
     */
    public TimeSlideAdapter setItemBackground(int background) {
        this.itemBackground = background;
        return this;
    }

    @Override
    public View onBuildSlideItem(int pos, LayoutInflater inflater, int itemHeight, int marTop) {
        View view=inflater.inflate(R.layout.item_slide_time,null,false);
        TextView numberTextView=view.findViewById(R.id.item_slide_number);
        TextView timeTextView=view.findViewById(R.id.item_slide_time);
        LinearLayout layout=view.findViewById(R.id.id_slide_layout);

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                itemHeight);
        lp.setMargins(0,marTop,0,0);
        view.setLayoutParams(lp);

        layout.setBackgroundColor(itemBackground);
        numberTextView.setText((pos+1)+"");
        numberTextView.setTextSize(textSize);
        numberTextView.setTextColor(textColor);

        if(times!=null&&pos>=0&&pos<times.length){
            timeTextView.setText(times[pos]);
            timeTextView.setTextColor(timeTextColor);
            timeTextView.setTextSize(timeTextSize);
        }
        return view;
    }
}

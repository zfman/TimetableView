package com.zhuangfei.timetable.listener;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuangfei.timetable.model.Schedule;

import java.util.List;

/**
 * 与Schedule有关的接口.
 */

public interface ISchedule {

    /**
     * 课程项点击监听器
     */
    interface OnItemClickListener{
        /**
         * 当课程项被点击时回调
         * @param v
         * @param scheduleList 该位置的所有课程
         */
        void onItemClick(View v, List<Schedule> scheduleList);
    }

    /**
     * 周次改变监听器
     */
    interface OnWeekChangedListener{
        /**
         * 当周次被改变时回调
         * @param curWeek 改变的周次，不一定是当前周，因为切换周次的时候有两种模式：仅仅切换周次、强制切换，前者不会更改当前周
         */
        void onWeekChanged(int curWeek);
    }

    /**
     * 课程项构建时回调
     */
    interface OnItemBuildListener{

        /**
         * 构建课程项的文本时回调.
         * @param schedule 该位置对应的课程实体
         * @param isThisWeek 该课程是否是本周上
         * @return
         */
        String getItemText(Schedule schedule,boolean isThisWeek);

        /**
         * 该接口用于拦截课程项的构建，但它并不是在任何情况下都有效的,
         * 如果课程项没有通过过滤规则（不会显示），则不会执行该回调,
         * 返回true表示该构建被拦截，不会添加到视图上
         * @param schedule
         * @return 返回true会拦截课程项的构建
         */
        boolean interceptItemBuild(Schedule schedule);

        //当changeWeek()调用结束后回调该接口
        //你可以在这里对属性进行二次设置，从而使其符合需求

        /**
         * 课程项构建完成后回调.
         * @param layout 课程项的帧布局
         * @param textView 课程项的内容区域
         * @param countTextView 课程项的角标
         * @param schedule 课程实体
         * @param gd 内容区域的背景是使用GradientDrawable来设置的，你可以二次设置
         */
        void onItemUpdate(FrameLayout layout,TextView textView,TextView countTextView,
                          Schedule schedule,GradientDrawable gd);
    }

    /**
     * 侧边栏构建监听器
     */
    interface OnSlideBuildListener{
        /**
         * 设置侧边栏的背景
         * @param layout
         */
        void setBackground(LinearLayout layout);

        /**
         * 侧边栏的节次数
         * @return
         */
        int getSlideItemSize();

        /**
         * 构建每项
         * @param pos 位置
         * @param inflater 转换器
         * @param itemHeight 课程项的高度
         * @param marTop 课程项的marTop值
         * @return 构建好的一个侧边项
         */
        View onBuildSlideItem(int pos, LayoutInflater inflater,int itemHeight,int marTop);
    }

    /**
     * 滚动布局构建监听器
     */
    interface  OnScrollViewBuildListener{
        /**
         * 构建滚动布局时回调
         * @param mInflate
         * @return
         */
        View getScrollView(LayoutInflater mInflate);
    }

    /**
     * 日期构造监听器
     */
    interface  OnDateBuildListener{
        //

        /**
         * 获取View数组
         * @param mInflate 转换器
         * @param perWidth 每个单位的宽度，月份占1个单位，其余为1.5个单位
         * @param height 默认的日期栏高度
         * @return
         */
        View[] getDateViews(LayoutInflater mInflate,float perWidth,int height);

        /**
         * 为日期栏设置高亮时回调
         */
        void onHighLight();

        /**
         * 更新日期时回调
         */
        void onUpdateDate();
    }
}

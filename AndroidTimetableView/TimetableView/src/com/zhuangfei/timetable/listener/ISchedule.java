package com.zhuangfei.timetable.listener;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.model.Schedule;

import java.util.List;

/**
 * 与Schedule有关的接口.
 */

public interface ISchedule {

    /**
     * 课程项点击监听器
     */
    interface OnItemClickListener {
        /**
         * 当课程项被点击时回调
         *
         * @param v
         * @param scheduleList 该位置的所有课程
         */
        void onItemClick(View v, List<Schedule> scheduleList);
    }

    /**
     * 课程项长按事件监听器
     */
    interface OnItemLongClickListener {
        /**
         * 当课程项被点击时回调
         *
         * @param v
         * @param day 星期，1：周一，7：周日
         * @param start 节次，从1开始
         */
        void onLongClick(View v, int day,int start);
    }

    /**
     * 空白格子点击监听器
     */
    interface OnSpaceItemClickListener {
        /**
         * 当课程项被点击时回调
         *
         * @param day 表示周几，0：周一，6：周日
         * @param start 表示点击空白格子的节次，1：第一节
         */
        void onSpaceItemClick(int day,int start);

        /**
         * 初始化方法
         * @param flagLayout 一个指示器的布局
         * @param monthWidth 月份列宽度
         * @param itemWidth 课程项宽度，itemWidth：是包含了边距的，设置宽度时所以需要减去边距
         * @param itemHeight 课程项高度
         * @param marTop 外边距
         */
        void onInit(LinearLayout flagLayout,int monthWidth,int itemWidth,
                    int itemHeight,int marTop,int marLeft);
    }

    /**
     * 旗标布局点击监听器
     */
    interface OnFlaglayoutClickListener {
        /**
         * 当旗标布局被点击时回调
         *
         * @param day 表示周几，0：周一，6：周日
         * @param start 表示点击空白格子的节次，1：第一节
         */
        void onFlaglayoutClick(int day,int start);
    }

    /**
     * 周次改变监听器
     */
    interface OnWeekChangedListener {
        /**
         * 当周次被改变时回调
         *
         * @param curWeek 改变的周次，不一定是当前周，因为切换周次的时候有两种模式：
         *                仅仅切换周次、强制切换，前者不会更改当前周
         */
        void onWeekChanged(int curWeek);
    }

    /**
     * 课程项构建时回调
     */
    interface OnItemBuildListener {

        /**
         * 构建课程项的文本时回调.
         *
         * @param schedule   该位置对应的课程实体
         * @param isThisWeek 该课程是否是本周上
         * @return
         */
        String getItemText(Schedule schedule, boolean isThisWeek);

        //当changeWeek()调用结束后回调该接口
        //你可以在这里对属性进行二次设置，从而使其符合需求

        /**
         * 课程项构建完成后回调.
         *
         * @param layout        课程项的帧布局
         * @param textView      课程项的内容区域
         * @param countTextView 课程项的角标
         * @param schedule      课程实体
         * @param gd            内容区域的背景是使用GradientDrawable来设置的，你可以二次设置
         */
        void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView,
                          Schedule schedule, GradientDrawable gd);
    }

    /**
     * 侧边栏构建监听器
     */
    interface OnSlideBuildListener {

        /**
         * 构建每项
         *
         * @param pos        位置
         * @param inflater   转换器
         * @param itemHeight 课程项的高度
         * @param marTop     课程项的marTop值
         * @return 构建好的一个侧边项
         */
        View getView(int pos, LayoutInflater inflater, int itemHeight, int marTop);

        /**
         * 初始化方法
         * @param layout 日期栏容器
         * @param alpha 透明度
         */
        void onInit(LinearLayout layout,float alpha);
    }

    /**
     * 滚动布局构建监听器
     */
    interface OnScrollViewBuildListener {
        /**
         * 构建滚动布局时回调
         *
         * @param mInflate
         * @return
         */
        View getScrollView(LayoutInflater mInflate);
    }

    /**
     * 日期构造监听器
     */
    interface OnDateBuildListener {

        /**
         * 初始化方法
         * @param layout 日期栏容器
         * @param alpha 透明度
         */
        void onInit(LinearLayout layout,float alpha);

        /**
         * 获取View数组
         * 被废弃，自v2.0.3起该方法无效
         *
         * @param mInflate 转换器
         * @param monthWidth 月份宽度px
         * @param perWidth 日期每项宽度px
         * @param height   默认的日期栏高度
         * @return
         */
        View[] getDateViews(LayoutInflater mInflate, float monthWidth,float perWidth, int height);

        /**
         * 为日期栏设置高亮时回调
         */
        void onHighLight();

        /**
         * 更新日期时回调
         */
        void onUpdateDate(int curWeek,int targetWeek);
    }

    interface OnConfigHandleListener{
        void onParseConfig(String key, String value, TimetableView mView);
    }
}

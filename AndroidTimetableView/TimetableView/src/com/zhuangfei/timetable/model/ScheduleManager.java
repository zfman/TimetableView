package com.zhuangfei.timetable.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.sample.R;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.utils.ColorUtils;
import com.zhuangfei.timetable.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 对课程的功能进行维护
 */
public class ScheduleManager {

    private static final String TAG = "ScheduleManager";
    
    private LayoutInflater inflater;
    private ScheduleConfig config;

    public ScheduleManager(ScheduleConfig config, LayoutInflater inflate) {
        this.config=config;
        this.inflater = inflate;
    }

    /**
     * 构建侧边栏
     *
     * @param slidelayout 侧边栏的容器
     */
    public void newSlideView(LinearLayout slidelayout) {
        if (slidelayout == null) return;
        slidelayout.removeAllViews();

        ISchedule.OnSlideBuildListener listener = config.onSlideBuildListener();
        listener.onInit(slidelayout,config.slideAlpha());
        for (int i = 0; i < config.maxSlideItem(); i++) {
            View view = listener.getView(i, inflater, config.itemHeight(), config.marTop());
            slidelayout.addView(view);
        }
    }

    /**
     * 构建课程项
     *
     * @param data    某一天的数据集合
     * @param subject 当前的课程数据
     * @param pre     上一个课程数据
     * @param i       构建的索引
     * @param curWeek 当前周
     * @return View
     */
    public View newItemView(final List<Schedule> data, final Schedule subject, Schedule pre, int i, int curWeek) {
        //宽高
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = config.itemHeight() * subject.getStep() + config.marTop() * (subject.getStep() - 1);

        //边距
        int left = config.marLeft() / 2, right = config.marLeft() / 2;
        int top = (subject.getStart() - (pre.getStart() + pre.getStep()))
                * (config.itemHeight() + config.marTop()) + config.marTop();

        if (i != 0 && top < 0) return null;

        // 设置Params
        View view = inflater.inflate(R.layout.item_timetable, null, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        if (i == 0) {
            top = (subject.getStart() - 1) * (config.itemHeight() + config.marTop()) + config.marTop();
        }
        lp.setMargins(left, top, right, 0);

        view.setBackgroundColor(Color.TRANSPARENT);
        view.setTag(subject);
        FrameLayout layout = view.findViewById(R.id.id_course_item_framelayout);
        TextView textView = view.findViewById(R.id.id_course_item_course);
        layout.setLayoutParams(lp);

        boolean isThisWeek = ScheduleSupport.isThisWeek(subject, curWeek);
        textView.setText(config.onItemBuildListener().getItemText(subject, isThisWeek));

        GradientDrawable gd = new GradientDrawable();
        if (isThisWeek) {
            gd.setColor(config.colorPool().getColorAuto(subject.getColorRandom()));
            gd.setCornerRadius(config.corner(true));
        } else {
            gd.setColor(config.colorPool().getUselessColor());
            gd.setCornerRadius(config.corner(false));
        }
        textView.setBackgroundDrawable(gd);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Schedule> result = ScheduleSupport.findSubjects(subject, data);
                config.onItemClickListener().onItemClick(v, result);
            }
        });

        boolean intercept = config.onItemBuildListener().interceptItemBuild(subject);
        return intercept == false ? view : null;
    }

    /**
     * 将数据data添加到layout的布局上
     *
     * @param layout  容器
     * @param data    某一天的数据集合
     * @param curWeek 当前周
     */
    public void addToLayout(LinearLayout layout, final List<Schedule> data, int curWeek) {
        if (layout == null || data == null || data.size() < 1) return;
        layout.removeAllViews();

        //遍历
        Schedule pre = data.get(0);
        for (int i = 0; i < data.size(); i++) {
            final Schedule subject = data.get(i);
            boolean isIgnore = filterSubject(subject, pre, i, curWeek);
            if (!isIgnore) {
                View view = newItemView(data, subject, pre, i, curWeek);
                if (view != null) {
                    layout.addView(view);
                    pre = subject;
                }
            }
        }
    }

    /**
     * 简单过滤课程
     *
     * @param subject
     * @param pre
     * @param index
     * @param curWeek
     * @return
     */
    private boolean filterSubject(Schedule subject, Schedule pre, int index, int curWeek) {

        if (index != 0 && ScheduleSupport.isThisWeek(subject, curWeek)) {
            if (ScheduleSupport.isThisWeek(pre, curWeek)) {
                if (subject.getStart() == pre.getStart()) return true;
            }
        }
        return false;
    }

    /**
     * 更新课程项的视图
     *
     * @param courses
     * @param view
     * @param preView
     * @param isChange
     * @param j
     * @param curWeek
     * @return
     */
    public boolean updateItemView(final List<Schedule> courses, View view, View preView, boolean isChange, int j, int curWeek) {
        FrameLayout layout = (FrameLayout) view.findViewById(R.id.id_course_item_framelayout);
        Schedule tag = (Schedule) view.getTag();
        Schedule subject = ScheduleSupport.findRealSubject(tag.getStart(), curWeek, courses);

        boolean isThisWeek = ScheduleSupport.isThisWeek(subject, curWeek);
        if (!isThisWeek && !config.isShowNotCurWeek()) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }

        int height = config.itemHeight() * subject.getStep() +
                config.marTop() * (subject.getStep() - 1);
        int leftOrRight = config.marLeft() / 2;
        int top = 0;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height);

        if (isChange || tag.getStart() != subject.getStart() || tag.getStep() != subject.getStep()) {
            if (j > 0) {
                Schedule pre = (Schedule) preView.getTag();
                top = (subject.getStart() - (pre.getStart() + pre.getStep())) * (config.itemHeight()
                        + config.marTop()) + config.marTop();
            } else {
                top = (subject.getStart() - 1) * (config.itemHeight() + config.marTop()) + config.marTop();
            }
            lp.setMargins(leftOrRight, top, leftOrRight, 0);
            layout.setLayoutParams(lp);
            view.setTag(subject);
            isChange = true;
        }

        if (top < 0) view.setVisibility(View.GONE);
        TextView textView = (TextView) view.findViewById(R.id.id_course_item_course);
        TextView countTextView = (TextView) view.findViewById(R.id.id_course_item_count);
        textView.setText(config.onItemBuildListener().getItemText(subject, isThisWeek));

        countTextView.setText("");
        countTextView.setVisibility(View.GONE);

        GradientDrawable gd = new GradientDrawable();
        if (isThisWeek) {
            textView.setTextColor(config.itemTextColorWithThisWeek());
            gd.setColor(config.colorPool().getColorAutoWithAlpha(subject.getColorRandom(),config.itemAlpha()));
            gd.setCornerRadius(config.corner(true));

            int count = 0;
            List<Schedule> result = ScheduleSupport.findSubjects(subject, courses);
            for (Schedule bean : result) {
                if (ScheduleSupport.isThisWeek(bean, curWeek)) {
                    count++;
                }
            }
            if (count > 1) {
                countTextView.setVisibility(View.VISIBLE);
                countTextView.setText(count + "");
            }
        } else {
            textView.setTextColor(config.itemTextColorWithNotThis());
            gd.setColor(config.colorPool().getUselessColorWithAlpha(config.itemAlpha()));
            gd.setCornerRadius(config.corner(false));
        }

        textView.setBackgroundDrawable(gd);
        config.onItemBuildListener().onItemUpdate(layout, textView, countTextView, subject, gd);
        return isChange;
    }


    /**
     * 周次切换
     */
    public void changeWeek(LinearLayout[] panels, List<Schedule>[] data, int curWeek) {
        for (int i = 0; i < panels.length; i++) {
            List<Schedule> courses = data[i];
            boolean isChange = false;
            for (int j = 0; j < panels[i].getChildCount(); j++) {
                View view = panels[i].getChildAt(j);
                View preView;
                if (j > 0) preView = panels[i].getChildAt(j - 1);
                else preView = null;
                isChange = updateItemView(courses, view, preView, isChange, j, curWeek);
            }
        }
    }
}

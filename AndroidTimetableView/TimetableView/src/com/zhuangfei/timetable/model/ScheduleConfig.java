package com.zhuangfei.timetable.model;

import android.content.Context;
import android.view.LayoutInflater;

import com.zhuangfei.timetable.TimetableView2;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.OnDateBuildAapter;
import com.zhuangfei.timetable.listener.OnItemBuildAdapter;
import com.zhuangfei.timetable.listener.OnItemClickAdapter;
import com.zhuangfei.timetable.listener.OnScrollViewBuildAdapter;
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter;
import com.zhuangfei.timetable.listener.OnWeekChangedAdapter;
import com.zhuangfei.timetable.view.WeekView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu ZhuangFei on 2018/7/27.
 */

public class ScheduleConfig extends ToggleEnable{

    private Context context;
    private TimetableView2 tableView;
    private WeekViewEnable weekView;

    // 当前周、学期
    private int curWeek = 1;
    private String curTerm = "Term";

    // 课程数据源
    private List<Schedule> dataSource = null;

    private ISchedule.OnWeekChangedListener onWeekChangedListener;//周次改变监听
    private ISchedule.OnScrollViewBuildListener onScrollViewBuildListener;//替换滚动布局构建监听
    private ISchedule.OnDateBuildListener onDateBuildListener;//日期栏构建监听
    private ISchedule.OnItemClickListener onItemClickListener;//课程表item点击监听
    private ISchedule.OnItemBuildListener onItemBuildListener;//课程表item构建监听
    private ISchedule.OnSlideBuildListener onSlideBuildListener;//侧边栏构建监听


    //上边距、左边距、项高度
    private int marTop,marLeft,itemHeight;

    //本周、非本周的弧度
    private int thisWeekCorner;
    private int nonThisWeekCorner;
    
    //侧边项的最大个数
    private int maxSlideItem = 12;

    //颜色池
    private ScheduleColorPool colorPool;

    //是否显示非本周课程
    private boolean isShowNotCurWeek = true;

    public ScheduleConfig(Context context,TimetableView2 tableView){
        this.context=context;
        this.tableView=tableView;
    }

    public Context context(){
        return context();
    }

    public <T extends WeekViewEnable> ScheduleConfig bind(T weekView){
        this.weekView=weekView;
        return this;
    }

    public <T extends WeekViewEnable> T weekView(Class<T> clazz){
        T t=(T)weekView;
        return t;
    }

    public WeekViewEnable weekViewInterface(){
        return weekView;
    }

    /**
     * 设置日期栏构建监听器
     *
     * @param onDateBuildListener
     * @return
     */
    public ScheduleConfig callback(ISchedule.OnDateBuildListener onDateBuildListener) {
        this.onDateBuildListener = onDateBuildListener;
        return this;
    }

    /**
     * 获取日期栏构建监听器
     *
     * @return
     */
    public ISchedule.OnDateBuildListener onDateBuildListener() {
        if (onDateBuildListener == null) onDateBuildListener = new OnDateBuildAapter();
        return onDateBuildListener;
    }
    
    

    /**
     * 获取周次改变监听器
     *
     * @return
     */
    public ISchedule.OnWeekChangedListener onWeekChangedListener() {
        if (onWeekChangedListener == null) onWeekChangedListener = new OnWeekChangedAdapter();
        return onWeekChangedListener;
    }

    /**
     * 设置周次改变监听器
     *
     * @param onWeekChangedListener
     * @return
     */
    public ScheduleConfig callback(ISchedule.OnWeekChangedListener onWeekChangedListener) {
        this.onWeekChangedListener = onWeekChangedListener;
        return this;
    }

    /**
     * 设置当前周
     *
     * @param curWeek 当前周
     * @return
     */
    public ScheduleConfig curWeek(int curWeek) {
        if (curWeek < 1) this.curWeek = 1;
        else if (curWeek > 25) this.curWeek = 25;
        else this.curWeek = curWeek;
        onBind(curWeek);
        return this;
    }

    /**
     * 设置开学时间来计算当前周
     *
     * @param startTime 满足"yyyy-MM-dd HH:mm:ss"模式的字符串
     * @return
     */
    public ScheduleConfig curWeek(String startTime) {
        int week = ScheduleSupport.timeTransfrom(startTime);
        if (week == -1)
            curWeek(1);
        else
            curWeek(week);
        onBind(week);
        return this;
    }

    /**
     * 获取当前周
     *
     * @return
     */
    public int curWeek() {
        return curWeek;
    }

    /**
     * 设置当前学期
     *
     * @param curTerm
     * @return
     */
    public ScheduleConfig curTerm(String curTerm) {
        this.curTerm = curTerm;
        return this;
    }

    /**
     * 获取当前学期
     *
     * @return
     */
    public String curTerm() {
        return curTerm;
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public List<Schedule> dataSource() {
        if (dataSource == null) dataSource = new ArrayList<>();
        return dataSource;
    }

    /**
     * 设置数据源
     *
     * @param dataSource
     * @return
     */
    public ScheduleConfig data(List<Schedule> dataSource) {
        this.dataSource = ScheduleSupport.getColorReflect(dataSource);
        return this;
    }

    /**
     * 设置数据源
     *
     * @param dataSource
     * @return
     */
    public ScheduleConfig source(List<? extends ScheduleEnable> dataSource) {
        data(ScheduleSupport.transform(dataSource));
        return this;
    }

    /**
     * 设置滚动布局构建监听器
     *
     * @param onScrollViewBuildListener
     * @return
     */
    public ScheduleConfig callback(ISchedule.OnScrollViewBuildListener onScrollViewBuildListener) {
        this.onScrollViewBuildListener = onScrollViewBuildListener;
        return this;
    }

    /**
     * 周次改变时的回调
     */
    private void onBind(int cur) {
        onWeekChangedListener().onWeekChanged(cur);
    }


    /**
     * 获取滚动布局构建监听器
     *
     * @return
     */
    public ISchedule.OnScrollViewBuildListener onScrollViewBuildListener() {
        if (onScrollViewBuildListener == null)
            onScrollViewBuildListener = new OnScrollViewBuildAdapter();
        return onScrollViewBuildListener;
    }

    /**
     * 设置最大节次
     *
     * @param maxSlideItem 最大节次
     * @return
     */
    public ScheduleConfig maxSlideItem(int maxSlideItem) {
        this.maxSlideItem = maxSlideItem;
        return this;
    }

    /**
     * 获取最大节次
     *
     * @return 最大节次
     */
    public int maxSlideItem() {
        return maxSlideItem;
    }

    /**
     * 获取Item构建监听器
     *
     * @return
     */
    public ISchedule.OnItemBuildListener onItemBuildListener() {
        if (onItemBuildListener == null) onItemBuildListener = new OnItemBuildAdapter();
        return onItemBuildListener;
    }

    /**
     * 获取Item点击监听
     *
     * @return
     */
    public ISchedule.OnItemClickListener onItemClickListener() {
        if (onItemClickListener == null) onItemClickListener = new OnItemClickAdapter();
        return onItemClickListener;
    }

    /**
     * 设置本周课程的弧度
     *
     * @param thisWeekCorner 弧度
     * @return
     */
    public ScheduleConfig thisWeekCorner(int thisWeekCorner) {
        this.thisWeekCorner = thisWeekCorner;
        return this;
    }

    /**
     * 设置本周、非本周相同的弧度
     *
     * @param cornerValue 弧度
     * @return
     */
    public ScheduleConfig cornerAll(int cornerValue) {
        corner(cornerValue,true);
        corner(cornerValue,false);
        return this;
    }

    public ScheduleConfig corner(int corner,boolean isThisWeek) {
        if(isThisWeek) thisWeekCorner=corner;
        else nonThisWeekCorner=corner;
        return this;
    }

    public int corner(boolean isThisWeek) {
        if(isThisWeek) return thisWeekCorner;
        return nonThisWeekCorner;
    }

    /**
     * 设置是否显示非本周课程
     *
     * @param showNotCurWeek 如果为true，将显示非本周，否则隐藏非本周
     * @return
     */
    public ScheduleConfig isShowNotCurWeek(boolean showNotCurWeek) {
        isShowNotCurWeek = showNotCurWeek;
        return this;
    }

    /**
     * 判断是否显示非本周课程
     *
     * @return true：显示，false：不显示
     */
    public boolean isShowNotCurWeek() {
        return isShowNotCurWeek;
    }

    /**
     * 获取侧边栏构建监听
     *
     * @return
     */
    public ISchedule.OnSlideBuildListener onSlideBuildListener() {
        if (onSlideBuildListener == null) onSlideBuildListener = new OnSlideBuildAdapter();
        return onSlideBuildListener;
    }

    /**
     * 设置侧边栏构建监听器
     *
     * @param onSlideBuildListener
     * @return
     */
    public ScheduleConfig callback(ISchedule.OnSlideBuildListener onSlideBuildListener) {
        this.onSlideBuildListener = onSlideBuildListener;
        return this;
    }

    /**
     * 获取颜色池
     *
     * @return ScheduleColorPool
     * @see ScheduleColorPool
     */
    public ScheduleColorPool colorPool() {
        if (colorPool == null) colorPool = new ScheduleColorPool(context);
        return colorPool;
    }

    /**
     * dp->px
     *
     * @param dp
     * @return
     */
    public int dp2px(int dp) {
        return context.getResources().getDimensionPixelSize(dp);
    }

    /**
     * 设置课程项构建监听器
     *
     * @param onItemBuildListener
     * @return
     */
    public ScheduleConfig callback(ISchedule.OnItemBuildListener onItemBuildListener) {
        this.onItemBuildListener = onItemBuildListener;
        return this;
    }

    /**
     * 设置Item点击监听器
     *
     * @param onItemClickListener
     * @return
     */
    public ScheduleConfig callback(ISchedule.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    /**
     * 设置上边距值
     *
     * @param marTopPx
     * @return
     */
    public ScheduleConfig marTop(int marTopPx) {
        this.marTop = marTopPx;
        return this;
    }

    /**
     * 设置左边距值
     *
     * @param marLeftPx
     * @return
     */
    public ScheduleConfig marLeft(int marLeftPx) {
        this.marLeft = marLeftPx;
        return this;
    }

    /**
     * 设置课程项的高度
     *
     * @param itemHeightPx
     * @return
     */
    public ScheduleConfig itemHeight(int itemHeightPx) {
        this.itemHeight = itemHeightPx;
        return this;
    }

    /**
     * 获取课程项的高度
     *
     * @return
     */
    public int itemHeight() {
        return itemHeight;
    }

    /**
     * 获取左边距
     *
     * @return
     */
    public int marLeft() {
        return marLeft;
    }

    /**
     * 获取上边距
     *
     * @return
     */
    public int marTop() {
        return marTop;
    }
}

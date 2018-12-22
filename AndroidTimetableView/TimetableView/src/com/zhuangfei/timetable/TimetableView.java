package com.zhuangfei.timetable;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.OnConfigHandleAdapter;
import com.zhuangfei.timetable.listener.OnDateBuildAapter;
import com.zhuangfei.timetable.listener.OnFlaglayoutClickAdapter;
import com.zhuangfei.timetable.listener.OnItemBuildAdapter;
import com.zhuangfei.timetable.listener.OnItemClickAdapter;
import com.zhuangfei.timetable.listener.OnItemLongClickAdapter;
import com.zhuangfei.timetable.listener.OnScrollViewBuildAdapter;
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter;
import com.zhuangfei.timetable.listener.OnSpaceItemClickAdapter;
import com.zhuangfei.timetable.listener.OnWeekChangedAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleColorPool;
import com.zhuangfei.timetable.model.ScheduleEnable;
import com.zhuangfei.timetable.operater.AbsOperater;
import com.zhuangfei.timetable.model.ScheduleSupport;
import com.zhuangfei.timetable.operater.SimpleOperater;
import com.zhuangfei.timetable.utils.ScreenUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程表控件，该类主要负责属性的设置，业务逻辑由{@link SimpleOperater}处理
 * 虽然这个类代码很多，但是都是属性设置，除了属性设置的方法：
 * {@link #showView()}
 * {@link #changeWeek(int, boolean)}
 * {@link #changeWeekOnly(int)}
 * {@link #changeWeekForce(int)}
 * {@link #updateView()}
 * {@link #updateSlideView()}
 * {@link #updateDateView()}
 * {@link #updateView()}
 *
 * 文档参考 https://github.com/zfman/TimetableView/wiki/%E6%9C%80%E6%96%B0%E6%96%87%E6%A1%A3
 * @author Administrator
 * @see AbsOperater
 * @see SimpleOperater
 */
public class TimetableView extends LinearLayout {

    protected static final String TAG = "TimetableView";

    //业务逻辑
    private AbsOperater operater;
    private Context context;
    protected AttributeSet attrs;

    // 当前周、学期、课程数据源
    private int curWeek = 1;
    private String curTerm = "Term";
    private List<Schedule> dataSource = null;

    //默认的本地配置名称
    private String configName="default_schedule_config";

    //上边距、左边距、项高度
    private int marTop, marLeft, itemHeight;

    //侧边栏宽度
    private int monthWidth;

    //旗标布局背景颜色
    private int flagBgcolor = Color.rgb(220, 230, 239);//背景颜色
    private boolean isShowFlaglayout = true;

    //本周、非本周的弧度
    private int thisWeekCorner;
    private int nonThisWeekCorner;

    //侧边项的最大个数
    private int maxSlideItem = 12;

    //颜色池
    private ScheduleColorPool colorPool;

    //是否显示非本周课程
    private boolean isShowNotCurWeek = true;

    //课程项、侧边栏、日期栏的透明度，1：不透明，0：透明
    private float itemAlpha = 1, slideAlpha = 1, dateAlpha = 1;

    //课程项文本颜色
    private int itemTextColorWithThisWeek = Color.WHITE;//本周
    private int itemTextColorWithNotThis = Color.WHITE;//非本周

    private boolean isShowWeekends=true;

    //监听器
    private ISchedule.OnWeekChangedListener onWeekChangedListener;//周次改变监听
    private ISchedule.OnScrollViewBuildListener onScrollViewBuildListener;//替换滚动布局构建监听
    private ISchedule.OnDateBuildListener onDateBuildListener;//日期栏构建监听
    private ISchedule.OnItemClickListener onItemClickListener;//课程表item点击监听
    private ISchedule.OnItemLongClickListener onItemLongClickListener;//课程表item长按监听
    private ISchedule.OnItemBuildListener onItemBuildListener;//课程表item构建监听
    private ISchedule.OnSlideBuildListener onSlideBuildListener;//侧边栏构建监听
    private ISchedule.OnSpaceItemClickListener onSpaceItemClickListener;//空白格子点击监听
    private ISchedule.OnFlaglayoutClickListener onFlaglayoutClickListener;//旗标布局点击监听
    private ISchedule.OnConfigHandleListener onConfigHandleListener;


    public TimetableView callback(ISchedule.OnConfigHandleListener onConfigHandleListener) {
        this.onConfigHandleListener = onConfigHandleListener;
        return this;
    }

    public ISchedule.OnConfigHandleListener onConfigHandleListener() {
        if(onConfigHandleListener==null) onConfigHandleListener=new OnConfigHandleAdapter();
        return onConfigHandleListener;
    }

    /**
     * 是否显示周末
     * @param isShowWeekends
     * @return
     */
    public TimetableView isShowWeekends(boolean isShowWeekends) {
        this.isShowWeekends = isShowWeekends;
        return this;
    }

    public boolean isShowWeekends() {
        return isShowWeekends;
    }

    public AbsOperater operater(){
        if(operater==null) operater=new SimpleOperater();
        return operater;
    }

    public TimetableView operater(AbsOperater operater) {
        operater.init(context,attrs,this);
        this.operater = operater;
        return this;
    }

    /**
     * 设置侧边栏宽度dp
     *
     * @param monthWidthDp
     * @return
     */
    public TimetableView monthWidthDp(int monthWidthDp) {
        this.monthWidth = ScreenUtils.dip2px(context, monthWidthDp);
        return this;
    }

    /**
     * 设置侧边栏宽度px
     *
     * @param monthWidthPx
     * @return
     */
    public TimetableView monthWidthPx(int monthWidthPx) {
        this.monthWidth = monthWidthPx;
        return this;
    }

    /**
     * 获取侧边栏宽度px
     *
     * @return
     */
    public int monthWidth() {
        return this.monthWidth;
    }

    /**
     * 课程项文本颜色
     *
     * @param color      颜色
     * @param isThisWeek 是否本周，true：设置当前周文本颜色，false：设置非本周文本颜色
     * @return
     */
    public TimetableView itemTextColor(int color, boolean isThisWeek) {
        if (isThisWeek) itemTextColorWithThisWeek = color;
        else itemTextColorWithNotThis = color;
        return this;
    }

    /**
     * 获取本周课程项文本颜色
     *
     * @return
     */
    public int itemTextColorWithThisWeek() {
        return itemTextColorWithThisWeek;
    }

    /**
     * 获取非本周课程项文本颜色
     *
     * @return
     */
    public int itemTextColorWithNotThis() {
        return itemTextColorWithNotThis;
    }

    /**
     * 获取课程项透明度
     *
     * @return
     */
    public float itemAlpha() {
        return itemAlpha;
    }

    /**
     * 获取侧边栏透明度
     *
     * @return
     */
    public float slideAlpha() {
        return slideAlpha;
    }

    /**
     * 获取日期栏透明度
     *
     * @return
     */
    public float dateAlpha() {
        return dateAlpha;
    }

    /**
     * 透明度设置
     *
     * @param dateAlpha  日期栏透明度
     * @param slideAlpha 侧边栏透明度
     * @param itemAlpha  课程项透明度
     * @return
     */
    public TimetableView alpha(float dateAlpha, float slideAlpha, float itemAlpha) {
        this.itemAlpha = itemAlpha;
        this.slideAlpha = slideAlpha;
        this.dateAlpha = dateAlpha;
        return this;
    }

    /**
     * 将三个透明度统一设置
     *
     * @param allAlpha 日期栏、侧边栏、课程项的透明度
     * @return
     */
    public TimetableView alpha(float allAlpha) {
        this.itemAlpha = allAlpha;
        this.slideAlpha = allAlpha;
        this.dateAlpha = allAlpha;
        return this;
    }

    /**
     * 设置旗标布局背景颜色
     *
     * @param color
     * @return
     */
    public TimetableView flagBgcolor(int color) {
        this.flagBgcolor = color;
        return this;
    }

    /**
     * 重置旗标布局背景色
     *
     * @return
     */
    public TimetableView resetFlagBgcolor() {
        flagBgcolor(Color.rgb(220, 230, 239));
        return this;
    }

    /**
     * 获取是否显示旗标布局
     *
     * @return
     */
    public boolean isShowFlaglayout() {
        return isShowFlaglayout;
    }

    /**
     * 设置是否显示旗标布局
     *
     * @param isShowFlaglayout
     * @return
     */
    public TimetableView isShowFlaglayout(boolean isShowFlaglayout) {
        this.isShowFlaglayout = isShowFlaglayout;
        return this;
    }

    /**
     * 获取旗标布局背景颜色
     *
     * @return
     */
    public int flagBgcolor() {
        return flagBgcolor;
    }

    /**
     * 获取旗标布局
     *
     * @return
     */
    public LinearLayout flagLayout() {
        return operater().getFlagLayout();
    }

    /**
     * 设置课程项长按监听器
     *
     * @param onItemLongClickListener
     * @return
     */
    public TimetableView callback(ISchedule.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        return this;
    }

    /**
     * 获取课程项长按监听器
     *
     * @return
     */
    public ISchedule.OnItemLongClickListener onItemLongClickListener() {
        if (onItemLongClickListener == null) onItemLongClickListener = new OnItemLongClickAdapter();
        return onItemLongClickListener;
    }

    /**
     * 设置日期栏构建监听器
     *
     * @param onDateBuildListener
     * @return
     */
    public TimetableView callback(ISchedule.OnDateBuildListener onDateBuildListener) {
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
    public TimetableView callback(ISchedule.OnWeekChangedListener onWeekChangedListener) {
        this.onWeekChangedListener = onWeekChangedListener;
        onWeekChangedListener.onWeekChanged(curWeek);
        return this;
    }

    /**
     * 设置滚动布局构建监听器
     *
     * @param onScrollViewBuildListener
     * @return
     */
    public TimetableView callback(ISchedule.OnScrollViewBuildListener onScrollViewBuildListener) {
        this.onScrollViewBuildListener = onScrollViewBuildListener;
        return this;
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
     * 设置课程项构建监听器
     *
     * @param onItemBuildListener
     * @return
     */
    public TimetableView callback(ISchedule.OnItemBuildListener onItemBuildListener) {
        this.onItemBuildListener = onItemBuildListener;
        return this;
    }

    /**
     * 设置Item点击监听器
     *
     * @param onItemClickListener
     * @return
     */
    public TimetableView callback(ISchedule.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
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
    public TimetableView callback(ISchedule.OnSlideBuildListener onSlideBuildListener) {
        this.onSlideBuildListener = onSlideBuildListener;
        return this;
    }

    /**
     * 设置空白格子点击监听器，点击之后会出现一个旗标布局
     *
     * @param
     * @return
     */
    public TimetableView callback(ISchedule.OnSpaceItemClickListener onSpaceItemClickListener) {
        this.onSpaceItemClickListener = onSpaceItemClickListener;
        return this;
    }

    /**
     * 获取空白格子点击监听器
     *
     * @return
     */
    public ISchedule.OnSpaceItemClickListener onSpaceItemClickListener() {
        if (onSpaceItemClickListener == null)
            onSpaceItemClickListener = new OnSpaceItemClickAdapter();
        return onSpaceItemClickListener;
    }

    /**
     * 设置旗标布局点击监听器
     *
     * @param onFlaglayoutClickListener
     * @return
     */
    public TimetableView callback(ISchedule.OnFlaglayoutClickListener onFlaglayoutClickListener) {
        this.onFlaglayoutClickListener = onFlaglayoutClickListener;
        return this;
    }

    /**
     * 获取旗标布局点击监听器
     *
     * @return
     */
    public ISchedule.OnFlaglayoutClickListener onFlaglayoutClickListener() {
        if (onFlaglayoutClickListener == null)
            onFlaglayoutClickListener = new OnFlaglayoutClickAdapter();
        return onFlaglayoutClickListener;
    }

    /**
     * 设置当前周
     *
     * @param curWeek 当前周
     * @return
     */
    public TimetableView curWeek(int curWeek) {
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
    public TimetableView curWeek(String startTime) {
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
    public TimetableView curTerm(String curTerm) {
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
    public TimetableView data(List<Schedule> dataSource) {
        this.dataSource = ScheduleSupport.getColorReflect(dataSource);
        return this;
    }

    /**
     * 设置数据源
     *
     * @param dataSource
     * @return
     */
    public TimetableView source(List<? extends ScheduleEnable> dataSource) {
        data(ScheduleSupport.transform(dataSource));
        return this;
    }

    /**
     * 周次改变时的回调
     */
    private void onBind(int cur) {
        onWeekChangedListener().onWeekChanged(cur);
    }

    /**
     * 设置最大节次
     *
     * @param maxSlideItem 最大节次
     * @return
     */
    public TimetableView maxSlideItem(int maxSlideItem) {
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
    public TimetableView thisWeekCorner(int thisWeekCorner) {
        this.thisWeekCorner = thisWeekCorner;
        return this;
    }

    /**
     * 设置本周、非本周相同的弧度
     *
     * @param cornerValue 弧度
     * @return
     */
    public TimetableView cornerAll(int cornerValue) {
        corner(cornerValue, true);
        corner(cornerValue, false);
        return this;
    }

    /**
     * 课程角度设置
     *
     * @param corner     角度px
     * @param isThisWeek 是否本周上
     * @return
     */
    public TimetableView corner(int corner, boolean isThisWeek) {
        if (isThisWeek) thisWeekCorner = corner;
        else nonThisWeekCorner = corner;
        return this;
    }

    /**
     * 获取课程角度
     *
     * @param isThisWeek 是否本周上
     * @return
     */
    public int corner(boolean isThisWeek) {
        if (isThisWeek) return thisWeekCorner;
        return nonThisWeekCorner;
    }

    /**
     * 设置是否显示非本周课程
     *
     * @param showNotCurWeek 如果为true，将显示非本周，否则隐藏非本周
     * @return
     */
    public TimetableView isShowNotCurWeek(boolean showNotCurWeek) {
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
     * 设置上边距值
     *
     * @param marTopPx
     * @return
     */
    public TimetableView marTop(int marTopPx) {
        this.marTop = marTopPx;
        return this;
    }

    /**
     * 设置左边距值
     *
     * @param marLeftPx
     * @return
     */
    public TimetableView marLeft(int marLeftPx) {
        this.marLeft = marLeftPx;
        return this;
    }

    /**
     * 设置课程项的高度
     *
     * @param itemHeightPx
     * @return
     */
    public TimetableView itemHeight(int itemHeightPx) {
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

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    public TimetableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs=attrs;
        operater().init(context,attrs,this);
    }

    /**
     * 等同于showView()
     *
     * @see TimetableView#showView()
     */
    public void updateView() {
        showView();
    }

    /**
     * 隐藏旗标布局，立即生效
     */
    public TimetableView hideFlaglayout() {
        flagLayout().setVisibility(GONE);
        return this;
    }

    /**
     * 显示旗标布局，立即生效
     *
     * @return
     */
    public TimetableView showFlaglayout() {
        flagLayout().setVisibility(VISIBLE);
        return this;
    }


    /**
     * 将日期栏设为隐藏状态
     *
     * @return
     */
    public void hideDateView() {
        operater().getDateLayout().setVisibility(View.GONE);
    }

    /**
     * 将日期栏设为可见状态
     *
     * @return
     */
    public void showDateView() {
        operater().getDateLayout().setVisibility(View.VISIBLE);
    }

    /**
     * 更新日期栏
     */
    public void updateDateView() {
        operater().updateDateView();
    }

    /**
     * 侧边栏更新
     */
    public void updateSlideView() {
        operater().updateSlideView();
    }

    /**
     * 周次切换
     *
     * @param week      周次
     * @param isCurWeek 是否强制设置为本周
     */
    public void changeWeek(int week, boolean isCurWeek) {
        operater().changeWeek(week,isCurWeek);
    }

    /**
     * 仅仅切换周次，不修改当前周
     *
     * @param week
     */
    public void changeWeekOnly(int week) {
        operater().changeWeek(week,false);
    }

    /**
     * 切换周次且修改为当前周
     *
     * @param week
     */
    public void changeWeekForce(int week) {
        operater().changeWeek(week,true);
    }

    /**
     * 更新旗标布局的背景色
     */
    public void updateFlaglayout() {
        flagLayout().setBackgroundColor(flagBgcolor());
        if (!isShowFlaglayout()) hideFlaglayout();
    }

    public void showView(){
        operater().showView();
    }

    public TimetableView configName(String configName){
        this.configName=configName;
        return this;
    }

    public String configName(){
        return this.configName;
    }
}

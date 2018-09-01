package com.zhuangfei.timetable;

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
import com.zhuangfei.timetable.listener.ISchedule;
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
import com.zhuangfei.timetable.model.ScheduleSupport;
import com.zhuangfei.timetable.utils.ColorUtils;
import com.zhuangfei.timetable.utils.ScreenUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 课程表控件
 *
 * @author Administrator
 */
public class TimetableView extends LinearLayout {

    private static final String TAG = "TimetableView";

    private Context context;

    // 当前周、学期
    private int curWeek = 1;
    private String curTerm = "Term";

    // 课程数据源
    private List<Schedule> dataSource = null;

    //布局转换器
    private LayoutInflater inflater;
    private LinearLayout weekPanel;//侧边栏
    private List<Schedule>[] data = new ArrayList[7];//每天的课程
    private LinearLayout[] panels = new LinearLayout[7];//每天的面板
    private LinearLayout containerLayout;//根布局
    private LinearLayout dateLayout;//根布局、日期栏容器

    //侧边栏宽度
    private int monthWidth=50;

    //点击空白格子显示的布局:旗标布局
    private LinearLayout flagLayout;
    private int flagBgcolor=Color.rgb(220,230,239);//背景颜色
    private boolean isShowFlaglayout=true;

    //上边距、左边距、项高度
    private int marTop, marLeft, itemHeight;

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

    //保存点击的坐标
    private float x, y;

    //课程项文本颜色
    private int itemTextColorWithThisWeek = Color.WHITE;//本周
    private int itemTextColorWithNotThis = Color.WHITE;//非本周

    private ISchedule.OnWeekChangedListener onWeekChangedListener;//周次改变监听
    private ISchedule.OnScrollViewBuildListener onScrollViewBuildListener;//替换滚动布局构建监听
    private ISchedule.OnDateBuildListener onDateBuildListener;//日期栏构建监听
    private ISchedule.OnItemClickListener onItemClickListener;//课程表item点击监听
    private ISchedule.OnItemLongClickListener onItemLongClickListener;//课程表item长按监听
    private ISchedule.OnItemBuildListener onItemBuildListener;//课程表item构建监听
    private ISchedule.OnSlideBuildListener onSlideBuildListener;//侧边栏构建监听
    private ISchedule.OnSpaceItemClickListener onSpaceItemClickListener;//空白格子点击监听
    private ISchedule.OnFlaglayoutClickListener onFlaglayoutClickListener;//旗标布局点击监听

    /**
     * 设置侧边栏宽度dp
     * @param monthWidthDp
     * @return
     */
    public TimetableView monthWidthDp(int monthWidthDp) {
        this.monthWidth = ScreenUtils.dip2px(context,monthWidthDp);
        return this;
    }

    /**
     * 设置侧边栏宽度px
     * @param monthWidthPx
     * @return
     */
    public TimetableView monthWidthPx(int monthWidthPx) {
        this.monthWidth = monthWidthPx;
        return this;
    }

    /**
     * 获取侧边栏宽度px
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
     * @param color
     * @return
     */
    public TimetableView flagBgcolor(int color){
        this.flagBgcolor=color;
        return this;
    }

    /**
     * 重置旗标布局背景色
     * @return
     */
    public TimetableView resetFlagBgcolor(){
        flagBgcolor(Color.rgb(220,230,239));
        return this;
    }

    /**
     * 获取是否显示旗标布局
     * @return
     */
    public boolean isShowFlaglayout(){
        return isShowFlaglayout;
    }

    /**
     * 设置是否显示旗标布局
     * @param isShowFlaglayout
     * @return
     */
    public TimetableView isShowFlaglayout(boolean isShowFlaglayout){
        this.isShowFlaglayout=isShowFlaglayout;
        return this;
    }

    /**
     * 获取旗标布局背景颜色
     * @return
     */
    public int flagBgcolor(){
        return flagBgcolor;
    }

    /**
     * 获取旗标布局
     * @return
     */
    public LinearLayout flagLayout(){
        return flagLayout;
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
     * @return
     */
    public ISchedule.OnItemLongClickListener onItemLongClickListener() {
        if(onItemLongClickListener==null) onItemLongClickListener=new OnItemLongClickAdapter();
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
     * @return
     */
    public ISchedule.OnSpaceItemClickListener onSpaceItemClickListener() {
        if (onSpaceItemClickListener == null) onSpaceItemClickListener = new OnSpaceItemClickAdapter();
        return onSpaceItemClickListener;
    }

    /**
     * 设置旗标布局点击监听器
     * @param onFlaglayoutClickListener
     * @return
     */
    public TimetableView callback(ISchedule.OnFlaglayoutClickListener onFlaglayoutClickListener) {
        this.onFlaglayoutClickListener = onFlaglayoutClickListener;
        return this;
    }

    /**
     * 获取旗标布局点击监听器
     * @return
     */
    public ISchedule.OnFlaglayoutClickListener onFlaglayoutClickListener() {
        if (onFlaglayoutClickListener == null) onFlaglayoutClickListener = new OnFlaglayoutClickAdapter();
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
        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.timetable_layout, this);
        containerLayout = findViewById(R.id.id_container);
        dateLayout = findViewById(R.id.id_datelayout);
        initAttr(attrs);
    }

    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private void initAttr(AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimetableView);
        int curWeek = ta.getInteger(R.styleable.TimetableView_cur_week, 1);
        String curTerm = ta.getString(R.styleable.TimetableView_cur_term);

        int defMarTop = (int) context.getResources().getDimension(R.dimen.weekItemMarTop);
        int defMarLeft = (int) context.getResources().getDimension(R.dimen.weekItemMarLeft);
        int defItemHeight = (int) context.getResources().getDimension(R.dimen.weekItemHeight);

        int marTop = (int) ta.getDimension(R.styleable.TimetableView_mar_top, defMarTop);
        int marLeft = (int) ta.getDimension(R.styleable.TimetableView_mar_left, defMarLeft);
        int itemHeight = (int) ta.getDimension(R.styleable.TimetableView_item_height, defItemHeight);
        int thisWeekCorner = (int) ta.getDimension(R.styleable.TimetableView_thisweek_corner, 5);
        int nonWeekCorner = (int) ta.getDimension(R.styleable.TimetableView_nonweek_corner, 5);
        int maxSlideItem = ta.getInteger(R.styleable.TimetableView_max_slide_item, 12);
        boolean isShowNotWeek = ta.getBoolean(R.styleable.TimetableView_show_notcurweek, true);

        ta.recycle();

        curWeek(curWeek)
                .curTerm(curTerm)
                .marTop(marTop)
                .marLeft(marLeft)
                .itemHeight(itemHeight)
                .corner(thisWeekCorner, true)
                .corner(nonWeekCorner, false)
                .maxSlideItem(maxSlideItem)
                .isShowNotCurWeek(isShowNotWeek);
    }

    /**
     * 构建侧边栏
     *
     * @param slidelayout 侧边栏的容器
     */
    private void newSlideView(LinearLayout slidelayout) {
        if (slidelayout == null) return;
        slidelayout.removeAllViews();

        ISchedule.OnSlideBuildListener listener = onSlideBuildListener();
        listener.onInit(slidelayout, slideAlpha());
        for (int i = 0; i < maxSlideItem(); i++) {
            View view = listener.getView(i, inflater, itemHeight(), marTop());
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
    private View newItemView(final List<Schedule> data, final Schedule subject, Schedule pre, int i, int curWeek) {
        //宽高
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = itemHeight() * subject.getStep() + marTop() * (subject.getStep() - 1);

        //边距
        int left = marLeft() / 2, right = marLeft() / 2;
        int top = (subject.getStart() - (pre.getStart() + pre.getStep()))
                * (itemHeight() + marTop()) + marTop();

        if (i != 0 && top < 0) return null;

        // 设置Params
        View view = inflater.inflate(R.layout.item_timetable, null, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        if (i == 0) {
            top = (subject.getStart() - 1) * (itemHeight() + marTop()) + marTop();
        }
        lp.setMargins(left, top, right, 0);

        view.setBackgroundColor(Color.TRANSPARENT);
        view.setTag(subject);
        FrameLayout layout = view.findViewById(R.id.id_course_item_framelayout);
        TextView textView = view.findViewById(R.id.id_course_item_course);
        layout.setLayoutParams(lp);

        boolean isThisWeek = ScheduleSupport.isThisWeek(subject, curWeek);
        textView.setText(onItemBuildListener().getItemText(subject, isThisWeek));

        GradientDrawable gd = new GradientDrawable();
        if (isThisWeek) {
            gd.setColor(colorPool().getColorAuto(subject.getColorRandom()));
            gd.setCornerRadius(corner(true));
        } else {
            gd.setColor(colorPool().getUselessColor());
            gd.setCornerRadius(corner(false));
        }
        textView.setBackgroundDrawable(gd);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Schedule> result = ScheduleSupport.findSubjects(subject, data);
                onItemClickListener().onItemClick(v, result);
            }
        });

        textView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemLongClickListener().onLongClick(view,subject.getDay(),subject.getStart());
                return true;
            }
        });

        return view;
    }

    /**
     * 将数据data添加到layout的布局上
     *
     * @param layout  容器
     * @param data    某一天的数据集合
     * @param curWeek 当前周
     */
    private void addToLayout(LinearLayout layout, final List<Schedule> data, int curWeek) {
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
    private boolean updateItemView(final List<Schedule> courses, View view, View preView, boolean isChange, int j, int curWeek) {
        FrameLayout layout = (FrameLayout) view.findViewById(R.id.id_course_item_framelayout);
        Schedule tag = (Schedule) view.getTag();
        Schedule subject = ScheduleSupport.findRealSubject(tag.getStart(), curWeek, courses);

        boolean isThisWeek = ScheduleSupport.isThisWeek(subject, curWeek);
        if (!isThisWeek && !isShowNotCurWeek()) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }

        int height = itemHeight() * subject.getStep() +
                marTop() * (subject.getStep() - 1);
        int leftOrRight = marLeft() / 2;
        int top = 0;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height);

        if (isChange || tag.getStart() != subject.getStart() || tag.getStep() != subject.getStep()) {
            if (j > 0) {
                Schedule pre = (Schedule) preView.getTag();
                top = (subject.getStart() - (pre.getStart() + pre.getStep())) * (itemHeight()
                        + marTop()) + marTop();
            } else {
                top = (subject.getStart() - 1) * (itemHeight() + marTop()) + marTop();
            }
            lp.setMargins(leftOrRight, top, leftOrRight, 0);
            layout.setLayoutParams(lp);
            view.setTag(subject);
            isChange = true;
        }

        if (top < 0) view.setVisibility(View.GONE);
        TextView textView = (TextView) view.findViewById(R.id.id_course_item_course);
        TextView countTextView = (TextView) view.findViewById(R.id.id_course_item_count);
        textView.setText(onItemBuildListener().getItemText(subject, isThisWeek));

        countTextView.setText("");
        countTextView.setVisibility(View.GONE);

        GradientDrawable gd = new GradientDrawable();
        if (isThisWeek) {
            textView.setTextColor(itemTextColorWithThisWeek());
            gd.setColor(colorPool().getColorAutoWithAlpha(subject.getColorRandom(), itemAlpha()));
            gd.setCornerRadius(corner(true));

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
            textView.setTextColor(itemTextColorWithNotThis());
            gd.setColor(colorPool().getUselessColorWithAlpha(itemAlpha()));
            gd.setCornerRadius(corner(false));
        }

        textView.setBackgroundDrawable(gd);
        onItemBuildListener().onItemUpdate(layout, textView, countTextView, subject, gd);
        return isChange;
    }


    /**
     * 周次切换
     */
    private void changeWeek(LinearLayout[] panels, List<Schedule>[] data, int curWeek) {
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

    /**
     * 点击panel时的事件响应
     */
    private void onPanelClicked(View view, float y) {
        if(isShowFlaglayout()){
            flagLayout.setVisibility(VISIBLE);
        }else{
            flagLayout.setVisibility(GONE);
        }

        //周几，0：周一，6：周日
        int day = 0;

        // 先找到点击的是周几的panel
        for (int i = 0; i < panels.length; i++) {
            if (view == panels[i]) {
                day = i;
                break;
            }
        }

        if (day == -1)
            return;

        // 判断点击的是第几节课，1：第1节
        final int start = (int) Math.ceil((y / (itemHeight + marTop)));
        onSpaceItemClickListener().onSpaceItemClick(day,start);
        final int finalDay = day;
        flagLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onFlaglayoutClickListener().onFlaglayoutClick(finalDay,start);
            }
        });
    }

    /**
     * 初始化panel并为panel设置事件监听
     */
    private void initPanel() {
        for (int i = 0; i < panels.length; i++) {
            panels[i] = findViewById(R.id.weekPanel_1 + i);
            data[i] = new ArrayList<>();

            /**
             * 点击空白格子时才会触发这个事件
             */
            panels[i].setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    switch (arg1.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            x = arg1.getX();
                            y = arg1.getY();

                            break;
                        case MotionEvent.ACTION_UP:
                            float x2 = arg1.getX();
                            float y2 = arg1.getY();
                            if (x2 == x && y2 == y)
                                onPanelClicked(arg0, arg1.getY());
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
        }
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
        flagLayout.setVisibility(GONE);
        return this;
    }

    /**
     * 显示旗标布局，立即生效
     * @return
     */
    public TimetableView showFlaglayout() {
        flagLayout.setVisibility(VISIBLE);
        return this;
    }


    /**
     * 将日期栏设为隐藏状态
     *
     * @return
     */
    public void hideDateView() {
        dateLayout.setVisibility(View.GONE);
    }

    /**
     * 将日期栏设为可见状态
     *
     * @return
     */
    public void showDateView() {
        dateLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 绘制课程表
     */
    public void showView() {
        if (dataSource() == null) return;

        //实现ScrollView的替换,只有在初始化时替换一次
        if (findViewById(R.id.id_scrollview) == null) {
            View view = onScrollViewBuildListener().getScrollView(inflater);
            containerLayout.addView(view);
            //初始化
            weekPanel = findViewById(R.id.weekPanel_0);
            flagLayout=findViewById(R.id.id_flaglayout);
            initPanel();
        }

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(monthWidth(),LayoutParams.MATCH_PARENT);
        weekPanel.setLayoutParams(lp);

        flagLayout.setBackgroundColor(flagBgcolor());
        float perWidth = (ScreenUtils.getWidthInPx(context) -monthWidth())/7;
        onSpaceItemClickListener().onInit(flagLayout, monthWidth(),
                Math.round(perWidth),itemHeight(),marTop(),
                Math.round(marLeft()/2.0f));

        //更新日期
        updateDateView();
        updateSlideView();

        //清空、拆分数据
        for (int i = 0; i < 7; i++) {
            data[i].clear();
        }
        List<Schedule> source = dataSource();
        for (int i = 0; i < source.size(); i++) {
            Schedule bean = source.get(i);
            if (bean.getDay() != -1)
                data[bean.getDay() - 1].add(bean);
        }

        //排序、填充课程
        ScheduleSupport.sortList(data);
        for (int i = 0; i < panels.length; i++) {
            panels[i].removeAllViews();
            addToLayout(panels[i], data[i], curWeek());
        }
        //周次切换，保证重叠时显示角标
        changeWeek(curWeek(), false);
    }

    /**
     * 更新日期栏
     */
    public void updateDateView() {
        dateLayout.removeAllViews();
        float perWidth = (ScreenUtils.getWidthInPx(context) -monthWidth())/7;
        int height = context.getResources().getDimensionPixelSize(R.dimen.headHeight);
//		//日期栏
        ISchedule.OnDateBuildListener listener = onDateBuildListener();
        listener.onInit(dateLayout, dateAlpha());
        View[] views = onDateBuildListener().getDateViews(inflater, monthWidth(),perWidth, height);
        for (View v : views) {
            if (v != null) {
                dateLayout.addView(v);
            }
        }
        onDateBuildListener().onUpdateDate(curWeek(), curWeek());
        onDateBuildListener().onHighLight();
    }

    /**
     * 侧边栏更新
     */
    public void updateSlideView() {
        newSlideView(weekPanel);
    }

    /**
     * 周次切换
     *
     * @param week      周次
     * @param isCurWeek 是否强制设置为本周
     */
    public void changeWeek(int week, boolean isCurWeek) {
        if (isCurWeek) changeWeekForce(week);
        else changeWeekOnly(week);
    }

    /**
     * 仅仅切换周次，不修改当前周
     *
     * @param week
     */
    public void changeWeekOnly(int week) {
        changeWeek(panels, data, week);
        onWeekChangedListener().onWeekChanged(week);
    }

    /**
     * 切换周次且修改为当前周
     *
     * @param week
     */
    public void changeWeekForce(int week) {
        changeWeek(panels, data, week);
        curWeek(week);
    }

    /**
     * 更新旗标布局的背景色
     */
    public void updateFlaglayout(){
        flagLayout.setBackgroundColor(flagBgcolor());
        if(!isShowFlaglayout()) hideFlaglayout();
    }
}

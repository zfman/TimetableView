package com.zhuangfei.timetable.operater;

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
import com.zhuangfei.timetable.model.ScheduleConfig;
import com.zhuangfei.timetable.model.ScheduleSupport;
import com.zhuangfei.timetable.utils.ColorUtils;
import com.zhuangfei.timetable.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 课表业务操作者，TimetableView中只涉及属性的设置，方法的具体实现在这里.
 * 常用的方法也就四个，如下
 *
 * @see SimpleOperater#changeWeek(int, boolean)
 * @see SimpleOperater#showView()
 * @see SimpleOperater#updateDateView()
 * @see SimpleOperater#updateSlideView()
 *
 * Created by Liu ZhuangFei on 2018/9/1.
 */
public class SimpleOperater extends AbsOperater{
    private static final String TAG = "SimpleOperater";

    protected TimetableView mView;
    protected Context context;

    //保存点击的坐标
    protected float x, y;

    //布局转换器
    protected LayoutInflater inflater;
    protected LinearLayout weekPanel;//侧边栏
    protected List<Schedule>[] data = new ArrayList[7];//每天的课程
    protected LinearLayout[] panels = new LinearLayout[7];//每天的面板
    protected LinearLayout containerLayout;//根布局
    protected LinearLayout dateLayout;//根布局、日期栏容器
    protected LinearLayout flagLayout;//旗标布局

    protected ScheduleConfig scheduleConfig;

    @Override
    public void init(Context context, AttributeSet attrs, TimetableView view) {
        this.context = context;
        mView = view;
        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.timetable_layout, mView);
        containerLayout = mView.findViewById(R.id.id_container);
        dateLayout = mView.findViewById(R.id.id_datelayout);
        mView.monthWidthDp(40);
        initAttr(attrs);
        scheduleConfig=new ScheduleConfig(context);
    }

    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    protected void initAttr(AttributeSet attrs) {
        if(attrs==null) return;
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

        mView.curWeek(curWeek)
                .curTerm(curTerm)
                .marTop(marTop)
                .marLeft(marLeft)
                .itemHeight(itemHeight)
                .corner(thisWeekCorner, true)
                .corner(nonWeekCorner, false)
                .maxSlideItem(maxSlideItem)
                .isShowNotCurWeek(isShowNotWeek);
    }

    @Override
    public LinearLayout getDateLayout() {
        return dateLayout;
    }

    /**
     * 获取旗标布局,需要在showView方法执行后执行
     * @return
     */
    @Override
    public LinearLayout getFlagLayout(){
        return flagLayout;
    }

    /**
     * 构建侧边栏
     *
     * @param slidelayout 侧边栏的容器
     */
    public void newSlideView(LinearLayout slidelayout) {
        if (slidelayout == null) return;
        slidelayout.removeAllViews();

        ISchedule.OnSlideBuildListener listener = mView.onSlideBuildListener();
        listener.onInit(slidelayout, mView.slideAlpha());
        for (int i = 0; i < mView.maxSlideItem(); i++) {
            View view = listener.getView(i, inflater, mView.itemHeight(), mView.marTop());
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
    private View newItemView(final List<Schedule> originData, final List<Schedule> data, final Schedule subject, Schedule pre, int i, int curWeek) {
        //宽高
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = mView.itemHeight() * subject.getStep() + mView.marTop() * (subject.getStep() - 1);

        //边距
        int left = mView.marLeft() / 2, right = mView.marLeft() / 2;
        int top = (subject.getStart() - (pre.getStart() + pre.getStep()))
                * (mView.itemHeight() + mView.marTop()) + mView.marTop();

        if (i != 0 && top < 0) return null;

        // 设置Params
        View view = inflater.inflate(R.layout.item_timetable, null, false);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        if (i == 0) {
            top = (subject.getStart() - 1) * (mView.itemHeight() + mView.marTop()) + mView.marTop();
        }
        lp.setMargins(left, top, right, 0);

        view.setBackgroundColor(Color.TRANSPARENT);
        view.setTag(subject);
        FrameLayout layout = view.findViewById(R.id.id_course_item_framelayout);
        layout.setLayoutParams(lp);

        boolean isThisWeek = ScheduleSupport.isThisWeek(subject, curWeek);
        TextView textView = (TextView) view.findViewById(R.id.id_course_item_course);
        TextView countTextView = (TextView) view.findViewById(R.id.id_course_item_count);
        textView.setText(mView.onItemBuildListener().getItemText(subject, isThisWeek));

        countTextView.setText("");
        countTextView.setVisibility(View.GONE);

        GradientDrawable gd = new GradientDrawable();
        if (isThisWeek) {
            textView.setTextColor(mView.itemTextColorWithThisWeek());
            Map<String,Integer> colorMap=mView.colorPool().getColorMap();
            if(!colorMap.isEmpty()&&colorMap.containsKey(subject.getName())){
                gd.setColor(ColorUtils.alphaColor(colorMap.get(subject.getName()),mView.itemAlpha()));
            }else{
                gd.setColor(mView.colorPool().getColorAutoWithAlpha(subject.getColorRandom(), mView.itemAlpha()));
            }
            gd.setCornerRadius(mView.corner(true));

            List<Schedule> clist = ScheduleSupport.findSubjects(subject, originData);
            int count =0;
            if(clist!=null){
                for(int k=0;k<clist.size();k++){
                    Schedule p=clist.get(k);
                    if(p!=null&&ScheduleSupport.isThisWeek(p,curWeek)) count++;
                }
            }
            if (count > 1) {
                countTextView.setVisibility(View.VISIBLE);
                countTextView.setText(count + "");
            }
        } else {
            textView.setTextColor(mView.itemTextColorWithNotThis());
            Map<String,Integer> colorMap=mView.colorPool().getColorMap();
            if(!colorMap.isEmpty()&&mView.colorPool().isIgnoreUserlessColor()&&colorMap.containsKey(subject.getName())){
                gd.setColor(ColorUtils.alphaColor(colorMap.get(subject.getName()),mView.itemAlpha()));
            }else{
                gd.setColor(mView.colorPool().getUselessColorWithAlpha(mView.itemAlpha()));
            }
            gd.setCornerRadius(mView.corner(false));
        }

        textView.setBackgroundDrawable(gd);
        mView.onItemBuildListener().onItemUpdate(layout, textView, countTextView, subject, gd);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Schedule> result = ScheduleSupport.findSubjects(subject, originData);
                mView.onItemClickListener().onItemClick(v, result);
            }
        });

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mView.onItemLongClickListener().onLongClick(view, subject.getDay(), subject.getStart());
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
        List<Schedule> filter = ScheduleSupport.fliterSchedule(data, curWeek,mView.isShowNotCurWeek());
        Schedule pre=null;
        if(filter.size()>0){
            pre = filter.get(0);
        }
        for (int i = 0; i < filter.size(); i++) {
            final Schedule subject = filter.get(i);
            View view = newItemView(data,filter, subject, pre, i, curWeek);
            if (view != null) {
                layout.addView(view);
                pre = subject;
            }
        }
    }


    /**
     * 点击panel时的事件响应
     */
    protected void onPanelClicked(View view, float y) {
        if(mView.isShowFlaglayout()){
            flagLayout.setVisibility(View.VISIBLE);
        }else{
            flagLayout.setVisibility(View.GONE);
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
        final int start = (int) Math.ceil((y / (mView.itemHeight() + mView.marTop())));
        if(!checkPosition(day,start)){
            mView.onSpaceItemClickListener().onSpaceItemClick(day,start);
        }
        final int finalDay = day;
        flagLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.onFlaglayoutClickListener().onFlaglayoutClick(finalDay,start);
            }
        });
    }

    /**
     * 判断位置是否有课
     * @param day
     * @param start
     * @return true：有课，false：无课
     */
    protected boolean checkPosition(int day,int start){
        List<Schedule> list;
        if(mView.isShowNotCurWeek()){
            list= ScheduleSupport.getAllSubjectsWithDay(mView.dataSource(),day);
        }else{
            list= ScheduleSupport.getHaveSubjectsWithDay(mView.dataSource(),mView.curWeek(),day);
        }
        boolean isHave=false;
        for(Schedule item:list){
            if(start==item.getStart()||(start>=item.getStart()&&start<=(item.getStart()+item.getStep()-1))){
                isHave=true;
            }
        }
        return isHave;
    }

    /**
     * 初始化panel并为panel设置事件监听
     */
    protected void initPanel() {
        for (int i = 0; i < panels.length; i++) {
            panels[i] = mView.findViewById(R.id.weekPanel_1 + i);
            data[i] = new ArrayList<>();

            /**
             * 点击空白格子时才会触发这个事件
             */
            panels[i].setOnTouchListener(new View.OnTouchListener() {

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
     * 实现ScrollView的替换,只有在初始化时替换一次
     */
    public void replaceScrollView(){
        if (mView.findViewById(R.id.id_scrollview) == null) {
            View view = mView.onScrollViewBuildListener().getScrollView(inflater);
            containerLayout.addView(view);
            //初始化
            weekPanel = mView.findViewById(R.id.weekPanel_0);
            flagLayout=mView.findViewById(R.id.id_flaglayout);
            initPanel();
        }
    }

    /**
     * 设置旗标布局的配置
     */
    public void applyFlagLayoutConf(){
        mView.hideFlaglayout();
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(mView.monthWidth(), LinearLayout.LayoutParams.MATCH_PARENT);
        weekPanel.setLayoutParams(lp);
        flagLayout.setBackgroundColor(mView.flagBgcolor());
        float perWidth=getPerWidth();
        mView.onSpaceItemClickListener().onInit(flagLayout, mView.monthWidth(),
                Math.round(perWidth),mView.itemHeight(),mView.marTop(),
                Math.round(mView.marLeft()/2.0f));
    }

    /**
     * 开始装填数据
     */
    public void startTodo(){
        //清空、拆分数据
        for (int i = 0; i < 7; i++) {
            data[i].clear();
        }
        List<Schedule> source = mView.dataSource();
        for (int i = 0; i < source.size(); i++) {
            Schedule bean = source.get(i);
            if (bean.getDay() != -1)
                data[bean.getDay() - 1].add(bean);
        }

        //排序、填充课程
        ScheduleSupport.sortList(data);
        for (int i = 0; i < panels.length; i++) {
            panels[i].removeAllViews();
            addToLayout(panels[i], data[i], mView.curWeek());
        }
    }

    /**
     * 设置宽度
     */
    public void applyWidthConfig(){
        setWeekendsVisiable(mView.isShowWeekends());
    }

    /**
     * 绘制课程表
     */
    @Override
    public void showView() {
        if (mView==null||mView.dataSource() == null) return;
        checkConfig();
        replaceScrollView();
        Log.d(TAG, "showView: "+flagLayout);
        applyFlagLayoutConf();
        applyWidthConfig();

        //更新日期
        updateDateView();
        updateSlideView();
        startTodo();
    }

    /**
     * 本地配置的加载
     */
    private void checkConfig() {
        if(mView==null||mView.onConfigHandleListener()==null) return;
        if(mView.onConfigHandleListener()!=scheduleConfig.getOnConfigHandleListener()){
            scheduleConfig.setOnConfigHandleListener(mView.onConfigHandleListener());
        }
        scheduleConfig.setConfigName(mView.configName());
        scheduleConfig.use(mView);
    }

    /**
     * 切换周次
     * @param week
     * @param isCurWeek 是否强制设置为当前周
     */
    @Override
    public void changeWeek(int week, boolean isCurWeek) {
        for (int i = 0; i < panels.length; i++) {
            addToLayout(panels[i], data[i], week);
        }
        if (isCurWeek){
            mView.curWeek(week);
        }else{
            mView.onWeekChangedListener().onWeekChanged(week);
        }
    }

    protected float getPerWidth(){
        float perWidth = 0;
        if(mView.isShowWeekends()){
            perWidth=(ScreenUtils.getWidthInPx(context) -mView.monthWidth())/7;
        }else{
            perWidth=(ScreenUtils.getWidthInPx(context) -mView.monthWidth())/5;
        }
        return perWidth;
    }
    /**
     * 更新日期栏
     */
    public void updateDateView() {
        dateLayout.removeAllViews();

        float perWidth=getPerWidth();

        int height = context.getResources().getDimensionPixelSize(R.dimen.headHeight);
//		//日期栏
        ISchedule.OnDateBuildListener listener = mView.onDateBuildListener();
        listener.onInit(dateLayout, mView.dateAlpha());
        View[] views = mView.onDateBuildListener().getDateViews(inflater, mView.monthWidth(),perWidth, height);
        for (View v : views) {
            if (v != null) {
                dateLayout.addView(v);
            }
        }
        mView.onDateBuildListener().onUpdateDate(mView.curWeek(), mView.curWeek());
        mView.onDateBuildListener().onHighLight();
    }

    /**
     * 侧边栏更新
     */
    @Override
    public void updateSlideView() {
        newSlideView(weekPanel);
    }

    /**
     * 设置周末的可见性
     */
    public void setWeekendsVisiable(boolean isShow) {
        if(isShow){
            if(panels!=null&&panels.length>6){
                panels[5].setVisibility(View.VISIBLE);
                panels[6].setVisibility(View.VISIBLE);
            }
        }else{
            if(panels!=null&&panels.length>6){
                panels[5].setVisibility(View.GONE);
                panels[6].setVisibility(View.GONE);
            }
        }
    }
}

package com.zhuangfei.timetable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zhuangfei.android_timetableview.sample.R;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleConfig;
import com.zhuangfei.timetable.model.ScheduleManager2;
import com.zhuangfei.timetable.model.ScheduleSupport;
import com.zhuangfei.timetable.utils.ScreenUtils;
import com.zhuangfei.timetable.view.WeekView;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程表控件
 *
 * @author Administrator
 *
 */
public class TimetableView2 extends LinearLayout{

	private static final String TAG = "TimetableView";

	private Context context;
	private ScheduleConfig config;
	private ScheduleManager2 manager2;

	//布局转换器
	private LayoutInflater inflater;
	private LinearLayout weekPanel;//侧边栏
	private List<Schedule>[] data = new ArrayList[7];//每天的课程
	private LinearLayout[] panels = new LinearLayout[7];//每天的面板
	private LinearLayout containerLayout;//根布局
	private LinearLayout dateLayout;//根布局、日期栏容器

    //侧边栏背景颜色
    private int weekpanelBgcolor;

	public TimetableView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
        inflater = LayoutInflater.from(context);
        config=new ScheduleConfig(context,this);
		manager2=new ScheduleManager2(config,inflater);
		inflater.inflate(R.layout.timetable_layout,this);
		containerLayout=findViewById(R.id.id_container);
		dateLayout=findViewById(R.id.id_datelayout);
		initAttr(attrs);
	}

	/**
	 * 获取自定义属性
	 * @param attrs
	 */
	private void initAttr(AttributeSet attrs) {
		TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.TimetableView);
		int curWeek=ta.getInteger(R.styleable.TimetableView_cur_week,1);
		String curTerm=ta.getString(R.styleable.TimetableView_cur_term);

		int defMarTop= (int) context.getResources().getDimension(R.dimen.weekItemMarTop);
		int defMarLeft= (int) context.getResources().getDimension(R.dimen.weekItemMarLeft);
		int defItemHeight= (int) context.getResources().getDimension(R.dimen.weekItemHeight);

		int marTop= (int) ta.getDimension(R.styleable.TimetableView_mar_top,defMarTop);
		int marLeft= (int) ta.getDimension(R.styleable.TimetableView_mar_left,defMarLeft);
		int itemHeight= (int) ta.getDimension(R.styleable.TimetableView_item_height,defItemHeight);
		int thisWeekCorner= (int) ta.getDimension(R.styleable.TimetableView_thisweek_corner,8);
		int nonWeekCorner= (int) ta.getDimension(R.styleable.TimetableView_nonweek_corner,8);
		int maxSlideItem=ta.getInteger(R.styleable.TimetableView_max_slide_item,12);
		boolean isShowNotWeek=ta.getBoolean(R.styleable.TimetableView_show_notcurweek,true);

		config.curWeek(curWeek)
				.curTerm(curTerm)
				.marTop(marTop)
				.marLeft(marLeft)
				.itemHeight(itemHeight)
				.corner(thisWeekCorner,true)
				.corner(nonWeekCorner,false)
				.maxSlideItem(maxSlideItem)
				.isShowNotCurWeek(isShowNotWeek);
	}

	public ScheduleConfig config(){
		return config;
	}

	/**
	 * 等同于showView()
	 *
	 * @see TimetableView2#showView()
	 */
	public void updateView(){
		showView();
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
		if (config.dataSource() == null) return;

		//实现ScrollView的替换,只有在初始化时替换一次
		if (findViewById(R.id.id_scrollview) == null) {
			View view = config.onScrollViewBuildListener().getScrollView(inflater);
			containerLayout.addView(view);
			//初始化
			weekPanel = findViewById(R.id.weekPanel_0);
			for (int i = 0; i < panels.length; i++) {
				panels[i] = findViewById(R.id.weekPanel_1 + i);
				data[i] = new ArrayList<>();
			}
		}

//		weekPanel.setBackgroundColor(weekpanelBgcolor);

		//更新日期
		updateDateView();
		updateSlideView();

		//拆分数据
		for (int i = 0; i < 7; i++) {
			data[i].clear();
		}

		List<Schedule> source = config.dataSource();
		for (int i = 0; i < source.size(); i++) {
			Schedule bean = source.get(i);
			if (bean.getDay() != -1)
				data[bean.getDay() - 1].add(bean);
		}

		//排序、填充课程
		ScheduleSupport.sortList(data);
		for (int i = 0; i < panels.length; i++) {
			panels[i].removeAllViews();
			manager2.addToLayout(panels[i], data[i], config.curWeek());
		}

		//周次切换，保证重叠时显示角标
		changeWeek(config.curWeek(), false);
	}

	/**
	 * 更新日期栏
	 */
	public void updateDateView() {
		dateLayout.removeAllViews();
		float perWidth = ScreenUtils.getWidthInPx(context) / 11.5f;
		int height = context.getResources().getDimensionPixelSize(R.dimen.headHeight);

//		//日期栏
		View[] views = config.onDateBuildListener().getDateViews(inflater, perWidth, height);
		for (View v : views) {
			if (v != null) {
				dateLayout.addView(v);
			}
		}

		config.onDateBuildListener().onUpdateDate();
		config.onDateBuildListener().onHighLight();
	}

	/**
	 * 侧边栏更新
	 */
	public void updateSlideView() {
		manager2.newSlideView(weekPanel);
	}

	/**
	 * 周次切换
	 * @param week 周次
	 * @param isCurWeek 是否强制设置为本周
	 */
	public void changeWeek(int week, boolean isCurWeek) {
		if(isCurWeek) changeWeekForce(week);
		else changeWeekOnly(week);
	}

	/**
	 * 仅仅切换周次，不修改当前周
	 * @param week
	 */
	public void changeWeekOnly(int week) {
		manager2.changeWeek(panels, data, week);
		config.onWeekChangedListener().onWeekChanged(week);
	}

	/**
	 * 切换周次且修改为当前周
	 * @param week
	 */
	public void changeWeekForce(int week) {
		manager2.changeWeek(panels, data, week);
		config.curWeek(week);
	}

}

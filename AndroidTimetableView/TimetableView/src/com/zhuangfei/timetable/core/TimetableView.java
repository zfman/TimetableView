package com.zhuangfei.timetable.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.sample.R;

/**
 * 课程表控件
 * 
 * @author Administrator
 * 
 */
public class TimetableView extends LinearLayout{

	// 存储一周内的每天的课程数据以及每天的Layout
	private List<SubjectBean>[] data = new ArrayList[7];
	private LinearLayout[] panels = new LinearLayout[7];

	// 周一到周日的七个TextView，实现星期高亮效果
	private LinearLayout week1;
	private LinearLayout week2;
	private LinearLayout week3;
	private LinearLayout week4;
	private LinearLayout week5;
	private LinearLayout week6;
	private LinearLayout week7;

	private TextView weekMonth;
	private TextView dateTextView1;
	private TextView dateTextView2;
	private TextView dateTextView3;
	private TextView dateTextView4;
	private TextView dateTextView5;
	private TextView dateTextView6;
	private TextView dateTextView7;

	// 左侧11节、12节课的布局以及虚线层
	private TextView start11TextView;
	private TextView start12TextView;
	private TextView dashLayer11TextView;
	private TextView dashLayer12TextView;
	//是否显示 最大节次，默认10节
	private boolean isMax = false;

	// 当前周
	private int curWeek = 1;
	private SubjectUIModel subjectUIModel;
	private Context context;
	private String curTerm = "大三 春季学期";

	// 课程数据源
	private List<SubjectBean> dataSource = null;

	LayoutInflater inflater;
	int textColor;

	//虚线层
	private LinearLayout dashLayer;
	private boolean isShowDashlayer = true;
	private boolean isChooseLayoutShowing=false;
	
	private ScrollView scrollView;
	private TextView titleTextView;

	private View bottomLayer=null;
	
	// 课表item事件监听器
	private OnSubjectItemClickListener onSubjectItemClickListener;
	private OnSubjectBindViewListener onSubjectBindViewListener;
	private OnSubjectItemLongClickListener onSubjectItemLongClickListener;

	public TimetableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		inflater = LayoutInflater.from(context);
		if (context != null) {
			textColor = context.getResources().getColor(
					R.color.app_course_textcolor_blue);
		} else {
			textColor = Color.BLACK;
		}

		initView(context);
		initWeekDateInfo();
		weekLightHight();
	}

	public TimetableView setCurWeek(int curWeek) {
		if(curWeek<1) this.curWeek=1;
		else if(curWeek>20) this.curWeek=20;
		else this.curWeek = curWeek;
		onBind();
		return this;
	}

	public TimetableView setCurWeek(String startTime) {
		int week = SubjectUtils.timeTransfrom(startTime);
		if (week == -1)
			setCurWeek(1);
		else
			setCurWeek(week);
		onBind();
		return this;
	}

	public int getCurWeek() {
		return curWeek;
	}

	public TimetableView setCurTerm(String curTerm) {
		this.curTerm = curTerm;
		onBind();
		return this;
	}

	public String getCurTerm() {
		return curTerm;
	}

	public TimetableView setDataSource(List<SubjectBean> dataSource) {
		this.dataSource = dataSource;
		onBind();
		return this;
	}

	public TimetableView setBottomLayer(View bottomLayer) {
		this.bottomLayer = bottomLayer;
		return this;
	}

	public TimetableView setOnSubjectItemClickListener(
			OnSubjectItemClickListener onSubjectItemClickListener) {
		this.onSubjectItemClickListener = onSubjectItemClickListener;
		return this;
	}

	public TimetableView setShowDashLayer(boolean isShow) {
		isShowDashlayer = isShow;
		if (isShowDashlayer) {
			dashLayer.setVisibility(View.VISIBLE);
		} else {
			dashLayer.setVisibility(View.GONE);
		}
		return this;
	}
	
	public TimetableView setMax(boolean isMax) {
		this.isMax = isMax;
		if (this.isMax) {
			start11TextView.setVisibility(View.VISIBLE);
			start12TextView.setVisibility(View.VISIBLE);
			dashLayer11TextView.setVisibility(View.VISIBLE);
			dashLayer12TextView.setVisibility(View.VISIBLE);
		} else {
			start11TextView.setVisibility(View.GONE);
			start12TextView.setVisibility(View.GONE);
			dashLayer11TextView.setVisibility(View.GONE);
			dashLayer12TextView.setVisibility(View.GONE);
		}
		return this;
	}

	public TimetableView bindTitleView(TextView titleTextView){
		this.titleTextView=titleTextView;
		return this;
	}
	
	public TimetableView setOnSubjectBindViewListener(OnSubjectBindViewListener onSubjectBindViewListener) {
		this.onSubjectBindViewListener = onSubjectBindViewListener;
		return this;
	}
	
	public TimetableView setOnSubjectItemLongClickListener(OnSubjectItemLongClickListener onSubjectItemLongClickListener) {
		this.onSubjectItemLongClickListener = onSubjectItemLongClickListener;
		return this;
	}
	
	
	public ScrollView getScrollView() {
		return scrollView;
	}
	
	private void onBind(){
		if(onSubjectBindViewListener!=null&&titleTextView!=null){
			onSubjectBindViewListener.onBindTitleView(titleTextView, curWeek, curTerm,dataSource);
		}
	}
	private void initWeekDateInfo() {
		// List<String> weekDays=DateTools.getWeekDays();
		List<String> weekDays = SubjectUtils.getWeekDate();
		if (weekDays != null && weekDays.size() > 6) {
			weekMonth.setText(weekDays.get(0) + "月");
			dateTextView1.setText(weekDays.get(1) + "日");
			dateTextView2.setText(weekDays.get(2) + "日");
			dateTextView3.setText(weekDays.get(3) + "日");
			dateTextView4.setText(weekDays.get(4) + "日");
			dateTextView5.setText(weekDays.get(5) + "日");
			dateTextView6.setText(weekDays.get(6) + "日");
			dateTextView7.setText(weekDays.get(7) + "日");
		}

	}

	public void initView(Context context) {
		LayoutInflater.from(context).inflate(R.layout.timetable_layout, this);
		scrollView=(ScrollView) findViewById(R.id.id_scrollview);
		
		week1 = (LinearLayout) findViewById(R.id.id_week1);
		week2 = (LinearLayout) findViewById(R.id.id_week2);
		week3 = (LinearLayout) findViewById(R.id.id_week3);
		week4 = (LinearLayout) findViewById(R.id.id_week4);
		week5 = (LinearLayout) findViewById(R.id.id_week5);
		week6 = (LinearLayout) findViewById(R.id.id_week6);
		week7 = (LinearLayout) findViewById(R.id.id_week7);

		weekMonth = (TextView) findViewById(R.id.id_week_month);
		dateTextView1 = (TextView) findViewById(R.id.id_week_date1);
		dateTextView2 = (TextView) findViewById(R.id.id_week_date2);
		dateTextView3 = (TextView) findViewById(R.id.id_week_date3);
		dateTextView4 = (TextView) findViewById(R.id.id_week_date4);
		dateTextView5 = (TextView) findViewById(R.id.id_week_date5);
		dateTextView6 = (TextView) findViewById(R.id.id_week_date6);
		dateTextView7 = (TextView) findViewById(R.id.id_week_date7);
		
		dashLayer = (LinearLayout) findViewById(R.id.id_dashlayer);
		
		start11TextView = (TextView) findViewById(R.id.id_start11_textview);
		start12TextView = (TextView) findViewById(R.id.id_start12_textview);
		dashLayer11TextView = (TextView) findViewById(R.id.id_dashlayer_start11);
		dashLayer12TextView = (TextView) findViewById(R.id.id_dashlayer_start12);

		// 初始化
		for (int i = 0; i < panels.length; i++) {
			panels[i] = (LinearLayout) this.findViewById(R.id.weekPanel_1 + i);
			data[i] = new ArrayList<>();
		}

	}
	
	/**
	 * 初始化一周的星期的文本背景
	 */
	public void initColor() {
		week1.setBackgroundColor(getResources().getColor(R.color.app_white2));
		week2.setBackgroundColor(getResources().getColor(R.color.app_white2));
		week3.setBackgroundColor(getResources().getColor(R.color.app_white2));
		week4.setBackgroundColor(getResources().getColor(R.color.app_white2));
		week5.setBackgroundColor(getResources().getColor(R.color.app_white2));
		week6.setBackgroundColor(getResources().getColor(R.color.app_white2));
		week7.setBackgroundColor(getResources().getColor(R.color.app_white2));
	}

	/**
	 * 星期高亮
	 */
	public void weekLightHight() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE");
		initColor();
		switch (sdf.format(new Date())) {
		case "周一":
			week1.setBackgroundColor(getResources().getColor(
					R.color.app_highlight));
			break;
		case "周二":
			week2.setBackgroundColor(getResources().getColor(
					R.color.app_highlight));
			break;
		case "周三":
			week3.setBackgroundColor(getResources().getColor(
					R.color.app_highlight));
			break;
		case "周四":
			week4.setBackgroundColor(getResources().getColor(
					R.color.app_highlight));
			break;
		case "周五":
			week5.setBackgroundColor(getResources().getColor(
					R.color.app_highlight));
			break;
		case "周六":
			week6.setBackgroundColor(getResources().getColor(
					R.color.app_highlight));
			break;
		case "周日":
			week7.setBackgroundColor(getResources().getColor(
					R.color.app_highlight));
			break;

		default:
			break;
		}
	}

	public void notifyDataSourceChanged() {
		showTimetableView();
		onBind();
	}

	private void prepre() {
		if (subjectUIModel == null) {
			subjectUIModel = new SubjectUIModel(context,onSubjectItemClickListener,onSubjectItemLongClickListener);
		}

		initWeekDateInfo();
		weekLightHight();
		setShowDashLayer(isShowDashlayer);
		for(int i=0;i<7;i++){
			data[i].clear();
		}

		checkBottomLayer();
	}

	private void checkBottomLayer(){
		if(bottomLayer!=null){
			setShowDashLayer(true);
			dashLayer.removeAllViews();
			dashLayer.addView(bottomLayer);
		}
	}

	/**
	 * 显示课程
	 */
	public void showTimetableView() {
		prepre();
		if(dataSource==null) return;
		for (int i = 0; i < dataSource.size(); i++) {
			SubjectBean bean = dataSource.get(i);
			if (bean.getDay() != -1)
				data[bean.getDay() - 1].add(bean);
		}
		SubjectUtils.sortList(data);
		
		for (int i = 0; i < panels.length; i++) {
			panels[i].removeAllViews();
			subjectUIModel.addSubjectLayout(panels[i], data[i], curWeek);
		}
	}

	public void changeWeek(int week, boolean isCurWeek) {
		subjectUIModel.changeWeek(panels, data, week);
		if (isCurWeek || week == curWeek) {
			setCurWeek(week);
		}
	}
}

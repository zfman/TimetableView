package com.zhuangfei.timetable.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.R.integer;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * 课程表控件
 * 
 * @author Administrator
 * 
 */
public class TimetableView extends LinearLayout implements OnClickListener {

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

	// 头部View
	private LinearLayout contentLinearLayout;
	private LinearLayout headLayout;
	TextView headText;
	TextView termText;
	// 头部左右布局
	private LinearLayout leftLayout;
	private LinearLayout rightLayout;
	private TextView leftTextView;
	private ImageView rightImageView;
	private String leftText = "工具";

	// 当前周
	private int curWeek = 1;
	private SubjectUIModel subjectUIModel;
	private Context context;
	private String curTerm = "大三 春季学期";

	// 周次选择布局
	LinearLayout chooseLayout;
	LinearLayout containerLayout;
	List<String> list = new ArrayList<>();
	List<LinearLayout> layouts = new ArrayList<>();
	HorizontalScrollView scrollView;

	// 课程数据源
	private List<SubjectBean> dataSource = null;

	LayoutInflater inflater;
	int textColor;

	//虚线层
	private LinearLayout dashLayer;
	private boolean isShowDashlayer = true;
	private boolean isChooseLayoutShowing=false;
	
	// 课表item事件监听器
	private OnSubjectItemClickListener onSubjectItemClickListener;
	private OnSubjectMenuListener onSubjectMenuListener;
	private OnSubjectHeaderListener onSubjectHeaderListener;

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
		initEvents();
	}

	public TimetableView setCurWeek(int curWeek) {
		if(curWeek<1) this.curWeek=1;
		else if(curWeek>20) this.curWeek=20;
		else this.curWeek = curWeek;
		headText.setText("第" + curWeek + "周");
		return this;
	}

	public TimetableView setCurWeek(String startTime) {
		int week = SubjectUtils.timeTransfrom(startTime);
		if (week == -1)
			setCurWeek(1);
		else
			setCurWeek(week);
		return this;
	}

	public int getCurWeek() {
		return curWeek;
	}

	public TimetableView setCurTerm(String curTerm) {
		this.curTerm = curTerm;
		termText.setText(curTerm);
		return this;
	}

	public String getCurTerm() {
		return curTerm;
	}

	public TimetableView setDataSource(List<SubjectBean> dataSource) {
		this.dataSource = dataSource;
		return this;
	}

	public TimetableView setOnSubjectHeaderListener(OnSubjectHeaderListener onSubjectHeaderListener) {
		this.onSubjectHeaderListener = onSubjectHeaderListener;
		return this;
	}
	
	public TimetableView setObjectSource(List<Object> objectSource,
			OnSubjectTransformListener onSubjectTransformListener) {
		if (this.dataSource == null)
			this.dataSource = new ArrayList<>();
		else
			this.dataSource.clear();
		for (int i = 0; i < objectSource.size(); i++)
			this.dataSource.add(onSubjectTransformListener
					.onTransform(objectSource.get(i)));
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

	public TimetableView setOnSubjectMenuClickListener(
			OnSubjectMenuListener onSubjectMenuListener) {
		this.onSubjectMenuListener = onSubjectMenuListener;
		return this;
	}

	public TimetableView setLeftText(String leftText) {
		this.leftText = leftText;
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

		contentLinearLayout=(LinearLayout) findViewById(R.id.id_content_linearlayout);
		headLayout = (LinearLayout) findViewById(R.id.id_course_headlayout);
		headText = (TextView) findViewById(R.id.id_course_head_text);
		termText = (TextView) findViewById(R.id.id_course_head_term);

		// containerLayout：选择周次的滚动布局的子元素，也是周次的列表项的父元素
		// chooseLayout：选择周次的布局
		// scrollView：滚动布局
		containerLayout = (LinearLayout) findViewById(R.id.id_container);
		chooseLayout = (LinearLayout) findViewById(R.id.id_maintab_chooseweek_layout);
		scrollView = (HorizontalScrollView) findViewById(R.id.id_maintab_scrollview);

		dashLayer = (LinearLayout) findViewById(R.id.id_dashlayer);
		leftLayout = (LinearLayout) findViewById(R.id.id_head_left_layout);
		rightLayout = (LinearLayout) findViewById(R.id.id_head_right_layout);
		leftTextView = (TextView) findViewById(R.id.id_head_left_textview);
		rightImageView = (ImageView) findViewById(R.id.id_head_right_imageview);
		
		start11TextView = (TextView) findViewById(R.id.id_start11_textview);
		start12TextView = (TextView) findViewById(R.id.id_start12_textview);
		dashLayer11TextView = (TextView) findViewById(R.id.id_dashlayer_start11);
		dashLayer12TextView = (TextView) findViewById(R.id.id_dashlayer_start12);

		// 初始化
		for (int i = 0; i < panels.length; i++) {
			panels[i] = (LinearLayout) this.findViewById(R.id.weekPanel_1 + i);
			data[i] = new ArrayList<>();
		}

		// 初始化周次数据集合
		for (int i = 1; i <= 20; i++)
			list.add("第" + i + "周");
	}

	public void initEvents() {
		headLayout.setOnClickListener(this);
		leftLayout.setOnClickListener(this);
		rightLayout.setOnClickListener(this);
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

	/**
	 * 创建选择周次布局
	 */
	@SuppressLint("NewApi")
	private void createWeekLayout() {
		if (containerLayout != null)
			containerLayout.removeAllViews();
		layouts.clear();
		for (int i = 0; i < list.size(); i++) {
			final int temp = i;
			View view = inflater.inflate(R.layout.chooseweek_item_layout, null,
					false);
			TextView week = (TextView) view
					.findViewById(R.id.id_choose_week_item_week);
			final LinearLayout choose = (LinearLayout) view
					.findViewById(R.id.id_choose_week_layout);
			layouts.add(choose);
			week.setText(list.get(i));
			if (curWeek == (i + 1))
				choose.setBackground(getResources().getDrawable(
						R.drawable.week_oval_press_style));
			choose.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					initWeekLayout();
					choose.setBackground(getResources().getDrawable(
							R.drawable.week_oval_press_style));

					scrollToTarget(temp + 1, true);
					changeWeek(temp + 1, false);
				}
			});
			containerLayout.addView(view);
		}
	}

	public void scrollToTarget(final int i, boolean isSmooth) {
		if (isSmooth)
			scrollView.post(new Runnable() {
				@Override
				public void run() {
					scrollView.smoothScrollTo(OtherUtils.dip2px(context, 70) * (i - 1)
							- OtherUtils.dip2px(context, 140), 0);
				}
			});
			
		else
			scrollView.post(new Runnable() {
				@Override
				public void run() {
					scrollView.scrollTo(OtherUtils.dip2px(context, 70) * (i - 1)
							- OtherUtils.dip2px(context, 140), 0);
				}
			});
			
	}

	/**
	 * 初始化选择周次的列表项，设为普通颜色
	 */
	@SuppressLint("NewApi")
	protected void initWeekLayout() {
		for (int i = 0; i < containerLayout.getChildCount(); i++) {
			View view = containerLayout.getChildAt(i);
			LinearLayout choose = (LinearLayout) view
					.findViewById(R.id.id_choose_week_layout);
			choose.setBackground(getResources().getDrawable(
					R.drawable.week_oval_style));
		}
	}

	public void notifyDataSourceChanged() {
		for (int i = 0; i < data.length; i++)
			data[i].clear();
		showSubjectView();
	}

	private void prepre() {
		if (subjectUIModel == null) {
			subjectUIModel = new SubjectUIModel(context,
					onSubjectItemClickListener);
		}

		initWeekDateInfo();
		weekLightHight();
		createWeekLayout();
		setShowDashLayer(isShowDashlayer);
		setCurWeek(curWeek);
		setCurTerm(curTerm);
		leftTextView.setText(leftText);
		for(int i=0;i<7;i++){
			data[i].clear();
		}
	}

	/**
	 * 显示课程
	 */
	public void showSubjectView() {
		prepre();
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
		// 初始化
//		for (int i = 0; i < panels.length; i++) {
//			data[i] = new ArrayList<>();
//		}
	}

	public void changeWeek(int week, boolean isCurWeek) {
		subjectUIModel.changeWeek(panels, data, week);
		if (isCurWeek || week == curWeek) {
			setCurWeek(week);
			headText.setTextColor(textColor);
			termText.setTextColor(textColor);
		} else {
			headText.setText("第" + week + "周");
			headText.setTextColor(Color.RED);
			termText.setTextColor(Color.RED);
		}

	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		if (id == R.id.id_course_headlayout) {
			
			if (isChooseLayoutShowing) {
				if(onSubjectHeaderListener!=null) onSubjectHeaderListener.onCloseHeader();
				ObjectAnimator
				.ofFloat(contentLinearLayout, "translationY", OtherUtils.dip2px(context, 50),0)
				.setDuration(400).start();
				isChooseLayoutShowing=false;
				setCurWeek(curWeek);
				changeWeek(curWeek, true);
				
			} else {
				if(onSubjectHeaderListener!=null) onSubjectHeaderListener.onShowHeader();
				ObjectAnimator
				.ofFloat(contentLinearLayout, "translationY", 0,OtherUtils.dip2px(context, 50))
				.setDuration(400).start();
				isChooseLayoutShowing=true;
				initWeekLayout();
				if (curWeek <= layouts.size()) {
					LinearLayout choose = layouts.get(curWeek - 1);
					choose.setBackground(context.getResources().getDrawable(
							R.drawable.week_oval_press_style));
				}
				
				scrollToTarget(curWeek, false);
				
			}
		}

		if (id == R.id.id_head_left_layout) {
			if (onSubjectMenuListener != null)
				onSubjectMenuListener.onLeftClick(arg0);
		}

		if (id == R.id.id_head_right_layout) {
			if (onSubjectMenuListener != null)
				onSubjectMenuListener.onRightClick(arg0);
		}
	}
}

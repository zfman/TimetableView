package com.zhuangfei.timetable.model;

import java.util.List;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zhuangfei.android_timetableview.sample.R;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.OnItemBuildAdapter;
import com.zhuangfei.timetable.listener.OnItemClickAdapter;
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter;

/**
 * 对课程的功能进行维护
 */
public class ScheduleManager {

	private static final String TAG = "ScheduleManager";
	//上下文
	private Context context;

	//边距、高度
	private int marTop;
	private int marLeft;
	private int itemHeight;

	//本周、非本周的弧度
	private int thisWeekCorner;
	private int nonThisWeekCorner;

	private int width,height;
	private int left,right,top,bottom;

	//侧边项的最大个数
	private int maxSlideItem=12;

	// 课程表item点击监听、构建监听
    private ISchedule.OnItemClickListener onItemClickListener;
    private ISchedule.OnItemBuildListener onItemBuildListener;

    //侧边栏构建监听
    private ISchedule.OnSlideBuildListener onSlideBuildListener;

	//布局转换器
	private LayoutInflater inflater;

	//颜色池
	private ScheduleColorPool colorPool;

	//是否显示非本周课程
	private boolean isShowNotCurWeek=true;

	public ScheduleManager(Context context) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	/**
	 * 设置最大节次
	 * @param maxSlideItem 最大节次
	 * @return
	 */
	public ScheduleManager setMaxSlideItem(int maxSlideItem) {
		this.maxSlideItem = maxSlideItem;
		return this;
	}

	/**
	 * 获取最大节次
	 * @return 最大节次
	 */
	public int getMaxSlideItem() {
		return maxSlideItem;
	}

	/**
	 * 获取Item构建监听器
	 * @return
	 */
	public ISchedule.OnItemBuildListener getOnItemBuildListener() {
		if(onItemBuildListener==null) onItemBuildListener=new OnItemBuildAdapter();
		return onItemBuildListener;
	}

	/**
	 * 获取Item点击监听
	 * @return
	 */
	public ISchedule.OnItemClickListener getOnItemClickListener() {
		if(onItemClickListener==null) onItemClickListener=new OnItemClickAdapter();
		return onItemClickListener;
	}

	/**
	 * 设置本周课程的弧度
	 * @param thisWeekCorner 弧度
	 * @return
	 */
	public ScheduleManager setThisWeekCorner(int thisWeekCorner) {
		this.thisWeekCorner=thisWeekCorner;
		return this;
	}

	/**
	 * 获取本周课程的弧度
	 * @return
	 */
	public int getThisWeekCorner() {
		return thisWeekCorner;
	}

	/**
	 * 设置非本周课程的弧度
	 * @param nonThisWeekCorner 弧度
	 * @return
	 */
	public ScheduleManager setNonThisWeekCorner(int nonThisWeekCorner) {
		this.nonThisWeekCorner = nonThisWeekCorner;
		return this;
	}

	/**
	 * 设置本周、非本周相同的弧度
	 * @param corner 弧度
	 * @return
	 */
	public ScheduleManager setCorner(int corner){
		setThisWeekCorner(corner);
		setNonThisWeekCorner(corner);
		return this;
	}

	/**
	 * 获取非本周课程的弧度
	 * @return
	 */
	public int getNonThisWeekCorner() {
		return nonThisWeekCorner;
	}

	/**
	 * 设置是否显示非本周课程
	 * @param showNotCurWeek 如果为true，将显示非本周，否则隐藏非本周
	 * @return
	 */
	public ScheduleManager setShowNotCurWeek(boolean showNotCurWeek) {
		isShowNotCurWeek = showNotCurWeek;
		return this;
	}

	/**
	 * 获取侧边栏构建监听
	 * @return
	 */
	public ISchedule.OnSlideBuildListener getOnSlideBuildListener() {
		if(onSlideBuildListener==null) onSlideBuildListener=new OnSlideBuildAdapter();
		return onSlideBuildListener;
	}

	/**
	 * 设置侧边栏构建监听器
	 * @param onSlideBuildListener
	 * @return
	 */
	public ScheduleManager setOnSlideBuildListener(ISchedule.OnSlideBuildListener onSlideBuildListener) {
		this.onSlideBuildListener = onSlideBuildListener;
		return this;
	}

	/**
	 * 判断是否显示非本周课程
	 * @return true：显示，false：不显示
	 */
	public boolean isShowNotCurWeek() {
		return isShowNotCurWeek;
	}

	/**
	 * 获取颜色池
	 * @see ScheduleColorPool
	 * @return ScheduleColorPool
	 */
	public ScheduleColorPool getColorPool() {
		if(colorPool==null) colorPool=new ScheduleColorPool(context);
		return colorPool;
	}

	/**
	 * dp->px
	 * @param dp
	 * @return
	 */
	public int getPx(int dp){
		return context.getResources().getDimensionPixelSize(dp);
	}

	/**
	 * 设置课程项构建监听器
	 * @param onItemBuildListener
	 * @return
	 */
	public ScheduleManager setOnItemBuildListener(ISchedule.OnItemBuildListener onItemBuildListener) {
		this.onItemBuildListener = onItemBuildListener;
		return this;
	}

	/**
	 * 设置Item点击监听器
	 * @param onItemClickListener
	 * @return
	 */
	public ScheduleManager setOnItemClickListener(ISchedule.OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
		return this;
	}

	/**
	 * 设置上边距值
	 * @param marTopPx
	 * @return
	 */
	public ScheduleManager setMarTop(int marTopPx) {
		this.marTop = marTopPx;
		return this;
	}

	/**
	 * 设置左边距值
	 * @param marLeftPx
	 * @return
	 */
	public ScheduleManager setMarLeft(int marLeftPx) {
		this.marLeft = marLeftPx;
		return this;
	}

	/**
	 * 设置课程项的高度
	 * @param itemHeightPx
	 * @return
	 */
	public ScheduleManager setItemHeight(int itemHeightPx) {
		this.itemHeight = itemHeightPx;
		return this;
	}

	/**
	 * 获取课程项的高度
	 * @return
	 */
	public int getItemHeight() {
		return itemHeight;
	}

	/**
	 * 获取左边距
	 * @return
	 */
	public int getMarLeft() {
		return marLeft;
	}

	/**
	 * 获取上边距
	 * @return
	 */
	public int getMarTop() {
		return marTop;
	}

	/**
	 * 构建侧边栏
	 * @param weekPanel0 侧边栏的容器
	 */
	public void newSlideView(LinearLayout weekPanel0){
		if(weekPanel0==null) return;
		weekPanel0.removeAllViews();

		int size=getOnSlideBuildListener().getSlideItemSize();
		int border=Math.min(size,getMaxSlideItem());
		getOnSlideBuildListener().setBackground(weekPanel0);
		for(int i=0;i<border;i++){
			View view=getOnSlideBuildListener().onBuildSlideItem(i,inflater,itemHeight,marTop);
			weekPanel0.addView(view);
		}
	}

	/**
	 * 构建课程项
	 * @param data 某一天的数据集合
	 * @param subject 当前的课程数据
	 * @param pre 上一个课程数据
	 * @param i 构建的索引
	 * @param curWeek 当前周
	 * @return View
	 */
	public View newItemView(final List<Schedule> data,final Schedule subject, Schedule pre, int i, int curWeek){
		//宽高
		width=LinearLayout.LayoutParams.MATCH_PARENT;
		height=itemHeight*subject.getStep()+marTop * (subject.getStep() - 1);

		//边距
		left=marLeft / 2;
		right=marLeft/2;
		bottom=0;
		top=(subject.getStart() - (pre.getStart() + pre.getStep())) * (itemHeight + marTop) + marTop;;

		if(i!=0&&top<0) return null;

		// 设置Params
		View view = inflater.inflate(R.layout.item_timetable, null, false);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);
		if (i == 0){
			top=(subject.getStart() - 1) * (itemHeight + marTop) + marTop;
		}
		lp.setMargins(left,top,right,bottom);

		view.setTag(subject);
		FrameLayout layout = (FrameLayout) view.findViewById(R.id.id_course_item_framelayout);
		TextView textView = (TextView) view.findViewById(R.id.id_course_item_course);
		layout.setLayoutParams(lp);

		boolean isThisWeek=ScheduleSupport.isThisWeek(subject,curWeek);
		textView.setText(getOnItemBuildListener().getItemText(subject,isThisWeek));

		GradientDrawable gd = new GradientDrawable();
		if (isThisWeek) {
			gd.setColor(getColorPool().getColorAuto(subject.getColorRandom()));
			gd.setCornerRadius(thisWeekCorner);
		}else{
			gd.setColor(getColorPool().getUselessColor());
			gd.setCornerRadius(nonThisWeekCorner);
		}
		textView.setBackgroundDrawable(gd);

		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<Schedule> result = ScheduleSupport.findSubjects(subject, data);
				getOnItemClickListener().onItemClick(v,result);
			}
		});

		boolean intercept=getOnItemBuildListener().interceptItemBuild(subject);
		return intercept==false?view:null;
	}

	/**
	 * 将数据data添加到layout的布局上
	 * @param layout 容器
	 * @param data 某一天的数据集合
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
				View view=newItemView(data,subject,pre,i,curWeek);
				if(view!=null){
					layout.addView(view);
					pre = subject;
				}
			}
		}
	}

	/**
	 * 简单过滤课程
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
	 * @param courses
	 * @param view
	 * @param preView
	 * @param isChange
	 * @param j
	 * @param curWeek
	 * @return
	 */
	public boolean updateItemView(final List<Schedule> courses,View view,View preView,boolean isChange,int j, int curWeek){
		FrameLayout layout = (FrameLayout) view.findViewById(R.id.id_course_item_framelayout);
		Schedule tag = (Schedule) view.getTag();
		Schedule subject = ScheduleSupport.findRealSubject(tag.getStart(), curWeek, courses);

		boolean isThisWeek=ScheduleSupport.isThisWeek(subject,curWeek);
		if(!isThisWeek&&!isShowNotCurWeek()){
			view.setVisibility(View.INVISIBLE);
		}else{
			view.setVisibility(View.VISIBLE);
		}

		width=LinearLayout.LayoutParams.MATCH_PARENT;
		height=itemHeight* subject.getStep() + marTop * (subject.getStep() - 1);
		left=marLeft/2;
		right=left;

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);
		if (isChange||tag.getStart() != subject.getStart() || tag.getStep() != subject.getStep()) {
			if (j > 0) {
				Schedule pre=(Schedule) preView.getTag();
				top=(subject.getStart() - (pre.getStart() + pre.getStep())) * (itemHeight
						+ marTop)+ marTop;
			} else {
				top=(subject.getStart() - 1) * (itemHeight + marTop) + marTop;
			}
			lp.setMargins(left, top, right, 0);
			layout.setLayoutParams(lp);
			view.setTag(subject);
			isChange=true;
		}

		TextView textView = (TextView) view.findViewById(R.id.id_course_item_course);
		TextView countTextView = (TextView) view.findViewById(R.id.id_course_item_count);
		textView.setText(getOnItemBuildListener().getItemText(subject,isThisWeek));

		countTextView.setText("");
		countTextView.setVisibility(View.GONE);

		GradientDrawable gd = new GradientDrawable();
		if (isThisWeek) {
			gd.setColor(getColorPool().getColorAuto(subject.getColorRandom()));
			gd.setCornerRadius(thisWeekCorner);

			int count=0;
			List<Schedule> result = ScheduleSupport.findSubjects(subject, courses);
			for(Schedule bean:result){
				if(ScheduleSupport.isThisWeek(bean,curWeek)){
					count++;
				}
			}
			if (count > 1) {
				countTextView.setVisibility(View.VISIBLE);
				countTextView.setText(count + "");
			}
		}else{
			gd.setColor(getColorPool().getUselessColor());
			gd.setCornerRadius(nonThisWeekCorner);
		}

		textView.setBackgroundDrawable(gd);
		getOnItemBuildListener().onItemUpdate(layout,textView,countTextView,subject,gd);
		return isChange;
	}


	/**
	 * 周次切换
	 */
	public void changeWeek(LinearLayout[] panels, List<Schedule>[] data, int curWeek) {
		for (int i = 0; i < panels.length; i++) {
			List<Schedule> courses = data[i];
			boolean isChange=false;
			for (int j = 0; j < panels[i].getChildCount(); j++) {
				View view = panels[i].getChildAt(j);
				View preView;
				if(j>0) preView=panels[i].getChildAt(j-1);
				else preView=null;
				isChange=updateItemView(courses,view,preView,isChange,j,curWeek);
			}
		}
	}
}

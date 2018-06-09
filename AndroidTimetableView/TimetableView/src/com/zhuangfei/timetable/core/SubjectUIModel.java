package com.zhuangfei.timetable.core;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.sample.R;

/**
 * Subject UI的维护
 * 
 * @author Administrator
 * 
 */
public class SubjectUIModel {

	Context context;

	// 课表的每项距离上一项的距离
	private static int marTop;

	// 课表的每项距离左侧的距离
	private static int marLeft;

	// 课表每项的高度
	private static int itemHeight;

	private LayoutInflater inflater;

	// 课程表item点击监听
	private OnSubjectItemClickListener onSubjectItemClickListener;
	private OnSubjectItemLongClickListener onSubjectItemLongClickListener;

	public SubjectUIModel(Context context, OnSubjectItemClickListener onSubjectItemClickListener,OnSubjectItemLongClickListener onSubjectItemLongClickListener) {
		this.itemHeight = context.getResources().getDimensionPixelSize(R.dimen.weekItemHeight);
		this.marTop = context.getResources().getDimensionPixelSize(R.dimen.weekItemMarTop);
		this.marLeft = context.getResources().getDimensionPixelSize(R.dimen.weekItemMarLeft);
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.onSubjectItemClickListener = onSubjectItemClickListener;
		this.onSubjectItemLongClickListener=onSubjectItemLongClickListener;
	}

	/**
	 * 将数据data添加到layout1的布局上
	 * 
	 * @param layout1
	 * @param data
	 * @param curWeek
	 */
	public void addSubjectLayout(LinearLayout layout1, final List<SubjectBean> data, int curWeek) {
		if (layout1 == null || data == null || data.size() < 1)
			return;
		layout1.removeAllViews();

		SubjectBean pre = data.get(0);
		// int[] arr=new int[12];
		// for(int i=0;i<arr.length;i++) arr[i]=-1;

		for (int i = 0; i < data.size(); i++) {
			final SubjectBean subject = data.get(i);
			boolean isIgnore = filterSubject(subject, pre, i, curWeek);
			if (!isIgnore) {
				final View view = inflater.inflate(R.layout.timetable_item_layout, null, false);

				TextView textView = createSubjectView(view, subject, pre, i);

				textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						List<SubjectBean> result = SubjectUtils.findSubjects(subject, data);
						doClick(v, result);
					}
				});

				textView.setOnLongClickListener(new OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						
						doLongClick(view, subject);
						return true;
					}
				});
				if (!SubjectUtils.isThisWeek(subject, curWeek)) {
					textView.setText(subject.getName());
					textView.setBackgroundResource(R.drawable.item_corner_style_useless);
				}

				int top = (subject.getStart() - (pre.getStart() + pre.getStep())) * (itemHeight + marTop) + marTop;

				// 添加布局
				if (i == 0 || top >= 0) {
					layout1.addView(view);
					pre = subject;
				}

			}

		}
	}

	private void doClick(View view,List<SubjectBean> result){
		if (onSubjectItemClickListener != null)
			onSubjectItemClickListener.onItemClick(view, result);
	}
	
	private void doLongClick(View view,SubjectBean bean){
		
		if (onSubjectItemLongClickListener != null)
			onSubjectItemLongClickListener.onItemLongClick(view, bean.getDay(),bean.getStart());
	}
	
	private TextView createSubjectView(View view, SubjectBean subject, SubjectBean pre, int i) {
		// 设置距离
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, itemHeight
				* subject.getStep() + marTop * (subject.getStep() - 1));
		if (i > 0) {
			lp.setMargins(marLeft / 2, (subject.getStart() - (pre.getStart() + pre.getStep())) * (itemHeight + marTop) + marTop,
					marLeft / 2, 0);
		} else {
			lp.setMargins(marLeft / 2, (subject.getStart() - 1) * (itemHeight + marTop) + marTop, marLeft / 2, 0);
		}

		// 新建一个课程的布局，设置颜色、添加监听
		view.setTag(subject);
		FrameLayout layout = (FrameLayout) view.findViewById(R.id.id_course_item_framelayout);
		TextView textView = (TextView) view.findViewById(R.id.id_course_item_course);
		layout.setLayoutParams(lp);
//		layout.setAlpha(0.8f);
		textView.setText(subject.getName() + "@" + subject.getRoom());
		textView.setBackgroundResource(getBackgroundByRandom(subject.getColorRandom()));
		textView.setClickable(true);
		return textView;
	}

	private boolean filterSubject(SubjectBean subject, SubjectBean pre, int index, int curWeek) {
		if (index != 0 && SubjectUtils.isThisWeek(subject, curWeek)) {
			if (SubjectUtils.isThisWeek(pre, curWeek)) {
				if (subject.getStart() == pre.getStart()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 根据随机数获取到其颜色值
	 * 
	 * @param random
	 * @return
	 */
	public static int getBackgroundByRandom(int random) {
		int result = R.drawable.item_corner_style11;
		switch (random) {
		case 1:
			result = R.drawable.item_corner_style1;
			break;
		case 2:
			result = R.drawable.item_corner_style2;
			break;
		case 3:
			result = R.drawable.item_corner_style3;
			break;
		case 4:
			result = R.drawable.item_corner_style4;
			break;
		case 5:
			result = R.drawable.item_corner_style5;
			break;
		case 6:
			result = R.drawable.item_corner_style6;
			break;
		case 7:
			result = R.drawable.item_corner_style7;
			break;
		case 8:
			result = R.drawable.item_corner_style8;
			break;
		case 9:
			result = R.drawable.item_corner_style9;
			break;
		case 10:
			result = R.drawable.item_corner_style10;
			break;
		case 11:
			result = R.drawable.item_corner_style11;
			break;

		case 12:
			result = R.drawable.item_corner_style30;
			break;
		case 13:
			result = R.drawable.item_corner_style31;
			break;
		case 14:
			result = R.drawable.item_corner_style32;
			break;
		case 15:
			result = R.drawable.item_corner_style33;
			break;
		case 16:
			result = R.drawable.item_corner_style34;
			break;
		case 17:
			result = R.drawable.item_corner_style35;
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * 切换周次，使用该方法只修改课程的背景色，不用removeAllView， 效率可以大大提高
	 *
	 * v1.0.3时完善此方法，解决角标计算错误的问题
	 */
	public static void changeWeek(LinearLayout[] panels, List<SubjectBean>[] data, int curWeek) {
		for (int i = 0; i < panels.length; i++) {
			List<SubjectBean> courses = data[i];
			boolean isChange=false;
			for (int j = 0; j < panels[i].getChildCount(); j++) {
				View view = panels[i].getChildAt(j);
				FrameLayout layout = (FrameLayout) view.findViewById(R.id.id_course_item_framelayout);
				SubjectBean tag = (SubjectBean) view.getTag();
				SubjectBean subject = SubjectUtils.findRealSubject(tag.getStart(), curWeek, courses);
				
				if (subject == null)
					continue;
				
				
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, itemHeight
						* subject.getStep() + marTop * (subject.getStep() - 1));
				
				if (isChange||tag.getStart() != subject.getStart() || tag.getStep() != subject.getStep()) {
					if (j > 0) {
						SubjectBean pre=(SubjectBean) panels[i].getChildAt(j-1).getTag();
						lp.setMargins(marLeft / 2, (subject.getStart() - (pre.getStart() + pre.getStep())) * (itemHeight + marTop)
								+ marTop, marLeft / 2, 0);
					} else {
						lp.setMargins(marLeft / 2, (subject.getStart() - 1) * (itemHeight + marTop) + marTop, marLeft / 2, 0);
					}
					layout.setLayoutParams(lp);
					view.setTag(subject);
					isChange=true;
				}
				TextView textView = (TextView) view.findViewById(R.id.id_course_item_course);
				TextView countTextView = (TextView) view.findViewById(R.id.id_course_item_count);
				textView.setBackgroundResource(getBackgroundByRandom(subject.getColorRandom()));
				textView.setText(subject.getName() + "@" + subject.getRoom());
				countTextView.setText("");
				countTextView.setVisibility(View.GONE);
				if (!SubjectUtils.isThisWeek(subject, curWeek)) {
					textView.setText(subject.getName());
					textView.setBackgroundResource(R.drawable.item_corner_style_useless);
				} else {
					int count=0;
					List<SubjectBean> result = SubjectUtils.findSubjects(subject, courses);
					for(SubjectBean bean:result){
						if(SubjectUtils.isThisWeek(bean,curWeek)){
							count++;
						}
					}
					if (count > 1) {
						countTextView.setVisibility(View.VISIBLE);
						countTextView.setText(count + "");
					}
				}
			}
		}
	}
}

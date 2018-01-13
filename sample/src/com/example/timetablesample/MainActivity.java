package com.example.timetablesample;

import java.util.List;

import com.zhuangfei.timetable.core.OnSubjectItemClickListener;
import com.zhuangfei.timetable.core.OnSubjectMenuListener;
import com.zhuangfei.timetable.core.OnSubjectTransformListener;
import com.zhuangfei.timetable.core.SubjectBean;
import com.zhuangfei.timetable.core.TimetableView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements OnSubjectItemClickListener,OnSubjectMenuListener{

	private TimetableView timetableView;
	private List<SubjectBean> subjectList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initDatas();
		initViews();
	}


	private void initViews() {
		timetableView=(TimetableView) findViewById(R.id.id_timetableView);
		
		//完整设置，仅供参考
//		timetableView.setDataSource(subjectList)
//		.setObjectSource(objectSource, onSubjectTransformListener)
//		.setMax(true)//是否启用最大节次（12节），默认为10节
//		.setShowDashLayer(true)//是否显示虚线层
//		.setCurWeek(2)//设置当前周
//		.setCurTerm("新学期")//设置当前学期
//		.setOnSubjectItemClickListener(this)//设置课表项点击监听器
//		.setOnSubjectMenuClickListener(this)//设置头部两侧按钮点击监听器
//		.setLeftText("发现")//设置左侧按钮的文本
//		.setRightImage(R.drawable.ic_launcher)//设置右侧按钮的图标
//		.showSubjectView();//显示视图
		
		//设置相关属性
		timetableView.setDataSource(subjectList)
		.setOnSubjectItemClickListener(this)//设置课表项点击监听器
		.setOnSubjectMenuClickListener(this)//设置头部两侧按钮点击监听器
		.setLeftText("发现")//设置左侧按钮的文本
		.showSubjectView();//显示视图
		
		timetableView.changeWeek(3,true);
	}
	
	private void initDatas() {
		subjectList=DataModel.getExampleSubjectSource();
		
		//List<SubjectBean>可以作为timetableView的数据源
		//但是我们获取到的数据可能并不是这个格式的
		//timetableView还提供了将任何类型的List集合转化为数据源的方法
		//1.实现OnSubjectTransformListener<T>接口
		//2.实现onTransform(T obj)方法
		//3.使用setObjectSource(objectSource, onSubjectTransformListener)设置数据源
	}

	@Override
	public void onItemClick(View v, List<SubjectBean> subjectList) {
		String string="";
		for(int i=0;i<subjectList.size();i++){
			string+=subjectList.get(i).getName()+" ";
		}
		Toast.makeText(this, string,Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onLeftClick(View v) {
		Toast.makeText(this, "点击了左侧Menu",Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onRightClick(View v) {
		Toast.makeText(this, "点击了右侧Menu",Toast.LENGTH_SHORT).show();
	}

}

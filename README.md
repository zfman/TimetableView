# TimetableView

一个非常漂亮的Android课程表控件，该控件支持的功能：

- 设置数据源

- 颜色设置，控件内置17种颜色

- 触感反馈，那种内置颜色都有对应的三个样式

- 日期显示与高亮

- 课表Item点击事件处理

- 解决课程重叠、交叉的问题，解决的效果同超级课程表

- 高效的切换周次


### 运行效果
![课表](https://raw.githubusercontent.com/zfman/TimetableView/master/extras/image/img1.png)

### Demo下载
[下载Demo App](https://raw.githubusercontent.com/zfman/TimetableView/master/extras/TimetableSample.apk)

### ChangeLog

- 2018/1/27 删除头部与周次选择；完善demo

### 简单使用

第一步：添加项目依赖

- 将本项目下载到本地上，解压会有两个子文件夹

>TimetableView：TimetableView项目源码

>TimetableSample：本项目的一个Demo

- 把`TimetableView`作为项目导入到`Eclipse`中，并将项目设置为`libary`

- 将`TimetableView`添加为自己的项目的`libary`

第二步：引入`TimetableView`控件
```xml
    <!-- XML CODE -->
    <com.zhuangfei.timetable.core.TimetableView 
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/id_timetableView"
        android:orientation="vertical">
    </com.zhuangfei.timetable.core.TimetableView>
```

第三步：初始化控件、设置

```java
mTimetableView=(TimetableView) findViewById(R.id.id_timetableView);
mTimetableView.setDataSource(subjectBeans)
	.setCurTerm("大三上学期")
	.setCurWeek(curWeek)
	.setOnSubjectItemClickListener(this)
	.showTimetableView();
		
//调用过showSubjectView后需要调用changWeek()
//第二个参数为true时在改变课表布局的同时也会将第一个参数设置为当前周
//第二个参数为false时只改变课表布局
mTimetableView.changeWeek(curWeek, true);

```

大功告成！简单吧!

### 高级使用

先看下`timetableView`可以设置哪些属性

```java
mTimetableView.setDataSource(List<SubjectBean>)
	.setCurTerm(String)//设置学期
	.setCurWeek(int)//设置当前周
	.setMax(boolean)//是否启用最大节次（12节）
	.setShowDashLayer(boolean)//是否显示虚线层，默认显示
	.bindTitleView(TextView)//绑定一个TextView当数据变化时同时更新该文本
	.setOnSubjectBindViewListener(OnSubjectBindViewListener)//指定一个在数据变化时更新文本的规则
	.setOnSubjectItemClickListener(OnSubjectItemClickListener)//指定一个item被点击的事件处理方式
	.showTimetableView();//显示视图
					  
```

**动态更新课表**

不管删除还是添加，只要更改数据源，最后要调用notifyDataSourceChanged()来通知UI界面同步即可。
```java
//添加课程
protected void addSubject() {
	int pos=(int)(Math.random()*subjectBeans.size());
	subjectBeans.add(subjectBeans.get(pos));
	mTimetableView.notifyDataSourceChanged();
}
```

**切换周次**
当数据源发生的变化不大时，切换周次的效率非常高，当数据源发生的变化很大时，该算法与清空布局重建的效率相当，该算法只是尽可能的不去清空布局。
```java
//第二个参数为：是否强制将第一个参数设置为当前周
timetableView.changeWeek(2,true);
```

### 注意的地方

1.在调用`showTimetableView()`后需要调用一次`changeWeek()`，因为我在`showTimetableView()`里没有处理课程重叠的问题，当课程重叠或者有交叉且该课程在本周上时，会在课程的右上方义小红点+数字的形式提示。

2.红点的出现时机：在同一时刻且在本周有课的课程数大于等于2时

### 最后

各个接口、方法的详细用法在TimetableSample项目中，你可以参考那个demo


# v2.0.2文档
> 本文中的功能介绍、控件用法均针对`v2.0.2`以其以上,你可以在 [ChangeLog](https://github.com/zfman/TimetableView/wiki/版本说明) 查看本控件的开发进展，作者联系方式`1193600556@qq.com`

[TimetableView](https://github.com/zfman/TimetableView)是一款开源的、完善、高效的Android课程表控件。高效在哪？周次切换高效、样式文件极少（只需1个，`v1.x`需要36个只能实现12种样式）

- 支持xml设置属性
- 丰富的课程工具包
- 支持多种自定义
- 课程颜色管理
- 调用简洁、性能高效
- ScrollView可替换
- 可设置背景以及透明度
- 数据源可添加额外信息

<img src="https://raw.githubusercontent.com/zfman/TimetableView/master/images/v2.x/v2img1.jpg" width="30%"/><img src="https://raw.githubusercontent.com/zfman/TimetableView/master/images/v2.x/v2img2.jpg" width="30%"/><img src="https://raw.githubusercontent.com/zfman/TimetableView/master/images/v2.x/v2img3.jpg" width="30%"/>

- [准备数据源](#准备数据源)
- [基础功能](#基础功能)
    - [添加依赖](#添加依赖)
    - [添加控件](#添加控件)
    - [配置属性](#配置属性)
    - [数据源设置](#数据源设置)
    - [删除课程](#删除课程)
    - [添加课程](#添加课程)
    - [非本周课程显示与隐藏](#非本周课程显示与隐藏)
    - [最大节次设置](#最大节次设置)
    - [节次时间显示与隐藏](#节次时间显示与隐藏)
    - [WeekView显示与隐藏](#WeekView显示与隐藏)
    - [小结](#小结)
- [周次选择栏](#周次选择栏)
    - [自定义属性](#自定义属性)
    - [默认的周次选择栏](#默认的周次选择栏)
    - [自定义周次选择栏](#自定义周次选择栏)
- [自定义属性](#自定义属性)
    - [属性列表](#属性列表)
    - [属性设置](#属性设置)
    - [显示视图](#显示视图)
- [日期栏](#日期栏)
    - [日期栏显示与隐藏](#日期栏显示与隐藏)
    - [自定义日期栏](#自定义日期栏)
    - [恢复默认日期栏](#恢复默认日期栏)
- [侧边栏](#侧边栏)
    - [准备](#准备)
    - [节次时间显示与隐藏](#节次时间显示与隐藏)
    - [修改侧边栏背景](#修改侧边栏背景)
    - [修改节次文本颜色](#修改节次文本颜色)
    - [修改时间文本颜色](#修改时间文本颜色)
    - [自定义侧边栏](#自定义侧边栏)
    - [侧边栏效果重置](#侧边栏效果重置)
- [课程项样式](#课程项样式)
    - [非本周课程显示与隐藏](#非本周课程显示与隐藏)
    - [设置间距以及弧度](#设置间距以及弧度)
    - [设置单个角弧度](#设置单个角弧度)
    - [修改显示的文本](#修改显示的文本)
    - [设置非本周课的背景](#设置非本周课的背景)
    - [课程重叠的样式](#课程重叠的样式)
- [颜色池](#颜色池)
    - [获取颜色池](#获取颜色池)
    - [指定颜色](#指定颜色)
    - [重置颜色池](#重置颜色池)
    - [追加颜色](#追加颜色)
    - [设置非本周课程颜色](#设置非本周课程颜色)
- [替换滚动布局](#替换滚动布局)
    - [自定义View](#自定义View)
    - [布局文件](#布局文件)
    - [设置监听](#设置监听)
- [工具类](#工具类)
    - [列表与适配器](#列表与适配器)
    - [显示所有课程](#显示所有课程)
    - [第一周有课的课程](#第一周有课的课程)
    - [周一有课的课程](#周一有课的课程)
- [额外的信息](#额外的信息)
    - [存入额外数据](#存入额外数据)
    - [读出额外数据](#读出额外数据)
- [旗标布局](#旗标布局)
    - [事件监听](#事件监听)
    - [背景修改与重置](#背景修改与重置)
    - [开启与关闭](#开启与关闭)
    - [显示与隐藏](#显示与隐藏)

## 准备数据源
> 在开始使用控件之前，你需要准备好数据源，数据可以从网上获取或者使用本地数据，为了方便演示，使用本地的JSON字符串，然后再将其解析为需要的格式

参见 [MySubject](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/model/MySubject)、[SubjectExtras](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/model/SubjectExtras)、[SubjectRepertory](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/model/SubjectRepertory)

## 基础功能
> 本节你将掌握如何使用本控件搭建自己的课表界面。这里使用的版本为`2.0.2`
> 虽说本节名字为`基础功能`，但是这是一个综合的例子，如果有些地方看不明白或者属性记不住，那么这是正常的，看到下文对应的章节时会清晰很多，如果仍然不懂请联系我~
> 完整代码参见 [BaseFuncActivity](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/views/BaseFuncActivity)

### 添加依赖
Gradle
```xml
compile 'com.zhuangfei:TimetableView:2.0.2'
```
Maven
```xml
<dependency>
  <groupId>com.zhuangfei</groupId>
  <artifactId>TimetableView</artifactId>
  <version>2.0.2</version>
  <type>pom</type>
</dependency>
```
### 添加控件

该控件包含的基础组件有日期栏、侧边栏、课表视图，在布局文件中加入如下代码后会包含这三个基础组件，注意要添加背景色，没有背景图片可以添加白色背景。
```xml
    <com.zhuangfei.timetable.view.WeekView
        android:id="@+id/id_weekview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <com.zhuangfei.timetable.TimetableView
        android:id="@+id/id_timetableView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@drawable/bg3">
    </com.zhuangfei.timetable.TimetableView>
```
### 数据源设置
数据源的设置方式有两种，以下分别来介绍：

方法1：使用指定的格式`List<Schedule>`,`Schedule`是控件提供的课程实体类,你可以将自己的数据封装为指定格式，然后进行如下配置即可

```java
 mTimetableView.data(scheduleList)
                .curWeek(1)
                .showView();
```
方法2：方法1在很多场景下都满足不了需求，往往需要定义自己的课程实体类，你可以跟随以下几个步骤来使用它

- 创建自定义的实体类并实现`ScheduleEnable`接口

```java

public class MySubject implements ScheduleEnable {
  //省略属性、setter、getter、构造函数

   @Override
  public Schedule getSchedule() {
      Schedule schedule=new Schedule();
      schedule.setDay(getDay());
      schedule.setName(getName());
      schedule.setRoom(getRoom());
      schedule.setStart(getStart());
      schedule.setStep(getStep());
      schedule.setTeacher(getTeacher());
      schedule.setWeekList(getWeekList());
      schedule.setColorRandom(2);
      return schedule;
   }
}
```
- 使用`source()`设置
```java
//模拟获取课程数据：自定义格式
List<MySubject> mySubjects = SubjectRepertory.loadDefaultSubjects();

//设置数据源并显示
mTimetableView.source(mySubjects)
                .curWeek(1)
                .showView();    
```

### 配置属性
我直接把这部分整个代码放出来了，分以下三步：

 1. 获取控件
 2. 设置`WeekView`属性
 3. 设置`TimetableView`属性

使用如下方式获取到控件
```java
   TimetableView mTimetableView;
   WeekView mWeekView;
```

```java
/**
     * 初始化课程控件
     */
    private void initTimetableView() {
        //获取控件
        mWeekView = findViewById(R.id.id_weekview);
        mTimetableView = findViewById(R.id.id_timetableView);

        //设置周次选择属性
        mWeekView.source(mySubjects)
                .curWeek(1)
                .callback(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        int cur = mTimetableView.curWeek();
                       //更新切换后的日期，从当前周cur->切换的周week
                        mTimetableView.onDateBuildListener()
                                .onUpdateDate(cur, week);
                        //课表切换周次
                        mTimetableView.changeWeekOnly(week);
                    }
                })
                .callback(new IWeekView.OnWeekLeftClickedListener() {
                    @Override
                    public void onWeekLeftClicked() {
                        onWeekLeftLayoutClicked();
                    }
                })
                .isShow(false)//设置隐藏，默认显示
                .showView();

        mTimetableView.source(mySubjects)
                .curWeek(1)
                .curTerm("大三下学期")
                .maxSlideItem(10)
                //透明度
                //日期栏0.1f、侧边栏0.1f，周次选择栏0.6f
                //透明度范围为0->1，0为全透明，1为不透明
                .alpha(0.3f, 0.1f, 0.6f)
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display(scheduleList);
                    }
                })
                .callback(new ISchedule.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(View v, int day, int start) {
                        Toast.makeText(BaseFuncActivity.this,
                                "长按:周" + day  + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        titleTextView.setText("第" + curWeek + "周");
                    }
                })
                //旗标布局点击监听
                .callback(new ISchedule.OnFlaglayoutClickListener() {
                    @Override
                    public void onFlaglayoutClick(int day, int start) {
                        mTimetableView.hideFlaglayout();
                        Toast.makeText(BaseFuncActivity.this,
                                "点击了旗标:周" + (day + 1) + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .showView();
    }
```

### 删除课程
```java
/**
     * 删除课程
     * 内部使用集合维护课程数据，操作集合的方法来操作它即可
     * 最后更新一下视图（全局更新）
     */
    protected void deleteSubject() {
        int size = mTimetableView.dataSource().size();
        int pos = (int) (Math.random() * size);
        if (size > 0) {
            mTimetableView.dataSource().remove(pos);
            mTimetableView.updateView();
        }
    }
```

### 添加课程
```java
    /**
     * 添加课程
     * 内部使用集合维护课程数据，操作集合的方法来操作它即可
     * 最后更新一下视图（全局更新）
     */
    protected void addSubject() {
        List<Schedule> dataSource=mTimetableView.dataSource();
        int size = dataSource.size();
        if (size > 0) {
            Schedule schedule = dataSource.get(0);
            dataSource.add(schedule);
            mTimetableView.updateView();
        }
    }
```

### 非本周课程显示与隐藏
```java
/**
     * 隐藏非本周课程
     * 修改了内容的显示，所以必须更新全部（性能不高）
     * 建议：在初始化时设置该属性
     *
     *  updateView()被调用后，会重新构建课程，课程会回到当前周
     */
    protected void hideNonThisWeek() {
        mTimetableView.isShowNotCurWeek(false).updateView();
    }

    /**
     * 显示非本周课程
     * 修改了内容的显示，所以必须更新全部（性能不高）
     * 建议：在初始化时设置该属性
     */
    protected void showNonThisWeek() {
        mTimetableView.isShowNotCurWeek(true).updateView();
    }
```

### 最大节次设置
```java
/**
     * 设置侧边栏最大节次，只影响侧边栏的绘制，对课程内容无影响
     * @param num
     */
    protected void setMaxItem(int num) {
        mTimetableView.maxSlideItem(num).updateSlideView();
    }
```

### 节次时间显示与隐藏
```java
/**
     * 显示时间
     * 设置侧边栏构建监听，TimeSlideAdapter是控件实现的可显示时间的侧边栏
     */
    protected void showTime() {
        String[] times = new String[]{
                "8:00", "9:00", "10:10", "11:00",
                "15:00", "16:00", "17:00", "18:00",
                "19:30", "20:30","21:30","22:30"
        };
        OnSlideBuildAdapter listener= (OnSlideBuildAdapter) mTimetableView.onSlideBuildListener();
        listener.setTimes(times)
                .setTimeTextColor(Color.BLACK);
        mTimetableView.updateSlideView();
    }

    /**
     * 隐藏时间
     * 将侧边栏监听置Null后，会默认使用默认的构建方法，即不显示时间
     * 只修改了侧边栏的属性，所以只更新侧边栏即可（性能高），没有必要更新全部（性能低）
     */
    protected void hideTime() {
        mTimetableView.callback((ISchedule.OnSlideBuildListener) null);
        mTimetableView.updateSlideView();
    }
```

### WeekView显示与隐藏
```java
/**
     * 显示WeekView
     */
    protected void showWeekView(){
        mWeekView.isShow(true);
    }

    /**
     * 隐藏WeekView
     */
    protected void hideWeekView(){
        mWeekView.isShow(false);
    }
```

### 小结
一个综合的例子你已经看完了，是不是感觉印象还不是那么深刻，不要担心，下文针对每个特性进行深入讲解

## 周次选择栏
> 周次选择栏`WeekView`是控件实现的一个默认的周次选择控件，你可以使用它快速的拥有周次选择功能，`TimetableView`是没有周次选择功能的，所以需要两者配合使用。本节你将掌握使用默认的周次选择栏
> 完整代码参见 [BaseFuncActivity](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/views/BaseFuncActivity)

### 自定义属性
在根节点添加一个名为app的命名空间
```xml
xmlns:app="http://schemas.android.com/apk/res-auto"
```

以下属性你可以在xml中配置
```xml
    <declare-styleable name="PerWeekView">
        <!--圆点半径-->
        <attr name="radius" format="dimension"/>

        <!--亮色-->
        <attr name="light_color" format="color"/>

        <!--暗色-->
        <attr name="gray_color" format="color"/>

    </declare-styleable>
```

### 默认的周次选择栏
**1.添加控件**

在布局文件中放一个`TimetableView`，然后在`TimetableView`的上边放一个`WeekView`
```xml
	<com.zhuangfei.timetable.view.WeekView
        android:id="@+id/id_weekview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <com.zhuangfei.timetable.TimetableView
        android:id="@+id/id_timetableView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@drawable/bg3">
    </com.zhuangfei.timetable.TimetableView>
```

**2.初始化**

我直接把这部分整个代码放出来了，分以下三步：

 1. 获取控件
 2. 设置`WeekView`属性
 3. 设置`TimetableView`属性
 
使用如下方式获取到控件
```java
TimetableView mTimetableView;
WeekView weekView;
```

```java
/**
     * 初始化课程控件
     */
    private void initTimetableView() {
        //获取控件
        mWeekView = findViewById(R.id.id_weekview);
        mTimetableView = findViewById(R.id.id_timetableView);

        //设置周次选择属性
        mWeekView.source(mySubjects)
                .curWeek(1)
                .callback(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        int cur = mTimetableView.curWeek();
                       //更新切换后的日期，从当前周cur->切换的周week
                        mTimetableView.onDateBuildListener()
                                .onUpdateDate(cur, week);
                        //课表切换周次
                        mTimetableView.changeWeekOnly(week);
                    }
                })
                .callback(new IWeekView.OnWeekLeftClickedListener() {
                    @Override
                    public void onWeekLeftClicked() {
                        onWeekLeftLayoutClicked();
                    }
                })
                .isShow(false)//设置隐藏，默认显示
                .showView();

        mTimetableView.source(mySubjects)
                .curWeek(1)
                .curTerm("大三下学期")
                .maxSlideItem(10)//最大侧边栏节次
                //透明度
                //日期栏0.1f、侧边栏0.1f，周次选择栏0.6f
                //透明度范围为0->1，0为全透明，1为不透明
                .alpha(0.1f, 0.1f, 0.6f)
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display(scheduleList);
                    }
                })
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        titleTextView.setText("第" + curWeek + "周");
                    }
                })
                .showView();
    }
```

**3.更新高亮日期**

由于在`onCreate`中设置了WeekView，所以默认的在WeekView初始化时会计算当前日期，如果程序在后台时间太长（超一天），那么进入页面时会发现日期不正确，所以可以在Activity的onStart生命周期方法中再次计算日期，设置高亮
```java
/**
     * 更新一下，防止因程序在后台时间过长（超过一天）而导致的日期或高亮不准确问题。
     */
    @Override
    protected void onStart() {
        super.onStart();
        mTimetableView.onDateBuildListener()
                .onHighLight();
    }
```
### 自定义周次选择栏
你只需要任意的定制即可，`WeekViewEnable`接口提供了自定义WeekView的规范，你可以用，也可以不用，不强制；因为它与`TimetableView`是没有任何关联的，有问题可以参考`WeekView`的实现

## 自定义属性
> 本控件不仅支持在代码中对属性进行设置，部分属性和监听还支持在布局文件xml中配置，一起来看看吧~
> 完整代码参见 [AttrActivity](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/views/AttrActivity)

### 属性列表
以下罗列的属性或监听可以在xml中配置
```xml
<!--Some Attr with TimetableView-->
    <declare-styleable name="TimetableView">

        <!--当前周-->
        <attr name="cur_week" format="integer"/>

        <!--当前学期-->
        <attr name="cur_term" format="string"/>

        <!--课程项的上边距-->
        <attr name="mar_top" format="dimension"/>

        <!--左边距-->
        <attr name="mar_left" format="dimension"/>

        <!--课程项的高度-->
        <attr name="item_height" format="dimension"/>

        <!--本周课程的圆角弧度-->
        <attr name="thisweek_corner" format="dimension"/>

        <!--非本周课程的圆角弧度-->
        <attr name="nonweek_corner" format="dimension"/>

        <!--侧边栏的最大项-->
        <attr name="max_slide_item" format="integer"/>

        <!--是否显示非本周课程-->
        <attr name="show_notcurweek" format="boolean"/>
    </declare-styleable>
```

### 属性设置
在根节点添加一个名为app的命名空间
```xml
xmlns:app="http://schemas.android.com/apk/res-auto"
```
直接在xml中对属性设置
```xml
<com.zhuangfei.timetable.TimetableView
        android:id="@+id/id_timetableView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/app_white"
        app:cur_week="3"
        app:cur_term="大三上学期"
        app:show_notcurweek="false"
        app:source="getSource"
        app:max_slide_item="10"/>
```
### 显示视图

数据源以及事件监听需要在代码中设置
```java
/**
     * 初始化课程控件
     */
    private void initTimetableView() {
        //获取控件
        mTimetableView = findViewById(R.id.id_timetableView);
        mTimetableView.source(mySubjects)
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display(scheduleList);
                    }
                })
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        Toast.makeText(AttrActivity.this,"第" + curWeek + "周",Toast.LENGTH_SHORT).show();
                    }
                })
                .showView();
    }
```

## 日期栏
> 本节主要演示如何对日期栏的属性设置以及自定义日期栏的步骤，通用步骤比如：添加控件、获取控件，设置数据源以及显示视图什么的都不再重复了，只讲解核心部分
> 完整代码参见 [DateActivity](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/views/DateActivity)

### 日期栏显示与隐藏
```java
/**
     * 隐藏日期栏
     */
    protected void hideDateView() {
        mTimetableView.hideDateView();
    }

    /**
     * 显示日期栏
     */
    protected void showDateView() {
        mTimetableView.showDateView();
    }
```
### 自定义日期栏
**Step1：自定义布局**

需要定义两个布局，第一个`item_custom_dateview_first.xml`定义的月份的样式
```xml
<?xml version="1.0" encoding="utf-8"?>
<TextView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/id_week_month"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:gravity="center_vertical|center_horizontal"
    android:textSize="12sp"
    android:textStyle="bold" />
```

第二个`item_custom_dateview.xml`定义的是星期一至星期日的每项的样式
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/id_week_layout"
    android:layout_width="wrap_content"
    android:layout_height="40dp"
    android:gravity="center_horizontal|center_vertical"
    android:orientation="vertical">

    <TextView
        android:id="@+id/id_week_day"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="12sp"
        android:textStyle="bold" />
</LinearLayout>
```

**Step2：自定义实现类**

由于这里实现的日期栏简单，所以直接继承自默认的实现`OnDateBuildAapter`即可，没有用默认的height，用的是自定义的30dp，那么首先将dp->px，然后使用自己的布局进行构建即可。
注意：由于要修改默认的height，所以`onBuildDayLayout` `onBuildMonthLayout`都必须重写并设置为新的height，否则无效。

你也可以直接实现`OnDateBuildListener`接口，更灵活但是操作复杂。

```java
/**
     * 自定义日期栏
     * 该段代码有点长，但是很好懂，仔细看看会有收获的，嘻嘻
     */
    protected void customDateView() {
        mTimetableView.callback(
                new OnDateBuildAapter() {
                    @Override
                    public View onBuildDayLayout(LayoutInflater mInflate, int pos, int width, int height) {
                        int newHeight=ScreenUtils.dip2px(DateActivity.this,30);
                        View v = mInflate.inflate(R.layout.item_custom_dateview, null, false);
                        TextView dayTextView = v.findViewById(R.id.id_week_day);
                        dayTextView.setText(dateArray[pos]);
                        layouts[pos] = v.findViewById(R.id.id_week_layout);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, newHeight);
                        layouts[pos].setLayoutParams(lp);

                        return v;
                    }

                    @Override
                    public View onBuildMonthLayout(LayoutInflater mInflate, int width, int height) {
                        int newHeight=ScreenUtils.dip2px(DateActivity.this,30);
                        View first = mInflate.inflate(R.layout.item_custom_dateview_first, null, false);
                        //月份设置
                        textViews[0] = first.findViewById(R.id.id_week_month);
                        layouts[0] = null;

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, newHeight);

                        int month = Integer.parseInt(weekDates.get(0));
                        first.setLayoutParams(lp);
                        textViews[0].setText(month + "\n月");
                        return first;

                    }
                })
                .updateDateView();
    }
```
### 恢复默认日期栏

```java
/**
     * 恢复默认日期栏
     */
    protected void cancelCustomDateView() {
        mTimetableView.callback((ISchedule.OnDateBuildListener) null)
                .updateDateView();
    }
```

## 侧边栏
> 在课程视图的左侧有一列是侧边栏，本节演示如何对侧边栏的属性进行配置以及自定义侧边栏的步骤
> 完整代码参见 [SlideActivity](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/views/SlideActivity)

### 准备
**添加控件**
```xml
    <com.zhuangfei.timetable.TimetableView
        android:id="@+id/id_timetableView"
        android:background="@color/app_white"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </com.zhuangfei.timetable.TimetableView>
```
**获取控件**
```java
mTimetableView = findViewById(R.id.id_timetableView);
List<MySubject> mySubjects = SubjectRepertory.loadDefaultSubjects();
mTimetableView.source(mySubjects)
                .curWeek(1)
                .showView();
```
### 节次时间显示与隐藏
```java
/**
     * 显示时间
     * 设置侧边栏构建监听，TimeSlideAdapter是控件实现的可显示时间的侧边栏
     * 只修改了侧边栏的属性，所以只更新侧边栏即可（性能高），没有必要更新全部（性能低）
     *
     * @see OnSlideBuildAdapter
     */
    protected void showTime() {
        String[] times = new String[]{
                "8:00", "9:00", "10:10", "11:00",
                "15:00", "16:00", "17:00", "18:00",
                "19:30", "20:30", "21:30", "22:30"
        };
        OnSlideBuildAdapter slideAdapter = new OnSlideBuildAdapter();
        slideAdapter.setTimes(times);
        mTimetableView.callback(slideAdapter);
        mTimetableView.updateSlideView();
    }

    /**
     * 隐藏时间
     * 将侧边栏监听置Null后，会默认使用默认的构建方法，即不显示时间
     * 只修改了侧边栏的属性，所以只更新侧边栏即可（性能高），没有必要更新全部（性能低）
     */
    protected void hideTime() {
        mTimetableView.callback((ISchedule.OnSlideBuildListener) null)
                .updateSlideView();
    }
```

### 修改侧边栏背景
```java
/**
     * 修改侧边栏背景,默认的使用的是OnSlideBuildAdapter，
     * 所以可以强转类型
     *
     * @param color
     */
    protected void modifySlideBgColor(int color) {
        OnSlideBuildAdapter listener = (OnSlideBuildAdapter) mTimetableView.onSlideBuildListener();
        listener.setBackground(color);
        mTimetableView.updateSlideView();
    }
```

### 修改节次文本颜色
```
    OnSlideBuildAdapter listener = (OnSlideBuildAdapter) mTimetableView.onSlideBuildListener();
    listener.setTextColor(color);
    mTimetableView.updateSlideView();
```

### 修改时间文本颜色
```
/**
     * 修改侧边栏时间文本的颜色值
     * @param color
     */
    protected void modifyItemTimeColor(int color){
        String[] times = new String[]{
                "8:00", "9:00", "10:10", "11:00",
                "15:00", "16:00", "17:00", "18:00",
                "19:30", "20:30", "21:30", "22:30"
        };
        OnSlideBuildAdapter listener = (OnSlideBuildAdapter) mTimetableView.onSlideBuildListener();
        listener.setTimes(times)
                .setTimeTextColor(color);
        mTimetableView.updateSlideView();
    }
```

### 自定义侧边栏

**Step1：创建布局**

创建一个XML文件`item_custom_slide.xml`，该文件的内容可完全自定义

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="vertical">
    <TextView
        android:textColor="@color/app_qing2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="top|center_horizontal"
        android:id="@+id/item_slide_textview"/>
</LinearLayout>
```
**Step2：设置监听**

为了简单起见，可以继承自`OnSlideBuildAdapter`，重写`getView()`方法，当然也可以直接实现`ISchedule.OnSlideBuildListener`接口
```
/**
     * 自定义侧边栏效果
     * 使用自定义的布局文件实现的文字居顶部的效果（默认居中）
     */
    protected void customSlideView(){
        mTimetableView.callback(
                new OnSlideBuildAdapter() {
                    @Override
                    public View getView(int pos, LayoutInflater inflater, int itemHeight, int marTop) {
                        //获取View并返回，注意设置marTop值
                        View v = inflater.inflate(R.layout.item_custom_slide, null, false);
                        TextView tv = v.findViewById(R.id.item_slide_textview);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                itemHeight);
                        lp.setMargins(0, marTop, 0, 0);
                        tv.setLayoutParams(lp);
                        tv.setText((pos + 1) + "");
                        return v;
                    }
                })
                .updateSlideView();
    }
```

### 侧边栏效果重置
```
/**
     * 取消自定义的侧边栏，回到默认状态
     * 只需要将监听器置空即可
     */
    protected void cancelCustomSlideView() {
        mTimetableView.callback((ISchedule.OnSlideBuildListener) null)
                .updateSlideView();
    }
```

## 课程项样式
> 本节将演示如何配置课程项的样式
> 完整代码参见 [ItemStyleActivity](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/views/ItemStyleActivity)

### 非本周课程显示与隐藏
```
/**
     * 隐藏非本周课程
     * 修改了内容的显示，所以必须更新全部（性能不高）
     * 建议：在初始化时设置该属性
     */
    protected void hideNonThisWeek() {
        mTimetableView.isShowNotCurWeek(false).updateView();
    }

    /**
     * 显示非本周课程
     * 修改了内容的显示，所以必须更新全部（性能不高）
     * 建议：在初始化时设置该属性
     */
    protected void showNonThisWeek() {
        mTimetableView.isShowNotCurWeek(true).updateView();
    }
```

### 设置间距以及弧度
```
/**
     * 设置间距以及弧度
     * 该方法只能同时设置四个角的弧度，设置单个角的弧度可参考下文
     */
    protected void setMarginAndCorner() {
        mTimetableView.cornerAll(0)
                .marTop(0)
                .marLeft(0)
                .updateView();
    }
```
### 设置单个角弧度
```
/**
     * 设置角度（四个角分别控制）
     *
     * @param leftTop
     * @param rightTop
     * @param rightBottom
     * @param leftBottom
     */
    public void setCorner(final int leftTop, final int rightTop, final int rightBottom, final int leftBottom) {
        mTimetableView.callback(new OnItemBuildAdapter() {
                    @Override
                    public void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView, Schedule schedule, GradientDrawable gd) {
                        super.onItemUpdate(layout, textView, countTextView, schedule, gd);
                        //数组8个元素，四个方向依次为左上、右上、右下、左下，
                        // 每个方向在数组中占两个元素，值相同
                        gd.setCornerRadii(new float[]{leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom});
                    }
                });
        mTimetableView.updateView();
    }
```
### 修改显示的文本
```
/**
     * 修改显示的文本
     */
    public void buildItemText() {
        mTimetableView.callback(new OnItemBuildAdapter() {
                    @Override
                    public String getItemText(Schedule schedule, boolean isThisWeek) {
                        if (isThisWeek) return "[本周]" + schedule.getName();
                        return "[非本周]" + schedule.getName();
                    }
                })
                .updateView();
    }
```

### 设置非本周课的背景
```
/**
     * 设置非本周课的背景
     *
     * @param color
     */
    public void setNonThisWeekBgcolor(int color) {
        mTimetableView.colorPool().setUselessColor(color);
        mTimetableView.updateView();
    }
```
### 课程重叠的样式
```
/**
     * 修改课程重叠的样式，在该接口中，你可以自定义出很多的效果
     */
    protected void modifyOverlayStyle() {
        mTimetableView.callback(new OnItemBuildAdapter() {
                    @Override
                    public void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView, Schedule schedule, GradientDrawable gd) {
                        super.onItemUpdate(layout, textView, countTextView, schedule, gd);
                        //可见说明重叠，取消角标，添加角度
                        if (countTextView.getVisibility() == View.VISIBLE) {
                            countTextView.setVisibility(View.GONE);
                            gd.setCornerRadii(new float[]{0, 0, 20, 20, 0, 0, 0, 0});
                        }
                    }
                });
        mTimetableView.updateView();
```
## 颜色池
> 颜色池`ScheduleColorPool`的内部实现是控件维护的一个集合，它管理着课程项的颜色，负责对颜色的存取
> 完整代码参见 [ColorPoolActivity](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/views/ColorPoolActivity)

### 获取颜色池
以下代码的返回结果就是一个`ScheduleColorPool`实例对象
```
mTimetableView.colorPool()；
```
### 指定颜色
如果需要让所有课程只在某几种颜色中分配颜色，只需要清空颜色池并加入特定的颜色，在分配颜色时会循环分配颜色池中的颜色
```java
/**
     * 设置指定的颜色，默认情况下颜色池中有一些颜色
     * 所以这里需要先清空一下颜色池
     * @param colors
     */
    public void setColor(int... colors){
        mTimetableView.colorPool().clear().add(colors);
        mTimetableView.updateView();
    }
```
### 重置颜色池
重置后，颜色池恢复到初始状态
```java
/**
     * 重置颜色池
     */
    public void resetColor(){
        mTimetableView.colorPool().reset();
        mTimetableView.updateView();
    }
```
### 追加颜色
向颜色池中追加颜色，在为课程项分配颜色时，会按照颜色池中的顺序依次取出颜色，所以并不保证追加的颜色一定会被用到
```java
/**
     * 追加颜色
     * @param colors
     */
    public void addColor(int... colors){
        mTimetableView.colorPool().add(colors);
        mTimetableView.updateView();
    }
```
### 设置非本周课程颜色

```java
/**
     * 设置非本周课的背景
     *
     * @param color
     */
    public void setNonThisWeekBgcolor(int color) {
        mTimetableView.colorPool().setUselessColor(color);
        mTimetableView.updateView();
    }
```

## 替换滚动布局
> 为什么会有这个功能？想象这样几个场景：下拉反弹效果、下拉刷新效果、监听到滚动的位置、解决滚动布局嵌套导致的滑动冲突。这几个需求都可以使用自定义View的方式来解决。
> 替换滚动布局的意思是：你可以使用自定义的`ScrollView`来替换控件中的普通的`ScrollView`，可以想象它将有更好的扩展性。
> 本节将演示如何实现一个有下拉反弹效果的课表界面，自定义View的知识不在本节的范围之内，有兴趣可以百度。
> 完整代码参见 [ElasticActivity](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/views/ElasticActivity)

### 自定义View
你需要先准备好一个自定义View，根据你的需求而定，可以参考demo中定义的弹性滚动布局`ElasticScrollView`

### 布局文件
准备一个布局文件，命名任意，这里命名为`custom_myscrollview.xml`,将以下内容复制到布局文件中，然后将根控件换成自定义控件，注意ID不能改变
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/id_scrollview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:scrollbars="none">

    <include layout="@layout/view_content"/>

</ScrollView>
```
`custom_myscrollview.xml`中的内容如下：
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.zhuangfei.android_timetableview.ElasticScrollView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/id_scrollview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:scrollbars="none">

    <include layout="@layout/view_content"/>

</com.zhuangfei.android_timetableview.ElasticScrollView>
```
### 设置监听

```java
/**
         * 过程很简单，步骤如下：
         * 1.创建一个xml文件，命名为custom_myscrollview.xml
         * 2.拷贝一段代码至该文件中，具体内容可以参见custom_myscrollview.xml
         * 3.将根布局控件修改为自定义的控件，其他内容无需修改
         * 4.设置滚动布局构建监听并实现其方法，将自定义的xml转换为View返回即可
         *
         */
        mTimetableView.source(mySubjects)
                .callback(new ISchedule.OnScrollViewBuildListener() {
                    @Override
                    public View getScrollView(LayoutInflater mInflate) {
                        return mInflate.inflate(R.layout.custom_myscrollview, null, false);
                    }
                })
                .showView();
```

## 工具类
> 控件提供了一个工具类，可以方便的以无界面的方式操作课程数据，本节演示如何使用工具类实现对课程颜色的可视化展示
> 完整代码参见 [NonViewActivity](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/views/NonViewActivity)

### 列表与适配器
在Activity的布局中放一个ListView
```xml
<ListView
        android:id="@+id/id_listview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></ListView>
```

`item_nonview.xml`是ListView中每一项的布局，它的内容如下：
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:background="@color/app_white">

    <TextView
        android:id="@+id/id_nonview_name"
        android:layout_width="wrap_content"
        android:layout_height="50dp"
        android:gravity="center_vertical"
        android:layout_alignParentLeft="true"
        android:layout_centerVertical="true"
        android:layout_marginLeft="15dp"
        android:layout_marginRight="15dp"
        android:ellipsize="end"
        android:singleLine="true"/>

    <TextView
        android:id="@+id/id_nonview_color"
        android:layout_width="30dp"
        android:layout_height="30dp"
        android:layout_alignParentRight="true"
        android:layout_centerVertical="true"
        android:layout_marginRight="15dp"/>
</RelativeLayout>
```

布局文件准备好之后，需要一个适配器
```java
public class NonViewAdapter extends BaseAdapter {

    List<Schedule> schedules;
    Context context;
    LayoutInflater inflater;

    public NonViewAdapter(Context context, List<Schedule> schedules) {
        this.context = context;
        this.schedules = schedules;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Object getItem(int i) {
        return schedules.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View mView = null;
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_nonview, null);
            holder.nameTextView = convertView.findViewById(R.id.id_nonview_name);
            holder.colorTextView = convertView.findViewById(R.id.id_nonview_color);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Schedule schedule= (Schedule) getItem(i);
        ScheduleColorPool colorPool=new ScheduleColorPool(context);
        holder.nameTextView.setText(schedule.getName());
        holder.colorTextView.setBackgroundColor(colorPool.getColorAuto(schedule.getColorRandom()));
        return convertView;
    }

    class ViewHolder {
        TextView nameTextView;
        TextView colorTextView;
    }
}
```

它是如何将课程的颜色找出来的？看下段代码：
```java
Schedule schedule= (Schedule) getItem(i);
ScheduleColorPool colorPool=new ScheduleColorPool(context);
holder.colorTextView.setBackgroundColor(colorPool.getColorAuto(schedule.getColorRandom()));
```

继续看，核心就是这一行，适配器中的数据都是被分配过颜色了(怎么分配的见下文),所谓分配颜色就是给它一个编号，然后拿着编号到颜色池中取颜色，`getColorAuto()`不会产生数组越界问题，内部使用模运算来循环的在颜色池中取值
```
colorPool.getColorAuto(schedule.getColorRandom())
```

设置适配器
```java
listView=findViewById(R.id.id_listview);
adapter=new NonViewAdapter(this,schedules);
listView.setAdapter(adapter);
```

获取数据
```java
public List<Schedule> getData() {
        List<Schedule> list = ScheduleSupport.transform(SubjectRepertory.loadDefaultSubjects());
        list = ScheduleSupport.getColorReflect(list);//分配颜色
        return list;
    }
```

### 显示所有课程
```java
protected void all(){
        schedules.clear();
        schedules.addAll(getData());
        adapter.notifyDataSetChanged();
    }
```

### 第一周有课的课程
```java
/**
     * 获取第一周有课的课程并显示出来
     */
    protected void haveTime(){
        List<Schedule> result = new ArrayList<>();
        List<Schedule>[] arr = ScheduleSupport.splitSubjectWithDay(getData());
        for (int i = 0; i < arr.length; i++) {
            List<Schedule> tmpList = arr[i];
            for (Schedule schedule : tmpList) {
                if (ScheduleSupport.isThisWeek(schedule, 1)) {
                    result.add(schedule);
                }
            }
        }
        schedules.clear();
        schedules.addAll(result);
        adapter.notifyDataSetChanged();
    }
```

### 周一有课的课程
```java
/**
     * 显示第一周周一有课的课程
     */
    protected void haveTimeWithMonday(){
        List<Schedule> tmpList = ScheduleSupport.getHaveSubjectsWithDay(
                getData(), 1, 0);
        schedules.clear();
        schedules.addAll(tmpList);
        adapter.notifyDataSetChanged();
    }
```

## 额外的信息

> 设置数据源时可以使用自定义的数据类型，但是必须实现`ScheduleEnable`接口，为什么呢？因为在TimetableView内部保存的数据格式是List<Schedule>的，接口只是起到一个转换的作用，点击事件会回调，会获取到点击位置的一个List<Schedule>集合，此时我们已经拿不到自定义的数据类型了，那么此时我想拿到这门课程的ID怎么办？
> 完整代码参见 [ExtrasActivity](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/views/ExtrasActivity)

### 存入额外数据
Schedule中没有这个字段，自定义类型中有，所以在Schedule新增了一个extras字段，它是一个Map。所以现在这个问题可以这样解决：

```java
/**
 * 自定义实体类需要实现ScheduleEnable接口并实现getSchedule()
 *
 * @see ScheduleEnable#getSchedule()
 */
public class MySubject implements ScheduleEnable {
    //Key
	public static final String EXTRAS_ID="extras_id";
	
    //额外信息
	private int id=0;

    //可分别与Schedule中的数据域对应
	private String name;
	private String room;
	private String teacher;
	private List<Integer> weekList;
	private int start;
	private int step;
	private int day;
	private String term;
	private int colorRandom = 0;
	
	//省略setter、getter、构造函数
	
	@Override
	public Schedule getSchedule() {
		Schedule schedule=new Schedule();
		schedule.setDay(getDay());
		schedule.setName(getName());
		schedule.setRoom(getRoom());
		schedule.setStart(getStart());
		schedule.setStep(getStep());
		schedule.setTeacher(getTeacher());
		schedule.setWeekList(getWeekList());
		schedule.setColorRandom(2);
		schedule.putExtras(EXTRAS_ID,getId());//重要的位置
		return schedule;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}

```

### 读出额外数据
```
/**
     * 初始化课程控件
     */
    private void initTimetableView() {
        //获取控件
        mTimetableView = findViewById(R.id.id_timetableView);

        mTimetableView.source(mySubjects)
                .curWeek(1)
                .curTerm("大三下学期")
                .maxSlideItem(10)
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display(scheduleList);
                    }
                })
                .showView();
    }

    /**
     * 显示内容
     *
     * @param beans
     */
    protected void display(List<Schedule> beans) {
        String str = "";
        for (Schedule bean : beans) {
            str += "["+bean.getName() + "]的id:"+bean.getExtras().get(MySubject.EXTRAS_ID)+"\n";
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
                
```

它是通过这样的方式拿到的额外数据：
```java
    String id=bean.getExtras().get(MySubject.EXTRAS_ID);
```

## 旗标布局

> 什么是旗标布局？我自己随意起的名，不要当真，嘻嘻。
> 旗标布局是指当我们点击空白格子的时候出现一个布局，该布局占据一个格子的位置，它所在的位置就是我们所点击的格子的位置，该布局可以响应事件并回调处理
> 完整代码参见 [FlaglayoutActivity](https://github.com/zfman/TimetableView/tree/master/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/views/FlaglayoutActivity)

默认情况下，旗标布局是开启状态的，即不需要任何配置，在空白格子处点击会出现旗标布局，当然，它也可以关闭。

### 事件监听
`OnSpaceItemClickListener`是空白格子点击监听器，当用户点击空白格子时，会回调该接口中的方法并传入点击的格子的位置，然后需要将旗标布局移动到指定格子位置，`OnSpaceItemClickAdapter`是这个接口的默认实现，这部分一般不用开发者关心

现在的效果是点击空白格子后，在点击的格子位置会出现一个旗标布局，开发者只需要使用使用`OnFlaglayoutClickListener`来监听旗标布局的点击事件即可，无需关心其他

以下代码中监听了三个事件：

- 课程项的单击
- 课程项的长按
- 空白格子的单击(默认)以及旗标布局的单击
```java
private void initTimetableView() {
        mTimetableView = findViewById(R.id.id_timetableView);
        List<MySubject> mySubjects = SubjectRepertory.loadDefaultSubjects();
        mTimetableView.source(mySubjects)
                .curWeek(1)
                .maxSlideItem(10)
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display(scheduleList);
                    }
                })
                .callback(new ISchedule.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(View v, int day, int start) {
                        Toast.makeText(FlaglayoutActivity.this,
                                "长按:周" + day  + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .callback(new ISchedule.OnFlaglayoutClickListener() {
                    @Override
                    public void onFlaglayoutClick(int day, int start) {
                        mTimetableView.hideFlaglayout();
                        Toast.makeText(FlaglayoutActivity.this,
                                "点击了旗标:周" + (day + 1) + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .showView();
    }
```

### 背景修改与重置
```java
/**
     * 修改旗标布局的背景色
     *
     * @param color
     */
    private void modifyFlagBgcolor(int color) {
        mTimetableView.flagBgcolor(color).updateFlaglayout();
    }

    /**
     * 重置旗标布局的背景色
     */
    private void resetFlagBgcolor() {
        mTimetableView.resetFlagBgcolor().updateFlaglayout();
    }
```

### 开启与关闭
```
/**
     * 取消旗标布局
     */
    private void cancelFlagBgcolor() {
        mTimetableView.isShowFlaglayout(false).updateFlaglayout();
    }

    /**
     * 显示旗标布局
     */
    private void resetFlaglayout() {
        mTimetableView.isShowFlaglayout(true).updateFlaglayout();
    }
```

### 显示与隐藏
```
//显示旗标布局
mTimetableView.showFlaglayout();

//隐藏旗标布局
mTimetableView.hideFlaglayout();
```












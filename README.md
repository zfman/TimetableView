### TimetableView
[TimetableView](https://github.com/zfman/TimetableView)是一款开源的Android课程表控件。

总体来说，v2.x的修改幅度非常大，现在的它很灵活，`v1.x`中为每个内置颜色定义了三个XML样式文件，仅仅是Item的样式就定义了36个样式文件，`v2.x`中删除了36个样式文件，仅保留一个作为模板，并在`v2.x`中引入颜色池的概念，进而使得Item的样式更加丰富

### Get Start
**Step1：添加依赖**

Gradle
```xml
compile 'com.zhuangfei:TimetableView:2.0.0'
```
Maven
```xml
<dependency>
  <groupId>com.zhuangfei</groupId>
  <artifactId>TimetableView</artifactId>
  <version>2.0.0</version>
  <type>pom</type>
</dependency>
```
**Step2：添加控件**

该控件包含的基础组件有日期栏、侧边栏、课表视图，在布局文件中加入如下代码后会包含这三个基础组件
```xml
    <com.zhuangfei.timetable.TimetableView
        android:id="@+id/id_timetableView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </com.zhuangfei.timetable.TimetableView>
```
**Step3：配置属性**

使用如下方式获取到控件
```java
   TimetableView mTimetableView = findViewById(R.id.id_timetableView);    
```

属性的设置分为两类：与课程项构建有关的、与全局有关的，前者的配置在`ScheduleManager`对象中，后者的配置在`TimetableView`中，所有配置信息需要在`showView()`调用前完成，否则无效

以下代码用来监听课程项点击事件：
```java
mTimetableView.getScheduleManager()
                .setOnItemClickListener(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display(scheduleList);
                    }
                });       
```
然后设置它的一些全局属性，最后调用`showView()`用来创建和显示课表视图，代码如下：
```
mTimetableView.setSource(mySubjects)
                .setCurWeek(1)
                .setCurTerm("大三下学期")
                .showView();    
```
至此，课表已经可以显示在视图上了。啊啊啊，怎么没有解释，看不懂呀！
不要着急，我特意写了大量的例子以及十篇文章来帮助你学习、理解它，如果你感觉它很好用，请帮我点一下右上角的`star`，蟹蟹~

### Resource
- [v1.x与v2.x的对比](https://github.com/zfman/TimetableView/wiki/v1.x%E4%B8%8Ev2.x%E7%9A%84%E5%AF%B9%E6%AF%94)
- [下载示例App](https://raw.githubusercontent.com/zfman/TimetableView/master/apks/v2.0.0.apk)
- [效果图](https://github.com/zfman/TimetableView/wiki/v2.x%E6%95%88%E6%9E%9C%E5%9B%BE)
- [Javadoc-v2.0.0](http://www.liuzhuangfei.com/github/timetableview/docs/v2.0.0/)

### Get Start 2.x
你可以跟随以下几个小节进行由简至难的学习：

- [基础功能](https://github.com/zfman/TimetableView/wiki/%E5%9F%BA%E7%A1%80%E5%8A%9F%E8%83%BD)
- [周次选择栏](https://github.com/zfman/TimetableView/wiki/%E5%91%A8%E6%AC%A1%E9%80%89%E6%8B%A9%E6%A0%8F)
- [自定义属性](https://github.com/zfman/TimetableView/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E5%B1%9E%E6%80%A7)
- [日期栏](https://github.com/zfman/TimetableView/wiki/%E6%97%A5%E6%9C%9F%E6%A0%8F)
- [侧边栏](https://github.com/zfman/TimetableView/wiki/%E4%BE%A7%E8%BE%B9%E6%A0%8F)
- [课程项样式](https://github.com/zfman/TimetableView/wiki/%E8%AF%BE%E7%A8%8B%E9%A1%B9%E6%A0%B7%E5%BC%8F)
- [颜色池](https://github.com/zfman/TimetableView/wiki/%E9%A2%9C%E8%89%B2%E6%B1%A0)
- [替换滚动布局](https://github.com/zfman/TimetableView/wiki/%E6%9B%BF%E6%8D%A2%E6%BB%9A%E5%8A%A8%E5%B8%83%E5%B1%80)
- [工具类](https://github.com/zfman/TimetableView/wiki/%E5%B7%A5%E5%85%B7%E7%B1%BB)

### About Me
- [csdn](https://blog.csdn.net/lzhuangfei)
- [github](https://github.com/zfman)
- [blog page](http://www.liuzhuangfei.com)
- 刘壮飞 QQ:1193600556

# TimetableView
一个非常漂亮的课程表控件，支持课程颜色、星期高亮、周日期显示、周次选择等功能

### 说明
由于前一段时间对TimetableView进行了修改，所以以下的API可能会有些许差异，暂时没有时间修改，如果有问题请发邮件至1193600556@qq.com.
维护计划：2018/1/25日左右我会加上核心代码的注释
最后，感谢你的支持。

### 运行效果
![课表](https://github.com/zfman/TimetableView/blob/master/extras/image/img1.png)

### Demo下载
[下载Demo App](https://raw.githubusercontent.com/zfman/TimetableView/master/extras/TimetableDemo.apk)

### 简单使用
我们先不管项目依赖什么的，先来看看使用流程：

第一步：引入TimetableView控件
```xml
    <!-- XML CODE -->
    <com.zhuangfei.timetable.core.TimetableView 
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/id_timetableView"
        android:orientation="vertical">
    </com.zhuangfei.timetable.core.TimetableView>
```

第二步：初始化控件、设置
```java
timetableView=(TimetableView) findViewById(R.id.id_timetableView);
//设置相关属性
timetableView.setDataSource(subjectList)//设置数据源
             .setOnSubjectItemClickListener(this)//设置课表项点击监听器
	     .setOnSubjectMenuClickListener(this)//设置头部两侧按钮点击监听器
	     .setLeftText("发现")//设置左侧按钮的文本
	     .showSubjectView();//显示视图
```

大功告成！简单吧!

### 添加项目依赖
1.将本项目下载到本地上，解压会有两个子文件夹

TimetableView：TimetableView项目源码
timetableSample：本项目的一个Demo

2.把TimetableView文件以及其子文件作为项目导入到Eclipse中，并将项目设置为libary

3.将TimetableView添加为自己的项目的libary

### 高级使用
先看下timetableView可以设置哪些属性
```java
timetableView.setDataSource(subjectList)
		.setObjectSource(objectSource, onSubjectTransformListener)
		.setMax(true)//是否启用最大节次（12节），默认为10节
		.setShowDashLayer(true)//是否显示虚线层
		.setCurWeek(2)//设置当前周
		.setCurTerm("新学期")//设置当前学期
		.setOnSubjectItemClickListener(this)//设置课表项点击监听器
		.setOnSubjectMenuClickListener(this)//设置头部两侧按钮点击监听器
		.setLeftText("发现")//设置左侧按钮的文本
		.setRightImage(R.drawable.ic_launcher)//设置右侧按钮的图标
		.showSubjectView();//显示视图
```

上边应该都不难理解，注意到设置数据源有两个方法：

setDataSource(subjectList)：将List&lt;SubjectBean&gt;设置为数据源

setObjectSource(objectSource, onSubjectTransformListener)：将List&lt;T&gt;设置为数据源，注意到集合的类型不定，所以要实现onSubjectTransformListener接口实现两个对象转换的规则

**动态更新课表**

不管删除还是添加，只要构造一个新的数据源，最后要调用notifyDataSourceChanged()来更新UI界面
```java
timetableView.setDataSource(newSubjectList)
		.notifyDataSourceChanged();
```

**切换周次**
```java
//第二个参数为：是否强制将第一个参数设置为当前周
timetableView.changeWeek(2,true);
```
切换周次的效率要远高于showSubjectView()的效率。

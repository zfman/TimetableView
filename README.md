# TimetableView

一个非常漂亮的`Android`课程表控件，该控件支持的功能：

- 设置数据源
- 颜色设置，控件内置17种颜色
- 触感反馈，每种内置颜色都有对应的三个样式
- 日期显示与高亮
- 课表Item点击事件处理
- 解决课程重叠、交叉的问题，解决的效果同超级课程表
- 高效的切换周次
- 获取某天要上的课程

### Resource
- [Document WIKI](https://github.com/zfman/TimetableView/wiki)
- [Demo运行效果](https://github.com/zfman/TimetableView/wiki/Demo%E8%BF%90%E8%A1%8C%E6%95%88%E6%9E%9C)
- [Demo App](https://raw.githubusercontent.com/zfman/TimetableView/master/extras/TimetableSample.apk)
- [TimetableAPI](https://github.com/zfman/api-demo/tree/master/timetable)
- [Change Log](https://github.com/zfman/TimetableView/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)

### CSDN
- [博客主页](https://blog.csdn.net/lzhuangfei)
- [一起实现一个健壮的课程表控件-原理篇](https://blog.csdn.net/lzhuangfei/article/details/78243745)
- [河南理工大学课程库API](https://blog.csdn.net/lzhuangfei/article/details/79946997)


### 简单使用

**Step 1：添加项目依赖**

在build.gradle文件中添加以下代码
```
compile 'com.zhuangfei:TimetableView:1.0.2'
```

**Step 2：引入`TimetableView`控件**
```xml
    <!-- XML CODE -->
    <com.zhuangfei.timetable.core.TimetableView 
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/id_timetableView"
        android:orientation="vertical">
    </com.zhuangfei.timetable.core.TimetableView>
```

**Step 3：初始化控件、设置**

设置数据源时，你需要将你的课程数据转化为`List<SubjectBean>`对象，请参考`Demo`的[`MainActivity`](https://github.com/zfman/TimetableView/blob/master/androidstudio/AndroidTimetableView/app/src/main/java/com/zhuangfei/android_timetableview/MainActivity.java)
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

### 属性

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

### 动态更新课表

不管删除还是添加，只需要更改数据源，最后调用notifyDataSourceChanged()来通知UI界面同步即可。
```java
//删除课程
    protected void deleteSubject() {
        int pos = (int) (Math.random() * subjectBeans.size());
        if (subjectBeans.size() > 0) {
            subjectBeans.remove(pos);
            mTimetableView.notifyDataSourceChanged();
        } else {
            Toast.makeText(this, "没有课程啦！", Toast.LENGTH_SHORT).show();
        }

    }
```

### 切换周次

切换周次的效率非常高，你可以使用以下代码切换周次：
```java
//第二个参数为：是否强制将第一个参数设置为当前周
timetableView.changeWeek(2,true);
```

### 获取某天的课程(要求版本>=v1.0.1)

SubjectUtils是课程的工具类，调用其方法获取课程，示例如下：
```java
//显示周一课程
    protected void showTodaySubjects() {
        //0表示周一，依次类推，6代表周日
        List<SubjectBean> beans = SubjectUtils.getTodaySubjects(subjectBeans, curWeek, 0);
        String subjectStr=showSubjects(beans);
        Toast.makeText(this, "周一有" + beans.size() + "门课要上\n\n" + subjectStr, Toast.LENGTH_SHORT).show();
    }

    //显示周一所有课程
    protected void showTodayAllSubjects() {
        List<SubjectBean> beans = SubjectUtils.getTodayAllSubjects(subjectBeans, 0);
        String subjectStr=showSubjects(beans);
        Toast.makeText(this, "周一共有" + beans.size() + "门课\n\n" + subjectStr, Toast.LENGTH_SHORT).show();
    }
```

### 绑定TextView

当curWeek、数据源、curTerm任一发生变化，系统根据自己定义的规则对绑定的TextView进行文字同步，示例如下：
```java
	mTimetableView.setDataSource(subjectBeans)
                .setCurTerm("大三上学期")
                .setCurWeek(curWeek)
                .bindTitleView(mTitleTextView)//这句话绑定View
                .setOnSubjectBindViewListener(this)//这句话实现接口，在接口中定义规则
                .setOnSubjectItemClickListener(this)
                .setOnSubjectItemLongClickListener(this)
                .showTimetableView();
				
	@Override
    public void onBindTitleView(TextView titleTextView, int curWeek, String curTerm, List<SubjectBean> subjectBeans) {
        String text = "第" + curWeek + "周" + ",共" + subjectBeans.size() + "门课";
		//填充
        titleTextView.setText(text);
		
		//同步当前周次
        this.curWeek=curWeek;
    }
	
```

### 注意的地方

1.在调用`showTimetableView()`后需要调用一次`changeWeek()`，因为我在`showTimetableView()`里没有处理课程重叠的问题，当课程重叠或者有交叉且该课程在本周上时，会在课程的右上方义小红点+数字的形式提示。

2.红点的出现时机：在同一时刻且在本周有课的课程数大于等于2时

3.欢迎star、watch、fork，有问题可以联系我`1193600556@qq.com`

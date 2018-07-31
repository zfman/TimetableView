### TimetableView
[TimetableView](https://github.com/zfman/TimetableView)是一款开源的Android课程表控件。

> 本分支`dev`为实时的开发进度，并不是当前发布的版本，已发布的最新版本为`v2.0.1`，详情请查看master分支。

### 目前进度

**已完成**

- 背景设置
- 日期栏、侧边栏、Item透明度设置
- 默认的课程项样式调整
- API调用更简洁

**未完成**

- 日期栏透明需要完善细节
- API、接口设计需要继续调整

**简单使用**
```xml
    <com.zhuangfei.timetable.TimetableView
        android:id="@+id/id_tableview"
        android:background="@color/app_white"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```
就目前来说，它的调用方法是这样的：
```java
TimetableView mTableView=findViewById(R.id.id_tableview);
```
```java
mTableView.config()
                .curWeek(1)
                .source(source)
                .alpha(0.1f,0.1f,0.6f)
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        Log.d(TAG, "onItemClick: "+scheduleList.get(0).getName());
                    }
                })
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        Log.d(TAG, "onWeekChanged: "+curWeek);
                    }
                })
                .toggle(mTableView)
                .showView();
```
### 代码贡献规范

在贡献代码前需要先确认是否安装了Git以及Android Studio,请参考[廖雪峰的Git教程](https://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000)、[Android Studio下载](https://developer.android.google.cn/studio/?utm_source=androiddevtools.cn&utm_medium=website)

我非常欢迎其他开发者为本项目贡献代码（`Pull Request`）,请将您贡献的代码提交到`dev`分支，我检查完毕后会将其合并到`dev`分支。

在贡献代码时应该注意以下事情，否则可能会被我拒绝的哟~：

- 提交的每个PR要尽可能的小
- 复杂的逻辑应该添加尽可能多的注释
- 方法的命名格式尽可能的和现有的保持一致





### 感谢贡献者
> 感谢所有参与本项目帮助我一起完善这个项目的开发者~~

用户 | Issues | pull request
- | :-: | -
[Mystery00](https://github.com/Mystery00)|[#6.WeekView数组越界](https://github.com/zfman/TimetableView/issues/6) |[修复#6，并添加新功能](https://github.com/zfman/TimetableView/pull/7) 

### About Me

- [CSDN](https://blog.csdn.net/lzhuangfei)
- [Github](https://github.com/zfman)
- `QQ:1193600556`

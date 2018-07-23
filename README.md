### TimetableView
[TimetableView](https://github.com/zfman/TimetableView)是一款开源的Android课程表控件。

- 支持xml设置属性、数据源
- 提供了默认的周次选择栏（超级课程表效果），支持自定义
- 提供了默认的日期栏，支持自定义
- 多项侧边栏配置项，可设置时间的显示与否，支持自定义
- 多项课程项配置项，可拦截、可修改弧度、可设置非本周是否显示
- 课程颜色管理，使用颜色池来管理颜色，操作灵活
- 高效灵活，切换周次高效率、一个样式文件实现了多种课程项样式效果
- 可替换滚动布局，不将该控件绑死在固有的ScrollView中
- 丰富的工具类，你可以使用工具类对课程进行模拟分配颜色、获取有课的课程等

![11](https://raw.githubusercontent.com/zfman/TimetableView/master/images/v2.x/bg.png)

### 效果展示

- [v1.x与v2.x的对比](https://github.com/zfman/TimetableView/wiki/v1.x%E4%B8%8Ev2.x%E7%9A%84%E5%AF%B9%E6%AF%94)
- [下载示例App](https://raw.githubusercontent.com/zfman/TimetableView/master/apks/v2.0.1.apk)
- [效果图](https://github.com/zfman/TimetableView/wiki/v2.x%E6%95%88%E6%9E%9C%E5%9B%BE)

### 完整案例

- [hputimetable](https://github.com/zfman/hputimetable)
- [hpu小课-酷安](https://www.coolapk.com/apk/com.zhuangfei.hputimetable)
- [课程库开放接口](https://github.com/zfman/api-demo/tree/master/timetable)

### 更改日志
`Tag:v2.0.1` 于 `18/06/24` 发布

- 修复v2.0.0引入的周日崩溃问题
- 修复课程重叠时显示不全的问题

[更多日志 >>](https://github.com/zfman/TimetableView/wiki/ChangeLog)

### 开始使用

- [document](https://github.com/zfman/TimetableView/wiki)
    有大量的例子以供参考，你可以快速掌握基础用法

- [Javadoc-v2.0.0](http://www.liuzhuangfei.com/github/timetableview/docs/v2.0.0/)
    项目版本`v2.0.0`生成的javadoc，用于查找`v2.0.0`相关方法

### 代码贡献
我非常欢迎其他开发者为本项目贡献代码（Pull Request）,在贡献代码时应该注意以下事情：

- 提交的每个PR要尽可能的小
- 添加尽可能多的注释

当我确认过参与者提交的代码后，我会首先将代码合并到dev分支，待下个版本时再将dev分支合并到master分支并上传到jcenter。新版本发布之后，我会在本项目的README页面中注明参与者的用户名以及Github地址，以资鼓励

在贡献代码前需要先确认环境：

- 本地电脑已安装Git，已正确执行了相关配置，可以参考[廖雪峰的Git教程](https://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000)
- 已安装Android Studio，可以参考[Android Studio下载](https://developer.android.google.cn/studio/?utm_source=androiddevtools.cn&utm_medium=website)

你需要先fork本项目到你的Github账号上，然后从你的Github上将本项目克隆到本地，由于Git相关的知识我也不太熟悉，可以参见[如何提交一个pr](https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=github%20%E5%A6%82%E4%BD%95%E6%8F%90%E4%BA%A4%E4%B8%80%E4%B8%AApr&oq=github%2520%25E5%25A6%2582%25E4%25BD%2595%25E5%258F%2582%25E4%25B8%258E%25E5%25BC%2580%25E6%25BA%2590%25E5%25BA%2593&rsv_pq=cd2d9a0700014aeb&rsv_t=e28bURQIG7i3qIv%2FYWuyGWC3CzLDsj%2FgNEybtz%2FYON8eDUF0Rfq%2FvvfbTbY&rqlang=cn&rsv_enter=0&inputT=8232&rsv_sug3=99&rsv_sug1=92&rsv_sug7=100&rsv_sug2=0&rsv_sug4=10861)。


用户 | Issues | pull request
- | :-: | -
[Mystery00](https://github.com/Mystery00)|[#6.WeekView数组越界](https://github.com/zfman/TimetableView/issues/6) |[修复#6，并添加新功能](https://github.com/zfman/TimetableView/pull/7) 

### About Me

- [CSDN](https://blog.csdn.net/lzhuangfei)
- [Github](https://github.com/zfman)
- `QQ:1193600556`

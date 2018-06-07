### 问题

例子中给的示例是这样的：
```java
private void initMarkData() {
        HashMap<String,String> markData=new HashMap<>();
        //1的时候是灰色，0的时候是亮色
        markData.put("2018-6-1", "1");
        markData.put("2018-6-2", "1");
        markData.put("2018-6-3", "0");
        markData.put("2018-6-4", "0");

        markData.put("2018-7-5", "0");
        markData.put("2018-7-6", "1");
        markData.put("2018-7-7", "1");

        calendarAdapter.setMarkData(markData);
    }
```

但是如果有其他的情景呢，比如我需要第二次设置mark，那么应该怎么办呢？

```java
//模拟第二次调用
public void initMarkDataAfter(){
        HashMap<String,String> markData=new HashMap<>();
        //1的时候是灰色，0的时候是亮色
        markData.put("2018-6-9", "0");
        markData.put("2018-6-10", "0");

        markData.put("2018-7-10", "0");
        markData.put("2018-7-11", "0");
        markData.put("2018-7-12", "1");

        calendarAdapter.setMarkData(markData);
    }
```

此方法一调用，那么第一次的mark数据就被覆盖了，为什么呢？我们跟进源码分析一下：
`CalendarViewAdapter.java`

```java
public void setMarkData(HashMap<String, String> markData) {
        Utils.setMarkData(markData);
    }
```

继续跟进`Utils.java`
```java
public static void setMarkData(HashMap<String, String> data) {
        markData = data;
    }
```

到这里就发现了，它是直接修改了HashMap的引用了，那么怎么解决呢？莫急


### 解决方法

我们只要不修改它的引用即可，很简单的，具体示例如下：

```java
//定义一个全局的HashMap
public HashMap<String, String> markData=new HashMap<>();

//第一次调用
private void initMarkData() {
        //1的时候是灰色，0的时候是亮色
        markData.put("2018-6-1", "1");
        markData.put("2018-6-2", "1");
        markData.put("2018-6-3", "0");
        markData.put("2018-6-4", "0");

        markData.put("2018-7-5", "0");
        markData.put("2018-7-6", "1");
        markData.put("2018-7-7", "1");

        calendarAdapter.setMarkData(markData);
    }

    /**
     * 刘壮飞
     *
     * 这里模拟的是第二次setMarkData(markData)的情景
     *
     * 我们维护一个Map，注意不要修改该Map的引用
     * 可以使用clear(),不要用markData=new HashMap<>();
     */
    public void initMarkDataAfter(){
        //1的时候是灰色，0的时候是亮色
        markData.put("2018-6-9", "0");
        markData.put("2018-6-10", "0");

        markData.put("2018-7-10", "0");
        markData.put("2018-7-11", "0");
        markData.put("2018-7-12", "1");

        calendarAdapter.setMarkData(markData);
    }
```

### 完整代码

以下是MainActivity中的代码，有`刘壮飞`标记的才是我添加或修改的部分，其他代码和项目的例子中相同，我就不放了

```java
package com.zhuangfei.calenderdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.view.MonthPager;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.zhuangfei.calenderdemo.CustomDayView;
import com.zhuangfei.calenderdemo.R;
import com.zhuangfei.calenderdemo.ThemeDayView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldf on 16/11/4.
 */

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    TextView tvYear;
    TextView tvMonth;
    TextView backToday;
    CoordinatorLayout content;
    MonthPager monthPager;
    RecyclerView rvToDoList;
    TextView scrollSwitch;
    TextView themeSwitch;
    TextView nextMonthBtn;
    TextView lastMonthBtn;

    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private Context context;
    private CalendarDate currentDate;
    private boolean initiated = false;


    /**
     * 刘壮飞
     */
    public HashMap<String, String> markData=new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        content = (CoordinatorLayout) findViewById(R.id.content);
        monthPager = (MonthPager) findViewById(R.id.calendar_view);
        //此处强行setViewHeight，毕竟你知道你的日历牌的高度
        monthPager.setViewHeight(Utils.dpi2px(context, 270));
        tvYear = (TextView) findViewById(R.id.show_year_view);
        tvMonth = (TextView) findViewById(R.id.show_month_view);
        backToday = (TextView) findViewById(R.id.back_today_button);
        scrollSwitch = (TextView) findViewById(R.id.scroll_switch);
        themeSwitch = (TextView) findViewById(R.id.theme_switch);
        nextMonthBtn = (TextView) findViewById(R.id.next_month);
        lastMonthBtn = (TextView) findViewById(R.id.last_month);
        rvToDoList = (RecyclerView) findViewById(R.id.list);
        rvToDoList.setHasFixedSize(true);
        //这里用线性显示 类似于listview
        rvToDoList.setLayoutManager(new LinearLayoutManager(this));
        rvToDoList.setAdapter(new ExampleAdapter(this));
        initCurrentDate();
        initCalendarView();
        initToolbarClickListener();
        Log.e("ldf","OnCreated");

        initMarkDataAfter();
    }

    /**
     * onWindowFocusChanged回调时，将当前月的种子日期修改为今天
     *
     * @return void
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !initiated) {
            refreshMonthPager();
            initiated = true;
        }
    }

    /*
    * 如果你想以周模式启动你的日历，请在onResume是调用
    * Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
    * calendarAdapter.switchToWeek(monthPager.getRowIndex());
    * */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化对应功能的listener
     *
     * @return void
     */
    private void initToolbarClickListener() {
        backToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBackToDayBtn();
            }
        });
        scrollSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK) {
                    Utils.scrollTo(content, rvToDoList, monthPager.getViewHeight(), 200);
                    calendarAdapter.switchToMonth();
                } else {
                    Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
                    calendarAdapter.switchToWeek(monthPager.getRowIndex());
                }
            }
        });
        themeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshSelectBackground();
            }
        });
        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);
            }
        });
        lastMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);
            }
        });
    }

    /**
     * 初始化currentDate
     *
     * @return void
     */
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        tvYear.setText(currentDate.getYear() + "年");
        tvMonth.setText(currentDate.getMonth() + "");
    }

    /**
     * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
     */
    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(context, R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                context,
                onSelectDateListener,
                CalendarAttr.CalendarType.MONTH,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);
        calendarAdapter.setOnCalendarTypeChangedListener(new CalendarViewAdapter.OnCalendarTypeChanged() {
            @Override
            public void onCalendarTypeChanged(CalendarAttr.CalendarType type) {
                rvToDoList.scrollToPosition(0);
            }
        });
        initMarkData();
        initMonthPager();
    }

    /**
    * 刘壮飞
     * 初始化标记数据，HashMap的形式，可自定义
     * 如果存在异步的话，在使用setMarkData之后调用 calendarAdapter.notifyDataChanged();
     */
    private void initMarkData() {
        //1的时候是灰色，0的时候是亮色
        markData.put("2018-6-1", "1");
        markData.put("2018-6-2", "1");
        markData.put("2018-6-3", "0");
        markData.put("2018-6-4", "0");

        markData.put("2018-7-5", "0");
        markData.put("2018-7-6", "1");
        markData.put("2018-7-7", "1");

        calendarAdapter.setMarkData(markData);
    }

    /**
     * 刘壮飞
     *
     * 这里模拟的是第二次setMarkData(markData)的情景
     *
     * 我们维护一个Map，注意不要修改该Map的引用
     * 可以使用clear(),不要用markData=new HashMap<>();
     */
    public void initMarkDataAfter(){
        //1的时候是灰色，0的时候是亮色
        markData.put("2018-6-9", "0");
        markData.put("2018-6-10", "0");

        markData.put("2018-7-10", "0");
        markData.put("2018-7-11", "0");
        markData.put("2018-7-12", "1");

        calendarAdapter.setMarkData(markData);
    }

    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                monthPager.selectOtherMonth(offset);
            }
        };
    }


    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        tvYear.setText(date.getYear() + "年");
        tvMonth.setText(date.getMonth() + "");
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    tvYear.setText(date.getYear() + "年");
                    tvMonth.setText(date.getMonth() + "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void onClickBackToDayBtn() {
        refreshMonthPager();
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
        tvYear.setText(today.getYear() + "年");
        tvMonth.setText(today.getMonth() + "");
    }

    private void refreshSelectBackground() {
        ThemeDayView themeDayView = new ThemeDayView(context, R.layout.custom_day_focus);
        calendarAdapter.setCustomDayRenderer(themeDayView);
        calendarAdapter.notifyDataSetChanged();
        calendarAdapter.notifyDataChanged(new CalendarDate());
    }
}


```

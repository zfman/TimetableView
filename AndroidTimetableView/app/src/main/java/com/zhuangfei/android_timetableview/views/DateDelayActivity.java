package com.zhuangfei.android_timetableview.views;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuangfei.android_timetableview.R;
import com.zhuangfei.android_timetableview.adapter.OnDateDelayAdapter;
import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.listener.OnDateBuildAapter;
import com.zhuangfei.timetable.listener.OnSpaceItemClickAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleSupport;
import com.zhuangfei.timetable.view.WeekView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期延迟案例：
 * 需求：设定一个开学时间，在开学时间到来之前，一直显示开学时的第一周的日期
 */
public class DateDelayActivity extends AppCompatActivity {

    //控件
    TimetableView mTimetableView;
    WeekView mWeekView;

    TextView titleTextView;
    List<MySubject> mySubjects;

    //记录切换的周次，不一定是当前周
    int target = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_delay);
        mySubjects = SubjectRepertory.loadDefaultSubjects();
        titleTextView = findViewById(R.id.id_title);
        initTimetableView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //用于更正日期的显示
        int cur=mTimetableView.curWeek();
        mTimetableView.onDateBuildListener().onUpdateDate(cur,cur);
    }

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
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        titleTextView.setText("第" + curWeek + "周");
                    }
                })
                .callback(getDateDelayAdapter())
                .showView();
    }

    /**
     * 配置OnDateDelayAdapter
     */
    public OnDateDelayAdapter getDateDelayAdapter(){
        OnDateDelayAdapter onDateDelayAdapter=new OnDateDelayAdapter();

        //计算开学时间戳
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long startTime=0;
        try {
            startTime=sdf.parse("2018-09-03 00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //计算开学时的一周日期，我这里模拟一下
        List<String> dateList= Arrays.asList("9","03","04","05","06","07","08","09");

        //设置
        onDateDelayAdapter.setStartTime(startTime);
        onDateDelayAdapter.setDateList(dateList);
        return onDateDelayAdapter;
    }

    /**
     * 周次选择布局的左侧被点击时回调<br/>
     * 对话框修改当前周次
     */
    protected void onWeekLeftLayoutClicked() {
        final String items[] = new String[20];
        int itemCount = mWeekView.itemCount();
        for (int i = 0; i < itemCount; i++) {
            items[i] = "第" + (i + 1) + "周";
        }
        target = -1;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置当前周");
        builder.setSingleChoiceItems(items, mTimetableView.curWeek() - 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        target = i;
                    }
                });
        builder.setPositiveButton("设置为当前周", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (target != -1) {
                    mWeekView.curWeek(target + 1).updateView();
                    mTimetableView.changeWeekForce(target + 1);
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}

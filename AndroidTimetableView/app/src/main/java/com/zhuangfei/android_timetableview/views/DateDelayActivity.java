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

    TextView titleTextView;
    List<MySubject> mySubjects;

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

        //更新文本
        OnDateDelayAdapter adapter= (OnDateDelayAdapter) mTimetableView.onDateBuildListener();
        long when=adapter.whenBeginSchool();
        if(when>0){
            titleTextView.setText("距离开学还有"+when+"天");
        }
    }

    /**
     * 初始化课程控件
     */
    private void initTimetableView() {
        mTimetableView = findViewById(R.id.id_timetableView);

        mTimetableView.source(mySubjects)
                .curWeek(1)
                .curTerm("大三下学期")
                .maxSlideItem(10)
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        OnDateDelayAdapter adapter= (OnDateDelayAdapter) mTimetableView.onDateBuildListener();
                        long when=adapter.whenBeginSchool();
                        if(when>0){
                            titleTextView.setText("距离开学还有"+when+"天");
                        }else{
                            titleTextView.setText("第" + curWeek + "周");
                        }
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
}

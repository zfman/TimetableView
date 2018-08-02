package com.zhuangfei.android_timetableview.views;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuangfei.android_timetableview.R;
import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.view.WeekView;

import java.util.List;

/**
 * 本例子展示如何在Schedule中放入一些额外的信息
 */
public class ExtrasActivity extends AppCompatActivity {

    private static final String TAG = "ExtrasActivity";

    //控件
    TimetableView mTimetableView;
    List<MySubject> mySubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);
        mySubjects = SubjectRepertory.loadDefaultSubjects();
        initTimetableView();
    }

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
}

package com.zhuangfei.android_timetableview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView2;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.view.WeekView;

import java.util.List;

public class NewFeatureActivity extends AppCompatActivity {

    private static final String TAG = "NewFeatureActivity";

    TimetableView2 mTableView;
    WeekView mWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feature);
        mTableView=findViewById(R.id.id_tableview);
        mWeekView=findViewById(R.id.id_weekview);
        initTimetableView();
    }

    private void initTimetableView() {
        List<MySubject> source=SubjectRepertory.loadDefaultSubjects();
        mWeekView.curWeek(1)
                .source(source)
                .setOnWeekItemClickedListener(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int curWeek) {
                        mTableView.changeWeekOnly(curWeek);
                    }
                })
                .showView();

        mTableView.config()
                .curWeek(1)
                .source(source)
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
    }
}

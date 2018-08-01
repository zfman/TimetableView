package com.zhuangfei.android_timetableview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.listener.OnDateBuildAapter;
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.utils.ScreenUtils;
import com.zhuangfei.timetable.view.WeekView;

import java.util.List;

public class NewFeatureActivity extends AppCompatActivity {

    private static final String TAG = "NewFeatureActivity";

    TimetableView mTableView;
    WeekView mWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long t1=System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feature);
        mTableView=findViewById(R.id.id_tableview);
        mWeekView=findViewById(R.id.id_weekview);
        initTimetableView();
        long t2=System.currentTimeMillis();
        Log.d(TAG, "onCreate: "+(t2-t1));
    }

    private void initTimetableView() {
        List<MySubject> source=SubjectRepertory.loadDefaultSubjects();
        mWeekView.curWeek(1)
                .source(source)
                .callback(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int curWeek) {
                        mTableView.changeWeekOnly(curWeek);
                    }
                })
                .isShow(false)
                .showView();

        mTableView.config()
                .curWeek(1)
                .source(source)
                .bind(mWeekView)
                .isShowNotCurWeek(false)
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
                .callback(new OnDateBuildAapter(){
                    @Override
                    public View onBuildDayLayout(LayoutInflater mInflate, int pos, int width, int height) {
//                        return super.onBuildDayLayout(mInflate, pos, width, height);
                        View v=mInflate.inflate(R.layout.item_custom_dateview,null,false);
                        TextView dayTextView = v.findViewById(R.id.id_week_day);
                        dayTextView.setText(dateArray[pos]);

                        layouts[pos] = v.findViewById(R.id.id_week_layout);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
                        layouts[pos].setLayoutParams(lp);

                        return v;
                    }
                })
                .toggle(mTableView)
                .showView();
    }
}

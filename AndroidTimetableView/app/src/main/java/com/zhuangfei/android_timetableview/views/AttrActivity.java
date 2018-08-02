package com.zhuangfei.android_timetableview.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuangfei.android_timetableview.R;
import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.model.Schedule;

import java.util.List;

/**
 * 使用自定义属性对TimetableView进行配置<br/>
 * xml中只能配置部分属性，知道有这个东西即可，
 * 建议使用代码进行配置更加灵活
 */
public class AttrActivity extends AppCompatActivity {

    private static final String TAG = "AttrActivity";

    TimetableView mTimetableView;

    TextView titleTextView;
    List<MySubject> mySubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attr);
        mySubjects=SubjectRepertory.loadDefaultSubjects();

        initTimetableView();
    }

    /**
     * 初始化课程控件
     */
    private void initTimetableView() {
        //获取控件
        mTimetableView = findViewById(R.id.id_timetableView);
        mTimetableView.source(mySubjects)
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display(scheduleList);
                    }
                })
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        Toast.makeText(AttrActivity.this,"第" + curWeek + "周",Toast.LENGTH_SHORT).show();
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
            str += bean.getName() + "、";
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}

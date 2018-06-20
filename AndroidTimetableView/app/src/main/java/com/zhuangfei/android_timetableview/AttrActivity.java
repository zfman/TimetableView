package com.zhuangfei.android_timetableview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.android_timetableview.R;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.model.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用自定义属性对TimetableView进行配置
 */
public class AttrActivity extends AppCompatActivity {

    private static final String TAG = "AttrActivity";

    TimetableView mTimetableView;
    TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attr);

        titleTextView=findViewById(R.id.id_title);
        mTimetableView=findViewById(R.id.id_timetableView);
        mTimetableView.showView();
    }

    /**
     * 使用自己的格式设置数据源，方法名任意，但必须和xml中设置的一致
     * @return
     */
    public List<MySubject> getSource(){
        return SubjectRepertory.loadDefaultSubjects();
    }

    /**
     * 设置点击监听
     * @return
     */
    public void onItemClicked(View v,ArrayList<Schedule> scheduleList){
        Log.d(TAG, "onItemClicked: "+scheduleList.size());
        display(scheduleList);
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

    /**
     * 监听周次改变的方法，方法名任意，但必须和xml中设置的一致
     * 参数必须与示例一致
     * @return
     */
    public void onWeekChanged(int curWeek){
        Log.d(TAG, "onWeekChanged: "+curWeek);
        if(mTimetableView!=null){
            int size = mTimetableView.getDataSource().size();
            titleTextView.setText("第" + curWeek + "周,共" + size + "门课");
        }
    }
}

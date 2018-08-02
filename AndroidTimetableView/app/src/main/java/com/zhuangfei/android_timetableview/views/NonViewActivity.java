package com.zhuangfei.android_timetableview.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.zhuangfei.android_timetableview.R;
import com.zhuangfei.android_timetableview.adapter.NonViewAdapter;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用工具操作
 * 实现课程的罗列以及颜色的显示
 * 显示的颜色使用的默认的颜色池中的颜色
 */
public class NonViewActivity extends AppCompatActivity {

    Button moreButton;
    List<Schedule> schedules;
    ListView listView;
    NonViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_view);
        moreButton = findViewById(R.id.id_more);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopmenu();
            }
        });

        schedules = new ArrayList<>();
        listView = findViewById(R.id.id_listview);
        adapter = new NonViewAdapter(this, schedules);
        listView.setAdapter(adapter);
        all();
    }

    /**
     * 显示弹出菜单
     */
    public void showPopmenu() {
        PopupMenu popup = new PopupMenu(this, moreButton);
        popup.getMenuInflater().inflate(R.menu.popmenu_nonview, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.top1:
                        all();
                        break;
                    case R.id.top2:
                        haveTime();
                        break;
                    case R.id.top3:
                        haveTimeWithMonday();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    public List<Schedule> getData() {
        List<Schedule> list = ScheduleSupport.transform(SubjectRepertory.loadDefaultSubjects());
        list = ScheduleSupport.getColorReflect(list);//分配颜色
        return list;
    }

    /**
     * 获取所有课程
     */
    protected void all() {
        schedules.clear();
        schedules.addAll(getData());
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取第一周有课的课程并显示出来
     */
    protected void haveTime() {
        List<Schedule> result = new ArrayList<>();
        List<Schedule>[] arr = ScheduleSupport.splitSubjectWithDay(getData());
        for (int i = 0; i < arr.length; i++) {
            List<Schedule> tmpList = arr[i];
            for (Schedule schedule : tmpList) {
                if (ScheduleSupport.isThisWeek(schedule, 1)) {
                    result.add(schedule);
                }
            }
        }
        schedules.clear();
        schedules.addAll(result);
        adapter.notifyDataSetChanged();
    }

    /**
     * 显示第一周周一有课的课程
     */
    protected void haveTimeWithMonday() {
        List<Schedule> tmpList = ScheduleSupport.getHaveSubjectsWithDay(
                getData(), 1, 0);
        schedules.clear();
        schedules.addAll(tmpList);
        adapter.notifyDataSetChanged();
    }
}

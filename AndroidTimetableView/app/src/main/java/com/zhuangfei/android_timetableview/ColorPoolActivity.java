package com.zhuangfei.android_timetableview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleColorPool;

import java.util.List;

/**
 * 颜色池示例，展示颜色池的相关用法
 */
public class ColorPoolActivity extends AppCompatActivity {

    TimetableView mTimetableView;

    Button moreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_pool);
        moreButton = findViewById(R.id.id_more);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopmenu();
            }
        });

        initTimetableView();
    }

    /**
     * 初始化课程控件
     */
    private void initTimetableView() {
        mTimetableView = findViewById(R.id.id_timetableView);

        List<MySubject> mySubjects = SubjectRepertory.loadDefaultSubjects();
        mTimetableView.setSource(mySubjects)
                .setCurWeek(1)
                .setCurTerm("大三下学期")
                .showView();
    }

    /**
     * 显示弹出菜单
     */
    public void showPopmenu() {
        PopupMenu popup = new PopupMenu(this, moreButton);
        popup.getMenuInflater().inflate(R.menu.popmenu_colorpool, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.top1:
                        setColor(Color.BLUE,Color.YELLOW,Color.CYAN);
                        break;
                    case R.id.top2:
                        resetColor();
                        break;
                    case R.id.top3:
                        addColor(Color.BLUE,Color.YELLOW,Color.CYAN);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    /**
     * 设置指定的颜色，默认情况下颜色池中有一些颜色
     * 所以这里需要先清空一下颜色池
     * @param colors
     */
    public void setColor(int... colors){
        mTimetableView.getScheduleManager()
                .getColorPool()
                .clear()
                .add(colors);
        mTimetableView.updateView();
    }

    /**
     * 重置颜色池
     */
    public void resetColor(){
        mTimetableView.getScheduleManager()
                .getColorPool()
                .reset();
        mTimetableView.updateView();
    }

    /**
     * 追加颜色
     * @param colors
     */
    public void addColor(int... colors){
        mTimetableView.getScheduleManager()
                .getColorPool()
                .add(colors);
        mTimetableView.updateView();
    }
}

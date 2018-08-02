package com.zhuangfei.android_timetableview.views;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.R;
import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.OnDateBuildAapter;
import com.zhuangfei.timetable.utils.ScreenUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateActivity extends AppCompatActivity {

    TimetableView mTimetableView;
    Button moreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
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
        mTimetableView.source(mySubjects)
                .curWeek(1)
                .showView();
    }

    /**
     * 显示弹出菜单
     */
    public void showPopmenu() {
        PopupMenu popup = new PopupMenu(this, moreButton);
        popup.getMenuInflater().inflate(R.menu.popmenu_date, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.top1:
                        hideDateView();
                        break;
                    case R.id.top2:
                        showDateView();
                        break;
                    case R.id.top3:
                        customDateView();
                        break;
                    case R.id.top4:
                        cancelCustomDateView();
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
     * 隐藏日期栏
     */
    protected void hideDateView() {
        mTimetableView.hideDateView();
    }

    /**
     * 显示日期栏
     */
    protected void showDateView() {
        mTimetableView.showDateView();
    }

    /**
     * 自定义日期栏
     * 该段代码有点长，但是很好懂，仔细看看会有收获的，嘻嘻
     */
    protected void customDateView() {
        mTimetableView.callback(
                new OnDateBuildAapter() {
                    @Override
                    public View onBuildDayLayout(LayoutInflater mInflate, int pos, int width, int height) {
                        int newHeight=ScreenUtils.dip2px(DateActivity.this,30);
                        View v = mInflate.inflate(R.layout.item_custom_dateview, null, false);
                        TextView dayTextView = v.findViewById(R.id.id_week_day);
                        dayTextView.setText(dateArray[pos]);
                        layouts[pos] = v.findViewById(R.id.id_week_layout);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, newHeight);
                        layouts[pos].setLayoutParams(lp);

                        return v;
                    }

                    @Override
                    public View onBuildMonthLayout(LayoutInflater mInflate, int width, int height) {
                        int newHeight=ScreenUtils.dip2px(DateActivity.this,30);
                        View first = mInflate.inflate(R.layout.item_custom_dateview_first, null, false);
                        //月份设置
                        textViews[0] = first.findViewById(R.id.id_week_month);
                        layouts[0] = null;

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, newHeight);

                        int month = Integer.parseInt(weekDates.get(0));
                        first.setLayoutParams(lp);
                        textViews[0].setText(month + "\n月");
                        return first;

                    }
                })
                .updateDateView();
    }

    /**
     * 恢复默认日期栏
     */
    protected void cancelCustomDateView() {
        mTimetableView.callback((ISchedule.OnDateBuildListener) null)
                .updateDateView();
    }
}

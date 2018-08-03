package com.zhuangfei.android_timetableview.views;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zhuangfei.android_timetableview.R;
import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.model.Schedule;

import java.util.List;

/**
 * 旗标布局演示：点击空白格子后会有一个小方块，可监听事件
 *
 * @see com.zhuangfei.timetable.listener.ISchedule.OnFlaglayoutClickListener
 * @see com.zhuangfei.timetable.listener.OnFlaglayoutClickAdapter
 * @see com.zhuangfei.timetable.listener.ISchedule.OnSpaceItemClickListener
 * @see com.zhuangfei.timetable.listener.OnSpaceItemClickAdapter
 */
public class FlaglayoutActivity extends AppCompatActivity {

    TimetableView mTimetableView;
    Button moreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flaglayout);
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
                .maxSlideItem(10)
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display(scheduleList);
                    }
                })
                .callback(new ISchedule.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(View v, int day, int start) {
                        Toast.makeText(FlaglayoutActivity.this,
                                "长按:周" + day  + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .callback(new ISchedule.OnFlaglayoutClickListener() {
                    @Override
                    public void onFlaglayoutClick(int day, int start) {
                        mTimetableView.hideFlaglayout();
                        Toast.makeText(FlaglayoutActivity.this,
                                "点击了旗标:周" + (day + 1) + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .showView();
    }

    /**
     * 显示弹出菜单
     */
    public void showPopmenu() {
        PopupMenu popup = new PopupMenu(this, moreButton);
        popup.getMenuInflater().inflate(R.menu.popmenu_flaglayout, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.top1:
                        modifyFlagBgcolor(Color.RED);
                        break;
                    case R.id.top2:
                        resetFlagBgcolor();
                        break;
                    case R.id.top3:
                        cancelFlagBgcolor();
                        break;
                    case R.id.top4:
                        resetFlaglayout();
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
     * 修改旗标布局的背景色
     *
     * @param color
     */
    private void modifyFlagBgcolor(int color) {
        mTimetableView.flagBgcolor(color).updateFlaglayout();
    }

    /**
     * 重置旗标布局的背景色
     */
    private void resetFlagBgcolor() {
        mTimetableView.resetFlagBgcolor().updateFlaglayout();
    }

    /**
     * 取消旗标布局
     */
    private void cancelFlagBgcolor() {
        mTimetableView.isShowFlaglayout(false).updateFlaglayout();
    }

    /**
     * 显示旗标布局
     */
    private void resetFlaglayout() {
        mTimetableView.isShowFlaglayout(true).updateFlaglayout();
    }
}

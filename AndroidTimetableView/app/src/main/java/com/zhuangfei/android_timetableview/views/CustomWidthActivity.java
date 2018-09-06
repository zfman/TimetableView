package com.zhuangfei.android_timetableview.views;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuangfei.android_timetableview.R;
import com.zhuangfei.android_timetableview.custom.CustomOperater;
import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.listener.OnDateBuildAapter;
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter;
import com.zhuangfei.timetable.listener.OnSpaceItemClickAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.view.WeekView;

import java.util.List;

/**
 * 基础功能演示：
 * 1.周次选择栏
 * 2.透明背景
 * 3.点击监听
 * 4.颜色分配
 * 5.日期高亮
 * 6.日期计算
 * <p>
 * <p>
 * 该界面的代码注释会比较详细，建议从此处开始看起
 */
public class CustomWidthActivity extends AppCompatActivity{

    private static final String TAG = "CustomWidthActivity";

    //控件
    TimetableView mTimetableView;
    CustomOperater operater;
    List<MySubject> mySubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_width);

        mySubjects = SubjectRepertory.loadDefaultSubjects2();
        mySubjects.addAll(SubjectRepertory.loadDefaultSubjects());

        initTimetableView();
    }

    /**
     * 初始化课程控件
     */
    private void initTimetableView() {
        //获取控件
        mTimetableView = findViewById(R.id.id_timetableView);
        operater=new CustomOperater();
        operater.setWidthWeights(new float[]{1,3,1,1,1,1,1});
        mTimetableView.source(mySubjects)
                .curWeek(1)
                .maxSlideItem(10)
                .monthWidthDp(30)
                .operater(operater)
                //旗标布局点击监听
                .callback(new ISchedule.OnFlaglayoutClickListener() {
                    @Override
                    public void onFlaglayoutClick(int day, int start) {
                        mTimetableView.hideFlaglayout();
                        Toast.makeText(CustomWidthActivity.this,
                                "点击了旗标:周" + (day + 1) + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .callback(new OnDateBuildAapter(){
                    @Override
                    public View[] getDateViews(LayoutInflater mInflate, float monthWidth, float perWidth, int height) {
                        View[] views = new View[8];
                        views[0] = onBuildMonthLayout(mInflate, (int) monthWidth, height);
                        float[] weights=operater.getWeights();
                        float sum=0;
                        for(int i=0;i<weights.length;i++){
                            sum+=weights[i];
                        }
                        for (int i = 1; i < 8; i++) {
                            float rato=weights[i-1]/sum;
                            views[i] = onBuildDayLayout(mInflate, i, (int) (7*perWidth*rato), height);
                        }
                        return views;
                    }
                })
                .callback(new OnSpaceItemClickAdapter(){
                    @Override
                    public void onSpaceItemClick(int day, int start) {
                        //day:从0开始，start：从1开始
                        if(flagLayout==null) return;
                        float[] weights=operater.getWeights();
                        float sum=0;
                        for(int i=0;i<weights.length;i++){
                            sum+=weights[i];
                        }
                        int newItemWidth= (int) (super.itemWidth*7*weights[day]/sum);
                        float newMarLeft=0;
                        for(int i=0;i<day;i++){
                            newMarLeft+=(super.itemWidth*7*weights[i]/sum);
                        }
                        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(newItemWidth-marLeft*2,itemHeight);
                        lp.setMargins(monthWidth+(int)newMarLeft+marLeft,(start-1)*(itemHeight+marTop)+marTop,0,0);
                        flagLayout.setLayoutParams(lp);
                    }
                })
                .showView();
    }

    /**
     * 更新一下，防止因程序在后台时间过长（超过一天）而导致的日期或高亮不准确问题。
     */
    @Override
    protected void onStart() {
        super.onStart();
        mTimetableView.onDateBuildListener()
                .onHighLight();
    }
}

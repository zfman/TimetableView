package com.zhuangfei.android_timetableview.views;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuangfei.android_timetableview.R;
import com.zhuangfei.android_timetableview.config.OnMyConfigHandleAdapter;
import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleConfig;
import com.zhuangfei.timetable.view.WeekView;

import java.util.List;
import java.util.Set;

public class LocalConfigActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BaseFuncActivity";

    //控件
    TimetableView mTimetableView;
    WeekView mWeekView;

    Button moreButton;
    LinearLayout layout;
    TextView titleTextView;
    List<MySubject> mySubjects;

    //记录切换的周次，不一定是当前周
    int target = -1;
    boolean initFinish=false;

    ScheduleConfig mScheduleConfig;
    Set<String> configSet;
    public static final String CONFIG_NAME="local_config_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_config);
        moreButton = findViewById(R.id.id_more);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopmenu();
            }
        });

        mySubjects = SubjectRepertory.loadDefaultSubjects2();
        mySubjects.addAll(SubjectRepertory.loadDefaultSubjects());
        titleTextView = findViewById(R.id.id_title);
        layout = findViewById(R.id.id_layout);
        layout.setOnClickListener(this);

        initTimetableView();
    }

    /**
     * 初始化课程控件
     */
    private void initTimetableView() {
        mScheduleConfig=new ScheduleConfig(this);
        mScheduleConfig.setConfigName(CONFIG_NAME);

        //获取控件
        mTimetableView = findViewById(R.id.id_timetableView);
        mTimetableView.source(mySubjects)
                .curWeek(1)
                .curTerm("大三下学期")
                .maxSlideItem(10)
                .monthWidthDp(30)
                .configName(CONFIG_NAME)
                .callback(new OnMyConfigHandleAdapter())
                .showView();
    }

    /**
     * 更新一下，防止因程序在后台时间过长（超过一天）而导致的日期或高亮不准确问题。
     */
    @Override
    protected void onStart() {
        super.onStart();
        if(initFinish){
            mTimetableView.onDateBuildListener()
                    .onHighLight();
        }
    }

    /**
     * 显示弹出菜单
     */
    public void showPopmenu() {
        PopupMenu popup = new PopupMenu(this, moreButton);
        popup.getMenuInflater().inflate(R.menu.popmenu_base_func, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.top1:
                        loadLocalConfig();
                        break;
                    case R.id.top2:
                        clearLocalConfig();
                        break;
                    case R.id.top3:
                        exportLocalConfig();
                        break;
                    case R.id.top4:
                        loadLocalConfigSet();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_layout:
                //如果周次选择已经显示了，那么将它隐藏，更新课程、日期
                //否则，显示
                if (mWeekView.isShowing()) {
                    mWeekView.isShow(false);
                    titleTextView.setTextColor(getResources().getColor(R.color.app_course_textcolor_blue));
                    int cur = mTimetableView.curWeek();
                    mTimetableView.onDateBuildListener()
                            .onUpdateDate(cur, cur);
                    mTimetableView.changeWeekOnly(cur);
                } else {
                    mWeekView.isShow(true);
                    titleTextView.setTextColor(getResources().getColor(R.color.app_red));
                }
                break;
        }
    }

    /**
     * 设置本地配置项并使其生效
     */
    protected void loadLocalConfig() {
        mScheduleConfig.put(OnMyConfigHandleAdapter.CONFIG_SHOW_WEEKENDS,OnMyConfigHandleAdapter.VALUE_FALSE)
                .put(OnMyConfigHandleAdapter.CONFIG_SHOW_NOT_CUR_WEEK,OnMyConfigHandleAdapter.VALUE_FALSE)
                .put(OnMyConfigHandleAdapter.CONFIG_USERLESSS_COLOR,"#000000")
                .commit();
        mTimetableView.updateView();
    }

    /**
     * 清除本地配置项
     *
     */
    protected void clearLocalConfig() {
        mScheduleConfig.clear();
        mTimetableView.updateView();
    }

    /**
     * 本地配置导出到文本
     */
    private void exportLocalConfig() {
        Set<String> set=mScheduleConfig.export();
        configSet=set;
        String content="";
        for(String s:set) content+=s+"\n";
        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setTitle("配置导出")
                .setMessage(content)
                .setPositiveButton("我知道了",null);
        builder.create().show();
    }

    /**
     * 从文本导入本地配置
     */
    private void loadLocalConfigSet() {
        if(configSet==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(this)
                    .setTitle("配置导入")
                    .setMessage("还没有导出，先导出再来试试吧")
                    .setPositiveButton("我知道了",null);
            builder.create().show();
        }else{
            mScheduleConfig.load(configSet);
        }
        mTimetableView.updateView();
    }
}

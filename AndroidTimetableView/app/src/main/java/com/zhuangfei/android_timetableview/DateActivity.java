package com.zhuangfei.android_timetableview;

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

import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
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
        mTimetableView.setSource(mySubjects)
                .setCurWeek(1)
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
    protected void hideDateView(){
        mTimetableView.hideDateView();
    }

    /**
     * 显示日期栏
     */
    protected void showDateView(){
        mTimetableView.showDateView();
    }

    /**
     * 自定义日期栏
     * 该段代码有点长，但是很好懂，仔细看看会有收获的，嘻嘻
     *
     * @see CustomDateAdapter
     */
    protected void customDateView(){
        mTimetableView.setOnDateBuildListener(new CustomDateAdapter());
        mTimetableView.showDateView().updateDateView();
    }

    /**
     * 恢复默认日期栏
     */
    protected void cancelCustomDateView(){
        mTimetableView.setOnDateBuildListener(null);
        mTimetableView.showDateView().updateDateView();
    }


    /**
     * 大部分代码copy的OnDateBuildAapter
     * 你可以决定是否使用该方式，如果不愿使用该方式，
     * 可以隐藏日期栏，自行实现即可
     */
    class CustomDateAdapter implements ISchedule.OnDateBuildListener {

        //第一个：月份，之后7个表示周一至周日
        LinearLayout[] layouts=new LinearLayout[8];

        /**
         * 获取View数组
         * @param mInflate
         * @param perWidth 每个单位的宽度，其中月份占1份，星期占1.5份
         * @param height 默认的日期栏高度，可用可不用
         * @return
         */
        @Override
        public View[] getDateViews(LayoutInflater mInflate,float perWidth,int height) {
            int heightPx= ScreenUtils.dip2px(DateActivity.this,35);
            View[] views=new View[8];
            View first=mInflate.inflate(R.layout.item_custom_dateview_first,null,false);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams((int) perWidth,heightPx);
            layouts[0]=null;
            views[0]=first;
            first.setLayoutParams(lp);

            //星期设置
            lp=new LinearLayout.LayoutParams((int)(perWidth*1.5),heightPx);
            String[] dateArray=new String[]{"","周一","周二","周三","周四","周五","周六","周日"};
            for(int i=1;i<8;i++){
                View v=mInflate.inflate(R.layout.item_custom_dateview,null,false);
                TextView dayTextView=v.findViewById(R.id.id_week_day);
                layouts[i]=v.findViewById(R.id.id_week_layout);
                views[i]=v;
                layouts[i].setLayoutParams(lp);
                dayTextView.setText(dateArray[i]);
            }
            return views;
        }

        @Override
        public void onHighLight() {
            //初始化背景色
            int color=Color.parseColor("#F4F8F8");
            for(int i=1;i<8;i++){
                layouts[i].setBackgroundColor(color);
            }

            //高亮
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
            if(dayOfWeek<0) dayOfWeek=7;
            layouts[dayOfWeek].setBackgroundColor(Color.parseColor("#BFF6F4"));
        }

        @Override
        public void onUpdateDate() {
        }
    }
}

package com.zhuangfei.android_timetableview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.ProviderTestCase2;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.R;
import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.TimeSlideAdapter;
import com.zhuangfei.timetable.model.Schedule;

import java.util.List;

/**
 * 简单展示侧边栏的样式效果（默认的侧边栏简单配置）
 */
public class SlideActivity extends AppCompatActivity {

    TimetableView mTimetableView;
    Button moreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide1);
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
        popup.getMenuInflater().inflate(R.menu.popmenu_slide1, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.top1:
                        showTime();
                        break;
                    case R.id.top2:
                        hideTime();
                        break;
                    case R.id.top3:
                        modifySlideBgColor(Color.YELLOW);
                        break;
                    case R.id.top4:
                        modifyItemBgColor(Color.YELLOW);
                        break;
                    case R.id.top5:
                        modifyItemTextColor(Color.RED);
                        break;
                    case R.id.top6:
                        modifyItemTimeColor(Color.RED);
                        break;
                    case R.id.top7:
                        customSlideView();
                        break;
                    case R.id.top8:
                        cancelCustomSlideView();
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
     * 显示时间
     * 设置侧边栏构建监听，TimeSlideAdapter是控件实现的可显示时间的侧边栏
     * 只修改了侧边栏的属性，所以只更新侧边栏即可（性能高），没有必要更新全部（性能低）
     * @see TimeSlideAdapter
     */
    protected void showTime() {
        String[] times = new String[]{
                "8:00", "9:00", "10:10", "11:00",
                "15:00", "16:00", "17:00", "18:00",
                "19:30", "20:30","21:30","22:30"
        };
        TimeSlideAdapter slideAdapter = new TimeSlideAdapter();
        slideAdapter.setTimes(times);
        mTimetableView.getScheduleManager().setOnSlideBuildListener(slideAdapter);
        mTimetableView.updateSlideView();
    }

    /**
     * 隐藏时间
     * 将侧边栏监听置Null后，会默认使用默认的构建方法，即不显示时间
     * 只修改了侧边栏的属性，所以只更新侧边栏即可（性能高），没有必要更新全部（性能低）
     */
    protected void hideTime() {
        mTimetableView.getScheduleManager().setOnSlideBuildListener(null);
        mTimetableView.updateSlideView();
    }

    /**
     * 修改侧边栏背景
     * 使用new的方式会覆盖掉之前设置的侧边栏的属性值
     * 如果不想覆盖，可以这样：
     * TimeSlideAdapter adapter= (TimeSlideAdapter) mTimetableView.getScheduleManager()
     *      .getOnSlideBuildListener();
     * 使用该方式获取到控件的TimeSlideAdapter对象，对其设置属性即可
     *
     * 在类型强制类型转换之前，你应该调用过以下代码：
     * TimeSlideAdapter slideAdapter = new TimeSlideAdapter();
     * mTimetableView.getScheduleManager().setOnSlideBuildListener(slideAdapter);
     *
     * 否则在类型转换时会出现异常，请注意
     *
     * @param color
     */
    protected void modifySlideBgColor(int color){
        TimeSlideAdapter slideAdapter = new TimeSlideAdapter();
        slideAdapter.setBackground(color);
        mTimetableView.getScheduleManager().setOnSlideBuildListener(slideAdapter);
        mTimetableView.updateSlideView();
    }

    /**
     * 修改侧边项背景
     * @param color
     */
    protected void modifyItemBgColor(int color){
        TimeSlideAdapter slideAdapter = new TimeSlideAdapter();
        slideAdapter.setItemBackground(color);
        mTimetableView.getScheduleManager().setOnSlideBuildListener(slideAdapter);
        mTimetableView.updateSlideView();
    }

    /**
     * 修改侧边栏节次文本的颜色值
     * @param color
     */
    protected void modifyItemTextColor(int color){
        TimeSlideAdapter slideAdapter = new TimeSlideAdapter();
        slideAdapter.setTextColor(color);
        mTimetableView.getScheduleManager().setOnSlideBuildListener(slideAdapter);
        mTimetableView.updateSlideView();
    }

    /**
     * 修改侧边栏时间文本的颜色值
     * @param color
     */
    protected void modifyItemTimeColor(int color){
        String[] times = new String[]{
                "8:00", "9:00", "10:10", "11:00",
                "15:00", "16:00", "17:00", "18:00",
                "19:30", "20:30","21:30","22:30"
        };
        TimeSlideAdapter slideAdapter = new TimeSlideAdapter();
        slideAdapter.setTimes(times).setTimeTextColor(color);
        mTimetableView.getScheduleManager().setOnSlideBuildListener(slideAdapter);
        mTimetableView.updateSlideView();
    }

    /**
     * 自定义侧边栏效果
     * 使用自定义的布局文件实现的文字居顶部的效果（默认居中）
     */
    protected void customSlideView(){
        mTimetableView.getScheduleManager()
                .setOnSlideBuildListener(new ISchedule.OnSlideBuildListener() {
                    @Override
                    public void setBackground(LinearLayout layout) {
                        //暂时不需要关注
                    }

                    @Override
                    public int getSlideItemSize() {
                        //返回侧边栏的Size
                        return 12;
                    }

                    @Override
                    public View onBuildSlideItem(int pos, LayoutInflater inflater, int itemHeight, int marTop) {
                        //获取View并返回，注意设置marTop值
                        View v=inflater.inflate(R.layout.item_custom_slide,null,false);
                        TextView tv=v.findViewById(R.id.item_slide_textview);
                        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                itemHeight);
                        lp.setMargins(0,marTop,0,0);
                        tv.setLayoutParams(lp);
                        tv.setText((pos+1)+"");
                        return v;
                    }
                });

        mTimetableView.updateSlideView();
    }

    /**
     * 取消自定义的侧边栏，回到默认状态
     * 只需要将监听器置空即可
     */
    protected void cancelCustomSlideView(){
        mTimetableView.getScheduleManager().setOnSlideBuildListener(null);
        mTimetableView.updateSlideView();
    }
}

package com.zhuangfei.android_timetableview.views;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter;

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
        mTimetableView.source(mySubjects)
                .curWeek(1)
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
     *
     * @see OnSlideBuildAdapter
     */
    protected void showTime() {
        String[] times = new String[]{
                "8:00", "9:00", "10:10", "11:00",
                "15:00", "16:00", "17:00", "18:00",
                "19:30", "20:30", "21:30", "22:30"
        };
        OnSlideBuildAdapter slideAdapter = new OnSlideBuildAdapter();
        slideAdapter.setTimes(times);
        mTimetableView.callback(slideAdapter);
        mTimetableView.updateSlideView();
    }

    /**
     * 隐藏时间
     * 将侧边栏监听置Null后，会默认使用默认的构建方法，即不显示时间
     * 只修改了侧边栏的属性，所以只更新侧边栏即可（性能高），没有必要更新全部（性能低）
     */
    protected void hideTime() {
        mTimetableView.callback((ISchedule.OnSlideBuildListener) null)
                .updateSlideView();
    }

    /**
     * 修改侧边栏背景,默认的使用的是OnSlideBuildAdapter，
     * 所以可以强转类型
     *
     * @param color
     */
    protected void modifySlideBgColor(int color) {
        OnSlideBuildAdapter listener = (OnSlideBuildAdapter) mTimetableView.onSlideBuildListener();
        listener.setBackground(color);
        mTimetableView.updateSlideView();
    }

    /**
     * 修改侧边栏节次文本的颜色值
     *
     * @param color
     */
    protected void modifyItemTextColor(int color) {
        OnSlideBuildAdapter listener = (OnSlideBuildAdapter) mTimetableView.onSlideBuildListener();
        listener.setTextColor(color);
        mTimetableView.updateSlideView();
    }

    /**
     * 修改侧边栏时间文本的颜色值
     *
     * @param color
     */
    protected void modifyItemTimeColor(int color) {
        String[] times = new String[]{
                "8:00", "9:00", "10:10", "11:00",
                "15:00", "16:00", "17:00", "18:00",
                "19:30", "20:30", "21:30", "22:30"
        };
        OnSlideBuildAdapter listener = (OnSlideBuildAdapter) mTimetableView.onSlideBuildListener();
        listener.setTimes(times)
                .setTimeTextColor(color);
        mTimetableView.updateSlideView();
    }

    /**
     * 自定义侧边栏效果
     * 使用自定义的布局文件实现的文字居顶部的效果（默认居中）
     */
    protected void customSlideView() {
        mTimetableView.callback(
                new OnSlideBuildAdapter() {
                    @Override
                    public View getView(int pos, LayoutInflater inflater, int itemHeight, int marTop) {
                        //获取View并返回，注意设置marTop值
                        View v = inflater.inflate(R.layout.item_custom_slide, null, false);
                        TextView tv = v.findViewById(R.id.item_slide_textview);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                itemHeight);
                        lp.setMargins(0, marTop, 0, 0);
                        tv.setLayoutParams(lp);
                        tv.setText((pos + 1) + "");
                        return v;
                    }
                })
                .updateSlideView();
    }

    /**
     * 取消自定义的侧边栏，回到默认状态
     * 只需要将监听器置空即可
     */
    protected void cancelCustomSlideView() {
        mTimetableView.callback((ISchedule.OnSlideBuildListener) null)
                .updateSlideView();
    }
}

package com.zhuangfei.timetable.listener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.sample.R;
import com.zhuangfei.timetable.model.ScheduleSupport;
import com.zhuangfei.timetable.utils.ScreenUtils;

/**
 * 侧边栏构建监听的默认实现
 */

public class OnSlideBuildAdapter implements ISchedule.OnSlideBuildListener {
    @Override
    public void setBackground(LinearLayout layout) {
    }

    @Override
    public int getSlideItemSize() {
        return 12;
    }

    @Override
    public View onBuildSlideItem(int pos, LayoutInflater inflater,
                                 int itemHeight,int marTop) {
        View view=inflater.inflate(R.layout.item_slide_default,null,false);
        TextView textView=view.findViewById(R.id.item_slide_textview);

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                itemHeight);
        lp.setMargins(0,marTop,0,0);
        textView.setLayoutParams(lp);
        textView.setText((pos+1)+"");
        return view;
    }
}

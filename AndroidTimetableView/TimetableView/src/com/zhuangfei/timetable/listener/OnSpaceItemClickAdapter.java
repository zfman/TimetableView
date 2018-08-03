package com.zhuangfei.timetable.listener;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Liu ZhuangFei on 2018/8/3.
 */

public class OnSpaceItemClickAdapter implements ISchedule.OnSpaceItemClickListener {

    private static final String TAG = "OnSpaceItemClickAdapter";

    protected LinearLayout flagLayout;
    protected int itemHeight;
    protected int itemWidth;
    protected int monthWidth;
    protected int marTop;
    protected int marLeft;

    @Override
    public void onSpaceItemClick(int day, int start) {
        //day:从0开始，start：从1开始
        if(flagLayout==null) return;
        //itemWidth：是包含了边距的，所以需要减去
        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(itemWidth-marLeft*2,itemHeight);
        lp.setMargins(monthWidth+day*itemWidth+marLeft,(start-1)*(itemHeight+marTop)+marTop,0,0);
        flagLayout.setLayoutParams(lp);
    }

    @Override
    public void onInit(LinearLayout flagLayout,int monthWidth, int itemWidth, int itemHeight, int marTop,int marLeft) {
        this.flagLayout=flagLayout;
        this.itemHeight=itemHeight;
        this.itemWidth=itemWidth;
        this.monthWidth=monthWidth;
        this.marTop=marTop;
        this.marLeft=marLeft;
    }
}

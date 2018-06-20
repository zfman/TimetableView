package com.zhuangfei.timetable.listener;

import android.view.LayoutInflater;
import android.view.View;

import com.zhuangfei.android_timetableview.sample.R;

/**
 * 滚动布局构建监听的默认实现
 */

public class OnScrollViewBuildAdapter implements ISchedule.OnScrollViewBuildListener {
    @Override
    public View getScrollView(LayoutInflater mInflate) {
        return mInflate.inflate(R.layout.view_simplescrollview,null,false);
    }
}

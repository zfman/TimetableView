package com.zhuangfei.android_timetableview.config;

import android.support.annotation.NonNull;

import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;

/**
 * Created by Liu ZhuangFei on 2018/12/22.
 */
public class OnMyConfigHandleAdapter implements ISchedule.OnConfigHandleListener {

    public static final String CONFIG_SHOW_WEEKENDS="config_show_weekends";
    public static final String VALUE_TRUE="config_value_true";
    public static final String VALUE_FALSE="config_value_false";

    @Override
    public void onParseConfig(String key, String value, TimetableView mView) {
        if(mView==null||key==null||value== null) return;
        switch (key){
            case CONFIG_SHOW_WEEKENDS:
                if(value.equals(VALUE_TRUE)) mView.isShowWeekends(true);
                else mView.isShowWeekends(false);
                break;

                default:break;
        }
    }
}

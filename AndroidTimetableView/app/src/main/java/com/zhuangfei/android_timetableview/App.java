package com.zhuangfei.android_timetableview;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Liu ZhuangFei on 2018/7/28.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //内存泄漏检测
//        LeakCanary.install(this);
    }
}

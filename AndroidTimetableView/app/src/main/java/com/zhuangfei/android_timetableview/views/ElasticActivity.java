package com.zhuangfei.android_timetableview.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.zhuangfei.android_timetableview.R;
import com.zhuangfei.android_timetableview.model.MySubject;
import com.zhuangfei.android_timetableview.model.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;

import java.util.List;

/**
 * 展示如何使用自定义的滚动控件
 * 应用场景：滑动冲突解决、下拉刷新、其他滚动效果
 * 效果由用户定义，自定义ScrollView需要继承自SimpleScrollView
 * 本例实现反弹的效果
 */
public class ElasticActivity extends AppCompatActivity {

    TimetableView mTimetableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scroll_view);

        mTimetableView = findViewById(R.id.id_timetableView);
        List<MySubject> mySubjects = SubjectRepertory.loadDefaultSubjects();

        /**
         * 过程很简单，步骤如下：
         * 1.创建一个xml文件，命名为custom_myscrollview.xml
         * 2.拷贝一段代码至该文件中，具体内容可以参见custom_myscrollview.xml
         * 3.将根布局控件修改为自定义的控件，其他内容无需修改
         * 4.设置滚动布局构建监听并实现其方法，将自定义的xml转换为View返回即可
         *
         */
        mTimetableView.source(mySubjects)
                .callback(new ISchedule.OnScrollViewBuildListener() {
                    @Override
                    public View getScrollView(LayoutInflater mInflate) {
                        return mInflate.inflate(R.layout.custom_myscrollview, null, false);
                    }
                })
                .showView();
    }
}

package com.zhuangfei.android_timetableview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhuangfei.timetable.core.OnSubjectBindViewListener;
import com.zhuangfei.timetable.core.OnSubjectItemClickListener;
import com.zhuangfei.timetable.core.OnSubjectItemLongClickListener;
import com.zhuangfei.timetable.core.SubjectBean;
import com.zhuangfei.timetable.core.SubjectUtils;
import com.zhuangfei.timetable.core.TimetableView;
import com.zhuangfei.timetable.core.grid.SubjectGridView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import static com.zhuangfei.android_timetableview.R.*;

public class MainActivity extends Activity implements OnSubjectItemClickListener,
        OnClickListener, OnSubjectBindViewListener, OnSubjectItemLongClickListener {

    private TimetableView mTimetableView;
    private LinearLayout moreLayout;
    private TextView mTitleTextView;
    private LinearLayout backLayout;

    private int curWeek = 1;
    private List<SubjectBean> subjectBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        ActivityTools.setTransparent(this);
        inits();
    }

    private void inits() {
        mTitleTextView = (TextView) findViewById(id.id_title);
        moreLayout = (LinearLayout) findViewById(id.id_more);
        backLayout = (LinearLayout) findViewById(id.id_back);

        moreLayout.setOnClickListener(this);
        backLayout.setOnClickListener(this);

        subjectBeans = transform(MySubjectModel.loadDefaultSubjects());

        mTimetableView = (TimetableView) findViewById(id.id_timetableView);

        //使用默认的参数构造一个网格View,默认灰色
        SubjectGridView gridView=new SubjectGridView(this);

        //以下构造一个红色的网格
//        SubjectGridView redGridView=new SubjectGridView(this);
//        redGridView.setLineColor(Color.RED);

        mTimetableView.setDataSource(subjectBeans)
                .setCurTerm("大三上学期")
                .setCurWeek(curWeek)
                .bindTitleView(mTitleTextView)
                .setOnSubjectBindViewListener(this)
                .setOnSubjectItemClickListener(this)
                .setOnSubjectItemLongClickListener(this)
                .setMax(true)
                .setBottomLayer(gridView)
//                .setBottomLayer(redGridView)
                .showTimetableView();

        //调用过showSubjectView后需要调用changWeek()
        //第二个参数为true时在改变课表布局的同时也会将第一个参数设置为当前周
        //第二个参数为false时只改变课表布局
//        mTimetableView.changeWeek(curWeek, true);

    }

    /**
     * 自定义转换规则,将自己的课程对象转换为所需要的对象集合
     *
     * @param mySubjects
     * @return
     */
    public List<SubjectBean> transform(List<MySubject> mySubjects) {
        //待返回的集合
        List<SubjectBean> subjectBeans = new ArrayList<>();

        //保存课程名、颜色的对应关系
        Map<String, Integer> colorMap = new HashMap<>();
        int colorCount = 1;

        //开始转换
        for (int i = 0; i < mySubjects.size(); i++) {
            MySubject mySubject = mySubjects.get(i);
            //计算课程颜色
            int color;
            if (colorMap.containsKey(mySubject.getName())) {
                color = colorMap.get(mySubject.getName());
            } else {
                colorMap.put(mySubject.getName(), colorCount);
                color = colorCount;
                colorCount++;
            }
            //转换
            subjectBeans.add(new SubjectBean(mySubject.getName(), mySubject.getRoom(), mySubject.getTeacher(), mySubject.getWeekList(),
                    mySubject.getStart(), mySubject.getStep(), mySubject.getDay(), color, mySubject.getTime()));
        }
        return subjectBeans;
    }

    /**
     * Item点击处理
     *
     * @param subjectList 该Item处的课程集合
     */
    @Override
    public void onItemClick(View v, List<SubjectBean> subjectList) {
        int size = subjectList.size();
        String subjectStr = "";

        for (int i = 0; i < size; i++) {
            SubjectBean bean = subjectList.get(i);
            subjectStr += bean.getName() + "\n";
            subjectStr += "上课周次:" + bean.getWeekList().toString() + "\n";
            subjectStr += "时间:周" + bean.getDay() + "," + bean.getStart() + "至" + (bean.getStart() + bean.getStep() - 1) + "节上";
            if (i != (size - 1)) {
                subjectStr += "\n########\n";
            }
        }
        subjectStr += "\n";

        Toast.makeText(this, "该时段有" + size + "门课\n\n" + subjectStr, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示弹出菜单
     */
    public void showPopmenu() {
        PopupMenu popup = new PopupMenu(this, moreLayout);
        popup.getMenuInflater().inflate(menu.popmenu_subject, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case id.top1:
                        addSubject();
                        break;
                    case id.top2:
                        deleteSubject();
                        break;
                    case id.top3:
                        resetStatus();
                        break;
                    case id.top4:
                        changeWeekByRandom();
                        break;
                    case id.top5:
                        showTodaySubjects();
                        break;
                    case id.top6:
                        showTodayAllSubjects();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    protected String showSubjects(List<SubjectBean> beans) {
        String subjectStr="";
        for (SubjectBean bean : beans) {
            subjectStr += bean.getName() + "\n";
            subjectStr += "上课周次:" + bean.getWeekList().toString() + "\n\n";
        }

        return subjectStr;
    }

    //显示周一课程
    protected void showTodaySubjects() {
        //0表示周一，依次类推，6代表周日
        List<SubjectBean> beans = SubjectUtils.getTodaySubjects(subjectBeans, curWeek, 0);
        String subjectStr=showSubjects(beans);
        Toast.makeText(this, "周一有" + beans.size() + "门课要上\n\n" + subjectStr, Toast.LENGTH_SHORT).show();
    }

    //显示周一所有课程
    protected void showTodayAllSubjects() {
        List<SubjectBean> beans = SubjectUtils.getTodayAllSubjects(subjectBeans, 0);
        String subjectStr=showSubjects(beans);
        Toast.makeText(this, "周一共有" + beans.size() + "门课\n\n" + subjectStr, Toast.LENGTH_SHORT).show();
    }

    //随机切换周次
    protected void changeWeekByRandom() {
        int week = (int) (Math.random() * 20) + 1;
        mTimetableView.changeWeek(week, true);
    }

    //重置状态
    protected void resetStatus() {
        curWeek = 1;
        subjectBeans = transform(MySubjectModel.loadDefaultSubjects());
        mTimetableView.setDataSource(subjectBeans)
                .setCurTerm("大三上学期")
                .setCurWeek(curWeek)
                .showTimetableView();
    }

    //删除课程
    protected void deleteSubject() {
        int pos = (int) (Math.random() * subjectBeans.size());
        if (subjectBeans.size() > 0) {
            subjectBeans.remove(pos);
            mTimetableView.notifyDataSourceChanged();
        } else {
            Toast.makeText(this, "没有课程啦！", Toast.LENGTH_SHORT).show();
        }

    }

    //添加课程
    protected void addSubject() {
        int pos = (int) (Math.random() * subjectBeans.size());
        subjectBeans.add(subjectBeans.get(pos));
        mTimetableView.notifyDataSourceChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.id_more:
                showPopmenu();
                break;
            case id.id_back:
                ActivityTools.toHome(this);
                break;
            default:
                break;
        }
    }

    /**
     * 绑定TitleView，你可以自定义一个文本的填充规则，
     * 当文本需要变化时将由系统回调
     * <p>
     * 当有以下事件时将会触发该函数
     * 1.setCurWeek(...)被调用
     * 2.setCurTerm(...)被调用
     * 3.notifyDataSourceChanged()被调用
     * 4.showTimetableView()被调用
     * 5.changeWeek(...)被调用
     *
     * @param titleTextView 绑定的TextView
     */
    @Override
    public void onBindTitleView(TextView titleTextView, int curWeek, String curTerm, List<SubjectBean> subjectBeans) {
        String text = "第" + curWeek + "周" + ",共" + subjectBeans.size() + "门课";
        titleTextView.setText(text);
        this.curWeek=curWeek;
    }

    @Override
    public void onItemLongClick(View view, int day, int start) {
    }
}

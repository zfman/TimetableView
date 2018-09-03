package com.zhuangfei.timetable.model;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * 课程表的工具包，主要提供几个便捷的方法
 */
public class ScheduleSupport {
    private static final String TAG = "ScheduleSupport";

    //*****************
    // 日期相关方法
    // getDateStringFromWeek()
    // getWeekDate()
    //*****************

    /**
     * 根据需要算的周数和当前周数计算日期,
     * 用于周次切换时对日期的更新
     *
     * @param targetWeek 需要算的周数
     * @param curWeek    当前周数
     * @return 当周日期集合，共8个元素，第一个为月份（高亮日期的月份），之后7个为周一至周日的日期
     */
    public static List<String> getDateStringFromWeek(int curWeek, int targetWeek) {
        Calendar calendar = Calendar.getInstance();
        if (targetWeek == curWeek)
            return getDateStringFromCalendar(calendar);
        int amount = targetWeek - curWeek;
        calendar.add(Calendar.WEEK_OF_YEAR, amount);
        return getDateStringFromCalendar(calendar);
    }

    /**
     * 根据周一的时间获取当周的日期
     *
     * @param calendar 周一的日期
     * @return 当周日期数组
     */
    private static List<String> getDateStringFromCalendar(Calendar calendar) {
        List<String> dateList = new ArrayList<>();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        dateList.add((calendar.get(Calendar.MONTH) + 1) + "");
        for (int i = 0; i < 7; i++) {
            dateList.add(calendar.get(Calendar.DAY_OF_MONTH) + "");
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dateList;
    }

    /**
     * 获取本周的周一-周日的所有日期
     *
     * @return 8个元素的集合，第一个为月份，之后7个依次为周一-周日
     */
    public static List<String> getWeekDate() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setFirstDayOfWeek(Calendar.MONDAY);

        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayOfWeek) {
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
        }
        int day = calendar1.get(Calendar.DAY_OF_WEEK);
        calendar1.add(Calendar.DATE, 0);
        calendar1.add(Calendar.DATE, calendar1.getFirstDayOfWeek() - day);

        Date beginDate = calendar1.getTime();
        calendar1.add(Calendar.DATE, 6);
        Date endDate = calendar1.getTime();
        return getBetweenDates(beginDate, endDate);
    }

    /**
     * 获取两个日期之间的日期集合
     *
     * @param start
     * @param end
     * @return
     */
    private static List<String> getBetweenDates(Date start, Date end) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        List<String> result = new ArrayList<String>();
        result.add(sdf2.format(new Date()));
        result.add(sdf.format(start));
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        result.add(sdf.format(end));
        return result;
    }

    /**
     * 根据开学时间计算当前周
     *
     * @param startTime 满足"yyyy-MM-dd HH:mm:ss"模式的字符串
     * @return
     */
    public static int timeTransfrom(String startTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long start = sdf.parse(startTime).getTime();
            long end = new Date().getTime();
            long seconds = (end - start) / 1000;
            long day = seconds / (24 * 3600);
            int week = (int) (Math.floor(day / 7) + 1);
            return week;
        } catch (ParseException e) {
            return -1;
        }
    }

    //*****************
    // 课程工具方法
    //*****************

    /**
     * 模拟分配颜色，将源数据的colorRandom属性赋值，
     * 然后根据该属性值在颜色池中查找颜色即可
     *
     * @param schedules 源数据
     * @return colorRandom属性已有值
     */
    public static List<Schedule> getColorReflect(List<Schedule> schedules) {
        if (schedules == null || schedules.size() == 0) return null;

        //保存课程名、颜色的对应关系
        Map<String, Integer> colorMap = new HashMap<>();
        int colorCount = 1;

        //开始转换
        for (int i = 0; i < schedules.size(); i++) {
            Schedule mySubject = schedules.get(i);
            //计算课程颜色
            int color;
            if (colorMap.containsKey(mySubject.getName())) {
                color = colorMap.get(mySubject.getName());
            } else {
                colorMap.put(mySubject.getName(), colorCount);
                color = colorCount;
                colorCount++;
            }
            mySubject.setColorRandom(color);
        }

        return schedules;
    }

    /**
     * 内部使用的是:context.getResources().getDimensionPixelSize(dp);
     *
     * @param context
     * @param dp
     * @return
     */
    public static int getPx(Context context, int dp) {
        return context.getResources().getDimensionPixelSize(dp);
    }

    /**
     * 转换，将自定义类型转换为List<Schedule>
     *
     * @param dataSource 源数据集合
     * @return
     */
    public static List<Schedule> transform(List<? extends ScheduleEnable> dataSource) {
        List<Schedule> data = new ArrayList<>();
        for (int i = 0; i < dataSource.size(); i++) {
            if (dataSource.get(i) != null) data.add(dataSource.get(i).getSchedule());
        }
        return data;
    }

    /**
     * 将源数据拆分为数组的七个元素，每个元素为一个集合，
     * 依次为周一-周日的课程集合
     *
     * @param dataSource 源数据
     * @return
     */
    public static List<Schedule>[] splitSubjectWithDay(List<Schedule> dataSource) {
        List<Schedule>[] data = new ArrayList[7];
        if (dataSource == null) return data;
        for (int i = 0; i < data.length; i++) {
            data[i] = new ArrayList<>();
        }
        for (int i = 0; i < dataSource.size(); i++) {
            Schedule bean = dataSource.get(i);
            if (bean.getDay() != -1)
                data[bean.getDay() - 1].add(bean);
        }
        sortList(data);
        return data;
    }

    /**
     * 获取某天有课的课程
     *
     * @param scheduleList 数据集合
     * @param curWeek      当前周，以1开始
     * @param day          星期几，0：周一，1：周二，依次类推..周日：6
     * @return
     */
    public static List<Schedule> getHaveSubjectsWithDay(List<Schedule> scheduleList, int curWeek, int day) {
        List<Schedule> subjectBeanList = getAllSubjectsWithDay(scheduleList, day);
        List<Schedule> result = new ArrayList<>();
        for (Schedule bean : subjectBeanList) {
            if (isThisWeek(bean, curWeek)) {
                result.add(bean);
            }
        }
        return result;
    }

    /**
     * 获取某天的所有课程
     *
     * @param scheduleList 数据集合
     * @param day          星期几，0：周一，1：周二，依次类推..周日：6
     * @return
     */
    public static List<Schedule> getAllSubjectsWithDay(List<Schedule> scheduleList, int day) {
        List<Schedule> subjectBeanList = splitSubjectWithDay(scheduleList)[day];
        return subjectBeanList;
    }

    //****************
    // 课程查找
    //****************

    /**
     * 在data中查找与subject的start相同的课程集合
     *
     * @param subject
     * @param data
     * @return
     */
    public static List<Schedule> findSubjects(Schedule subject, List<Schedule> data) {
        List<Schedule> result = new ArrayList<>();
        if(subject==null||data==null) return result;
        for (int i = 0; i < data.size(); i++) {
            Schedule bean = data.get(i);
            if (bean.getStart() >= subject.getStart() && bean.getStart() < (subject.getStart() + subject.getStep()))
                result.add(data.get(i));
        }
        return result;
    }

    /**
     * 按照上课节次排序
     *
     * @param data
     */
    public static void sortList(List<Schedule>[] data) {
        for (int i = 0; i < data.length; i++)
            sortList(data[i]);
    }

    public static void sortList(List<Schedule> data) {
        int min;
        Schedule tmp;
        for (int m = 0; m < data.size() - 1; m++) {
            min = m;
            for (int k = m + 1; k < data.size(); k++) {
                if (data.get(min).getStart() > data.get(k).getStart()) {
                    min = k;
                }
            }
            tmp = data.get(m);
            data.set(m, data.get(min));
            data.set(min, tmp);
        }
    }

    /**
     * 判断该课是否为本周的
     *
     * @param cur_week
     * @return
     */
    public static boolean isThisWeek(Schedule subject, int cur_week) {
        List<Integer> weekList = subject.getWeekList();
        if (weekList.indexOf(cur_week) != -1) return true;
        return false;
    }

    /**
     * 根据当前周过滤课程，获取本周有效的课程（忽略重叠的）
     *
     * @param data
     * @param curWeek
     * @return
     */
    public static List<Schedule> fliterSchedule(List<Schedule> data, int curWeek, boolean isShowNotCurWeek) {
        if (data == null) return new ArrayList<>();
        Set<Schedule> result = new HashSet<>();

        if(!isShowNotCurWeek){
            List<Schedule> filter=new ArrayList<>();
            for(int i=0;i<data.size();i++){
                Schedule s=data.get(i);
                if(ScheduleSupport.isThisWeek(s,curWeek)) filter.add(s);
            }
            data=filter;
        }
        if(data.size()>=1){
            result.add(data.get(0));
        }
        for (int i = 1; i < data.size(); i++) {
            Schedule s = data.get(i);
            boolean is=true;
            for (int j = 0; j < i; j++) {
                Schedule s2 = data.get(j);
                if(s.getStart()>=s2.getStart()&&s.getStart()<=(s2.getStart()+s2.getStep()-1)){
                    is=false;
                    if(isThisWeek(s2,curWeek)){
                        break;
                    }else if(isThisWeek(s,curWeek)){
                        result.remove(s2);
                        result.add(s);
                    }
                }
            }
            if(is) result.add(s);
        }
        List<Schedule> list = new ArrayList<>(result);
        sortList(list);
        return list;
    }
}
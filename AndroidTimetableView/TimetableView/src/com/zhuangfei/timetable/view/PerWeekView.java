package com.zhuangfei.timetable.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.zhuangfei.android_timetableview.sample.R;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;
import com.zhuangfei.timetable.model.ScheduleSupport;
import com.zhuangfei.timetable.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 周次选择的每项View，自定义View显示每周课程的概览，
 * 有课的地方用亮色的圆点，没课的地方用暗色的圆点
 */

public class PerWeekView extends View {

    private static final String TAG = "PerWeekView";

    private int width;

    private int radius;
    private Paint lightPaint;
    private Paint grayPaint;
    private int lightColor;
    private int grayColor;

    /**
     * 数据源
     */
    private List<Schedule> dataSource;

    /**
     * 获取亮色的画笔
     * @return
     */
    public Paint getLightPaint() {
        return lightPaint;
    }

    /**
     * 获取暗色的画笔
     * @return
     */
    public Paint getGrayPaint() {
        return grayPaint;
    }

    /**
     * 设置亮色
     * @param lightColor 亮色
     * @return
     */
    public PerWeekView setLightColor(int lightColor) {
        this.lightColor = lightColor;
        invalidate();
        return this;
    }

    /**
     * 设置暗色
     * @param grayColor 暗色
     * @return
     */
    public PerWeekView setGrayColor(int grayColor) {
        this.grayColor = grayColor;
        invalidate();
        return this;
    }

    /**
     * 获取数据源
     * @return
     */
    public List<Schedule> getDataSource() {
        if(dataSource==null) dataSource=new ArrayList<>();
        return dataSource;
    }

    /**
     * 设置数据源
     * @param list
     * @param curWeek
     * @return
     */
    public PerWeekView setSource(List<? extends ScheduleEnable> list,int curWeek) {
        if(list==null) return this;
        setData(ScheduleSupport.transform(list),curWeek);
        return this;
    }

    /**
     * 设置数据源
     * @param list
     * @param curWeek
     * @return
     */
    public PerWeekView setData(List<Schedule> list,int curWeek) {
        if(list==null) return this;
        getDataSource().clear();
        for(int i=0;i<list.size();i++){
            Schedule schedule=list.get(i);
            if(ScheduleSupport.isThisWeek(schedule,curWeek)&&schedule.getStart()<=10&&schedule.getDay()<=5){
                getDataSource().add(schedule);
            }
        }
        invalidate();
        return this;
    }

    /**
     * 设置半径Px
     * @param radiusPx 半径
     * @return
     */
    public PerWeekView setRadiusInPx(int radiusPx) {
        this.radius = radius;
        return this;
    }

    /**
     * 设置半径Dp
     * @param radiusDp 半径
     * @return
     */
    public PerWeekView setRadiusInDp(int radiusDp) {
        setRadiusInPx(ScreenUtils.dip2px(getContext(),radiusDp));
        return this;
    }

    public PerWeekView(Context context) {
        this(context,null);
    }

    public PerWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);

        lightPaint=new Paint();
        lightPaint.setColor(lightColor);
        lightPaint.setAntiAlias(true);
        lightPaint.setStyle(Paint.Style.FILL);

        grayPaint=new Paint();
        grayPaint.setColor(grayColor);
        grayPaint.setAntiAlias(true);
        grayPaint.setStyle(Paint.Style.FILL);
    }

    private void initAttr(AttributeSet attrs) {
        int defRadius=ScreenUtils.dip2px(getContext(),2);
        TypedArray ta=getContext().obtainStyledAttributes(attrs, R.styleable.PerWeekView);
        grayColor=ta.getColor(R.styleable.PerWeekView_gray_color,Color.rgb(207,219,219));
        lightColor=ta.getColor(R.styleable.PerWeekView_light_color,Color.parseColor("#3FCAB8"));
        radius= (int) ta.getDimension(R.styleable.PerWeekView_radius,defRadius);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mar=(width-10*radius)/6;
        lightPaint.setColor(lightColor);
        grayPaint.setColor(grayColor);

        int[][] tmp=getArray();

        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(tmp[i][j]==0){
                    drawPoint(canvas,mar+radius+(mar+2*radius)*j,mar+radius+(mar+2*radius)*i,radius,grayPaint);
                }else{
                    drawPoint(canvas,mar+radius+(mar+2*radius)*j,mar+radius+(mar+2*radius)*i,radius,lightPaint);
                }
            }
        }
    }

    /**
     * 根据此数据源分析出一个二维数组
     * @return
     */
    public int[][] getArray(){
        int[][] arr=new int[10][5];
        int[][] tmp=new int[5][5];
        for(int i=0;i<10;i++){
            for(int j=0;j<5;j++){
                arr[i][j]=0;
            }
        }

        int start,end;
        for(int i=0;i<getDataSource().size();i++){
            Schedule schedule=getDataSource().get(i);
            start=schedule.getStart();
            end=schedule.getStart()+schedule.getStep()-1;
            for(int m=start;m<=end;m++){
                arr[m-1][schedule.getDay()-1]=1;
            }
        }

        int t=0;
        for(int i=0;i<10;i+=2){
            for(int j=0;j<5;j++){
                if(arr[i][j]==0&&arr[i+1][j]==0){
                    tmp[t][j]=0;
                }else{
                    tmp[t][j]=1;
                }
            }
            t++;
        }
        return tmp;
    }
    /**
     * 画点
     * @param canvas
     * @param x
     * @param y
     * @param radius
     * @param p
     */
    public void drawPoint(Canvas canvas,int x,int y,int radius,Paint p){
        canvas.drawCircle(x,y,radius, p);
    }
}

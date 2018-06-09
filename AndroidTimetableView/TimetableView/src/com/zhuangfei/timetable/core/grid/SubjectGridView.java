package com.zhuangfei.timetable.core.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhuangfei.timetable.core.OtherUtils;

/**
 * 自定义表格视图
 * Created by Liu ZhuangFei on 2018/5/3.
 */

public class SubjectGridView extends View {

    private static final String TAG = "SubjectGridView";

    Paint paint = new Paint();

    //布局的宽度、高度
    float width,height;

    //课程项的宽度、高度、间距
    int itemHeight;
    int itemWidth;
    int marTop,marLeft;

    int lineColor=Color.GRAY;

    /**
     * 根据制定的参数构造网格
     * @param context
     * @param itemWidth
     * @param itemHeight
     * @param marTop
     * @param matLeft
     */
    public SubjectGridView(Context context,int itemWidth,int itemHeight,int marTop,int matLeft) {
        super(context);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(lp);
        this.itemWidth=itemWidth;
        this.itemHeight=itemHeight;
        this.marTop=marTop;
        this.marLeft=matLeft;
    }

    public SubjectGridView(Context context) {
        this(context,null);
    }

    public SubjectGridView setLineColor(int lineColor) {
        this.lineColor = lineColor;
        invalidate();
        return this;
    }

    /**
     * 使用默认的参数构造网格
     * @param context
     * @param attrs
     */
    public SubjectGridView(Context context,AttributeSet attrs) {
        super(context, attrs);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(lp);
        itemHeight=OtherUtils.dip2px(context,55);
        marTop=OtherUtils.dip2px(context,3);
        marLeft=OtherUtils.dip2px(context,3);
    }

    //计算参数
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width=MeasureSpec.getSize(widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);

        //课程项分为了15等分，第一列占一份，其余七列每列占两份
        float each=width/15.0f;
        itemWidth=(int)each*2;
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(lineColor);
        paint.setStrokeWidth(1);

        //第一条横线
        canvas.drawLine(0,itemHeight+1,width,itemHeight+1,paint);
        //剩余横线
        for(int i=2;i<=12;i++){
            float startY=itemHeight*i+(i-1)*marTop+2;
            float stopY=startY;
            canvas.drawLine(0,startY,width,stopY,paint);
        }

        //竖线
        canvas.drawLine(itemWidth/2,0,itemWidth/2,height,paint);

        for(int j=1;j<=8;j++){
            float startX=itemWidth*j+itemWidth/2-1;
            float stopX=startX;
            canvas.drawLine(startX,0,stopX,height,paint);
        }
    }
}

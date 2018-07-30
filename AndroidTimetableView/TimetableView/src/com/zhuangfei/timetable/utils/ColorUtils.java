package com.zhuangfei.timetable.utils;

import android.graphics.Color;

/**
 * Created by Liu ZhuangFei on 2018/7/25.
 */

public class ColorUtils {

    /**
     * 合成指定颜色、指定透明度的颜色
     * @param color
     * @param alpha
     * @return
     */
    public static int alphaColor(int color,float alpha){
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & color;
        return a + rgb;
    }
}

package com.zhuangfei.timetable.utils;

public class ColorUtils {
	/**
	 * 对rgb色彩加入透明度
	 *
	 * @param alpha     透明度，取值范围 0.0f -- 1.0f.
	 * @param baseColor
	 * @return a color with alpha made from base color
	 */
	public static int getColorWithAlpha(float alpha, int baseColor) {
		int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
		int rgb = 0x00ffffff & baseColor;
		return a + rgb;
	}
}

package com.handmark.pulltorefresh.library.extras;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
	
	private ScrollViewListener scrollViewListener;
	float mDownPosX,mDownPosY;
	
	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	public MyScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		// TODO Auto-generated method stub
		super.onScrollChanged(x, y, oldx, oldy);
		if(scrollViewListener!=null){
			scrollViewListener.onScrollChanged(x, y, oldx, oldy);
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
	    final float x = ev.getX();
	    final float y = ev.getY();

	    final int action = ev.getAction();
	    switch (action) {
	        case MotionEvent.ACTION_DOWN:
	            mDownPosX = x;
	            mDownPosY = y;

	            break;
	        case MotionEvent.ACTION_MOVE:
	            final float deltaX = Math.abs(x - mDownPosX);
	            final float deltaY = Math.abs(y - mDownPosY);
	            // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
	            if (deltaX > deltaY) {
	                return false;
	            }
	    }

	    return super.onInterceptTouchEvent(ev);
	}

}

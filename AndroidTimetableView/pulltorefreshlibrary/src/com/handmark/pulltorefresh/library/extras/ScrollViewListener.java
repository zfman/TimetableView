package com.handmark.pulltorefresh.library.extras;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import android.widget.ScrollView;

public interface ScrollViewListener {
	void onScrollChanged(int x, int y, int oldx, int oldy);  
}

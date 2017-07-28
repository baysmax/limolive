package com.example.project.limolive.tencentlive.giftpage;


import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.project.limolive.R;

public class BasePager {
	public FrameLayout page_view;
	public Activity mActivity;
	public View mRootView;// 布局对象

	public BasePager(Activity activity) {
		mActivity = activity;
		initViews();
	}
	private void initViews() {
		mRootView = View.inflate(mActivity, R.layout.page_view, null);
		page_view = (FrameLayout) mRootView.findViewById(R.id.page_view_p);
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		
	}
}

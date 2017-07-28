package com.example.project.limolive.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * 作者：hpg on 2017/3/3 14:40
 * 功能：
 */
public class MyRelativeLayout extends RelativeLayout {

    private static int count = 0;
    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.e("onMeasure " + count++, "=>onMeasure called! widthMeasureSpec=" + widthMeasureSpec + ", heightMeasureSpec=" + heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.e("onSizeChanged " + count++, "=>onResize called! w="+w + ",h="+h+",oldw="+oldw+",oldh="+oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e("onLayout " + count++, "=>OnLayout called! l=" + l + ", t=" + t + ",r=" + r + ",b="+b);
    }

}

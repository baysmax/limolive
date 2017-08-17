package com.example.project.limolive.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 自动刷新Layout
 * <p>直接调用autoRefresh()即可触发onRefresh回调</p>
 *
 * @author hwj on 2016/8/8.
 */
public class RefreshViewPageLayout extends ViewPager {
    private float startY;
    private float startX;
    private int mTouchSlop;
    // 记录viewPager是否拖拽的标记
    private boolean mIsVpDragger;

    public RefreshViewPageLayout(Context context) {
        super(context);
    }

    public RefreshViewPageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        Log.i("下拉刷新","ViewPager...onInterceptHoverEvent...event.getAction()"+event.getAction());
        return super.onInterceptHoverEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("下拉刷新","ViewPager...onInterceptHoverEvent...ev.getAction()"+ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        Log.i("下拉刷新","ViewPager...onTouchEvent...ev.getAction()="+ev.getAction());
        return super.onTouchEvent(ev);
    }
    //    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                // 记录手指按下的位置
//                startY = ev.getY();
//                startX = ev.getX();
//                // 初始化标记
//                mIsVpDragger = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
//                if (mIsVpDragger) {
//                    return false;
//                }
//
//                // 获取当前手指位置
//                float endY = ev.getY();
//                float endX = ev.getX();
//                float distanceX = Math.abs(endX - startX);
//                float distanceY = Math.abs(endY - startY);
//                // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
//                if (distanceX > mTouchSlop && distanceX > distanceY) {
//                    mIsVpDragger = true;
//                    return false;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                // 初始化标记
//                mIsVpDragger = false;
//                break;
//        }
//        // 如果是Y轴位移大于X轴，事件交给swipeRefreshLayout处理。
//        return false;
//    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(VISIBLE);

            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

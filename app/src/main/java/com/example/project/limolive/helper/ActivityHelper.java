package com.example.project.limolive.helper;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;

import java.lang.ref.SoftReference;

/**
 * Created by hwj on 2016/7/19.
 * 方便管理Activity的类,由于是栈设计，只能先进后出，无法在某个位置增加或者删除
 * 需要注意的是，onDestroy只是销毁当前处于前台的Activity
 *
 *  注：本类没有处理在多线程状态下的实例化问题，仅仅使用普通单例模式
 */
public class ActivityHelper {

    private static final String TAG="ActivityHelper.class";
    private static ActivityHelper activityHelper;
    private static SparseArray<SoftReference<Activity>> activities;
    private static int activityCount=0;

    private ActivityHelper(){}

    public static ActivityHelper getInstance(){
        if(activityHelper!=null){
            return activityHelper;
        }else{
            return new ActivityHelper();
        }
    }

    /**
     * 向栈中添加Activity，计数器加1
     * @param activity
     */
    public synchronized void onCreate(Activity activity){
        if(activities==null){
            activities=new SparseArray<>();
        }
        activities.put(activityCount,new SoftReference<>(activity));
        activityCount++;
        Log.i(TAG,"after create activityCount="+activityCount);
    }

    /**
     * 获取处于顶部的Activity
     * @return
     */
    public synchronized Activity getActivity(){
        Activity activity=null;
        if(activityCount<0){
            Log.w(TAG,"栈中无数据！");
            return null;
        }
        if(activities!=null&&activities.get(activityCount-1)!=null){
            activity=activities.get(activityCount-1).get();
        }
        return activity;
    }


    /**
     * 删除当前Activity，只可以在具体子类Activity的onDestroy中调用，不可以
     * 在父类中调用
     */
    public synchronized void onDestroy(){
        if(activityCount<0){
            Log.w(TAG,"栈中无数据！");
            return;
        }
        if(activities!=null&&activities.get(activityCount-1)!=null){
            /*删除之后计数器减1*/
            activities.get(activityCount-1).get().finish();
            activityCount--;
        }
        Log.i(TAG,"after destroy activityCount="+activityCount);
    }

    /**
     * 删除所有Activity，一般用于退出程序或者退出登录跳到登录页面
     */
    public synchronized void onAllDestroy(){
        if(activityCount<0){
            Log.w(TAG,"栈中无数据！");
            return;
        }
        if(activities!=null&&activityCount>0){
            while(activityCount-1>=0){
                Activity act=activities.get(activityCount-1).get();
                if(act!=null&&!act.isFinishing()){
                    act.finish();
                }
                activities.remove(activityCount-1);
                activityCount--;
            }
        }
        Log.i(TAG,"after destroy activityCount="+activityCount);
    }

    /**
     * 退出程序
     */
    public synchronized void ExitApp(){
        try{
            onAllDestroy();
        }finally {
            System.exit(0);
        }
    }

    /**
     * 移除Handler所有的Message和Callback
     * @param handler
     */
    public synchronized void clearHandlerMessage(Handler handler){
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
    }


}

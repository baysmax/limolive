package com.example.project.limolive.presenter;

import android.content.Context;

/**
 * Created by hwj on 2016/8/24.
 * 管理众多Presenter的抽象类，用于存储一些公共的方法接口及变量
 * @author hwj
 */
public abstract class Presenter {

    public NotificationToActivity tellActivity;//向Activity发送通知

    /*Dialog Msg*/
    public static final String LOGINING="正在登录...";
    public static final String REGISTERING="正在注册...";
    public static final String CHANGING="正在修改...";
    public static final String ADDING="正在发送请求...";
    public static final String LOADING="正在加载...";
    public static final String DELETING="正在删除...";
    public static final String EXITING="正在退出...";
    public static final String LOGING_BE_DOWN="您的账户在另一处登录";

    public static final String NET_UNCONNECT="请检查网络!";


    protected Context context;

    public Presenter(Context context){
        this.context=context;
    }



    /**
     * 本接口定义Presenter变化通知Activity的动作，同时也可以作为在fragment和Activity之间通信
     * 的媒介
     */
    public interface NotificationToActivity{
        void presenterTakeAction(int action);
    }

    public void registerMsgToActivity(NotificationToActivity tellActivity){
        this.tellActivity=tellActivity;
    }

    /**
     * 子类实现分方法用于界面的刷新操作，数据的加载
     */
    public void refresh(){
    }

    /**
     * 在向网络请求数据的时候需要子类重写本方法
     */
    protected void getData(){
    }

    /**
     * 子类在具有分页场景的时候需要重写本方法
     * @param isClear 是否清除旧数据
     */
    protected void getDate(boolean isClear){
    }

    protected String getString(int resString){
        return context.getString(resString);
    }

    /**
     * 清除相关数据
     */
    public void clearTask(){
    }

}

package com.example.project.limolive.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * Created by hwj on 2016/7/29.
 * 登录管理器，用于缓存登录用户的相关信息
 */
public class LoginManager {
    private static LoginManager loginManager;


    private LoginManager(){}

    public static LoginManager getInstance() {
        if (loginManager == null) {
            loginManager = new LoginManager();
        }

        return loginManager;
    }
    /**
     * 首次打开app
     * @param context
     */
    public void firstEnter(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean("firstEnter",false).apply();
    }

    /**
     * 是否第一次进入app
     * @param context
     */
    public boolean isFirstEnter(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("firstEnter",true);
    }

    /**
     * 登录记录
     * @param context
     */
    public void login(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean("login",true).apply();
    }


    /**
     * 退出登录
     * @param context
     */
    public void loginOut(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean("login",false).apply();
    }

    /**
     * 首次配置app
     * @param context
     */
    public void firstSetting(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean("firstSetting",false).apply();
    }
    /**
     * 是否应该配置app
     * @param context
     */
    public boolean isFirstSetting(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("firstSetting",true);
    }

    /**
     * 是否登录
     * @param context
     */
    public boolean isLogin(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("login",false);
    }

    /**
     * 存储当前登录用户userid
     * @param username
     * @return
     */
    public void setUserID(Context context,String username){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("uid",username).apply();
    }

    /**
     * 获取当前登录用户userid
     * @param context
     * @return
     */
    public String getUserID(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("uid","");
    }
    /**
     * 存储当前登录用户phone
     * @param username
     * @return
     */
    public void setPhone(Context context,String username){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("phone",username).apply();
    }

    /**
     * 获取当前登录用户sig
     * @return
     */
    public String getSig(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("sig","");
    }
    /**
     * 存储当前登录用户sig
     * @param sig
     * @return
     */
    public void setSig(Context context,String sig){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("sig",sig).apply();
    }
    /**
     * 获取当前登录用户phone
     * @param context
     * @return
     */
    public String getPhone(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("phone","");
    }

    /**
     * 存储当前的登录时间
     * @param context
     * @return
     */
    public void saveLoginTime(Context context,long time){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putLong("login_time",time).apply();
    }

    /**
     * 获取登录时间
     * @param context
     * @return
     */
    public long getLoginTime(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getLong("login_time",0);
    }
    /**
     *
     * 获取当前登录用户头像地址
     * @param context
     * @return
     */

    public void setAvatar(Context context,String avatar){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("avatar",avatar).apply();
    }

    public String getAvatar(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("avatar","");
    }


    /**
     *
     * 获取当前登录用户名称
     * @param context
     * @return
     */

    public void setHostName(Context context,String hostName){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("hostName",hostName).apply();
    }

    public String getHostName(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("hostName","");
    }

    /**
     *
     * 获取当前登录房间号
     * @param context
     * @return
     */

    public void setRoomNum(Context context,String RoomNum){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("RoomNum",RoomNum).apply();
    }

    public String getRoomNum(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("RoomNum","");
    }
}

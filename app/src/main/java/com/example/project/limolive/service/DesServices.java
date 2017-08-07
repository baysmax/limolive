package com.example.project.limolive.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.project.limolive.tencentlive.presenters.LiveHelper;

/**
 * Created by AAA on 2017/8/1.
 */

public class DesServices extends Service {
    public static final String TAG = "DesServices";
    private MsgReceiver msgReceiver=null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.project.limolive.service");
        registerReceiver(msgReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    boolean isKill=false;
    private class MsgReceiver extends BroadcastReceiver{
        Intent intent=null;
         Thread thread=new Thread(){
            @Override
            public void run() {
                for (;;){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!intent.getStringExtra("111").equals("111")){
                        Log.i("tell","service tell=");
                        tellService();
                        onDestroy();
                    }
                }
            }
        };
        {
            thread.start();
        }
        @Override
        public void onReceive(final Context context, final Intent intent) {
            this.intent=intent;
            Log.i(TAG,"onReceive........");
        }
    }

    private void tellService() {
        if (isKill){
            Log.i(TAG,"tellService........");
            LiveHelper.tellstartExitRoom(getApplication());
        }
    }
}

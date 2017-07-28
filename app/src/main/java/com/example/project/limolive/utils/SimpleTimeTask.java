package com.example.project.limolive.utils;

import android.os.Handler;
import android.os.Message;

import java.util.TimerTask;

/**
 * Created by hwj on 2016/9/10.
 * @author hwj
 */
public class SimpleTimeTask extends TimerTask{
    private Handler mHandler;
    private final int PASS_WHAT=1;

    public SimpleTimeTask(Handler handler){
        this.mHandler=handler;
    }

    @Override
    public void run() {
        Message msg=Message.obtain();
        msg.what=PASS_WHAT;
        if(mHandler!=null){
            mHandler.sendMessage(msg);
        }
    }
}

package com.example.project.limolive.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.project.limolive.R;

/**
 * Created by AAA on 2017/9/19.
 */

public class SoundPlayUtils {
    // SoundPool对象
    public static SoundPool mSoundPlayer =null;
    public static SoundPlayUtils soundPlayUtils;
    // 上下文
    static Context mContext;


    private SoundPlayUtils(){
        if (mSoundPlayer==null){
            mSoundPlayer= new SoundPool(10,
                    AudioManager.STREAM_SYSTEM, 5);
        }
    }

    /**
     * 初始化
     *
     * @param context
     */
    public static SoundPlayUtils init(Context context) {
        if (soundPlayUtils == null) {
            soundPlayUtils = new SoundPlayUtils();
        }

        // 初始化声音
        mContext = context;

        mSoundPlayer.load(mContext, R.raw.yazu, 1);// 1
        mSoundPlayer.load(mContext, R.raw.yaosaiz, 1);// 2
        mSoundPlayer.load(mContext, R.raw.fapai, 1);// 3

        return soundPlayUtils;
    }

    /**
     * 播放声音
     *
     * @param soundID
     */
    public static void play(int soundID) {
        if (mSoundPlayer!=null){
            mSoundPlayer.play(soundID, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }
    public static void desPlay(){
        soundPlayUtils=null;
        mSoundPlayer=null;
        mContext=null;
    }
}

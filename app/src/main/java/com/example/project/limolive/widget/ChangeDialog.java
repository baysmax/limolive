package com.example.project.limolive.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.project.limolive.R;

/**
 * 修改头像性别Dialog
 * @author hwj on 2016/12/15.
 */

public class ChangeDialog extends Dialog{

    public static final int TAKE_PHOTO=1; //拍照
    public static final int SEX_SELECT=2; //性别选择
    private int defaultType;

    private TextView tv_select_title;
    private TextView tv_select_box1;
    private TextView tv_select_box2;
    private TextView tv_select_cancel;

    private Context context;


    public ChangeDialog(Context context,int type) {
        super(context,R.style.dialog_without_black_line);
        this.context=context;
        if(type!=1&&type!=2){
            defaultType =1;
        }else{
            defaultType =type;
        }
    }
    public ChangeDialog(Context context) {
        super(context,R.style.dialog_without_black_line);
        this.context=context;
        defaultType =1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change);
        //配置全屏
        WindowManager.LayoutParams params = getWindow().getAttributes();// 得到属性
        params.gravity = Gravity.BOTTOM;// 显示在底部
        int screenWidth=getContext().getResources().getDisplayMetrics().widthPixels;
        params.width = screenWidth;// 设置对话框的框
        getWindow().setAttributes(params);// 设置属性
        initView();
        setUpView();
    }

    private void setUpView() {
        switch(defaultType){
            case TAKE_PHOTO:  //拍照选择
                takePhoto();
                break;
            case SEX_SELECT:  //性别选择
                setSex();
                break;
        }
    }

    private void setSex() {
        tv_select_title.setText(context.getString(R.string.dialog_change_sex));
        tv_select_box1.setText(context.getString(R.string.dialog_change_male));
        tv_select_box2.setText(context.getString(R.string.dialog_change_female));
        tv_select_cancel.setText(context.getString(R.string.cancel));
    }

    private void takePhoto() {
        tv_select_title.setText(context.getString(R.string.dialog_change_avatar_title));
        tv_select_box1.setText(context.getString(R.string.dialog_change_avatar_take_photo));
        tv_select_box2.setText(context.getString(R.string.dialog_change_avatar_select_photo));
        tv_select_cancel.setText(context.getString(R.string.cancel));
    }


    private void initView(){
        tv_select_title= (TextView) findViewById(R.id.tv_select_title);
        tv_select_box1= (TextView) findViewById(R.id.tv_select_box1);
        tv_select_box2= (TextView) findViewById(R.id.tv_select_box2);
        tv_select_box2.setSelected(true);
        tv_select_cancel= (TextView) findViewById(R.id.tv_select_cancel);
        tv_select_cancel.setSelected(true);
    }

    /**
     * 对外暴露三个点击事件
     * @param listener
     */
    public void setCheckedFirst(View.OnClickListener listener){
        if(tv_select_box1!=null){
            tv_select_box1.setOnClickListener(listener);
        }
    }
    public void setCheckedSecond(View.OnClickListener listener){
        if(tv_select_box2!=null){
            tv_select_box2.setOnClickListener(listener);
        }
    }
    public void setCheckedThird(View.OnClickListener listener){
        if(tv_select_cancel!=null){
            tv_select_cancel.setOnClickListener(listener);
        }
    }
}

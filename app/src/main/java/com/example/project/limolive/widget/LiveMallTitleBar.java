package com.example.project.limolive.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.limolive.R;

/**
 * @author hwj on 2016/12/13
 */

public class LiveMallTitleBar extends RelativeLayout {

    protected RelativeLayout leftLayout;
    protected ImageView leftImage;
    protected RelativeLayout rightLayout;
    protected ImageView rightImage;
    protected TextView titleView;
    protected RelativeLayout titleLayout;
    protected TextView leftText;
    protected TextView rightText;

    protected RelativeLayout right_layout2;
    protected TextView right_text2;
    protected ImageView right_image2;



    public LiveMallTitleBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public LiveMallTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LiveMallTitleBar(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.live_mall_title_bar, this);
        leftLayout = (RelativeLayout) findViewById(R.id.left_layout);
        leftImage = (ImageView) findViewById(R.id.left_image);
        rightLayout = (RelativeLayout) findViewById(R.id.right_layout);
        rightImage = (ImageView) findViewById(R.id.right_image);
        titleView = (TextView) findViewById(R.id.title);
        titleLayout = (RelativeLayout) findViewById(R.id.root);
        leftText= (TextView) findViewById(R.id.left_text);
        rightText= (TextView) findViewById(R.id.right_text);
        right_layout2= (RelativeLayout) findViewById(R.id.right_layout2);
        right_text2= (TextView) findViewById(R.id.right_text2);
        right_image2= (ImageView) findViewById(R.id.right_image2);
        parseStyle(context, attrs);
    }


    private void parseStyle(Context context, AttributeSet attrs){
        if(attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LiveMallTitleBar);
            String title = ta.getString(R.styleable.LiveMallTitleBar_titleBarTitle);
            titleView.setText(title);

            Drawable leftDrawable = ta.getDrawable(R.styleable.LiveMallTitleBar_titleBarLeftImage);
            if (null != leftDrawable) {
                leftImage.setImageDrawable(leftDrawable);
            }
            Drawable rightDrawable = ta.getDrawable(R.styleable.LiveMallTitleBar_titleBarRightImage);
            if (null != rightDrawable) {
                rightImage.setImageDrawable(rightDrawable);
            }
            ta.recycle();
        }
    }

    /**
     * 设置Title
     * @param title
     */
    public void setTitle(String title){
        if(titleView!=null){
            titleView.setText(title);
        }
    }
    public void setTitle(String title,float textSize){
        if(titleView!=null){
            titleView.setTextSize(textSize);
            setTitle(title);
        }
    }
    /**
     * 获取TitleView
     * @return
     */
    public TextView getTitleView(){
        return titleView;
    }

    /**
     * 设置图片
     * @param resId
     */
    public void setLeftImageResource(int resId) {
        leftImage.setImageResource(resId);
    }

    public void setRightImageResource(int resId) {
        rightImage.setImageResource(resId);
    }
    public void setRightImageResource2(int resId) {
        right_image2.setImageResource(resId);
    }

    public void setLeftText(String text){
        leftText.setText(text);
    }
    public void setRightText(String text){
        rightText.setText(text);

    }


    /**
     * 设置左右点击事件
     * @param listener
     */
    public void setLeftLayoutClickListener(OnClickListener listener){
        leftLayout.setOnClickListener(listener);
    }

    public void setRightLayoutClickListener(OnClickListener listener){
        rightLayout.setOnClickListener(listener);
    }
    public void setRightLayoutClickListener2(OnClickListener listener){
        right_layout2.setOnClickListener(listener);
    }

    /**
     * 设置背景色
     * @param color
     */
    public void setBackgroundColor(int color){
        titleLayout.setBackgroundColor(color);
    }

}

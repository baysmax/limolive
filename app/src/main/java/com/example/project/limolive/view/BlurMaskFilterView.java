package com.example.project.limolive.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.ImageView;

import static android.R.attr.radius;

/**
 * Created by AAA on 2017/10/12.
 */

public class BlurMaskFilterView extends ImageView {
    private Paint mPaint;
    private int mWidth;

    private int mHeight;

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }
    public BlurMaskFilterView(Context context) {
        super(context);
        init();
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Shader shader = new RadialGradient(mWidth/2,mWidth/2,mWidth/2);
//        Shader shader = new RadialGradient();
//        Shader shader = new RadialGradient(fluorescencePointF.mPointF.x,fluorescencePointF
//                .mPointF.y,radius,COLOR_WHITE,COLOR_WHITE_SHADER,
//                Shader.TileMode.CLAMP);
//        mPaint.setShader(shader);
        canvas.drawCircle(mWidth/2,mWidth/2,(mWidth-2)/2,mPaint);
    }
}

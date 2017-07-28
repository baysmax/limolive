package com.example.project.limolive.sidebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.project.limolive.R;


/**
 * 作用: 右边的字母View
 *
 * @author LITP
 * @date 2016/11/2
 */

public class SideBar extends View {

    // 26个字母
    public static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    private int choose = -1;  // 选中的字母的y坐标

    //字母画笔
    private Paint paint = new Paint();

    private TextView mTextDialog;       //显示当前字母的文本框

    private int singleHeight;   //一个字母的空间

    Rect rect = new Rect();     //存放文字的高度

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        paint.setAntiAlias(true); //设置抗锯齿

        paint.setTextSize(DensityUtil.sp2px(10f)); //设置字母字体大小为12sp

        //获取一个字母实际的宽高到rect
        paint.getTextBounds("A", 0, 1, rect);

        //获取一个字母的空间
        singleHeight = (getHeight() - (getPaddingTop() + getPaddingBottom())) / 27;

    }

    /**
     * 为SideBar设置显示字母的TextView
     *
     * @param mTextDialog 在activity传递过来的textView
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }


    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //循环绘制字母
        for (int i = 0; i < letters.length; i++) {

            //paint.setTypeface(Typeface.DEFAULT_BOLD); //设置默认字体加粗

            // 选中的状态
            if (i == choose) {
                paint.setColor(getResources().getColor(R.color.light_color_blue)); //选中的字母改变颜色
                paint.setFakeBoldText(true); //设置字体为粗体
            } else {
                paint.setColor(getResources().getColor(R.color.darkgray)); //设置字体颜色
                paint.setFakeBoldText(false); //设置字体为正常
            }


            // x坐标等于中间-字符宽度的一半.
            float xPos = getWidth() / 2 - paint.measureText(letters[i]) / 2;

            //Y轴坐标
            float yPos = getPaddingTop() + singleHeight * i + rect.height();

            canvas.drawText(letters[i], xPos, yPos, paint); //绘制字母

        }
    }


    /**
     * 分发触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        final int action = event.getAction();

        final float y = event.getY();// 点击y坐标

        final int oldChoose = choose;  //上一个选中的字母

        // 点击y坐标所占总高度的比例  *   数组的长度就等于点击了 数组中的位置.
        final int c = (int) (y / (getHeight() - getPaddingBottom() - getPaddingTop()) * letters.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
                //抬起来的时候设置背景为透明
                //setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                //按下，滑动的时候设置背景为灰色
                //setBackgroundDrawable(new ColorDrawable(0x44000000));
                //setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) { //判断选中字母是否发生改变
                    if (c >= 0 && c < letters.length) {
                        if (listener != null) {
                            listener.onTouchLetterChanged(letters[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(letters[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        //设置选中字母在数组的位置
                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }


    // 触摸回调接口
    private OnTouchLetterChangedListener listener;


    public void setOnTouchLetterChangedListener(
            OnTouchLetterChangedListener onTouchLetterChangedListener) {
        this.listener = onTouchLetterChangedListener;
    }


    public interface OnTouchLetterChangedListener {
        /**
         * 触摸字母回调
         *
         * @param s 触摸的字符
         */
        void onTouchLetterChanged(String s);
    }
}

package com.example.project.limolive.tencentlive.giftpage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.project.limolive.R;
import com.example.project.limolive.tencentlive.utils.SPUtil;


/**
 * Created by Administrator on 2016/8/14.
 */
public class Page_two extends BasePager {
    private LinearLayout present_checked_1,present_checked_2,present_checked_3,present_checked_4,present_checked_5,present_checked_6,present_checked_7,present_checked_8;
    private SPUtil sp = SPUtil.getInstance(mActivity);
    public Page_two(Activity activity) {
        super(activity);
        View view1 = LayoutInflater.from(mActivity).inflate(R.layout.page1_view,
                null);
        initView(view1);
        page_view.addView(view1);
    }

    @Override
    public void initData() {
        super.initData();
        present_checked_1.setBackgroundResource(R.color.full_transparent);
        present_checked_2.setBackgroundResource(R.color.full_transparent);
        present_checked_3.setBackgroundResource(R.color.full_transparent);
        present_checked_4.setBackgroundResource(R.color.full_transparent);
        present_checked_5.setBackgroundResource(R.color.full_transparent);
        present_checked_6.setBackgroundResource(R.color.full_transparent);
        present_checked_7.setBackgroundResource(R.color.full_transparent);
        present_checked_8.setBackgroundResource(R.color.full_transparent);
        present_checked_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//拍手 1钻
                present_checked_1.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","8");
                sp.setString("score","1");
            }
        });
        present_checked_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//干杯 5钻
                present_checked_2.setBackgroundResource(R.drawable.present_chat);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","9");
                sp.setString("score","5");
            }
        });
        present_checked_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//糖果 10钻
                present_checked_3.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","10");
                sp.setString("score","10");
            }
        });
        present_checked_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//1314 200钻

                present_checked_4.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","11");
                sp.setString("score","200");
            }
        });
        present_checked_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//招财猪 166
                present_checked_5.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","12");
                sp.setString("score","166");
            }
        });
        present_checked_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//生日蛋糕 200钻
                present_checked_6.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","13");
                sp.setString("score","200");
            }
        });
        present_checked_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//城堡 300钻
                present_checked_7.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","14");
                sp.setString("score","300");
            }
        });
        present_checked_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 跑车 300钻
                present_checked_8.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","15");
                sp.setString("score","300");
            }
        });
    }

    private void initView(View view) {
        present_checked_1 = (LinearLayout) view.findViewById(R.id.present_checked_9);
        present_checked_2 = (LinearLayout) view.findViewById(R.id.present_checked_10);
        present_checked_3 = (LinearLayout) view.findViewById(R.id.present_checked_11);
        present_checked_4 = (LinearLayout) view.findViewById(R.id.present_checked_12);
        present_checked_5 = (LinearLayout) view.findViewById(R.id.present_checked_13);
        present_checked_6 = (LinearLayout) view.findViewById(R.id.present_checked_14);
        present_checked_7 = (LinearLayout) view.findViewById(R.id.present_checked_15);
        present_checked_8 = (LinearLayout) view.findViewById(R.id.present_checked_16);

    }
}

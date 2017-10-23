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
public class Page_one extends BasePager {
    private LinearLayout present_checked_1,present_checked_2,present_checked_3,present_checked_4,present_checked_5,present_checked_6,present_checked_7,present_checked_8;
    private SPUtil sp = SPUtil.getInstance(mActivity);
    public Page_one(Activity activity) {
        super(activity);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.gift_page_one,
                null);
        initView(view);
        page_view.addView(view);
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
            public void onClick(View v) {//飞吻 1钻石
                present_checked_1.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","0");
                sp.setString("score","1");
            }
        });
        present_checked_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 么么哒 5钻石
                present_checked_2.setBackgroundResource(R.drawable.present_chat);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","1");
                sp.setString("score","5");
            }
        });
        present_checked_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 红包 10钻石
                present_checked_3.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","2");
                sp.setString("score","8");
            }
        });
        present_checked_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//糖果 30

                present_checked_4.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","3");
                sp.setString("score","10");
            }
        });
        present_checked_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 666 66钻石
                present_checked_5.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                //柠檬啪
//                sp.setString("present_num_show","0");
//                sp.setString("p_type","4");
//                sp.setString("score","30");
                sp.setString("present_num_show","0");
                sp.setString("p_type","4");
                sp.setString("score","2888");
            }
        });
        present_checked_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//爱心 50钻
                present_checked_6.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","5");
                sp.setString("score","50");
            }
        });
        present_checked_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//魔杖 60钻石
                present_checked_7.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_8.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","6");
                sp.setString("score","60");
            }
        });
        present_checked_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//钻戒 80钻
                present_checked_8.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","7");
                sp.setString("score","80");
            }
        });
    }

    private void initView(View view) {
        present_checked_1 = (LinearLayout) view.findViewById(R.id.present_checked_1);
        present_checked_2 = (LinearLayout) view.findViewById(R.id.present_checked_2);
        present_checked_3 = (LinearLayout) view.findViewById(R.id.present_checked_3);
        present_checked_4 = (LinearLayout) view.findViewById(R.id.present_checked_4);
        present_checked_5 = (LinearLayout) view.findViewById(R.id.present_checked_5);
        present_checked_6 = (LinearLayout) view.findViewById(R.id.present_checked_6);
        present_checked_7 = (LinearLayout) view.findViewById(R.id.present_checked_7);
        present_checked_8 = (LinearLayout) view.findViewById(R.id.present_checked_8);

    }
}

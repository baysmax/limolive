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
public class Page_three extends BasePager {
    private LinearLayout present_checked_1,present_checked_2,present_checked_3,present_checked_4,present_checked_5,present_checked_6,present_checked_7;
    private SPUtil sp = SPUtil.getInstance(mActivity);
    public Page_three(Activity activity) {
        super(activity);
        View view2 = LayoutInflater.from(mActivity).inflate(R.layout.page2_view,
                null);
        initView(view2);
        page_view.addView(view2);
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
        present_checked_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present_checked_1.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","16");
                sp.setString("score","100");
            }
        });
        present_checked_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present_checked_2.setBackgroundResource(R.drawable.present_chat);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","17");
                sp.setString("score","120");
            }
        });
        present_checked_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present_checked_3.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","18");
                sp.setString("score","300");
            }
        });
        present_checked_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                present_checked_4.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","19");
                sp.setString("score","666");
            }
        });
        present_checked_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present_checked_5.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","20");
                sp.setString("score","888");
            }
        });
        present_checked_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present_checked_6.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                present_checked_7.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","21");
                sp.setString("score","999");
            }
        });
        present_checked_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present_checked_7.setBackgroundResource(R.drawable.present_chat);
                present_checked_2.setBackgroundResource(R.color.full_transparent);
                present_checked_3.setBackgroundResource(R.color.full_transparent);
                present_checked_4.setBackgroundResource(R.color.full_transparent);
                present_checked_5.setBackgroundResource(R.color.full_transparent);
                present_checked_6.setBackgroundResource(R.color.full_transparent);
                present_checked_1.setBackgroundResource(R.color.full_transparent);
                sp.setString("present_num_show","0");
                sp.setString("p_type","22");
                sp.setString("score","1314");
            }
        });
    }

    private void initView(View view) {
        present_checked_1 = (LinearLayout) view.findViewById(R.id.present_checked_17);
        present_checked_2 = (LinearLayout) view.findViewById(R.id.present_checked_18);
        present_checked_3 = (LinearLayout) view.findViewById(R.id.present_checked_19);
        present_checked_4 = (LinearLayout) view.findViewById(R.id.present_checked_20);
        present_checked_5 = (LinearLayout) view.findViewById(R.id.present_checked_21);
        present_checked_6 = (LinearLayout) view.findViewById(R.id.present_checked_22);
        present_checked_7 = (LinearLayout) view.findViewById(R.id.present_checked_23);

    }
}


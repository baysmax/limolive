package com.example.project.limolive.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.bean.mine.FansAttention;

import java.util.List;

/**
 * @author hwj on 2016/12/19.
 */

public class AttentionAdapter extends CommonAdapter<FansAttention>  {


    public AttentionAdapter(Context context, List<FansAttention> mDatas) {
        super(context, mDatas, R.layout.item_fans_layout);
    }

    @Override
    public void convert(ViewHolder helper, FansAttention item, int position) {
        TextView tv_nickname=helper.getView(R.id.tv_nickname);
        tv_nickname.setText(item.getNickname());
    }

}

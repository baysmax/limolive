package com.example.project.limolive.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.mine.FansAttention;
import com.example.project.limolive.presenter.FansAndAttention;
import com.example.project.limolive.tencentlive.utils.GlideCircleTransform;
import com.example.project.limolive.tencentlive.utils.UIUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * 粉丝
 * @author hwj on 2016/12/19.
 */

public class FansAdapter extends CommonAdapter<FansAttention> {
    private Context context;
    List<FansAttention> mDatas;
    FansAndAttention fansPersenter;
    public FansAdapter(Context context, List<FansAttention> mDatas, FansAndAttention fansPersenter) {
        super(context, mDatas, R.layout.item_fans_layout);
        this.context=context;
        this.mDatas=mDatas;
        this.fansPersenter=fansPersenter;
    }

    @Override
    public void convert(ViewHolder helper, FansAttention item, int position) {
        final FansAttention fansAttention = mDatas.get(position);
        TextView tv_nickname=helper.getView(R.id.tv_nickname);
        tv_nickname.setText(item.getNickname());
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.iv_user_head);
        final ImageView imageView = helper.getView(R.id.iv_select);
        if (fansAttention.getHeadsmall().contains("http://")){
            simpleDraweeView.setImageURI(fansAttention.getHeadsmall());
        }else {
            simpleDraweeView.setImageURI(ApiHttpClient.API_PIC+fansAttention.getHeadsmall());
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setSelected(true);
            }
        });
    }
}

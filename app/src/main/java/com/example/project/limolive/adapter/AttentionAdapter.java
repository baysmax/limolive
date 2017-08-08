package com.example.project.limolive.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.mine.FansAttention;
import com.example.project.limolive.presenter.FansAndAttention;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @author hwj on 2016/12/19.
 */

public class AttentionAdapter extends CommonAdapter<FansAttention>  {
    private Context context;
    List<FansAttention> mDatas;
    FansAndAttention fansPersenter;

    public AttentionAdapter(Context context, List<FansAttention> mDatas,FansAndAttention fansPersenter) {
        super(context, mDatas, R.layout.item_fans_layout);
        this.context=context;
        this.mDatas=mDatas;
        this.fansPersenter=fansPersenter;
    }
    boolean isFans=false;
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
                isFans=(!isFans);
                isFouc(imageView,isFans);
            }
        });
    }

    private void isFouc(ImageView imageView,boolean isFans) {
        imageView.setSelected(isFans);
    }

}

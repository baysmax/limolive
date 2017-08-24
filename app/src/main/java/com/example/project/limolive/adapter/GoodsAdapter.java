package com.example.project.limolive.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.GoodsDetails;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.taowu.RecommendBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.List;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * Created by AAA on 2017/8/22.
 */

public class GoodsAdapter extends RecyclerView.Adapter{

    private List<RecommendBean> list;
    private Context context;

    public GoodsAdapter(List<RecommendBean> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReHolder(View.inflate(context, R.layout.goodsmgs_item,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        final RecommendBean recommendBean = list.get(position);
        ReHolder holder= (ReHolder) holder1;
        holder.tv_price.setText(recommendBean.getShop_price());
        holder.tv_name.setText(recommendBean.getGoods_name());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(
                        context
                        , GoodsDetails.class
                )
                        .putExtra("goods_id",recommendBean.getGoods_id()));
            }
        });
        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCar(recommendBean.getCat_id());
            }
        });
        if (TextUtils.isEmpty(recommendBean.getOriginal_img())) {
            holder.iv_goods_iicon.setImageResource(R.mipmap.goods);
        } else {
            //String[] split = rb.getGoods_content().split(";");
            Log.i("获取普通商品","getOriginal_img"+recommendBean.getOriginal_img());
            Glide.with(context).load(ApiHttpClient.API_PIC + recommendBean.getOriginal_img()).into( holder.iv_goods_iicon);
        }

    }
    private void addCar(String id){
        /**
         * 添加购物车
         */
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        Api.addCar(LoginManager.getInstance().getUserID(context),id,"1","", new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("添加购物车","apiResponse="+apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    ToastUtils.showShort(context, "添加成功");
                } else {
                    ToastUtils.showShort(context,"此商品已经在购物车");
                }
            }
            @Override
            public void onFailure(String errMessage) {
                ToastUtils.showShort(context, errMessage);
                super.onFailure(errMessage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ReHolder extends RecyclerView.ViewHolder{
        TextView tv_kucun,tv_repertoryNum,tv_price,tv_name;
        ImageView iv_goods_iicon,shape,iv_add;
        RelativeLayout rl;
        public ReHolder(View itemView) {
            super(itemView);
            tv_kucun=itemView.findViewById(R.id.tv_kucun);
            tv_repertoryNum=itemView.findViewById(R.id.tv_repertoryNum);
            tv_price=itemView.findViewById(R.id.tv_price);
            iv_goods_iicon=itemView.findViewById(R.id.iv_goods_iicon);
            tv_name=itemView.findViewById(R.id.tv_name);
            shape=itemView.findViewById(R.id.shape);
            shape.setVisibility(View.GONE);
            iv_add=itemView.findViewById(R.id.iv_add);
            iv_add.setVisibility(View.VISIBLE);
            tv_kucun.setVisibility(View.GONE);
            tv_repertoryNum.setVisibility(View.GONE);
            rl=itemView.findViewById(R.id.rls);
        }
    }
}

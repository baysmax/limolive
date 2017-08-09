package com.example.project.limolive.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.ShoppingCartActivity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.taowu.RecommendBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.Arrays;
import java.util.List;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

public class FindGoodsList_Adapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<RecommendBean> list;
    //private RecommendBean rb;

    public FindGoodsList_Adapter(Context mContext, List<RecommendBean> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        RecommendBean rb = (RecommendBean) getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.findgoods_list, null);
            viewHolder.tv_howMoney = (TextView) convertView.findViewById(R.id.tv_howMoney);
            viewHolder.iv_goodspicture = (ImageView) convertView.findViewById(R.id.iv_goodspicture);
            viewHolder.tv_recommend_baokuan = (TextView) convertView.findViewById(R.id.tv_recommend_baokuan);
            viewHolder.iv_car = (ImageView) convertView.findViewById(R.id.iv_car);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (TextUtils.isEmpty(rb.getOriginal_img())) {
            viewHolder.iv_goodspicture.setImageResource(R.mipmap.goods);
        } else {
            //String[] split = rb.getGoods_content().split(";");
            Log.i("获取普通商品","getOriginal_img"+rb.getOriginal_img());
           Glide.with(mContext).load(ApiHttpClient.API_PIC + rb.getOriginal_img()).into(viewHolder.iv_goodspicture);
        }
        viewHolder.tv_recommend_baokuan.setText(rb.getGoods_name());
        viewHolder.tv_howMoney.setText("¥" + rb.getShop_price());

        viewHolder.iv_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCar(list.get(position).getGoods_id());
            }
        });

        return convertView;
    }

    private void addCar(String id){
        /**
         * 添加购物车
         */
        if (!NetWorkUtil.isNetworkConnected(mContext)) {
            ToastUtils.showShort(mContext, NET_UNCONNECT);
            return;
        }
        Api.addCar(LoginManager.getInstance().getUserID(mContext),id,"", new ApiResponseHandler(mContext) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                    Intent intent=new Intent(mContext,ShoppingCartActivity.class);
                    mContext.startActivity(intent);
                    ToastUtils.showShort(mContext, apiResponse.getMessage());
                } else {
                    Intent intent=new Intent(mContext,ShoppingCartActivity.class);
                    mContext.startActivity(intent);
                    ToastUtils.showShort(mContext,"此商品已经在购物车");
                }
            }
            @Override
            public void onFailure(String errMessage) {
                ToastUtils.showShort(mContext, errMessage);
                super.onFailure(errMessage);
            }
        });
    }

    class ViewHolder {
        TextView tv_howMoney, tv_recommend_baokuan;
        ImageView iv_goodspicture, iv_car;
    }
}

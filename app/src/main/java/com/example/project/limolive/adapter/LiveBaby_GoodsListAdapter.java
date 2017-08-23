package com.example.project.limolive.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.GoodsDetails;
import com.example.project.limolive.activity.ShoppingCartActivity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.taowu.GoodInfo;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.view.CustomProgressDialog;

import java.util.List;
import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * 作者：hpg on 2017/1/3 16:45
 * 功能：
 */
public class LiveBaby_GoodsListAdapter extends BaseAdapter {

    private Context context;
    private List<GoodInfo> mGoodInfo;
    private GoodsDetails mGoodsDetails;
    private CustomProgressDialog  mProgressDialog;
    public LiveBaby_GoodsListAdapter(Context context, List<GoodInfo> mGoodInfo) {
        this.context = context;
        this.mGoodInfo = mGoodInfo;
        Log.i("宝贝数据",mGoodInfo.toString());
        mGoodsDetails = new GoodsDetails();
    }

    @Override
    public int getCount() {
        return mGoodInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mGoodInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.findgoods_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.iv_goodspicture = (ImageView) convertView.findViewById(R.id.iv_goodspicture);
            viewHolder.tv_recommend_baokuan = (TextView) convertView.findViewById(R.id.tv_recommend_baokuan);//描述
            viewHolder.tv_howMoney = (TextView) convertView.findViewById(R.id.tv_howMoney);//价格
            viewHolder.iv_car = (ImageView) convertView.findViewById(R.id.iv_car);//购物车
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.i("宝贝数据",mGoodInfo.get(position).toString());
        if (mGoodInfo.get(position) != null) {
            if (null != mGoodInfo.get(position).getOriginal_img() && !" ".equals(mGoodInfo.get(position).getOriginal_img())) {
                Glide.with(context).load(ApiHttpClient.API_PIC + mGoodInfo.get(position).getOriginal_img()).into(viewHolder.iv_goodspicture);
            } else {
                viewHolder.iv_goodspicture.setBackgroundResource(R.drawable.button_bg1);
            }
            viewHolder.tv_recommend_baokuan.setText(mGoodInfo.get(position).getGoods_remark());
            viewHolder.tv_howMoney.setText(mGoodInfo.get(position).getShop_price());

            viewHolder.iv_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressDialog("正在处理，请稍后...");
                    addCar(mGoodInfo.get(position).getGoods_id());
                }
            });
        }
        return convertView;
    }
    public void showProgressDialog(String msg) {
        mProgressDialog = new CustomProgressDialog(context);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
    private class ViewHolder {
        ImageView iv_goodspicture;
        TextView tv_recommend_baokuan;
        TextView tv_howMoney;
        ImageView iv_car;
    }

    private void addCar(String goods_id){
        /**
         * 添加购物车
         */
        if (!NetWorkUtil.isNetworkConnected(context)) {
            mGoodsDetails.hideProgressDialog();
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        Api.addCar(LoginManager.getInstance().getUserID(context),goods_id,"","", new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                mGoodsDetails.hideProgressDialog();
                Log.i("添加购物车","apiResponse..."+apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    Intent intent=new Intent(context,ShoppingCartActivity.class);
                    context.startActivity(intent);
                    ToastUtils.showShort(context, apiResponse.getMessage());
                } else {
                    Intent intent=new Intent(context,ShoppingCartActivity.class);
                    context.startActivity(intent);
                    ToastUtils.showShort(context,"此商品已在购物车中");
                }
                hideProgressDialog();
            }
            @Override
            public void onFailure(String errMessage) {
                mGoodsDetails.hideProgressDialog();
                ToastUtils.showShort(context, errMessage);
                hideProgressDialog();
                super.onFailure(errMessage);
            }
        });
    }
}

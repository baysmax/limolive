package com.example.project.limolive.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.ShopCartBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * 购物车
 * <p>接口{@link CartButtonListener}即可回调Adapter的点击事件</p>
 * @author hwj on 2016/12/21.
 */

public class ShopCartAdapter extends CommonAdapter<ShopCartBean> implements View.OnClickListener{

    private CartButtonListener listener;


    public ShopCartAdapter(Context context, List<ShopCartBean> mDatas) {
        super(context, mDatas, R.layout.item_shop_cart);
    }

    @Override
    public void convert(ViewHolder helper, ShopCartBean item, int position) {
        attachView(helper,null,position);
    }

    private void attachView(ViewHolder helper, View view,int position) {
        ShopCartBean item=mDatas.get(position);
        ImageView iv_good_select=null;
        SimpleDraweeView iv_good_image=null;
        TextView tv_goods_name=null;
        Button btn_reduce=null;
        TextView tv_number_show=null;
        Button btn_add=null;
        TextView tv_goods_price=null;
        if(helper!=null){
            iv_good_select = helper.getView(R.id.iv_good_select);
            iv_good_image = helper.getView(R.id.iv_good_image);
            tv_goods_name= helper.getView(R.id.tv_goods_name);
            btn_reduce= helper.getView(R.id.btn_reduce);
            tv_number_show= helper.getView(R.id.tv_number_show);
            btn_add = helper.getView(R.id.btn_add);
            tv_goods_price= helper.getView(R.id.tv_goods_price);
        }else if(view!=null){
            iv_good_select = (ImageView) view.findViewById(R.id.iv_good_select);
            iv_good_image = (SimpleDraweeView) view.findViewById(R.id.iv_good_image);
            tv_goods_name = (TextView) view.findViewById(R.id.tv_goods_name);
            btn_reduce = (Button) view.findViewById(R.id.btn_reduce);
            tv_number_show = (TextView) view.findViewById(R.id.tv_number_show);
            btn_add = (Button) view.findViewById(R.id.btn_add);
            tv_goods_price = (TextView) view.findViewById(R.id.tv_goods_price);
        }

        if(iv_good_select!=null){
            if(item.getIsSelect()==0){  //未选择
                iv_good_select.setSelected(false);
            }else if(item.getIsSelect()==1){
                iv_good_select.setSelected(true);
            }
            iv_good_select.setTag(position);
            iv_good_select.setOnClickListener(this);
        }
        if(iv_good_image!=null){
            iv_good_image.setImageURI(ApiHttpClient.API_PIC+item.getOriginal_img());
        }
        if(tv_goods_name!=null){
            tv_goods_name.setText(item.getGoods_name());
        }
        if(btn_reduce!=null){
            btn_reduce.setTag(position);
            btn_reduce.setOnClickListener(this);
        }
        if(btn_add!=null){
            btn_add.setTag(position);
            btn_add.setOnClickListener(this);
        }
        if(tv_goods_price!=null){
            tv_goods_price.setText("¥" +item.getGoods_price());
        }
        if(tv_number_show!=null){
            tv_number_show.setText(item.getGoods_num());
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_add: //加
                if(listener!=null){
                    Object obj=v.getTag();
                    if(obj!=null&&obj instanceof Integer){
                        listener.addGoodNumber(v,(int)obj);
                    }
                }
                break;
            case R.id.btn_reduce: //减
                if(listener!=null){
                    Object obj=v.getTag();
                    if(obj!=null&&obj instanceof Integer){
                        listener.reduceNumber(v,(int)obj);
                    }
                }
                break;
            case R.id.iv_good_select: //选择取消选择
                if(listener!=null){
                    Object obj=v.getTag();
                    if(obj!=null&&obj instanceof Integer){
                        listener.isSelected(v,(int)obj);
                    }
                }
                break;
        }
    }

    /**
     * 购物车相关操作
     */
    public interface CartButtonListener{
        /**
         * 商品数量加1
         * @param v
         */
        void addGoodNumber(View v,int position);

        /**
         * 商品数量减1
         * @param v
         */
        void reduceNumber(View v,int position);

        /**
         * 是否选择
         * @param position
         */
        void isSelected(View v,int position);

    }

    /**
     * 更新item
     * 当前会出现，复用的相关bug，需要更正优化后使用
     */
    public void updateItem(ListView attachList,int index){
        if(attachList==null){
            return;
        }
        //得到第一个可显示控件的位置，
        int visiblePosition = attachList.getFirstVisiblePosition();
        if (index - visiblePosition >= 0){
            View view=attachList.getChildAt(index);
            if(view==null){
                return;
            }
            attachView(null,view,index);
        }
    }

    /**
     * 注册相关事件
     * @param listener
     */
    public void setCartButtonListener(CartButtonListener listener){
        this.listener=listener;
    }
}

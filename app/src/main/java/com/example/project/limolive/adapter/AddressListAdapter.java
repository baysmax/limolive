package com.example.project.limolive.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.bean.ShopAddress;

import java.util.List;

/**
 * 地址列表
 * @author hwj on 2016/12/20.
 */

public class AddressListAdapter extends CommonAdapter<ShopAddress> implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    private EditClickListener editClickListener;
    private CheckClickListener checkClickListener;


    public AddressListAdapter(Context context, List<ShopAddress> mDatas) {
        super(context, mDatas, R.layout.view_address_item);
    }

    @Override
    public void convert(ViewHolder helper, ShopAddress item, int position) {
        ImageView iv_edit_address=helper.getView(R.id.iv_edit_address);
        iv_edit_address.setOnClickListener(this);
        iv_edit_address.setTag(position);
        CheckBox ck = helper.getView(R.id.ck);
        ck.setOnCheckedChangeListener(this);
        ck.setTag(position);
        TextView tv_user_name=helper.getView(R.id.tv_user_name);
        TextView tv_user_phone=helper.getView(R.id.tv_user_phone);
        TextView tv_user_address=helper.getView(R.id.tv_user_address);

        tv_user_name.setText(item.getConsignee());
        //tv_user_phone.setText(item.getMobile());
        tv_user_phone.setText(item.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        tv_user_address.setText(item.getAddress());

        if("1".equals(item.getIs_default())){
            ck.setChecked(true);
        }else {
            ck.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        if(editClickListener!=null){
            editClickListener.onClick(v);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(checkClickListener!=null){
            checkClickListener.onCheckedChanged(compoundButton,b);
        }
    }

    /**
     * 点击
     */
    public interface EditClickListener{
        void onClick(View v);
    }

    /**
     * CheckBox点击
     */
    public interface CheckClickListener{
        void onCheckedChanged(CompoundButton compoundButton, boolean b);
    }

    public void setOnEditClickListener(EditClickListener listener){
        this.editClickListener=listener;
    }

    public void setOnCheckBoxClickListener(CheckClickListener listener){
        this.checkClickListener=listener;
    }
}

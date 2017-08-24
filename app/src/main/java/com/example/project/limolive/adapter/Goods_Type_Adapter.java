package com.example.project.limolive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.bean.BtnBean;
import com.example.project.limolive.bean.GoodsTypeBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAA on 2017/8/22.
 */

public class Goods_Type_Adapter extends RecyclerView.Adapter implements View.OnClickListener{
    private OnItemClickListener mOnItemClickListener = null;
    private List<TextView> tvlist=new ArrayList<>();

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
            for (int i=0;i<tvlist.size();i++){
                int tag = (int) tvlist.get(i).getTag();
                if (tag==(int)v.getTag()){
                    tvlist.get(i).setBackground(context.getDrawable(R.drawable.follow));
                }else {//@color/line_color3
                    tvlist.get(i).setBackground(context.getDrawable(R.color.line_color3));
                }
            }
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    private List<BtnBean> list;
    private Context context;

    public Goods_Type_Adapter(List<BtnBean> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TypeHolder holder = new TypeHolder(View.inflate(context, , null));
        View view = LayoutInflater.from(context).inflate(R.layout.item_btn, parent, false);
        TypeHolder vh = new TypeHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        //将position保存在itemView的Tag中，以便点击时进行获取
        BtnBean btnBean = list.get(position);
        TypeHolder holder= (TypeHolder) holder1;
        holder.btn.setText(btnBean.getName());
        if (position==0){
            holder.btn.setBackground(context.getDrawable(R.drawable.follow));
        }
        holder.itemView.setTag(position);
        holder.btn.setTag(position);
        tvlist.add(holder.btn);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    private class TypeHolder extends RecyclerView.ViewHolder{
        TextView btn;
        public TypeHolder(View itemView) {
            super(itemView);
            btn=itemView.findViewById(R.id.btn);
        }
    }
}

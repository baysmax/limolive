package com.example.project.limolive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected final int mItemLayoutId;
    protected List<T> mDatas;

    /**
     * 获取adapter中所有数据
     *
     * @return
     */
    public List<T> getAll() {
        return mDatas;
    }

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        if (mDatas == null)
            mDatas = new ArrayList<T>();
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    /**
     * 追加数据
     *
     * @param mDatas    新数据
     * @param clearable 是否清除旧数据
     */
    public void append(List<T> mDatas, boolean clearable) {
        if (mDatas == null)
            return;

        if (clearable)
            this.mDatas.clear();
        this.mDatas.addAll(mDatas);
    }

    /**
     * 清除旧数据
     */
    public void clearData() {
        this.mDatas.clear();
    }

    /**
     * 更新数据
     */
    public void updateData(int position, T t) {
        if (position >= 0 && position < mDatas.size()) {
            mDatas.set(position, t);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder helper, T item, int position);

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

}
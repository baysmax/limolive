package com.example.project.limolive.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.project.limolive.R;

/**
 * Created by AAA on 2017/8/29.
 */

public class MyListViews  extends ListView implements AbsListView.OnScrollListener {
        private View footer;//底部布局
        private int totalItemCount;//总数量
        private int lastVisibleItem;//最后一个可见的Item
        private boolean isLoading;//正在加载


        public void setiLoadListener(ILoadListener iLoadListener) {
            this.iLoadListener = iLoadListener;
        }

        ILoadListener iLoadListener;
        public MyListViews(Context context) {
            super(context);
            initView(context);
        }

        public MyListViews(Context context, AttributeSet attrs) {
            super(context, attrs);
            initView(context);
        }

        public MyListViews(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initView(context);
        }
        /**
         * 添加底部加载提示布局到listview
         * @param context
         */
        private void initView(Context context) {
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            footer=layoutInflater.inflate(R.layout.lx_footer_layout, null);
            footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
            this.addFooterView(footer);
            this.setOnScrollListener(this);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
        {
            this.lastVisibleItem=firstVisibleItem+visibleItemCount;
            this.totalItemCount=totalItemCount;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (totalItemCount==lastVisibleItem&&scrollState==SCROLL_STATE_IDLE) {
                if (!isLoading) {
                    isLoading=true;
                    //footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                    if (iLoadListener!=null){
                        iLoadListener.onLoad();//加载更多数据
                    }
                }
            }
        }
        //加载完毕
        public void loadComplete() {
            isLoading=false;
            //footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
        }
        public void setInterface(ILoadListener iLoadListener) {
            this.iLoadListener=iLoadListener;
        }
        //加载更多数据的回调接口
        public interface ILoadListener
        {
            void onLoad();
        }
}

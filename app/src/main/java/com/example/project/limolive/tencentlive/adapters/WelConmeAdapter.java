package com.example.project.limolive.tencentlive.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by AAA on 2017/7/28.
 */

public class WelConmeAdapter extends PagerAdapter {
        private Context context;
        private ArrayList<Integer> goodsList;

        public WelConmeAdapter(Context context, ArrayList<Integer> goodsList) {
            this.context = context;
            this.goodsList = goodsList;
        }
        @Override
        public int getCount() {
            return goodsList.size();
        }

        @Override
        public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public ImageView instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(context);
            iv.setImageResource(goodsList.get(position));
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView iv= (ImageView) object;
            container.removeView(iv);
        }


}

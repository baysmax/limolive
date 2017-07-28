package com.example.project.limolive.tencentlive.views.customviews;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.project.limolive.R;

/**
 * 作者：hpg on 2017/3/21 18:41
 * 功能：
 */
public class PopuGiftCount {
    private int popHight;
    private Context mContext;
    private View root;
    private TextView count;
    public PopuGiftCount(Context mContext,final View root,TextView count){
        this.mContext = mContext;
        this.root = root;
        this.count = count;

    }

    public void showPopuGftCount(){
        final int[] num = mContext.getResources().getIntArray(R.array.gift_group_count);
        final int[] drawables = {0, 0, 0, 0, R.drawable.ns_gift_count_v50, R.drawable.ns_gift_count_v99,
                R.drawable.ns_gift_count_v188, R.drawable.ns_gift_count_v520, R.drawable.ns_gift_count_v1314,
                R.drawable.ns_gift_count_v3344};
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        popHight = height - width*3/4 - height;
        LayoutInflater inflaterDl = LayoutInflater.from(mContext);
        View groupGift1 = (inflaterDl.inflate(R.layout.room_pop_chat_to, null));
        ListView listView1 = (ListView) groupGift1.findViewById(R.id.chat_list);

        int width1 = root.getWidth();
        int height1 = (int) mContext.getResources().getDimension(R.dimen.pop_group_gift_height);
        final PopupWindow popupWindow1 = new PopupWindow(groupGift1, width1, height1);
        listView1.setAdapter(new BaseAdapter() {

            @Override
            public View getView(int position, View arg1, ViewGroup arg2) {
                Holder h;
                if (arg1 == null) {
                    h = new Holder();
                    arg1 = View.inflate(mContext, R.layout.ns_live_pop_chat_to_item1, null);
                    h.text = (TextView) arg1.findViewById(R.id.text1);
                    h.image = (ImageView) arg1.findViewById(R.id.image);
                    arg1.setTag(h);
                } else {
                    h = (Holder) arg1.getTag();
                }
                h.text.setText(num[position] + "");
                if (drawables[position] == 0) {
                    h.image.setVisibility(View.INVISIBLE);
                } else {
                    h.image.setVisibility(View.VISIBLE);
                    h.image.setImageResource(drawables[position]);
                }
                return arg1;
            }

            @Override
            public long getItemId(int arg0) {
                return 0;
            }

            @Override
            public Object getItem(int arg0) {
                return null;
            }

            @Override
            public int getCount() {
                return num.length;
            }

            class Holder {
                TextView text;
                ImageView image;
            }

        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                count.setText(num[position] + "");
                popupWindow1.dismiss();
            }
        });
        popupWindow1.setOutsideTouchable(true);
        popupWindow1.setTouchable(true);
        popupWindow1.setFocusable(true);
        popupWindow1.setBackgroundDrawable(new ColorDrawable(000000));
        popupWindow1.showAtLocation(root, Gravity.BOTTOM | Gravity.LEFT, root.getLeft(), root.getHeight() - root.getPaddingTop());
    }
}

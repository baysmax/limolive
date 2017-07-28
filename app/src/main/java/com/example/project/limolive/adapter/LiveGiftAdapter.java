package com.example.project.limolive.adapter;


import android.content.Context;
import android.graphics.Bitmap.Config;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.tencentlive.model.GiftInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;


/**
 * 礼物适配器
 * 
 * @author wsc
 *
 * @email jacen@wscnydx.com
 * @date 2014-4-3 下午3:19:26
 */
public class LiveGiftAdapter extends BaseAdapter {

	private List<GiftInfo> mGiftList;
	private Context mContext;
	private DisplayImageOptions mOptions;
	private ImageLoader mImageLoader;
	private int giftClazz;
	private int page;
	private int itemhight;
	public static String[] stockselectGiftDate;
	private boolean isStock = false;
	private GridView giftGv;
	public static String lastSelectIndex="";
	public static FrameLayout lastSelect_item_fl;
	public boolean isStock() {
		return isStock;
	}

	public void setStock(boolean isStock) {
		this.isStock = isStock;
	}
	public void setStockSeclection() {
		if (!TextUtils.isEmpty(lastSelectIndex)) {
			stockselectGiftDate = lastSelectIndex.split(",");
		}
	}

	/**
	 * 
	 * @param mContext
	 * @param giftClazz
	 *            礼物类别
	 * @param i
	 *            礼物的page页
	 * @param data
	 *            礼物数据
	 */
	@SuppressWarnings("deprecation")
	public LiveGiftAdapter(Context mContext,GridView mGiftGridView ,int giftClazz, int i,
			List<GiftInfo> data) {
		this.mContext = mContext;
		this.giftClazz = giftClazz;
		this.giftGv = mGiftGridView;
		this.page = i;
		mImageLoader = ImageLoader.getInstance();
		mOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ns_live_gift_default)
				.bitmapConfig(Config.RGB_565).cacheInMemory(true)
				.cacheOnDisc(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.build();
		mGiftList = new ArrayList<GiftInfo>();
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();// 屏幕高度
		int width = wm.getDefaultDisplay().getWidth();// 屏幕宽度;
		itemhight = (height - width*3/4 - 30)
				* 25 / 37 * 115 / 123 / 2;
		for (int j = i * 8; j < (i + 1) * 8 && j < data.size(); j++) {
			mGiftList.add(data.get(j));
		}
	}

	@Override
	public int getCount() {
		return mGiftList.size();
	}

	@Override
	public Object getItem(int position) {
		return mGiftList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	/** 
     * 刷新指定item 
     *  
     *  index item在listview中的位置
     */  
    public void updateItem(String currentSelect,int currentIndex)  
    {  
        // 获取当前点击的view  
        View currentSelectItem = giftGv.getChildAt(currentIndex);  
        FrameLayout  currentSelect_fl =  (FrameLayout) currentSelectItem.findViewById(R.id.fl_gift_item); 
        if(currentSelect_fl!=null){
        	currentSelect_fl.setBackgroundResource(R.drawable.shape_mbgift_rbtn_press);
        }
		if(TextUtils.isEmpty(lastSelectIndex)){//第一次点击
        	lastSelectIndex = currentSelect;
        	lastSelect_item_fl = currentSelect_fl;
        	return;
        }
        if(!lastSelectIndex.equals(currentSelect) && lastSelect_item_fl != null){
        	lastSelect_item_fl.setBackgroundResource(R.drawable.shape_mbgift_rbtn_normal);
        	lastSelectIndex = currentSelect;
        	lastSelect_item_fl = currentSelect_fl;  
        }
    }  
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final GiftViewHold viewHold;
		GiftInfo gift = mGiftList.get(position);
		if (convertView == null) {
			viewHold = new GiftViewHold();
			convertView = View.inflate(mContext, R.layout.ns_live_gift_item,null);

			viewHold.gift_ll = (LinearLayout) convertView.findViewById(R.id.gift_ll);
			viewHold.fl_gift_item = (FrameLayout) convertView.findViewById(R.id.fl_gift_item);
			viewHold.gift_tag = (ImageView) convertView.findViewById(R.id.gift_tag);
			viewHold.giftThumb = (ImageView) convertView.findViewById(R.id.gift_thumb);
			viewHold.tvGiftName = (TextView) convertView.findViewById(R.id.gift_name);
			viewHold.tvGiftPrice = (TextView) convertView.findViewById(R.id.gift_price);

			LayoutParams linearParams = (LayoutParams) viewHold.gift_ll.getLayoutParams(); // 取控件textView当前的布局参数
			linearParams.height = itemhight;// 强制设置控件的高
			linearParams.width = LayoutParams.FILL_PARENT;
			viewHold.gift_ll.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件</pre>

			LayoutParams linearParams1 = (LayoutParams) viewHold.fl_gift_item.getLayoutParams(); // 取控件textView当前的布局参数
			linearParams1.height = itemhight * 11 / 20;// 强制设置控件的高
			linearParams1.width = itemhight * 11 / 20;// 强制设置控件的宽
			viewHold.fl_gift_item.setLayoutParams(linearParams1); // 使设置好的布局参数应用到控件</pre>
			FrameLayout.LayoutParams linearParams2 = (FrameLayout.LayoutParams) viewHold.gift_tag.getLayoutParams(); // 取控件textView当前的布局参数
			linearParams2.height = (int)(itemhight * 11 / 20 /3.8);// 强制设置控件的高
			linearParams2.width = itemhight * 11 / 20;// 强制设置控件的宽
			viewHold.gift_tag.setLayoutParams(linearParams2); // 使设置好的布局参数应用到控件</pre>
			convertView.setTag(viewHold);
		} else {
			viewHold = (GiftViewHold) convertView.getTag();
		}
		if(isStock){
			if (stockselectGiftDate != null&& Integer.parseInt(stockselectGiftDate[0]) == giftClazz&& Integer.parseInt(stockselectGiftDate[1]) == page&& Integer.parseInt(stockselectGiftDate[2]) == position) {
				viewHold.fl_gift_item.setBackgroundResource(R.drawable.shape_gift_rbtn_press);
				lastSelect_item_fl = viewHold.fl_gift_item;
			} else {
				viewHold.fl_gift_item.setBackgroundResource(R.drawable.shape_gift_rbtn_normal);
			}
		}
		viewHold.gift_tag.setVisibility(View.GONE);
		viewHold.tvGiftName.setText(gift.getName());
		viewHold.tvGiftName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		if(gift.getType() == 1){
			viewHold.gift_tag.setVisibility(View.VISIBLE);
			viewHold.gift_tag.setBackgroundResource(R.drawable.gift_tag_luck);
			if(gift.getTab() == 4){//抢星  周星
				viewHold.tvGiftName.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.drawable.weekstart_icon), null);
			}
		}else if(gift.getType() == 0){
			if(gift.getTab() == 3){//vip
				viewHold.gift_tag.setVisibility(View.VISIBLE);
				viewHold.gift_tag.setBackgroundResource(R.drawable.gift_tag_vip);
			}else if(gift.getTab() == 4){//抢星  周星
				viewHold.tvGiftName.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.drawable.weekstart_icon), null);
			}else if(gift.getTab() == 7){//守护
				viewHold.gift_tag.setVisibility(View.VISIBLE);
				viewHold.gift_tag.setBackgroundResource(R.drawable.gift_tag_guard);
			}else if(gift.getTab() == 8){//八富
				viewHold.gift_tag.setVisibility(View.VISIBLE);
				viewHold.gift_tag.setBackgroundResource(R.drawable.gift_tag_level8);
			}
		}
		if (isStock) {
			viewHold.tvGiftPrice.setText(gift.getNum() + "");
		} else {
			viewHold.tvGiftPrice.setText(gift.getPrice() + "九币");
		}
	//	displayImage(viewHold.giftThumb,Constants.LIVE__ROOM_GIFT_URL + gift.getGid() + ".png");
		return convertView;
	}

	private void displayImage(ImageView imageView, String url) {
		mImageLoader.displayImage(url, imageView, mOptions, null);
	}

	private final class GiftViewHold {
		public FrameLayout fl_gift_item;
		public ImageView giftThumb; // 礼物图像
		public ImageView gift_tag; // 图像标记
		public TextView tvGiftName; // 礼物名字
		public TextView tvGiftPrice;// 礼物价格
		public LinearLayout gift_ll;// 礼物项背景
	}
}

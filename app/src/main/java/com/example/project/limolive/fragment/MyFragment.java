package com.example.project.limolive.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.AccountDetailActivity;
import com.example.project.limolive.activity.CollectionListActivity;
import com.example.project.limolive.activity.FansAttentionActivity;
import com.example.project.limolive.activity.HistoryActivity;
import com.example.project.limolive.activity.MyShopActivity;
import com.example.project.limolive.activity.OrderActivity;
import com.example.project.limolive.activity.SettingActivity;
import com.example.project.limolive.activity.ShoppingCartActivity;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.model.LoginModel;
import com.example.project.limolive.provider.MineDataProvider;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by hpg on 2016/12/13
 * 我的 fragment
 * <p>本fragment需要注意购物车模块，查看{@link ShoppingCartActivity}，该类为购物车页面</p>
 */
public class MyFragment extends BaseFragment implements View.OnClickListener{

	private SimpleDraweeView iv_user_head;  //用户头像
	private RelativeLayout rl_all_order;//全部订单

	//用户名和电话
	private TextView tv_username;
	private TextView tv_phone;
	private TextView tv_shopping_car; //购物车
	private TextView tv_my_shop; //我的店铺
	private TextView tv_account_detail;//账单明细
	private TextView tv_collection;//收藏
	private TextView tv_shopping_history;//足迹

	private MineDataProvider provider; //个人信息

	private LinearLayout ll_fans;    //粉丝
	private LinearLayout ll_attention;//关注

	private TextView tv_wait_pay;  //待付款
	private TextView tv_wait_receive;  //待收货
	private TextView tv_wait_comment;  //待评价
	private TextView tv_return_shop;  //退款售后


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return setContentView(R.layout.fragment_my,inflater,container);
	}

	@Override
	protected void initView() {
		super.initView();
		loadTitle();
		provider=new MineDataProvider(getActivity());
		iv_user_head= (SimpleDraweeView) findViewById(R.id.iv_user_head);
		rl_all_order= (RelativeLayout) findViewById(R.id.rl_all_order);
		tv_username= (TextView) findViewById(R.id.tv_username);
		tv_phone= (TextView) findViewById(R.id.tv_phone);
		tv_shopping_car= (TextView) findViewById(R.id.tv_shopping_car);
		tv_my_shop= (TextView) findViewById(R.id.tv_my_shop);
		tv_account_detail= (TextView) findViewById(R.id.tv_account_detail);
		tv_collection= (TextView) findViewById(R.id.tv_collection);
		tv_shopping_history= (TextView) findViewById(R.id.tv_shopping_history);

		ll_fans= (LinearLayout) findViewById(R.id.ll_fans);
		ll_attention= (LinearLayout) findViewById(R.id.ll_attention);

		tv_wait_pay= (TextView) findViewById(R.id.tv_wait_pay);
		tv_wait_receive= (TextView) findViewById(R.id.tv_wait_receive);
		tv_wait_comment= (TextView) findViewById(R.id.tv_wait_comment);
		tv_return_shop= (TextView) findViewById(R.id.tv_return_shop);


		initEvent();
	}

	/**
	 * 设置数据
	 */
	private void setData() {
		LoginModel loginModel=provider.getMineInfo(LoginManager.getInstance().getPhone(getApplication()));
		if(loginModel!=null){
			tv_username.setText(loginModel.getNickname());
			tv_phone.setText(loginModel.getPhone());
			Log.i("头像地址","getAvatar.."+LoginManager.getInstance().getAvatar(getActivity()));
			Log.i("头像地址","loginModel.getHeadsmall().."+loginModel.getHeadsmall());
			if (loginModel.getHeadsmall().toString().contains("http://")){
				iv_user_head.setImageURI(loginModel.getHeadsmall());
			}else {
				iv_user_head.setImageURI(ApiHttpClient.API_PIC+loginModel.getHeadsmall());
			}

		}
	}

	@Override
	public void onResume() {
		super.onResume();
		setData();
	}

	private void initEvent() {
		rl_all_order.setOnClickListener(this);
		ll_fans.setOnClickListener(this);
		ll_attention.setOnClickListener(this);
		tv_shopping_car.setOnClickListener(this);
		tv_my_shop.setOnClickListener(this);
		tv_account_detail.setOnClickListener(this);
		tv_collection.setOnClickListener(this);
		tv_shopping_history.setOnClickListener(this);
		tv_wait_pay.setOnClickListener(this);
		tv_wait_receive.setOnClickListener(this);
		tv_wait_comment.setOnClickListener(this);
		tv_return_shop.setOnClickListener(this);
	}

	private void loadTitle() {
		setTitleString(getString(R.string.mine_fragment_title));
//		setLeftImage(R.mipmap.icon_return);
		setRightImage(R.mipmap.fenlei);
		setLeftRegionListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		if(title_bar_standard!=null){
			title_bar_standard.setRightImageResource2(R.mipmap.settings);
			title_bar_standard.setRightLayoutClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//消息
				}
			});
			title_bar_standard.setRightLayoutClickListener2(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//设置
					startActivity(new Intent(getActivity(),SettingActivity.class));
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.rl_all_order:
				lookOrder(0);
				break;
			case R.id.ll_fans:   //粉丝
				fansAndAttention(FansAttentionActivity.FANS);
				break;
			case R.id.ll_attention:  //关注
				fansAndAttention(FansAttentionActivity.ATTENTIONS);
				break;
			case R.id.tv_shopping_car:  //购物车
				shopCart();
				break;
			case R.id.tv_my_shop:  //我的店铺
				enterMyShop();
				break;
			case R.id.tv_account_detail:  //账单明细
				enterAccountDetail();
				break;
			case R.id.tv_collection:  //收藏
				collectionList();
				break;
			case R.id.tv_shopping_history:  //足迹
				history();
				break;
			case R.id.tv_wait_pay:  //待付款
				lookOrder(1);
				break;
			case R.id.tv_wait_receive:  //待收货
				lookOrder(2);
				break;
			case R.id.tv_wait_comment:  //待评价
				lookOrder(3);
				break;
			case R.id.tv_return_shop:  //售后
				lookOrder(4);
				break;
		}
	}

	/**
	 * 购物车
	 */
	private void shopCart() {
		Intent intent=new Intent(getActivity(),ShoppingCartActivity.class);
		startActivity(intent);
	}

	/**
	 * 足迹
	 */
	private void history() {
		Intent intent=new Intent(getActivity(),HistoryActivity.class);
		startActivity(intent);
	}

	/**
	 * 收藏列表
	 */
	private void collectionList() {
		Intent intent=new Intent(getActivity(),CollectionListActivity.class);
		startActivity(intent);
	}

	/**
	 * 账单明细
	 */
	private void enterAccountDetail() {
		Intent intent=new Intent(getActivity(),AccountDetailActivity.class);
		startActivity(intent);
	}

	/**
	 * 我的店铺
	 */
	private void enterMyShop() {
		Intent intent=new Intent(getActivity(),MyShopActivity.class);
		startActivity(intent);
	}

	/**
	 * 进入粉丝或者关注页面 type
	 * @param type  {@link FansAttentionActivity}FANS ATTENTIONS
     */
	private void fansAndAttention(int type){
		Intent intent=new Intent(getActivity(), FansAttentionActivity.class);
		intent.putExtra("fansOrAttention",type);
		startActivity(intent);
	}

	/**
	 * 查看订单
	 * @param page 0为全部订单，1、2、3……则依次
	 */
	private void lookOrder(int page) {
		Intent intent=new Intent(getActivity(), OrderActivity.class);
		intent.putExtra("orderPage",page);
		startActivity(intent);
	}
}

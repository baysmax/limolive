package com.example.project.limolive.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.AccountDetailActivity;
import com.example.project.limolive.activity.AfterSaleActivity;
import com.example.project.limolive.activity.CollectionListActivity;
import com.example.project.limolive.activity.FansAttentionActivity;
import com.example.project.limolive.activity.HistoryActivity;
import com.example.project.limolive.activity.MyApplyActivity;
import com.example.project.limolive.activity.MyShopActivity;
import com.example.project.limolive.activity.MyWalletActivity;
import com.example.project.limolive.activity.OrderActivity;
import com.example.project.limolive.activity.PersonInfoActivity;
import com.example.project.limolive.activity.SettingActivity;
import com.example.project.limolive.activity.ShoppingCartActivity;
import com.example.project.limolive.activity.UserRebackActivity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.model.LoginModel;
import com.example.project.limolive.presenter.LoginPresenter;
import com.example.project.limolive.provider.MineDataProvider;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.tencentlive.views.MyAccountActivity;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

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
	private LinearLayout ll_my_money;//柠檬币点击事件
	private TextView tvMembers;//关注人数
	private TextView tv_wait_pay;  //待付款
	private TextView tv_wait_receive;  //待收货
	private TextView tv_wait_comment;  //待评价
	private TextView tv_return_shop;  //退款售后
	private TextView tv_my_money_num; //主播钻石；
	private TextView tv_my_money_nums; //主播柠檬币；
	private TextView tv_grade; //等级
	private TextView tvfansMembers;//粉丝数量
	private TextView tv_jianyi;//用户反馈
	private RelativeLayout rl_cz;//充值
	private ImageView iv_settings;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = setContentView(R.layout.fragment_my, inflater, container);
		getFollow();
		getFans();
		return view;
	}

	private void getFans() {
		if (NetWorkUtil.isNetworkConnected(getContext())) {
			Api.getFansHandle(LoginManager.getInstance().getUserID(getContext()), String.valueOf(2), new ApiResponseHandler(getContext()) {
				@Override
				public void onSuccess(ApiResponse apiResponse) {
					Object parse = JSON.parse(apiResponse.getData());
					JSONArray objects = JSONArray.parseArray(apiResponse.getData());
					tvfansMembers.setText(String.valueOf(objects.size()));
					Log.i("main","getFans:"+apiResponse.toString());
				}

			});

		} else {
			ToastUtils.showShort(getContext(), "网络异常，请检查您的网络~");
		}


	}

	@Override
	protected void initView() {
		super.initView();
		loadTitle();
		rl_cz= (RelativeLayout) findViewById(R.id.rl_cz);
		iv_settings= (ImageView) findViewById(R.id.iv_settings);
		ll_my_money= (LinearLayout) findViewById(R.id.ll_my_money);
		provider=new MineDataProvider(getActivity());
		tvMembers=(TextView)findViewById(R.id.tv_attention_num);//关注
		tvfansMembers=(TextView)findViewById(R.id.tv_fans_num);//粉丝
		iv_user_head= (SimpleDraweeView) findViewById(R.id.iv_user_head);
		rl_all_order= (RelativeLayout) findViewById(R.id.rl_all_order);
		tv_username= (TextView) findViewById(R.id.tv_username);
		tv_phone= (TextView) findViewById(R.id.tv_phone);
		tv_shopping_car= (TextView) findViewById(R.id.tv_shopping_car);
		tv_my_shop= (TextView) findViewById(R.id.tv_my_shop);
		tv_account_detail= (TextView) findViewById(R.id.tv_account_detail);
		tv_collection= (TextView) findViewById(R.id.tv_collection);
		tv_shopping_history= (TextView) findViewById(R.id.tv_shopping_history);
		tv_my_money_nums= (TextView) findViewById(R.id.tv_my_money_num);//柠檬币
		tv_grade= (TextView) findViewById(R.id.tv_grade);//等级
		tv_my_money_num= (TextView) findViewById(R.id.tv_my_money_nums);//钻石
		tv_jianyi= (TextView) findViewById(R.id.tv_jianyi);//用户反馈
		ll_fans= (LinearLayout) findViewById(R.id.ll_fans);
		ll_attention= (LinearLayout) findViewById(R.id.ll_attention);

		tv_wait_pay= (TextView) findViewById(R.id.tv_wait_pay);
		tv_wait_receive= (TextView) findViewById(R.id.tv_wait_receive);
		tv_wait_comment= (TextView) findViewById(R.id.tv_wait_comment);
		tv_return_shop= (TextView) findViewById(R.id.tv_return_shop);
		//显示

		initEvent();
	}

	/**
	 * 设置数据
	 */
	private void setData() {
		getSelCoins();
		getGrade();
		LoginModel loginModel=provider.getMineInfo(LoginManager.getInstance().getPhone(getApplication()));
		if(loginModel!=null){
			tv_username.setText(loginModel.getNickname());
			tv_phone.setText("手机号:"+loginModel.getPhone());
			Log.i("头像地址","getAvatar.."+LoginManager.getInstance().getAvatar(getActivity()));
			Log.i("头像地址","loginModel.getHeadsmall().."+loginModel.getHeadsmall());
			if (loginModel.getHeadsmall().toString().contains("http://")){
				iv_user_head.setImageURI(loginModel.getHeadsmall());
			}else {
				iv_user_head.setImageURI(ApiHttpClient.API_PIC+loginModel.getHeadsmall());
			}

		}
	}

	private void getGrade() {
		if (!NetWorkUtil.isNetworkConnected(getActivity())) {
			ToastUtils.showShort(getActivity(), NET_UNCONNECT);
			return;
		}else {
			Api.live_grade(LoginManager.getInstance().getPhone(getActivity()),
					new ApiResponseHandler(getActivity()) {
						@Override
						public void onSuccess(ApiResponse apiResponse) {
							if (apiResponse.getCode()==Api.SUCCESS){
								String data = apiResponse.getData();
								JSONObject parse = (JSONObject) JSON.parse(data);
								String diamonds_coins = parse.getString("grade");
								tv_grade.setText("Lv"+diamonds_coins);
							}
						}
					});
		}
	}

	/*********
	 * 获取观众自己钻石
	 */
	private void getSelCoins() {
		if (!NetWorkUtil.isNetworkConnected(getActivity())) {
			ToastUtils.showShort(getActivity(), NET_UNCONNECT);
			return;
		}else {
			Api.getDiamonds(LoginManager.getInstance().getUserID(getActivity()),
					new ApiResponseHandler(getActivity()) {
						@Override
						public void onSuccess(ApiResponse apiResponse) {
							Log.i("钻石","apiResponse="+apiResponse.toString());
							if (apiResponse.getCode()==Api.SUCCESS){
								String data = apiResponse.getData();
								JSONObject parse = (JSONObject) JSON.parse(data);
								String diamonds_coins = parse.getString("diamonds_coins");
								Log.i("钻石","diamonds_coins:"+diamonds_coins);
								tv_my_money_num.setText(diamonds_coins + "钻石 ");
							}
						}
					});
		}


		if (NetWorkUtil.isNetworkConnected(getContext())) {
			Api.getMemberCoins(LoginManager.getInstance().getUserID(getContext()), new ApiResponseHandler(getContext()) {
				@Override
				public void onSuccess(ApiResponse apiResponse) {
					Log.i("获取成员剩余柠檬币", "apiResponse.." + apiResponse.toString());
					if (apiResponse.getCode() == 0) {
						JSONObject parse = JSON.parseObject(apiResponse.getData());
						String Sellemon_coins = parse.getString("charm");
						Log.i("获取成员剩余柠檬币", "1走没有");
						tv_my_money_nums.setText(Sellemon_coins);
					} else {
						ToastUtils.showShort(getContext(), apiResponse.getMessage());
					}
				}
			});

		} else {
			ToastUtils.showShort(getContext(), "网络异常，请检查您的网络~");
		}

	}
	/**
	 * 主播的关注人数
	 */
	public void getFollow() {

		if (!NetWorkUtil.isNetworkConnected(getContext())) {
			ToastUtils.showShort(getContext(), NET_UNCONNECT);
			return;
		} else {
			Api.getFollows(LoginManager.getInstance().getUserID(getContext()), new ApiResponseHandler(getContext()) {
				@Override
				public void onSuccess(ApiResponse apiResponse) {
					Log.i("主播的关注人数", "apiResponse" + apiResponse.toString());

					if (apiResponse.getCode() == 0) {
						//主播的关注人数
						JSONObject parse = (JSONObject) JSON.parse(apiResponse.getData());
						String follow_nums = parse.getString("follow_nums");
						Log.i("main","getFollow:"+follow_nums);
						tvMembers.setText(follow_nums + "");

					}
				}

				@Override
				public void onFailure(String errMessage) {
					super.onFailure(errMessage);
					ToastUtils.showShort(getContext(), errMessage);
				}
			});
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		setData();
		getFollow();
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
		ll_my_money.setOnClickListener(this);
		iv_settings.setOnClickListener(this);
		rl_cz.setOnClickListener(this);
		tv_jianyi.setOnClickListener(this);
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
			case R.id.iv_settings://设置
				//startActivity(new Intent(getActivity(),SettingActivity.class)); UserRebackActivity
				Intent personInfo = new Intent(getActivity(), PersonInfoActivity.class);
				startActivity(personInfo);//个人资料
				break;
			case R.id.rl_cz://充值
				goWallet();
				break;
			case R.id.tv_jianyi://充值
				startActivity(new Intent(getActivity(), UserRebackActivity.class));//用户反馈
				break;
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
				lookOrder(3);
				break;
			case R.id.tv_wait_comment:  //待评价
				lookOrder(4);
				break;
			case R.id.tv_return_shop:  //售后
				Intent intent = new Intent(getActivity(), AfterSaleActivity.class);
				intent.putExtra("type","1");
				startActivity(intent);
				break;
			case R.id.ll_my_money:
				//充值
				//goAccount();
				goWallet();
				break;
		}
	}

	private void goWallet() {
		Intent sendPres = new Intent(getActivity(), MyWalletActivity.class);
		startActivity(sendPres);
	}

	private void goAccount() {
		Intent sendPres = new Intent(getActivity(), MyAccountActivity.class);
		startActivity(sendPres);
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
	if (!NetWorkUtil.isNetworkConnected(getActivity())) {
			ToastUtils.showShort(getActivity(), NET_UNCONNECT);
			return;
		}
		Api.myOrder(LoginManager.getInstance().getUserID(getActivity()), new ApiResponseHandler(getActivity()) {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				Log.i("店铺","apiResponse"+apiResponse.toString());
				if (apiResponse.getCode()==Api.CANCLE){
					Intent intent=new Intent(getActivity(),MyApplyActivity.class);
					startActivity(intent);
				}else {
					 if (apiResponse.getCode()==-2){
						ToastUtils.showShort(getActivity(),"审核未通过");
					}else {
						 Intent intent=new Intent(getActivity(),MyShopActivity.class);
						 startActivity(intent);
					 }
				}
			}

			@Override
			public void onFailure(String errMessage) {
				Log.i("店铺","errMessage"+errMessage);
				super.onFailure(errMessage);
			}
		});

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

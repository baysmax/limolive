package com.example.project.limolive.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.GoodsContentAdapter;
import com.example.project.limolive.adapter.GoodsManageAdapter;
import com.example.project.limolive.adapter.MoreLiveingAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.BtnBean;
import com.example.project.limolive.bean.FrendUserInfo;
import com.example.project.limolive.bean.RankBean;
import com.example.project.limolive.bean.StandardBean;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.bean.order.OrderBean;
import com.example.project.limolive.bean.taowu.GoodsContentBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.tencentim.model.FriendInfoBean;
import com.example.project.limolive.tencentim.widget.CircleImageView;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.tencentlive.utils.GlideCircleTransform;
import com.example.project.limolive.tencentlive.utils.SxbLog;
import com.example.project.limolive.tencentlive.utils.UIUtils;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.view.CustomDialog;
import com.example.project.limolive.view.MyListview;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * 作者：hpg on 2016/12/23 15:06
 * 功能：商品详情
 */
public class GoodsDetails extends BaseActivity implements View.OnClickListener {
    private MyListview lv_myListview;
    private View header;
    private GoodsContentAdapter adapter;
    private ImageView header_pic, header_touxiang, header_share,iv_goods_icon,iv_delete;
    private TextView tv_number_show,tv_price,tv_goodsname,tv_comment,tv_userName,tv_more,tv_spac,tv_collect, header_name, header_count, header_new, header_people, header_price, header_kuaidi, header_sale, header_adds, header_title, header_des;
    private LinearLayout detail_kefu, detail_store, detail_collect;
    private CustomDialog.Builder builder;
    private GoodsContentBean gb;
    private List<String> url;
    private CircleImageView civ_avatar;
    private Button btn_reduce,btn_add,detail_add,detail_buy;
    private RecyclerView rv_spec;
    private List<StandardBean> list;
    private int number=1;
    private TvAdapter tv_adapter;
    private List<RankBean> com_list;
    private Button btn_add_cart,go_shopping;
    private RelativeLayout rl_no_more;
    private LinearLayout ll_more,ll_Goods_sd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodsdetail);
        initView();
        getDatas();
        monitor();

    }

    /**
     * 弹出对话框
     */
    Dialog bottomDialog;
    private void show1() {//Dialog
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        iv_goods_icon = contentView.findViewById(R.id.iv_goods_icon);//商品头像


        if (!TextUtils.isEmpty(gb.getGoodInfo().getOriginal_img())) {
            // String[] split = gb.getGoodInfo().getGoods_content().split(";");
            Glide.with(GoodsDetails.this).load(ApiHttpClient.API_PIC + gb.getGoodInfo().getOriginal_img()).into(iv_goods_icon);
            Log.i("获取商品详情","getOriginal_img全路径"+ApiHttpClient.API_PIC + gb.getGoodInfo().getOriginal_img());
        }
        tv_goodsname = contentView.findViewById(R.id.tv_goodsname);//商品简绍
        tv_goodsname.setText(gb.getGoodInfo().getGoods_name());

        header_count.setText(gb.getStoreInfo().getAllgoods_num());

        tv_price = contentView.findViewById(R.id.tv_price);//商品价格
        tv_price.setText(gb.getGoodInfo().getShop_price());

        iv_delete = contentView.findViewById(R.id.iv_delete);//退出
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomDialog.isShowing()){
                    bottomDialog.dismiss();
                }
            }
        });
        btn_add_cart=contentView.findViewById(R.id.btn_add_cart);
        go_shopping=contentView.findViewById(R.id.go_shopping);
        btn_reduce = contentView.findViewById(R.id.btn_reduce);//减少数量
        btn_add = contentView.findViewById(R.id.btn_add);//添加数量
        tv_number_show = contentView.findViewById(R.id.tv_number_show);//数量
        tv_number_show.setText(String.valueOf(number));
        rv_spec = contentView.findViewById(R.id.rv_spec);//列表
        initRv();
        setListener();



        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width =contentView.getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = (int) (contentView.getResources().getDisplayMetrics().heightPixels*0.6);
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
        adapter.notifyDataSetChanged();
    }

    private void initRv() {

        rv_spec.setLayoutManager(new GridLayoutManager(this,3));
        rv_spec.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen._15sp)));
        rv_spec.setAdapter(tv_adapter);
    }

    private void setListener() {
        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tv)){
                    ToastUtils.showShort(GoodsDetails.this,"请选择商品规格");
                }else {
                    showProgressDialog("正在处理，请稍后...");
                    addCar();
                }
            }
        });
        go_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tv)){
                    ToastUtils.showShort(GoodsDetails.this,"请选择商品规格");
                }else {
                    Intent order = new Intent(GoodsDetails.this, CommitOrderActivity.class);
                    order.putExtra("goods_id", getIntent().getStringExtra("goods_id"));
                    order.putExtra("num", number);
                    order.putExtra("tv_resou_names", tv);
                    startActivity(order);
                }
            }
        });
        btn_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number==1){
                    return;
                }
                number--;
                tv_number_show.setText(String.valueOf(number));

            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number++;
                tv_number_show.setText(String.valueOf(number));
            }
        });
    }

    private void initView() {
        loadTitle();
        list=new ArrayList<>();
        url = new ArrayList<>();
        com_list=new ArrayList();
        tv_adapter=new TvAdapter(this,list);
        lv_myListview = (MyListview) findViewById(R.id.lv_myListview);
        detail_kefu = (LinearLayout) findViewById(R.id.detail_kefu);
        detail_store = (LinearLayout) findViewById(R.id.detail_store);
        detail_collect = (LinearLayout) findViewById(R.id.detail_collect);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
        detail_add = (Button) findViewById(R.id.detail_add);
        detail_buy = (Button) findViewById(R.id.detail_buy);

        header = LayoutInflater.from(this).inflate(R.layout.goods_content_header, null);

        rl_no_more= header.findViewById(R.id.rl_no_more);//没有评论
        ll_more= header.findViewById(R.id.ll_more);//评论外部布局
        ll_Goods_sd= header.findViewById(R.id.ll_Goods_sd);//商家信息外部布局
        ll_Goods_sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(GoodsDetails.this,GoodsManagsActivity.class);
                intent.putExtra("uid",gb.getGoodInfo().getUid());
                startActivity(intent);
            }
        });


        tv_spac=(TextView) header.findViewById(R.id.tv_spac);//规格
        tv_spac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show1();
            }
        });
        tv_more=header.findViewById(R.id.tv_more);//更多评论
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GoodsDetails.this, CommentsActivity.class));
            }
        });

        civ_avatar=header.findViewById(R.id.civ_avatar);//用户头像


        tv_userName=header.findViewById(R.id.tv_userName);//用户昵称
        tv_comment=header.findViewById(R.id.tv_comment);//用户评价


        header_pic = (ImageView) header.findViewById(R.id.header_pic);
        header_share = (ImageView) header.findViewById(R.id.header_share);
        header_des = (TextView) header.findViewById(R.id.header_des);
        header_title = (TextView) header.findViewById(R.id.header_title);
        header_price = (TextView) header.findViewById(R.id.header_price);
        header_kuaidi = (TextView) header.findViewById(R.id.header_kuaidi);
        header_sale = (TextView) header.findViewById(R.id.header_sale);
        header_adds = (TextView) header.findViewById(R.id.header_adds);
        header_touxiang = (ImageView) header.findViewById(R.id.header_touxiang);
        header_name = (TextView) header.findViewById(R.id.header_name);
        header_count = (TextView) header.findViewById(R.id.header_count);
        header_new = (TextView) header.findViewById(R.id.header_new);
        header_people = (TextView) header.findViewById(R.id.header_people);
        lv_myListview.addHeaderView(header);

        adapter = new GoodsContentAdapter(this, url);
        lv_myListview.setAdapter(adapter);
    }

    private void loadTitle() {
        setTitleString(getString(R.string.goods_content__title));
        setLeftImage(R.mipmap.icon_return);
        setRightImage(R.mipmap.message);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setRightRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frendUserInfo!=null){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("friendInfoBeen",  friendInfoBeen);
                    intent.putExtras(bundle);
                    intent.setClass(GoodsDetails.this, FrendInfoActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void getDatas() {


        /**
         * 获取商品详情
         */
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }
        Api.goodsContent(LoginManager.getInstance().getUserID(this), getIntent().getStringExtra("goods_id"), new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                    Log.i("获取商品详情", "apiResponse.." + apiResponse);
                    gb = JSON.parseObject(apiResponse.getData(), GoodsContentBean.class);
                    initUserInfo(gb.getGoodInfo().getUid());
                    list.addAll(gb.getGoodInfo().getGoods_standard());
                    Log.i("获取商品详情", "gb.getGoodInfo().getGoods_standard().." + gb.getGoodInfo().getGoods_standard().size());
                    tv_adapter.notifyDataSetChanged();
                    if (!TextUtils.isEmpty(gb.getGoodInfo().getOriginal_img())) {
                       // String[] split = gb.getGoodInfo().getGoods_content().split(";");
                        Glide.with(GoodsDetails.this).load(ApiHttpClient.API_PIC + gb.getGoodInfo().getOriginal_img()).into(header_pic);
                        Log.i("获取商品详情","getOriginal_img全路径"+ApiHttpClient.API_PIC + gb.getGoodInfo().getOriginal_img());
                    }
                    if ("0".equals(gb.getGoodInfo().getIs_collect())) {
                        Drawable drawable = getResources().getDrawable(R.mipmap.favourite);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                        tv_collect.setCompoundDrawables(null, drawable, null, null);
                    } else {
                        Drawable drawable = getResources().getDrawable(R.mipmap.favourite_select);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                        tv_collect.setCompoundDrawables(null, drawable, null, null);
                    }
                    header_title.setText(gb.getGoodInfo().getGoods_name());
                    header_price.setText("¥" + gb.getGoodInfo().getShop_price());
                    if ("0".equals(gb.getGoodInfo().getIs_free_shipping())) {
                        header_kuaidi.setText("快递:不包邮");
                    } else {
                        header_kuaidi.setText("快递:免运费");
                    }
                    header_sale.setText("月销" + gb.getGoodInfo().getSales_sum() + "笔");
                    header_adds.setText(gb.getGoodInfo().getProvince());
                    if (!TextUtils.isEmpty(gb.getStoreInfo().getHeadsmall())) {
                        Glide.with(GoodsDetails.this).load(ApiHttpClient.API_PIC + gb.getStoreInfo().getHeadsmall()).into(header_touxiang);
                    }
                    header_name.setText(gb.getStoreInfo().getNickname());
                    header_count.setText(gb.getStoreInfo().getAllgoods_num());
                    header_new.setText(gb.getStoreInfo().getAllgoods_num());
                    header_people.setText(gb.getStoreInfo().getFocus_num());
                    header_des.setText(gb.getGoodInfo().getGoods_remark());



                    String[] bytes = gb.getGoodInfo().getGoods_content().split(";");
                    url.addAll(Arrays.asList(bytes));
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort(GoodsDetails.this, apiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(String errMessage) {
                ToastUtils.showShort(GoodsDetails.this, errMessage);
                super.onFailure(errMessage);
            }
        });
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }
        Api.comment_list(LoginManager.getInstance().getUserID(this), getIntent().getStringExtra("goods_id"), new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("评论","apiResponse="+apiResponse);
                if (apiResponse.getCode() == Api.SUCCESS) {
                    String data = apiResponse.getData();
                    //;
                    com_list.addAll(JSONArray.parseArray(data,RankBean.class));
                    if (com_list!=null&&com_list.size()>0){
                        tv_userName.setText(com_list.get(0).getUsername());
                        tv_comment.setText(com_list.get(0).getContent());
                        initUserInfo1(com_list.get(0).getUser_id());
                        rl_no_more.setVisibility(View.GONE);
                        ll_more.setVisibility(View.VISIBLE);
                    }else {
                        rl_no_more.setVisibility(View.VISIBLE);
                        ll_more.setVisibility(View.GONE);
                    }
                } else {
                    rl_no_more.setVisibility(View.VISIBLE);
                    ll_more.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errMessage) {
                //ToastUtils.showShort(GoodsDetails.this, errMessage);
                rl_no_more.setVisibility(View.VISIBLE);
                ll_more.setVisibility(View.GONE);
                super.onFailure(errMessage);
            }
        });
    }
    FrendUserInfo frendUserInfo;
    FriendInfoBean friendInfoBeen;
    private void initUserInfo(String uid) {
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, Presenter.NET_UNCONNECT);
            return;
        } else {
            Api.frendUserInfo(LoginManager.getInstance().getUserID(GoodsDetails.this), uid, new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    if (apiResponse.getCode()==Api.SUCCESS){
                        frendUserInfo = JSON.parseObject(apiResponse.getData(), FrendUserInfo.class);
                        friendInfoBeen=new FriendInfoBean();
                        friendInfoBeen.setPhone(frendUserInfo.getPhone());
                        friendInfoBeen.setHeadsmall(frendUserInfo.getHeadsmall());
                        friendInfoBeen.setUid(frendUserInfo.getUid());
                        friendInfoBeen.setNickname(frendUserInfo.getNickname());
                    }
                }
            });
        }
    }
    private void initUserInfo1(String uid) {
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, Presenter.NET_UNCONNECT);
            return;
        } else {
            Api.frendUserInfo(LoginManager.getInstance().getUserID(GoodsDetails.this), uid, new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    if (apiResponse.getCode()==Api.SUCCESS){
                        FrendUserInfo frendUserInfo = JSON.parseObject(apiResponse.getData(), FrendUserInfo.class);
                        showHeadIcon(civ_avatar, frendUserInfo.getHeadsmall().toString());
                    }
                }
            });
        }
    }
    private void showHeadIcon(ImageView view, String avatar) {

        if (TextUtils.isEmpty(avatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.default_avatar);
            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
            view.setImageBitmap(cirBitMap);
        } else {
            RequestManager req = Glide.with(this);
            if (avatar.toString().contains("http://")) {
                req.load(avatar).transform(new GlideCircleTransform(this)).into(view);
                Log.i("主播头像", "微信avatar" + avatar);
            } else {
                req.load(ApiHttpClient.API_PIC + avatar).transform(new GlideCircleTransform(this)).into(view);
                Log.i("主播头像", "avatar" + avatar);
            }
        }
    }


    private void monitor() {

        detail_kefu.setOnClickListener(this);
        detail_store.setOnClickListener(this);
        detail_collect.setOnClickListener(this);
        detail_add.setOnClickListener(this);
        detail_buy.setOnClickListener(this);
        header_share.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_kefu://客服
                callKeFu();
                break;
            case R.id.detail_store://店铺
                Intent intent = new Intent(this, MyShopActivity.class);
                startActivity(intent);
                break;
            case R.id.detail_collect://收藏
                showProgressDialog("正在处理，请稍后...");
                addCollect();
                break;
            case R.id.detail_add://加入购物车
                if(bottomDialog!=null&&!bottomDialog.isShowing()){
                    bottomDialog.show();
                    }else {
                        show1();
                    }
                break;
            case R.id.detail_buy://立即购买

                    if(bottomDialog!=null&&!bottomDialog.isShowing()){
                        bottomDialog.show();
                    }else {
                        show1();
                    }
                break;
            case R.id.header_share://分享
                share();
                break;
        }
    }

    private void callKeFu() {
        builder = new CustomDialog.Builder(this);
        builder.setMessage("是否联系客服 17600113823");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestPermissions();
            }
        });
        builder.create().show();
    }

    /*****
     * 打电话的权限
     */
    public void requestPermissions() {
        //判断当前是否获取到打电话的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                Toast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }

        } else {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri
                    .parse("tel:17600113823"));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                //如果请求被拒绝，那么通常grantResults数组为空
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //申请成功，进行响应操作
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri
                            .parse("tel:17600113823"));
                    if (ActivityCompat.checkSelfPermission(GoodsDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    startActivity(intent);
                } else {
                    //申请失败，可以继续像用户解释
                }
                return;
            }
        }

    }

    private void addCollect() {
        /**
         * 添加或取消收藏
         */
        if (!NetWorkUtil.isNetworkConnected(this)) {
            hideProgressDialog();
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }
        Api.addCollect(LoginManager.getInstance().getUserID(this), getIntent().getStringExtra("goods_id"), new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                hideProgressDialog();
                if (apiResponse.getCode() == Api.SUCCESS) {
                    Drawable drawable = getResources().getDrawable(R.mipmap.favourite_select);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    tv_collect.setCompoundDrawables(null, drawable, null, null);
                    ToastUtils.showShort(GoodsDetails.this, "收藏成功");
                } else if (apiResponse.getCode() == Api.CANCLE) {
                    Drawable drawable = getResources().getDrawable(R.mipmap.favourite);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    tv_collect.setCompoundDrawables(null, drawable, null, null);
                    ToastUtils.showShort(GoodsDetails.this, "取消收藏");
                } else {
                    ToastUtils.showShort(GoodsDetails.this, apiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(String errMessage) {
                hideProgressDialog();
                ToastUtils.showShort(GoodsDetails.this, errMessage);
                super.onFailure(errMessage);
            }
        });
    }

    private void addCar() {
        /**
         * 添加购物车
         */
        if (!NetWorkUtil.isNetworkConnected(this)) {
            hideProgressDialog();
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }
        Api.addCar(LoginManager.getInstance().getUserID(this), getIntent().getStringExtra("goods_id"), String.valueOf(number),tv, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                hideProgressDialog();
                if (apiResponse.getCode() == Api.SUCCESS) {
//                    Intent intent = new Intent(GoodsDetails.this, ShoppingCartActivity.class);
//                    startActivity(intent);
                    ToastUtils.showShort(GoodsDetails.this, "添加成功");
                } else {
//                    Intent intent = new Intent(GoodsDetails.this, ShoppingCartActivity.class);
//                    startActivity(intent);
                    ToastUtils.showShort(GoodsDetails.this, "此商品已经在购物车");
                }
            }

            @Override
            public void onFailure(String errMessage) {
                hideProgressDialog();
                ToastUtils.showShort(GoodsDetails.this, errMessage);
                super.onFailure(errMessage);
            }
        });
    }

    private void share() {
        UMImage thumb = new UMImage(this, R.mipmap.logo);
        UMWeb web = new UMWeb("https://www.pgyer.com/B2NX");
        web.setTitle("柠檬秀");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("大家好,我正在直播哦，喜欢我的朋友赶紧来哦");//描述
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ)
                .withMedia(web)
                .setCallback(umShareListener).open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            Toast.makeText(GoodsDetails.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(GoodsDetails.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(GoodsDetails.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    String tv="";
    private class TvAdapter extends RecyclerView.Adapter {
        private Context context;
        private List<StandardBean> resou;
        private List<TextView> tv_list=new ArrayList<>();
        public TvAdapter(Context context, List<StandardBean> resou) {
            this.context = context;
            this.resou = resou;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ResouHolder(View.inflate(context, R.layout.item_resou, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final StandardBean standardBean = resou.get(position);
            final ResouHolder holder1 = (ResouHolder) holder;
            holder1.tv_resou_names.setText(standardBean.getGood_size());
            Log.i("获取商品详情","standardBean="+standardBean.toString());
            tv_list.add(holder1.tv_resou_names);
            holder1.tv_resou_names.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < tv_list.size(); i++) {
                        tv_list.get(i).setBackground(context.getDrawable(R.drawable.button_bg12));
                    }
                    holder1.tv_resou_names.setBackground(context.getDrawable(R.drawable.button_bg1));
                    tv = holder1.tv_resou_names.getText().toString();
                    tv_spac.setText("已选择: "+standardBean.getGood_size());
                }
            });
        }

        @Override
        public int getItemCount() {
            return resou.size();
        }

        private class ResouHolder extends RecyclerView.ViewHolder {
            TextView tv_resou_names;

            public ResouHolder(View itemView) {
                super(itemView);
                tv_resou_names = itemView.findViewById(R.id.tv_resou_names);
            }
        }
    }
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            //if(parent.getChildPosition(view) != 0)
            outRect.top = space;
        }
    }
}

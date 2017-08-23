package com.example.project.limolive.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.GoodsContentAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.taowu.GoodsContentBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.view.CustomDialog;
import com.example.project.limolive.view.MyListview;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

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
    private ImageView header_pic, header_touxiang, header_share;
    private TextView tv_collect, header_name, header_count, header_new, header_people, header_price, header_kuaidi, header_sale, header_adds, header_title, header_des;
    private LinearLayout detail_kefu, detail_store, detail_collect, detail_add, detail_buy;
    private CustomDialog.Builder builder;
    private GoodsContentBean gb;
    private List<String> url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodsdetail);
        initView();
        getDatas();
        monitor();
    }

    private void initView() {
        loadTitle();
        url = new ArrayList<>();
        lv_myListview = (MyListview) findViewById(R.id.lv_myListview);
        detail_kefu = (LinearLayout) findViewById(R.id.detail_kefu);
        detail_store = (LinearLayout) findViewById(R.id.detail_store);
        detail_collect = (LinearLayout) findViewById(R.id.detail_collect);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
        detail_add = (LinearLayout) findViewById(R.id.detail_add);
        detail_buy = (LinearLayout) findViewById(R.id.detail_buy);

        header = LayoutInflater.from(this).inflate(R.layout.goods_content_header, null);
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
                ToastUtils.showShort(GoodsDetails.this, "进入右边界面");
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
                showProgressDialog("正在处理，请稍后...");
                addCar();
                break;
            case R.id.detail_buy://立即购买
                Intent order = new Intent(this, CommitOrderActivity.class);
                order.putExtra("goods_id", getIntent().getStringExtra("goods_id"));
                order.putExtra("num", 1);
                startActivity(order);
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
        Api.addCar(LoginManager.getInstance().getUserID(this), getIntent().getStringExtra("goods_id"), "","", new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                hideProgressDialog();
                if (apiResponse.getCode() == Api.SUCCESS) {
                    Intent intent = new Intent(GoodsDetails.this, ShoppingCartActivity.class);
                    startActivity(intent);
                    ToastUtils.showShort(GoodsDetails.this, apiResponse.getMessage());
                } else {
                    Intent intent = new Intent(GoodsDetails.this, ShoppingCartActivity.class);
                    startActivity(intent);
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
        UMWeb web = new UMWeb("https://www.pgyer.com/Ko1C");
        web.setTitle("柠檬直播");//标题
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
}

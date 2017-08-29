package com.example.project.limolive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.model.LoginModel;
import com.example.project.limolive.provider.MineDataProvider;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 我的店铺
 * @author hwj on 2016/12/19.
 */

public class MyShopActivity extends BaseActivity implements View.OnClickListener{

    private SimpleDraweeView iv_user_head;   //用户头像
    private TextView tv_shop_name;          //商铺名称
    private TextView tv_attention_number;   //关注数量
    private MineDataProvider provider; //个人信息
    private RelativeLayout rl_goodsMG;//商品管理

//    发布宝贝
    private TextView shop_bottom_list_text,tv_contact_custerm,tv_after_management,
        tv_trade_management;

    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        provider= new MineDataProvider(getApplicationContext());
        initView();
        monitor();
    }

    private void initView() {
        loadTitle();

        iv_user_head = (SimpleDraweeView) findViewById(R.id.iv_user_head);
        tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);
        tv_attention_number = (TextView) findViewById(R.id.tv_attention_number);
        rl_goodsMG = (RelativeLayout) findViewById(R.id.rl_goodsMG);
        setData();


        //发布宝贝
        shop_bottom_list_text = (TextView)findViewById(R.id.tv_publish_product);
        //交易管理
        tv_trade_management = (TextView) findViewById(R.id.tv_trade_management);
        //联系客服
        tv_contact_custerm = (TextView) findViewById(R.id.tv_contact_custerm);
        //售后管理
        tv_after_management = (TextView) findViewById(R.id.tv_after_management);


    }

    private void monitor(){
        shop_bottom_list_text.setOnClickListener(this);
        tv_trade_management.setOnClickListener(this);
        tv_contact_custerm.setOnClickListener(this);
        tv_after_management.setOnClickListener(this);
        rl_goodsMG.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //发布宝贝
            case R.id.tv_publish_product:
                intent = new Intent(this,PublishProductsActivity.class);
                startActivity(intent);
                break;
            //交易管理
            case R.id.tv_trade_management:
                intent = new Intent(this,TradeManagementActivity.class);
                startActivity(intent);
                break;
            //联系客服
            case R.id.tv_contact_custerm:
                intent = new Intent(this,ContactCustomerActivity.class);
                startActivity(intent);
                break;
            //售后管理
            case R.id.tv_after_management:

                intent = new Intent(this,AfterSaleActivity.class);
                intent.putExtra("type","0");

                startActivity(intent);
                break;
            //商品管理
            case R.id.rl_goodsMG:
                intent = new Intent(this,GoodsManagActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 配置数据
     */
    private void setData() {
        LoginModel loginModel=provider.getMineInfo(LoginManager.getInstance().getPhone(getApplication()));
        if(loginModel!=null){
            tv_shop_name.setText(loginModel.getStore_name());
            iv_user_head.setImageURI(ApiHttpClient.API_PIC+loginModel.getHeadsmall());
        }
    }

    private void loadTitle() {
        setTitleString(getString(R.string.my_store));
        setLeftImage(R.mipmap.icon_return);
        setRightImage(R.mipmap.fenlei);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyShopActivity.this.finish();
            }
        });
    }

}

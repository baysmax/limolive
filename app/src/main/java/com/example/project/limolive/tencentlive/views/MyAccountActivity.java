package com.example.project.limolive.tencentlive.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.BaseActivity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

/**
 * Created by hwj on 2016/5/30.
 * 账户
 */
public class MyAccountActivity extends BaseActivity implements View.OnClickListener {

    /*各种费用*/
    private TextView btn_charge_six, btn_charge_thirty, btn_charge_hundred,
            btn_charge_two_hundred, btn_charge_five_hundred, sssssssss;

    private TextView account_balance_num;
    private ImageView title_back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_account);
        setUpView();
    }

    private void setUpView() {
        btn_charge_six = (TextView) findViewById(R.id.btn_charge_six);
        btn_charge_thirty = (TextView) findViewById(R.id.btn_charge_thirty);
        btn_charge_hundred = (TextView) findViewById(R.id.btn_charge_hundred);
        btn_charge_two_hundred = (TextView) findViewById(R.id.btn_charge_two_hundred);
        btn_charge_five_hundred = (TextView) findViewById(R.id.btn_charge_five_hundred);
        account_balance_num = (TextView) findViewById(R.id.account_balance_num);
        sssssssss = (TextView) findViewById(R.id.sssssssss);
        title_back_button = (ImageView) findViewById(R.id.title_back_button);

        btn_charge_six.setOnClickListener(this);
        btn_charge_thirty.setOnClickListener(this);
        btn_charge_hundred.setOnClickListener(this);
        btn_charge_two_hundred.setOnClickListener(this);
        btn_charge_five_hundred.setOnClickListener(this);
        title_back_button.setOnClickListener(this);
        sssssssss.setOnClickListener(this);

        //这里需要请求接口 账户余额
        //  account_balance_num.setText(datas.getPersonInfo().getBalance());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("获取剩余柠檬币","组没走");
        getHaveCoins();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.btn_charge_six://6
                jumpToPay(1);
                break;
            case R.id.btn_charge_thirty://30
                jumpToPay(2);
                break;
            case R.id.btn_charge_hundred://100
                jumpToPay(3);
                break;
            case R.id.btn_charge_two_hundred://200
                jumpToPay(4);
                break;
            case R.id.btn_charge_five_hundred://500
                jumpToPay(5);
                break;
            case R.id.sssssssss://0.01
                jumpToPay(6);
                break;
        }
    }

    private void jumpToPay(int lemon_id) {
        Intent intent = new Intent(this, PayReadyActivity.class);
        intent.putExtra("lemon_id", String.valueOf(lemon_id));
        intent.putExtra("type", "2");
        startActivity(intent);
    }

    /*********
     * 获取剩余柠檬币
     */
    private void getHaveCoins() {

        if (NetWorkUtil.isNetworkConnected(this)) {
            Log.i("获取剩余柠檬币", "getUserID.." + LoginManager.getInstance().getUserID(MyAccountActivity.this));
            Api.getMemberCoins(LoginManager.getInstance().getUserID(MyAccountActivity.this), new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    Log.i("获取剩余柠檬币", "apiResponse.." + apiResponse.toString());
                    if (apiResponse.getCode() == 0) {
                        JSONObject parse = JSON.parseObject(apiResponse.getData());
                        String lemon_coins = parse.getString("charm");
                        Log.i("获取剩余柠檬币", "lemon_coins.." + lemon_coins);
                        account_balance_num.setText(lemon_coins);
                    } else {
                        ToastUtils.showShort(MyAccountActivity.this, apiResponse.getMessage());
                    }
                }
            });

        } else {
            ToastUtils.showShort(MyAccountActivity.this, "网络异常，请检查您的网络~");
        }

    }
}

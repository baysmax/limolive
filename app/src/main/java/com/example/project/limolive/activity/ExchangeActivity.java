package com.example.project.limolive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.BtnBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * Created by AAA on 2017/9/7.
 */

public class ExchangeActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_number;//钻石数量
    private TextView tv_explain;//说明
    private TextView tv_nmb_number;//积分数量
    private TextView[] textView;//积分或钻石
    private Button btn_wallet_integrak;//切换模式按钮
    private Button btn_zhifu;//去兑换
    private ImageView title_back_button; //退出按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        initView();
        initListener();
        setInit();
        getData();
    }

    private void getData() {
        getZuanshi();
        getIntegral();
    }

    private void setInit() {
        setViewText();
        setDraws();
        textView[0].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));
        result=10;
        btn_zhifu.setText("兑换"+result+getStatus(isDeaOrInt));
    }

    private void initListener() {
        title_back_button.setOnClickListener(this);
        btn_wallet_integrak.setOnClickListener(this);
        btn_zhifu.setOnClickListener(this);
        textView[0].setOnClickListener(this);
        textView[1].setOnClickListener(this);
        textView[2].setOnClickListener(this);
        textView[3].setOnClickListener(this);
        textView[4].setOnClickListener(this);
        textView[5].setOnClickListener(this);
    }

    private void initView() {
        initTitle();
        tv_number= (TextView) findViewById(R.id.tv_number);
        tv_explain= (TextView) findViewById(R.id.tv_explain);
        tv_nmb_number= (TextView) findViewById(R.id.tv_nmb_number);
        textView=new TextView[6];
        textView[0]= (TextView) findViewById(R.id.btn_charge_10);
        textView[1]= (TextView) findViewById(R.id.btn_charge_20);
        textView[2]= (TextView) findViewById(R.id.btn_charge_50);
        textView[3]= (TextView) findViewById(R.id.btn_charge_100);
        textView[4]= (TextView) findViewById(R.id.btn_charge_500);
        textView[5]= (TextView) findViewById(R.id.btn_charge_1000);
        btn_wallet_integrak= (Button) findViewById(R.id.btn_wallet_integrak);
        btn_zhifu= (Button) findViewById(R.id.btn_zhifu);
    }

    private void initTitle() {
        title_back_button= (ImageView) findViewById(R.id.title_back_button);
    }
    private void setDraws() {
        for (int i = 0; i < textView.length; i++) {
            textView[i].setBackgroundDrawable(getDrawable(R.drawable.btns_bg));
        }
    }
    private String Diamonds_To_integral="钻石";
    private String integral_To_Diamonds="积分";
    private boolean isDeaOrInt=true;// true 积分 false 钻石 默认为兑换积分

    private String getStatus(boolean bl){
        if (bl==true){
            return integral_To_Diamonds;
        }
        return Diamonds_To_integral;
    }

    private int result=10;//默认兑换返回
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("integral",tv_nmb_number.getText().toString());
        intent.putExtra("diamonds",tv_number.getText().toString());
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_button:
                Intent intent = new Intent();
                intent.putExtra("integral",tv_nmb_number.getText().toString());
                intent.putExtra("diamonds",tv_number.getText().toString());
                setResult(RESULT_OK,intent);
                ExchangeActivity.this.finish();
                break;
            case R.id.btn_zhifu:
                exchange();
                break;
            case R.id.btn_wallet_integrak:
                isDeaOrInt=!isDeaOrInt;
                setViewText();
                break;
            case R.id.btn_charge_10:
                setDraws();
                textView[0].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));
                result=10;
                btn_zhifu.setText("兑换"+result+getStatus(isDeaOrInt));
                break;
            case R.id.btn_charge_20:
                setDraws();
                result=20;
                textView[1].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));
                btn_zhifu.setText("兑换"+result+getStatus(isDeaOrInt));
                break;
            case R.id.btn_charge_50:
                setDraws();
                result=50;
                textView[2].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));
                btn_zhifu.setText("兑换"+result+getStatus(isDeaOrInt));
                break;
            case R.id.btn_charge_100:
                setDraws();
                result=100;
                textView[3].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));
                btn_zhifu.setText("兑换"+result+getStatus(isDeaOrInt));
                break;
            case R.id.btn_charge_500:
                setDraws();
                result=500;
                textView[4].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));
                btn_zhifu.setText("兑换"+result+getStatus(isDeaOrInt));
                break;
            case R.id.btn_charge_1000:
                setDraws();
                ((EditText)textView[5]).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence==null){
                            String s = "0";
                            result=Integer.parseInt(s);
                        }else {
                            if ("".equals(charSequence.toString())){
                                result=0;
                            }else {
                                String s = charSequence.toString();
                                result=Integer.parseInt(s);
                            }

                        }
                        btn_zhifu.setText("兑换"+result+getStatus(isDeaOrInt));
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                btn_zhifu.setText("兑换"+result+getStatus(isDeaOrInt));
                textView[5].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));

                break;
        }
    }

    private void exchange() {
        if (isDeaOrInt){
            String string = tv_number.getText().toString();
            int i = Integer.parseInt(string);
            if (result>i){
                ToastUtils.showShort(this,"钻石不足");
                return;
            }else {
                exchangeIntegral();//兑换积分
            }
        }else {
            String string = tv_nmb_number.getText().toString();
            int i = Integer.parseInt(string);
            if (result>i){
                ToastUtils.showShort(this,"积分不足");
                return;
            }else {
                exchangeDiamonds();//兑换钻石
            }
        }

    }

    private void exchangeDiamonds() {
        if (!NetWorkUtil.isNetworkConnected(ExchangeActivity.this)) {
            ToastUtils.showShort(ExchangeActivity.this, NET_UNCONNECT);
            return;
        }else {
            if (result==1000){
                EditText editText= (EditText) textView[5];
                String s = editText.getText().toString();
                if (s.contains(".")){
                    String[] split = s.split(".");
                    ToastUtils.showShort(this,"小于1的并不会转换");
                    result=Integer.parseInt(split[0]);
                }
                if (result==0){
                    return;
                }

            }
            Api.exchangeDiamonds(LoginManager.getInstance().getUserID(this), String.valueOf(result), new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    Log.i("积分","兑换钻石api="+apiResponse.toString());
                    if (apiResponse.getCode()==Api.SUCCESS){
                        ToastUtils.showShort(ExchangeActivity.this,"兑换成功");

                    }
                    getData();
                }
            });
        }
    }

    private void exchangeIntegral() {
        if (!NetWorkUtil.isNetworkConnected(ExchangeActivity.this)) {
            ToastUtils.showShort(ExchangeActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.exchangeIntegral(LoginManager.getInstance().getUserID(this), String.valueOf(result), new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    Log.i("积分","兑换积分api="+apiResponse.toString());
                    if (apiResponse.getCode()==Api.SUCCESS){
                        ToastUtils.showShort(ExchangeActivity.this,"兑换成功");
                    }
                    getData();
                }
            });
        }
    }
    private void getZuanshi(){
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }else {
            Api.getDiamonds(LoginManager.getInstance().getUserID(this),
                    new ApiResponseHandler(this) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            Log.i("钻石","apiResponse="+apiResponse.toString());
                            if (apiResponse.getCode()==Api.SUCCESS){
                                String data = apiResponse.getData();
                                JSONObject parse = (JSONObject) JSON.parse(data);
                                String diamonds_coins = parse.getString("diamonds_coins");
                                Log.i("钻石","diamonds_coins:"+diamonds_coins);
                                tv_number.setText(diamonds_coins);
                            }
                        }
                    });
        }

    }
    private void getIntegral(){
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }else {
            Api.getIntegral(LoginManager.getInstance().getUserID(this),
                    new ApiResponseHandler(this) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            Log.i("积分","apiResponse="+apiResponse.toString());
                            if (apiResponse.getCode()==Api.SUCCESS){
                                String data = apiResponse.getData();
                                JSONObject parse = (JSONObject) JSON.parse(data);
                                String diamonds_coins = parse.getString("integral");
                                Log.i("积分","integral:"+diamonds_coins);
                                tv_nmb_number.setText(diamonds_coins);
                            }
                        }
                    });
        }

    }

    private void setViewText() {
        textView[0].setText(10+getStatus(isDeaOrInt));
        textView[1].setText(20+getStatus(isDeaOrInt));
        textView[2].setText(50+getStatus(isDeaOrInt));
        textView[3].setText(100+getStatus(isDeaOrInt));
        textView[4].setText(500+getStatus(isDeaOrInt));
        //textView[5].setText(1000+getStatus(isDeaOrInt));
        btn_zhifu.setText("兑换"+result+getStatus(isDeaOrInt));
        tv_explain.setText("(点击切换到兑换"+getStatus(isDeaOrInt==true?false:true)+")");
        btn_wallet_integrak.setText(isDeaOrInt?"兑换积分":"兑换钻石");
    }


}

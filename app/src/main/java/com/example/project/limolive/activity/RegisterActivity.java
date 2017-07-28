package com.example.project.limolive.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.presenter.LoginPresenter;

import static com.example.project.limolive.activity.BeforeLiveActivity.isShouldHideInput;

/**
 * 注册界面
 * @author hwj on 2016/12/13.
 */

public class RegisterActivity extends BaseActivity {

    private LoginPresenter loginPresenter;

    private EditText edit_user_name; //用户名
    private EditText edit_pwd;      //密码
    private EditText edit_pwd_ok;   //确认密码
    private EditText edit_phone;   //电话
    private EditText edit_code;     //验证码

    private TextView tv_get_code;  //获取验证码

    //同意服务协议
    private TextView tv_agree_text;
    //是否同意协议
    private ImageView check_agree;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginPresenter=new LoginPresenter(this);
        initView();
    }

    private void initView() {
        loadTitle();
        edit_user_name= (EditText) findViewById(R.id.edit_user_name);
        edit_pwd= (EditText) findViewById(R.id.edit_pwd);
        edit_pwd_ok= (EditText) findViewById(R.id.edit_pwd_ok);
        edit_phone= (EditText) findViewById(R.id.edit_phone);
        edit_code= (EditText) findViewById(R.id.edit_code);
        check_agree= (ImageView) findViewById(R.id.check_agree);
        tv_get_code= (TextView) findViewById(R.id.tv_get_code);


        initEvent();
        setServiceProtocol();
    }

    private void initEvent() {
        tv_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.getCode(v,edit_phone.getText().toString().trim());
            }
        });
        check_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelect=check_agree.isSelected();
                check_agree.setSelected(!isSelect);
            }
        });
    }

    /**
     * 配置服务协议点击事件
     */
    private void setServiceProtocol() {
        tv_agree_text= (TextView) findViewById(R.id.tv_agree_text);
        SpannableString spannableString=new SpannableString(getString(R.string.register_agree_text));
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#001B6A"));
                ds.setUnderlineText(true);
            }

            @Override
            public void onClick(View widget) {
                RegisterActivity.this.startActivity(new Intent(RegisterActivity.this,ServiceProtocolActivity.class));
            }
        },8,14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_agree_text.setText(spannableString);
        tv_agree_text.setMovementMethod(LinkMovementMethod.getInstance()); //相应点击事件
    }

    /**
     * 设置顶部标题栏颜色属性
     */
    private void loadTitle() {
        setTitleString(getString(R.string.register_title_text));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
    }

    /**
     * 立即注册
     * @param v
     */
    public void register(View v){
        loginPresenter.register(edit_user_name.getText().toString().trim()
                ,edit_pwd.getText().toString().trim()
                ,edit_pwd_ok.getText().toString().trim()
                ,edit_phone.getText().toString().trim()
                ,edit_code.getText().toString().trim()
                );
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
}

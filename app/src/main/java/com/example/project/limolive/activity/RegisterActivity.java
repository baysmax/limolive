package com.example.project.limolive.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.example.project.limolive.R;
import com.example.project.limolive.presenter.LoginPresenter;
import com.example.project.limolive.utils.ToastUtils;

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
    private TextView username,password,password2,phone;
    private ImageView iv1,iv2,iv3,iv4;

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

        username= (TextView) findViewById(R.id.username);
        password= (TextView) findViewById(R.id.password);
        password2= (TextView) findViewById(R.id.password2);
        phone= (TextView) findViewById(R.id.phone);
        iv1= (ImageView) findViewById(R.id.iv1);
        iv2= (ImageView) findViewById(R.id.iv2);
        iv3= (ImageView) findViewById(R.id.iv3);
        iv4= (ImageView) findViewById(R.id.iv4);
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

        setDraws();
        if (TextUtils.isEmpty(edit_user_name.getText().toString().trim())) {
            setDraw(username,iv1,false,"用户名不可为空");
            return;
        }else {
            setDraw(username,iv1,true,"");
        }
        if (TextUtils.isEmpty(edit_pwd.getText().toString().trim())) {
            setDraw(password,iv2,false,"密码不可为空");
            return;
        }else {
            setDraw(password,iv2,true,"");
        }
        if (TextUtils.isEmpty(edit_pwd_ok.getText().toString().trim())) {
            setDraw(password2,iv3,false,"重复密码不可为空");
            return;
        }else {
            setDraw(password2,iv3,true,"");
        }
        if (!edit_pwd_ok.getText().toString().trim().equals(edit_pwd.getText().toString().trim())) {
            setDraw(password2,iv3,false,"两次密码不一致");
            return;
        }else {
            setDraw(password2,iv3,true,"");
        }
        if (TextUtils.isEmpty(edit_phone.getText().toString().trim())) {
            setDraw(phone,iv4,false,"手机号不可为空");
            return;
        }else {
            setDraw(phone,iv4,true,"");
        }

        loginPresenter.register(
                edit_user_name.getText().toString().trim()
                ,edit_pwd.getText().toString().trim()
                ,edit_pwd_ok.getText().toString().trim()
                ,edit_phone.getText().toString().trim(),/*edit_code.getText().toString().trim()*/"1234"
        );
    }

    private void setDraws() {
        iv1.setImageDrawable(null);
        iv2.setImageDrawable(null);
        iv3.setImageDrawable(null);
        iv4.setImageDrawable(null);
        username.setText("");
        password.setText("");
        password2.setText("");
        phone.setText("");
    }

    private void setDraw(TextView username,ImageView iv,boolean isb,String tis) {
        //设置EditText中文字左边的显示图标，上面 、右边 、下面 没有则置空null
        iv.setImageDrawable(getDrawable(isb?R.drawable.duihao_03:R.drawable.cuohao_03));
        username.setText(tis);
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

package com.example.project.limolive.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.presenter.LoginPresenter;

import static com.example.project.limolive.activity.BeforeLiveActivity.isShouldHideInput;

/**
 * 忘记密码
 * @author hwj on 2016/12/13.
 */

public class ForgetPasswordActivity extends BaseActivity {

    private LoginPresenter loginPresenter;

    private EditText edit_phone;
    private EditText edit_code;
    private EditText edit_pwd_reset;

    private TextView tv_get_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        loginPresenter=new LoginPresenter(this);
        initView();
    }

    private void initView() {
        loadTitle();

        edit_phone= (EditText) findViewById(R.id.edit_phone);
        edit_code= (EditText) findViewById(R.id.edit_code);
        edit_pwd_reset= (EditText) findViewById(R.id.edit_pwd_reset);

        tv_get_code= (TextView) findViewById(R.id.tv_get_code);
        tv_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.getForgetCode(v,edit_phone.getText().toString().trim());
            }
        });

    }

    private void loadTitle() {
        setTitleString(getString(R.string.login_forgot_title));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPasswordActivity.this.finish();
            }
        });
    }

    /**
     * 找回密码
     * @param v
     */
    public void findPwd(View v){
        loginPresenter.findPassword(edit_phone.getText().toString().trim(),
                edit_pwd_reset.getText().toString().trim(),edit_code.getText().toString().trim());
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

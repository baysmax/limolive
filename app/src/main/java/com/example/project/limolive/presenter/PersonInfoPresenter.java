package com.example.project.limolive.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.DialogFactory;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.provider.MineDataProvider;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.utils.DateUtils;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.utils.datepicker.DatePickerDialog;
import com.example.project.limolive.widget.address.CityPicker;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 负责并更新修改个人信息
 * @author hwj on 2016/12/15.
 */

public class PersonInfoPresenter extends Presenter {

    public static final int HEAD_UPDATE_OVER=211;
    public static final int HEAD_UPDATE_JOB=212;
    public static final int HEAD_UPDATE_USERNAME=213;
    public MineDataProvider provider;

    public PersonInfoPresenter(Context context) {
        super(context);
        provider=new MineDataProvider(context);
    }

    /**
     * 更新头像
     * @param msg 接收到的msg信息
     * @param handler handler处理者
     */
    public void upDateHeadToServer(Message msg,final Handler handler) {
        if(!NetWorkUtil.isNetworkConnected(context)){
            return;
        }
        if(msg!=null&&msg.obj!=null&&msg.obj instanceof Uri){
            final Uri uri= (Uri) msg.obj;
            File file=new File(uri.getPath());
            try {
                RequestParams params=new RequestParams();
                params.put("uid",LoginManager.getInstance().getUserID(context));
                params.put("headsmall",file);
                final ProgressDialog progressDialog=DialogFactory.
                        getDefualtProgressDialog((Activity) context,CHANGING);
                progressDialog.show();
                Api.changeInfo(params, new ApiResponseHandler(context) {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        Message overMsg=Message.obtain();
                        if(apiResponse.getCode()==Api.SUCCESS){
                            overMsg.obj=uri;
                            updateLocal(apiResponse.getData());
                        }else{
                            overMsg.obj=null;
                            ToastUtils.showShort(context,apiResponse.getMessage());
                        }
                        progressDialog.dismiss();
                        if(handler!=null){
                            overMsg.what=HEAD_UPDATE_OVER;
                            handler.sendMessage(overMsg);
                        }
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateLocal(String data) {
        if(TextUtils.isEmpty(data)){
            return;
        }
        try {
            JSONObject obj=new JSONObject(data);
            String headPath=obj.optString("headsmall");
            ContentValues values=new ContentValues();
            values.put("headsmall",headPath);
            provider.updateMineData(values,LoginManager.getInstance().getPhone(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改Job
     * @param job 修改后的Job
     * @param job 修改完毕后通知
     */
    public void changeJob(final String job,final Handler handler) {
        if(TextUtils.isEmpty(job)){
            return;
        }
        if(!NetWorkUtil.isNetworkConnected(context)){
            ToastUtils.showShort(context,NET_UNCONNECT);
            return;
        }
        final ProgressDialog progressDialog=DialogFactory.getDefualtProgressDialog((Activity) context,
                CHANGING);
        progressDialog.show();
        RequestParams params=new RequestParams();
        params.put("uid",LoginManager.getInstance().getUserID(context));
        params.put("job",job);
        Api.changeInfo(params, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(apiResponse.getCode()==Api.SUCCESS){
                    if(handler!=null){
                        ContentValues values=new ContentValues();
                        values.put("job",job);
                        provider.updateMineData(values,
                                LoginManager.getInstance().getPhone(context));
                        handler.sendEmptyMessage(HEAD_UPDATE_JOB);
                    }
                }else{
                    ToastUtils.showShort(context,apiResponse.getMessage());
                }
                progressDialog.dismiss();
            }
        });
    }

    /**
     * 修改用户名
     * @param userName 修改后的Username
     */
    public void changeUserName(final String userName,final Handler handler) {
        if(TextUtils.isEmpty(userName)){
            return;
        }
        if(!NetWorkUtil.isNetworkConnected(context)){
            ToastUtils.showShort(context,NET_UNCONNECT);
            return;
        }
        final ProgressDialog progressDialog=DialogFactory.getDefualtProgressDialog((Activity) context,
                CHANGING);
        progressDialog.show();
        RequestParams params=new RequestParams();
        params.put("uid",LoginManager.getInstance().getUserID(context));
        params.put("nickname",userName);
        Api.changeInfo(params, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(apiResponse.getCode()==Api.SUCCESS){
                    if(handler!=null){
                        ContentValues values=new ContentValues();
                        values.put("nickname",userName);
                        provider.updateMineData(values,
                                LoginManager.getInstance().getPhone(context));
                        LiveMySelfInfo.getInstance().setNickName(userName);
                        LoginManager.getInstance().setHostName(context,userName);
                        handler.sendEmptyMessage(HEAD_UPDATE_USERNAME);
                    }
                }else{
                    ToastUtils.showShort(context,apiResponse.getMessage());
                }
                progressDialog.dismiss();
            }
        });
    }


    /**
     * 显示时间选择Dialog
     * @param dateForString
     * @param tv_user_age
     */
    private DatePickerDialog dialog=null;  //时间选择器
    public void showDateDialog(List<Integer> dateForString, final TextView tv_user_age) {
        dialog=DialogFactory.getDatePickerDialog((Activity) context
                ,new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                String time=dates[0] + "/" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "/"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2]));
                tv_user_age.setText(time);
                changeAge(time);
            }
            @Override
            public void onCancel() {
                if(dialog!=null&&dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    /**
     * @param time 时间xxxx/xx/xx
     */
    private void changeAge(final String time) {
        if(!NetWorkUtil.isNetworkConnected(context)){
            ToastUtils.showShort(context,NET_UNCONNECT);
            return;
        }
        if(TextUtils.isEmpty(time)){
            return;
        }
        final ProgressDialog progressDialog=DialogFactory.getDefualtProgressDialog((Activity) context,
                CHANGING);
        progressDialog.show();
        RequestParams params=new RequestParams();
        params.put("uid",LoginManager.getInstance().getUserID(context));
        params.put("birthday",time);
        Api.changeInfo(params, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(apiResponse.getCode()==Api.SUCCESS){
                    try {
                        JSONObject object=new JSONObject(apiResponse.getData());
                        String age=object.optString("birthday");
                        if(!TextUtils.isEmpty(age)){
                            ContentValues values=new ContentValues();
                            values.put("birthday",DateUtils.format(1000*Long.parseLong(age),DateUtils.Y_M_D));
                            provider.updateMineData(values,
                                    LoginManager.getInstance().getPhone(context));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    ToastUtils.showShort(context,apiResponse.getMessage());
                }
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        });
    }

    /**
     * 显示地址
     * @param tv_user_home
     */
    public void showAddressDialog(final TextView tv_user_home) {
        CityPicker cityPicker=DialogFactory.getCityPickerDialog((Activity) context);
        cityPicker.show();
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                tv_user_home.setText("" + citySelected[0] + " " + citySelected[1] + " "
                        + citySelected[2]);
                changeHome(citySelected[0],citySelected[1]);
            }
        });
    }

    /**
     * 修改选中的地址
     * @param province 省
     * @param city 城市
     */
    private void changeHome(final String province,final String city){
        if(!NetWorkUtil.isNetworkConnected(context)){
            ToastUtils.showShort(context,NET_UNCONNECT);
            return;
        }
        if(TextUtils.isEmpty(province)){
            return;
        }
        if(TextUtils.isEmpty(city)){
            return;
        }
        final ProgressDialog progressDialog=DialogFactory.getDefualtProgressDialog((Activity) context,
                CHANGING);
        progressDialog.show();
        RequestParams params=new RequestParams();
        params.put("uid",LoginManager.getInstance().getUserID(context));
        params.put("province",province);
        params.put("city",city);
        Api.changeInfo(params, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(apiResponse.getCode()==Api.SUCCESS){
                    ContentValues values=new ContentValues();
                    values.put("province",province);
                    values.put("city",city);
                    provider.updateMineData(values,
                            LoginManager.getInstance().getPhone(context));
                }else{
                    ToastUtils.showShort(context,apiResponse.getMessage());
                }
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        });
    }

    /**
     * 设置性别
     * @param sex 0未填写 1男 2女
     */
    public void changeSex(final String sex,final TextView sexText) {
        if(!NetWorkUtil.isNetworkConnected(context)){
            ToastUtils.showShort(context,NET_UNCONNECT);
            return;
        }
        final ProgressDialog progressDialog=DialogFactory.getDefualtProgressDialog((Activity) context,
                CHANGING);
        progressDialog.show();
        RequestParams params=new RequestParams();
        params.put("uid",LoginManager.getInstance().getUserID(context));
        params.put("sex",sex);
        Api.changeInfo(params, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if(apiResponse.getCode()==Api.SUCCESS){
                    ContentValues values=new ContentValues();
                    values.put("sex",sex);
                    provider.updateMineData(values,
                            LoginManager.getInstance().getPhone(context));
                    if("0".equals(sex)){
                        sexText.setText("未填写");
                    }else{
                        sexText.setText(("1".equals(sex)?"男":"女"));
                    }
                }else{
                    ToastUtils.showShort(context,apiResponse.getMessage());
                }
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        });
    }
}

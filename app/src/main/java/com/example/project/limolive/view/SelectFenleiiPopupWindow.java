package com.example.project.limolive.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.BeforeLiveActivity;
import com.example.project.limolive.adapter.FenleiAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.live.AllcatgoryBean;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.project.limolive.activity.BeforeLiveActivity.FENLEIINFO;

/**
 * 作者：hpg on 2016/12/20 17:55
 * 功能：选择分类
 */
public class SelectFenleiiPopupWindow {
    private Context context;
    private List<AllcatgoryBean> AllcatgoryBeans;
    private FenleiAdapter adapter;
    private ChangeText changeText;
    private int selectPosition;
    private CustomProgressDialog mProgressDialog;
    public SelectFenleiiPopupWindow(Context context,ChangeText changeText,int position) {
        this.context = context;
      //  AllcatgoryBeans = allcatgoryBeans;
        this.changeText = changeText;
        this.selectPosition = position;
        mProgressDialog = new CustomProgressDialog(context);
        showProgressDialog("正在加载");
        SelectFenlei();
    }

    public void showPopupWindow(View view) {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.selectefenlei_popu, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);



        GridView gv_fenleiList = (GridView) contentView.findViewById(R.id.gv_fenleiList);
        AllcatgoryBeans = new ArrayList<>();
        adapter = new FenleiAdapter(AllcatgoryBeans,context);
        adapter.setSelectPosition(selectPosition);
        gv_fenleiList.setAdapter(adapter);
        gv_fenleiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeText.changeText(AllcatgoryBeans.get(position).getType_name(),position);
                adapter.setSelectPosition(position);
                adapter.notifyDataSetChanged();
                if (popupWindow!=null) popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("mengdd", "onTouch : ");
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.color.white));

        Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int height = defaultDisplay.getHeight();
        int width = defaultDisplay.getWidth();

        popupWindow.setHeight(height / 2);

        popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);

    }

    private void SelectFenlei() {
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, "请检查网络!");
            return;
        }
        Api.getGoodsType(new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                    Log.i("所有分类", apiResponse.toString());

                    List<AllcatgoryBean> list = JSON.parseArray(apiResponse.getData(), AllcatgoryBean.class);
                    AllcatgoryBeans.clear();
                    AllcatgoryBeans.addAll(list);
                    Log.i("所有分类", AllcatgoryBeans.toString());
                    hideProgressDialog();
                }else {
                    ToastUtils.showCustom(context, apiResponse.getMessage().toString(), Toast.LENGTH_SHORT);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                ToastUtils.showCustom(context, errMessage, Toast.LENGTH_SHORT);
                hideProgressDialog();
            }

        });
    }

    public interface ChangeText{
        void changeText(String text,int position);
    }
    public void showProgressDialog(String msg) {
        mProgressDialog = new CustomProgressDialog(context);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

}

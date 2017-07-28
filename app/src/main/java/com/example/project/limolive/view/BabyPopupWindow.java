package com.example.project.limolive.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.LiveBaby_GoodsListAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.taowu.GoodInfo;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * 作者：hpg on 2016/12/20 17:55
 * 功能：
 */
public class BabyPopupWindow {
    private Context context;
    private TextView tv_textNull;
    private ListView lv_all;
    private List<GoodInfo> mGoodInfo;
    private LiveBaby_GoodsListAdapter adapter;
    public void showPopupWindow(final Context context, View view) {
        this.context = context;
        goodsManager("");
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.click_baby_dialog, null);
        lv_all = (MyListview) contentView.findViewById(R.id.lv_all);
        tv_textNull = (TextView) contentView.findViewById(R.id.tv_textNull);
        mGoodInfo = new ArrayList<>();
        adapter = new LiveBaby_GoodsListAdapter(context,mGoodInfo);
        lv_all.setAdapter(adapter);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

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

        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.color.white));

        Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int height = defaultDisplay.getHeight();
        int width = defaultDisplay.getWidth();

        popupWindow.setHeight(height - 400);

        popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);

    }

    private void goodsManager(String page){
        /**
         * 商品管理
         */
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }else {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage(Presenter.LOADING);
            dialog.show();

            Api.goodsManager(LoginManager.getInstance().getUserID(context),"1",page, new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    Log.i("商品管理",apiResponse.toString());
                    if (apiResponse.getCode() == Api.SUCCESS) {
                        List<GoodInfo> list = JSON.parseArray(apiResponse.getData(), GoodInfo.class);
                        mGoodInfo.clear();
                        mGoodInfo.addAll(list);
                    } else if (apiResponse.getCode() ==-2){
                        tv_textNull.setVisibility(View.VISIBLE);
                    }else {
                        ToastUtils.showShort(context,apiResponse.getMessage().toString());
                    }
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
                @Override
                public void onFailure(String errMessage) {
                    super.onFailure(errMessage);
                    dialog.dismiss();
                }
            });
        }
    }
}

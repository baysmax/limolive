package com.example.project.limolive.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.tencentim.ui.ChatActivity;
import com.example.project.limolive.tencentlive.model.AvMemberInfo;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.utils.GlideCircleTransform;
import com.example.project.limolive.tencentlive.utils.UIUtils;
import com.tencent.TIMConversationType;

import java.util.List;

/**
 * 作者：hpg on 2016/12/20 17:55
 * 功能：点击成员  显示信息
 */
public class ManberInfoPopupWindow {
    private Context context;
    private ImageView iv_host_head;
    private TextView tv_host_name;
    private ImageView tv_sixin;
    private AvMemberInfo avMemberInfos;
    public ManberInfoPopupWindow(Context context) {
        this.context = context;
    }

    public void showPopupWindow(View view, final AvMemberInfo avMemberInfos) {
        this.avMemberInfos =avMemberInfos;
        Log.i("成员信息","avMemberInfos"+avMemberInfos);
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.click_manberhead_dialog, null);
        tv_sixin = (ImageView) contentView.findViewById(R.id.tv_sixin);
        iv_host_head = (ImageView) contentView.findViewById(R.id.iv_host_head);
        tv_host_name = (TextView) contentView.findViewById(R.id.tv_host_name);

        tv_host_name.setText(avMemberInfos.getNickname());
        showHeadIcon(iv_host_head, avMemberInfos.getHeadsmall());

        tv_sixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击私信
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("identify", avMemberInfos.getPhone());
                intent.putExtra("type", TIMConversationType.C2C);
                intent.putExtra("name", avMemberInfos.getNickname());
                intent.putExtra("headsmall", avMemberInfos.getHeadsmall());
                context.startActivity(intent);
            }
        });

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

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

        popupWindow.setHeight(height / 4);
        popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
    }

    private void showHeadIcon(ImageView view, String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avatar);
            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
            view.setImageBitmap(cirBitMap);
        } else {
            RequestManager req = Glide.with(context);
            if (avatar.toString().contains("http://")){
                req.load(avatar).transform(new GlideCircleTransform(context)).into(view);
            }else {
                req.load(ApiHttpClient.API_LOCATION_PIC + avatar).transform(new GlideCircleTransform(context)).into(view);
            }
            Log.i("头像地址",ApiHttpClient.API_LOCATION_PIC + avatar.toString());
        }
    }

}

package com.example.project.limolive.view;

import android.app.Dialog;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentim.ui.ChatActivity;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.utils.GlideCircleTransform;
import com.example.project.limolive.tencentlive.utils.UIUtils;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.tencent.TIMConversationType;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * 作者：hpg on 2016/12/20 17:55
 * 功能：
 */
public class HostInfoPopupWindow {
    private Context context;
    private ImageView iv_host_head;
    private TextView tv_host_name;
    private ImageView iv_guanzhu;
    private ImageView tv_sixin;
    private TextView tv_guanzhuNum;
    private TextView tv_fensiNum;
    public HostInfoPopupWindow(Context context) {
        this.context = context;
    }

    public void showPopupWindow(View view) {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.click_hosthead_dialog, null);
        iv_guanzhu = (ImageView) contentView.findViewById(R.id.iv_guanzhu);
        tv_sixin = (ImageView) contentView.findViewById(R.id.tv_sixin);
        tv_guanzhuNum = (TextView) contentView.findViewById(R.id.tv_guanzhuNum);
        tv_fensiNum = (TextView) contentView.findViewById(R.id.tv_fensiNum);
        iv_host_head = (ImageView) contentView.findViewById(R.id.iv_host_head);
        tv_host_name = (TextView) contentView.findViewById(R.id.tv_host_name);
        TextView tv_report = (TextView) contentView.findViewById(R.id.tv_report);//举报
        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //举报
                showReportDialog();
            }
        });
        getFollow();
        Log.i("头像主播", "头像" + CurLiveInfo.getHostAvator());
        //  tv_host_name.setText(CurLiveInfo.getHostName());
        // tv_guanzhuNum.setText(CurLiveInfo.getAdmires() + "关注");
        tv_fensiNum.setText(CurLiveInfo.getMembers() + "粉丝");
        showHeadIcon(iv_host_head, CurLiveInfo.getHostAvator());

        tv_sixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击私信
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("identify", CurLiveInfo.getHost_phone());
                intent.putExtra("type", TIMConversationType.C2C);
                intent.putExtra("name", CurLiveInfo.getHostName());
                intent.putExtra("headsmall", CurLiveInfo.getHostAvator());
                context.startActivity(intent);
            }
        });
        iv_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击关注
                followHandle();
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

        popupWindow.setHeight(height / 3);
        popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
    }

    private void showReportDialog() {
        final Dialog reportDialog = new Dialog(context, R.style.report_dlg);
        reportDialog.setContentView(R.layout.dialog_live_report);

        TextView tvReportDirty = (TextView) reportDialog.findViewById(R.id.btn_dirty);
        TextView tvReportFalse = (TextView) reportDialog.findViewById(R.id.btn_false);
        TextView tvReportVirus = (TextView) reportDialog.findViewById(R.id.btn_virus);
        TextView tvReportIllegal = (TextView) reportDialog.findViewById(R.id.btn_illegal);
        TextView tvReportYellow = (TextView) reportDialog.findViewById(R.id.btn_yellow);
        TextView tvReportCancel = (TextView) reportDialog.findViewById(R.id.btn_cancel);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    default:
                        reportDialog.cancel();
                        break;
                }
            }
        };

        tvReportDirty.setOnClickListener(listener);
        tvReportFalse.setOnClickListener(listener);
        tvReportVirus.setOnClickListener(listener);
        tvReportIllegal.setOnClickListener(listener);
        tvReportYellow.setOnClickListener(listener);
        tvReportCancel.setOnClickListener(listener);
        reportDialog.setCanceledOnTouchOutside(true);
        reportDialog.show();
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

    /**
     * 关注和取消关注直播
     */
    public void followHandle(){

        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }else {
            Api.followHandle(LoginManager.getInstance().getUserID(context),CurLiveInfo.getHostID(), new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    Log.i("关注","apiResponse"+apiResponse.toString());
                    Log.i("关注","getHostID："+CurLiveInfo.getHostID());
                    Log.i("关注","1参数："+LoginManager.getInstance().getUserID(context));

                    if (apiResponse.getCode()==0){
                        //关注成功
                        iv_guanzhu.setSelected(true);
                    }else if (apiResponse.getCode()==1){
                        //取消关注
                        iv_guanzhu.setSelected(false);
                    }else {
                        ToastUtils.showShort(context,apiResponse.getMessage());
                    }
                }
                @Override
                public void onFailure(String errMessage) {
                    super.onFailure(errMessage);
                    ToastUtils.showShort(context,errMessage);
                }
            });
        }
    }

    /**
     * 主播的关注人数
     */
    public void getFollow() {

        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        } else {
            Api.getFollows(CurLiveInfo.getHostID(), new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    Log.i("主播的关注人数", "apiResponse" + apiResponse.toString());

                    if (apiResponse.getCode() == 0) {
                        //主播的关注人数
                        JSONObject parse = (JSONObject) JSON.parse(apiResponse.getData());
                        String follow_nums = parse.getString("follow_nums");
                        tv_guanzhuNum.setText(follow_nums + "关注");
                    }
                }

                @Override
                public void onFailure(String errMessage) {
                    super.onFailure(errMessage);
                    ToastUtils.showShort(context, errMessage);
                }
            });
        }
    }
}

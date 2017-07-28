package com.example.project.limolive.tencentlive.presenters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentlive.model.ChatEntity;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.model.GiftVo;
import com.example.project.limolive.tencentlive.model.LiveInfoJson;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.model.RoomInfoBean;
import com.example.project.limolive.tencentlive.presenters.viewinface.LiveView;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.tencentlive.utils.LogConstants;
import com.example.project.limolive.tencentlive.utils.SxbLog;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.SPUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.view.CustomProgressDialog;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMGroupSystemElem;
import com.tencent.TIMGroupSystemElemType;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.av.TIMAvManager;
import com.tencent.av.sdk.AVRoomMulti;
import com.tencent.av.sdk.AVVideoCtrl;
import com.tencent.av.sdk.AVView;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.core.ILiveLog;
import com.tencent.ilivesdk.core.ILivePushOption;
import com.tencent.ilivesdk.core.ILiveRecordOption;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.ilivesdk.core.ILiveRoomOption;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVText;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;
import static com.example.project.limolive.tencentlive.utils.Constants.PRESENT_MSG;
import static com.example.project.limolive.tencentlive.views.LiveingActivity.GETMENBERINFO;
import static com.example.project.limolive.tencentlive.views.LiveingActivity.GUANZHU;
import static com.example.project.limolive.tencentlive.views.LiveingActivity.NIMOBI;
import static com.example.project.limolive.tencentlive.views.LiveingActivity.PAIHANG;


/**
 * 直播控制类
 */
public class LiveHelper extends Presenter implements ILiveRoomOption.onRoomDisconnectListener, Observer {
    private final String TAG = "LiveHelper";
    private LiveView mLiveView;
    public Context mContext;
    private boolean bCameraOn = false;
    private boolean bMicOn = false;
    private boolean flashLgihtStatus = false;
    private long streamChannelID;
    private SPUtil sp;
    private NotifyServerLiveEnd liveEndTask;
    private CustomProgressDialog mProgressDialog;
    private Handler mHandler;
    class NotifyServerLiveEnd extends AsyncTask<String, Integer, LiveInfoJson> {

        @Override
        protected LiveInfoJson doInBackground(String... strings) {
            return OKhttpHelper.getInstance().notifyServerLiveStop(strings[0]);
        }

        @Override
        protected void onPostExecute(LiveInfoJson result) {

        }
    }

    public LiveHelper(Context context, LiveView liveview,Handler mHandler) {
        mContext = context;
        mLiveView = liveview;
        this.mHandler = mHandler;
        MessageEvent.getInstance().addObserver(this);
    }

    @Override
    public void onDestory() {
        mLiveView = null;
        mContext = null;
        MessageEvent.getInstance().deleteObserver(this);
    }

    /**
     * 进入房间
     */
    public void startEnterRoom() {
        if (LiveMySelfInfo.getInstance().isCreateRoom() == true) {
            Log.i("有房间没有", "没有房间  创建房间");
            createRoom();
        } else {
            Log.i("有房间没有", "有房间  进入房间");
            joinRoom();
        }
    }

    public void startExitRoom() {
        Log.i("监控主播退出了房间", "startExitRoom");
        ILVLiveManager.getInstance().quitRoom(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                CurLiveInfo.setCurrentRequestCount(0);
                //通知结束
                notifyServerLiveEnd();
                if (null != mLiveView) {
                    mLiveView.quiteRoomComplete(LiveMySelfInfo.getInstance().getIdStatus(), true, null);
                } else {
                    mLiveView.quiteRoomComplete(LiveMySelfInfo.getInstance().getIdStatus(), false, null);
                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                if (null != mLiveView) {
                    ToastUtils.showShort(mContext, errMsg.toString());
                    mLiveView.quiteRoomComplete(LiveMySelfInfo.getInstance().getIdStatus(), false, null);
                }
            }
        });
    }

    public void perpareQuitRoom(boolean bPurpose) {
        if (bPurpose) {
            sendGroupCmd(Constants.AVIMCMD_EXITLIVE, "");
        }
        mLiveView.readyToQuit();
    }

    /**
     * 发送信令
     */

    public int sendGroupCmd(int cmd, String param) {        //群组
        ILVCustomCmd customCmd = new ILVCustomCmd();
        customCmd.setCmd(cmd);
        customCmd.setParam(param);
        Log.i("发送群组消息","param"+param.toString());
        customCmd.setType(ILVText.ILVTextType.eGroupMsg);
        return sendCmd(customCmd);
    }

    public int sendC2CCmd(final int cmd, String param, String destId) {  //C2C会话，表示单聊情况自己与对方建立的对话，读取消息和发送消息都是通过会话完成； 单聊
        ILVCustomCmd customCmd = new ILVCustomCmd();
        customCmd.setDestId(destId);
        customCmd.setCmd(cmd);
        customCmd.setParam(param);
        customCmd.setType(ILVText.ILVTextType.eC2CMsg);
        return sendCmd(customCmd);
    }

    /**
     * 打开闪光灯
     */
    public void toggleFlashLight() {
        AVVideoCtrl videoCtrl = ILiveSDK.getInstance().getAvVideoCtrl();
        if (null == videoCtrl) {
            return;
        }

        final Object cam = videoCtrl.getCamera();
        if ((cam == null) || (!(cam instanceof Camera))) {
            return;
        }
        final Camera.Parameters camParam = ((Camera) cam).getParameters();
        if (null == camParam) {
            return;
        }

        Object camHandler = videoCtrl.getCameraHandler();
        if ((camHandler == null) || (!(camHandler instanceof Handler))) {
            return;
        }

        //对摄像头的操作放在摄像头线程
        if (flashLgihtStatus == false) {
            ((Handler) camHandler).post(new Runnable() {
                public void run() {
                    try {
                        camParam.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        ((Camera) cam).setParameters(camParam);
                        flashLgihtStatus = true;
                    } catch (RuntimeException e) {
                        SxbLog.d("setParameters", "RuntimeException");
                    }
                }
            });
        } else {
            ((Handler) camHandler).post(new Runnable() {
                public void run() {
                    try {
                        camParam.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        ((Camera) cam).setParameters(camParam);
                        flashLgihtStatus = false;
                    } catch (RuntimeException e) {
                        SxbLog.d("setParameters", "RuntimeException");
                    }

                }
            });
        }
    }

    public void startRecord(ILiveRecordOption option) {
        ILiveRoomManager.getInstance().startRecordVideo(option, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                SxbLog.i(TAG, "start record success ");
                mLiveView.startRecordCallback(true);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                SxbLog.e(TAG, "start record error " + errCode + "  " + errMsg);
                mLiveView.startRecordCallback(false);
            }
        });
    }

    public void stopRecord() {
        ILiveRoomManager.getInstance().stopRecordVideo(new ILiveCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                SxbLog.d(TAG, "stopRecord->success");
                for (String url : data) {
                    SxbLog.d(TAG, "stopRecord->url:" + url);
                }
                mLiveView.stopRecordCallback(true, data);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                SxbLog.e(TAG, "stopRecord->failed:" + module + "|" + errCode + "|" + errMsg);
                mLiveView.stopRecordCallback(false, null);
            }
        });
    }

    public void startPush(ILivePushOption option) {
        ILiveRoomManager.getInstance().startPushStream(option, new ILiveCallBack<TIMAvManager.StreamRes>() {
            @Override
            public void onSuccess(TIMAvManager.StreamRes data) {
                List<TIMAvManager.LiveUrl> liveUrls = data.getUrls();
                streamChannelID = data.getChnlId();
                mLiveView.pushStreamSucc(data);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                SxbLog.e(TAG, "url error " + errCode + " : " + errMsg);
                Toast.makeText(mContext, "start stream error,try again " + errCode + " : " + errMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void stopPush() {
        ILiveRoomManager.getInstance().stopPushStream(streamChannelID, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                SxbLog.e(TAG, "stopPush->success");
                mLiveView.stopStreamSucc();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                SxbLog.e(TAG, "stopPush->failed:" + module + "|" + errCode + "|" + errMsg);
            }
        });
    }


    @Override
    public void onRoomDisconnect(int errCode, String errMsg) {
        if (null != mLiveView) {
            mLiveView.quiteRoomComplete(LiveMySelfInfo.getInstance().getIdStatus(), true, null);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        List<TIMMessage> list = (List<TIMMessage>) o;
        parseIMMessage(list);
    }

    /**
     * 上报房间信息
     */
    private void notifyServerCreateRoom() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject liveInfo = null;
                try {
                    liveInfo = new JSONObject();
                    if (TextUtils.isEmpty(CurLiveInfo.getTitle())) {
                        liveInfo.put("title", mContext.getString(R.string.text_live_default_title));
                    } else {
                        liveInfo.put("title", CurLiveInfo.getTitle());
                    }
                    liveInfo.put("cover", CurLiveInfo.getCoverurl());
                    liveInfo.put("chatRoomId", CurLiveInfo.getChatRoomId());
                    liveInfo.put("avRoomId", CurLiveInfo.getRoomNum());
                    JSONObject hostinfo = new JSONObject();
                    hostinfo.put("uid", LiveMySelfInfo.getInstance().getId());
                    hostinfo.put("avatar", LiveMySelfInfo.getInstance().getAvatar());
                    hostinfo.put("username", LiveMySelfInfo.getInstance().getNickName());

                    liveInfo.put("host", hostinfo);
                    JSONObject lbs = new JSONObject();
                    lbs.put("longitude", CurLiveInfo.getLong1());
                    lbs.put("latitude", CurLiveInfo.getLat1());
                    lbs.put("address", CurLiveInfo.getAddress());
                    liveInfo.put("lbs", lbs);
                    liveInfo.put("appid", Constants.SDK_APPID);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (liveInfo != null) {
                    SxbLog.standardEnterRoomLog(TAG, "upload room info to serve", "", "room id " + CurLiveInfo.getRoomNum());
                    OKhttpHelper.getInstance().notifyServerNewLiveInfo(liveInfo);
                    //创建直播间返回信息
                }

            }
        }).start();
    }

    public void toggleCamera() {
        bCameraOn = !bCameraOn;
        SxbLog.d(TAG, "toggleCamera->change camera:" + bCameraOn);
        ILiveRoomManager.getInstance().enableCamera(ILiveRoomManager.getInstance().getCurCameraId(), bCameraOn);
    }

    public void toggleMic() {
        bMicOn = !bMicOn;
        SxbLog.d(TAG, "toggleMic->change mic:" + bMicOn);
        ILiveRoomManager.getInstance().enableMic(bMicOn);
    }

    public boolean isMicOn() {
        return bMicOn;
    }

    public void upMemberVideo() {
        if (!ILiveRoomManager.getInstance().isEnterRoom()) {
            SxbLog.e(TAG, "upMemberVideo->with not in room");
        }
        ILVLiveManager.getInstance().upToVideoMember(Constants.VIDEO_MEMBER_ROLE, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                SxbLog.d(TAG, "upToVideoMember->success");
                bMicOn = true;
                bCameraOn = true;
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                SxbLog.e(TAG, "upToVideoMember->failed:" + module + "|" + errCode + "|" + errMsg);
            }
        });
    }

    public void downMemberVideo() {
        if (!ILiveRoomManager.getInstance().isEnterRoom()) {
            SxbLog.e(TAG, "downMemberVideo->with not in room");
        }
        ILVLiveManager.getInstance().downToNorMember(Constants.NORMAL_MEMBER_ROLE, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                bMicOn = false;
                bCameraOn = false;
                SxbLog.e(TAG, "downMemberVideo->onSuccess");
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                SxbLog.e(TAG, "downMemberVideo->failed:" + module + "|" + errCode + "|" + errMsg);
            }
        });
    }

    /**
     * 通知用户UserServer房间
     */
    public void notifyServerLiveEnd() {
        Log.i("退出房间", "走进来没");
        Api.stopLiveRoom(LiveMySelfInfo.getInstance().getMyRoomNum(), new ApiResponseHandler(mContext) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                }
                Log.i("退出房间", apiResponse.toString());
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                Log.i("退出房间", errMessage);
            }
        });
    }

    /**
     * 创建直播间
     */
    private void createRoom() {
        //判断是不是wifi网络 提示一下
        Log.i("网络状态", "网络状态" + NetWorkUtil.getConnectedType(mContext));
        if (!NetWorkUtil.isNetworkConnected(mContext)) {
            hideProgressDialog();
            ToastUtils.showShort(mContext, NET_UNCONNECT);
            return;
        } else if (TextUtils.equals("0", String.valueOf(NetWorkUtil.getConnectedType(mContext)))) {
            Dialog alertDialog = new AlertDialog.Builder(mContext).
                    setTitle("提示").
                    setMessage("是否在非wifi网络开启直播  (土豪略过...)").
                    setCancelable(false).
                    setNegativeButton("设置网络", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NetWorkUtil.openSetting((LiveingActivity) mContext);
                        }
                    }).
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            createRoom_Into();
                        }
                    }).
                    create();
            alertDialog.show();
        } else {
            createRoom_Into();
        }
    }

    public void createRoom_Into() {
        ILiveRoomOption hostOption = new ILiveRoomOption(LiveMySelfInfo.getInstance().getId())
                .roomDisconnectListener(this)
                .controlRole(Constants.HOST_ROLE)
                .authBits(AVRoomMulti.AUTH_BITS_DEFAULT)
                .videoRecvMode(AVRoomMulti.VIDEO_RECV_MODE_SEMI_AUTO_RECV_CAMERA_VIDEO);
        if (!TextUtils.isEmpty(LiveMySelfInfo.getInstance().getMyRoomNum())) {
            Log.i("创建房间", "getMyRoomNum" + LiveMySelfInfo.getInstance().getMyRoomNum());
            Log.i("创建房间", "LiveMySelfInfo getId" + LiveMySelfInfo.getInstance().getId());
            Log.i("创建房间", "LoginManager  getId" + LoginManager.getInstance().getUserID(mContext));
            Log.i("创建房间", "LiveMySelfInfo  getMyRoomNum" + LiveMySelfInfo.getInstance().getMyRoomNum());
            Log.i("创建房间", "LoginManager  getMyRoomNum" + LoginManager.getInstance().getRoomNum(mContext));
            ILVLiveManager.getInstance().createRoom(Integer.parseInt(LiveMySelfInfo.getInstance().getMyRoomNum()), hostOption, new ILiveCallBack() {
                @Override
                public void onSuccess(Object data) {
                    sp = SPUtil.getInstance(mContext);
                    String lon = sp.getString("lon");//网络定位经度1
                    String lat = sp.getString("lat");//网络定位wei度1
                    String LocationAddress = sp.getString("LocationAddress");//网络定位wei度1
                    ILiveLog.d(TAG, "ILVB-DBG|startEnterRoom->create room sucess");
                    bCameraOn = true;
                    bMicOn = true;
                    mLiveView.enterRoomComplete(LiveMySelfInfo.getInstance().getIdStatus(), true);
                    //notifyServerCreateRoom();
                    Log.i("创建房间", LiveMySelfInfo.getInstance().getPhone() + "getPhone");
                    Log.i("创建房间", CurLiveInfo.getRoomNum() + "getRoomNum");
                    GreatNewRoom(LiveMySelfInfo.getInstance().getPhone(), lon, lat, LocationAddress, CurLiveInfo.getLiveType(), CurLiveInfo.getTitle(), CurLiveInfo.getRoomNum());

                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    Log.d("创建房间", "ILVB-DBG|startEnterRoom->create room failed:" + module + "|" + errCode + "|" + errMsg);
                    if (null != mLiveView) {
                        Log.i("创建房间", "房间创建失败");
                        mLiveView.quiteRoomComplete(LiveMySelfInfo.getInstance().getIdStatus(), true, null);
                    }
                }
            });
        }
    }

    /**
     * 创建直播间
     */
    private void GreatNewRoom(final String phone, String longitude, String latitude, String address, String live_type, String title, String chat_room_id) {
       /* final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setMessage(LOADING);
        dialog.show();*/
        mProgressDialog = new CustomProgressDialog(mContext);
        showProgressDialog("正在处理，请稍后...");
        Api.GreatNewRoom(phone, longitude, latitude, address, live_type, title, chat_room_id, new ApiResponseHandler(mContext) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("创建直播间", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    RoomInfoBean roomInfoBean = JSON.parseObject(apiResponse.getData(), RoomInfoBean.class);
                    Log.i("创建直播间", roomInfoBean.toString());
                    CurLiveInfo.setRoomNum(roomInfoBean.getChat_room_id());
                    //成功了 发送广播 获取成员信息
                    Intent intent = new Intent();
                    intent.setAction(GETMENBERINFO);
                    mContext.sendBroadcast(intent);

                    //开启对焦
                    ILiveRoomOption option = new ILiveRoomOption(phone)
                            .autoFocus(true);
                } else {
                    ToastUtils.showShort(mContext, apiResponse.getMessage());
                }
                bCameraOn = true;
                bMicOn = true;
                mLiveView.enterRoomComplete(LiveMySelfInfo.getInstance().getIdStatus(), true);
                //  dialog.dismiss();
                hideProgressDialog();
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                if (null != mLiveView) {
                    mLiveView.quiteRoomComplete(LiveMySelfInfo.getInstance().getIdStatus(), true, null);
                }
                hideProgressDialog();
            }
        });
    }

    private void joinRoom() {
        ILiveRoomOption memberOption = new ILiveRoomOption(CurLiveInfo.getHostID())
                .autoCamera(false)
                .roomDisconnectListener(this)
                .controlRole(Constants.NORMAL_MEMBER_ROLE)
                .authBits(AVRoomMulti.AUTH_BITS_JOIN_ROOM | AVRoomMulti.AUTH_BITS_RECV_AUDIO | AVRoomMulti.AUTH_BITS_RECV_CAMERA_VIDEO | AVRoomMulti.AUTH_BITS_RECV_SCREEN_VIDEO)
                .videoRecvMode(AVRoomMulti.VIDEO_RECV_MODE_SEMI_AUTO_RECV_CAMERA_VIDEO)
                .autoMic(false);
        ILVLiveManager.getInstance().joinRoom(Integer.parseInt(CurLiveInfo.getRoomNum()), memberOption, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                ILiveLog.d(TAG, "ILVB-DBG|startEnterRoom->join room sucess");
                mLiveView.enterRoomComplete(LiveMySelfInfo.getInstance().getIdStatus(), true);
                //成功了 发送广播 获取成员信息
                Intent intent = new Intent();
                intent.setAction(GETMENBERINFO);
                mContext.sendBroadcast(intent);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ILiveLog.d(TAG, "ILVB-DBG|startEnterRoom->join room failed:" + module + "|" + errCode + "|" + errMsg);
                if (null != mLiveView) {
                    mLiveView.quiteRoomComplete(LiveMySelfInfo.getInstance().getIdStatus(), true, null);
                }
            }
        });
        SxbLog.i(TAG, "joinLiveRoom startEnterRoom ");
    }

    private int sendCmd(final ILVCustomCmd cmd) {
        return ILVLiveManager.getInstance().sendCustomCmd(cmd, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                SxbLog.i(TAG, "sendCmd->success:" + cmd.getCmd() + "|" + cmd.getParam());
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Toast.makeText(mContext, "sendCmd->failed:" + module + "|" + errCode + "|" + errMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 解析消息回调
     *
     * @param list 消息列表
     */
    private void parseIMMessage(List<TIMMessage> list) {
        List<TIMMessage> tlist = list;

        for (int i = tlist.size() - 1; i >= 0; i--) {
            TIMMessage currMsg = tlist.get(i);

            for (int j = 0; j < currMsg.getElementCount(); j++) {
                if (currMsg.getElement(j) == null)
                    continue;
                TIMElem elem = currMsg.getElement(j);
                TIMElemType type = elem.getType();
                String sendId = currMsg.getSender();

                //系统消息
                if (type == TIMElemType.GroupSystem) {
                    if (TIMGroupSystemElemType.TIM_GROUP_SYSTEM_DELETE_GROUP_TYPE == ((TIMGroupSystemElem) elem).getSubtype()) {
                        mLiveView.hostLeave("host", null);
                    }
                }
                //定制消息
                if (type == TIMElemType.Custom) {
                    String id, nickname;
                    if (currMsg.getSenderProfile() != null) {
                        id = currMsg.getSenderProfile().getIdentifier();
                        nickname = currMsg.getSenderProfile().getNickName();
                        Log.i("定制消息","ssss"+ id+""+nickname);
                    } else {
                        id = sendId;
                        nickname = sendId;

                        Log.i("定制消息", "wwww"+id+""+nickname);
                    }
                    handleCustomMsg(elem, id, nickname);
                    continue;
                }

                //其他群消息过滤

                if (currMsg.getConversation() != null && currMsg.getConversation().getPeer() != null)
                    if (!CurLiveInfo.getChatRoomId().equals(currMsg.getConversation().getPeer())) {
                        continue;
                    }

                //最后处理文本消息
                if (type == TIMElemType.Text) {
                    if (currMsg.isSelf()) {
                        handleTextMessage(elem, LiveMySelfInfo.getInstance().getNickName());
                    } else {
                        String nickname;
                        Log.i("处理文字消息","currMsg.getSenderProfile()"+currMsg.getSenderProfile().toString());
                        if (currMsg.getSenderProfile() != null && (!currMsg.getSenderProfile().getNickName().equals(""))) {
                            nickname = currMsg.getSenderProfile().getNickName();
                        } else {
                            nickname = sendId;
                        }
                        handleTextMessage(elem, nickname);
                    }
                }
            }
        }
    }

    /**
     * 处理文本消息解析
     *
     * @param elem
     * @param name
     */
    private void handleTextMessage(TIMElem elem, String name) {
        TIMTextElem textElem = (TIMTextElem) elem;

        mLiveView.refreshText(textElem.getText(), name);
    }

    /**
     * 处理定制消息 赞 关注 取消关注
     *
     * @param elem
     */
    private void handleCustomMsg(TIMElem elem, String identifier, String nickname) {
        try {
            if (null == mLiveView) {
                return;
            }
            String customText = new String(((TIMCustomElem) elem).getData(), "UTF-8");
            Log.i("接收到的消息", "customText.." + customText);

            if (customText.contains("userAction")) {
                JSONTokener jsonParser = new JSONTokener(customText);
                // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
                // 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
                JSONObject json = (JSONObject) jsonParser.nextValue();
                int action = json.getInt(Constants.CMD_KEY);
                Log.i("接收到的消息", "action.." + action);
                switch (action) {
                    case Constants.AVIMCMD_MUlTI_HOST_INVITE:
                        SxbLog.d(TAG, LogConstants.ACTION_VIEWER_SHOW + LogConstants.DIV + LiveMySelfInfo.getInstance().getId() + LogConstants.DIV + "receive invite message" +
                                LogConstants.DIV + "id " + identifier);
                        mLiveView.showInviteDialog();
                        break;
                    case Constants.AVIMCMD_MUlTI_JOIN:
                        SxbLog.i(TAG, "handleCustomMsg " + identifier);
                        mLiveView.cancelInviteView(identifier);
                        break;
                    case Constants.AVIMCMD_MUlTI_REFUSE:
                        mLiveView.cancelInviteView(identifier);
                        Toast.makeText(mContext, identifier + " refuse !", Toast.LENGTH_SHORT).show();
                        break;
                    case Constants.AVIMCMD_PRAISE:
                        mLiveView.refreshThumbUp(identifier, nickname);
                        //同步关注人数
                        Message msg = new Message();
                        msg.obj = "guanzhu";
                        msg.what = GUANZHU;
                        mHandler.sendMessage(msg);
                        break;
                    case Constants.AVIMCMD_ENTERLIVE:   //进入房间
                        if (mLiveView != null){
                            mLiveView.memberJoin(identifier, nickname);
                        }
                        //更新显示排列头像
                        Message msg1 = new Message();
                        msg1.obj = "paihanghead";
                        msg1.what = PAIHANG;
                        mHandler.sendMessage(msg1);

                        break;
                    case Constants.AVIMCMD_EXITLIVE:
                        //mLiveView.refreshText("quite live", sendId);
                        if (mLiveView != null)
                            mLiveView.memberQuit(identifier, nickname);
                        break;
                    case Constants.AVIMCMD_MULTI_CANCEL_INTERACT://主播关闭摄像头命令
                        //如果是自己关闭Camera和Mic
                        String closeId = json.getString(Constants.CMD_PARAM);
                        if (closeId.equals(LiveMySelfInfo.getInstance().getId())) {//是自己
                            //TODO 被动下麦 下麦 下麦
                            downMemberVideo();
                        }
                        //其他人关闭小窗口
                        ILiveRoomManager.getInstance().getRoomView().closeUserView(closeId, AVView.VIDEO_SRC_TYPE_CAMERA, true);
                        mLiveView.hideInviteDialog();
                        mLiveView.refreshUI(closeId);
                        break;
                    case Constants.AVIMCMD_MULTI_HOST_CANCELINVITE:
                        mLiveView.hideInviteDialog();
                        break;
                    case Constants.AVIMCMD_MULTI_HOST_CONTROLL_CAMERA:
                        toggleCamera();
                        break;
                    case Constants.AVIMCMD_MULTI_HOST_CONTROLL_MIC:
                        toggleMic();
                        break;
                    case Constants.AVIMCMD_HOST_LEAVE:
                        Log.i("监控主播退出了房间", "AVIMCMD_HOST_LEAVE" + "identifier" + identifier);
                        mLiveView.hostLeave(identifier, nickname);
                        //更新显示排列头像
                        Message msg2 = new Message();
                        msg2.obj = "paihanghead";
                        msg2.what = PAIHANG;
                        mHandler.sendMessage(msg2);
                        break;
                    case Constants.AVIMCMD_HOST_BACK:
                        mLiveView.hostBack(identifier, nickname);

                    case PRESENT_MSG:

                        break;
                    default:
                        break;
                }
            } else {
                Animation liwu = (Animation) AnimationUtils.loadAnimation(mContext, R.anim.liwu);
                Animation enter = (Animation) AnimationUtils.loadAnimation(mContext, R.anim.act_open_enter);
                Animation exit = (Animation) AnimationUtils.loadAnimation(mContext, R.anim.act_open_exit);
                liwu.setRepeatMode(Animation.REVERSE);
                String splitItems[] = customText.split("&");
                int cmd = Integer.parseInt(splitItems[1]);
                if (cmd == PRESENT_MSG) {
                    String present_i = String.valueOf(splitItems[1]);
                    final String present_name = String.valueOf(splitItems[2]);
                    final String present_image = String.valueOf(splitItems[3]);
                    final String present_type = String.valueOf(splitItems[4]);
                    int asd = Integer.parseInt(present_type);
                    /*if (asd == 16 || asd == 17 || asd == 18 || asd == 19 || asd == 20 || asd == 21 || asd == 22) {
                        if (asd == 16) {
                            LiveingActivity.gif_donghua.setImageResource(R.drawable.present_16);
                            LiveingActivity.gif_donghua.startAnimation(liwu);
                        } else if (asd == 17) {
                            LiveingActivity.gif_donghua.setImageResource(R.drawable.present_17);
                            LiveingActivity.gif_donghua.startAnimation(liwu);
                        } else if (asd == 18) {
                            LiveingActivity.gif_donghua.setImageResource(R.drawable.present_18);
                            LiveingActivity.gif_donghua.startAnimation(liwu);
                        } else if (asd == 19) {
                            LiveingActivity.gif_donghua.setImageResource(R.drawable.present_19);
                            LiveingActivity.gif_donghua.startAnimation(liwu);
                        } else if (asd == 20) {
                            LiveingActivity.gif_donghua.setImageResource(R.drawable.present_20);
                            LiveingActivity.gif_donghua.startAnimation(liwu);
                        } else if (asd == 21) {
                            LiveingActivity.gif_donghua.setImageResource(R.drawable.present_21);
                            LiveingActivity.gif_donghua.startAnimation(liwu);
                        } else if (asd == 22) {
                            LiveingActivity.gif_donghua.setImageResource(R.drawable.present_22);
                            LiveingActivity.gif_donghua.startAnimation(liwu);
                        }
                    } else {*/
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GiftVo vo = new GiftVo();
                                vo.setName(present_name);
                                vo.setHeard(present_image);
                                vo.setType(present_type);
                                vo.setNum(1);
                                LiveingActivity.giftManger.addGift(vo);
                            }
                        }).start();
                  //  }

                    //同步显示魅力值
                    Message msg = new Message();
                    msg.obj = "hostCoins";
                    msg.what = NIMOBI;
                    mHandler.sendMessage(msg);

                    //更新显示排列头像
                    Message msg3 = new Message();
                    msg3.obj = "paihanghead";
                    msg3.what = PAIHANG;
                    mHandler.sendMessage(msg3);

                    ChatEntity en = new ChatEntity();
                    en.setMessage_type(present_i);
                    en.setPresent_name(present_name);
                    en.setPresenr_type(present_type);
                    LiveingActivity.mArrayListPresent.add(en);
                    LiveingActivity.mArrayListChatEntity.add(en);
                    LiveingActivity.mChatMsgListAdapter.notifyDataSetChanged();
                    if (LiveingActivity.mListViewMsgItems.getCount() > 1) {
                        if (true)
                            LiveingActivity.mListViewMsgItems.setSelection(0);
                        else
                            LiveingActivity.mListViewMsgItems.setSelection(LiveingActivity.mListViewMsgItems.getCount() - 1);
                    }

                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException ex) {
            // 异常处理代码
        }
    }

    public void showProgressDialog(String msg) {
        mProgressDialog = new CustomProgressDialog(mContext);
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

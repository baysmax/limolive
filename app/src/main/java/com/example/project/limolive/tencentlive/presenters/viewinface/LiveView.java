package com.example.project.limolive.tencentlive.presenters.viewinface;


import com.example.project.limolive.tencentlive.model.LiveInfoJson;
import com.tencent.av.TIMAvManager;

import java.util.List;

/**
 *  直播界面回调
 */
public interface LiveView extends MvpView {

    void enterRoomComplete(int id_status, boolean succ);

    void quiteRoomComplete(int id_status, boolean succ, LiveInfoJson liveinfo);

    void showInviteDialog();

    void refreshText(String text, String name);

    void refreshThumbUp(String id, String name);

    void refreshUI(String id);

    boolean showInviteView(String id);

    void cancelInviteView(String id);

    void cancelMemberView(String id);

    void memberJoin(String id, String name);

    void memberQuit(String id, String name);

    void readyToQuit();

    void hideInviteDialog();

    void pushStreamSucc(TIMAvManager.StreamRes streamRes);

    void stopStreamSucc();

    void startRecordCallback(boolean isSucc);

    void stopRecordCallback(boolean isSucc, List<String> files);

    void hostLeave(String id, String name);

    void hostBack(String id, String name);
}

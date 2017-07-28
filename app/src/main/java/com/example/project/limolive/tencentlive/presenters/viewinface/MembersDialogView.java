package com.example.project.limolive.tencentlive.presenters.viewinface;

import com.example.project.limolive.tencentlive.model.MemberInfo;

import java.util.ArrayList;


/**
 * 成员列表回调
 */
public interface MembersDialogView extends MvpView {

    void showMembersList(ArrayList<MemberInfo> data);

}

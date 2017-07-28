package com.example.project.limolive.tencentlive.presenters.viewinface;

import com.example.project.limolive.tencentlive.model.LiveInfoJson;

import java.util.ArrayList;


/**
 *  列表页面回调
 */
public interface LiveListView extends MvpView {

    void showFirstPage(ArrayList<LiveInfoJson> livelist);
}

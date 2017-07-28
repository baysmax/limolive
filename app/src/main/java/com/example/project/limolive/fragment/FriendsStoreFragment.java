package com.example.project.limolive.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.FrendInfoActivity;
import com.example.project.limolive.activity.MoreLiveingActicity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.live.Be_Liveing_Bean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentim.model.FriendInfoBean;
import com.example.project.limolive.tencentim.ui.SearchFriendActivity;
import com.example.project.limolive.sidebar.BMJLetterListAdapter;
import com.example.project.limolive.sidebar.PinYinComparator;
import com.example.project.limolive.sidebar.SideBar;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 好友店铺 fragment
 *
 * @author 20161030
 */
public class FriendsStoreFragment extends BaseFragment implements SideBar.OnTouchLetterChangedListener, View.OnClickListener {

    private ListView bmj_lv_letter;
    private TextView bmj_letter_letter_tip;
    private SideBar bmj_letter_sidebar;
    private BMJLetterListAdapter mAdapter;
    private List<FriendInfoBean> friendInfoBeens;
    private List<Be_Liveing_Bean> be_Liveing_Beans;//在直播的2个（更多）
    private SimpleDraweeView draweview01, draweview02;
    private TextView tv_01, tv_02;
    private LinearLayout ll_liveOne, ll_liveTwo;
    private LinearLayout ll_click_more,ll_search;
    private EditText sy_query;
    private AutoSwipeRefreshLayout swipe_refresh_tool;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_friends_store, inflater, container);
    }

    @Override
    protected void initView() {
        super.initView();
        loadTitle();
        bmj_lv_letter = (ListView) findViewById(R.id.bmj_lv_letter);
        View header = ViewGroup.inflate(getActivity(), R.layout.friends_store_header, null);
        bmj_lv_letter.addHeaderView(header);
        bmj_letter_letter_tip = (TextView) findViewById(R.id.bmj_letter_letter_tip);
        bmj_letter_sidebar = (SideBar) findViewById(R.id.bmj_letter_sidebar);
        ll_liveOne = (LinearLayout) header.findViewById(R.id.ll_liveOne);
        ll_liveTwo = (LinearLayout) header.findViewById(R.id.ll_liveTwo);
        draweview01 = (SimpleDraweeView) header.findViewById(R.id.draweview01);
        draweview02 = (SimpleDraweeView) header.findViewById(R.id.draweview02);
        ll_click_more = (LinearLayout) header.findViewById(R.id.ll_click_more);
        ll_search = (LinearLayout) header.findViewById(R.id.ll_search);//搜索框
        swipe_refresh_tool = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_refresh_tool);
        swipe_refresh_tool.autoRefresh();
        sy_query = (EditText) header.findViewById(R.id.sy_query);//搜索框edittext
        tv_01 = (TextView) header.findViewById(R.id.tv_01);
        tv_02 = (TextView) header.findViewById(R.id.tv_02);
        bmj_letter_sidebar.setTextView(bmj_letter_letter_tip);
        bmj_letter_sidebar.setOnTouchLetterChangedListener(this);
        BindEvent();
        initData();
        swipe_refresh_tool.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFriendLists();
            }
        });
        swipe_refresh_tool.setColorSchemeColors(Color.parseColor("#31D5BA"));
    }

    private void BindEvent() {
        ll_liveOne.setOnClickListener(this);
        ll_liveTwo.setOnClickListener(this);
        ll_click_more.setOnClickListener(this);
        ll_search.setOnClickListener(this);
    }

    private void loadTitle() {
        setTitleString(getString(R.string.friends_store_title));
        setRightImage(R.mipmap.add_friends);
        setRightRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchFriendActivity.class);
                intent.putExtra("fromWhere","AddFriend");
                getActivity().startActivity(intent);
            }
        });
    }

    private void initData() {
        getFriendLists();
        getLiveLists(null, 1);
        be_Liveing_Beans = new ArrayList<>();
        friendInfoBeens = new ArrayList<>();
        mAdapter = new BMJLetterListAdapter(getActivity(), friendInfoBeens);
        bmj_lv_letter.setAdapter(mAdapter);
        bmj_lv_letter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("friendInfoBeen", friendInfoBeens.get(position-1));
                intent.putExtras(bundle);
                intent.setClass(getActivity(), FrendInfoActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onTouchLetterChanged(String s) {
        for (FriendInfoBean item : friendInfoBeens) {
            if (item.getLetter().equals(s)) {
                bmj_lv_letter.setSelection(friendInfoBeens.indexOf(item));
                return;
            }
        }
    }

    /**
     * 好友列表
     */
    private void getFriendLists() {

        Log.i("获取好友列表", LoginManager.getInstance().getUserID(getActivity()));
        Api.getFriendLists(LoginManager.getInstance().getUserID(getActivity()), new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("获取好友列表", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    List<FriendInfoBean> list = JSON.parseArray(apiResponse.getData(), FriendInfoBean.class);
                    friendInfoBeens.clear();
                    friendInfoBeens.addAll(list);
                    sortData();
                }else {
                    ToastUtils.showShort(getActivity(),apiResponse.getMessage());
                    if (apiResponse.getCode() == -2){
                    }
                    swipe_refresh_tool.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                swipe_refresh_tool.setRefreshing(false);
            }
        });
    }

    private void sortData() {
        //重新把list进行排序
        Collections.sort(friendInfoBeens, new PinYinComparator());
        //排序完毕在线程中更新数据
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bmj_lv_letter.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
                swipe_refresh_tool.setRefreshing(false);
            }
        });
    }

    /**
     * 在直播的2个（更多）
     */
    private void getLiveLists(String type, int page) {
        Api.livelists(LoginManager.getInstance().getUserID(getActivity()), type, page, new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("在直播的2个和更多", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    List<Be_Liveing_Bean> list = JSON.parseArray(apiResponse.getData(), Be_Liveing_Bean.class);
                    be_Liveing_Beans.clear();
                    be_Liveing_Beans.addAll(list);

                    if (be_Liveing_Beans.size() > 1) {
                        Glide.with(getActivity()).load(ApiHttpClient.API_PIC + be_Liveing_Beans.get(0).getCover()).into(draweview01);
                        Glide.with(getActivity()).load(ApiHttpClient.API_PIC + be_Liveing_Beans.get(1).getCover()).into(draweview02);
                        tv_01.setText(be_Liveing_Beans.get(0).getTitle());
                        tv_02.setText(be_Liveing_Beans.get(1).getTitle());
                    } else if (be_Liveing_Beans.size() == 0) {
                        draweview01.setBackground(getResources().getDrawable(R.mipmap.picture));
                        draweview02.setBackground(getResources().getDrawable(R.mipmap.picture));
                        tv_01.setText("默认");
                        tv_02.setText("默认");
                    } else if (be_Liveing_Beans.size() == 1) {
                        Glide.with(getActivity()).load(ApiHttpClient.API_PIC + be_Liveing_Beans.get(0).getCover()).into(draweview01);
                        tv_01.setText(be_Liveing_Beans.get(0).getTitle());
                        draweview02.setBackground(getResources().getDrawable(R.mipmap.picture));
                        tv_02.setText("默认");
                    }
                }
            }
            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_liveOne:
                if (be_Liveing_Beans != null && be_Liveing_Beans.size() > 0) {
                    if (be_Liveing_Beans.get(0).getHost().getUid().equals(LiveMySelfInfo.getInstance().getId())) {
                        intent.setClass(getActivity(), LiveingActivity.class);
                        intent.putExtra(Constants.ID_STATUS, Constants.HOST);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.HOST);
                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(be_Liveing_Beans.get(0).getHost().getUid());
                        CurLiveInfo.setHostName(be_Liveing_Beans.get(0).getHost().getUsername());
                        CurLiveInfo.setHostAvator(be_Liveing_Beans.get(0).getHost().getAvatar());
                        CurLiveInfo.setRoomNum(be_Liveing_Beans.get(0).getAvRoomId());
                        CurLiveInfo.setMembers(Integer.parseInt(be_Liveing_Beans.get(0).getWatchCount()) + 1); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(be_Liveing_Beans.get(0).getAdmireCount()));
                        CurLiveInfo.setAddress(be_Liveing_Beans.get(0).getLbs().getAddress());
                        startActivity(intent);
                    } else {
                        intent.setClass(getActivity(), LiveingActivity.class);
                        intent.putExtra(Constants.ID_STATUS, Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(be_Liveing_Beans.get(0).getHost().getUid());
                        CurLiveInfo.setHostName(be_Liveing_Beans.get(0).getHost().getUsername());
                        CurLiveInfo.setHostAvator(be_Liveing_Beans.get(0).getHost().getAvatar());
                        CurLiveInfo.setRoomNum(be_Liveing_Beans.get(0).getAvRoomId());
                        CurLiveInfo.setMembers(Integer.parseInt(be_Liveing_Beans.get(0).getWatchCount()) + 1); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(be_Liveing_Beans.get(0).getAdmireCount()));
                        CurLiveInfo.setAddress(be_Liveing_Beans.get(0).getLbs().getAddress());
                        startActivity(intent);
                    }
                }

                break;
            case R.id.ll_liveTwo:
                if (be_Liveing_Beans != null && be_Liveing_Beans.size() > 1) {
                    if (be_Liveing_Beans.get(1).getHost().getUid().equals(LiveMySelfInfo.getInstance().getId())) {
                        intent.setClass(getActivity(), LiveingActivity.class);
                        intent.putExtra(Constants.ID_STATUS, Constants.HOST);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.HOST);
                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(be_Liveing_Beans.get(1).getHost().getUid());
                        CurLiveInfo.setHostName(be_Liveing_Beans.get(1).getHost().getUsername());
                        CurLiveInfo.setHostAvator(be_Liveing_Beans.get(1).getHost().getAvatar());
                        CurLiveInfo.setRoomNum(be_Liveing_Beans.get(1).getAvRoomId());
                        CurLiveInfo.setMembers(Integer.parseInt(be_Liveing_Beans.get(1).getWatchCount()) + 1); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(be_Liveing_Beans.get(1).getAdmireCount()));
                        CurLiveInfo.setAddress(be_Liveing_Beans.get(1).getLbs().getAddress());
                        startActivity(intent);
                    } else {
                        intent.setClass(getActivity(), LiveingActivity.class);
                        intent.putExtra(Constants.ID_STATUS, Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.MEMBER);

                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(be_Liveing_Beans.get(1).getHost().getUid());
                        CurLiveInfo.setHostName(be_Liveing_Beans.get(1).getHost().getUsername());
                        CurLiveInfo.setHostAvator(be_Liveing_Beans.get(1).getHost().getAvatar());
                        CurLiveInfo.setRoomNum(be_Liveing_Beans.get(1).getAvRoomId());
                        CurLiveInfo.setMembers(Integer.parseInt(be_Liveing_Beans.get(1).getWatchCount()) + 1); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(be_Liveing_Beans.get(1).getAdmireCount()));
                        CurLiveInfo.setAddress(be_Liveing_Beans.get(1).getLbs().getAddress());
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_click_more:
                intent.setClass(getActivity(), MoreLiveingActicity.class);
                startActivity(intent);
                break;
            case R.id.ll_search:
                intent.setClass(getActivity(), SearchFriendActivity.class);
                intent.putExtra("fromWhere","IsFriend");
                startActivity(intent);
                break;
        }
    }
}

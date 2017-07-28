package com.example.project.limolive.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.AddChangeAddressActivity;
import com.example.project.limolive.activity.BaseActivity;
import com.example.project.limolive.presenter.AddressPresenter;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

/**
 * 地址管理
 * @author hwj on 2016/12/17.
 */

public class AddressManagerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        Presenter.NotificationToActivity{

    private AutoSwipeRefreshLayout swipe_list;
    private ListView lv_address_list;

    private AddressPresenter addressPresenter;

    private Button btn_add_address;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        addressPresenter=new AddressPresenter(context);
        addressPresenter.registerMsgToActivity(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_address_list,inflater, container);
    }

    @Override
    protected void initView() {
        super.initView();
        loadTitle();
        swipe_list= (AutoSwipeRefreshLayout) findViewById(R.id.swipe_list);
        swipe_list.setColorSchemeColors(BaseActivity.STATUS_BAR_COLOR_DEFAULT);
        swipe_list.setOnRefreshListener(this);

        lv_address_list= (ListView) findViewById(R.id.lv_address_list);
        lv_address_list.setAdapter(addressPresenter.getAddressAdapter());

        btn_add_address= (Button) findViewById(R.id.btn_add_address);
        initEvent();
    }

    private void initEvent() {
        btn_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddChangeAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadTitle() {
        setTitleString(getString(R.string.mine_address_title));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onRefresh() {
        addressPresenter.refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!swipe_list.isRefreshing()){
            swipe_list.autoRefresh();
        }
    }

    @Override
    public void presenterTakeAction(int action) {
        if(action==AddressPresenter.ADDRESS_LIST){
            swipe_list.setRefreshing(false);
        }
    }
}

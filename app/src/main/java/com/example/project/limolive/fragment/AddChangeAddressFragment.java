package com.example.project.limolive.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.bean.ShopAddress;
import com.example.project.limolive.helper.DialogFactory;
import com.example.project.limolive.presenter.AddressPresenter;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.widget.address.CityPicker;

/**
 * 添加或修改地址
 * @author hwj on 2016/12/20.
 */

public class AddChangeAddressFragment extends BaseFragment implements Presenter.NotificationToActivity{

    private AddressPresenter presenter;
    private Button btn_delete_address; //删除

    private EditText edit_receive_man;//收货人
    private EditText edit_user_phone;//手机号码
    private TextView edit_province;//省市区
    private EditText edit_detail_address;//详细地址

    private String province;
    private String city;
    private String district;
    //地址对象
    private ShopAddress shopAddress;

    //是否是修改页面
    private boolean isChange=false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_add_address,inflater, container);
    }

    @Override
    protected void initView() {
        super.initView();
        presenter=new AddressPresenter(getActivity());
        presenter.registerMsgToActivity(this);
        loadTitle();

        edit_receive_man= (EditText) findViewById(R.id.edit_receive_man);
        edit_user_phone= (EditText) findViewById(R.id.edit_user_phone);
        edit_detail_address= (EditText) findViewById(R.id.edit_detail_address);
        edit_province= (TextView) findViewById(R.id.edit_province);

        btn_delete_address= (Button) findViewById(R.id.btn_delete_address);
        btn_delete_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
                if(isChange&&shopAddress!=null){
                    presenter.deleteAddress(shopAddress.getAddress_id());
                }else{
                    getActivity().finish();
                }
            }
        });

        edit_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择省市区
                showCityDialog();
                hideSoft();
            }
        });

        getAddressInfo();
    }

    private void getAddressInfo() {
        Bundle bundle=getArguments();
        if(bundle!=null){
            Parcelable bean=bundle.getParcelable("address_model");
            if(bean!=null&&bean instanceof ShopAddress){
                shopAddress= (ShopAddress) bean;
                isChange=true;
            }
        }
        refreshUI();
    }

    private void refreshUI() {
        if(shopAddress==null){
            return;
        }
        province=shopAddress.getProvince_name();
        city=shopAddress.getCity_name();
        district=shopAddress.getDistrict_name();
        edit_receive_man.setText(shopAddress.getConsignee());
        edit_user_phone.setText(shopAddress.getMobile());
        edit_province.setText(province+" "+city+" "+district);
        edit_detail_address.setText(shopAddress.getAddress());
    }

    private void hideSoft() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.root_view).getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 显示省市区
     */
    private void showCityDialog() {
        CityPicker cityPicker= DialogFactory.getCityPickerDialog(getActivity());
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                edit_province.setText("" + citySelected[0] + " " + citySelected[1] + " "
                        + citySelected[2]);
                province=citySelected[0];
                city=citySelected[1];
                district=citySelected[2];
            }
        });
        cityPicker.show();
    }

    private void loadTitle() {
        setTitleString(getString(R.string.mine_address_change));
        setLeftImage(R.mipmap.icon_return);
        setRightText(getString(R.string.save));
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        setRightRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChange){
                    presenter.changeAddress(shopAddress.getAddress_id()
                            ,edit_receive_man.getText().toString().trim()
                            ,edit_user_phone.getText().toString().trim()
                            ,province,city,district
                            ,edit_detail_address.getText().toString().trim());
                }else{
                    presenter.saveAddress(edit_receive_man.getText().toString().trim()
                            ,edit_user_phone.getText().toString().trim()
                            ,province,city,district
                            ,edit_detail_address.getText().toString().trim()
                    );
                }
            }
        });
    }

    @Override
    public void presenterTakeAction(int action) {
        if(action==AddressPresenter.CHANGE_ADDRESS){
            getActivity().finish();
        }
    }
}

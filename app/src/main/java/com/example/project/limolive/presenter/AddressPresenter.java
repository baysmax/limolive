package com.example.project.limolive.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListAdapter;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.AddChangeAddressActivity;
import com.example.project.limolive.adapter.AddressListAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.ShopAddress;
import com.example.project.limolive.helper.DialogFactory;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hwj on 2016/12/20.
 */

public class AddressPresenter extends Presenter implements AddressListAdapter.EditClickListener, AddressListAdapter.CheckClickListener {

    public static final int ADDRESS_LIST = 1;
    public static final int CHANGE_ADDRESS = 2;

    private AddressListAdapter adapter;
    private List<ShopAddress> data;


    public AddressPresenter(Context context) {
        super(context);
    }

    @Override
    protected void getDate(boolean isClear) {
        super.getDate(isClear);
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        Api.getAddress(LoginManager.getInstance().getUserID(context),
                new ApiResponseHandler(context) {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if (apiResponse.getCode() == Api.SUCCESS) {
                            List<ShopAddress> list =
                                    JSON.parseArray(apiResponse.getData(), ShopAddress.class);
                            data.clear();
                            data.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                        if (tellActivity != null) {
                            tellActivity.presenterTakeAction(ADDRESS_LIST);
                        }
                    }
                });
    }

    @Override
    public void refresh() {
        super.refresh();
        getDate(true);
    }

    /**
     * 获取ListView 的adapter
     *
     * @return
     */
    public ListAdapter getAddressAdapter() {
        if (adapter == null) {
            data = new ArrayList<>();
            adapter = new AddressListAdapter(context, data);
            adapter.setOnEditClickListener(this);
            adapter.setOnCheckBoxClickListener(this);
        }
        return adapter;
    }

    @Override
    public void onClick(View v) {
        Object obj = v.getTag();
        if (obj != null && obj instanceof Integer) {
            int position = (int) obj;
            Intent intent = new Intent(context, AddChangeAddressActivity.class);
            intent.putExtra("address_model", data.get(position));
            context.startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Object obj = compoundButton.getTag();
        if (obj != null && obj instanceof Integer) {
            if (b) {
                for (ShopAddress sa : data) {
                    sa.setIs_default("0");
                }
                data.get((Integer) (obj)).setIs_default("1");
                modifyDefaultAddress(LoginManager.getInstance().getUserID(context),data.get((Integer) (obj)).getAddress_id());
            } else {
                data.get((Integer) obj).setIs_default("0");
            }
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 删除地址
     */
    public void deleteAddress(String addressId) {
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        if (TextUtils.isEmpty(addressId)) {
            return;
        }
        final ProgressDialog progressDialog
                = DialogFactory.getDefualtProgressDialog((Activity) context, DELETING);
        progressDialog.show();
        Api.deleteAddress(addressId, LoginManager.getInstance().getUserID(context)
                , new ApiResponseHandler(context) {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if (apiResponse.getCode() == Api.SUCCESS) {
                            if (tellActivity != null) {
                                tellActivity.presenterTakeAction(CHANGE_ADDRESS);
                            }
                        } else {
                            ToastUtils.showShort(context, apiResponse.getMessage());
                        }
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    /**
     * 修改默认地址
     */
    public void modifyDefaultAddress(String user_id, String address_id) {
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        if (TextUtils.isEmpty(address_id)) {
            return;
        }
        final ProgressDialog progressDialog
                = DialogFactory.getDefualtProgressDialog((Activity) context, CHANGING);
        progressDialog.show();
        Api.modifyDefaultAddress(user_id, address_id, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                    if (tellActivity != null) {
                        tellActivity.presenterTakeAction(CHANGE_ADDRESS);
                    }
                } else {
                    ToastUtils.showShort(context, apiResponse.getMessage());
                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    /**
     * 创建新地址
     *
     * @param receive  收货人
     * @param phone    电话
     * @param province 省
     * @param city     市
     * @param district 区
     * @param detail   详细地址
     */
    public void saveAddress(String receive, String phone, String province,
                            String city, String district, String detail) {
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        if (TextUtils.isEmpty(receive)) {
            ToastUtils.showShort(context, getString(R.string.mine_address_error1));
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(context, getString(R.string.mine_address_error2));
            return;
        }
        if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city) || TextUtils.isEmpty(district)) {
            ToastUtils.showShort(context, getString(R.string.mine_address_error3));
            return;
        }
        if (TextUtils.isEmpty(detail)) {
            ToastUtils.showShort(context, getString(R.string.mine_address_error4));
            return;
        }
        final ProgressDialog progressDialog
                = DialogFactory.getDefualtProgressDialog((Activity) context, ADDING);
        progressDialog.show();
        Api.editAddress(LoginManager.getInstance().getUserID(context),
                receive, phone, province, city, district, detail, null,
                new ApiResponseHandler(context) {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if (apiResponse.getCode() == Api.SUCCESS) {
                            if (tellActivity != null) {
                                tellActivity.presenterTakeAction(CHANGE_ADDRESS);
                            }
                        } else {
                            ToastUtils.showShort(context, apiResponse.getMessage());
                        }
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    /**
     * 修改地址
     *
     * @param address_id 地址id
     * @param receive    接收者
     * @param phone      电话
     * @param province   省
     * @param city       市
     * @param district   区
     * @param address    详细地址
     */
    public void changeAddress(String address_id, String receive
            , String phone, String province, String city, String district, String address) {
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        if (TextUtils.isEmpty(address_id)) {
            return;
        }
        if (TextUtils.isEmpty(receive)) {
            ToastUtils.showShort(context, getString(R.string.mine_address_error1));
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(context, getString(R.string.mine_address_error2));
            return;
        }
        if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city) || TextUtils.isEmpty(district)) {
            ToastUtils.showShort(context, getString(R.string.mine_address_error3));
            return;
        }
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showShort(context, getString(R.string.mine_address_error4));
            return;
        }
        final ProgressDialog progressDialog
                = DialogFactory.getDefualtProgressDialog((Activity) context, CHANGING);
        progressDialog.show();
        Api.changeAddress(LoginManager.getInstance().getUserID(context), address_id,
                receive, phone, province, city, district, address, null,
                new ApiResponseHandler(context) {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if (apiResponse.getCode() == Api.SUCCESS) {
                            if (tellActivity != null) {
                                tellActivity.presenterTakeAction(CHANGE_ADDRESS);
                            }
                        } else {
                            ToastUtils.showShort(context, apiResponse.getMessage());
                        }
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
    }

}

package com.example.project.limolive.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.ProductSortAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.live.AllcatgoryBean;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：黄亚菲 on 2017/2/21 16:41
 * 功能：
 */
public class ProductSortActivity extends BaseActivity implements View.OnClickListener{


    //返回
    private ImageView left_arrow;

    private ProductSortAdapter adapter;
    private ListView list_product_sort;

    private List<AllcatgoryBean> listAll;

    private int PRODUCT_SORT_RESULT_CODE = 20202;

    public static String productsort,productsortid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sort);
        initView();
        monitor();
    }


    private void initView(){
        left_arrow = (ImageView) findViewById(R.id.left_arrow);
        list_product_sort = (ListView) findViewById(R.id.list_product_sort);

        listAll = new ArrayList<>();
        getSort();

        list_product_sort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productsort = listAll.get(i).getType_name();
                productsortid = listAll.get(i).getId();
//                Log.e("选取内容",listAll.get(i).getType_name()+"id:" + listAll.get(i).getId());
                setResult(RESULT_OK);
                finish();


            }
        });
    }

    private void monitor(){
        left_arrow.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_arrow:
                finish();
                break;
        }
    }

    /******
     * 商品分类
     */
    private void getSort(){

        if (NetWorkUtil.isNetworkConnected(this)){
            Api.getGoodsType(new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    List<AllcatgoryBean> list = JSON.parseArray(apiResponse.getData(),AllcatgoryBean.class);
                    listAll.clear();
                    listAll.addAll(list);
                    if (listAll != null){
                        adapter = new ProductSortAdapter(listAll,ProductSortActivity.this);
                        list_product_sort.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }
            });
        }else {
            ToastUtils.showShort(this,"网络异常，请检查您的网络~");
        }


    }



}

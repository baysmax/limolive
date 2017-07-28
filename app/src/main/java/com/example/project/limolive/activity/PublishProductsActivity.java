package com.example.project.limolive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project.limolive.R;

/**
 * @author 黄亚菲 on 2017/1/6.
 * 我的店铺--->我的 ---->发布宝贝
 */

public class PublishProductsActivity extends BaseActivity implements View.OnClickListener {

    //发布宝贝fragment
    private PublishProductsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish_product);
        initView();
    }

    private  void initView(){
        loadTitle();
        fragment = new PublishProductsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment).commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    /**
     * 设置顶部标题栏颜色属性
     */
    private void loadTitle() {
        setTitleString(getString(R.string.publish_product_text));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /***
     * 商品圆形头像
     * @param view
     * @param avatar
     */
//    private void showHeadIcon(ImageView view, String avatar) {
//        if (TextUtils.isEmpty(avatar)) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
//            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
//            view.setImageBitmap(cirBitMap);
//        } else {
////            SxbLog.d(TAG, "load icon: " + avatar);
//            RequestManager req = Glide.with(this);
//            req.load(ApiHttpClient.API_PIC + avatar).transform(new GlideCircleTransform(this)).into(view);
//        }
//    }

}

package com.example.project.limolive.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.ProductDescriptionActivity;
import com.example.project.limolive.helper.DialogFactory;
import com.example.project.limolive.helper.TakePhotoHelper;
import com.example.project.limolive.widget.ChangeDialog;

/**
 * 作者：黄亚菲 on 2017/1/10 11:55
 * 功能：发布宝贝fragment
 */
public class PublishProductsFragment extends BaseFragment implements View.OnClickListener{


    //上传商品头像
    private ImageView im_product_head;
    //拍照工具
    private TakePhotoHelper takePhotoHelper;

    //拍照 选择相册 弹出框
    private ChangeDialog dialog;


    private Intent intent;
    //宝贝描述
    private RelativeLayout rl_des;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_publish_product,inflater,container);
    }


    @Override
    protected void initView() {
        super.initView();
        takePhotoHelper = new TakePhotoHelper(getActivity());
        im_product_head = (ImageView) findViewById(R.id.im_product_head);
        rl_des = (RelativeLayout) findViewById(R.id.rl_des);
        initEvent();

    }


    private void initEvent(){
        rl_des.setOnClickListener(this);
        im_product_head.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.im_product_head:
                changeUserHead();
                break;
            //宝贝描述
            case R.id.rl_des:
                intent = new Intent(getActivity(),ProductDescriptionActivity.class);
                startActivity(intent);

        }

    }

    private void changeUserHead() {
        dialog = DialogFactory.getDefaultSelectDialog(getActivity());
        dialog.show();
        dialog.setCheckedFirst(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoHelper.takePhotosFromFragment(PublishProductsFragment.this);
            }
        });
        dialog.setCheckedSecond(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoHelper.openSystemAlbumFromFragment(PublishProductsFragment.this);
            }
        });
        dialog.setCheckedThird(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){

            case TakePhotoHelper.REQUEST_IMAGE_CAPTURE: //拍照
                takePhotoHelper.handleSavedImage(this);
                break;
            case TakePhotoHelper.REQUEST_ALBUM_IMAGE: //相册
                if (data == null || data.getData() == null) {
                    return;
                }
                takePhotoHelper.startPhotoZoom(data.getData(),this); //裁剪
                break;
            case TakePhotoHelper.REQUEST_CUTTING: //裁剪之后
                if(data==null){
                    return;
                }
                Bundle bundle=data.getExtras();
                if(bundle!=null){
                    Bitmap photo = bundle.getParcelable("data");
                    im_product_head.setImageBitmap(photo);
//                    showHeadIcon(im_product_head,photo);
                    if(dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
                break;
        }

    }

    /***
     * 商品圆形头像
     * @param view
     * @param avatar
     */
//    private void showHeadIcon(ImageView view, Bitmap avatar) {
//        if (avatar != null) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), avatar);
//            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
//            view.setImageBitmap(cirBitMap);
//        } else {
////            SxbLog.d(TAG, "load icon: " + avatar);
//            RequestManager req = Glide.with(this);
//            req.load(ApiHttpClient.API_PIC + avatar).transform(new GlideCircleTransform(getApplication())).into(view);
//        }
//    }



}

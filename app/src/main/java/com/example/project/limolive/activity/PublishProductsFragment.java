package com.example.project.limolive.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.GoodsStandard;
import com.example.project.limolive.fragment.BaseFragment;
import com.example.project.limolive.helper.DialogFactory;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.helper.TakePhotoHelper;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.view.CustomProgressDialog;
import com.example.project.limolive.view.RoundImageView;
import com.example.project.limolive.widget.ChangeDialog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：黄亚菲 on 2017/1/10 11:55
 * 功能：发布宝贝fragment
 */
public class PublishProductsFragment extends BaseFragment implements View.OnClickListener{
    private CustomProgressDialog  mProgressDialog;

    //上传商品头像
    private RoundImageView im_product_head;
    //拍照工具
    private TakePhotoHelper takePhotoHelper;

    //拍照 选择相册 弹出框
    private ChangeDialog dialog;


    private Intent intent;
    //宝贝描述
    private RelativeLayout rl_des,rl_sort;

    //发布
    private Button button;

//    private int PRODUCT_SORT_REQUEST_CODE = 10101;
//    private int PRODUCT_SORT_RESULT_CODE = 20202;

//    private List<File> headFileList;

    //商品名称 价格 库存
    private EditText et_product_title,et_product_price,et_product_storge;

    //商品描述图片
    private List<File> listpics;
    private File headsmall;
    private RelativeLayout rl_size;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_publish_product,inflater,container);
    }


    @Override
    protected void initView() {
        super.initView();
        takePhotoHelper = new TakePhotoHelper(getActivity());
        im_product_head = (RoundImageView) findViewById(R.id.im_product_head);
        rl_des = (RelativeLayout) findViewById(R.id.rl_des);
        rl_sort = (RelativeLayout) findViewById(R.id.rl_sort);
        rl_size = (RelativeLayout) findViewById(R.id.rl_size);
        rl_size.setOnClickListener(this);
        button = (Button) findViewById(R.id.button);
        et_product_title = (EditText) findViewById(R.id.et_product_title);
        et_product_price = (EditText) findViewById(R.id.et_product_price);
        et_product_storge = (EditText) findViewById(R.id.et_product_storge);
        initEvent();

    }


    private void initEvent(){
        rl_des.setOnClickListener(this);
        rl_sort.setOnClickListener(this);
        im_product_head.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.im_product_head:
                changeUserHead();
                break;
            case R.id.rl_size:
                setSize();
                break;
            //宝贝描述
            case  R.id.rl_des:
                intent = new Intent(getActivity(),ProductDescriptionActivity.class);
                startActivity(intent);
                break;
            //商品分类
            case R.id.rl_sort:
                intent = new Intent(getActivity(),ProductSortActivity.class);
                startActivity(intent);
                break;
            //发布
            case R.id.button:
//                if (null == headFileList || headFileList.size() == 0){
//                    ToastUtils.showShort(getActivity(),"请选择商品头像");
//                    return;
//                }
                if (TextUtils.isEmpty(et_product_title.getText().toString())){
                    ToastUtils.showShort(getActivity(),"请输入商品名称");
                    return;
                }
                if (TextUtils.isEmpty(ProductSortActivity.productsort) || TextUtils.isEmpty(ProductSortActivity.productsortid)){
                    ToastUtils.showShort(getActivity(),"请选择商品分类");
                    return;
                }
                if (TextUtils.isEmpty(et_product_price.getText().toString())){
                    ToastUtils.showShort(getActivity(),"请输入您的商品价格");
                    return;
                }
                if (TextUtils.isEmpty(et_product_storge.getText().toString())){
                    ToastUtils.showShort(getActivity(),"请输入商品现有库存");
                    return;
                }
                if (sizeList.size()==0){
                    ToastUtils.showShort(getActivity(),"请输入商品规格");
                    return;
                }

//                Log.e("picString",ProductDescriptionActivity.picString.size()+"");

                if (null != ProductDescriptionActivity.picString && ProductDescriptionActivity.picString.size() != 0 ){
                    listpics = new ArrayList<>();

//                Log.e("商品描述goods_content",ProductDescriptionActivity.picString.size()+"");
//                Log.e("商品描述内容goods_remark",ProductDescriptionActivity.des);
                    for (int i=0;i<ProductDescriptionActivity.picString.size();i++){
                        File file = new File(ProductDescriptionActivity.picString.get(i));
                        listpics.add(file);
                    }

//                    Log.e("listpics",listpics.size()+"");

                }

//                Log.e("uid", LoginManager.getInstance().getUserID(getActivity()));
//                Log.e("商品分类",ProductSortActivity.productsort + "id--->"+ ProductSortActivity.productsortid);
//                Log.e("商品头像",headFileList.size()+"");
//                Log.e("商品名称",et_product_title.getText().toString());
//                Log.e("商品价格",et_product_price.getText().toString());
//                Log.e("商品库存",et_product_storge.getText().toString());

                showProgressDialog("正在加载..");
                publishProduct(LoginManager.getInstance().getUserID(getActivity()),
                        ProductSortActivity.productsortid,headsmall,
                        et_product_title.getText().toString(),
                        et_product_price.getText().toString(),
                        et_product_storge.getText().toString(),
                        ProductDescriptionActivity.des,listpics,sizeList);
                break;




        }

    }
    private EditText et_size,et_num;
    private Button btn_fig,btn_save;
    private Dialog dialogs;
    private List<GoodsStandard> sizeList=new ArrayList();
    private void setSize() {
        View view = View.inflate(getContext(), R.layout.dialogs, null);
        et_size = view.findViewById(R.id.et_size);
        et_num = view.findViewById(R.id.et_num);
        btn_save = view.findViewById(R.id.btn_save);
        btn_fig = view.findViewById(R.id.btn_fig);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("请输入商品规格信息")
                .setView(view);
        dialogs = builder.show();
        btn_fig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String size = et_size.getText().toString();
                String num = et_num.getText().toString();
                if (TextUtils.isEmpty(size)){
                    ToastUtils.showShort(getActivity(),"商品规格不能为空");
                    return;
                }
                if (TextUtils.isEmpty(num)){
                    ToastUtils.showShort(getActivity(),"商品数量不能为空");
                    return;
                }
                sizeList.add(new GoodsStandard(size,num));
                ToastUtils.showShort(getActivity(),size+":"+num);
                et_size.setText("");
                et_num.setText("");
                et_num.setHint("");
                et_size.setHint("");
            }
        });
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialog.dismiss();
//                    }
//                }


    }

    /********
     * 商品发布按钮
     * String uid, String cat_id, List<File> headsmall,
     int goods_name, int shop_price, int store_count,
     String goods_remark, List<File> goods_content
     */
    private void publishProduct(String uid, String cat_id, File headsmall,
                                String goods_name, String shop_price, String store_count,
                                String goods_remark, List<File> goods_content,List<GoodsStandard> sizeList){
        if (NetWorkUtil.isNetworkConnected(getActivity())){

            Api.sendProducts(uid, cat_id, headsmall, goods_name, shop_price, store_count, goods_remark, goods_content,sizeList, new ApiResponseHandler(getActivity()) {

                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    Log.i("店铺","apiResponse"+apiResponse.toString());
                    if (apiResponse.getCode() == 0){

                        hideProgressDialog();
                        Toast.makeText(getActivity(),"发布成功！",Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                }
                @Override
                public void onFailure(String errMessage) {
                    super.onFailure(errMessage);
                    ToastUtils.showShort(getActivity(),errMessage);
                    hideProgressDialog();
                }
            });

        }else {
            ToastUtils.showShort(getActivity(),"网络异常，请检查您的链接~");
        }


    }


    public void showProgressDialog(String msg) {
        mProgressDialog = new CustomProgressDialog(getActivity());
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }



    private void changeUserHead() {
        dialog = DialogFactory.getDefaultSelectDialog(getActivity());
        dialog.show();
        dialog.setCheckedFirst(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoHelper.takePhotosFromFragment(PublishProductsFragment.this);            }
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
                    try {
                        saveFile(photo,"headsmalls.jpg");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    if(dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
                break;
        }

    }


    /**
     * 保存文件
     * @param bm
     * @param fileName
     */
    public void saveFile(Bitmap bm, String fileName) throws IOException {
        String path = Environment.getExternalStorageDirectory() +"/revoeye/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();

        headsmall = new File(path+fileName);
    }




}

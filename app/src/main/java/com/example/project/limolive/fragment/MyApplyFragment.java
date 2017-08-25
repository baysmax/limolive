package com.example.project.limolive.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.*;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.DialogFactory;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.helper.TakePhotoHelper;
import com.example.project.limolive.localalbum.common.ImageUtils;
import com.example.project.limolive.localalbum.common.LocalImageHelper;
import com.example.project.limolive.localalbum.ui.LocalAlbum;
import com.example.project.limolive.localalbum.widget.FilterImageView;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.widget.ChangeDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAA on 2017/8/25.
 */

public class MyApplyFragment extends BaseFragment implements View.OnClickListener{
    private LinearLayout picContainer;
    private ImageView add;
    private HorizontalScrollView scrollView;
    private int padding;
    private int size;
    private List<LocalImageHelper.LocalFile> pictures = new ArrayList<>();
    public  List<String> picString = new ArrayList<>();
    private DisplayImageOptions options;
    private Button btn_saves;
    private List<File> listpics;
    //拍照工具
    private TakePhotoHelper takePhotoHelper;

    //拍照 选择相册 弹出框
    private ChangeDialog dialog;
    private ImageView im_product_head;
    private File headsmall;
    private EditText idcard,real_name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.activity_my_apply,inflater,container);
    }

    @Override
    protected void initView() {
        super.initView();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(false)
                .showImageForEmptyUri(R.drawable.dangkr_no_picture_small)
                .showImageOnFail(R.drawable.dangkr_no_picture_small)
                .showImageOnLoading(R.drawable.dangkr_no_picture_small)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
        initViews();
        initData();
    }

    private void initViews() {
        picContainer= (LinearLayout) findViewById(R.id.post_pic_container);
        scrollView= (HorizontalScrollView) findViewById(R.id.post_scrollview);
        takePhotoHelper = new TakePhotoHelper(getActivity());
        im_product_head= (ImageView) findViewById(R.id.im_product_head);
        im_product_head.setOnClickListener(this);
        btn_saves= (Button) findViewById(R.id.btn_saves);
        btn_saves.setOnClickListener(this);
        add= (ImageView) findViewById(R.id.post_add_pic);
        add.setOnClickListener(this);
        idcard= (EditText) findViewById(R.id.idcard);
        real_name= (EditText) findViewById(R.id.real_name);
    }

    private void initData() {
        size = (int) getResources().getDimension(R.dimen.size_100);
        padding = (int) getResources().getDimension(R.dimen.padding_10);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                if (LocalImageHelper.getInstance().isResultOk()) {
                    LocalImageHelper.getInstance().setResultOk(false);
                    //获取选中的图片
                    List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                    for (int i = 0; i < files.size(); i++) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
                        params.rightMargin = padding;
                        FilterImageView imageView = new FilterImageView(getActivity());
                        imageView.setLayoutParams(params);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ImageLoader.getInstance().displayImage(files.get(i).getThumbnailUri(), new ImageViewAware(imageView), options,null, null, files.get(i).getOrientation());
                        imageView.setOnClickListener(this);
                        pictures.add(files.get(i));
                        picString.add(files.get(i).getFilePath());

                        Log.e("pictures.size()","图片地址:"+pictures.get(i).getFilePath());

                        if (pictures.size() == 9) {
                            add.setVisibility(View.GONE);
                        } else {
                            add.setVisibility(View.VISIBLE);
                        }
                        picContainer.addView(imageView, picContainer.getChildCount() - 1);
//                      picRemain.setText(pictures.size() + "/9");
                        LocalImageHelper.getInstance().setCurrentSize(pictures.size());
                    }
                    //清空选中的图片
                    files.clear();
                    //设置当前选中的图片数量
                    LocalImageHelper.getInstance().setCurrentSize(pictures.size());
                    //延迟滑动至最右边
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                        }
                    }, 50L);
                }
                //清空选中的图片
                LocalImageHelper.getInstance().getCheckedItems().clear();
                break;
            default:
                break;
        }
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
                        saveFile(photo,"headsmall.jpg");
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.post_add_pic:
                requestPermission();
                Intent intent = new Intent(getActivity(), LocalAlbum.class);
                startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
                break;
            case R.id.btn_saves:
                save();
                break;
            case R.id.im_product_head:
                changeUserHead();
                break;

        }
    }
    private void changeUserHead() {
        dialog = DialogFactory.getDefaultSelectDialog(getActivity());
        dialog.show();
        dialog.setCheckedFirst(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoHelper.takePhotosFromFragment(MyApplyFragment.this);            }
        });
        dialog.setCheckedSecond(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoHelper.openSystemAlbumFromFragment(MyApplyFragment.this);
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

    private void save() {
        if (!NetWorkUtil.isNetworkConnected(getActivity())) {
            ToastUtils.showShort(getActivity(), "请检查网络!");
            return;
        }
        listpics = new ArrayList<>();
        for (int i=0;i<picString.size();i++){
            File file = new File(picString.get(i));
            listpics.add(file);
        }
        if (TextUtils.isEmpty(real_name.getText().toString())){
            ToastUtils.showShort(getActivity(),"请输入店铺名称");
            return;
        }
        if (TextUtils.isEmpty(idcard.getText().toString())){
            ToastUtils.showShort(getActivity(),"请输入身份证号码");
            return;
        }
        if (idcard.getText().toString().length()!=18){
            ToastUtils.showShort(getActivity(),"请输入正确的身份证号码");
            return;
        }
//        if (listpics.size()<2){
//            ToastUtils.showShort(getActivity(),"请选择两张照片");
//            return;
//        }
        for (int i = 0; i < listpics.size(); i++) {
            Log.i("店铺",listpics.get(i).toString());
        }
        Api.apply_order(LoginManager.getInstance().getUserID(getActivity())
                , real_name.getText().toString().trim()
                , idcard.getText().toString().trim()
                , listpics
                ,headsmall, new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("店铺","apiResponse"+apiResponse.toString());
                if (apiResponse.getCode()==Api.SUCCESS){
                    ToastUtils.showShort(getActivity(),"申请成功");
                    getActivity().finish();
                }else {
                    ToastUtils.showShort(getActivity(),"申请失败");
                }
            }
        });
    }
    public void requestPermission() {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(getActivity(), "please give me the permission", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            }
        } else {
            LocalImageHelper.init(getActivity());
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

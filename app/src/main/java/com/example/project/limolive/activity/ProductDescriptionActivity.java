package com.example.project.limolive.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.localalbum.common.ImageUtils;
import com.example.project.limolive.localalbum.common.LocalImageHelper;
import com.example.project.limolive.localalbum.ui.LocalAlbum;
import com.example.project.limolive.localalbum.widget.AlbumViewPager;
import com.example.project.limolive.localalbum.widget.FilterImageView;
import com.example.project.limolive.localalbum.widget.MatrixImageView;
import com.example.project.limolive.utils.ToastUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄亚菲 on 2017/1/6.
 * 宝贝描述
 */

public class ProductDescriptionActivity extends BaseActivity implements View.OnClickListener, MatrixImageView.OnSingleTapListener{

    private EditText mContent;//动态内容编辑框
    private InputMethodManager imm;//软键盘管理
    private ImageView add;//添加图片按钮
    private LinearLayout picContainer;//图片容器
    private List<LocalImageHelper.LocalFile> pictures = new ArrayList<>();//图片路径数组

    //描述图片
    public static List<String> picString = new ArrayList<>();
    public static String des;//描述
    HorizontalScrollView scrollView;//滚动的图片容器
    View editContainer;//动态编辑部分
    View pagerContainer;//图片显示部分

    //显示大图的viewpager 集成到了Actvity中 下面是和viewpager相关的控件
    AlbumViewPager viewpager;//大图显示pager
    ImageView mBackView;//返回/关闭大图
    TextView mCountView;//大图数量提示
    View mHeaderBar;//大图顶部栏
    ImageView delete;//删除按钮

    int size;//小图大小
    int padding;//小图间距
    DisplayImageOptions options;


    //返回按钮
    private ImageView left_arrow;
    private Button btn_complete;

    private EditText service_rank,deliver_rank,courier_rank,goods_rank;
    String type="";
    String order_id="";
    private View post_scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        type = getIntent().getStringExtra("type");
        order_id = getIntent().getStringExtra("order_id");
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //设置ImageLoader参数
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


    /**
     * @Description： 初始化Views
     */
    private void initViews() {
        // TODO Auto-generated method stub
        loadTitle();
        mContent = (EditText) findViewById(R.id.post_content);
        add = (ImageView) findViewById(R.id.post_add_pic);
        picContainer = (LinearLayout) findViewById(R.id.post_pic_container);
        scrollView = (HorizontalScrollView) findViewById(R.id.post_scrollview);
        viewpager = (AlbumViewPager) findViewById(R.id.albumviewpager);
        mBackView = (ImageView) findViewById(R.id.header_bar_photo_back);
        mCountView = (TextView) findViewById(R.id.header_bar_photo_count);
        mHeaderBar = findViewById(R.id.album_item_header_bar);
        delete = (ImageView) findViewById(R.id.header_bar_photo_delete);
        editContainer = findViewById(R.id.post_edit_container);
        service_rank = (EditText) findViewById(R.id.service_rank);
        deliver_rank = (EditText) findViewById(R.id.deliver_rank);
        courier_rank = (EditText) findViewById(R.id.courier_rank);
        goods_rank = (EditText) findViewById(R.id.goods_rank);
        post_scrollview=findViewById(R.id.post_scrollview);

        delete.setVisibility(View.VISIBLE);

        btn_complete = (Button) findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(this);


        left_arrow = (ImageView) findViewById(R.id.left_arrow);
        left_arrow.setOnClickListener(this);

        viewpager.setOnPageChangeListener(pageChangeListener);
        viewpager.setOnSingleTapListener(this);
        mBackView.setOnClickListener(this);
        mCountView.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);

//        mContent.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//                                          int arg3) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable content) {
//            }
//        });
        if ("0".equals(type)){
            post_scrollview.setVisibility(View.GONE);
        }else {
            service_rank.setVisibility(View.GONE);
            deliver_rank.setVisibility(View.GONE);
            courier_rank.setVisibility(View.GONE);
            goods_rank.setVisibility(View.GONE);
        }


    }

    private void initData() {
        size = (int) getResources().getDimension(R.dimen.size_100);
        padding = (int) getResources().getDimension(R.dimen.padding_10);
    }

    @Override
    public void onBackPressed() {
        if (pagerContainer.getVisibility() != View.VISIBLE) {
            //showSaveDialog();
        } else {
            hideViewPager();
        }
    }



    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.header_bar_photo_back:
            case R.id.header_bar_photo_count:
                hideViewPager();
                break;
            case R.id.header_bar_photo_delete:
                final int index = viewpager.getCurrentItem();
                break;
            case R.id.post_add_pic:
                requestPermission();
                Intent intent = new Intent(ProductDescriptionActivity.this, LocalAlbum.class);
                startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
                break;

            //返回键
            case R.id.left_arrow:
                if (!"".equals(mContent.getText())){
                    des = mContent.getText().toString();

                }
                finish();
                break;
            //完成按钮
            case R.id.btn_complete:
                if (!"".equals(mContent.getText())){
                    des = mContent.getText().toString();
                }
                if ("0".equals(type)){
                    if (TextUtils.isEmpty(service_rank.getText().toString())){
                        ToastUtils.showShort(this,"请输入商家评价数字 0-5");
                        return;
                    }
                    if (TextUtils.isEmpty(deliver_rank.getText().toString())){
                        ToastUtils.showShort(this,"请输入物流评价数字 0-5");
                        return;
                    }
                    if (TextUtils.isEmpty(courier_rank.getText().toString())){
                        ToastUtils.showShort(this,"请输入快递员评价数字 0-5");
                        return;
                    }
                    if (TextUtils.isEmpty(goods_rank.getText().toString())){
                        ToastUtils.showShort(this,"请输入商品评价数字 0-5");
                        return;
                    }
                    if (TextUtils.isEmpty(mContent.getText().toString())){
                        ToastUtils.showShort(this,"请输入评论");
                        return;
                    }
                    Api.add_comment(LoginManager.getInstance().getUserID(this), order_id
                            , service_rank.getText().toString()
                            , deliver_rank.getText().toString()
                            , courier_rank.getText().toString()
                            , goods_rank.getText().toString()
                            , mContent.getText().toString(),
                            new ApiResponseHandler(this) {
                                @Override
                                public void onSuccess(ApiResponse apiResponse) {
                                    if (apiResponse.getCode()==Api.SUCCESS){
                                        ToastUtils.showShort(ProductDescriptionActivity.this,"完成评论");
                                        finish();
                                    }

                                }
                            });
                }

                break;

            default:
                break;
        }
    }


    public void requestPermission() {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            }
        } else {
            LocalImageHelper.init(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10: {
                // 如果请求被拒绝，那么通常grantResults数组为空
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //申请成功，进行相应操作
//                    createFile("hello.txt");
                    LocalImageHelper.init(ProductDescriptionActivity.this);
                    Intent intent = new Intent(ProductDescriptionActivity.this, LocalAlbum.class);
                    startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
                } else {
                    //申请失败，可以继续向用户解释。
                }
                return;
            }
        }
    }





    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (viewpager.getAdapter() != null) {
                String text = (position + 1) + "/" + viewpager.getAdapter().getCount();
                mCountView.setText(text);
            } else {
                mCountView.setText("0/0");
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    };

    //显示大图pager
    private void showViewPager(int index) {
        pagerContainer.setVisibility(View.VISIBLE);
        editContainer.setVisibility(View.GONE);
        viewpager.setAdapter(viewpager.new LocalViewPagerAdapter(pictures));
        viewpager.setCurrentItem(index);
        mCountView.setText((index + 1) + "/" + pictures.size());
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation((float) 0.9, 1, (float) 0.9, 1, pagerContainer.getWidth() / 2, pagerContainer.getHeight() / 2);
        scaleAnimation.setDuration(200);
        set.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0.1, 1);
        alphaAnimation.setDuration(200);
        set.addAnimation(alphaAnimation);
        pagerContainer.startAnimation(set);
    }

    //关闭大图显示
    private void hideViewPager() {
        pagerContainer.setVisibility(View.GONE);
        editContainer.setVisibility(View.VISIBLE);
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, (float) 0.9, 1, (float) 0.9, pagerContainer.getWidth() / 2, pagerContainer.getHeight() / 2);
        scaleAnimation.setDuration(200);
        set.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(200);
        set.addAnimation(alphaAnimation);
        pagerContainer.startAnimation(set);
    }

    @Override
    public void onSingleTap() {
        hideViewPager();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                if (LocalImageHelper.getInstance().isResultOk()) {
                    LocalImageHelper.getInstance().setResultOk(false);
                    //获取选中的图片
                    List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                    for (int i = 0; i < files.size(); i++) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
                        params.rightMargin = padding;
                        FilterImageView imageView = new FilterImageView(this);
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
    }








    /**
     * 设置顶部标题栏颜色属性
     */
    private void loadTitle() {
        if ("0".equals(type)){
            setTitleString("商品评论");
        }else {
            setTitleString(getString(R.string.publish_product_detail));
        }
        setLeftImage(R.mipmap.icon_return);
        setRightImage(R.mipmap.fenlei);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}

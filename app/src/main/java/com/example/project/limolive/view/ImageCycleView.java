package com.example.project.limolive.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.utils.ImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告图片自动轮播控件</br>
 * <p/>
 * <pre>
 *   集合ViewPager和指示器的一个轮播控件，主要用于一般常见的广告图片轮播，具有自动轮播和手动轮播功能
 *   使用：只需在xml文件中使用{@code <com.minking.imagecycleview.ImageCycleView/>} ，
 *   然后在页面中调用  {setImageResources(ArrayList) }即可!
 *
 *   另外提供{@link #startImageCycle() } \ {@link #pushImageCycle() }两种方法，用于在Activity不可见之时节省资源；
 *   因为自动轮播需要进行控制，有利于内存管理
 * </pre>
 *
 * @author minking
 */
public class ImageCycleView extends LinearLayout {

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 图片轮播视图
     */
    private ViewPager mAdvPager = null;

    /**
     * 滚动图片视图适配器
     */
    private ImageCycleAdapter mAdvAdapter;

    /**
     * 图片轮播指示器控件
     */
    private ViewGroup mGroup;

    /**
     * 图片轮播指示器-个图
     */
    private SimpleDraweeView mImageView = null;

    /**
     * 滚动图片指示器-视图列表
     */
    private SimpleDraweeView[] mImageViews = null;

    /**
     * 图片滚动当前图片下标
     */
    private int mImageIndex = 0;

    /**
     * 手机密度
     */
    private float mScale;

    /**
     * @param context
     */
    public ImageCycleView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public ImageCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScale = context.getResources().getDisplayMetrics().density;
        LayoutInflater.from(context).inflate(R.layout.ad_cycle_view, this);
        mAdvPager = (ViewPager) findViewById(R.id.adv_pager);
        mAdvPager.setOnPageChangeListener(new GuidePageChangeListener());
        mAdvPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 开始图片滚动
                        startImageTimerTask();
                        break;
                    default:
                        // 停止图片滚动
                        stopImageTimerTask();
                        break;
                }
                return false;
            }
        });
        // 滚动图片右下指示器视图
        mGroup = (ViewGroup) findViewById(R.id.viewGroup);
    }

    /**
     * 加载图片资源
     *
     * @param imageUrlList //   * @param imageCycleViewListener
     *                     \ImageCycleViewListener imageCycleViewListener
     */
    public void setImageResources(List<String> imageUrlList) {
        // 清除所有子视图
        mGroup.removeAllViews();
        // 图片广告数量
        final int imageCount = imageUrlList.size();
        mImageViews = new SimpleDraweeView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageView = new SimpleDraweeView(mContext);
            int imageParams = (int) (mScale * 5);// XP涓嶥P杞崲锛岄?搴斾笉鍚屽垎杈ㄧ巼
            int imagePadding = (int) (mScale * 5 + 0.5f);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageParams, imageParams);
            params.setMargins(0, 0, 10, 0);
            mImageView.setLayoutParams(params);
            mImageView.setPadding(imagePadding, imagePadding, imagePadding,
                    imagePadding);
            mImageViews[i] = mImageView;
            if (i == 0) {
                mImageViews[i]
                        .setBackgroundResource(R.mipmap.play_display);
            } else {
                mImageViews[i]
                        .setBackgroundResource(R.mipmap.play_hide);
            }
            mGroup.addView(mImageViews[i]);
        }
//        ,imageCycleViewListener
        mAdvAdapter = new ImageCycleAdapter(mContext, imageUrlList);
        mAdvPager.setAdapter(mAdvAdapter);
        startImageTimerTask();
    }

    /**
     * 开始轮播(手动控制自动轮播与否，便于资源控制)
     */
    public void startImageCycle() {
        startImageTimerTask();
    }

    /**
     * 暂停轮播——用于节省资源
     */
    public void pushImageCycle() {
        stopImageTimerTask();
    }

    /**
     * 开始图片滚动任务
     */
    private void startImageTimerTask() {
        stopImageTimerTask();
        mHandler.postDelayed(mImageTimerTask, 3000);
    }

    /**
     * 停止图片滚动任务
     */
    private void stopImageTimerTask() {
        mHandler.removeCallbacks(mImageTimerTask);
    }

    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {

        @Override
        public void run() {
            if (mImageViews != null) {
                // 涓嬫爣绛変簬鍥剧墖鍒楄〃闀垮害璇存槑宸叉粴鍔ㄥ埌鏈?悗涓?紶鍥剧墖,閲嶇疆涓嬫爣
                if ((++mImageIndex) == mImageViews.length) {
                    mImageIndex = 0;
                }
                mAdvPager.setCurrentItem(mImageIndex);
            }
        }
    };

    /**
     * 轮播图片状态监听器
     *
     * @author minking
     */
    private final class GuidePageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE)
                startImageTimerTask(); // 寮?涓嬫璁℃椂
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {
            // 设置当前显示的图片下标
            mImageIndex = index;
            //设置图片滚动指示器背景
            mImageViews[index]
                    .setBackgroundResource(R.mipmap.play_display);
            for (int i = 0; i < mImageViews.length; i++) {
                if (index != i) {
                    mImageViews[i]
                            .setBackgroundResource(R.mipmap.play_hide);
                }
            }

        }

    }

    private class ImageCycleAdapter extends PagerAdapter {

        /**
         * 图片视图缓存列表
         */
        private ArrayList<SimpleDraweeView> mImageViewCacheList;

        /**
         * 图片资源列表
         */
        private List<String> mAdList = new ArrayList<String>();

        /**
         * 广告图片点击监听器
         */
        private ImageCycleViewListener mImageCycleViewListener;

        private Context mContext;

        //        ,
//        ImageCycleViewListener imageCycleViewListener
        public ImageCycleAdapter(Context context, List<String> adList) {
            mContext = context;
            mAdList = adList;
//            mImageCycleViewListener = imageCycleViewListener;
            mImageViewCacheList = new ArrayList<SimpleDraweeView>();
        }

        @Override
        public int getCount() {
            return mAdList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            String imageUrl = mAdList.get(position);
            SimpleDraweeView imageView = null;
            if (mImageViewCacheList.isEmpty()) {
                imageView = new SimpleDraweeView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            } else {
                imageView = mImageViewCacheList.remove(0);
            }
            // 设置图片点击监听
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mImageCycleViewListener.onImageClick(position, v);
                }
            });
            Log.i("12312312312312", imageUrl);
            imageView.setImageURI(ApiHttpClient.API_PIC + imageUrl, ImageUtils.getOptions());
            container.addView(imageView);
//            mImageCycleViewListener.displayImage(imageUrl, imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            SimpleDraweeView view = (SimpleDraweeView) object;
            container.removeView(view);
            mImageViewCacheList.add(view);
        }

    }

    /**
     * 轮播控件的监听事件
     *
     * @author minking
     */
    public static interface ImageCycleViewListener {
        /**
         * 加载图片资源
         *
         * @param imageURL
         * @param imageView
         */
        public void displayImage(String imageURL, SimpleDraweeView imageView);

        /**
         * 单击图片事件
         *
         * @param position
         * @param imageView
         */
        public void onImageClick(int position, View imageView);

    }

}

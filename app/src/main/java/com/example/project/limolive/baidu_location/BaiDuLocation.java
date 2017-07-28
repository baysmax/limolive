package com.example.project.limolive.baidu_location;
/**
 * Created by Administrator on 2017/1/6.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.clusterutil.clustering.Cluster;
import com.baidu.mapapi.clusterutil.clustering.ClusterManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.project.limolive.LiveMallApplication;
import com.example.project.limolive.R;
import com.example.project.limolive.baidu_location.event.IconEvent;
import com.example.project.limolive.baidu_location.event.RequestDataEvent;
import com.example.project.limolive.baidu_location.job.IConJob;
import com.example.project.limolive.baidu_location.job.RequestLogicJob;
import com.example.project.limolive.baidu_location.model.IconModel;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.utils.SPUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.OnClick;

/**
 * BaiDuLocation
 * 作者：李志超 on 2017/1/6 10:52
 */

public class BaiDuLocation extends NewBaseActivity implements BaiduMap.OnMapLoadedCallback {


    /***********
     * mainactivity
     */

    private boolean mHadRequest = false;

    //城市是否改变
    boolean mIsChangeCity;

    //是否移动
    boolean mIsMove;

    //所有的数量
    int mTotalCount;


    private ClusterBaiduItem mPreClickItem;

    private Handler mHandler = new Handler();

    //网络请求的线程
    NetRunnable mNetRunnable;

    //图标的item
    private List<ClusterBaiduItem> mClusterBaiduItems = new ArrayList<>();

    //lbs数据列表
    private List<HomeListBeen> mDataList = new ArrayList<>();

    /***********
     * mainactivity
     */

    private SPUtil sp;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.location_layout);
//        initViews();
//        ButterKnife.bind(this);

//        initDefaultLocation();
//        if (!LiveMallApplication.getApplication().getEventBus().isRegistered(this)) {
//            LiveMallApplication.getApplication().getEventBus().register(this);
//        }
        /***********mainactivity  */
        initListeners();
        showLoadingDialog();
        //初始化请求数据
        RequestNewDataLogic(false, false);
        /***********mainactivity  */
    }


    @Override
    protected void loading() {
        super.loading();
        //点击loading请求数据
        RequestNewDataLogic(false, false);
    }

    protected void initListeners() {

        //地图状态发生变化，主要是移动、放大、经纬度发生变化
        mClusterManager.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            //记住变化前的上一个状态
            private MapStatus mFrontMapStatus;

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                if (mFrontMapStatus == null) {
                    mFrontMapStatus = mapStatus;
                }
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                //此处需要注意，如果是进入的时候重新定位了地址，或者进入后在改变地图状态，可能也会进入这里
                if (mHadRequest) {
                    if (StatusChangeLogic(mFrontMapStatus, mapStatus)) {//处理移动与放大
                        mFrontMapStatus = null;
                    }
                }
                mCurrentMapStatus = mapStatus;
            }
        });

        //将百度的图标点击转为marker的点击
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //通过这里转到mClusterManager
                return mMarkerManager.onMarkerClick(marker);
            }
        });

        //单个的点击
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<ClusterBaiduItem>() {
            @Override
            public boolean onClusterItemClick(ClusterBaiduItem item) {
//                Toast.makeText(BaiDuLocation.this, item.getLBAModel().getTitle(), Toast.LENGTH_SHORT).show();
//                IconClick(item);
                return true;
            }
        });

        //聚合的点击
        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<ClusterBaiduItem>() {
            @Override
            public boolean onClusterClick(Cluster<ClusterBaiduItem> cluster) {
//                Toast.makeText(BaiDuLocation.this, "聚合图标：" + cluster.getSize(), Toast.LENGTH_SHORT).show();
                ClusterOnClick(cluster);
                return true;
            }
        });

    }


    /**
     * 地图因为操作而发生了状态改变
     */
    private boolean StatusChangeLogic(MapStatus frontMapStatus, MapStatus mapStatus) {
        //重新确定搜索半径的中心图标
        mSearchModel.setLatitude(mapStatus.bound.getCenter().latitude);
        mSearchModel.setLongitude(mapStatus.bound.getCenter().longitude);
        mSearchModel.setGps(mapStatus.bound.getCenter().longitude + "," + mapStatus.bound.getCenter().latitude);
        //重新确定层级
        mSearchModel.setLevel(mapStatus.zoom);

        if (frontMapStatus == null)
            return false;

        //得到屏幕的距离大小
        double areaLength1 = DistanceUtil.getDistance(mapStatus.bound.northeast, mapStatus.bound.southwest);

        //计算屏幕的大小半径
        int radius = (int) areaLength1 / 2;

//        重新确定搜索的半径
//        mSearchModel.setRadius(radius);


        if (frontMapStatus.zoom == mapStatus.zoom) {
            if (frontMapStatus.bound == null)
                return false;
            //如果是移动了，得到距离
            double moveLenght = DistanceUtil.getDistance(frontMapStatus.bound.getCenter(), mapStatus.bound.getCenter());
            //如果移动距离大于屏幕的检索半径，请求数据
            if (moveLenght >= radius) {
                RequestNewDataLogic(true, true);
                return true;
            }

            //如果经纬度发生变化了，一般都是切换的城市之类的
            if (mChangeStatus != null && (mapStatus.target.latitude) != (int) (mChangeStatus.target.latitude)
                    && (int) (mapStatus.target.longitude) != (int) (mChangeStatus.target.longitude) && mIsChangeCity) {
                RequestNewDataLogic(true, true);
                mIsChangeCity = false;
                return true;
            }

            return false;
        } else {
            //如果是缩放的话，地图层级发生改变，重新请求数据
            RequestNewDataLogic(true, true);
            return true;
        }
    }

    /**
     * 请求新数据的逻辑
     */
    private void RequestNewDataLogic(final boolean hideFreshBtn, final boolean clearMap) {

        //移动或者缩放之类的产生新的数据请求的时候，需要隐藏掉按键，避免冲突
        if (hideFreshBtn) {
            hideLoading();
        }

        //取消掉原本的请求
        if (mHandler != null && mNetRunnable != null)
            mHandler.removeCallbacks(mNetRunnable);

        //标志位已经移动了
        mIsMove = true;

        mNetRunnable = new NetRunnable(clearMap);
        //等待确定请求逻辑
        mHandler.postDelayed(mNetRunnable, 1300);

    }

    /**
     * 请求数据
     */
    private void netDataLogic(boolean isClearStatus) {
        mHadRequest = true;
        //是否清除掉地图上已经有的
        clearStatus(isClearStatus);
        //请求当前数据是第几页
        requestData(mIndex);
    }

    /**
     * 发起真正的请求
     */
    private void requestData(int pageIndex) {
        LiveMallApplication.getApplication().getJobManager().clear();
        //这个UUID，用于判断当前回来的数据是否为最新请求的数据
        mUUID = UUID.randomUUID().toString();
        //将请求的JOB发布，请求数据，组装数据并返回。
        LiveMallApplication.getApplication().getJobManager().addJob(new RequestLogicJob(mSearchModel, pageIndex, mUUID));

    }

    /**
     * 显示地图ICON marker
     */
    private void showMapData(List<HomeListBeen> dataList, List<ClusterBaiduItem> clusterList, int totalCount) {
        //总数
        mTotalCount = totalCount;
        //lbs数据列表
        mDataList = dataList;
        //组装好的百度item
        mClusterBaiduItems = clusterList;
        //计算有多少页，这里不保险，因为有时候一页不一定是你需要的数量
        int page = (int) Math.ceil(((float) totalCount / LiveMallApplication.PAGE_SIZE)) - 1;
        //最大页数
        mMaxPageSize = (page >= 0) ? page : 0;

        if (mTotalCount == 0 && (mDataList == null || mDataList.size() == 0)) {
            mIndex = 0;
        }
        //清除聚合管理器数据
        mClusterManager.clearItems();
        //重新加入聚合管理器数据
        mClusterManager.addItems(mClusterBaiduItems);

        mBaiduMap.clear();

        //更新状态
        if (mBaiduMap != null && mCurrentMapStatus != null)
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mCurrentMapStatus));
        //更新页面
        if (mClusterManager != null)
            mClusterManager.cluster();

        //下载网络icon
        DownLoadIcons(mClusterBaiduItems);

        //显示刷新按键
        showLoading();

        dismissLoadingDialog();

    }

    /**
     * 下载lbs数据中对应的icon作为marker
     */
    private void DownLoadIcons(List<ClusterBaiduItem> clusterBaiduItems) {
        if (clusterBaiduItems == null || clusterBaiduItems.size() == 0)
            return;
        List<IconModel> logoUrl = new ArrayList<>();
        for (int i = 0; i < clusterBaiduItems.size(); i++) {
            ClusterBaiduItem clusterBaiduItem = clusterBaiduItems.get(i);
            //将所有没有下载的market拿出来
            if (!TextUtils.isEmpty(clusterBaiduItem.getMarkerUrl()) && !new File(clusterBaiduItem.getUrlLocalMarkerIconPath()).exists()) {
                IconModel iconModel = new IconModel();
                iconModel.setUrl(clusterBaiduItem.getMarkerUrl());
                iconModel.setId(Integer.valueOf(clusterBaiduItem.getLBAModel().getHost().getUid()));
                logoUrl.add(iconModel);
            }
        }
        //执行下载图标的job
        if (logoUrl.size() > 0) {
            IConJob iConJob = new IConJob(BaiDuLocation.this, logoUrl);
            LiveMallApplication.getApplication().getJobManager().addJob(iConJob);
        }
    }


    /**
     * 下载的图标发生了改变
     */
    private void ChangeIconLogic(IconEvent e) {
        for (ClusterBaiduItem clusterBaiduItem : mClusterBaiduItems) {
            HomeListBeen lbsModel = clusterBaiduItem.getLBAModel();
            //此处根据id设置对应的图片
            if (Integer.valueOf(lbsModel.getHost().getUid()) == e.geteId()) {
                BitmapDescriptor bitmapDescriptor;
                if (!TextUtils.isEmpty(clusterBaiduItem.getUrlLocalMarkerIconPath()) &&
                        new File(clusterBaiduItem.getUrlLocalMarkerIconPath()).exists()) {
                    bitmapDescriptor = clusterBaiduItem.getUrlMarkerIconBitmapDescriptor(false);
                    if (bitmapDescriptor == null) {
                        bitmapDescriptor = clusterBaiduItem.getBitmapDescriptor();
                    }
                } else {
                    bitmapDescriptor = clusterBaiduItem.getBitmapDescriptor();
                }
                //从聚合管理器里面拿到marker，动态改变它
                Marker marker = mClusterManager.getDefaultClusterRenderer().getMarker(clusterBaiduItem);
                if (marker != null) {
                    marker.setIcon(bitmapDescriptor);
                }
                //刷新
                mClusterManager.cluster();
                return;
            }

        }
    }

    /**
     * 点击逻辑
     */
    private void IconClick(ClusterBaiduItem clusterBaiduItem) {
        //恢复上一个点击为正常状态
        if (mPreClickItem != null) {
            mPreClickItem.setBitmapId(R.mipmap.icon_ditu);
            BitmapDescriptor bitmapDescriptor;
            if (!TextUtils.isEmpty(mPreClickItem.getUrlLocalMarkerIconPath()) &&
                    new File(mPreClickItem.getUrlLocalMarkerIconPath()).exists()) {
                bitmapDescriptor = mPreClickItem.getUrlMarkerIconBitmapDescriptor(false);
                if (bitmapDescriptor == null) {
                    bitmapDescriptor = mPreClickItem.getBitmapDescriptor();
                }
            } else {
                bitmapDescriptor = mPreClickItem.getBitmapDescriptor();
            }
            //从聚合管理器里面拿到marker，动态改变它
            Marker marker = mClusterManager.getDefaultClusterRenderer().getMarker(mPreClickItem);
            if (marker != null) {
                marker.setIcon(bitmapDescriptor);
            }
        }
        //设置新的点击为大图状态
        if (clusterBaiduItem != null) {
            clusterBaiduItem.setBitmapId(R.mipmap.icon_ditu);
            BitmapDescriptor bitmapDescriptor;
            if (!TextUtils.isEmpty(clusterBaiduItem.getUrlLocalMarkerIconPath()) &&
                    new File(clusterBaiduItem.getUrlLocalMarkerIconPath()).exists()) {
                bitmapDescriptor = clusterBaiduItem.getUrlMarkerIconBitmapDescriptor(true);
                if (bitmapDescriptor == null) {
                    bitmapDescriptor = clusterBaiduItem.getBitmapDescriptor();
                }
            } else {
                bitmapDescriptor = clusterBaiduItem.getBitmapDescriptor();
            }
            //从聚合管理器里面拿到marker，动态改变它
            Marker marker = mClusterManager.getDefaultClusterRenderer().getMarker(clusterBaiduItem);
            if (marker != null) {
                marker.setIcon(bitmapDescriptor);
            }
            //刷新
            mClusterManager.cluster();

        }
        mPreClickItem = clusterBaiduItem;
    }


    /**
     * 聚合点击
     */
    private void ClusterOnClick(Cluster<ClusterBaiduItem> clusterBaiduItems) {
        if (mBaiduMap == null) {
            return;
        }
        if (clusterBaiduItems.getItems().size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (ClusterBaiduItem clusterBaiduItem : clusterBaiduItems.getItems()) {
                builder.include(clusterBaiduItem.getPosition());
            }
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory
                    .newLatLngBounds(builder.build()));
        }
    }

    /**
     * 清除各种状态
     */
    protected void clearStatus(boolean isClearPageIndex) {
        mBaiduMap.clear();
        mClusterManager.clearItems();
        mClusterBaiduItems.clear();
        if (isClearPageIndex)
            mIndex = 0;
        mIsMove = false;
    }


    /**
     * 请求数据对应的返回
     */
    public void onEventMainThread(RequestDataEvent e) {

        if (e.getEventType() == RequestDataEvent.SUCCESS) {
            //成功，是最新的请求
            if (mUUID.equals(e.getUUID())) {
                showMapData(e.getDataList(), e.getClusterBaiduItems(), e.getTotalSize());
            }
        } else if (e.getEventType() == RequestDataEvent.DEFAULT) {

        } else {
            //没有数据
            if (mUUID.equals(e.getUUID())) {
                if (e.getEventType() == RequestDataEvent.FAIL)
                    Toast.makeText(this, "附近没有直播", Toast.LENGTH_SHORT).show();
//                showLoading();
                dismissLoadingDialog();
            }
        }

    }

    /**
     * 下载完图片完成
     */
    public void onEventMainThread(IconEvent e) {
        ChangeIconLogic(e);
    }

    /**
     * 网络请求执行逻辑
     */
    private class NetRunnable implements Runnable {

        private boolean isClearStatus;

        public NetRunnable() {
            super();
        }

        public NetRunnable(boolean isClearStatus) {
            super();
            this.isClearStatus = isClearStatus;
        }

        @Override
        public void run() {
            netDataLogic(isClearStatus);
        }
    }


//    private void initViews() {
//        sp = SPUtil.getInstance(this);
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mBaiduMap = mBaiduMapView.getMap();
//
//        // 比例尺控件
//        mBaiduMapView.showScaleControl(true);
//        // 缩放控件
//        mBaiduMapView.showZoomControls(false);
//        // 百度地图LoGo -> 正式版切记不能这么做，本人只是觉得logo丑了
//        mBaiduMapView.removeViewAt(1);
//        //不倾斜
//        mBaiduMap.getUiSettings().setOverlookingGesturesEnabled(false);
//        //不旋转
//        mBaiduMap.getUiSettings().setRotateGesturesEnabled(false);
//        //设置缩放层级
//        mBaiduMap.setMaxAndMinZoomLevel(19, 12);
//        //图标管理器
//        mMarkerManager = new MarkerManager(mBaiduMap);
//        //聚合与渲染管理器
//        mClusterManager = new ClusterManager<>(this, mBaiduMap, mMarkerManager);
//
//        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
//
//        mBaiduMap.setOnMapLoadedCallback(this);
//
//        mBaiduMap.setMyLocationEnabled(true);
//
//        //为什么从这里读？这里读出来的size统一
//        Bitmap bitmap = CommonUtil.getImageFromAssetsFile(LiveMallApplication.getApplication(), "current_location.png");
//
//        //调整当前位置图片的显示
//        float scale = 0.80f;
//        Matrix matrix = new Matrix();
//        matrix.postScale(scale, scale);
//        mCLBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        bitmap.recycle();
//
//        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(mCLBitmap);
//        MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, false, bitmapDescriptor);
//        mBaiduMap.setMyLocationConfigeration(myLocationConfiguration);
//
//    }

    /**
     * 初始化地图默认位置
     */
//    private void initDefaultLocation() {
////        String uid = sp.getString("uid");
//        String uid = sharedPreferences.getString("uid", "");
//        String llat = sp.getString("lon");
//        String llng = sp.getString("lat");
//
//        Log.e("llat",llat);
//        Log.e("llng",llng);
//
//
//        //珠海经纬度x
////        double llat = 22.276012;
////        double llng = 113.583087;
//        if (llat == null || llng == null || "".equals(llat) || "".equals(llng))
//            return;
//
//        //显示上方位置的builder
//        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(0)
//                .direction(0).latitude(Double.valueOf(llat))
//                .longitude(Double.valueOf(llng)).build();
//
//        //显示上方位置
//        mBaiduMap.setMyLocationData(locData);
//
//        //显示等级-转换：初始化为mDefaultRadius半径的层级用于显示
//        float level = LocationLevelUtils.returnCurZoom(mDefaultRadius);
//
//        //当前地图状态
//        mCurrentMapStatus = new MapStatus.Builder().target(new LatLng(Double.valueOf(llat), Double.valueOf(llng))).zoom(level).build();
//
//        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mCurrentMapStatus));
////        private String uid;
////
////        //纬度
////        private int latitude;
////
////        //经度
////        private int longitude;
////
////        //页数
////        private int page;
//        //初始化数据搜索model
//        mSearchModel = new SearchModel();
//        mSearchModel.setUid(uid);
//        mSearchModel.setLatitude(Double.valueOf(llng));
//        mSearchModel.setLongitude(Double.valueOf(llat));
////        mSearchModel.setLevel(level);
////        mSearchModel.setTableId(158714);
//    }


    //刷新
//    private void loading() {
//        mIsLoading = true;
//        hideLoading();
//    }

    //隐藏
//    private void hideLoading() {
//        refresh.setVisibility(View.GONE);
//        mIsLoading = false;
//    }

    //显示
//    private void showLoading() {
//        refresh.setVisibility(View.VISIBLE);
//        mIsLoading = false;
//
//    }

    //计算刷新的index
//    private void PageIndex() {
//        if (mIndex >= mMaxPageSize) {
//            mIndex = 0;
//        } else {
//            mIndex++;
//        }
//    }

//    private void showLoadingDialog() {
//        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
//            return;
//        }
//        if (isFinishing())
//            return;
//        mLoadingDialog = new LoadingDialog(this);
//        mLoadingDialog.setCanceledOnTouchOutside(false);
//        mLoadingDialog.show();
//    }

//    private void dismissLoadingDialog() {
//        if (isFinishing())
//            return;
//        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
//            mLoadingDialog.dismiss();
//            mLoadingDialog = null;
//        }
//    }
    @Override
    public void onResume() {
        super.onResume();
        if (mBaiduMapView != null)
            mBaiduMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBaiduMapView != null)
            mBaiduMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (LiveMallApplication.getApplication().getEventBus().isRegistered(this)) {
//            LiveMallApplication.getApplication().getEventBus().unregister(this);
//        }

        //销毁对应的bitmap
//        if (mCLBitmap != null && !mCLBitmap.isRecycled()) {
//            mCLBitmap.recycle();
//        }

//        mClusterManager.clearItems();

//        mBaiduMap.clear();
        //销毁地图
//        mBaiduMapView.onDestroy();
//        LiveMallApplication.getApplication().getJobManager().clear();
//        dismissLoadingDialog();
    }

    @Override
    public void onMapLoaded() {
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mCurrentMapStatus));
    }

    @OnClick({R.id.btn_finish, R.id.btn_location, R.id.btn_search, R.id.refresh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_finish:
                finish();
                break;
            case R.id.btn_location:
                showLoadingDialog();
                PageIndex();
                loading();
                break;
            case R.id.btn_search:
                break;
            case R.id.refresh:
                showLoadingDialog();
                PageIndex();
                loading();
                break;
        }
    }
}

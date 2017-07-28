package com.example.project.limolive.baidu_location;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.clusterutil.clustering.ClusterItem;
import com.baidu.mapapi.clusterutil.clustering.view.DefaultClusterRenderer;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.example.project.limolive.LiveMallApplication;
import com.example.project.limolive.R;
import com.example.project.limolive.bean.home.HomeListBeen;

import java.io.File;

import static com.example.project.limolive.baidu_location.utils.FileUtils.BIG_END;

/**
 * 百度地图上一个一个的item
 */
public class ClusterBaiduItem implements ClusterItem {


    //经纬度
    private LatLng mPosition;

    //对应的网络数据
    private HomeListBeen mLBAModel;

    //当前这个item的地址
    private String markerAddress;

    //聚合marker本地路径
    private String urlClusterPath;

    //当个marker本地路径
    private String urlMarkerPath;

    //marker的url
    private String markerUrl;

    //默认的marker图标
    private int mBitmapId = -1;
    private View view;

    @Override

    public String getUrlLocalMarkerIconPath() {
        return urlMarkerPath;
    }

    public void setUrlMarkerPath(String urlMarkerPath) {
        this.urlMarkerPath = urlMarkerPath;
    }

    @Override
    public String getUrlClusterIconPath() {
        return urlClusterPath;
    }

    public void setUrlClusterPath(String urlClusterPath) {
        this.urlClusterPath = urlClusterPath;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }


    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        if (mBitmapId != -1) {
            return BitmapDescriptorFactory
                    .fromResource(mBitmapId);
        }

        return BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_ditu);
//        return BitmapDescriptorFactory
//                .fromResource(R.drawable.default_map_icon);
    }

    @Override
    public BitmapDescriptor getUrlClusterIconBitmapDescriptor() {
        if (!TextUtils.isEmpty(urlClusterPath)) {
            Bitmap var1 = BitmapFactory.decodeFile(urlClusterPath);
            if (var1 == null) {
                if (!DefaultClusterRenderer.LOADING_LOGO)
                    deleteFile(urlClusterPath);
                return null;
            } else {
                var1.recycle();
            }
            return BitmapDescriptorFactory
                    .fromPath(urlClusterPath);

        }

        return null;
    }

    @Override
    public BitmapDescriptor getUrlMarkerIconBitmapDescriptor(boolean select) {
        if (select) {
            if (!TextUtils.isEmpty(urlMarkerPath + BIG_END)) {
                Bitmap var1 = BitmapFactory.decodeFile(urlMarkerPath + BIG_END);
                if (var1 == null) {
                    if (!DefaultClusterRenderer.LOADING_LOGO)
                        deleteFile(urlMarkerPath + BIG_END);
                    return null;
                } else {
                    var1.recycle();
                }
                return BitmapDescriptorFactory
                        .fromPath(urlMarkerPath + BIG_END);
            }
        } else {
            if (!TextUtils.isEmpty(urlMarkerPath)) {
                Bitmap var1 = BitmapFactory.decodeFile(urlMarkerPath);
                if (var1 == null) {
                    if (!DefaultClusterRenderer.LOADING_LOGO)
                        deleteFile(urlMarkerPath);
                    return null;
                } else {
                    view = LayoutInflater.from(LiveMallApplication.getContext()).inflate(R.layout.sasas, null);
                    ImageView ditu_icon = (ImageView) view.findViewById(R.id.ditu_icon);
                    ditu_icon.setImageBitmap(var1);
//                    var1.recycle();
                }
//                return BitmapDescriptorFactory
//                        .fromPath(urlMarkerPath);
                return BitmapDescriptorFactory
                        .fromView(view);
            }
        }
        return null;
    }

    private void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public String getMarkerAddress() {
        return markerAddress;
    }

    public void setMarkerAddress(String markerAddress) {
        this.markerAddress = markerAddress;
    }

    public ClusterBaiduItem(LatLng latLng) {
        mPosition = latLng;
    }

    public void setBitmapId(int bitmapId) {
        this.mBitmapId = bitmapId;
    }

    public String getMarkerUrl() {
        return markerUrl;
    }

    public void setMarkerUrl(String markerUrl) {
        this.markerUrl = markerUrl;
    }

    public HomeListBeen getLBAModel() {
        return mLBAModel;
    }

    public void setLBAModel(HomeListBeen lbsModel) {
        this.mLBAModel = lbsModel;
    }
}
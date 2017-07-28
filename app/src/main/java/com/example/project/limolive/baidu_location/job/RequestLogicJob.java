package com.example.project.limolive.baidu_location.job;


import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.model.LatLng;
import com.example.project.limolive.LiveMallApplication;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.baidu_location.ClusterBaiduItem;
import com.example.project.limolive.baidu_location.event.RequestDataEvent;
import com.example.project.limolive.baidu_location.model.SearchModel;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;

import static com.example.project.limolive.baidu_location.utils.CommonUtil.MapToUrl;
import static com.example.project.limolive.baidu_location.utils.CommonUtil.inputMapForUrl;
import static com.example.project.limolive.baidu_location.utils.FileUtils.getLogoNamePath;

public class RequestLogicJob extends Job {

    private String mUUID;

    private Map<String, String> urlMap;

    private SearchModel mSearchModel;

    private Handler mHandler = new Handler();

    private int mPageIndex = 0;

    protected RequestLogicJob() {
        super(new Params(1000));
    }

    public RequestLogicJob(SearchModel searchModel, int pageIndex, String uuid) {
        super(new Params(1000));
        this.mSearchModel = searchModel;
        this.mPageIndex = pageIndex;
        this.mUUID = uuid;
    }

    @Override
    public void onAdded() {
    }

    @Override
    public void onRun() throws Throwable {
        requestCloudData();
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }

    private void requestCloudData() {

//        urlMap = inputMapForUrl(mSearchModel, mPageIndex, LiveMallApplication.PAGE_SIZE);
//
//        //头部链接
//        String requestHeader = "/geosearch/v3/nearby?";
//
//        //sn签名
//        String sn = SNLogic(requestHeader, urlMap);
//
//        //生成请求URL
//        final String url = "http://api.map.baidu.com" + requestHeader + MapToUrl(urlMap) + "&sn=" + sn;
        urlMap = inputMapForUrl(mSearchModel, mPageIndex, LiveMallApplication.PAGE_SIZE);

        //头部链接
        String requestHeader = "appapi/Ucenter/nearlive?";
        //生成请求URL
        final String url = "http://esj.tts1000.com/index.php/" + requestHeader + MapToUrl(urlMap);
        //发起请求
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                HttpRequest.get(url, new getDataListener());
            }
        });
    }

    /**
     * 拿到了数据
     */
    private class getDataListener extends JsonHttpRequestCallback {

        @Override
        protected void onSuccess(final JSONObject jsonObject) {
            super.onSuccess(jsonObject);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i("JsonObject", jsonObject.toString());
                    JSONArray dataJSON = jsonObject.getJSONArray("data");
                    List<HomeListBeen> list = toListModel(jsonObject);
                    successResult((dataJSON == null || dataJSON.size() == 0) ? 0 : dataJSON.size(), list, jsonObject.getString("message"));
                }
            }).start();
        }

        @Override
        public void onFailure(int errorCode, String msg) {
            super.onFailure(errorCode, msg);
            FailToEvent();
        }
    }


    private List<HomeListBeen> toListModel(JSONObject response) {
        int status = response.getInteger("code");
        if (status != 0) {
            FailToEvent();
            return null;
        }
        JSONArray dataJSON = response.getJSONArray("data");
        List<HomeListBeen> data = new ArrayList<>();
        if (dataJSON != null && !TextUtils.isEmpty(dataJSON.toString())) {
            List<HomeListBeen> list = JSON.parseArray(dataJSON.toString(), HomeListBeen.class);
            data.addAll(list);
        }
        return data;

    }

    /**
     * 返回数据
     */
    private void successResult(int totalCount, List<HomeListBeen> lbsModels, String message) {
        if (lbsModels != null && lbsModels.size() != 0) {

            //生成百度items
            List<ClusterBaiduItem> items = BaiduItemLogic(lbsModels);

            RequestDataEvent requestDataEvent = new RequestDataEvent();
            requestDataEvent.setEventType(RequestDataEvent.SUCCESS);
            requestDataEvent.setMessage(message);
            requestDataEvent.setClusterBaiduItems(items);
            requestDataEvent.setDataList(lbsModels);
            requestDataEvent.setLastSize(lbsModels.size());
            requestDataEvent.setUUID(mUUID);
            requestDataEvent.setTotalSize(totalCount);
            requestDataEvent.setParamsMap(urlMap);
            LiveMallApplication.getApplication().getEventBus().post(requestDataEvent);

        } else {
            RequestDataEvent requestDataEvent = new RequestDataEvent();
            requestDataEvent.setEventType(RequestDataEvent.NULL);
            requestDataEvent.setUUID(mUUID);
            LiveMallApplication.getApplication().getEventBus().post(requestDataEvent);

        }
    }

    /**
     * 组装百度需要的item
     */
    private List<ClusterBaiduItem> BaiduItemLogic(List<HomeListBeen> list) {
        List<ClusterBaiduItem> items = new ArrayList<>();
        for (HomeListBeen lbsModel : list) {
            LatLng ll = new LatLng(Double.valueOf(lbsModel.getLbs().getLatitude()), Double.valueOf(lbsModel.getLbs().getLongitude()));
            ClusterBaiduItem baiduItem = new ClusterBaiduItem(ll);
            baiduItem.setMarkerAddress(lbsModel.getLbs().getAddress());
            baiduItem.setLBAModel(lbsModel);
            baiduItem.setMarkerUrl(ApiHttpClient.API_LOCATION_PIC + lbsModel.getHost().getAvatar());
            //如果是图片字段会变为几个尺寸的model
            if (!TextUtils.isEmpty(lbsModel.getHost().getAvatar())) {
                baiduItem.setUrlMarkerPath(getLogoNamePath(ApiHttpClient.API_LOCATION_PIC + lbsModel.getHost().getAvatar()));
            } else {
                baiduItem.setUrlMarkerPath("");
            }
            items.add(baiduItem);
        }
        return items;
    }

    /**
     * 请求失败
     */
    private void FailToEvent() {
        RequestDataEvent requestDataEvent = new RequestDataEvent();
        requestDataEvent.setEventType(RequestDataEvent.FAIL);
        requestDataEvent.setUUID(mUUID);
        LiveMallApplication.getApplication().getEventBus().post(requestDataEvent);
    }

}
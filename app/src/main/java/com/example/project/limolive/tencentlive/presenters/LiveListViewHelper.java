package com.example.project.limolive.tencentlive.presenters;


import android.os.AsyncTask;

import com.example.project.limolive.tencentlive.model.LiveInfoJson;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.presenters.viewinface.LiveListView;
import com.example.project.limolive.tencentlive.utils.LogConstants;
import com.example.project.limolive.tencentlive.utils.SxbLog;

import java.util.ArrayList;

/**
 * 直播列表页Presenter
 */
public class LiveListViewHelper extends Presenter {

    private final static String TAG = "LiveListViewHelper";

    private LiveListView mLiveListView;
    private GetLiveListTask mGetLiveListTask;

    public LiveListViewHelper(LiveListView view) {
        mLiveListView = view;
    }


    public void getPageData() {
        mGetLiveListTask = new GetLiveListTask();
        mGetLiveListTask.execute(0, 20);
    }

    public void getMoreData() {

    }

    @Override
    public void onDestory() {
    }

    /**
     * 获取后台数据接口
     */
    class GetLiveListTask extends AsyncTask<Integer, Integer, ArrayList<LiveInfoJson>> {

        @Override
        protected ArrayList<LiveInfoJson> doInBackground(Integer... params) {
            SxbLog.d(TAG, LogConstants.ACTION_VIEWER_ENTER_ROOM + LogConstants.DIV + LiveMySelfInfo.getInstance().getId() + LogConstants.DIV + "request room list");
            return OKhttpHelper.getInstance().getLiveList(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(ArrayList<LiveInfoJson> result) {
            if(null != result) {
                SxbLog.d(TAG, LogConstants.ACTION_VIEWER_ENTER_ROOM + LogConstants.DIV + LiveMySelfInfo.getInstance().getId() + LogConstants.DIV + "request room list"
                        + LogConstants.DIV + LogConstants.STATUS.SUCCEED + LogConstants.DIV + "get list size " + result.size());
                if (mLiveListView != null)
                    mLiveListView.showFirstPage(result);
            }
        }
    }

}

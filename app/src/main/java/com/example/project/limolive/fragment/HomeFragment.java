package com.example.project.limolive.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.GoodsTypeAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.baidu_location.BaiDuLocation;
import com.example.project.limolive.bean.GoodsTypeBeen;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.bean.home.LunBoPicBean;
import com.example.project.limolive.tencentim.ui.ConversationActivity;
import com.example.project.limolive.presenter.HomePresenter;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.utils.AnimationUtil;
import com.example.project.limolive.utils.SimpleTimeTask;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.utils.ViewUtils;
import com.example.project.limolive.view.ColumnHorizontalScrollView;
import com.example.project.limolive.view.HeaderGridView;
import com.example.project.limolive.view.ImageCycleView;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * 首页
 *
 * @author hpg
 *         2016/12/13
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, Presenter.NotificationToActivity {

    private HomePresenter homePresenter;
    private LinearLayout llShouyeAdress, ll_more_columns, mRadioGroup_content, ll_message, ll_column;
    private RelativeLayout rl_column;
    private EditText sy_query;
    private ImageView shade_left, shade_right;
    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    private HeaderGridView home_gv;
    private AutoSwipeRefreshLayout swipe_refresh_tool;
    private ImageCycleView home_banner;
    private TextView live_title, tv_shouye_all_type;
    private View mGrayLayout;
    //屏幕宽度
    private int mScreenWidth = 0;
    // 当前选中的栏目
    private int columnSelectIndex = 0;
    //    private String str[] = new String[];
    //定义手势检测器实例
    private GestureDetector detector = null;
    //定义手势动作两点之间的最小距离
    private final int FLIP_DISTANCE = 100;
    private List<String> stringList;
    private List<HomeListBeen> homeListBeens = null;
    private List<GoodsTypeBeen> allcatgoryBeens;
    private boolean isPopWindowShowing = false;
    int fromYDelta;
    private PopupWindow mPopupWindow;
    private GoodsTypeAdapter typeAdapter;
    /**
     * 刷新计时器
     */
    private Timer updateTimer;
    private SimpleTimeTask task;
    //是否刷新成功
    private boolean isUpDateFinished = false;
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isUpDateFinished) {
                cancelTimer();
            } else {
                cancelTimer();
                if (swipe_refresh_tool != null && swipe_refresh_tool.isRefreshing()) {
                    swipe_refresh_tool.setRefreshing(false);
                    ToastUtils.showShort(getActivity(), "网络问题，请您稍后重试！");
                    ApiHttpClient.cancelAllRequest();
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_shouye, inflater, container);
    }

    @Override
    protected void initView() {
        super.initView();
        homePresenter = new HomePresenter(getActivity());
        homePresenter.registerMsgToActivity(this);
        mScreenWidth = getWindowsWidth(getActivity());
        rl_column = (RelativeLayout) findViewById(R.id.rl_column);
        ll_column = (LinearLayout) findViewById(R.id.ll_column);
        llShouyeAdress = (LinearLayout) findViewById(R.id.ll_shouye_adress);
        ll_more_columns = (LinearLayout) findViewById(R.id.ll_more_columns);
        mRadioGroup_content = (LinearLayout) findViewById(R.id.mRadioGroup_content);
        ll_message = (LinearLayout) findViewById(R.id.ll_message);
        mColumnHorizontalScrollView = (ColumnHorizontalScrollView) findViewById(R.id.mColumnHorizontalScrollView);
        home_gv = (HeaderGridView) findViewById(R.id.home_gv);
        sy_query = (EditText) findViewById(R.id.sy_query);
        tv_shouye_all_type = (TextView) findViewById(R.id.tv_shouye_all_type);
        shade_left = (ImageView) findViewById(R.id.shade_left);
        shade_right = (ImageView) findViewById(R.id.shade_right);
//        mGrayLayout = findViewById(R.id.gray_layout);
        swipe_refresh_tool = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_refresh_tool);
        swipe_refresh_tool.setOnRefreshListener(this);
        swipe_refresh_tool.setColorSchemeColors(Color.parseColor("#31D5BA"));
        llShouyeAdress.setOnClickListener(this);
        ll_more_columns.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        home_gv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
        //创建手势检测器
        detector = new GestureDetector(getActivity(), new DetectorGestureListener());
//        initTabColumn();
        initHeader();
        home_gv.setOnScrollListener(homePresenter);
        home_gv.setAdapter(homePresenter.getHomeAdapter());
        home_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (homePresenter.getListBean() != null && homePresenter.getListBean().size() > 0) {
                    homeListBeens = homePresenter.getListBean();
                    HomeListBeen item = homeListBeens.get(position - 2);
                    if (item.getHost().getUid().equals(LiveMySelfInfo.getInstance().getId())) {
                        Intent intent = new Intent(getActivity(), LiveingActivity.class);
                        intent.putExtra(Constants.ID_STATUS, Constants.HOST);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.HOST);
                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(item.getHost().getUid());
                        CurLiveInfo.setHostName(item.getHost().getUsername());
                        CurLiveInfo.setHostAvator(item.getHost().getAvatar());
                        CurLiveInfo.setHost_phone(item.getHost().getPhone());
                        CurLiveInfo.setRoomNum(item.getAvRoomId());
                        CurLiveInfo.setMembers(Integer.parseInt(item.getWatchCount()) + 1); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(item.getAdmireCount()));
                        CurLiveInfo.setAddress(item.getLbs().getAddress());
                        CurLiveInfo.setTitle(item.getTitle());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), LiveingActivity.class);
                        intent.putExtra(Constants.ID_STATUS, Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(item.getHost().getUid());
                        CurLiveInfo.setHostName(item.getHost().getUsername());
                        CurLiveInfo.setHostAvator(item.getHost().getAvatar());
                        CurLiveInfo.setRoomNum(item.getAvRoomId());
                        CurLiveInfo.setHost_phone(item.getHost().getPhone());
                        CurLiveInfo.setMembers(Integer.parseInt(item.getWatchCount()) + 1); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(item.getAdmireCount()));
                        CurLiveInfo.setAddress(item.getLbs().getAddress());
                        CurLiveInfo.setTitle(item.getTitle());
                        startActivity(intent);
                    }
                }
            }
        });
        //对黑色半透明背景做监听，点击时开始退出动画并将popupwindow dismiss掉
//        mGrayLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isPopWindowShowing) {
//                    mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(getActivity(), fromYDelta));
//                    mPopupWindow.getContentView().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mPopupWindow.dismiss();
//                        }
//                    }, AnimationUtil.ANIMATION_OUT_TIME);
//                }
//            }
//        });
        swipe_refresh_tool.autoRefresh();
    }

    /**
     * 加载头布局
     */
    private void initHeader() {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.view_home_header, null);
        header.findViewById(R.id.home_banner).setOnClickListener(this);
        home_banner = (ImageCycleView) header.findViewById(R.id.home_banner);
        live_title = (TextView) header.findViewById(R.id.live_title);
        home_gv.addHeaderView(header, null, true);
//        live_title.setText("热门推荐");
    }


    private void initTabColumn() {
        mRadioGroup_content.removeAllViews();
        mColumnHorizontalScrollView.setParam(getActivity(), mScreenWidth, mRadioGroup_content, shade_left, shade_right, ll_more_columns, rl_column);
        for (int i = 0; i < allcatgoryBeens.size(); i++) {
            final int index = i;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 30;
            params.rightMargin = 30;
            TextView columnTextView = new TextView(getActivity());
            columnTextView.setTextAppearance(getActivity(), R.style.top_category_scroll_view_item_text);
//			localTextView.setBackground(getResources().getDrawable(R.drawable.top_category_scroll_text_view_bg));
//            columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
            columnTextView.setGravity(Gravity.CENTER);
//            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setId(i);
            columnTextView.setTextSize(18);
            columnTextView.setText(allcatgoryBeens.get(i).getType_name());
            columnTextView.setTextColor(getResources().getColorStateList(R.color.text_scroll_color));
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
                live_title.setText(allcatgoryBeens.get(index).getType_name());
            }
            mRadioGroup_content.addView(columnTextView, i, params);
            columnTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRadioGroup_content.getChildAt(index).setSelected(true);
                    selectTab(index);
                    live_title.setText(allcatgoryBeens.get(index).getType_name());
                }
            });
        }
        Log.i("initTabColumn````````", "123");
    }


    /**
     * 获取屏幕的宽度
     */
    public final static int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 启动定时器
     */
    private void loadTime() {
        cancelTimer();
        updateTimer = new Timer();
        task = new SimpleTimeTask(refreshHandler);
        updateTimer.schedule(task, 5000); //延迟10s发送
        isUpDateFinished = false;
    }

    /**
     * 释放定时器
     */
    private void cancelTimer() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (updateTimer != null) {
            updateTimer.purge();
            updateTimer.cancel();
            updateTimer = null;
        }
    }

    @Override
    public void onRefresh() {
        Carousel();
        loadTime();
        goodsType();
        homePresenter.refresh();
    }

    public void goodsType() {
        Api.get_goodstype(new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("商品类型", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    allcatgoryBeens = new ArrayList<GoodsTypeBeen>();
                    allcatgoryBeens.addAll(JSON.parseArray(apiResponse.getData(), GoodsTypeBeen.class));
                    initTabColumn();
                } else {
                    goodsType();
//                    ToastUtils.showShort(getActivity(), apiResponse.getMessage());
                }
            }
        });
    }

    public void get_hotGoodstype() {
        Api.get_hotGoodstype(new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("热门商品类型", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    allcatgoryBeens = new ArrayList<GoodsTypeBeen>();
                    allcatgoryBeens.addAll(JSON.parseArray(apiResponse.getData(), GoodsTypeBeen.class));
                    initTabColumn();
                } else {
                    goodsType();
//                    ToastUtils.showShort(getActivity(), apiResponse.getMessage());
                }
            }
        });
    }

    public void Carousel() {
        final List<LunBoPicBean> lunBoPicBeen = new ArrayList<LunBoPicBean>();
        final List<String> string = new ArrayList<String>();
        Api.carousel(new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("轮播图", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    lunBoPicBeen.addAll(JSON.parseArray(apiResponse.getData(), LunBoPicBean.class));
                    string.clear();
                    for (LunBoPicBean l : lunBoPicBeen) {
                        string.add(l.getImg());
                    }
                    if (string != null && string.size() > 0)
                        home_banner.setImageResources(string);
                } else {
                    ToastUtils.showShort(getActivity(), apiResponse.getMessage());
                }
            }
        });
    }

    @Override
    public void presenterTakeAction(int action) {
        if (action == HomePresenter.FRIEND_FORUM_LIST) {
            swipe_refresh_tool.setRefreshing(false);
            cancelTimer();
            isUpDateFinished = true;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_shouye_adress:
                //startActivity(new Intent(getActivity(), BaiDuLocation.class));
                break;
            case R.id.ll_message:
                Intent intent = new Intent();
                intent.setClass(getActivity(), ConversationActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_more_columns:
                if (isPopWindowShowing) {
                    mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(getActivity(), fromYDelta));
                    mPopupWindow.getContentView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPopupWindow.dismiss();
                            tv_shouye_all_type.setVisibility(View.GONE);
                            mColumnHorizontalScrollView.setVisibility(View.VISIBLE);
                        }
                    }, AnimationUtil.ANIMATION_OUT_TIME);
                } else {
                    tv_shouye_all_type.setVisibility(View.VISIBLE);
                    mColumnHorizontalScrollView.setVisibility(View.GONE);
                    showPopupWindow();
                }
                break;
        }
    }

    private void showPopupWindow() {
        final View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_home_layout, null);
        HeaderGridView home_popWindow_gv = (HeaderGridView) contentView.findViewById(R.id.home_popWindow_gv);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //将这两个属性设置为false，使点击popupwindow外面其他地方不会消失
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setFocusable(false);
//        mGrayLayout.setVisibility(View.VISIBLE);
        //获取popupwindow高度确定动画开始位置
        int contentHeight = ViewUtils.getViewMeasuredHeight(contentView);
        mPopupWindow.showAsDropDown(ll_column, 0, 0);
        fromYDelta = -contentHeight - 50;
        mPopupWindow.getContentView().startAnimation(AnimationUtil.createInAnimation(getActivity(), fromYDelta));

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isPopWindowShowing = false;
//                mGrayLayout.setVisibility(View.GONE);
            }
        });
        if (allcatgoryBeens != null && allcatgoryBeens.size() > 0) {
            if (typeAdapter == null) {
                typeAdapter = new GoodsTypeAdapter(getActivity(), allcatgoryBeens);
            } else {
                typeAdapter.notifyDataSetChanged();
            }
        }
        home_popWindow_gv.setAdapter(typeAdapter);
        home_popWindow_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(getActivity(), fromYDelta));
                mPopupWindow.getContentView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPopupWindow.dismiss();
                        tv_shouye_all_type.setVisibility(View.GONE);
                        mColumnHorizontalScrollView.setVisibility(View.VISIBLE);
                    }
                }, AnimationUtil.ANIMATION_OUT_TIME);
                live_title.setText(allcatgoryBeens.get(position).getType_name());
                isPopWindowShowing = false;
                selectTab(position);
            }
        });
        isPopWindowShowing = true;
    }

    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
        if (tab_postion < mRadioGroup_content.getChildCount()) {
            for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                View checkView = mRadioGroup_content.getChildAt(tab_postion);
                int k = checkView.getMeasuredWidth();
                int l = checkView.getLeft();
                int i2 = l + k / 2 - mScreenWidth / 2;
                // rg_nav_content.getParent()).smoothScrollTo(i2, 0);
                mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
                // mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
                // mItemWidth , 0);

            }
            //判断是否选中
            for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
                View checkView = mRadioGroup_content.getChildAt(j);
                boolean ischeck;
                if (j == tab_postion) {
                    ischeck = true;
                } else {
                    ischeck = false;
                }
                checkView.setSelected(ischeck);
            }
        }
    }

    //内部类，实现GestureDetector.OnGestureListener接口
    class DetectorGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        //当用户在触屏上“滑过”时触发此方法
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            if (e1 != null && e2 != null) {
//            int i = tabs.getCurrentTab();
                //第一个触点事件的X坐标值减去第二个触点事件的X坐标值超过FLIP_DISTANCE，也就是手势从右向左滑动
                if (e1.getX() - e2.getX() > FLIP_DISTANCE) {
                    if (columnSelectIndex < allcatgoryBeens.size())
                        columnSelectIndex++;
                    Log.i("columnSelectIndex", columnSelectIndex + "");
                    selectTab(columnSelectIndex);
//                    tabs.setCurrentTab(i + 1);
                    //	float currentX = e2.getX();
                    //	list[i].setRight((int) (inialX - currentX));
                    return true;
                }

                //第二个触点事件的X坐标值减去第一个触点事件的X坐标值超过FLIP_DISTANCE，也就是手势从左向右滑动
                else if (e2.getX() - e1.getX() > FLIP_DISTANCE) {
                    if (columnSelectIndex > 0)
                        columnSelectIndex--;
                    selectTab(columnSelectIndex);
//                if (i > 0)
//                    tabs.setCurrentTab(i - 1);
                    return true;
                }
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

    }
}

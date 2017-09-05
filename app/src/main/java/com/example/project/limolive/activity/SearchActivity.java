package com.example.project.limolive.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.FollowAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.FollowLiveBeans;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.fragment.BaseFragment;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAA on 2017/8/16.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_refresh, tv_search;
    private LinearLayout ll_is_gone;
    private RecyclerView rv_tvj, rv_resou, rv_search;
    private GridLayoutManager gm1, gm2;

    private List<HomeListBeen> list_resou;
    private List<HomeListBeen> list_tj;
    private List<HomeListBeen> list_search;
    private resouAdapter resouAdapter;
    private TvAdapter tvAdapter;
    private SearchAdapter searchAdapter;
    private EditText inputSearch;
    private RelativeLayout rl_rv_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initDate();
    }

    private void initDate() {

        Api.search_hot(LoginManager.getInstance().getUserID(this), new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                    list_resou.clear();
                    list_resou.addAll(JSONArray.parseArray(apiResponse.getData(), HomeListBeen.class));
                    resouAdapter.notifyDataSetChanged();
                    Log.i("热搜", "apiResponse.getData()=" + apiResponse.getData().toString());
                }
            }
        });
        Api.search_recommend(LoginManager.getInstance().getUserID(this), new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                    list_tj.clear();
                    list_tj.addAll(JSONArray.parseArray(apiResponse.getData(), HomeListBeen.class));
                    tvAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initView() {
        tv_refresh = (TextView) findViewById(R.id.tv_refresh);
        tv_refresh.setOnClickListener(this);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        ll_is_gone = (LinearLayout) findViewById(R.id.ll_is_gone);
        rl_rv_search = (RelativeLayout) findViewById(R.id.rl_rv_search);

        rv_tvj = (RecyclerView) findViewById(R.id.rv_tvj);
        rv_resou = (RecyclerView) findViewById(R.id.rv_resou);
        rv_search = (RecyclerView) findViewById(R.id.rv_search);

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        rl_rv_search.setVisibility(View.GONE);
        ll_is_gone.setVisibility(View.VISIBLE);
        initRecyclerView();
    }

    private void initRecyclerView() {
        gm1 = new GridLayoutManager(this, 4);
        gm2 = new GridLayoutManager(this, 2);
        list_resou = new ArrayList<>();
        list_tj = new ArrayList();
        list_search = new ArrayList<>();
        resouAdapter = new resouAdapter(this, list_resou);
        rv_resou.setLayoutManager(gm2);
        rv_resou.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen._15sp)));
        rv_resou.setAdapter(resouAdapter);

        tvAdapter = new TvAdapter(this, list_tj);
        rv_tvj.setLayoutManager(gm1);
        rv_tvj.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen._15sp)));
        rv_tvj.setAdapter(tvAdapter);

        searchAdapter=new SearchAdapter(this,list_search);
        rv_search.setLayoutManager(new LinearLayoutManager(this));
        rv_tvj.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen._15sp)));
        rv_search.setAdapter(searchAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh:
                list_resou.clear();
                list_tj.clear();
                initDate();
                break;
            case R.id.tv_search:

                String tv_search = inputSearch.getText().toString();
                search(tv_search);
                break;

        }

    }

    private void search(String tv_search) {
        list_search.clear();
        if (TextUtils.isEmpty(tv_search)){
            ToastUtils.showShort(this,"请输入内容");
            return;
        }
        rl_rv_search.setVisibility(View.VISIBLE);
        ll_is_gone.setVisibility(View.GONE);
        Api.search(LoginManager.getInstance().getUserID(this), tv_search, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {

                    list_search.addAll(JSONArray.parseArray(apiResponse.getData(), HomeListBeen.class));
                    searchAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private class resouAdapter extends RecyclerView.Adapter {
        private Context context;
        private List<HomeListBeen> resou;

        public resouAdapter(Context context, List<HomeListBeen> resou) {
            this.context = context;
            this.resou = resou;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ResouHolder(View.inflate(context, R.layout.item_resous, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final HomeListBeen homeListBeen = resou.get(position);
            ResouHolder holder1 = (ResouHolder) holder;
            holder1.tv_username.setText(homeListBeen.getHost().getUsername());
            switch (position) {
                case 0:
                    holder1.tv_paiming.setBackground(getDrawable(R.drawable.text_bgs1));
                    break;
                case 1:
                    holder1.tv_paiming.setBackground(getDrawable(R.drawable.text_bgs2));
                    break;
                case 2:
                    holder1.tv_paiming.setBackground(getDrawable(R.drawable.text_bgs3));
                    break;
                default:
                    holder1.tv_paiming.setBackground(getDrawable(R.drawable.text_bgs4));
                    break;
            }
            holder1.tv_paiming.setText(String.valueOf(position + 1));
            holder1.tv_username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (homeListBeen.getHost().getUid().equals(LiveMySelfInfo.getInstance().getId())) {
                        Intent intent = new Intent(context, LiveingActivity.class);
                        intent.putExtra(Constants.ID_STATUS, Constants.HOST);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.HOST);
                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(homeListBeen.getHost().getUid());
                        CurLiveInfo.setHostName(homeListBeen.getHost().getUsername());
                        CurLiveInfo.setHostAvator(homeListBeen.getHost().getAvatar());
                        CurLiveInfo.setHost_phone(homeListBeen.getHost().getPhone());
                        CurLiveInfo.setRoomNum(homeListBeen.getAvRoomId());
                        CurLiveInfo.setMembers(Integer.parseInt(homeListBeen.getWatchCount()) + 1); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(homeListBeen.getAdmireCount()));
                        CurLiveInfo.setAddress(homeListBeen.getLbs().getAddress());
                        CurLiveInfo.setTitle(homeListBeen.getTitle());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, LiveingActivity.class);
                        intent.putExtra(Constants.ID_STATUS, Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(homeListBeen.getHost().getUid());
                        CurLiveInfo.setHostName(homeListBeen.getHost().getUsername());
                        CurLiveInfo.setHostAvator(homeListBeen.getHost().getAvatar());
                        CurLiveInfo.setRoomNum(homeListBeen.getAvRoomId());
                        CurLiveInfo.setHost_phone(homeListBeen.getHost().getPhone());
                        CurLiveInfo.setMembers(Integer.parseInt(homeListBeen.getWatchCount()) + 1); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(homeListBeen.getAdmireCount()));
                        CurLiveInfo.setAddress(homeListBeen.getLbs().getAddress());
                        CurLiveInfo.setTitle(homeListBeen.getTitle());
                        startActivity(intent);
                    }
                    //跳转直播间
                }
            });
        }

        @Override
        public int getItemCount() {
            return resou.size();
        }

        private class ResouHolder extends RecyclerView.ViewHolder {
            TextView tv_username, tv_paiming;

            public ResouHolder(View itemView) {
                super(itemView);
                tv_username = itemView.findViewById(R.id.tv_username);
                tv_paiming = itemView.findViewById(R.id.tv_paiming);
            }
        }
    }

    private class TvAdapter extends RecyclerView.Adapter {
        private Context context;
        private List<HomeListBeen> resou;

        public TvAdapter(Context context, List<HomeListBeen> resou) {
            this.context = context;
            this.resou = resou;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ResouHolder(View.inflate(context, R.layout.item_resou1, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final HomeListBeen homeListBeen = resou.get(position);
            ResouHolder holder1 = (ResouHolder) holder;
            holder1.tv_resou_names.setText(homeListBeen.getHost().getUsername());
            holder1.tv_resou_names.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (homeListBeen.getHost().getUid().equals(LiveMySelfInfo.getInstance().getId())) {
                        Intent intent = new Intent(context, LiveingActivity.class);
                        intent.putExtra(Constants.ID_STATUS, Constants.HOST);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.HOST);
                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(homeListBeen.getHost().getUid());
                        CurLiveInfo.setHostName(homeListBeen.getHost().getUsername());
                        CurLiveInfo.setHostAvator(homeListBeen.getHost().getAvatar());
                        CurLiveInfo.setHost_phone(homeListBeen.getHost().getPhone());
                        CurLiveInfo.setRoomNum(homeListBeen.getAvRoomId());
                        CurLiveInfo.setMembers(Integer.parseInt(homeListBeen.getWatchCount()) + 1); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(homeListBeen.getAdmireCount()));
                        CurLiveInfo.setAddress(homeListBeen.getLbs().getAddress());
                        CurLiveInfo.setTitle(homeListBeen.getTitle());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, LiveingActivity.class);
                        intent.putExtra(Constants.ID_STATUS, Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(homeListBeen.getHost().getUid());
                        CurLiveInfo.setHostName(homeListBeen.getHost().getUsername());
                        CurLiveInfo.setHostAvator(homeListBeen.getHost().getAvatar());
                        CurLiveInfo.setRoomNum(homeListBeen.getAvRoomId());
                        CurLiveInfo.setHost_phone(homeListBeen.getHost().getPhone());
                        CurLiveInfo.setMembers(Integer.parseInt(homeListBeen.getWatchCount()) + 1); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(homeListBeen.getAdmireCount()));
                        CurLiveInfo.setAddress(homeListBeen.getLbs().getAddress());
                        CurLiveInfo.setTitle(homeListBeen.getTitle());
                        startActivity(intent);
                    }
                    //跳转直播间
                }
            });
        }

        @Override
        public int getItemCount() {
            return resou.size();
        }

        private class ResouHolder extends RecyclerView.ViewHolder {
            TextView tv_resou_names;

            public ResouHolder(View itemView) {
                super(itemView);
                tv_resou_names = itemView.findViewById(R.id.tv_resou_names);
            }
        }
    }

    private class SearchAdapter extends RecyclerView.Adapter {
        private Context context;

        private List<HomeListBeen> Search_lsit;

        public SearchAdapter(Context context, List<HomeListBeen> search_lsit) {
            this.context = context;
            Search_lsit = search_lsit;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SearchHolder(View.inflate(context, R.layout.item_follow, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final HomeListBeen homeListBeen = Search_lsit.get(position);
            SearchHolder holder1 = (SearchHolder) holder;

            holder1.tv_nick.setText(homeListBeen.getHost().getUsername());
            holder1.num.setText(homeListBeen.getWatchCount());

            if (homeListBeen.getHost().getAvatar().contains("http://")){
                holder1.avatar.setImageURI(homeListBeen.getHost().getAvatar());
            }else {
                holder1.avatar.setImageURI(ApiHttpClient.API_PIC+homeListBeen.getHost().getAvatar());
            }

            if (homeListBeen.getCover().contains("http://")){
                ImageLoader.getInstance().displayImage(homeListBeen.getCover(),holder1.largeImgs);
            }else {
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC+homeListBeen.getCover(),holder1.largeImgs);
            }
            holder1.isLive.setImageDrawable(context.getDrawable(R.drawable.zbz));

            holder1.iv_follow.setVisibility(View.GONE);
            holder1.num.setVisibility(View.GONE);
            holder1.tv_mlz.setText("魅力值 "+homeListBeen.getCharm());

            holder1.iv_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //取消关注接口调用
                }
            });
            holder1.largeImgs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (homeListBeen.getHost().getUid().equals(LiveMySelfInfo.getInstance().getId())) {
                        Intent intent = new Intent(context, LiveingActivity.class);
                        intent.putExtra(Constants.ID_STATUS, Constants.HOST);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.HOST);
                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(homeListBeen.getHost().getUid());
                        CurLiveInfo.setHostName(homeListBeen.getHost().getUsername());
                        CurLiveInfo.setHostAvator(homeListBeen.getHost().getAvatar());
                        CurLiveInfo.setHost_phone(homeListBeen.getHost().getPhone());
                        CurLiveInfo.setRoomNum(homeListBeen.getAvRoomId());
                        CurLiveInfo.setMembers(Integer.parseInt(homeListBeen.getWatchCount()) + 1); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(homeListBeen.getAdmireCount()));
                        CurLiveInfo.setAddress(homeListBeen.getLbs().getAddress());
                        CurLiveInfo.setTitle(homeListBeen.getTitle());
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, LiveingActivity.class);
                        intent.putExtra(Constants.ID_STATUS, Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(homeListBeen.getHost().getUid());
                        CurLiveInfo.setHostName(homeListBeen.getHost().getUsername());
                        CurLiveInfo.setHostAvator(homeListBeen.getHost().getAvatar());
                        CurLiveInfo.setRoomNum(homeListBeen.getAvRoomId());
                        CurLiveInfo.setHost_phone(homeListBeen.getHost().getPhone());
                        CurLiveInfo.setMembers(Integer.parseInt(homeListBeen.getWatchCount()) + 1); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(homeListBeen.getAdmireCount()));
                        CurLiveInfo.setAddress(homeListBeen.getLbs().getAddress());
                        CurLiveInfo.setTitle(homeListBeen.getTitle());
                        context.startActivity(intent);
                    }//进入直播间点击事件
                }
            });

        }

        @Override
        public int getItemCount() {
            return list_search.size();
        }

        private class SearchHolder extends RecyclerView.ViewHolder {
            ImageView isLive, iv_follow;
            SimpleDraweeView avatar;
            ImageView largeImgs;
            TextView tv_nick, tv_mlz, num;


            public SearchHolder(View itemView) {
                super(itemView);
                initView(itemView);
            }

            private void initView(View itemView) {
                iv_follow = itemView.findViewById(R.id.iv_follow);
                isLive = itemView.findViewById(R.id.iv_isLives);
                tv_nick = itemView.findViewById(R.id.nick);
                tv_mlz = itemView.findViewById(R.id.mlz);
                num = itemView.findViewById(R.id.Num);
                avatar = itemView.findViewById(R.id.avatar);
                largeImgs = itemView.findViewById(R.id.rciv_largeImgs);
            }
        }
    }
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            //if(parent.getChildPosition(view) != 0)
            outRect.top = space;
        }
    }
}

package com.example.project.limolive.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.RankBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentim.widget.CircleImageView;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * Created by AAA on 2017/8/24.
 */

public class CommentsActivity extends BaseActivity{
    private RecyclerView rv_com;
    private ArrayList<RankBean> com_list;
    private ComAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initView();
        initData();
    }

    private void initData() {
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }
        Api.comment_list(LoginManager.getInstance().getUserID(this), getIntent().getStringExtra("goods_id"), new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("评论","apiResponse="+apiResponse.getData());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    String data = apiResponse.getData();
                    com_list.addAll(JSONArray.parseArray(data,RankBean.class));
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort(CommentsActivity.this, apiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(String errMessage) {
                ToastUtils.showShort(CommentsActivity.this, errMessage);
                super.onFailure(errMessage);
            }
        });
    }

    private void initView() {
        com_list=new ArrayList<>();
        adapter=new ComAdapter(this,com_list);
        rv_com= (RecyclerView) findViewById(R.id.rv_com);
        rv_com.setLayoutManager(new LinearLayoutManager(this));
        rv_com.setAdapter(adapter);

    }
    class ComAdapter extends RecyclerView.Adapter{
        private Context context;

        private List<RankBean> list;

        public ComAdapter(Context context, List<RankBean> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Com_Holder(View.inflate(context,R.layout.item_comment,null)) ;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
            RankBean rankBean = com_list.get(position);
            Com_Holder holder= (Com_Holder) holder1;
            holder.tv_comment.setText(rankBean.getContent());
            holder.tv_userName.setText(rankBean.getUsername());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
    class Com_Holder extends RecyclerView.ViewHolder{
        CircleImageView civ_avatar;
        TextView tv_userName,tv_comment;

        public Com_Holder(View itemView) {
            super(itemView);
            civ_avatar=itemView.findViewById(R.id.civ_avatar);
            tv_userName=itemView.findViewById(R.id.tv_userName);
            tv_comment=itemView.findViewById(R.id.tv_comment);
        }
    }

}

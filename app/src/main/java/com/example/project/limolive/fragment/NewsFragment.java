package com.example.project.limolive.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.FrendInfoActivity;
import com.example.project.limolive.activity.FrendInfoSettingActivity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.FrendUserInfo;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.tencentim.adapters.ConversationAdapter;
import com.example.project.limolive.tencentim.model.Conversation;
import com.example.project.limolive.tencentim.model.CustomMessage;
import com.example.project.limolive.tencentim.model.FriendInfoBean;
import com.example.project.limolive.tencentim.model.FriendshipConversation;
import com.example.project.limolive.tencentim.model.MessageFactory;
import com.example.project.limolive.tencentim.model.NomalConversation;
import com.example.project.limolive.tencentim.presenter.ConversationPresenter;
import com.example.project.limolive.tencentim.presenter.FriendshipManagerPresenter;
import com.example.project.limolive.tencentim.ui.ChatActivity;
import com.example.project.limolive.tencentim.ui.ConversationActivity;
import com.example.project.limolive.tencentim.utils.PushUtil;
import com.example.project.limolive.tencentim.viewfeatures.ConversationView;
import com.example.project.limolive.tencentim.viewfeatures.FriendshipMessageView;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMFriendFutureItem;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.example.project.limolive.R.id.iv_hostHead;

/**
 * Created by AAA on 2017/8/17.
 */

public class NewsFragment extends BaseFragment  implements ConversationView,FriendshipMessageView {

    private final String TAG = "ConversationActivity";

    private View view;
    private List<Conversation> conversationList = new LinkedList<>();
    private List<FriendInfoBean> friendInfoBeens;//好友列表
    private ConversationAdapter adapter;
    private ListView listView;
    private ConversationPresenter presenter;
    private FriendshipManagerPresenter friendshipManagerPresenter;
    //  private GroupManagerPresenter groupManagerPresenter;
    private List<String> groupList;
    private FriendshipConversation friendshipConversation;
    // private GroupManageConversation groupManageConversation;
    private AutoSwipeRefreshLayout swipe_refresh_tool;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_contacts,inflater,container);
    }

    @Override
    protected void initView() {
        super.initView();

        //rv_contacts= (RecyclerView) findViewById(R.id.rv_contacts);
        listView = (ListView) findViewById(R.id.list);

        swipe_refresh_tool = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_refresh_tool);
        swipe_refresh_tool.autoRefresh();
        initData();
        adapter = new ConversationAdapter(getContext(), R.layout.item_conversation, conversationList,friendInfoBeens);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conversation conversation = conversationList.get(position);

                FriendInfoBean friend = new FriendInfoBean();
                friend.setPhone(conversation.getIdentify());

                Conversation data = adapter.getItem(position);
                FriendInfoBean b = new FriendInfoBean(data.getIdentify());
                Intent intent=null;
                if (friendInfoBeens.contains(b)){
                    int i = friendInfoBeens.indexOf(b);
                    FriendInfoBean friendInfoBean = friendInfoBeens.get(i);
                    intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("identify", friendInfoBean.getPhone());
                    intent.putExtra("type", TIMConversationType.C2C);
                    intent.putExtra("name", friendInfoBean.getNickname());
                    intent.putExtra("headsmall", friendInfoBean.getHeadsmall());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("friendInfoBeen", friendInfoBean);
                    intent.putExtras(bundle);
                }
                conversation.navToDetail(getContext(),intent);


                   /* if (conversationList.get(position) instanceof GroupManageConversation) {
                        groupManagerPresenter.getGroupManageLastMessage();
                    }*/

            }
        });
        friendshipManagerPresenter = new FriendshipManagerPresenter(this);
        presenter = new ConversationPresenter(this);
        presenter.getConversation();
        registerForContextMenu(listView);

        swipe_refresh_tool.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFriendLists();
            }
        });
        swipe_refresh_tool.setColorSchemeColors(Color.parseColor("#31D5BA"));
    }

    private void initData() {
        friendInfoBeens=new ArrayList<>();
        getFriendLists();
    }
    /**
     * 好友列表
     */
    private void getFriendLists() {
        Log.i("获取好友列表", LoginManager.getInstance().getUserID(getActivity()));
        Api.getFriendLists(LoginManager.getInstance().getUserID(getActivity()), new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("获取好友列表", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    List<FriendInfoBean> list = JSON.parseArray(apiResponse.getData(), FriendInfoBean.class);
                    friendInfoBeens.clear();
                    friendInfoBeens.addAll(list);
                    adapter.notifyDataSetChanged();
                    swipe_refresh_tool.setRefreshing(false);
                }else {
                    ToastUtils.showShort(getActivity(),apiResponse.getMessage());
                    if (apiResponse.getCode() == -2){
                    }
                    swipe_refresh_tool.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                swipe_refresh_tool.setRefreshing(false);
            }
        });
    }


    private void loadTitle() {
        setTitleString(getString(R.string.friends_conversation_list));
        setLeftImage(R.mipmap.icon_return);
    }
    @Override
    public void onResume(){
        super.onResume();
        refresh();
        PushUtil.getInstance().reset();
    }

    @Override
    public void initView(List<TIMConversation> conversationList) {
        this.conversationList.clear();
        groupList = new ArrayList<>();
        for (TIMConversation item:conversationList){
            switch (item.getType()){
                case C2C:
                case Group:
                    this.conversationList.add(new NomalConversation(item));
                    groupList.add(item.getPeer());
                    break;
            }
        }
        friendshipManagerPresenter.getFriendshipLastMessage();
        //  groupManagerPresenter.getGroupManageLastMessage();
    }

    @Override
    public void updateMessage(TIMMessage message) {
        if (message == null){
            adapter.notifyDataSetChanged();
            return;
        }
      /*  if (message.getConversation().getType() == TIMConversationType.System){
            groupManagerPresenter.getGroupManageLastMessage();
            return;
        }*/
        if (MessageFactory.getMessage(message) instanceof CustomMessage) return;
        NomalConversation conversation = new NomalConversation(message.getConversation());
        Iterator<Conversation> iterator =conversationList.iterator();
        while (iterator.hasNext()){
            Conversation c = iterator.next();
            if (conversation.equals(c)){
                conversation = (NomalConversation) c;
                iterator.remove();
                break;
            }
        }
        conversation.setLastMessage(MessageFactory.getMessage(message));
        conversationList.add(conversation);
        Collections.sort(conversationList);
        refresh();

    }

    @Override
    public void updateFriendshipMessage() {
        friendshipManagerPresenter.getFriendshipLastMessage();
    }

    @Override
    public void removeConversation(String identify) {
        Iterator<Conversation> iterator = conversationList.iterator();
        while(iterator.hasNext()){
            Conversation conversation = iterator.next();
            if (conversation.getIdentify()!=null&&conversation.getIdentify().equals(identify)){
                iterator.remove();
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void updateGroupInfo(TIMGroupCacheInfo info) {
        for (Conversation conversation : conversationList){
            if (conversation.getIdentify()!=null && conversation.getIdentify().equals(info.getGroupInfo().getGroupId())){
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void refresh() {
        Collections.sort(conversationList);
        adapter.notifyDataSetChanged();
       /* if (getActivity() instanceof  HomeActivity)
            ((HomeActivity) getActivity()).setMsgUnread(getTotalUnreadNum() == 0);*/

    }

    @Override
    public void onGetFriendshipLastMessage(TIMFriendFutureItem message, long unreadCount) {
        if (friendshipConversation == null){
            friendshipConversation = new FriendshipConversation(message);
            conversationList.add(friendshipConversation);
        }else{
            friendshipConversation.setLastMessage(message);
        }
        friendshipConversation.setUnreadCount(unreadCount);
        Collections.sort(conversationList);
        refresh();
    }

    @Override
    public void onGetFriendshipMessage(List<TIMFriendFutureItem> message) {
        friendshipManagerPresenter.getFriendshipLastMessage();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Conversation conversation = conversationList.get(info.position);
        if (conversation instanceof NomalConversation){
            menu.add(0, 1, Menu.NONE, getString(R.string.conversation_del));
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        NomalConversation conversation = (NomalConversation) conversationList.get(info.position);
        switch (item.getItemId()) {
            case 1:
                if (conversation != null){
                    if (presenter.delConversation(conversation.getType(), conversation.getIdentify())){
                        conversationList.remove(conversation);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
    private long getTotalUnreadNum(){
        long num = 0;
        for (Conversation conversation : conversationList){
            num += conversation.getUnreadNum();
        }
        return num;
    }
}

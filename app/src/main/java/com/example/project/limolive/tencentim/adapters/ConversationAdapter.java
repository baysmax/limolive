package com.example.project.limolive.tencentim.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.FrendInfoActivity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.FrendUserInfo;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentim.model.Conversation;
import com.example.project.limolive.tencentim.model.FriendInfoBean;
import com.example.project.limolive.tencentim.model.FriendProfile;
import com.example.project.limolive.tencentim.model.FriendshipInfo;
import com.example.project.limolive.tencentim.utils.TimeUtil;
import com.example.project.limolive.tencentim.widget.CircleImageView;
import com.example.project.limolive.tencentlive.utils.GlideCircleTransform;
import com.example.project.limolive.tencentlive.utils.SxbLog;
import com.example.project.limolive.tencentlive.utils.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * 会话界面adapter
 */
public class ConversationAdapter extends ArrayAdapter<Conversation> {

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;
    private Context context;
    private List<FriendInfoBean> friendInfoBeens;

    /**
     * Constructor
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ConversationAdapter(Context context, int resource, List<Conversation> objects, List<FriendInfoBean> friendInfoBeens) {
        super(context, resource, objects);
        resourceId = resource;
        this.context=context;
        this.friendInfoBeens=friendInfoBeens;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null){
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }else{
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.name);
            viewHolder.avatar = (CircleImageView) view.findViewById(R.id.avatar);
            viewHolder.lastMessage = (TextView) view.findViewById(R.id.last_message);
            viewHolder.time = (TextView) view.findViewById(R.id.message_time);
            viewHolder.unread = (TextView) view.findViewById(R.id.unread_num);
            view.setTag(viewHolder);
        }
        final Conversation data = getItem(position);
        viewHolder.tvName.setText(data.getName());
        viewHolder.avatar.setImageResource(data.getAvatar());
        viewHolder.lastMessage.setText(data.getLastMessageSummary());
        viewHolder.time.setText(TimeUtil.getTimeStr(data.getLastMessageTime()));
        loadInfo(data);
        long unRead = data.getUnreadNum();
        if (unRead <= 0){
            viewHolder.unread.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.unread.setVisibility(View.VISIBLE);
            String unReadStr = String.valueOf(unRead);
            if (unRead < 10){
                viewHolder.unread.setBackground(getContext().getResources().getDrawable(R.drawable.point1));
            }else{
                viewHolder.unread.setBackground(getContext().getResources().getDrawable(R.drawable.point2));
                if (unRead > 99){
                    unReadStr = getContext().getResources().getString(R.string.time_more);
                }
            }
            viewHolder.unread.setText(unReadStr);
        }
        return view;
    }

    private void loadInfo(final Conversation data) {
        FriendInfoBean b = new FriendInfoBean(data.getIdentify());
        if (friendInfoBeens.contains(b)){
            int i = friendInfoBeens.indexOf(b);
            FriendInfoBean friendInfoBean = friendInfoBeens.get(i);
            viewHolder.tvName.setText(friendInfoBean.getNickname());
            showHeadIcon(viewHolder.avatar,friendInfoBean.getHeadsmall());
        }
    }
    private void showHeadIcon(ImageView view, String avatar) {

        if (TextUtils.isEmpty(avatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avatar);
            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
            view.setImageBitmap(cirBitMap);
        } else {
            SxbLog.d(TAG, "load icon: " + avatar);

            RequestManager req = Glide.with(context);
            if (avatar.toString().contains("http://")) {
                req.load(avatar).transform(new GlideCircleTransform(context)).into(view);
                Log.i("主播头像", "微信avatar" + avatar);
            } else {
                req.load(ApiHttpClient.API_PIC + avatar).transform(new GlideCircleTransform(context)).into(view);
                Log.i("主播头像", "avatar" + avatar);
            }
        }
    }

    public class ViewHolder{
        public TextView tvName;
        public CircleImageView avatar;
        public TextView lastMessage;
        public TextView time;
        public TextView unread;

    }
}

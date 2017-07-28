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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentim.model.Message;
import com.example.project.limolive.tencentim.widget.CircleImageView;
import com.example.project.limolive.tencentlive.utils.GlideCircleTransform;
import com.example.project.limolive.tencentlive.utils.SxbLog;
import com.example.project.limolive.tencentlive.utils.UIUtils;

import java.util.List;

/**
 * 聊天界面adapter
 */
public class ChatAdapter extends ArrayAdapter<Message> {

    private final String TAG = "ChatAdapter";

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;
    private Context context;
    private String headsmall;//好友头像

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ChatAdapter(Context context, int resource, List<Message> objects,String headsmall) {
        super(context, resource, objects);
        resourceId = resource;
        this.context = context;
        this.headsmall = headsmall;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.leftMessage = (RelativeLayout) view.findViewById(R.id.leftMessage);
            viewHolder.rightMessage = (RelativeLayout) view.findViewById(R.id.rightMessage);
            viewHolder.leftPanel = (RelativeLayout) view.findViewById(R.id.leftPanel);
            viewHolder.rightPanel = (RelativeLayout) view.findViewById(R.id.rightPanel);
            viewHolder.sending = (ProgressBar) view.findViewById(R.id.sending);
            viewHolder.error = (ImageView) view.findViewById(R.id.sendError);
            viewHolder.sender = (TextView) view.findViewById(R.id.sender);
            viewHolder.rightDesc = (TextView) view.findViewById(R.id.rightDesc);
            viewHolder.systemMessage = (TextView) view.findViewById(R.id.systemMessage);
            viewHolder.leftAvatar = (CircleImageView) view.findViewById(R.id.leftAvatar);//别人头像
            viewHolder.rightAvatar = (CircleImageView) view.findViewById(R.id.rightAvatar);//自己头像

            showHeadIcon(viewHolder.rightAvatar, LoginManager.getInstance().getAvatar(context));
            showHeadIcon(viewHolder.leftAvatar, headsmall);

            view.setTag(viewHolder);
        }
        if (position < getCount()) {
            final Message data = getItem(position);
            data.showMessage(viewHolder, getContext());
        }
        return view;
    }


    public class ViewHolder {
        public RelativeLayout leftMessage;
        public RelativeLayout rightMessage;
        public RelativeLayout leftPanel;
        public RelativeLayout rightPanel;
        public ProgressBar sending;
        public ImageView error;
        public TextView sender;
        public TextView systemMessage;
        public TextView rightDesc;
        public CircleImageView rightAvatar;
        public CircleImageView leftAvatar;
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
}

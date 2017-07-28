package com.example.project.limolive.tencentlive.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.tencentlive.model.AvMemberInfo;
import com.example.project.limolive.tencentlive.utils.GlideCircleTransform;
import com.example.project.limolive.tencentlive.utils.UIUtils;

import java.util.List;


/**
 * 成员列表适配器
 */
public class MembersHeadAdapter extends BaseAdapter {

    private Context context;
    private List<AvMemberInfo> avMemberInfos;
    public MembersHeadAdapter(Context context,List<AvMemberInfo> avMemberInfos) {
        this.context = context;
        this.avMemberInfos = avMemberInfos;
    }

    @Override
    public int getCount() {
        return avMemberInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return avMemberInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.horizontal_head_item, null);
        ImageView iv_member_head = (ImageView) convertView.findViewById(R.id.iv_member_head);
        showHeadIcon(iv_member_head,avMemberInfos.get(position).getHeadsmall());
        return convertView;
    }

    private void showHeadIcon(ImageView view, String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avatar);
            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
            view.setImageBitmap(cirBitMap);
        } else {
            RequestManager req = Glide.with(context);
            if (avatar.contains("http://")){
                req.load(avatar).transform(new GlideCircleTransform(context)).into(view);
            }else {
                req.load(ApiHttpClient.API_PIC+avatar).transform(new GlideCircleTransform(context)).into(view);
            }
        }
    }
}

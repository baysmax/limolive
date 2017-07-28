package com.example.project.limolive.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.project.limolive.R;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.ShareBoardConfig;

public class UMShareUtils {
	private static UMShareUtils shareUtils = null;
	private ShareBoardConfig config;
	private UMShareListener umShareListener;

	public void init(final Activity activity) {
		umShareListener = new UMShareListener() {
			@Override
			public void onStart(SHARE_MEDIA share_media) {

			}

			@Override
			public void onResult(SHARE_MEDIA platform) {
				Log.d("plat", "platform" + platform);

				Toast.makeText(activity, "分享成功啦", Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onError(SHARE_MEDIA platform, Throwable t) {
				Toast.makeText(activity, "分享失败啦", Toast.LENGTH_SHORT).show();
				if (t != null) {
					Log.d("throw", "throw:" + t.getMessage());
				}
			}

			@Override
			public void onCancel(SHARE_MEDIA platform) {
				Toast.makeText(activity, "分享取消了", Toast.LENGTH_SHORT).show();
			}
		};
	}

	private UMShareUtils() {
	}

	public static synchronized UMShareUtils getInstance() {
		if (shareUtils == null)
			shareUtils = new UMShareUtils();
		return shareUtils;
	}

	public ShareBoardConfig getConfig() {
		return config;
	}
	
	public UMShareListener umShareListener(){
		return umShareListener;
	}

	public void configShareBoard() {
		config = new ShareBoardConfig();
		config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
		config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
		config.setCancelButtonVisibility(true);
		config.setTitleText("柠檬直播");
		config.setTitleTextColor(R.color.table_text_select);
		config.setMenuItemIconPressedColor(R.color.font_gray);
	}
}

package com.example.project.limolive.tencentlive.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {

	private static SharedPreferences mSharedPreferences;

	/** 配置名称 **/
	private static final String GREEN_WEB_SETTINGS = "config";

	private static SPUtil mSPUtil;

	private SPUtil() {
	}

	public static SPUtil getInstance(Context context) {
		if (mSPUtil == null) {
			mSPUtil = new SPUtil();
		}
		if (mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(
					GREEN_WEB_SETTINGS, Context.MODE_PRIVATE);
		}
		return mSPUtil;
	}

	public void setIsLogin(boolean islogin) {
		mSharedPreferences.edit().putBoolean("islogin", islogin).commit();
	}

	public boolean getIsLogin() {
		boolean islogin = mSharedPreferences.getBoolean("islogin", false);
		return islogin;
	}

	public void setUserId(String id) {
		mSharedPreferences.edit().putString("user_id", id).commit();
	}

	public String getUserId() {
		return mSharedPreferences.getString("user_id", "");
	}

	public String getPresentNum() {
		return mSharedPreferences.getString("presentNum", "");
	}

	public void setPresentNum(String presentNum) {
		mSharedPreferences.edit().putString("presentNum", presentNum).commit();
	}

	// 保存json
	public String getString(String key) {

		return mSharedPreferences.getString(key, "");
	}

	public void setString(String key, String value) {

		mSharedPreferences.edit().putString(key, value).commit();
	}

}

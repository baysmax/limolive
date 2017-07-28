package com.example.project.limolive.wxapi;


import android.content.Intent;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.weixin.view.WXCallbackActivity;


public class WXEntryActivity extends WXCallbackActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}

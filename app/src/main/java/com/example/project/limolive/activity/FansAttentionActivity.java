package com.example.project.limolive.activity;

import android.os.Bundle;

import com.example.project.limolive.R;
import com.example.project.limolive.fragment.AttentionFragment;
import com.example.project.limolive.fragment.FansFragment;

/**
 * 粉丝和关注
 * @author hwj on 2016/12/19.
 */
public class FansAttentionActivity extends BaseActivity {

    public static final int FANS=0;
    public static final int ATTENTIONS=1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans_attention);

        switch(getIntent().getIntExtra("fansOrAttention",FANS)){
            case FANS:
                loadFansFragment();
                break;
            case ATTENTIONS:
                loadAttentionFragment();
                break;
        }
    }

    /**
     * 加载关注
     */
    private void loadAttentionFragment() {
        AttentionFragment attentionFragment=new AttentionFragment();
        loadFragment(attentionFragment,R.id.fragment_container);
    }

    /**
     * 加载粉丝
     */
    private void loadFansFragment() {
        FansFragment fansFragment=new FansFragment();
        loadFragment(fansFragment,R.id.fragment_container);
    }

}

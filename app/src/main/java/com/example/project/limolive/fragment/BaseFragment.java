package com.example.project.limolive.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.limolive.LiveMallApplication;
import com.example.project.limolive.R;
import com.example.project.limolive.widget.LiveMallTitleBar;

/**
 * Created by Administrator on 2016/12/13.
 */

public class BaseFragment extends Fragment {

    protected View root;
    /**
     * 顶部标题栏
     */
    protected LiveMallTitleBar title_bar_standard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected View setContentView(int layout,LayoutInflater inflater,
                                  ViewGroup container){
        root=inflater.inflate(layout,container,false);
        initView();
        return root;
    }

    protected View findViewById(int viewRes){
        return root.findViewById(viewRes);
    }

    protected void initView(){
        title_bar_standard= (LiveMallTitleBar) findViewById(R.id.title_bar_standard);
    }

    /**
     * 设置标题
     * @param titleString
     */
    protected void setTitleString(String titleString){
        if(title_bar_standard!=null){
            title_bar_standard.setTitle(titleString);
        }
    }
    protected void setTitleString(int resString){
        if(title_bar_standard!=null){
            title_bar_standard.setTitle(getString(resString));
        }
    }

    /**
     * 配置图片控件
     * @param resImg
     */
    protected void setLeftImage(int resImg){
        if(title_bar_standard!=null){
            title_bar_standard.setLeftImageResource(resImg);
        }
    }
    protected void setRightImage(int resImg){
        if(title_bar_standard!=null){
            title_bar_standard.setRightImageResource(resImg);
        }
    }
    protected void setLeftText(String leftText){
        if(title_bar_standard!=null){
            title_bar_standard.setLeftText(leftText);
        }
    }
    protected void setRightText(String rightText){
        if(title_bar_standard!=null){
            title_bar_standard.setRightText(rightText);
        }
    }


    /**
     * 设置左右两边控件点击事件
     * @param clickListener
     */
    protected void setLeftRegionListener(View.OnClickListener clickListener){
        if(title_bar_standard!=null){
            title_bar_standard.setLeftLayoutClickListener(clickListener);
        }
    }
    protected void setRightRegionListener(View.OnClickListener clickListener){
        if(title_bar_standard!=null){
            title_bar_standard.setRightLayoutClickListener(clickListener);
        }
    }

    /**
     * 获取Application
     * @return
     */
    public LiveMallApplication getApplication(){
        return LiveMallApplication.getApplication();
    }

}

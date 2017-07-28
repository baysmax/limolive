package com.example.project.limolive.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.project.limolive.R;
import com.example.project.limolive.helper.ActivityHelper;
import com.example.project.limolive.presenter.PersonInfoPresenter;

/**
 * 修改职业、用户名等信息的Fragment
 * @author hwj on 2016/12/17.
 */

public class ChangeSimpleInfoFragment extends BaseFragment implements View.OnClickListener{

    private PersonInfoPresenter presenter;

    public static final int SIMPLE_INFO_JOB=1;
    public static final int SIMPLE_INFO_USER_NAME=2;
    public static final String SIMPLE_INFO_TEXT="simple_info";
    private int currentType;

    private EditText edit_change_info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_change_simple,inflater, container);
    }

    @Override
    protected void initView() {
        super.initView();
        presenter=new PersonInfoPresenter(getActivity());
        edit_change_info= (EditText) findViewById(R.id.edit_change_info);
        loadTitle();
    }

    private void loadTitle() {
        setLeftText(getString(R.string.cancel));
        setRightText(getString(R.string.save));
        Bundle bundle=getArguments();
        currentType=bundle.getInt(SIMPLE_INFO_TEXT,SIMPLE_INFO_JOB);
        switch(currentType){
            case SIMPLE_INFO_JOB:
                setTitleString(getString(R.string.change_info_simple_job));
                edit_change_info.setHint(getString(R.string.change_info_simple_job_hint));
                break;
            case SIMPLE_INFO_USER_NAME:
                setTitleString(getString(R.string.change_info_simple_username));
                edit_change_info.setHint(getString(R.string.change_info_simple_username_hint));
                break;
        }
        setRightRegionListener(this);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(currentType){
            case SIMPLE_INFO_JOB:
                presenter.changeJob(edit_change_info.getText().toString().trim(),handler);
                break;
            case SIMPLE_INFO_USER_NAME:
                presenter.changeUserName(edit_change_info.getText().toString().trim(),handler);
                break;
        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case PersonInfoPresenter.HEAD_UPDATE_JOB: //修改完职业的回调
                    getActivity().finish();
                    break;
                case PersonInfoPresenter.HEAD_UPDATE_USERNAME: //修改完用户名的回调
                    getActivity().finish();
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        ActivityHelper.getInstance().clearHandlerMessage(handler);
    }
}

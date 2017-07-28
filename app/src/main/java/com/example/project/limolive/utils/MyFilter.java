package com.example.project.limolive.utils;

import android.widget.Filter;

import com.example.project.limolive.tencentim.model.FriendInfoBean;
import com.example.project.limolive.sidebar.BMJLetterListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：hpg on 2017/1/5 14:35
 * 功能：
 */
public class  MyFilter extends Filter {
    List<FriendInfoBean> friendInfoBeans = null;
    private boolean notiyfyByFilter;
    private MyFilter myFilter;
    private BMJLetterListAdapter adapter;
    public MyFilter(List<FriendInfoBean> myList,BMJLetterListAdapter adapter) {
        this.friendInfoBeans = myList;
        this.adapter = adapter;
    }
    public Filter getFilter() {
        if(myFilter==null){
            myFilter = new MyFilter(friendInfoBeans,adapter);
        }
        return myFilter;
    }

    public Filter getNewFilter(){
        friendInfoBeans.clear();
        friendInfoBeans.addAll(friendInfoBeans);
        if(myFilter==null){
            myFilter = new MyFilter(friendInfoBeans,adapter);
        }
        return myFilter;
    }
    protected synchronized FilterResults performFiltering(CharSequence prefix) {
        FilterResults results = new FilterResults();
        if(friendInfoBeans==null){
            friendInfoBeans = new ArrayList<FriendInfoBean>();
        }

        if(prefix==null || prefix.length()==0){
            results.values = friendInfoBeans;
            results.count = friendInfoBeans.size();
        }else{
            String prefixString = prefix.toString();
            final int count = friendInfoBeans.size();
            final ArrayList<FriendInfoBean> newValues = new ArrayList<FriendInfoBean>();
            for(int i=0;i<count;i++){
                final FriendInfoBean user = friendInfoBeans.get(i);
                String username = user.getNickname();

                if(username.startsWith(prefixString)){
                    newValues.add(user);
                }
                else{
                    final String[] words = username.split(" ");
                    final int wordCount = words.length;

                    // Start at index 0, in case valueText starts with space(s)
                    for (String word : words) {
                        if (word.startsWith(prefixString)) {
                            newValues.add(user);
                            break;
                        }
                    }
                }
            }
            results.values=newValues;
            results.count=newValues.size();
        }
        return results;
    }

    protected synchronized void publishResults(CharSequence constraint,
                                               FilterResults results) {
        friendInfoBeans.clear();
        friendInfoBeans.addAll((List<FriendInfoBean>)results.values);
        if (results.count > 0) {
            notiyfyByFilter = true;
            adapter.notifyDataSetChanged();
            notiyfyByFilter = false;
        } else {
            adapter.notifyDataSetInvalidated();
        }
    }
}

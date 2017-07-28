package com.example.project.limolive.sidebar;

import android.util.Log;
import android.util.SparseArray;

import com.example.project.limolive.tencentim.model.FriendInfoBean;

import java.util.Comparator;

/**
 * 作用:取第一个字的拼音进行比较排序
 *
 * @author LITP
 * @date 2016/11/10
 */

public class PinYinComparator implements Comparator<FriendInfoBean> {

    //已经存在的首字母列表
    private SparseArray<Character> letters = new SparseArray<>();

    @Override
    public int compare(FriendInfoBean lhs, FriendInfoBean rhs) {
        int c1 =0;
        int c2 =0;
        if (PinYinUtils.getFirstPiniYin(lhs.getNickname()).length()>0){
            c1 = PinYinUtils.getFirstPiniYin(lhs.getNickname()).charAt(0);
        }

        if (PinYinUtils.getFirstPiniYin(rhs.getNickname()).length()>0){
            c2 = PinYinUtils.getFirstPiniYin(rhs.getNickname()).charAt(0);
        }
      /*  int c1 = PinYinUtils.getFirstPiniYin(lhs.getNickname()).charAt(0);
        int c2 = PinYinUtils.getFirstPiniYin(rhs.getNickname()).charAt(0);*/

        Log.i("c1",c1+"c1");
        Log.i("c1",c2+"c2");

        //判断是否已经存在了首字母了
        if (letters.get(c1) == null) {
            //判断是不是字母
            if (Character.isLetter((char) c1)) {
                letters.put(c1,(char) c1);

                //重点，设置首字母标识
                lhs.setLetter((char) c1 + "");
            } else { //设置#标识
                letters.put('#', (char) c1);
                lhs.setLetter("#");
            }
        }

        //c1 不为字母
        if ((c1 < 65)) {
            c1 += 90;   // 使其在后面
        }
        //c2 不为字母
        if ((c2 < 65)) {
            c2 += 90;   // 使其在后面
        }

        return c1 - c2;   //负数则第一个参数在前面，
    }

}

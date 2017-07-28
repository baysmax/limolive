package com.example.project.limolive.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;

import com.example.project.limolive.utils.datepicker.DatePickerDialog;
import com.example.project.limolive.utils.datepicker.DateUtil;
import com.example.project.limolive.widget.ChangeDialog;
import com.example.project.limolive.widget.address.CityPicker;

import java.util.List;


/**
 * 用于创建Dialog相关对象
 * @author hwj on 2016/12/14.
 */

public class DialogFactory {

    public static ProgressDialog getDefualtProgressDialog(Activity activity, String msg){
        ProgressDialog dialog=new ProgressDialog(activity);
        dialog.setMessage(msg);
        return dialog;
    }
    public static ProgressDialog getDefualtProgressDialog(Activity activity){
        return new ProgressDialog(activity);
    }

    /**
     * 获取从底部弹出的选择Dialog
     * @param activity context
     * @param type 传入需要显示的类型 1为图片模式 2位选择性别模式
     * @return
     */
    public static ChangeDialog getBottomSelectDialog(Activity activity,int type){
        return new ChangeDialog(activity,type);
    }

    /**
     * 获取默认选择dialog 默认为选择图片类型
     * @param activity context
     * @return
     */
    public static ChangeDialog getDefaultSelectDialog(Activity activity){
        return new ChangeDialog(activity);
    }

    /**
     * 获取城市三级联动列表Dialog（此控件为一个pop）
     * @param activity
     * @return
     */
    public static CityPicker getCityPickerDialog(Activity activity){
        CityPicker cityPicker = new CityPicker.Builder(activity).textSize(20)
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#FF5740")
                .cancelTextColor("#FF5740")
                .province("北京市")
                .city("北京市")
                .district("朝阳区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .build();
        return cityPicker;
    }

    /**
     * 获取时间选择器滚轮
     * @param activity
     * @param listener 选择器的监听，需实现此方法
     * @return
     */
    public static DatePickerDialog getDatePickerDialog(Activity activity
            ,DatePickerDialog.OnDateSelectedListener listener){
        List<Integer> list = DateUtil.getDateForString("1990-01-01");
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(activity);
        builder.setOnDateSelectedListener(listener)
                .setSelectYear(list.get(0) - 1)
                .setSelectMonth(list.get(1) - 1)
                .setSelectDay(list.get(2) - 1);
        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        return builder.create();
    }



}

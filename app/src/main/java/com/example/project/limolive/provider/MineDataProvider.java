package com.example.project.limolive.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.example.project.limolive.model.LoginModel;

import static com.example.project.limolive.provider.PersonDataHelper.Tables.MINE_INFO;

/**
 * 获取我的个人信息
 * <p>查询和插入统一按phone进行查询，phone会缓存到本地share中</p>
 * <p>取消了单例模式的设计</p>
 * @author hwj on 2016/12/13.
 */

public class MineDataProvider extends DataProvider{


    //表名
    private String tableName;

    public MineDataProvider(Context context){
        super(context);
        tableName=MINE_INFO;
    }

    /**
     * 添加个人数据
     */
    public synchronized void addMineData(LoginModel bean){
        if(getDataNumber(tableName)>1){ //数据量大于1删除首行数据
            delete(selectFirstId());
        }
        ContentValues values=importValues(bean);
        if(values!=null){
            addSimpleData(tableName,values);
        }
    }

    /**
     * 更新本地用户数据
     */
    public synchronized void updateMineData(LoginModel bean){
        ContentValues values=importValues(bean);
        if(values!=null){
            updateData(tableName,values,"phone=?",new String[]{bean.getPhone()});
        }
    }

    /**
     * 修改单条信息
     * @param values 相关消息
     * @param phone  用户手机号码
     */
    public synchronized void updateMineData(ContentValues values,String phone){
        if(values!=null&&!TextUtils.isEmpty(phone)){
            updateData(tableName,values,"phone=?",new String[]{phone});
        }
    }


    /**
     * 删除单条数据数据
     */
    public synchronized void removeSimpleData(String phone){
        if(!TextUtils.isEmpty(phone)){
            deleteSimpleData(tableName,"phone=?",new String[]{phone});
        }
    }

    /**
     * 根据提供的key查询单个信息
     * @param phone 用户手机号
     * @param key 需要查询的字段
     * @return
     */
    public synchronized String getMineInfo(String phone,String key){
        if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(key)){
            Cursor cursor=queryData(tableName,new String[]{key},"phone=?", new String[]{phone},null,
                    null,null,null);
            if(cursor!=null&&cursor.moveToFirst()){
                String info=cursor.getString(cursor.getColumnIndex(key));
                if(!cursor.isClosed()){
                    cursor.close();
                }
                return info;
            }
        }
        return null;
    }

    /**
     * 查询用户数据
     * @return
     */
    public synchronized LoginModel getMineInfo(String phone){
        if(!TextUtils.isEmpty(phone)){
            Cursor cursor=queryData(tableName,null,"phone=?",new String[]{phone},null,null,null,null);
            if(cursor!=null&&cursor.moveToFirst()){
                LoginModel bean=new LoginModel(
                        cursor.getString(cursor.getColumnIndex("uid")),
                        cursor.getString(cursor.getColumnIndex("phone")),
                        cursor.getString(cursor.getColumnIndex("password")),
                        cursor.getString(cursor.getColumnIndex("nickname")),
                        cursor.getString(cursor.getColumnIndex("livenum")),
                        cursor.getString(cursor.getColumnIndex("headsmall")),
                        cursor.getString(cursor.getColumnIndex("sex")),
                        cursor.getString(cursor.getColumnIndex("personalized")),
                        cursor.getString(cursor.getColumnIndex("birthday")),
                        cursor.getString(cursor.getColumnIndex("love_status")),
                        cursor.getString(cursor.getColumnIndex("money")),
                        cursor.getString(cursor.getColumnIndex("province")),
                        cursor.getString(cursor.getColumnIndex("city")),
                        cursor.getString(cursor.getColumnIndex("job")),
                        cursor.getString(cursor.getColumnIndex("verify")),
                        cursor.getString(cursor.getColumnIndex("real_name")),
                        cursor.getString(cursor.getColumnIndex("idcard")),
                        cursor.getString(cursor.getColumnIndex("create_time")),
                        cursor.getString(cursor.getColumnIndex("store_name")),
                        cursor.getString(cursor.getColumnIndex("sig"))
                );
                if(!cursor.isClosed()){
                    cursor.close();
                }
                return bean;
            }
        }
        return null;
    }

    /**
     * 删除数据，设为私有权限，内部调用自动删除
     */
    private void delete(int id){
        deleteSimpleData(tableName,
                "_id=?",new String[]{String.valueOf(id)});
    }

    /**
     * 查询首行ID
     * @return
     */
    private int selectFirstId(){
        return getFirstId(tableName);
    }


    private ContentValues importValues(LoginModel bean){
        if(bean!=null) {
            ContentValues values = new ContentValues();
            values.put("uid", bean.getUid());
            values.put("phone", bean.getPhone());
            values.put("password", bean.getPassword());
            values.put("nickname", bean.getNickname());
            values.put("livenum", bean.getLivenum());
            values.put("headsmall", bean.getHeadsmall());
            values.put("sex", bean.getSex());
            values.put("personalized", bean.getPersonalized());
            values.put("birthday", bean.getBirthday());
            values.put("love_status", bean.getLove_status());
            values.put("money", bean.getMoney());
            values.put("province", bean.getProvince());
            values.put("city", bean.getCity());
            values.put("job", bean.getJob());
            values.put("verify", bean.getVerify());
            values.put("real_name", bean.getReal_name());
            values.put("idcard", bean.getIdcard());
            values.put("create_time", bean.getCreate_time());
            values.put("store_name", bean.getStore_name());
            values.put("sig", bean.getSig());
            return values;
        }
        return null;
    }

}

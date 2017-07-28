package com.example.project.limolive.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 查询相关表的类
 * @author hwj on 2016/12/13.
 */

public abstract class DataProvider {

    private static final String TAG="DataProvider";


    private SQLiteDatabase database;
    private PersonDataHelper helper;
    private Context context;

    public DataProvider(Context context){
        this.context=context;
        helper=PersonDataHelper.getInstance(context);
        database=helper.getReadableDatabase();
    }

    /**
     * 删除所有表
     * 直接调用helper的deleteAllData
     */
    public void deleteAllTables(){
        helper.deleteAllTable();
    }


    /**
     * 插入单条数据
     * @param table
     * @param values
     */
    public void addSimpleData(String table,ContentValues values){
        long result=database.insert(table,null,values);
        if(result==-1){
            Log.i(TAG,"addSimpleData is fail");
        }
    }

    /**
     * 删除单条信息
     * @param table
     * @param whereClause
     * @param args
     */
    public void deleteSimpleData(String table,String whereClause,String args[]){
        int result=database.delete(table,whereClause,args);
        Log.i(TAG,"delete "+result+" row suc");
    }

    /**
     * 修改
     * @param table
     * @param values
     * @param whereClause
     * @param args
     */
    public void updateData(String table,ContentValues values,String whereClause,String args[]){
        int result=database.update(table,values,whereClause,args);
        Log.i(TAG,"update "+result+" row suc");
    }


    /**
     * 查询
     * @param table
     * @param columes
     * @param selection
     * @param selectArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @return
     */
    public Cursor queryData(String table, String columes[], String selection,
                            String selectArgs[], String groupBy, String having, String orderBy, String limit){
        return database.query(table,columes,selection,selectArgs,groupBy,having,orderBy,limit);
    }

    /**
     * 获取表数据的数量
     * @param table
     * @return
     */
    public long getDataNumber(String table){
        Cursor cursor=database.rawQuery("SELECT count(*) from "+table,null);
        if(cursor.moveToFirst()){
            long re=cursor.getLong(0);
            cursor.close();
            return re;
        }
        return 0;
    }

    /**
     * 查询首行ID
     * @param table
     * @return
     */
    public int getFirstId(String table){
        Cursor cursor=database.query(table,null,"_id",null,null,null,null,"0,1");
        if(cursor.moveToFirst()){
            int re=cursor.getInt(0);
            cursor.close();
            return re;
        }
        return -1;
    }

    /**
     * 利用sql语句查询数据
     * @param sql
     * @return
     */
    public Cursor selectData(String sql,String args[]){
        return database.rawQuery(sql,args);
    }


    /**
     * 重建表
     */
    public void reBuildData(){
        helper.reCreate();
    }


}

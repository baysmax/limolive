package com.example.project.limolive.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 个人数据
 * @author hwj on 2016/12/13.
 */

public class PersonDataHelper extends SQLiteOpenHelper {

    public static int VERSION=1;
    //保存一些本数据库包含的sql语句，建表，删除等
    public interface Tables{


        //个人信息数据库名称
        String PERSON_INFO="person_info.db";

        /*1. 个人信息 表名*/
        String MINE_INFO = "mine_info";
        //字段
        String UID = "uid";
        String PHONE = "phone";
        String PASSWORD = "password";
        String NICKNAME = "nickname";
        String LIVENUM = "livenum";
        String HEADSMALL = "headsmall";
        String SEX = "sex";
        String PERSONALIZED = "personalized";
        String BIRTHDAY = "birthday";
        String LOVE_STATUS = "love_status";
        String MONEY = "money";
        String PROVINCE = "province";
        String CITY = "city";
        String JOB = "job";
        String VERIFY = "verify";
        String REAL_NAME = "real_name";
        String IDCARD = "idcard";
        String CREATE_TIME = "create_time";
        String STORE_NAME = "store_name";
        String SIG = "sig";
        String CREATE_PERSON="CREATE TABLE IF NOT EXISTS " + MINE_INFO + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UID + " VARCHAR,"
                + PHONE + " VARCHAR,"
                + PASSWORD + " VARCHAR,"
                + NICKNAME + " VARCHAR,"
                + LIVENUM + " VARCHAR,"
                + HEADSMALL + " VARCHAR,"
                + SEX + " VARCHAR,"
                + PERSONALIZED + " VARCHAR,"
                + BIRTHDAY + " VARCHAR,"
                + LOVE_STATUS + " VARCHAR,"
                + MONEY + " VARCHAR,"
                + PROVINCE + " VARCHAR,"
                + CITY + " VARCHAR,"
                + JOB + " VARCHAR,"
                + VERIFY + " VARCHAR,"
                + REAL_NAME + " VARCHAR,"
                + IDCARD + " VARCHAR,"
                + CREATE_TIME + " VARCHAR,"
                + STORE_NAME + " VARCHAR,"
                + SIG + " VARCHAR"
                +
                ")";

        //删除本地用户登录信息表
        String DELETETABLE="DROP TABLE IF EXISTS ";

        enum DefineTableName{
            MINE_INFO(Tables.MINE_INFO);
            String tableName;
            DefineTableName(String tableName){
                this.tableName=tableName;
            }
        }

    }

    private static PersonDataHelper instance;

    private PersonDataHelper(Context context) {
        super(context, Tables.PERSON_INFO, null, VERSION);
    }

    public static PersonDataHelper getInstance(Context context){
        if(instance==null){
            instance=new PersonDataHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDatabase(db);
    }

    private void createDatabase(SQLiteDatabase db) {
        db.execSQL(Tables.CREATE_PERSON); //创建本地登录用户列表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 删除全部表
     */
    public void deleteAllTable(){
        SQLiteDatabase db=getReadableDatabase();
        for(Tables.DefineTableName tables: Tables.DefineTableName.values()){
            db.execSQL(Tables.DELETETABLE+tables.tableName);
        }
    }

    /**
     * 重新创建
     */
    public void reCreate(){
        SQLiteDatabase db=getReadableDatabase();
        onCreate(db);
    }
}

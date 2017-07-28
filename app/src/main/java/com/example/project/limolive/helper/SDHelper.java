package com.example.project.limolive.helper;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * @author hwj on 2016/12/15.
 * 用于管理SD卡工具
 */

public class SDHelper {

    private static final String TAG="SDHelper";
    private static volatile SDHelper sdHelper=null;
    /**
     * 根目录名称
     */
    private static final String rootFolderName="liveMall";

    public static SDHelper getSDHelper(){
        if(sdHelper==null){
            synchronized (SDHelper.class){
                if(sdHelper==null){
                    sdHelper=new SDHelper();
                }
            }
        }
        return sdHelper;
    }

    private SDHelper(){
    }

    /**
     * 初始化,初始化即为创建一个根目录，6.0以上设备需要申请权限
     * @param context
     */
    public void init(Context context){
        //申请读写SD卡
        if(PermissionHelper.getInstance().requestSDCardPermission(context)){
            //初始化即创建根目录
            createRootFolder();
        }
    }

    /**
     * 初始化创建根目录文件夹
     */
    private void createRootFolder() {
        String path= Environment.getExternalStorageDirectory().getPath()+File.separator
                +rootFolderName;
        File file=new File(path);
        if(!file.exists()||!file.isDirectory()){
            boolean isSuccess=file.mkdir();
            Log.i(TAG,"Create folder "+isSuccess);
        }else{
            Log.i(TAG,"The folder exist");
        }
    }

    /**
     * 删除根目录
     */
    public void deleteRootFolder(){
        String path= Environment.getExternalStorageDirectory().getPath()+File.separator
                +rootFolderName;
        File file=new File(path);
        if(file.exists()&&file.isDirectory()){
            deleteFiles(file.listFiles());
            boolean delete=file.delete();
            Log.i(TAG,"Delete "+delete);
        }else{
            Log.i(TAG,"The file not exist! ");
        }
    }

    /**
     * 递归删除文件夹
     * @param files 文件夹
     */
    private void deleteFiles(File []files){
        if(files!=null){
            for(File f:files){
                if(f.isFile()){
                    f.delete();
                }else if(f.isDirectory()){
                    deleteFiles(f.listFiles());
                    f.delete();
                }
            }
        }
    }

    /**
     * 重新创建文件夹
     * <p>首先要删除文件，然后创建文件夹</p>
     */
    public void reCreateRootFolder(){
        deleteRootFolder();
        createRootFolder();
    }

    /**
     * 获取拍照路径
     * @return
     */
    public String getCameraPath(){
        return Environment.getExternalStorageDirectory().getPath()+File.separator
                +rootFolderName;
    }



}

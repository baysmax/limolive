package com.example.project.limolive.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 使用v4兼容包进行处理,针对android6.0API以及以上的版本
 * 调用本方法需要在Activity的权限回调中确认
 * <p>新增加了访问SD卡的权限，和相册的权限相同，增加了版本判断API15以下直接默认授权</p>
 * @author hwj on 2016/8/19.
 */
public class PermissionHelper {

    private static final PermissionHelper instance=new PermissionHelper();
    private PermissionHelper(){}

    public static PermissionHelper getInstance(){
        return instance;
    }

    public final static int CAMERA_PERMISSION=801; //相机权限
    public final static int PHOTO_PERMISSION=802; //相册权限
    public final static int SDCARD_PERMISSION=803; //SD卡读写权限

    /**
     * 申请相机的权限
     * @param context
     * @return
     */
    public boolean requestCameraPermission(Context context){
        boolean isHandle=false;
        int hasCameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //标记直接处理
            isHandle=true;
        }else{
            Activity activity=null;
            if(context instanceof Activity)
                activity=(Activity)context;
            if(activity==null)
                return false;
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION);
        }
        return isHandle;
    }

    /**
     * 申请相册的权限
     * @param context
     * @return
     */
    public boolean requestPhotoPermission(Context context){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN){
            return true;
        }
        boolean isHandle=false;
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (read == PackageManager.PERMISSION_GRANTED&&write== PackageManager.PERMISSION_GRANTED) {
            //标记直接处理
            isHandle=true;
        }else{
            Activity activity=null;
            if(context instanceof Activity){
                activity=(Activity)context;
            }
            if(activity==null){
                return false;
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PHOTO_PERMISSION);
        }
        return isHandle;
    }
    /**
     * 申请读写SD卡的权限
     * @param context
     * @return
     */
    public boolean requestSDCardPermission(Context context){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN){
            return true;
        }
        boolean isHandle=false;
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (read == PackageManager.PERMISSION_GRANTED&&write== PackageManager.PERMISSION_GRANTED) {
            //标记直接处理
            isHandle=true;
        }else{
            Activity activity=null;
            if(context instanceof Activity){
                activity=(Activity)context;
            }
            if(activity==null){
                return false;
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    SDCARD_PERMISSION);
        }
        return isHandle;
    }

    /**
     * 判定是否申请过相应权限
     * @param permissions
     * @param grantResults
     * @param permission
     * @return
     */
    public boolean isPermission(String[] permissions, int[] grantResults, String permission[]) {
        int lenPs;
        int lenP;
        int lenG;
        if(permissions!=null&&permission!=null&&grantResults!=null){
            lenPs=permissions.length;
            lenP=permission.length;
            lenG=grantResults.length;
        }else{
            return false;
        }
        if(lenPs!=lenP||lenP!=lenG){
            return false;
        }
        for(int i=0;i<lenPs;i++){
            if(!permissions[i].equals(permission[i])
                    ||grantResults[i] != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

}

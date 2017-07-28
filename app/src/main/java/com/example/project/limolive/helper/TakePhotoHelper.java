package com.example.project.limolive.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 用于进行拍照的类
 * <p>在6.0及以上API需要进行权限的判断</p>
 * @author hwj on 2016/12/15.
 */

public class TakePhotoHelper {

    private static final String TAG="TakePhotoHelper";

    private SDHelper helper;
    private Activity activity;
    public static final int REQUEST_IMAGE_CAPTURE = 201; //拍照
    public static final int REQUEST_ALBUM_IMAGE = 202; //相册
    public static final int REQUEST_CUTTING = 203; //裁剪
    public static final int IMAGE_SAVE_OVER = 210; //图片保存完毕


    public TakePhotoHelper(Activity activity){
        this.activity=activity;
        helper=SDHelper.getSDHelper();
        helper.init(activity);
    }



    /** 检查本设备是否有摄像头*/
    private boolean checkCameraHardware() {
        if (activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 打开相册
     */
    public void openSystemAlbum(){
        if(PermissionHelper.getInstance().requestPhotoPermission(activity)){
            openAlbum();
        }
    }

    /**
     * 拍照
     */
    public void takePhotos(){
        if(PermissionHelper.getInstance().requestCameraPermission(activity)){
            takePhoto();
        }
    }
    /**
     * 打开相册
     */
    public void openSystemAlbumFromFragment(Fragment fragment){
        if(PermissionHelper.getInstance().requestPhotoPermission(activity)){
            openAlbumFromFragment(fragment);
        }
    }

    /**
     * 拍照
     */
    public void takePhotosFromFragment(Fragment fragment){
        if(PermissionHelper.getInstance().requestCameraPermission(activity)){
            takePhotoFromFragment(fragment);
        }
    }

    /**
     * 拍照
     * <p>在相应的Activity的onActivityResult方法通过判断requestCode获取bitmap</p>
     */
    private void takePhoto() {
        if(!checkCameraHardware()){
            return;
        }
        File file = new File(helper.getCameraPath());
        if(!file.exists()){  //文件夹不存在，重新创建Folder
            helper.reCreateRootFolder();
        }
        String imgsName=String.valueOf(System.currentTimeMillis())+".png";
        saveImageName(activity,imgsName);
        Intent takePhoto = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        takePhoto.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(helper.getCameraPath()+File.separator+imgsName)));
        activity.startActivityForResult(takePhoto, REQUEST_IMAGE_CAPTURE);// 打开拍照界面
    }


    /**
     * 打开系统相册
     */
    private void openAlbum() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,null);
        String imgsName=String.valueOf(System.currentTimeMillis())+".png";
        saveImageName(activity,imgsName);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(pickIntent, REQUEST_ALBUM_IMAGE); //打开相册界面
    }
    /**
     * 拍照
     * <p>在相应的Activity的onActivityResult方法通过判断requestCode获取bitmap</p>
     */
    private void takePhotoFromFragment(Fragment fragment) {
        if(!checkCameraHardware()){
            return;
        }
        File file = new File(helper.getCameraPath());
        if(!file.exists()){  //文件夹不存在，重新创建Folder
            helper.reCreateRootFolder();
        }
        String imgsName=String.valueOf(System.currentTimeMillis())+".png";
        saveImageName(activity,imgsName);
        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhoto.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(helper.getCameraPath()+File.separator+imgsName)));
        fragment.startActivityForResult(takePhoto, REQUEST_IMAGE_CAPTURE);// 打开拍照界面
    }


    /**
     * 打开系统相册
     */
    private void openAlbumFromFragment(Fragment fragment) {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,null);
        String imgsName=String.valueOf(System.currentTimeMillis())+".png";
        saveImageName(activity,imgsName);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        fragment.startActivityForResult(pickIntent, REQUEST_ALBUM_IMAGE); //打开相册界面
    }


    public void startPhotoZoom(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, REQUEST_CUTTING);// 打开裁剪界面
    }
    public void startPhotoZoom(Uri uri,Fragment fragment){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        fragment.startActivityForResult(intent, REQUEST_CUTTING);// 打开裁剪界面
    }


    /**
     * 处理保存好的图片
     */
    public void handleSavedImage() {
        File file=new File(helper.getCameraPath()+File.separator+getImageName(activity));
        if(file.exists()&&file.isFile()){
            Uri uri=Uri.fromFile(file);
            startPhotoZoom(uri); //打开裁剪界面
        }
    }
    /**
     * 处理保存好的图片
     */
    public void handleSavedImage(Fragment fragment) {
        File file=new File(helper.getCameraPath()+File.separator+getImageName(activity));
        if(file.exists()&&file.isFile()){
            Uri uri=Uri.fromFile(file);
            startPhotoZoom(uri,fragment); //打开裁剪界面
        }
    }

    /**
     * 存储当前的图片名字
     * @param context
     * @return
     */
    public void saveImageName(Context context, String name){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("current_image_name",name).apply();
    }

    /**
     * 获取图片名字
     * @param context
     * @return
     */
    public String getImageName(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("current_image_name",null);
    }

    /**
     * 压缩图片
     * @param options Options
     * @param reqWidth 重新定义图片的宽
     * @param reqHeight 重新定义图片的高
     * @return 返回新图片的尺寸比例
     */
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 传入路径获取压缩后的图片
     * @param filePath  图片路径
     * @return 压缩后的图片
     */
    public Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 1080, 1920);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 重新保存bitmap
     * @param bitmap
     */
    public Uri saveBitmap(Bitmap bitmap) {
            File img = new File(helper.getCameraPath()+
                    File.separator+getImageName(activity));// 保存图片文件的对象
            try {
                FileOutputStream fos = new FileOutputStream(img);
                bitmap.compress(Bitmap.CompressFormat.PNG, 85, fos);// 第一个参数是压缩格式,第二个参数是图片的质量,第三个参数是要写入参数的输出流
                fos.flush();
                fos.close();
                MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                        img.getAbsolutePath(), getImageName(activity), null);
                Uri uri=Uri.fromFile(img);
                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                return uri;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
    }
    /**
     * 开启子线程重新保存bitmap
     * <p>图片保存完毕时，发送消息向UI线程，通知其更新</p>
     * @param bitmap
     */
    public void saveBitmap(final Bitmap bitmap,final Handler handler) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File img = new File(helper.getCameraPath()+ File.separator+getImageName(activity));// 保存图片文件的对象


                    Log.e("img","图片地址"+img);


                    try {
                        FileOutputStream fos = new FileOutputStream(img);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 85, fos);// 第一个参数是压缩格式,第二个参数是图片的质量,第三个参数是要写入参数的输出流
                        fos.flush();
                        MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                                img.getAbsolutePath(), getImageName(activity), null);
                        Uri uri=Uri.fromFile(img);
                        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                        if(handler!=null){
                            Message msg=Message.obtain();
                            msg.what=IMAGE_SAVE_OVER;
                            msg.obj=uri;
                            handler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }
}

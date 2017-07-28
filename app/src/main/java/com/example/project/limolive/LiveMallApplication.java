package com.example.project.limolive;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.baidu.mapapi.SDKInitializer;
import com.example.project.limolive.activity.MainActivity;
import com.example.project.limolive.helper.ActivityHelper;
import com.example.project.limolive.tencentlive.presenters.InitBusinessHelper;
import com.example.project.limolive.tencentlive.utils.SxbLogImpl;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.log.CustomLogger;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.tencent.TIMGroupReceiveMessageOpt;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;
import de.greenrobot.event.EventBus;

/**
 * 全局Application
 * <p>本应用限制了用户设定的字号，强行采用默认字号</p>
 * <p>提供了Fresco的初始化方法，可参考{@link Fresco}，{@link com.facebook.drawee.view.SimpleDraweeView},
 * 文档参见https://www.fresco-cn.org/docs/</p>
 * <p>暴露了提供外部调用的接口getApplication(),可通过该方法使用LiveMallApplication</p>
 * <p>增加了SD卡管理工具，方便进行图片的保存与管理</p>
 *
 * @author hwj on 2016/12/15.
 */

public class LiveMallApplication extends Application {

    private static Context context;

    private static LiveMallApplication application;

    public final static int PAGE_SIZE = 20;//最大可以50

    private EventBus mEventBus;

    private JobManager mJobManager;


    private static final String TAG = LiveMallApplication.class.getSimpleName();
    private static final String APP_CACAHE_DIRNAME = "/webcache";

    //singleton
    private static LiveMallApplication appContext = null;
    private Display display;

    {
        //友盟初始化
        PlatformConfig.setWeixin("wx6846e27866f95324", "7d95fb63dd550c648fd3114da9e574e0");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //三方登录 目前SDK默认设置为在Token有效期内登录不进行二次授权，如果有需要每次登录都弹出授权页面，便于切换账号的开发者可以自行配置
        UMShareConfig config = new UMShareConfig();
        //友盟
        Config.isJumptoAppStore = true;
        Config.DEBUG = true;
        config.setSinaAuthType(UMShareConfig.AUTH_TYPE_WEBVIEW);
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
        application = this;
        appContext = this;
        if (display == null) {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            display = windowManager.getDefaultDisplay();
        }
        //初始化Fresco
        Fresco.initialize(this);
        PgyCrashManager.register(getApplicationContext());
        PgyCrashManager.register(this);


        context = getApplicationContext();

        SxbLogImpl.init(getApplicationContext());

        //初始化APP
        Log.i("contextcontextcontext", context + "");
        InitBusinessHelper.initApp(context);

        initImageLoader(getApplicationContext());

        if (MsfSdkUtils.isMainProcess(this)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
                        //消息被设置为需要提醒
                        notification.doNotify(getApplicationContext(), R.mipmap.ic_launcher);
                    }
                }
            });
        }
        mEventBus = EventBus.getDefault();

        com.path.android.jobqueue.config.Configuration netConfig = new com.path.android.jobqueue.config.Configuration.Builder(this).minConsumerCount(1)
                .maxConsumerCount(5)
                .loadFactor(2)
                .consumerKeepAlive(120)
                .id("JobManager").customLogger(new CustomLogger() {
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String s, Object... objects) {
                        Log.d("JOB", String.format("Debug:%s", s));
                    }

                    @Override
                    public void e(Throwable throwable, String s, Object... objects) {
                        Log.d("JOB", String.format("Error:%s", s));
                    }

                    @Override
                    public void e(String s, Object... objects) {
                        Log.d("JOB", String.format("Error:%s", s));
                    }
                }).build();
        mJobManager = new JobManager(this, netConfig);
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());

        //初始化百度地图SDK
        SDKInitializer.initialize(this);
    }


    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCacheSize((int) Runtime.getRuntime().maxMemory() / 4);
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(100 * 1024 * 1024); // 100 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //修改连接超时时间5秒，下载超时时间5秒
        config.imageDownloader(new BaseImageDownloader(application, 5 * 1000, 5 * 1000));
        //		config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }


    public String getCachePath() {
        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = getExternalCacheDir();
        else
            cacheDir = getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir.getAbsolutePath();
    }

    /**
     * @return
     * @Description： 获取当前屏幕1/4宽度
     */
    public int getQuarterWidth() {
        return display.getWidth() / 4;
    }

    /**
     * 获取全局Application
     *
     * @return
     */
    public static LiveMallApplication getApplication() {
        return application;
    }

    /**
     * 获取Application
     */
    public static LiveMallApplication getApp() {
        return application;
    }

    public static LiveMallApplication getInstance() {
        return application;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 强行使用默认字体
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != 1) {
            Configuration configuration = new Configuration();
            configuration.setToDefaults();//设置默认缩放大小
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    /**
     * 销毁所有Activity
     */
    public void destroyAllActivity(final Handler handler) {
        if (handler != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ActivityHelper.getInstance().onAllDestroy();
                            ActivityHelper.getInstance().clearHandlerMessage(handler);
                        }
                    });
                }
            }).start();
        }
    }

    public static Context getContext() {
        return context;
    }

    public EventBus getEventBus() {
        return mEventBus;
    }

    public JobManager getJobManager() {
        return mJobManager;
    }

    public static String SK() {
        return "PWTIyZGdbBTtXri84Oj3NC932DhxXN8n";
    }

    public static String AK() {
        return "r3sHA6uyjCwDvE838WGfvnPSpghTxi93";
    }

    public static int TABLE_ID() {
        return 158714;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

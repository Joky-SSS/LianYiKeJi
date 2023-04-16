package com.lianyi.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.allenliu.classicbt.BleManager;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.TokenBean;
import com.lianyi.app.utils.AppManageHelper;
import com.lianyi.app.utils.TokenInterceptor;
import com.tencent.mmkv.MMKV;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rxhttp.RxHttp;

/**
 * @ClassName: AppHolder
 * @Description:
 * @Author: Lee
 * @CreateDate: 2020/6/22 15:55
 */
public class AppHolder extends Application implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MMKV.initialize(this);
        //设置debug模式，默认为false，设置为true后，发请求，过滤"RxHttp"能看到请求日志
        RxHttp.init(getDefaultOkHttpClient(), true);
        RxHttp.setOnParamAssembly(param -> {
            String paramUrl = param.getUrl();
            if (paramUrl.contains(ApiService.API_BASE)) {
                TokenBean tokenBean = MMKV.defaultMMKV().decodeParcelable(Constant.DATA, TokenBean.class, null);
                if (tokenBean != null) {
                    param.addHeader(Constant.AUTHORIZATION, tokenBean.getToken_type() + " " + tokenBean.getAccess_token());
//                    param.addHeader(Constant.AUTHORIZATION, "bearer d68a09ee-a03a-4fcd-b9d8-6d43e7ea7472");
                }
            }
            return param;
        });

        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        registerActivityLifecycleCallbacks(this);

        initBle();

    }

    private void initBle() {
        BleManager.getInstance().init(this);
        BleManager.getInstance().setForegroundService(true);
    }


    private static AppHolder instance;

    public static AppHolder getInstance() {
        return instance;
    }

    private static OkHttpClient getDefaultOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(60*3, TimeUnit.SECONDS)
                .readTimeout(60*3, TimeUnit.SECONDS)
                .writeTimeout(60*3, TimeUnit.SECONDS)
                .addInterceptor(new TokenInterceptor())
                .hostnameVerifier((hostname, session) -> true) //忽略host验证
                .build();
    }


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        /**
         *  监听到 Activity创建事件 将该 Activity 加入list
         */
        AppManageHelper.pushActivity(activity);

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
//        AndroidBug5497Workaround.assistActivity(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (null == AppManageHelper.mActivityList || AppManageHelper.mActivityList.isEmpty()) {
            return;
        }
        if (AppManageHelper.mActivityList.contains(activity)) {
            /**
             *  监听到 Activity销毁事件 将该Activity 从list中移除
             */
            AppManageHelper.popActivity(activity);
        }
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}

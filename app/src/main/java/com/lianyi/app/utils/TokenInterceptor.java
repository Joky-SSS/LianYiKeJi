package com.lianyi.app.utils;

import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ClassName: TokenInterceptor
 * @Description: 刷新token
 * @Author: Lee
 * @CreateDate: 2020/6/29 15:45
 */
public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);
        int code = originalResponse.code();
        if (code == 401) { //token 失效  1、这里根据自己的业务需求写判断条件
            AppManageHelper.finishAllActivity();
            ARouter.getInstance().build(RoutePath.PATH_LOGIN)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK).navigation();

        }
        return originalResponse;
    }
}
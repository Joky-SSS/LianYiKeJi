package com.lianyi.app.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lianyi.app.otto.OttoBus;
import com.mumu.dialog.MMLoading;
import com.mumu.dialog.MMToast;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName: BaseActvity
 * @Description:
 * @Author: Lee
 * @CreateDate: 2020/6/29 16:57
 */
public abstract class BaseActvity extends AppCompatActivity {

    private Unbinder mUnbinder;
    private MMLoading mmLoading;
    private MMToast mmToast;
    private Bus bus;
    public String queryStr = "";
    public int searchType = 1;
    public int pageNo = 1;
    public int pageSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ARouter.getInstance().inject(this);
        onInit(savedInstanceState);
        //如果有使用黄油刀，请在这边加入即可
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
        if (isRegisterOttO()) {
            bus = OttoBus.getInstance();
            bus.register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (bus != null) {
            //取消注册订阅事件
            bus.unregister(this);
        }
    }

    /**
     * 设置布局文件
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * //初始化
     *
     * @param savedInstanceState
     */
    public void onInit(Bundle savedInstanceState) {

    }

    /**
     * 初始化数据,请求网络数据等
     */
    public abstract void initData();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 是否注册OTTO
     *
     * @return
     */
    public abstract boolean isRegisterOttO();


    /**
     * 跳转Activity
     * skip Another Activity
     *
     * @param activity
     * @param cls
     */
    public static void skipActivityFinish(Activity activity, Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 跳转Activity
     * skip Activity
     *
     * @param activity
     * @param cls
     */
    public static void skipActivity(Activity activity, Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);

    }

    public void showLoading() {
        if (mmLoading == null) {
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage("加载中...")
                    .setCancelable(true)
                    .setCancelOutside(false);
            mmLoading = builder.create();
        } else {
            mmLoading.dismiss();
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage("加载中...")
                    .setCancelable(true)
                    .setCancelOutside(false);
            mmLoading = builder.create();
        }
        mmLoading.show();
    }

    public void showLoading(String msg) {
        if (mmLoading == null) {
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage(msg)
                    .setCancelable(true)
                    .setCancelOutside(false);
            mmLoading = builder.create();
        } else {
            mmLoading.dismiss();
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage(msg)
                    .setCancelable(true)
                    .setCancelOutside(false);
            mmLoading = builder.create();
        }
        mmLoading.show();
    }

    /**
     *
     * @param msg
     * @param isCancelable 是否点击空白和返回键dismiss
     */
    public void showLoading(String msg,boolean isCancelable) {
        if (mmLoading == null) {
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage(msg)
                    .setCancelable(isCancelable)
                    .setCancelOutside(isCancelable);
            mmLoading = builder.create();
        } else {
            mmLoading.dismiss();
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage(msg)
                    .setCancelable(isCancelable)
                    .setCancelOutside(isCancelable);
            mmLoading = builder.create();
        }
        mmLoading.show();
    }

    /**
     * 隐藏加载框
     */
    public void hideLoading() {
        if (mmLoading != null && mmLoading.isShowing()) {
            mmLoading.dismiss();
        }
    }

    /**
     * 成功
     *
     * @param msg
     */
    public void showToastSuccess(String msg) {
        if (mmToast == null) {
            MMToast.Builder builder = new MMToast.Builder(this)
                    .setMessage(msg)
                    .setSuccess(true);
            mmToast = builder.create();
        } else {
            mmToast.cancel();
            MMToast.Builder builder = new MMToast.Builder(this)
                    .setMessage(msg)
                    .setSuccess(true);
            mmToast = builder.create();
        }
        mmToast.show();
    }

    /**
     * 失败
     *
     * @param msg
     */
    public void showToastFailure(String msg) {
        if (mmToast == null) {
            MMToast.Builder builder = new MMToast.Builder(this)
                    .setMessage(msg)
                    .setSuccess(false);
            mmToast = builder.create();
        } else {
            mmToast.cancel();
            MMToast.Builder builder = new MMToast.Builder(this)
                    .setMessage(msg)
                    .setSuccess(false);
            mmToast = builder.create();
        }
        mmToast.show();
    }

    /**
     *  隐藏软键盘
     * @param view
     */
    public static void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    /**
     *  显示软键盘
     * @param view
     */
    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }


}
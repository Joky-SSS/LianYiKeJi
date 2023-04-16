package com.lianyi.app.ui;


import android.graphics.Color;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.GsonUtils;
import com.lianyi.app.BuildConfig;
import com.lianyi.app.R;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.Response;
import com.lianyi.app.model.TaskBean;
import com.lianyi.app.utils.DoubleClicks;
import com.lianyi.app.utils.RoutePath;
import com.lianyi.app.utils.SystemBarUtil;
import com.rxjava.rxlife.RxLife;

import butterknife.BindView;
import butterknife.OnClick;
import rxhttp.RxHttp;
import rxhttp.wrapper.exception.HttpStatusCodeException;
import rxhttp.wrapper.exception.ParseException;

/**
 * @ClassName: HomeActivity
 * @Description:
 * @Author: Lee
 * @CreateDate: 2020/6/28 11:48
 */
@Route(path = RoutePath.PATH_HOME)
public class HomeActivity extends BaseActvity {


    @Override
    public int getLayoutId() {
        return R.layout.home_layout;
    }

    @Override
    public void initView() {
        SystemBarUtil.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        SystemBarUtil.setStatusBarDarkMode(this,false);
        SystemBarUtil.translucentStatusBar(this,true);
        if (!BuildConfig.hasRFID) findViewById(R.id.layoutInventory).setVisibility(View.GONE);
    }

    @Override
    public void initData() {


    }

    @Override
    public boolean isRegisterOttO() {
        return false;
    }


    @OnClick({R.id.layoutCheck, R.id.layoutInventory})
    public void onViewClicked(View view) {
        if (DoubleClicks.isDoubleClick(view)) {
            return;
        }
        if (R.id.layoutCheck == view.getId()) {
            queryTask();
        } else if (R.id.layoutInventory == view.getId()) {
            queryInventory();
        }
    }

    /**
     * 查询任务
     * PROCESSING 进行中
     * NOTSTARTED 尚未开始
     * FINISHED 已完成
     */
    private void queryTask() {
        RxHttp.get(ApiService.API_ASSET_TASK)
                .asResponse(TaskBean.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(taskBean -> {
                    if (taskBean.getStatus().equals("PROCESSING")) {
                        ARouter.getInstance().build(RoutePath.PATH_ROOM).withParcelable(Constant.TASK, taskBean).navigation();
                    } else {
                        startTask();
                    }
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
                });
    }


    /**
     * 查询任务
     */
    private void startTask() {
        RxHttp.postForm(ApiService.API_ASSET_TASK)
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading("开启清查任务"))  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(taskBean -> {
                    queryTask();
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
                });
    }

    /**
     * 查询RFID盘点库存相关信息
     * PROCESSING 进行中
     * NOTSTARTED 尚未开始
     * FINISHED 已完成
     */
    private void queryInventory() {
        RxHttp.get(ApiService.API_RFID_TASK)
                .asResponse(TaskBean.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(taskBean -> {
                    if (taskBean.getStatus().equals("PROCESSING")) {
                        ARouter.getInstance().build(RoutePath.PATH_INVENTORY_ROOM).withParcelable(Constant.TASK, taskBean).navigation();
                    } else {
                        startInventory();
                    }
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
                });
    }

    /**
     * 开启RFID盘点库存任务
     */
    private void startInventory() {
        RxHttp.postForm(ApiService.API_RFID_TASK)
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading("开启盘点任务"))  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(taskBean -> {
                    queryInventory();
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
                });
    }
}
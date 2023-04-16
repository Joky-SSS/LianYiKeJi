package com.lianyi.app.ui;


import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lianyi.app.R;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.InventoryGoodBean;
import com.lianyi.app.model.InventoryRoomListBean;
import com.lianyi.app.model.TaskBean;
import com.lianyi.app.utils.RoutePath;
import com.lianyi.app.utils.SystemBarUtil;
import com.rxjava.rxlife.RxLife;

import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import rxhttp.RxHttp;

/**
 * RFID盘点二维码扫描
 */
@Route(path = RoutePath.PATH_QRCODE_SCAN)
public class InventoryQRCodeScanActivity extends BaseActvity implements QRCodeView.Delegate {
    @Autowired(name = Constant.ROOM_BEAN)
    InventoryRoomListBean.RoomBean mRoomBean;
    @Autowired(name = Constant.TASK)
    TaskBean mTaskBean;
    private ZXingView zxingView;
    private ImageView ivClose;
    private boolean hasPermission = false;
    @Override
    public int getLayoutId() {
        return R.layout.qrcode_scan_layout;
    }

    @Override
    public void initView() {
        SystemBarUtil.translucentStatusBar(this, true);
        zxingView = findViewById(R.id.zxingView);
        zxingView.setDelegate(this);
        ivClose = findViewById(R.id.ivClose);
        ivClose.setOnClickListener(v->{finish();});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && PackageManager.PERMISSION_DENIED ==
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1001);
        }else {
            hasPermission = true;
        }
    }

    @Override
    public void initData() {


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasPermission){
            //先显示界面，延迟初始化摄像头
            zxingView.post(()->{
                zxingView.startCamera();
                zxingView.showScanRect();
                zxingView.startSpot();
            });
        }
    }

    @Override
    protected void onStop() {
        zxingView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zxingView.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean isRegisterOttO() {
        return false;
    }


    @OnClick({})
    public void onViewClicked(View view) {

    }

    /**
     * 二维码扫描结果回调
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        Uri uri = Uri.parse(result);
        String label = uri.getQueryParameter("label");
        queryGoodsInfo(label);
    }

    /**
     * 根据扫码标签，查询物品信息
     * @param label
     */
    private void queryGoodsInfo(String label) {
        RxHttp.get(ApiService.API_RFID_QRCODE)
                .add("barcode",label)
                .add("taskId",mTaskBean.getId())
                .asResponse(InventoryGoodBean.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(goodBean -> {
                    if ("NEW".equalsIgnoreCase(goodBean.getNewOldSign())){
                        //新增的物品
                        ARouter.getInstance().build(RoutePath.PATH_INVENTORY_NEW_GOOD)
                                .withString(Constant.GOODS_ID, goodBean.getId())
                                .withParcelable(Constant.ROOM_BEAN, mRoomBean)
                                .withParcelable(Constant.TASK, mTaskBean)
                                .navigation();
                    }else{
                        ARouter.getInstance().build(RoutePath.PATH_INVENTORY_GOODS_DETAILS)
                                .withString(Constant.GOODS_ID, goodBean.getId())
                                .withParcelable(Constant.ROOM_BEAN, mRoomBean)
                                .withParcelable(Constant.TASK, mTaskBean)
                                .navigation();
                    }
                    finish();
                }, throwable -> {
                    showToastFailure("未识别到对应的物品信息");
                    finish();
                });

    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }


    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);

    }
    @Override
    public void onScanQRCodeOpenCameraError() {
        showToastFailure("打开摄像头失败");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1001){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                hasPermission = true;
                zxingView.startSpot();
            }else {
                showToastFailure("获取摄像头权限失败");
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
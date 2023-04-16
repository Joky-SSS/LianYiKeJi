package com.lianyi.app.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.CompoundButton;

import com.allen.library.SuperTextView;
import com.inuker.bluetooth.library.BluetoothClient;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.lianyi.app.R;
import com.lianyi.app.otto.BusData;
import com.lianyi.app.otto.OttoBus;
import com.squareup.otto.Subscribe;


/**
 * @ClassName: PrintSettingPopup
 * @Description: 打印机设置
 * @Author: Lee
 * @CreateDate: 2020/7/7 11:46
 */
public class PrintSettingPopup extends BottomPopupView implements SuperTextView.OnSwitchCheckedChangeListener, SuperTextView.OnSuperTextViewClickListener, XPopupCallback {
    SuperTextView svPrintOpenBluetooth, svPrintDevices, svPrintType, svPrintConcentration, svPrintSpeed;

    Context mContext;
    BluetoothClient mBluetoothClient;

    public PrintSettingPopup(Context context, BluetoothClient bluetoothClient) {
        super(context);
        this.mContext = context;
        this.mBluetoothClient = bluetoothClient;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        OttoBus.getInstance().register(this);
        svPrintOpenBluetooth = findViewById(R.id.sv_print_open_bluetooth);
        svPrintDevices = findViewById(R.id.sv_print_devices);
        svPrintType = findViewById(R.id.sv_print_type);
        svPrintConcentration = findViewById(R.id.sv_print_concentration);
        svPrintSpeed = findViewById(R.id.sv_print_speed);

        svPrintOpenBluetooth.setSwitchCheckedChangeListener(this);
        svPrintDevices.setOnSuperTextViewClickListener(this);
        svPrintType.setOnSuperTextViewClickListener(this);
        svPrintConcentration.setOnSuperTextViewClickListener(this);
        svPrintSpeed.setOnSuperTextViewClickListener(this);
        svPrintOpenBluetooth.setSwitchIsChecked(mBluetoothClient.isBluetoothOpened());

    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_list_layout;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            svPrintDevices.setRightString("");
        }
        if (mOnPrintSettingListener != null) {
            mOnPrintSettingListener.onOpenBluetooth(isChecked);
        }
    }

    @Override
    public void onClickListener(SuperTextView superTextView) {
        switch (superTextView.getId()) {
            case R.id.sv_print_type:
                new XPopup.Builder(mContext).asBottomList("请选择纸张类型", new String[]{"间隙纸", "黑标纸", "连续纸", "过孔纸", "透明纸"}, (position, text) -> {
                    if (mOnPrintSettingListener != null) {
                        mOnPrintSettingListener.onPrintPaperType(position + 1);
                        svPrintType.setRightString(text);
                    }
                }).show();
                break;
            case R.id.sv_print_concentration:
                new XPopup.Builder(mContext).asBottomList("请选择打印浓度", new String[]{"正常", "较浓", "最浓"}, (position, text) -> {
                    if (mOnPrintSettingListener != null) {
                        mOnPrintSettingListener.onPrintConcentration(position + 3);
                        svPrintConcentration.setRightString(text);
                    }

                }).show();

                break;
            case R.id.sv_print_speed:
                new XPopup.Builder(mContext).asBottomList("请选择打印速度", new String[]{"最慢(1)", "较慢(3)", "正常(3)", "较快(4)", "最快(5)"}, (position, text) -> {
                    if (mOnPrintSettingListener != null) {
                        mOnPrintSettingListener.onPrintSpeed(position + 1);
                        svPrintSpeed.setRightString(text);
                    }
                }).show();
                break;
            case R.id.sv_print_devices:
                if (mOnPrintSettingListener != null) {
                    mOnPrintSettingListener.onSearchDevices(svPrintDevices);
                }
                break;
            default:
                break;
        }


    }

    @Override
    public void onDestroy() {
        OttoBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @Subscribe()
    public void sysnData(BusData busData) {
        String address = (String) busData.getData();
        svPrintDevices.setRightString(address);
    }

    OnPrintSettingListener mOnPrintSettingListener;

    public PrintSettingPopup setOnPrintSettingListener(OnPrintSettingListener onPrintSettingListener) {
        mOnPrintSettingListener = onPrintSettingListener;
        return this;
    }

    public interface OnPrintSettingListener {
        /**
         * 搜索设备
         */
        void onSearchDevices(SuperTextView svPrintDevices);

        /**
         * 蓝牙开关
         *
         * @param isChecked
         */
        void onOpenBluetooth(boolean isChecked);

        /**
         * 打印纸张类型
         *
         * @param paperType
         */
        void onPrintPaperType(int paperType);

        /**
         * 打印浓度
         *
         * @param printConcentration
         */
        void onPrintConcentration(int printConcentration);

        /**
         * 打印速度
         *
         * @param speed
         */
        void onPrintSpeed(int speed);

        /**
         *
         * @param superTextView
         */
        void onShow(SuperTextView superTextView);
    }

    @Override
    public void onDismiss() {

    }

    @Override
    public void onShow() {
        if (mOnPrintSettingListener != null) {
            mOnPrintSettingListener.onShow(svPrintDevices);
        }
    }

    @Override
    public void onCreated() {

    }

    @Override
    public void beforeShow() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}

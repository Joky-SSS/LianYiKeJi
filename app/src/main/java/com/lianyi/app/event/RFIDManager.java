package com.lianyi.app.event;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.lianyi.app.utils.ThreadUtil;
import com.uhf.base.UHFManager;
import com.uhf.base.UHFModuleType;

/**
 * RFID读取模块管理器
 */
public class RFIDManager {
    private static final String TAG = "RFIDManager";
    private static UHFManager uhfMangerImpl;
    private static boolean ifPowerOn;
    public static boolean ifSupportR2000Fun = true;
    public static boolean if5100Module = false;
    public static boolean if7100Module = false;
    public static boolean ifRMModule = false;

    public static boolean ifUM510 = false;

    /**
     * 初始化射频读取模块
     */
    public static void init() {
        Log.e(TAG,"init()");
        //初始化模块管理器类型  UM_MODULE, SLR_MODULE, RM_MODULE;
        try {
            uhfMangerImpl = UHFManager.getUHFImplSigleInstance(UHFModuleType.SLR_MODULE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //异步初始化扫描模块
        ThreadUtil.get().getExecutor().execute(() -> {
            if (uhfMangerImpl==null) return;
            try {
                if (uhfMangerImpl.getDeviceInfo().isIfHaveTrigger()) {
                    //给扫描模块硬件上电
                    ifPowerOn = uhfMangerImpl.powerOn();
                    Log.e(TAG, "powerOn = " + ifPowerOn);
                    uhfMangerImpl.changeConfig(true);
                } else {
                    //给扫描模块硬件上电
                    ifPowerOn = uhfMangerImpl.powerOn();
                    Log.e(TAG, "powerOn = " + ifPowerOn);
                }
                //获取配置信息
                getModuleInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //启动扫描结果读取线程
        if (uhfMangerImpl!=null && !GetRFIDThread.getInstance().isAlive()) GetRFIDThread.getInstance().start();
    }

    /**
     * 获取模块配置信息
     */
    private static void getModuleInfo() {
        //给时间(根据机型配置性能判断，一般2.5S足以)让串口和模块初始化
        //Give time (according to the configuration performance of the model, 2.5S is generally sufficient) for the serial port and module to initialize
        SystemClock.sleep(3000);
        if (UHFModuleType.UM_MODULE == UHFManager.getType()) {
            //初始化判断UM系列的UHF模块类型
            //Initialize and judge the UHF module type of UM series
            String ver = uhfMangerImpl.hardwareVerGet();
            if (!TextUtils.isEmpty(ver)) {
                //判断是否支持UM7模块功能
                //Determine whether the UM 7 module function is supported
                char moduleType = ver.charAt(0);
                ifSupportR2000Fun = moduleType == '7' || moduleType == '4' || moduleType == '5';
                Log.e(TAG, "ifMode = " + ifSupportR2000Fun + " ver =" + ver);
                ifUM510 = moduleType == '5';
            }
        } else if (UHFModuleType.SLR_MODULE == UHFManager.getType()) {
            String type = uhfMangerImpl.getUHFModuleType();
            Log.e(TAG, "type = " + type);
            if (!TextUtils.isEmpty(type)) {
                if (type.contains("5100")) {
                    if5100Module = true;
                }
                if (type.contains("7100") || type.contains("3100")) {
                    if7100Module = true;
                    uhfMangerImpl.slrInventoryModeSet(3);
                }
            }
        } else if (UHFModuleType.RM_MODULE == UHFManager.getType()) {
            uhfMangerImpl.getRFIDProtocolStandard();
            ifRMModule = true;
        }
    }

    public static void setUhfMangerImpl(UHFManager uhfMangerImpl) {
        RFIDManager.uhfMangerImpl = uhfMangerImpl;
    }

    public static UHFManager getUhfMangerImpl() {
        return uhfMangerImpl;
    }


}

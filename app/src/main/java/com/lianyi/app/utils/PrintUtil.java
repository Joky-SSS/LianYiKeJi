package com.lianyi.app.utils;

import android.app.Application;

import com.gengcon.www.jcprintersdk.JCPrintApi;
import com.gengcon.www.jcprintersdk.callback.Callback;

/**
 * 打印工具类
 *
 * @author zhangbin
 */
public class PrintUtil {
    private final static Callback CALLBACK = new Callback() {
        @Override
        public void onConnectSuccess(String s) {

        }

        @Override
        public void onDisConnect() {

        }

        @Override
        public void onElectricityChange(int i) {

        }

        @Override
        public void onCoverStatus(int i) {

        }

        @Override
        public void onPaperStatus(int i) {

        }

        @Override
        public void onRfidReadStatus(int i) {

        }

        @Override
        public void onHeartDisConnect() {

        }

        @Override
        public void onFirmErrors() {

        }

        @Override
        public void onPrinterIsFree(int i) {

        }
    };


    private static JCPrintApi api;

    public static JCPrintApi getInstance(Application application) {
        if (api == null) {
            api = JCPrintApi.getInstance(CALLBACK);
            api.init(application);
            api.initImageProcessingDefault("","");
        }

        return api;
    }


}

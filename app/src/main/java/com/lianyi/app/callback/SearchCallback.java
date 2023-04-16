package com.lianyi.app.callback;

import android.text.TextUtils;

import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @ClassName: SearchCallback
 * @Description: 打印机连接
 * @Author: Lee
 * @CreateDate: 2020/7/11 19:05
 */
public class SearchCallback implements SearchResponse {

    List<SearchResult> mBleDeviceList;
    Set<String> mResultSet;

    public SearchCallback() {

    }

    public void setOnPrintSearchListener(OnPrintSearchListener onPrintSearchListener) {
        mOnPrintSearchListener = onPrintSearchListener;
    }

    OnPrintSearchListener mOnPrintSearchListener;

    @Override
    public void onSearchStarted() {
        mBleDeviceList = new ArrayList<>();
        mResultSet = new HashSet<>();
        if (mOnPrintSearchListener != null) {
            mOnPrintSearchListener.onSearchStarted();
        }
    }

    @Override
    public void onDeviceFounded(SearchResult device) {
        if (mResultSet.add(device.getAddress()) && !TextUtils.isEmpty(device.getName()) && !device.getName().equalsIgnoreCase("NULL")) {
            mBleDeviceList.add(device);
        }

    }

    @Override
    public void onSearchStopped() {
        if (mOnPrintSearchListener != null) {
            mOnPrintSearchListener.onSearchStopped(mBleDeviceList);
        }
    }

    @Override
    public void onSearchCanceled() {
        if (mOnPrintSearchListener != null) {
            mOnPrintSearchListener.onSearchCanceled();
        }
    }

    public interface OnPrintSearchListener {
        void onSearchStarted();

        void onSearchStopped(List<SearchResult> bleDeviceList);

        void onSearchCanceled();

    }
}

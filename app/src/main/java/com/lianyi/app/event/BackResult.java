package com.lianyi.app.event;


/**
 * author CYD
 * date 2018/11/19
 * 读取RFID标签数据回调
 */
public interface BackResult {
    void postResult(String[] tagData);

    void postInventoryRate(long rate);
}

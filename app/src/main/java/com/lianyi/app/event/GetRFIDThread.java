package com.lianyi.app.event;

import android.os.SystemClock;
import android.util.Log;

/**
 * author CYD
 * date 2018/11/19
 * 读取RFID标签数据线程
 */
public class GetRFIDThread extends Thread {


    private GetRFIDThread() {
    }

    public static GetRFIDThread getInstance() {
        return MySingleton.instance;
    }

    static class MySingleton {
        static final GetRFIDThread instance = new GetRFIDThread();
    }

    private BackResult ba;

    private boolean ifPostMsg = false;

    public void setBackResult(BackResult ba) {
        this.ba = ba;
    }

    public boolean isIfPostMsg() {
        return ifPostMsg;
    }

    private boolean flag = true;

    public void destoryThread() {
        flag = false;
    }

    private long sTime;

    public void setIfPostMsg(boolean ifPostMsg) {
        if (ifPostMsg) {
            sTime = SystemClock.elapsedRealtime();
        }
        this.ifPostMsg = ifPostMsg;
    }
    //是否处于查询标签模式
    // Whether in query tag mode
    private boolean searchTag; 

    public void setSearchTag(boolean searchTag) {
        this.searchTag = searchTag;
    }
    //是否处于锁定盘点模式
    private boolean lockPostTag = false;

    public void setLockPostTag(boolean lockPostTag) {
        this.lockPostTag = lockPostTag;
    }
    public boolean getLockPostTag() {
        return lockPostTag;
    }

    @Override
    public void run() {
        long curTime, oldTime = 0;
        //每秒的读取速率
        // Read rate per second
        long readRate = 0;
        //开始盘点的时间
        // Start time
        long tempTime = 0; 
        while (flag) {
            if (ifPostMsg) {
                if (tempTime == 0 && sTime != 0) {
                    tempTime = sTime;
                }
                long cTime = SystemClock.elapsedRealtime();
                if (cTime - tempTime >= 1000 && tempTime != 0) {
//                    if (/*MyApp.getMyApp().getUhfMangerImpl().slrInventoryModelGet() == 3 ||*/ MyApp.getMyApp().getUhfMangerImpl().slrInventoryModelGet() == 4) {
//                        ba.postInventoryRate(LeftFragment.time);
//                        LeftFragment.time = 0;
//                        tempTime = cTime;
//                    }
//                    else {
                        if (ba!=null)ba.postInventoryRate(readRate);
                        readRate = 0;
                        tempTime = cTime;
//                    }
                }
                String[] tagData = RFIDManager.getUhfMangerImpl().readTagFromBuffer();
                Log.e("GetRFIDThread","epcFottest = " + tagData);
                if (tagData != null) {
                    readRate++;
                    oldTime = 0;
                    if (ba!=null)ba.postResult(tagData);
                } else if (searchTag) { 
                    //当超过一秒查询不到标签，清空状态
                    // Clear status when no tag is queried for more than one second
                    curTime = System.currentTimeMillis();
                    if ((curTime - oldTime) > 2000 && (oldTime != 0)) {
                        if (ba!=null)ba.postResult(null);
                    }
                    if (oldTime == 0) {
                        oldTime = curTime;
                    }
                }
            }else if(lockPostTag) {
                String[] tagData = RFIDManager.getUhfMangerImpl().readTagFromBuffer();
                Log.e("GetRFIDThread","epcFottest = " + tagData);
                if (tagData != null) {
                    if (ba!=null)ba.postResult(tagData);
                }
            }else {
                if (readRate != 0 ) {
                    //重置时间数据
                    // Reset time
                    sTime =0;
                    tempTime =0;
                    readRate = 0;
                }
            }
        }
    }
}

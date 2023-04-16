package com.lianyi.app.utils;

import android.view.View;

/**
 * @ClassName: DoubleClicks
 * @Description: 重复点击
 * @Author: Lee
 * @CreateDate: 2020/8/10 10:23
 */
public final class DoubleClicks {
    private static final int MIN_DELAY_TIME = 2000;  // 两次点击间隔不能少于1000ms

    public  static boolean isDoubleClick(View v) {
        Object tag = v.getTag(v.getId());
        long beforeTimemiles = tag != null ? (long) tag : 0;
        long timeInMillis = System.currentTimeMillis();
        v.setTag(v.getId(), timeInMillis);
        return timeInMillis - beforeTimemiles < MIN_DELAY_TIME;
    }

}

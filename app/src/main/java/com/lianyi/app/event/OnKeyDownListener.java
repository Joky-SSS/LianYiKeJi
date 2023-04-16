package com.lianyi.app.event;

import android.view.KeyEvent;

/**
 * Author CYD
 * Date 2019/2/20
 * 按键按下事件回调
 */
public interface OnKeyDownListener {
    void onKeyDown(int keyCode, KeyEvent event);
}

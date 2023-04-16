package com.lianyi.app.otto;

import com.squareup.otto.Bus;

/**
 * @ClassName: OttoBus
 * @Description: OTTO单例
 * @Author: Lee
 * @CreateDate: 2020/7/5 18:38
 */
public class OttoBus extends Bus {
    private volatile static OttoBus bus;

    private OttoBus() {
    }

    public static OttoBus getInstance() {
        if (bus == null) {
            synchronized (OttoBus.class) {
                if (bus == null) {
                    bus = new OttoBus();
                }
            }
        }
        return bus;
    }
}
package com.lianyi.app.utils;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author CYD
 * Date 2019/1/4
 *
 */
public class ThreadUtil {
    private ExecutorService executors;

    private ThreadUtil() {
        executors = Executors.newFixedThreadPool(3);
    }

    public static ThreadUtil get() {
        return MySingleton.instance;
    }

    static class MySingleton {
        static final ThreadUtil instance = new ThreadUtil();
    }

    public ExecutorService getExecutor() {
        return executors;
    }



}

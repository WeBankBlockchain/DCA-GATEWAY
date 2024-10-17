package com.webank.blockchain.dca.gateway.utils;


import cn.hutool.core.thread.ThreadUtil;
import com.webank.blockchain.dca.gateway.model.Context;

public class ThreadLocalUtils {

    private static final ThreadLocal<Context> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Context value) {
        THREAD_LOCAL.set(value);
    }

    public static Context get() {
        return THREAD_LOCAL.get();
    }

    public static void clear(){
        THREAD_LOCAL.remove();
    }

}

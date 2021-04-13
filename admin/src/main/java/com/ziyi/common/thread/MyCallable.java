package com.ziyi.common.thread;

import java.util.concurrent.Callable;

/**
 * auther:jurzis
 * date: 2021/2/24 10:01
 */
public class MyCallable implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println("线程"+Thread.currentThread().getName()+"在运行");
        return Thread.currentThread().getName();
    }
}

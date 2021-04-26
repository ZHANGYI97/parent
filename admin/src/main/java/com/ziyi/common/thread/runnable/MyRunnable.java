package com.ziyi.common.thread.runnable;

/**
 * auther:jurzis
 * date: 2021/2/24 9:55
 */
public class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("线程"+Thread.currentThread().getName()+"在运行");
    }
}

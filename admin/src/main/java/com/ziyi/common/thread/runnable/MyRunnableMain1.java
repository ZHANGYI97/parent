package com.ziyi.common.thread.runnable;

/**
 * auther:jurzis
 * date: 2021/2/24 9:56
 */
public class MyRunnableMain1 {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println("线程" + Thread.currentThread().getName() + "在运行");
        };
        new Thread(runnable).start();
    }
}

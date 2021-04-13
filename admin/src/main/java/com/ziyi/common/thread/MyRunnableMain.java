package com.ziyi.common.thread;

/**
 * auther:jurzis
 * date: 2021/2/24 9:56
 */
public class MyRunnableMain {

    public static void main(String[] args) {
        Thread thread = new Thread(new MyRunnable());
        Thread thread1 = new Thread(new MyRunnable());
        thread.start();
        thread1.start();
    }
}

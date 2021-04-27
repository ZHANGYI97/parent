package com.ziyi.common.thread.thread;

/**
 * auther:jurzis
 * date: 2021/2/24 9:52
 */
public class MyThreadMain {

    public static void main(String[] args) {
        Thread thread = new Thread(new MyThread());
        Thread thread1 = new Thread(new MyThread());
        thread.start();
        if (!thread.isAlive()){
            thread.start();
            System.out.println("+++++");
        }
        thread1.start();
    }
}

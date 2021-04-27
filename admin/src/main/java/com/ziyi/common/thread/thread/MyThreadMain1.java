package com.ziyi.common.thread.thread;

/**
 * auther:jurzis
 * date: 2021/2/24 9:52
 */
public class MyThreadMain1 {

    public static void main(String[] args) {

        Thread thread = new Thread() {
            public void run() {
                System.out.println("线程" + this.getName() + "在运行");
            }
        };
        thread.start();
    }

}

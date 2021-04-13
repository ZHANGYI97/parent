package com.ziyi.common.thread;

/**
 * auther:jurzis
 * date: 2021/2/24 9:48
 */
public class MyThread extends Thread {

    @Override
    public void run(){
        System.out.println("线程"+this.getName()+"在运行");
    }

}

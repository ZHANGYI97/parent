package com.ziyi.common.thread.join;

/**
 * auther:jurzis
 * date: 2021/4/26 11:42
 */
public class JoinThread {

    static Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("thread01");
        }
    });

    static Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("thread02");
        }
    });

    static Thread thread3 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("thread03");
        }
    });

    static Thread thread4 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("thread04");
        }
    });

    static Thread thread5 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("thread05");
        }
    });

    public static void main(String[] args) throws Exception{
        //join让线程阻塞，顺序执行
        thread1.start();
        thread1.join();

        thread2.start();
        thread2.join();

        thread3.start();
        thread3.join();

        thread4.start();
        thread4.join();

        thread5.start();
        thread5.join();
    }

}

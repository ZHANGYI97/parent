package com.ziyi.common.thread.join;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * auther:jurzis
 * date: 2021/4/26 14:25
 * ExecutorService方式按顺序执行线程
 */
public class ExecutorServiceThread {

    static ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    static JoinThread joinThread = new JoinThread();


    /**
     * 按顺序执行
     * @param args
     */
    public static void main(String[] args) {
        executorService.submit(joinThread.thread1);
        executorService.submit(joinThread.thread2);
        executorService.submit(joinThread.thread3);
        executorService.submit(joinThread.thread4);
        executorService.submit(joinThread.thread5);
        executorService.shutdown();
    }

}

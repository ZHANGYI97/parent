package com.ziyi.common.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * auther:jurzis
 * date: 2021/2/24 10:02
 */
public class MyCallableMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<String>(new MyCallable());
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("线程返回结果："+ futureTask.get());
    }
}

package com.ziyi.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * auther:jurzis
 * date: 2021/2/24 10:55
 */
@Service
public class AsyncTestImpl implements AsyncTest{

    private static final Logger logger = LoggerFactory.getLogger(AsyncTestImpl.class);


    @Override
    @Async("asyncServiceExecutor")
    public Future<String> testAsync(CountDownLatch countDownLatch) {
        try {
            logger.info("线程：" + Thread.currentThread().getName() + "在执行");
            //Thread.sleep(1000);
            /*return new Result(200, "success", Thread.currentThread());*/
            return new AsyncResult<String>(Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            countDownLatch.countDown();
        }
    }
}

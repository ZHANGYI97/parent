package com.ziyi.common.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * auther:jurzis
 * date: 2021/2/24 10:50
 */
public interface AsyncTest {

    Future<String> testAsync(CountDownLatch countDownLatch);

}

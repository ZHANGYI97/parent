package com.ziyi.Test;

import com.ziyi.common.service.AsyncTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * auther:jurzis
 * date: 2021/2/24 11:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private AsyncTest asyncTest;

    @org.junit.Test
    public void testAsync() {
        List<String> list = new ArrayList<>();
        try {
            CountDownLatch countDownLatch = new CountDownLatch(10);
            for (int i = 0; i < 10; i++) {
                Future<String> result = asyncTest.testAsync(countDownLatch);
                list.add(result.get());
            }
            countDownLatch.await();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

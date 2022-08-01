package com.ziyi.service.impl;

import com.ziyi.redis.lock.Lock;
import com.ziyi.redis.lock.LockService;
import com.ziyi.service.RedisLockService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhy
 * @data 2022/7/31 16:45
 */
@Service
@Slf4j
public class RedisLockServiceImpl implements RedisLockService {

    @Autowired
    private LockService lockService;

    @SneakyThrows
    @Override
    public String lock(String key) {

        Lock lock = null;

        try {
            lock = lockService.create(key);
            Thread.sleep(3600 * 10);
        } finally {
            if (lock != null && lock.isFlag()) {
                lockService.release(lock);
            }
        }
        return key;
    }
}

package com.ziyi.service;

/**
 * @author zhy
 * @data 2022/7/31 16:45
 */
public interface RedisLockService {

    String lock(String key);

}

package com.ziyi.service;

import com.ziyi.entity.Users;

/**
 * auther:jurzis
 * date: 2021/4/29 15:39
 */
public interface RedisTestService {

    Users methodTest1();

    Users methodTest2(String name, String salt);

}

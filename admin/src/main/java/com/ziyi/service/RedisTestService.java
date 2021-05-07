package com.ziyi.service;

import com.ziyi.entity.Users;

import java.util.Map;

/**
 * auther:jurzis
 * date: 2021/4/29 15:39
 */
public interface RedisTestService {

    Users methodTest1();

    Users methodTest2(String name, String salt);

    Map<String, String> methodTest3(String name);

    Users methodTest4(String name, String filed);

    Map<String, Users> methodTest5(String name);

}

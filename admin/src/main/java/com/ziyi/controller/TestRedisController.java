package com.ziyi.controller;

import com.ziyi.entity.Users;
import com.ziyi.service.RedisTestService;
import com.ziyi.service.impl.RedisTestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * auther:jurzis
 * date: 2021/4/29 16:45
 */
@RestController
@RequestMapping("/redis")
public class TestRedisController {

    @Autowired
    private RedisTestService redisTestService;

    @PostMapping("/testNoParam")
    public Users testNoParam(){
        return redisTestService.methodTest1();
    }

    @PostMapping("/testParam")
    public Users testParam(@RequestParam("name") String name, @RequestParam("salt") String salt){
        return redisTestService.methodTest2(name, salt);
    }
}

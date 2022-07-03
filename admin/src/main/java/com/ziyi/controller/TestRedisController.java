package com.ziyi.controller;

import com.ziyi.entity.Users;
import com.ziyi.service.RedisTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * auther:jurzis
 * date: 2021/4/29 16:45
 */
@RestController
@RequestMapping("/redis")
public class TestRedisController {

    @Autowired
    private RedisTestService redisTestService;

    /**
     * string
     * 推送对象类型，key指定死
     *
     * @return
     */
    @PostMapping("/testNoParam")
    public Users testNoParam() {
        return redisTestService.methodTest1();
    }

    /**
     * string
     * 推送对象类型，key可以自己指定
     *
     * @param name
     * @param salt
     * @return
     */
    @PostMapping("/testParam")
    public Users testParam(@RequestParam("name") String name, @RequestParam("salt") String salt) {
        return redisTestService.methodTest2(name, salt);
    }

    /**
     * string
     * 推送hash类型，key可以自己指定
     *
     * @param name
     * @return
     */
    @PostMapping("/testMapParam")
    public Map<String, String> testParam(@RequestParam("name") String name) {
        return redisTestService.methodTest3(name);
    }

    /**
     * hash
     * 推送对象类型，key可以自己指定，filed可以指定
     *
     * @param name
     * @return
     */
    @PostMapping("/testHashParamByFiled")
    public Users testParamByFiled(@RequestParam("name") String name, @RequestParam("filed") String filed) {
        return redisTestService.methodTest4(name, filed);
    }

    /**
     * hash
     * 推送对象类型，key可以自己指定，推送整个map
     *
     * @param name
     * @return
     */
    @PostMapping("/testHashParamByHash")
    public Map<String, Users> testParamByHash(@RequestParam("name") String name) {
        return redisTestService.methodTest5(name);
    }
}

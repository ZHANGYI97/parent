package com.ziyi.controller;

import com.ziyi.common.model.ResultData;
import com.ziyi.service.RedisLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhy
 * @data 2022/7/31 16:41
 */
@RestController
@RequestMapping("/redisLock")
public class TestRedisLockController {

    @Autowired
    private RedisLockService redisLockService;

    @GetMapping ("/lock")
    public ResultData<String> lock(@RequestParam String param) {
       return ResultData.succeed(redisLockService.lock(param));
    }

}

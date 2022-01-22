package com.ziyi.controller;

import com.ziyi.entity.Redis;
import com.ziyi.entity.TestTranslate;
import com.ziyi.mapper.TestTranslateMapper;
import com.ziyi.service.RedisTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redisTemplate")
@RequiredArgsConstructor
public class TestRedisTemplateController {

    final RedisTemplateService redisTemplateService;

    @PostMapping("/getKey")
    public Redis getKey(String key) {
        return redisTemplateService.getKey(key);
    }

    @PostMapping("/setKey")
    public String setKey(@RequestParam("key") String key, @RequestParam("value") String value) {
        return redisTemplateService.setKey(key, value);
    }

    @PostMapping("/setKeyIncrement")
    public Redis setKeyIncrement(@RequestParam("key") String key, @RequestParam("value") String value) {
        return redisTemplateService.setKeyIncrement(key, value);
    }

    @PostMapping("/translate")
    public String translate(){
        return redisTemplateService.translate();
    }

}

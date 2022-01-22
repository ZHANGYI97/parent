package com.ziyi.service.impl;

import com.ziyi.entity.Redis;
import com.ziyi.entity.TestTranslate;
import com.ziyi.mapper.TestTranslateMapper;
import com.ziyi.service.RedisTemplateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedisTemplateServiceImpl implements RedisTemplateService {

    final RedisTemplate redisTemplate;

    final RedisTemplateServiceCommon redisTemplateServiceCommon;

    @Override
    public Redis getKey(String key) {
        Redis redis = Redis.builder().build();
        redis.setKey(key);
        if (redisTemplate.hasKey(key)) {
            redis.setTime(redisTemplate.getExpire(key));
            redis.setValue((String) redisTemplate.opsForValue().get(key));
        }
        return redis;
    }

    @Override
    public String setKey(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return "Success";
    }

    @Override
    public Redis setKeyIncrement(String key, String value) {
        Redis redis = Redis.builder().build();
        redis.setKey(key);
        long i = redisTemplate.opsForValue().increment(key);
        System.out.println("----------" + i);
        redis.setTime(redisTemplate.getExpire(key));
        redis.setValue(redisTemplate.opsForValue().get(key).toString());
        return redis;
    }

    @Override
    public String translate() {
        redisTemplateServiceCommon.testTranslate();
        return "Sucess";
    }

}

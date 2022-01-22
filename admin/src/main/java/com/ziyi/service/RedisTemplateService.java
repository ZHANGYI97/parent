package com.ziyi.service;

import com.ziyi.entity.Redis;

public interface RedisTemplateService {

    Redis getKey(String key);

    String setKey(String key, String value);

    Redis setKeyIncrement(String key, String value);

    String translate();

}

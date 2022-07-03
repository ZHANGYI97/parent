package com.ziyi.service.impl;

import com.ziyi.common.constants.Constants;
import com.ziyi.entity.Users;
import com.ziyi.mapper.UsersMapper;
import com.ziyi.redis.aop.RedisCacheable;
import com.ziyi.service.RedisTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * auther:jurzis
 * date: 2021/4/29 15:46
 */
@Service
public class RedisTestServiceImpl implements RedisTestService {

    private static final Logger log = LoggerFactory.getLogger(RedisTestServiceImpl.class);

    @Autowired
    private UsersMapper usersMapper;

    @Override
    @RedisCacheable(key = "'ziyi:com'",firstLayerTtl = 20L,secondLayerTtl = 10L)
    public Users methodTest1() {
        log.info("-- 查询开始");
        // 这里处理未命中缓存时，查询数据库的逻辑
        Users users = usersMapper.selectOne();
        log.info("-- 查询结束 --");
        return users;
    }

    @Override
    @RedisCacheable(key = "'ziyi:com:'.concat(#name).concat(#salt)",firstLayerTtl = 20L,secondLayerTtl = 10L)
    public Users methodTest2(String name, String salt) {
        log.info("-- 查询开始");
        // 这里处理未命中缓存时，查询数据库的逻辑
        Users users = usersMapper.select(name, salt);
        log.info("-- 查询结束 --");
        return users;
    }

    @Override
    @RedisCacheable(key = "'ziyi:com:'.concat(#name)",firstLayerTtl = 20L,secondLayerTtl = 10L)
    public Map<String, String> methodTest3(String name) {
        log.info("-- 查询开始");
        Map<String, String> map = new HashMap<>();
        map.put("czw", "a");
        map.put("czw1", "b");
        log.info("-- 查询结束 --");
        return map;
    }

    @Override
    @RedisCacheable(key = "'ziyi:com:'.concat(#name)", filed = "#filed", type = Constants.HASH)
    public Users methodTest4(String name, String filed) {
        log.info("-- 查询开始");
        // 这里处理未命中缓存时，查询数据库的逻辑
        Users users = usersMapper.selectByName(name);
        log.info("-- 查询结束 --");
        return users;
    }

    @Override
    @RedisCacheable(key = "'ziyi:com:'.concat(#name)", type = Constants.HASH)
    public Map<String, Users> methodTest5(String name) {
        Map<String, Users> map = new HashMap<>();
        log.info("-- 查询开始");
        // 这里处理未命中缓存时，查询数据库的逻辑
        Users users = usersMapper.selectByName(name);
        map.put("zhy", users);
        map.put("zhy1", users);
        log.info("-- 查询结束 --");
        return map;
    }
}

package com.ziyi.service.impl;

import com.ziyi.entity.User;
import com.ziyi.entity.Users;
import com.ziyi.mapper.UsersMapper;
import com.ziyi.redis.aop.RedisCacheable;
import com.ziyi.service.RedisTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @RedisCacheable(key = "'gof:com:test'",firstLayerTtl = 20L,secondLayerTtl = 10L)
    public Users methodTest1() {
        log.info("-- 查询开始");
        // 这里处理未命中缓存时，查询数据库的逻辑
        // todo……
        Users users = usersMapper.selectOne();
        log.info("-- 查询结束 --");
        return users;
    }

    @Override
    @RedisCacheable(key = "'gof:com:test:'.concat(#name).concat(#salt)",firstLayerTtl = 20L,secondLayerTtl = 10L)
    public Users methodTest2(String name, String salt) {
        log.info("-- 查询开始");
        // 这里处理未命中缓存时，查询数据库的逻辑
        // todo……
        Users users = usersMapper.select(name, salt);
        log.info("-- 查询结束 --");
        return users;
    }
}

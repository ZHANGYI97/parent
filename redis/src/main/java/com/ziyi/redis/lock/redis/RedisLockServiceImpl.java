package com.ziyi.redis.lock.redis;

import com.google.common.collect.Lists;
import com.ziyi.common.base.exception.LockExistsException;
import com.ziyi.common.base.exception.ZiRuntimeException;
import com.ziyi.common.constants.MsgCodeConstants;
import com.ziyi.redis.lock.Lock;
import com.ziyi.redis.lock.LockService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Redis 分布式锁实现
 *
 * @author ZhangYuanXia
 * @date 2021/2/19 14:34
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedisLockServiceImpl implements LockService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 默认失效时间
     */
    private final static int EXPIRY = 15;

    /**
     * 释放锁LUA脚本
     */
    private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Override
    public Lock create(String name, String value) {
        Lock lock = Lock.newLock(name, value);
        boolean result;
        log.debug("//// lock name is {},lock key is {},lock expires is {}", name, lock.lockKey(), lock.getExpires());
        try {
            result = setIfAbsent(lock, EXPIRY);
        } catch (Exception e) {
            log.error("//// catch a error when create redis lock, cause by :", e);
            throw new ZiRuntimeException(MsgCodeConstants.LOCK_OPERATE_ERROR, e.getMessage());
        }
        if (!result) {
            throw new LockExistsException(lock.lockKey());
        }
        log.debug("//// create lock {} success", lock.lockKey());
        return lock;
    }

    @Override
    public Lock waitingLock(String name, String value, long timeout) {
        Lock lock = Lock.newLock(name, value);
        long start = System.currentTimeMillis();
        while (!setIfAbsent(lock, EXPIRY)) {
            // 等待锁
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                log.error("////  waiting lock sleep error {}", e.getMessage(), e);
                throw new ZiRuntimeException(MsgCodeConstants.LOCK_OPERATE_ERROR, e.getMessage());
            }
            long waitTime = System.currentTimeMillis() - start;
            if (TimeUnit.MILLISECONDS.toSeconds(waitTime) >= timeout) {
                throw new LockExistsException(lock.lockKey());
            }
        }
        return lock;
    }

    @Override
    public void release(@NonNull Lock lock) {
        List<String> keys = Lists.newArrayList(lock.lockKey());
        RedisScript<Long> script = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT, Long.class);
        if (0 == Optional.ofNullable(redisTemplate.execute(script, keys, lock.getValue())).orElse(0L)) {
            //解锁失败
            throw new ZiRuntimeException(MsgCodeConstants.LOCK_OPERATE_ERROR, "release fail");
        }
    }


    @Override
    public Lock refresh(Lock lock) {
        lock.setExpires(new Date(System.currentTimeMillis() + EXPIRY * 1000L));
        String stored = (String) redisTemplate.opsForValue().get(lock.lockKey());
        if (!ObjectUtils.isEmpty(stored) && stored.equals(lock.getValue())) {
            try {
                redisTemplate.expire(lock.lockKey(), EXPIRY, TimeUnit.SECONDS);
                return lock;
            } catch (Exception e) {
                log.error("//// refresh lock fail, unknown error ", e);
                throw new ZiRuntimeException(MsgCodeConstants.LOCK_OPERATE_ERROR, e.getMessage());
            }
        } else {
            log.error("//// refresh lock fail, do not fund effective lock");
            throw new ZiRuntimeException(MsgCodeConstants.LOCK_OPERATE_ERROR, "no lock");
        }
    }

    /**
     * 设置Redis 锁
     *
     * @param lock 锁对象
     * @return 是否成功
     */
    private boolean setIfAbsent(Lock lock, int expiry) {
        boolean result = Optional
                .ofNullable(redisTemplate.opsForValue()
                        .setIfAbsent(lock.lockKey(), lock.getValue(), expiry, TimeUnit.SECONDS))
                .orElse(false);
        if (result) {
            lock.setExpires(new Date(System.currentTimeMillis() + expiry * 1000L));
        }
        return result;
    }
}

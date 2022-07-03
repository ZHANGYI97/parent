package com.ziyi.redis.lock;

import com.ziyi.common.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

/**
 * 分布式锁
 *
 * @author ZhangYuanXia
 * @date 2021/2/19 14:28
 */
@Slf4j
@AllArgsConstructor
@Data
public class Lock implements Comparable<Lock> {

    /**
     * 锁的名称
     */
    private String name;
    /**
     * 锁的值，唯一
     */
    private String value;
    /**
     * 有效期
     */
    private Date expires;

    @Override
    public int compareTo(@NonNull Lock lock) {
        return expires.compareTo(lock.getExpires());
    }

    public String lockKey() {
        return Constants.LOCK_KEY_PREFIX + name;
    }

    public boolean isExpired() {
        return expires.before(new Date());
    }

    /**
     * 新建锁对象
     *
     * @param name 锁名称
     * @return 锁对象
     */
    public static Lock newLock(String name) {
        return new Lock(name, UUID.randomUUID().toString(), new Date());
    }

    /**
     * 新建锁对象
     *
     * @param name  锁名称
     * @param value 锁值
     * @return 锁对象
     */
    public static Lock newLock(String name, String value) {
        return new Lock(name, value, new Date());
    }

    /**
     * 新建锁对象
     *
     * @param name    锁名称
     * @param value   锁值
     * @param expires 锁失效时间
     * @return 锁对象
     */
    public static Lock newLock(String name, String value, Date expires) {
        return new Lock(name, value, expires);
    }
}

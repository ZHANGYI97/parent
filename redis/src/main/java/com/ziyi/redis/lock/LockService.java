package com.ziyi.redis.lock;

import com.ziyi.common.base.exception.LockExistsException;
import com.ziyi.common.base.exception.ZiRuntimeException;
import com.ziyi.common.constants.MsgCodeConstants;
import com.ziyi.common.string.StringUtils;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * 锁服务
 *
 * @author zhy
 * @date 2022/7/3
 */
@SuppressWarnings("unused")
public interface LockService {

    // region 创建锁

    /**
     * 创建锁
     *
     * @param name  锁名称
     * @param value 锁值
     * @return 锁
     */
    Lock create(String name, String value);

    /**
     * 等待获取锁
     *
     * @param name    锁名称
     * @param value   锁值
     * @param timeout 等待时长(秒)
     * @return 锁对象
     */
    Lock waitingLock(String name, String value, long timeout);

    /**
     * 创建锁
     *
     * @param name 锁名称
     * @return 锁对象
     */
    default Lock create(String name) {
        return create(name, UUID.randomUUID().toString());
    }

    /**
     * 等待获取锁
     *
     * @param name    锁名称
     * @param timeout 等待时长(秒)
     * @return 锁对象
     */
    default Lock waitingLock(String name, long timeout) {
        return waitingLock(name, UUID.randomUUID().toString(), timeout);
    }

    /**
     * 创建锁
     *
     * @param name  锁名称
     * @param value 锁值
     * @return 锁
     * @deprecated 功能和 {@link #create(String, String)}重复
     */
    @Deprecated
    default Lock acquireLock(String name, String value) {
        return create(name, value);
    }

    // endregion

    // region 释放锁

    /**
     * 释放锁
     *
     * @param lock 锁对象
     */
    void release(Lock lock);

    /**
     * 释放锁
     *
     * @param name  锁名称
     * @param value 锁值
     */
    default void releaseLock(String name, String value) {
        release(new Lock(name, value, new Date()));
    }

    /**
     * 释放锁
     *
     * @param name  锁名称
     * @param value 锁值
     */
    default void release(String name, String value) {
        release(new Lock(name, value, new Date()));
    }

    // endregion

    // region 延长锁

    /**
     * 更新锁
     *
     * @param lock 锁对象
     * @return 锁对象
     */
    Lock refresh(Lock lock);

    /**
     * 更新锁
     *
     * @param name  名称
     * @param value 值
     * @return Lock
     */
    default Lock refresh(String name, String value) {
        return refresh(Lock.newLock(name, value));
    }

    // endregion

    /**
     * 加锁执行任务
     *
     * @param supplier  带反参任务
     * @param name      锁名称
     * @param errorCode 错误码
     * @param <T>       返回值类型
     * @return 返回值
     */
    default <T> T lock(Supplier<T> supplier, String name, String errorCode) {
        Lock lock = null;
        try {
            lock = create(name);
            return supplier.get();
        } catch (LockExistsException e) {
            errorCode = StringUtils.isEmpty(errorCode) ? MsgCodeConstants.LOCK_EXISTS_ERROR : errorCode;
            throw new ZiRuntimeException(errorCode, name);
        } finally {
            if (Objects.nonNull(lock)) {
                release(lock);
            }
        }
    }

    /**
     * 加锁执行任务
     *
     * @param runnable  任务
     * @param name      锁名称
     * @param errorCode 错误码
     */
    default void lock(Runnable runnable, String name, String errorCode) {
        Lock lock = null;
        try {
            lock = create(name);
            runnable.run();
        } catch (LockExistsException e) {
            errorCode = StringUtils.isEmpty(errorCode) ? MsgCodeConstants.LOCK_EXISTS_ERROR : errorCode;
            throw new ZiRuntimeException(errorCode, name);
        } finally {
            if (Objects.nonNull(lock)) {
                release(lock);
            }
        }
    }

    /**
     * 加锁执行任务
     *
     * @param supplier 带反参任务
     * @param name     锁名称
     * @param <T>      返回值类型
     * @return 返回值
     */
    default <T> T lock(Supplier<T> supplier, String name) {
        return lock(supplier, name, "");
    }

    /**
     * 加锁执行任务
     *
     * @param runnable 任务
     * @param name     锁名称
     */
    default void lock(Runnable runnable, String name) {
        lock(runnable, name, "");
    }
}

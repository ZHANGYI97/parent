package com.ziyi.common.utils;

/**
 * @author zhy
 * @date 2022/7/3
 */
public interface AutoIncreaseCounter {

    /**
     * 计数器
     *
     * @param key     key
     * @param value   自增值
     * @param seconds 有效时间
     * @return 计数结果
     */
    Long increment(String key, Long value, int seconds);

    /**
     * 永久计数器（不推荐使用，计数器持久化存在风险）
     *
     * @param key   key
     * @param value 计数值
     * @return 计数结果
     */
    default Long increment(String key, Long value) {
        return increment(key, value, -1);
    }

    /**
     * 永久计数器（不推荐使用，计数器持久化存在风险）
     *
     * @param key   key
     * @return 计数结果
     */
    default Long increment(String key) {
        return increment(key, 1L, -1);
    }

    /**
     * 计数器 默认+1
     *
     * @param key     缓存key
     * @param seconds 计数有效时间(过期后重新计数)
     * @return 计数结果
     */
    default Long increment(String key, int seconds) {
        return increment(key, 1L, seconds);
    }
}

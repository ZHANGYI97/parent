package com.ziyi.redis.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * auther:jurzis
 * date: 2021/4/29 15:04
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheable {

    /** 第一过期时间 **/
    long firstLayerTtl() default 10L;

    /** 第一过期时间 **/
    long secondLayerTtl() default 60L;

    /** Redis key 值 **/
    String key();

}

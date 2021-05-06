package com.ziyi.redis.aop;

import com.ziyi.common.Constants.Constants;
import com.ziyi.redis.enums.RedisTypeEnum;

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

    /**类型，String,hash等**/
    String type() default Constants.STRING;

    /**为hsah时传入可以推送单条hash**/
    String filed() default Constants.NULL;

}

package com.ziyi.redis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * auther:jurzis
 * date: 2021/4/29 15:14
 */
@Aspect
@Component
public class RedisCacheableAspect {

    private static final Logger log = LoggerFactory.getLogger(RedisCacheableAspect.class);
    private static final ExpressionParser expressionParser = new SpelExpressionParser();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisCacheableAspect(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Pointcut("@annotation(redisCacheable)")
    public void RedisCacheablePointcut(RedisCacheable redisCacheable) {
    }

    private StandardEvaluationContext getContextContainingArguments(ProceedingJoinPoint joinPoint) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 通过Java反射，解析ProceedingJoinPoint的方法参数及参数值
        CodeSignature codeSignature = (CodeSignature)joinPoint.getSignature();
        // 获取入参对象中的所有参数名
        String[] parameterNames = codeSignature.getParameterNames();
        // 获取连接点（joinPoint）的方法运行时的入参列表
        Object[] args = joinPoint.getArgs();

        for(int i = 0; i < parameterNames.length; ++i) {
            context.setVariable(parameterNames[i], null == args[i] ? "null" : args[i]);
        }

        return context;
    }

    private String getCacheKeyFromAnnotationKeyValue(StandardEvaluationContext context, String key) {
        // 表达式解析器，解析注解中的 key 值
        Expression expression = expressionParser.parseExpression(key);
        return (String)expression.getValue(context);
    }

    @Around("RedisCacheablePointcut(redisCacheable)")
    public Object cacheTwoLayered(ProceedingJoinPoint joinPoint, RedisCacheable redisCacheable) throws Throwable {
        // 获取注解中的第一过期时间
        long firstLayerTtl = redisCacheable.firstLayerTtl();
        // 获取注解中的第二过期时间
        long secondLayerTtl = redisCacheable.secondLayerTtl();
        // 获取注解中的 key 值
        String key = redisCacheable.key();
        StandardEvaluationContext context = this.getContextContainingArguments(joinPoint);
        String cacheKey = this.getCacheKeyFromAnnotationKeyValue(context, key);
        log.info("### Cache key: {}", cacheKey);
        // 获取系统当前时间
        long start = System.currentTimeMillis();
        Object result;
        // 如果缓存中存在 当前 key 的数据
        if (this.redisTemplate.hasKey(cacheKey)) {
            // 通过 key 获取 redis 缓存值
            result = this.redisTemplate.opsForValue().get(cacheKey);
            log.info("Reading from cache ..." + result.toString());
            // 当缓存中的剩余过期时间，小于第二过期时间时，不取缓存中的数据，查询数据库
            if (this.redisTemplate.getExpire(cacheKey, TimeUnit.MINUTES) < secondLayerTtl) {
                try {
                    result = joinPoint.proceed();
                    // 将查询结果放入 Redis 缓存，并设置过期时间，过期时间为 第一过期时间+第二过期时间
                    this.redisTemplate.opsForValue().set(cacheKey, result, secondLayerTtl + firstLayerTtl, TimeUnit.MINUTES);
                } catch (Exception var15) {
                    log.warn("An error occured while trying to refresh the value - extending the existing one", var15);
                    this.redisTemplate.opsForValue().getOperations().expire(cacheKey, secondLayerTtl + firstLayerTtl, TimeUnit.MINUTES);
                }
            }
        } else {
            result = joinPoint.proceed();
            log.info("Cache miss: Called original method");
            // 将查询结果放入 Redis 缓存，并设置过期时间，过期时间为 第一过期时间+第二过期时间
            this.redisTemplate.opsForValue().set(cacheKey, result, firstLayerTtl + secondLayerTtl, TimeUnit.MINUTES);
        }
        // 获取执行时间
        long executionTime = System.currentTimeMillis() - start;
        log.info("{} executed in {} ms", joinPoint.getSignature(), executionTime);
        log.info("Result: {}", result);
        return result;
    }

}

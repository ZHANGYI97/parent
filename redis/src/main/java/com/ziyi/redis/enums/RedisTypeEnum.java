package com.ziyi.redis.enums;

/**
 * auther:jurzis
 * date: 2021/4/30 14:39
 */
public enum  RedisTypeEnum {

    STRING("String", "字符串类型"),

    HASH("Hash", "哈希类型");

    private String type;

    private String desc;

    RedisTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}

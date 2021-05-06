package com.ziyi.redis.enums;

import com.ziyi.common.string.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * auther:jurzis
 * date: 2021/4/30 14:39
 */
public enum  RedisTypeEnum {

    STRING("String", "字符串类型"),

    HASH("Hash", "哈希类型");

    private String type;

    private String desc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private static final Map<RedisTypeEnum, String> map = new HashMap();

    RedisTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    static {
        for (RedisTypeEnum redisTypeEnum : values()) {
            map.put(redisTypeEnum, redisTypeEnum.type);
        }
    }

    public static String getTypeByEnum(RedisTypeEnum redisTypeEnum) {
        return map.get(redisTypeEnum);
    }

}

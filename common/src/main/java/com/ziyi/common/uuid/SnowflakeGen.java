package com.ziyi.common.uuid;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 雪花算法生成器
 *
 * @author zhy
 * @date 2021/7/3
 */
public class SnowflakeGen {

    private final Snowflake snowflake;

    /**
     * @param workerId     工作机器ID
     * @param dataCenterId 数据中心ID
     */
    public SnowflakeGen(long workerId, long dataCenterId) {
        this.snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
    }


    /**
     * 下一个ID（字符串形式）
     *
     * @return ID 字符串形式
     */
    public String nextIdStr() {
        return snowflake.nextIdStr();
    }

    /**
     * 下一个ID
     *
     * @return ID
     */
    public long nextId() {
        return snowflake.nextId();
    }
}

package com.ziyi.config.web.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 雪花算法配置
 *
 * @author zhy
 * @date 2022/7/5 21:10
 */


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "zi.snowflake")
public class ZiSnowflakeProperties {
    /**
     * 工作机器ID
     */
    long workId = 1L;
    /**
     * 数据中心ID
     */
    long dataCenterId = 1L;
}

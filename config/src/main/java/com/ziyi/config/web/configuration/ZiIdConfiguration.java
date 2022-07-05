package com.ziyi.config.web.configuration;

import com.ziyi.common.uuid.IdGenUtils;
import com.ziyi.common.uuid.SnowflakeGen;
import com.ziyi.common.uuid.UuidGen;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhy
 * @date 2022/7/5  21:09
 */
@Configuration
@EnableConfigurationProperties(ZiSnowflakeProperties.class)
@ConditionalOnProperty(value = "zi.no.enabled", havingValue = "true", matchIfMissing = true)
public class ZiIdConfiguration {

    @Bean
    public SnowflakeGen snowflakeGen(ZiSnowflakeProperties properties) {
        SnowflakeGen snowflakeGen = new SnowflakeGen(properties.getWorkId(), properties.getDataCenterId());
        IdGenUtils.setSnowflakeGen(snowflakeGen);
        return snowflakeGen;
    }

    @Bean
    public UuidGen uuidGen() {
        UuidGen uuidGen = new UuidGen();
        IdGenUtils.setUuidGen(uuidGen);
        return uuidGen;
    }
}

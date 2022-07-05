package com.ziyi.config.web.configuration;

import com.ziyi.common.utils.AutoIncreaseCounter;
import com.ziyi.common.uuid.IdGenUtils;
import com.ziyi.common.uuid.SerialNoGen;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhy
 * @date 2022/7/5  21:11
 */
@Configuration
@ConditionalOnClass({AutoIncreaseCounter.class})
@ConditionalOnProperty(value = "zi.no.enabled", havingValue = "true", matchIfMissing = true)
public class ZiSerialNoConfiguration {

    @Bean
    @ConditionalOnBean(AutoIncreaseCounter.class)
    public SerialNoGen serialNoGen(AutoIncreaseCounter autoIncreaseCounter) {
        SerialNoGen serialNoGen = new SerialNoGen(autoIncreaseCounter);
        IdGenUtils.setSerialNoGen(serialNoGen);
        return serialNoGen;
    }
}

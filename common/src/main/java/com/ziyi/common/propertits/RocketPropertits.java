package com.ziyi.common.propertits;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author zhy
 */
@Configuration
@ConfigurationProperties(value = "apache.rocketmq")
@Component
@Data
public class RocketPropertits {

    /**
     * 消费者参数
     */
    private RocketConsumer consumer;

    /**
     * 生产者参数
     */
    private RocketProducer producer;

    /**
     * 路由
     */
    private String namesrvAddr;

}

package com.ziyi.common.propertits;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author zhy
 * @data 2022/2/7 6:48 下午
 */
@Configuration
@ConfigurationProperties(value = "netty")
@Component
@Data
public class NettyPropertits {

    private int port;

    private String host;

    private int port1;

}

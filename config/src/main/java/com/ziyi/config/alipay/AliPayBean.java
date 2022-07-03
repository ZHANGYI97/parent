package com.ziyi.config.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/production/alipay.properties")
@ConfigurationProperties(prefix = "alipay")
@Data
public class AliPayBean {
    private String appId;
    private String privateKey;
    private String publicKey;
    private String serverUrl;
    private String domain;
    private String format;
    private String charset;
    private String signType;
    private String appCertPath;
    private String alipayCertPath;
    private String alipayRootCertPath;

}

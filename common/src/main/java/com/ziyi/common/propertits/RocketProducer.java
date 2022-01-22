package com.ziyi.common.propertits;

import lombok.Data;

/**
 * @author zhy
 * @data 2022/1/11 7:57 下午
 */
@Data
public class RocketProducer {

    /**
     * 生产者所在组
     */
    private String producerGroup;

    private String producerSendMsgTimeout;

    private String producerMaxMessageSize;

}

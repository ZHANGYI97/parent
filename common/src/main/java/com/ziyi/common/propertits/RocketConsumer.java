package com.ziyi.common.propertits;

import lombok.Data;


/**
 * @author zhy
 */
@Data
public class RocketConsumer {

    /**
     * 消费者订阅组
     */
    private String pushConsumer;


    private int consumerConsumeThreadMin;

    private int consumerConsumeThreadMax;

    private int consumerConsumeMessageBatchMaxSize;

}

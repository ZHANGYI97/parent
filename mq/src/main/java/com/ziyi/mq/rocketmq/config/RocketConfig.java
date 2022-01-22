package com.ziyi.mq.rocketmq.config;

import com.ziyi.common.propertits.RocketPropertits;
import com.ziyi.mq.rocketmq.consumer.AbstractRocketConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhy
 * 生产者
 */
@Slf4j
@Configuration
public class RocketConfig {

    @Autowired
    RocketPropertits rocketPropertits;

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public DefaultMQProducer producer() {
        DefaultMQProducer producer;
        //1：创建一个默认的生产者对象--作用用于生成消息
        producer = new DefaultMQProducer(rocketPropertits.getProducer().getProducerGroup());
        //2:绑定生产者和nameserver，就是建立和broker程序的关系
        producer.setNamesrvAddr(rocketPropertits.getNamesrvAddr());
        //3:发送消息
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }

    /**
     * SpringBoot启动时加载所有消费者
     */
    @PostConstruct
    public void initConsumer() {
        Map<String, AbstractRocketConsumer> consumers = applicationContext.getBeansOfType(AbstractRocketConsumer.class);
        if (consumers == null || consumers.size() == 0) {
            log.info("init rocket consumer 0");
        }
        Iterator<String> beans = consumers.keySet().iterator();
        while (beans.hasNext()) {
            String beanName = beans.next();
            AbstractRocketConsumer consumer = consumers.get(beanName);
            consumer.init();
            createConsumer(consumer);
            log.info("init success consumer title {} , toips {} , tags {}", consumer.consumerTitel, consumer.tags,
                    consumer.topics);
        }
    }

    /**
     * 通过消费者信心创建消费者
     *
     * @param arc
     */
    public DefaultMQPushConsumer createConsumer(AbstractRocketConsumer arc) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.rocketPropertits.getConsumer().getPushConsumer());
        consumer.setNamesrvAddr(rocketPropertits.getNamesrvAddr());
        consumer.setConsumeThreadMin(rocketPropertits.getConsumer().getConsumerConsumeThreadMin());
        consumer.setConsumeThreadMax(rocketPropertits.getConsumer().getConsumerConsumeThreadMax());
        consumer.registerMessageListener(arc.messageListener);
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        // consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        /**
         * 设置消费模型，集群还是广播，默认为集群
         */
        // consumer.setMessageModel(MessageModel.CLUSTERING);

        /**
         * 设置一次消费消息的条数，默认为1条
         */
        consumer.setConsumeMessageBatchMaxSize(this.rocketPropertits.getConsumer().getConsumerConsumeMessageBatchMaxSize());
        try {
            consumer.subscribe(arc.topics, arc.tags);
            startConsumer(consumer);
            arc.mqPushConsumer=consumer;
        } catch (MQClientException e) {
            log.error("info consumer title {}", arc.consumerTitel, e);
        }
        return consumer;
    }

    /**
     * 启动消费者
     * @param consumer
     */
    public void startConsumer(DefaultMQPushConsumer consumer) {
        try {
            consumer.start();
        } catch (MQClientException e) {
            log.error("info consumer title {}",consumer.getMessageListener(), e);
        }
    }

}

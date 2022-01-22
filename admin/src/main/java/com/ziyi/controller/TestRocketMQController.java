package com.ziyi.controller;

import com.ziyi.common.bean.JsonData;
import com.ziyi.mq.rocketmq.RocketProducerClient;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * auther:jurzis
 * date: 2021/4/15 18:51
 */
@RestController
@RequestMapping("/rocket")
@RequiredArgsConstructor
public class TestRocketMQController {

    final RocketProducerClient rocketProducerClient;

    @GetMapping("/order")
    public Object order(@RequestParam String msg, @RequestParam String tag) {
        SendResult send = rocketProducerClient.sendMsg(msg, "orderTopic", tag);
        System.out.println("消息ID" + send.getMsgId() + "消息发送状态" + send.getSendStatus());
        return JsonData.buildSuccess();
    }

}

package com.ziyi.controller;

import com.ziyi.common.bean.JsonData;
import com.ziyi.netty.client.NettyClient;
import com.ziyi.netty.upback.client.UpbackNettyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhy
 * @data 2022/2/7 7:01 下午
 */
@RestController
@RequestMapping("/netty")
@RequiredArgsConstructor
public class TestNettyController {

    final NettyClient nettyClient;

    final UpbackNettyClient upbackNettyClientUpback;

    @PostMapping("/send")
    public Object send(@RequestBody String msg) {
        nettyClient.send(msg);
        return JsonData.buildSuccess();
    }

    @PostMapping("/sendUpback")
    public Object sendUpback(@RequestBody String msg) {
        upbackNettyClientUpback.send(msg);
        return JsonData.buildSuccess();
    }

}

package com.ziyi.netty.client;

import com.ziyi.common.propertits.NettyPropertits;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Title: NettyClient
 * Description:
 * Netty客户端
 * Version:1.0.0
 *
 * @author pancm
 * @date 2017-8-31
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class NettyClient {

    final NettyPropertits nettyPropertits;

    /**
     * Netty创建全部都是实现自AbstractBootstrap。
     * 客户端的是Bootstrap，服务端的则是    ServerBootstrap。
     **/
    public void send(String msg) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        System.out.println("客户端成功启动...");
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new NettyClientFilter());
        // 连接服务端
        try {
            Channel channel = bootstrap.connect(nettyPropertits.getHost(), nettyPropertits.getPort()).sync().channel();
            channel.writeAndFlush(msg + "\r\n");
            System.out.println("客户端发送数据:" + msg);
        } catch (Exception e) {
            log.error("发送客户端消息失败");
        }
    }

}

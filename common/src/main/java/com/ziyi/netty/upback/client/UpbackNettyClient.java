package com.ziyi.netty.upback.client;

import com.ziyi.common.propertits.NettyPropertits;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Title: NettyClient
 * Description: Netty客户端  用于测试粘包、拆包
 * Version:1.0.0
 *
 * @author pancm
 * @date 2017年9月20日
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UpbackNettyClient {

    final NettyPropertits nettyPropertits;

    /**
     * Netty创建全部都是实现自AbstractBootstrap。
     * 客户端的是Bootstrap，服务端的则是ServerBootstrap。
     **/
    public void send(String args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        Channel ch;
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new StringDecoder());    //绑定解码器
                        p.addLast(new NettyClientHandler());   //绑定自定义业务
                    }
                });
        // 连接服务端
        try {
            ch = b.connect(nettyPropertits.getHost(), nettyPropertits.getPort()).sync().channel();
            System.out.println("客户端成功启动...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("客户端发送消息失败");
        }
    }
}

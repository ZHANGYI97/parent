package com.ziyi.netty.server;

import com.ziyi.common.propertits.NettyPropertits;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author zhy
 * @data 2022/2/7 7:01 下午
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Order(value = 2)
public class NettyServer implements CommandLineRunner {


    final NettyPropertits nettyPropertits;

    EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    ServerBootstrap serverBootstrap = new ServerBootstrap();

    /**
     * Netty创建全部都是实现自AbstractBootstrap。
     * 客户端的是Bootstrap，服务端的则是    ServerBootstrap。
     **/
    public void start() throws InterruptedException {
        try {
            serverBootstrap.group(eventLoopGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new NettyServerFilter());
            // 服务器绑定端口监听
            ChannelFuture f = serverBootstrap.bind(nettyPropertits.getPort()).sync();
            System.out.println("服务端启动成功...");
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully(); ////关闭EventLoopGroup，释放掉所有资源包括创建的线程
        }
    }

    @Override
    public void run(String... args) throws Exception {
        this.start();
    }
}

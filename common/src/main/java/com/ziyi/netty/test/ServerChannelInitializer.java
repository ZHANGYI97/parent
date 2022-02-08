package com.ziyi.netty.test;

/**
 * @author zhy
 * @data 2022/2/7 8:43 下午
 */
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerChannelInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 自己的逻辑Handler
        pipeline.addLast("handler", new PeServerHandler());
    }
}

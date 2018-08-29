package com.paulzhangcc.zookeeper;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * @author paul
 * @description
 * @date 2018/7/26
 */
public class QosServerTest {
    public static void main(String[] args) throws Throwable {

        NioEventLoopGroup boss = new NioEventLoopGroup(1, new DefaultThreadFactory("qos-boss", true));
        NioEventLoopGroup worker = new NioEventLoopGroup(1, new DefaultThreadFactory("qos-worker", true));
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker);
        serverBootstrap.channel(NioServerSocketChannel.class);
        //serverBootstrap.handler(new LoggerChannelHandler());
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
        serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                System.out.println(Thread.currentThread().getName()+"=====================initChannel===="+ch+"  channel="+ch.getClass().getSimpleName());
                //ch.pipeline().addLast(new LineBasedFrameDecoder(2048));
                //ch.pipeline().addLast(new ClientLoggerChannelHandler());
                ch.pipeline().addLast(new MyQosProcessHandler("", true));
            }
        });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(22222).sync();
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Throwable throwable) {
            throw throwable;
        }
    }
}

package com.paulzhangcc.zookeeper;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author paul
 * @description
 * @date 2018/7/30
 */
public class LoggerChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        System.out.println(Thread.currentThread().getName()+"=====================channelRegistered"+ctx.channel()+"  channel="+ctx.channel().getClass().getSimpleName());
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getName()+"=====================channelUnregistered==="+ctx.channel()+"  channel="+ctx.channel().getClass().getSimpleName());
        ctx.fireChannelUnregistered();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getName()+"=====================channelActive"+ctx.channel()+"  channel="+ctx.channel().getClass().getSimpleName());
        ctx.fireChannelActive();
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getName()+"=====================channelInactive"+ctx.channel()+"  channel="+ctx.channel().getClass().getSimpleName());
        ctx.fireChannelInactive();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(Thread.currentThread().getName()+"=====================channelRead"+ctx.channel()+"  channel="+ctx.channel().getClass().getSimpleName());
        ctx.fireChannelRead(msg);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getName()+"=====================channelReadComplete"+ctx.channel()+"  channel="+ctx.channel().getClass().getSimpleName());
        ctx.fireChannelReadComplete();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println(Thread.currentThread().getName()+"=====================userEventTriggered"+ctx.channel()+"  channel="+ctx.channel().getClass().getSimpleName());
        ctx.fireUserEventTriggered(evt);
    }


    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getName()+"=====================channelWritabilityChanged"+ctx.channel()+"  channel="+ctx.channel().getClass().getSimpleName());
        ctx.fireChannelWritabilityChanged();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println(Thread.currentThread().getName()+"=====================exceptionCaught"+ctx.channel()+"  channel="+ctx.channel().getClass().getSimpleName());
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // NOOP
        System.out.println(Thread.currentThread().getName()+"=====================handlerAdded"+ctx.channel()+"  channel="+ctx.channel().getClass().getSimpleName());
    }

    /**
     * Do nothing by default, sub-classes may override this method.
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // NOOP
        System.out.println(Thread.currentThread().getName()+"=====================handlerRemoved"+ctx.channel()+"  channel="+ctx.channel().getClass().getSimpleName());
    }

}

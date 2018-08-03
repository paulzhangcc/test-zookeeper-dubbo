package com.paulzhangcc.zookeeper;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.Values.CHUNKED;
import static io.netty.handler.codec.http.HttpHeaders.Values.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;

/**
 * @author paul
 * @description
 * @date 2018/8/2
 */
public class TestServerKeepalive {
    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(1), new NioEventLoopGroup(10));
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.childOption(ChannelOption.SO_REUSEADDR, true);

        serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                        .addLast(new HttpServerCodec())
                        .addLast(new HttpObjectAggregator(512 * 1024))
                        .addLast(new HttpRequestHandler());
            }
        });
        ChannelFuture channelFuture = serverBootstrap.bind(22222).sync();
    }

    private static class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
            DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            HttpHeaders headers = response.headers();
            boolean keepAlive = isKeepAlive(fullHttpRequest);
            if (keepAlive) {
                headers.add(CONTENT_TYPE, "text/plain;charset=utf-8");
                headers.add(CONNECTION, KEEP_ALIVE);

                //传输完成之后就会关闭
                //headers.add(CONNECTION, io.netty.handler.codec.http.HttpHeaders.Values.CLOSE);
                //headers.add(TRANSFER_ENCODING, CHUNKED);

                //headers.add(CONNECTION, io.netty.handler.codec.http.HttpHeaders.Values.CLOSE);
                headers.add(TRANSFER_ENCODING, CHUNKED);
                //headers.add(CONTENT_LENGTH, 12 * 10);//+1的原因是最后一个分块的10占两个字节
            }

            byte[] body = new byte[fullHttpRequest.content().readableBytes()];

            fullHttpRequest.content().getBytes(0, body);
            System.out.println(ctx.channel()+":请求的内容:"+ new String(body));
            body=null;
            ctx.writeAndFlush(response);//写响应行和响应头

            System.out.println(ctx.channel()+":响应头和行===");
            for (int i = 1; i < 3; i++) {
                HttpContent httpContent = new DefaultHttpContent(Unpooled.copiedBuffer("zjf" + i, CharsetUtil.UTF_8));
                ctx.writeAndFlush(httpContent);//分批次写响应体，每次运行完此行代码，浏览器页面也会显示最新的响应内容
            }
            ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT)
                    ;//.addListener(ChannelFutureListener.CLOSE);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("========channelActive=====" + ctx.channel());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("========channelInactive=====" + ctx.channel());
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("========channelUnregistered=====" + ctx.channel());
        }
    }
}

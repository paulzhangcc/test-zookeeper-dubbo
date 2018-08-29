package com.paulzhangcc.zookeeper;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.qos.command.CommandExecutor;
import com.alibaba.dubbo.qos.command.DefaultCommandExecutor;
import com.alibaba.dubbo.qos.common.QosConstants;
import com.alibaba.dubbo.qos.server.handler.QosProcessHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * @author paul
 * @description
 * @date 2018/8/17
 */

/**
 * Telnet process handler
 */
public class MyTelnetProcessHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger log = LoggerFactory.getLogger(com.alibaba.dubbo.qos.server.handler.TelnetProcessHandler.class);
    private static CommandExecutor commandExecutor = new DefaultCommandExecutor();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        if (StringUtils.isBlank(msg)) {
            ctx.writeAndFlush(QosProcessHandler.prompt);
        } else {
            ctx.writeAndFlush(msg + " :no such command");
            ctx.writeAndFlush(QosConstants.BR_STR + QosProcessHandler.prompt);
        }
    }
}

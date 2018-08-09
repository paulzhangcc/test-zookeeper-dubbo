/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.paulzhangcc.zookeeper;


import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Telnet process handler
 */
public class TimeSendHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger log = LoggerFactory.getLogger(TimeSendHandler.class);

    public AtomicBoolean flag = new AtomicBoolean(false);


    public final static ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//        boolean compareAndSet = flag.compareAndSet(false, true);
//        final Channel channel = ctx.channel();
//        if (compareAndSet){
//            timer.scheduleAtFixedRate(new Runnable() {
//                @Override
//                public void run() {
//                    channel.writeAndFlush("nihao\r\n");
//                }
//            }, 1000, 2000, TimeUnit.MILLISECONDS);
//        }
        ctx.fireChannelRead(msg);
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ctx.fireUserEventTriggered(evt);
    }



}

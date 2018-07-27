package com.paulzhangcc.zookeeper;

import com.alibaba.dubbo.qos.server.Server;

/**
 * @author paul
 * @description
 * @date 2018/7/26
 */
public class QosServerTest {
    public static void main(String[] args) throws Throwable {
        Server server = com.alibaba.dubbo.qos.server.Server.getInstance();
        server.setPort(22222);
        server.setAcceptForeignIp(true);
        server.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}

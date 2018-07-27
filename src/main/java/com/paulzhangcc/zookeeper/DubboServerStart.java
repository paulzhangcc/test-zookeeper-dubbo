package com.paulzhangcc.zookeeper;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

/**
 * @author paul
 * @description
 * @date 2018/7/25
 */
public class DubboServerStart {
    public static void main(String[] args) throws InterruptedException {
        HelloService helloService = new HelloServiceImpl();

        ApplicationConfig application = new ApplicationConfig();
        application.setName("HELLO");

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("127.0.0.1:2181");
        registry.setProtocol("zookeeper");

        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(18569);
        protocol.setThreads(20);

        ServiceConfig<HelloService> service = new ServiceConfig<HelloService>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(HelloService.class);
        service.setRef(helloService);
        service.setVersion("1.0.0");

        service.export();

        Thread.sleep(Integer.MAX_VALUE);
    }
}

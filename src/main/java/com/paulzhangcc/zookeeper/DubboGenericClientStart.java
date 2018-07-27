package com.paulzhangcc.zookeeper;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;

/**
 * @author paul
 * @description
 * @date 2018/7/25
 */
public class DubboGenericClientStart {
    public static void main(String[] args) throws InterruptedException {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("HELLO");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("127.0.0.1:2181");
        registry.setProtocol("zookeeper");
        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
        // 引用远程服务
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        //reference.setInterface(HelloService.class);
        reference.setVersion("1.0.0");
        reference.setGeneric(true);
        reference.setInterface("com.paulzhangcc.zookeeper.HelloService");

        // 和本地bean一样使用xxxService
        GenericService genericService = reference.get();
        Object ping = genericService.$invoke("ping", new String[]{"java.lang.String"}, new Object[]{"nihao"});
        System.out.println(ping);

        Thread.sleep(Integer.MAX_VALUE);
    }
}

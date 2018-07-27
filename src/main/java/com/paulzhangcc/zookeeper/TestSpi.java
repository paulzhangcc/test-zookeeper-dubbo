package com.paulzhangcc.zookeeper;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionFactory;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.registry.RegistryFactory;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.ProxyFactory;
import com.alibaba.dubbo.rpc.cluster.Cluster;

/**
 * @author paul
 * @description
 * @date 2018/7/25
 */
public class TestSpi {
    public static void main(String[] args) {
        ExtensionFactory extensionFactory = ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension();

        Cluster cluster = ExtensionLoader.getExtensionLoader(Cluster.class).getAdaptiveExtension();
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

        Protocol refprotocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

        Protocol registry = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("registry");

        RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
        String urlStr = "registry://127.0.0.1:2181/com.alibaba.dubbo.registry.RegistryService?application=HELLO&dubbo=2.6.2&pid=22004&refer=application%3DHELLO%26dubbo%3D2.6.2%26interface%3Dcom.paulzhangcc.zookeeper.HelloService%26methods%3Dping%26pid%3D22004%26register.ip%3D192.168.181.1%26revision%3D1.0.0%26side%3Dconsumer%26timestamp%3D1532586285020%26version%3D1.0.0&registry=zookeeper&timestamp=1532586286137";

        URL url = URL.valueOf(urlStr);

        String decode = URL.decode(urlStr);
        Invoker<HelloService> invoker = refprotocol.refer(HelloService.class, url);
        //ExtensionLoader<ExtensionFactory> extensionLoader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);

        //ExtensionFactory adaptiveExtension = extensionLoader.getAdaptiveExtension();

        //ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        //Protocol refprotocol = extensionLoader.getAdaptiveExtension();
        //Cluster cluster = ExtensionLoader.getExtensionLoader(Cluster.class).getAdaptiveExtension();
        //ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
    }
}

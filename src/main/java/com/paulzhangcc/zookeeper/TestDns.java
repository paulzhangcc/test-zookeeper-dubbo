package com.paulzhangcc.zookeeper;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author paul
 * @description
 * @date 2018/7/31
 */
public class TestDns {
    public static void main(String[] args) throws UnknownHostException {

        InetAddress[] address =InetAddress.getAllByName("www.baidu.com");
        for (InetAddress inetAddress : address){
            System.out.println(inetAddress);
        }
    }
}

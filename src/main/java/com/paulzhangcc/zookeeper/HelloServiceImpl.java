package com.paulzhangcc.zookeeper;

/**
 * @author paul
 * @description
 * @date 2018/7/25
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String ping(String pingStr) {
        return pingStr+"..........hello world";
    }
}

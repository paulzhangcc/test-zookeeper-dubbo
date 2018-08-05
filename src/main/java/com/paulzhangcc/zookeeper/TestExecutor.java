package com.paulzhangcc.zookeeper;

import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by paul on 2018/8/5.
 */
public class TestExecutor {
    public static void main(String[] args) {

        GlobalEventExecutor instance = GlobalEventExecutor.INSTANCE;
        System.out.println(instance);
        instance.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"nihao");
            }
        });
    }
}

package com.paulzhangcc.zookeeper;

import java.nio.ByteBuffer;

/**
 * @author paul
 * @description
 * @date 2018/7/31
 */
public class TestBuffer {
    static {
        System.out.println("nihao");
    }
    public static void main(String[] args) {
        ByteBuffer writeBuf = ByteBuffer.allocateDirect(1* 1024);
        writeBuf.put("nihao".getBytes());
        writeBuf.flip();
        byte b = writeBuf.get();
        System.out.println((char) b);
         b = writeBuf.get();
        System.out.println((char) b);
        writeBuf.clear();
        writeBuf.put("haha".getBytes());
    }
}

package com.paulzhangcc.zookeeper;

import org.jboss.netty.util.internal.ConcurrentHashMap;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author paul
 * @description
 * @date 2018/7/31
 */
public class TelnetNioServer {

    public static final ConcurrentHashMap<SelectionKey, LinkedBlockingQueue<String>> responseQueue = new ConcurrentHashMap();

    public static void main(String args[]) throws Exception {

        ByteBuffer requestBuffer = ByteBuffer.allocateDirect(4 * 1024);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 22222));

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int select = selector.select(TimeUnit.SECONDS.toMillis(5));
            if (select == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    ServerSocketChannel sChannel = (ServerSocketChannel) next.channel();
                    SocketChannel clientChannel = sChannel.accept();
                    clientChannel.configureBlocking(false);
                    SelectionKey selectionKey = clientChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    responseQueue.put(selectionKey, new LinkedBlockingQueue<String>());
                    System.out.println("客户端请求连接:"+clientChannel);

                } else if ((next.readyOps() & (SelectionKey.OP_READ | SelectionKey.OP_WRITE)) != 0) {
                    SocketChannel clientChannel = (SocketChannel) next.channel();
                    if (next.isReadable() && next.isValid()) {
                        try {
                            int bytesRcvd = 0;
                            StringBuffer stringBuffer = new StringBuffer();
                            //如果requestBuffer定义的小，就需要是用while不断的进行去read
                            if ((bytesRcvd = clientChannel.read(requestBuffer)) > 0) {
                                requestBuffer.flip();
                                byte temp[] = new byte[bytesRcvd];
                                requestBuffer.get(temp);
                                requestBuffer.clear();

                                String s = new String(temp);
                                System.out.println("收到的内容为:" + s);
                                temp = null;
                                responseQueue.get(next).put("@@@@@:" + s.trim());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (next.isWritable() && next.isValid()) {
                        LinkedBlockingQueue<String> strings = responseQueue.get(next);
                        if (strings != null && strings.size() > 0) {
                            String take = null;
                            while ((take = strings.poll()) != null){
                                ByteBuffer wrap = ByteBuffer.wrap(take.getBytes(Charset.forName("UTF-8")));
                                while (wrap.hasRemaining()) {
                                    clientChannel.write(wrap);
                                }
                                wrap.clear();
                                wrap = null;
                            }
                        }
                    }
                } else if (next.isValid()) {
                    System.out.println("@@@@@@:isValid" + selectionKeys);
                }
                iterator.remove();
            }

        }

        //selector.close();
        //clntChan.close();


    }
}


package com.paulzhangcc.zookeeper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author paul
 * @description
 * @date 2018/7/31
 */
public class TelnetNioClient {

    public static void main(String args[]) throws Exception {

        ByteBuffer resposneBuffer = ByteBuffer.allocateDirect(4 * 1024);
        SocketChannel clntChan = SocketChannel.open();
        clntChan.configureBlocking(false);
        Selector selector = Selector.open();
        clntChan.register(selector, SelectionKey.OP_CONNECT);
        clntChan.connect(new InetSocketAddress("127.0.0.1", 22222));

        System.out.println("===============");
        while (true) {
            int select = selector.select(TimeUnit.SECONDS.toMillis(5));
            if (select == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isConnectable()) {

                    if (clntChan.isConnectionPending()) {
                        //完成连接的建立（TCP三次握手）
                        clntChan.finishConnect();
                    }
                    next.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    System.out.println("@@@@@@:isConnectable" + selectionKeys);
                } else if ((next.readyOps() & (SelectionKey.OP_READ | SelectionKey.OP_WRITE)) != 0) {
                    if (next.isReadable()) {
                        try {
                            int bytesRcvd = 0;
                            StringBuffer stringBuffer = new StringBuffer();
                            if ((bytesRcvd = clntChan.read(resposneBuffer)) > 0) {
                                resposneBuffer.flip();
                                byte temp[] = new byte[bytesRcvd];
                                resposneBuffer.get(temp);
                                resposneBuffer.clear();

                                String s = new String(temp);
                                System.out.println("收到的内容为:" + s);
                                temp = null;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (next.isWritable()) {
//                        ByteBuffer wrap = ByteBuffer.wrap("ls\r\n".getBytes(Charset.forName("UTF-8")));
//                        clntChan.write(wrap);
//                        wrap = null;
                    }
                } else if (next.isValid()) {
                    System.out.println("@@@@@@:isValid" + selectionKeys);
                }
                iterator.remove();
            }

        }

    }
}


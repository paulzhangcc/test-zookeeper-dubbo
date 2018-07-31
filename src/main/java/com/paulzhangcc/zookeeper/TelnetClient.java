package com.paulzhangcc.zookeeper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author paul
 * @description
 * @date 2018/7/31
 */
public class TelnetClient {

    public static final LinkedBlockingQueue<String> requestQueue = new LinkedBlockingQueue<String>();


    public static void main(String args[]) throws Exception {

        SocketChannel clntChan = SocketChannel.open();
        clntChan.configureBlocking(false);
        //向服务端发起连接
        if (!clntChan.connect(new InetSocketAddress("127.0.0.1", 22222))) {
            //不断地轮询连接状态，直到完成连接
            while (!clntChan.finishConnect()) {
                //在等待连接的时间里，可以执行其他任务，以充分发挥非阻塞IO的异步特性
                //这里为了演示该方法的使用，只是一直打印"."
                System.out.print(".");
            }
        }
        //分别实例化用来读写的缓冲区
        ByteBuffer resposneBuffer = ByteBuffer.allocateDirect(4 * 1024);
        ByteBuffer requestBuffer = ByteBuffer.allocateDirect(4 * 1024);

        Thread writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        String takeRequest = requestQueue.take();
                        byte[] bytes = takeRequest.getBytes(Charset.forName("UTF-8"));
                        requestBuffer.put(bytes);
                        //加入回车
                        requestBuffer.put((byte) '\r');
                        requestBuffer.put((byte) '\n');
                        requestBuffer.flip();
                        clntChan.write(requestBuffer);
                        requestBuffer.clear();
                        System.out.println("向远程发送数据:" + takeRequest);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        writeThread.start();

        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        int bytesRcvd = 0;
                        StringBuffer stringBuffer = new StringBuffer();
                        if ((bytesRcvd = clntChan.read(resposneBuffer)) > 0){
                            resposneBuffer.flip();
                            byte temp[]=new byte[bytesRcvd];
                            resposneBuffer.get(temp);
                            resposneBuffer.clear();

                            String s = new String(temp);
                            System.out.println("收到的内容为:"+s);
                            temp = null;
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }
        });
        readThread.start();
        requestQueue.put("ls");

        //clntChan.close();
    }
}


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

        final SocketChannel clntChan = SocketChannel.open();

        //向服务端发起连接
        clntChan.connect(new InetSocketAddress("127.0.0.1", 22222));
        clntChan.finishConnect();
        //clntChan.configureBlocking(false);
        //分别实例化用来读写的缓冲区
        final ByteBuffer resposneBuffer = ByteBuffer.allocateDirect(4 * 1024);
        final ByteBuffer requestBuffer = ByteBuffer.allocateDirect(4 * 1024);

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
                        while (requestBuffer.hasRemaining()){
                            clntChan.write(requestBuffer);
                        }
                        requestBuffer.clear();
                        System.out.println("send:" + takeRequest);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        writeThread.setName("writeThread");
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
                            System.out.println("receive:"+s);
                            temp = null;
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }
        });
        readThread.setName("readThread");
        readThread.start();
        requestQueue.put("ls");

        Thread.sleep(Integer.MAX_VALUE);
        //clntChan.close();
    }
}


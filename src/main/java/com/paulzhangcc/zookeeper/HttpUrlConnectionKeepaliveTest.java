package com.paulzhangcc.zookeeper;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author paul
 * @description
 * @date 2018/8/3
 */
public class HttpUrlConnectionKeepaliveTest {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://127.0.0.1:22222");

        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setUseCaches(false);
        httpUrlConnection.setRequestProperty("Connection", "keep-alive");
        //httpUrlConnection.connect();
        OutputStream outStrm = httpUrlConnection.getOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outStrm);
        bufferedOutputStream.write("nihao".getBytes(Charset.forName("UTF-8")));
        bufferedOutputStream.flush();

        httpUrlConnection.getInputStream().close();
        //httpUrlConnection.disconnect();

        httpUrlConnection = (HttpURLConnection) url.openConnection();

        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setRequestProperty("Connection", "keep-alive");
        //httpUrlConnection.connect();
        outStrm = httpUrlConnection.getOutputStream();
        bufferedOutputStream = new BufferedOutputStream(outStrm);
        bufferedOutputStream.write("nihao".getBytes(Charset.forName("UTF-8")));
        bufferedOutputStream.flush();
        httpUrlConnection.getInputStream().close();

        Thread.sleep(Integer.MAX_VALUE);

    }
}

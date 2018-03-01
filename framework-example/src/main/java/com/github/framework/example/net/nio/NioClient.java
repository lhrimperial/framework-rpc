package com.github.framework.example.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 *
 */
public class NioClient {
    private static final String host = "127.0.0.1";
    private static final int port = 8080;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    private void connect(String host, int port) {
        SocketChannel sc = null;

        try {
            sc = SocketChannel.open();
            sc.connect(new InetSocketAddress(host, port));
           /* while (true) {
                byte[] bytes = new byte[1024];
                System.in.read(bytes);
                //把输入的数据放入buffer缓冲区
                buffer.put(bytes);
                //复位操作
                buffer.flip();
                //将buffer的数据写入通道
                sc.write(buffer);
                //清空缓冲区中的数据
                buffer.clear();
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sc != null) {
                    sc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        new NioClient().connect(host, port);
    }
}

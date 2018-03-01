package com.github.framework.example.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SelectionKey.OP_ACCEPT —— 接收连接继续事件，表示服务器监听到了客户连接，服务器可以接收这个连接了(SocketChannel不支持)
 * SelectionKey.OP_CONNECT —— 连接就绪事件，表示客户与服务器的连接已经建立成功
 * SelectionKey.OP_READ —— 读就绪事件，表示通道中已经有了可读的数据，可以执行读操作了（通道目前有数据，可以进行读操作了）
 * SelectionKey.OP_WRITE —— 写就绪事件，表示已经可以向通道写数据了（通道目前可以用于写操作）
 */
public class NioServer {

    private int port;
    private Selector selector;
    private Buffer buffer = ByteBuffer.allocate(1024);
    private ExecutorService executor = Executors.newFixedThreadPool(3);

    public NioServer(int port) {
        this.port = port;
    }

    private void start() {
        init();
        //主线程作为acceptor
        while (true) {
            try {
                //开始监听多路复用选择器
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    //如果key的状态是有效的
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            accept(key);
                        } else {
                            executor.submit(new NioServerHandler(key));
                        }
                    }

                    keys.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void accept(SelectionKey key) {
        try {
            //1 获取服务器通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            System.out.println("accept " + Thread.currentThread().getName() + ".....");
            //2 执行阻塞方法
            SocketChannel sc = ssc.accept();
            //3 设置阻塞模式为非阻塞
            sc.configureBlocking(false);
            //4 注册到多路复用选择器上，并设置读取标识
            sc.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE|SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            //打开服务器通道
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //设置为非阻塞模式
            ssc.configureBlocking(false);
            //绑定地址
            ssc.bind(new InetSocketAddress(port));
            //打开多路复用器
            selector = Selector.open();
            //把服务器通道注册到多路复用选择器上，并监听链接状态
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server started .... success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new NioServer(8080).start();
    }

    public static class NioServerHandler implements Runnable {

        private SelectionKey selectionKey;
        private String threadName = Thread.currentThread().getName();

        public NioServerHandler(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
        }

        @Override
        public void run() {
            if (selectionKey.isAcceptable()) {
                connect();
            }
            if (selectionKey.isReadable()) {
                read();
            }
            if (selectionKey.isWritable()) {
                write();
            }

        }

        private void read() {
            System.out.println(threadName + " read something ....");
        }

        private void write() {
            System.out.println(threadName + " write something ....");
        }

        private void connect() {
            System.out.println(threadName + " connect ....");
        }
    }
}

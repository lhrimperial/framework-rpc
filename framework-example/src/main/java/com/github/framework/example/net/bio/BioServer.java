package com.github.framework.example.net.bio;

import com.github.framework.example.net.AbstractNetwork;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 即使是通过线程池来处理链接，如果链接一直不断开，线程池中的线程就会一直被占用
 * 一旦连接数大于线程池中线程数量，后续的链接将不能处理
 *
 * 链接不断一直由同一个线程处理同一个请求
 *
 * 例子中如果启动client数量大于3，后面的链接将无法处理
 */
public class BioServer {

    private int port;
    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    public BioServer(int port) {
        this.port = port;
    }

    public void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                socket = serverSocket.accept();
                executorService.submit(new ServerHandler(socket));
            }
        } catch (Exception e) {

        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ServerHandler extends AbstractNetwork implements Runnable {

        private Socket socket;

        public ServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                System.out.println(socket.toString());

                String threadName = Thread.currentThread().getName();
                String requestName = socket.getInetAddress().getHostName();
                String readerInfo = null;
                while (true) {
                    readerInfo = reader.readLine();
                    if (readerInfo == null) {
                        break;
                    }
                    System.out.println("接收到客户端消息：" + readerInfo + ",处理线程：" + threadName);

                    writer.println(requestName + " 你的请求已处理，处理线程：" + threadName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(reader, writer, socket);
            }

        }
    }

    public static void main(String[] args){
        new BioServer(8080).start();
    }

}

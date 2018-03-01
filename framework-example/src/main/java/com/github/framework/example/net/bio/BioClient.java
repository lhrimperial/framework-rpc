package com.github.framework.example.net.bio;

import com.github.framework.example.net.AbstractNetwork;

import java.io.*;
import java.net.Socket;

/**
 *
 */
public class BioClient extends AbstractNetwork {
    public static final String host = "127.0.0.1";
    public static final int port = 8080;

    public static void main(String[] args){
        new BioClient().connect(host, port);
    }

    public void connect(String host, int port) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        Socket socket = null;
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("客户端请求服务器。。。。");

            while (true) {
                writer.println("Hello Server, I am " + socket.getInetAddress());
                String response = reader.readLine();
                System.out.println("server return message : " + response);

                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           close(reader, writer, socket);
        }
    }
}

package com.github.framework.example.netty.handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 *
 */
public class NettyHandlerServer {

    private int port;

    public NettyHandlerServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap().group(boss, worker).channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port)).option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new ChannelInboundHandlerAdapter(){
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            System.out.println("NettyHandlerServer active");
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println("server channelRead..");
                                            System.out.println(ctx.channel().remoteAddress()+"->Server :"+ msg.toString());
                                            ctx.write("server write"+msg);
                                            ctx.flush();
                                        }
                                    });
                        }
                    }).childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = server.bind().sync();
            System.out.println("server start listen at 8088");
            future.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        new NettyHandlerServer(8088).start();
    }
}

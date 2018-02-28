package com.github.framework.example.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 *
 */
public class BaseServer {
    private int port;

    public BaseServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap().group(boss, worker).localAddress(port)
                    .channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
//                                    .addLast("decoder", new StringDecoder())
                                    //TCP 粘包 和 拆包 解决方案
                                    //发送端给远程端一个标记，告诉远程端，每个信息的结束标志是什么，这样，远程端获取到数据后，根据跟发送端约束的标志，
                                    //将接收的信息分切或者合并成我们需要的信息，这样我们就可以获取到正确的信息了
                                    //按行解码
                                    .addLast(new LineBasedFrameDecoder(2048))
                                    //按特殊字符
//                                    .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("$$__".getBytes())))
                                    //按固定长度
//                                    .addLast(new FixedLengthFrameDecoder(23))
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new ChannelInboundHandlerAdapter(){
                                        private int counter;

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            String body = (String)msg;
                                            System.out.println("server receive order : " + body + ";the counter is: " + ++counter);
                                        }
                                    });
                        }
                    }).childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = server.bind().sync();
            System.out.println("server started listen at " + port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        new BaseServer(8080).start();
    }
}

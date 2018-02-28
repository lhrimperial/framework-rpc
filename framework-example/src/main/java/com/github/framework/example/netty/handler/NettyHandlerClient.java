package com.github.framework.example.netty.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 *
 */
public class NettyHandlerClient {

    public static final String HOST = System.getProperty("host", "127.0.0.1");
    public static final int PORT = Integer.parseInt(System.getProperty("port", "8088"));

    public static void main(String[] args){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap().group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                                .handler(new ChannelInitializer<NioSocketChannel>() {
                                    @Override
                                    protected void initChannel(NioSocketChannel ch) throws Exception {
                                        ch.pipeline()
                                                .addLast("decoder", new StringDecoder())
                                                .addLast("encoder", new StringEncoder())
                                                .addLast(new BaseClient1ChannelHandler())
                                                .addLast(new BaseClient2ChannelHandler());
                                        /**
                                         * 如果一个channelPipeline中有多个channelHandler时，且这些channelHandler中有同样的方法时，
                                         * 例如这里的channelActive方法，只会调用处在第一个的channelHandler中的channelActive方法，
                                         * 如果你想要调用后续的channelHandler的同名的方法就需要调用以“fire”为开头的方法了，这样做很灵活
                                         */
                                    }
                                });
            ChannelFuture future = client.connect(HOST, PORT).sync();
            future.channel().writeAndFlush("Hello Netty Server ,I am a common client");
            future.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            group.shutdownGracefully();
        }
    }
}

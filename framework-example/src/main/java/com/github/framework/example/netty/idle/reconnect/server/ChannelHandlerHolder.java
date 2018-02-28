package com.github.framework.example.netty.idle.reconnect.server;

import io.netty.channel.ChannelHandler;

/**
 *
 */
public interface ChannelHandlerHolder {

    ChannelHandler[] handlers();
}

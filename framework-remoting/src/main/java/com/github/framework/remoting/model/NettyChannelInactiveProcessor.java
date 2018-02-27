package com.github.framework.remoting.model;

import com.github.framework.common.exception.remoting.RemotingSendRequestException;
import com.github.framework.common.exception.remoting.RemotingTimeoutException;
import io.netty.channel.ChannelHandlerContext;

/**
 * 处理channel关闭或者inactive的状态的时候的改变
 **/
public interface NettyChannelInactiveProcessor {

    void processChannelInactive(ChannelHandlerContext ctx) throws RemotingSendRequestException, RemotingTimeoutException, InterruptedException;
}

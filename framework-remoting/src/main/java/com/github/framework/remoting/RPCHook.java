package com.github.framework.remoting;

import com.github.framework.remoting.model.RemotingTransporter;

/**
 * RPC的回调钩子，在发送请求和接收请求的时候触发，这样做事增加程序的健壮性和灵活性
 **/
public interface RPCHook {
    void doBeforeRequest(final String remoteAddr, final RemotingTransporter request);

    void doAfterResponse(final String remoteAddr, final RemotingTransporter request,final RemotingTransporter response);
}

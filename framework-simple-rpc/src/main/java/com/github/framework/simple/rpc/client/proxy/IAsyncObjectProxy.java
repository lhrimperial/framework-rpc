package com.github.framework.simple.rpc.client.proxy;

import com.github.framework.simple.rpc.client.RpcFuture;

/**
 *
 */
public interface IAsyncObjectProxy {
    public RpcFuture call(String funcName, Object... args);
}

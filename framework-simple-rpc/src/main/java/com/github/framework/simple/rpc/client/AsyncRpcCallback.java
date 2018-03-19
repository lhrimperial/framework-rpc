package com.github.framework.simple.rpc.client;

/**
 *
 */
public interface AsyncRpcCallback {

    void success(Object result);

    void fail(Exception e);
}

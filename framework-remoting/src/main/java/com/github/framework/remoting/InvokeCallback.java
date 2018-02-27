package com.github.framework.remoting;

import com.github.framework.remoting.model.RemotingResponse;

/**
 * 远程调用之后的回调函数
 **/
public interface InvokeCallback {

    void operationComplete(final RemotingResponse remotingResponse);
}

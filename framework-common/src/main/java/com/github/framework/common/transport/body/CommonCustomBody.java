package com.github.framework.common.transport.body;

import com.github.framework.common.exception.remoting.RemotingCommmonCustomException;

/**
 * 网络传输对象的主体对象
 */
public interface CommonCustomBody {

    void checkFields() throws RemotingCommmonCustomException;
}

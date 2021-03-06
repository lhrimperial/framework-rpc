package com.github.framework.common.transport.body;

import com.github.framework.common.exception.remoting.RemotingCommmonCustomException;

/**
 *
 */
public class AckCustomBody implements CommonCustomBody {

    //request请求id
    private long requestId;

    //是否消费处理成功
    private boolean success;


    public AckCustomBody(long requestId, boolean success) {
        this.requestId = requestId;
        this.success = success;
    }

    @Override
    public void checkFields() throws RemotingCommmonCustomException {
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "AckCustomBody [requestId=" + requestId + ", success=" + success + "]";
    }

}

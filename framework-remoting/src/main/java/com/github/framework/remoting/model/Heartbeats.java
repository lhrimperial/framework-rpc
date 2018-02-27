package com.github.framework.remoting.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static com.github.framework.common.protocal.FrameworkProtocol.HEAD_LENGTH;
import static com.github.framework.common.protocal.FrameworkProtocol.HEARTBEAT;
import static com.github.framework.common.protocal.FrameworkProtocol.MAGIC;

/**
 *
 **/
public class Heartbeats {
    private static final ByteBuf HEARTBEAT_BUF;

    static {
        ByteBuf buf = Unpooled.buffer(HEAD_LENGTH);
        buf.writeShort(MAGIC);
        buf.writeByte(HEARTBEAT);
        buf.writeByte(0);
        buf.writeLong(0);
        buf.writeInt(0);
        HEARTBEAT_BUF = Unpooled.unmodifiableBuffer(Unpooled.unreleasableBuffer(buf));
    }

    /**
     * Returns the shared heartbeat content.
     */
    public static ByteBuf heartbeatContent() {
        //duplicate方法复制缓冲区
        return HEARTBEAT_BUF.duplicate();
    }
}

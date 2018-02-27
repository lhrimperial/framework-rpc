package com.github.framework.remoting.model;

import com.github.framework.common.protocal.FrameworkProtocol;
import com.github.framework.common.transport.body.CommonCustomBody;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 网络传输的唯一对象
 */
public class RemotingTransporter extends ByteHolder {

    private static final AtomicLong requestId = new AtomicLong(0L);

    /**
     * 请求的类型，是订阅服务、发布服务等
     */
    private byte code;
    /**
     * 请求主体
     */
    private transient CommonCustomBody customHeader;

    /**
     * 请求的时间戳
     */
    private transient long timestamp;

    /**
     * 请求的id
     */
    private long opaque = requestId.getAndIncrement();

    /**
     * 定义该传输对象是请求还是响应信息
     */
    private byte transporterType;


    protected RemotingTransporter() {
    }

    /**
     * 创建一个请求传输对象
     * @param code 请求的类型
     * @param commonCustomHeader 请求的正文
     * @return
     */
    public static RemotingTransporter createRequestTransporter(byte code, CommonCustomBody commonCustomHeader){
        RemotingTransporter remotingTransporter = new RemotingTransporter();
        remotingTransporter.setCode(code);
        remotingTransporter.customHeader = commonCustomHeader;
        remotingTransporter.transporterType = FrameworkProtocol.REQUEST_REMOTING;
        return remotingTransporter;
    }

    /**
     * 创建一个响应对象
     * @param code 响应对象的类型
     * @param commonCustomHeader 响应对象的正文
     * @param opaque 此响应对象对应的请求对象的id
     * @return
     */
    public static RemotingTransporter createResponseTransporter(byte code,CommonCustomBody commonCustomHeader,long opaque){
        RemotingTransporter remotingTransporter = new RemotingTransporter();
        remotingTransporter.setCode(code);
        remotingTransporter.customHeader = commonCustomHeader;
        remotingTransporter.setOpaque(opaque);
        remotingTransporter.transporterType = FrameworkProtocol.RESPONSE_REMOTING;
        return remotingTransporter;
    }


    public byte getTransporterType() {
        return transporterType;
    }

    public void setTransporterType(byte transporterType) {
        this.transporterType = transporterType;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public long getOpaque() {
        return opaque;
    }

    public void setOpaque(long opaque) {
        this.opaque = opaque;
    }

    public long timestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public CommonCustomBody getCustomHeader() {
        return customHeader;
    }

    public void setCustomHeader(CommonCustomBody customHeader) {
        this.customHeader = customHeader;
    }


    public static RemotingTransporter newInstance(long id, byte sign,byte type, byte[] bytes) {
        RemotingTransporter remotingTransporter = new RemotingTransporter();
        remotingTransporter.setCode(sign);
        remotingTransporter.setTransporterType(type);
        remotingTransporter.setOpaque(id);
        remotingTransporter.bytes(bytes);
        return remotingTransporter;
    }

    @Override
    public String toString() {
        return "RemotingTransporter [code=" + code + ", customHeader=" + customHeader + ", timestamp=" + timestamp + ", opaque=" + opaque
                + ", transporterType=" + transporterType + "]";
    }
}

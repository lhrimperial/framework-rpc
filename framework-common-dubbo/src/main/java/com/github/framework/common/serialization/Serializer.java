package com.github.framework.common.serialization;

import com.github.framework.common.extension.Adaptive;
import com.github.framework.common.extension.SPI;

/**
 * 序列化接口
 */
@SPI("proto")
public interface Serializer {
    /**
     * 将对象序列化成byte[]
     * @param obj
     * @return
     */
    @Adaptive
    <T> byte[] writeObject(T obj);

    /**
     * 将byte数组反序列成对象
     * @param bytes
     * @param clazz
     * @return
     */
    @Adaptive
    <T> T readObject(byte[] bytes, Class<T> clazz);
}

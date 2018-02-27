package com.github.framework.common.serialization;

import com.github.framework.common.spi.BaseServiceLoader;

/**
 *
 */
public final class SerializerHolder {

    // SPI
    private static final Serializer serializer = BaseServiceLoader.load(Serializer.class);

    public static Serializer serializerImpl() {
        return serializer;
    }
}
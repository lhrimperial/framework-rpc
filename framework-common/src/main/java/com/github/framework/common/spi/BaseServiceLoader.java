package com.github.framework.common.spi;

import java.util.ServiceLoader;

/**
 * 简单的扩展加载
 */
public class BaseServiceLoader {

    public static <S> S load(Class<S> serviceClass) {
        return ServiceLoader.load(serviceClass).iterator().next();
    }
}

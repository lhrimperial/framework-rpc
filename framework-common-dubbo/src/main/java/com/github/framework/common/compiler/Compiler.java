package com.github.framework.common.compiler;

import com.github.framework.common.extension.SPI;

/**
 *
 */
@SPI("javassist")
public interface Compiler {

    Class<?> compile(String code, ClassLoader classLoader);
}

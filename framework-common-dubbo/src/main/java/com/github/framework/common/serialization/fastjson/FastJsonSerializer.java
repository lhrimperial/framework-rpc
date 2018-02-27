package com.github.framework.common.serialization.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.framework.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用fastjson序列化
 * 需要有无参构造函数
 */
public class FastJsonSerializer implements Serializer{
    private static Logger logger = LoggerFactory.getLogger(FastJsonSerializer.class);

    @Override
    public <T> byte[] writeObject(T obj) {
        if (logger.isDebugEnabled()) {
            logger.debug("FastJsonSerializer Serializer");
        }
        return JSON.toJSONBytes(obj, SerializerFeature.SortField);
    }

    @Override
    public <T> T readObject(byte[] bytes, Class<T> clazz) {
        if (logger.isDebugEnabled()) {
            logger.debug("FastJsonSerializer Deserializer");
        }
        return JSON.parseObject(bytes,clazz, Feature.SortFeidFastMatch);
    }
}

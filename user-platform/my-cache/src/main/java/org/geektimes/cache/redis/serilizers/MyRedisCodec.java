package org.geektimes.cache.redis.serilizers;

import io.lettuce.core.codec.RedisCodec;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @description
 * @Author TheFool
 * @Date 2021/4/14 22:49
 */
public class MyRedisCodec<K extends Serializable, V extends Serializable> implements RedisCodec<K, V> {


    public <K, V> MyRedisCodec(Class<K> keyType, Class<V> valueType) {

    }

    @Override
    public K decodeKey(ByteBuffer byteBuffer) {
        return null;
    }

    @Override
    public V decodeValue(ByteBuffer byteBuffer) {
        return null;
    }

    @Override
    public ByteBuffer encodeKey(K k) {
        return null;
    }

    @Override
    public ByteBuffer encodeValue(V v) {
        return null;
    }
}

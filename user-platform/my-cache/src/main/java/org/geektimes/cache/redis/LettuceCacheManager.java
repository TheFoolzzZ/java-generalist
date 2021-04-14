package org.geektimes.cache.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.geektimes.cache.AbstractCacheManager;
import org.geektimes.cache.redis.serilizers.MyRedisCodec;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

/**
 * {@link javax.cache.CacheManager} based on Jedis
 */
@SuppressWarnings("ALL")
public class LettuceCacheManager extends AbstractCacheManager {

    private final  RedisClient redisClient;
    StatefulRedisConnection connection;

    public LettuceCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, Properties properties) {
        super(cachingProvider, uri, classLoader, properties);
        this.redisClient = RedisClient.create(String.valueOf(uri));
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        connection = redisClient.connect(new MyRedisCodec(configuration.getKeyType(), configuration.getValueType()));
        return new LettuceCache(this, cacheName, configuration, connection.sync());
    }

    @Override
    protected void doClose() {
        redisClient.shutdown();
    }
}

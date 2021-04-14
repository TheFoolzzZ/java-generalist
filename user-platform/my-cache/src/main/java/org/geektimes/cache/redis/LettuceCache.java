package org.geektimes.cache.redis;

import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.protocol.RedisCommand;
import org.geektimes.cache.AbstractCache;
import org.geektimes.cache.ExpirableEntry;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.geektimes.cache.redis.serilizers.CodecUtils.*;

/**
 * @description
 * @Author chengde.tan
 * @Date 2021/4/14 22:57
 */
public class LettuceCache<K extends Serializable, V extends Serializable> extends AbstractCache<K,V> {

    private final RedisCommands<K, V> redisCommands;


    protected LettuceCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration,
                           RedisCommands<K, V> redisCommands) {
        super(cacheManager, cacheName, configuration);
        this.redisCommands = redisCommands;
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        return Optional.ofNullable(redisCommands.get(key)).isPresent();
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        return ExpirableEntry.of(key,redisCommands.get(key));
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        redisCommands.set(entry.getKey(), entry.getValue());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        ExpirableEntry<K, V> entry = getEntry(key);
        if (Objects.nonNull(entry)) {
            this.redisCommands.del(key);
        }
        return entry;
    }

    @Override
    protected void clearEntries() throws CacheException {
//        redisCommands.keys()
    }

    @Override
    protected Set<K> keySet() {
        return null;
    }
}

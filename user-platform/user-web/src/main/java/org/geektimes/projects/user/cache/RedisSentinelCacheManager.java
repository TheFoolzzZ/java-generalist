/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geektimes.projects.user.cache;

import com.sun.jndi.toolkit.url.Uri;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.util.Assert;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Redis {@link CacheManager} 实现
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 * Date : 2021-04-29
 */
public class RedisSentinelCacheManager extends AbstractCacheManager {

    private final JedisSentinelPool jedisSentinelPool;

    public RedisSentinelCacheManager(String masterName, String uri) {
        Assert.isTrue(StringUtils.isNotBlank(uri),"uri cant be blank");
        Set<String> nodes = Arrays.stream(uri.split(",")).collect(Collectors.toSet());
        if (nodes.isEmpty()) {
            throw new RuntimeException(String.format("[RedisSentinelCacheManager] uri:%s, is not avaliable!", uri));
        }
        this.jedisSentinelPool = new JedisSentinelPool(masterName, nodes);
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        // 确保接口不返回 null
        List<? extends Cache> caches = new LinkedList<>();
        prepareCaches(caches);
        return caches;
    }

    protected Cache getMissingCache(String name) {
        return new RedisCache(name, jedisSentinelPool.getResource());
    }

    private void prepareCaches(List<? extends Cache> caches) {
    }
}

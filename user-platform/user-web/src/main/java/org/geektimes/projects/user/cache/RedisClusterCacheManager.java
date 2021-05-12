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
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.net.MalformedURLException;
import java.util.*;

/**
 * Redis {@link CacheManager} 实现
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 * Date : 2021-04-29
 */
public class RedisClusterCacheManager extends AbstractCacheManager {

    private final JedisCluster jedisCluster;

    public RedisClusterCacheManager(String uri) {
        Assert.isTrue(StringUtils.isNotBlank(uri),"uri cant be blank");
        Set<HostAndPort> nodes = new HashSet<>();
        Arrays.stream(uri.split(","))
                .forEach(url->{
                    try {
                        Uri singleUri = new Uri(url);
                        nodes.add(new HostAndPort(singleUri.getHost(), singleUri.getPort()));
                    } catch (MalformedURLException e) {
                        // log.error();
                        e.printStackTrace();
                    }
                });
        if (nodes.isEmpty()) {
            throw new RuntimeException(String.format("[RedisClusterCacheManager] uri:%s, is not avaliable!", uri));
        }
        this.jedisCluster = new JedisCluster(nodes);
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        // 确保接口不返回 null
        List<? extends Cache> caches = new LinkedList<>();
        prepareCaches(caches);
        return caches;
    }

    protected Cache getMissingCache(String name) {
        return new RedisClusterCache(name, this.jedisCluster);
    }

    private void prepareCaches(List<? extends Cache> caches) {
    }
}

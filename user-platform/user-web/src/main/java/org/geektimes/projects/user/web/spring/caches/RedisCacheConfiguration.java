package org.geektimes.projects.user.web.spring.caches;

import org.geektimes.projects.user.cache.RedisCacheManager;
import org.geektimes.projects.user.cache.RedisClusterCacheManager;
import org.geektimes.projects.user.cache.RedisSentinelCacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description redis配置类
 * @Author chengde.tan
 * @Date 2021/5/12 22:34
 */
@Configuration
public class RedisCacheConfiguration {


    @Cacheable(value = "testValue",key = "#key",cacheManager = "redisCacheManager")
    public String cache(String key) {
        System.out.println("org.geektimes.projects.user.web.spring.caches.RedisCacheConfiguration");
        return "testValue";
    }


    @Bean(name = "redisCacheManager")
    public RedisCacheManager cacheManager() {
        // TODO springboot项目后迁移到配置文件中
        String uri = "redis://127.0.0.1:6379";
        return new RedisCacheManager(uri);
    }


    @Bean(name = "redisClusterCacheManager")
    public RedisClusterCacheManager redisClusterCacheManager() {
        // TODO springboot项目后迁移到配置文件中
        String uri = "redis://192.168.10.100:6379/,redis://192.168.10.101:6379/";
        return new RedisClusterCacheManager(uri);
    }


    @Bean(name = "redisSentinelCacheManager")
    public RedisSentinelCacheManager redisSentinelCacheManager() {
        // TODO springboot项目后迁移到配置文件中
        String uri = "redis://192.168.10.100:6379/,redis://192.168.10.101:6379/";
        return new RedisSentinelCacheManager("myMasterName",uri);
    }


}

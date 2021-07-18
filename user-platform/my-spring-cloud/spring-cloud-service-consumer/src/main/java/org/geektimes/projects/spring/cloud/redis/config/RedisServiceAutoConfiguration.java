package org.geektimes.projects.spring.cloud.redis.config;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.redis.RedisHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * TODO
 *
 * @author theFool
 * @since 1.0.0
 * Date: 2021-07-10
 */
@Configuration
@ConditionalOnMissingBean(Binder.class)
@Import(RedisMessageChannelBinderConfiguration.class)
@PropertySource("classpath:/META-INF/redis-binder.properties")
@AutoConfigureBefore({RedisAutoConfiguration.class})
public class RedisServiceAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("192.168.8.43");
        config.setPort(6379);
        config.setPassword("gwsoft123");
        return new LettuceConnectionFactory(config);
    }


    @Bean
    public HealthIndicator binderHealthIndicator(RedisConnectionFactory redisConnectionFactory) {
        return new RedisHealthIndicator(redisConnectionFactory);
    }
}

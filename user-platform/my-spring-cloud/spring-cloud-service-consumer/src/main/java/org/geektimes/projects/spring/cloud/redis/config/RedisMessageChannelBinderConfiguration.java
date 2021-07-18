package org.geektimes.projects.spring.cloud.redis.config;

import org.geektimes.projects.spring.cloud.stream.binder.redis.RedisMessageChannelBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.stream.config.codec.kryo.KryoCodecAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.codec.Codec;

/**
 * TODO
 *
 * @author theFool
 * @since 1.0.0
 * Date: 2021-07-10
 */
@Configuration
@Import({PropertyPlaceholderAutoConfiguration.class, KryoCodecAutoConfiguration.class})
@ConfigurationProperties(prefix = "spring.cloud.stream.redis.binder")
public class RedisMessageChannelBinderConfiguration {


    @Autowired
    private Codec codec;

    @Bean
    public RedisMessageChannelBinder redisMessageChannelBinder(RedisConnectionFactory redisConnectionFactory) {

        RedisMessageChannelBinder redisMessageChannelBinder = new RedisMessageChannelBinder(redisConnectionFactory);
        redisMessageChannelBinder.setCodec(this.codec);
        return redisMessageChannelBinder;
    }

}

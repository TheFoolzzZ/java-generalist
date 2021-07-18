package org.geektimes.projects.spring.cloud.provider;

import org.geektimes.projects.spring.cloud.bus.RedisBridge;
import org.geektimes.projects.spring.cloud.bus.RedisBusBridge;
import org.geektimes.projects.spring.cloud.stream.binder.redis.RedisMessageChannelBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.channel.SubscribableRedisChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * TODO
 *
 * @author theFool
 * @since 1.0.0
 * Date: 2021-07-10
 */
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class RedisServiceProvider {

    public static void main(String[] args) {
        SpringApplication.run(RedisServiceProvider.class, args);
    }

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        MessageChannel messageChannel = new SubscribableRedisChannel(redisConnectionFactory, "springCloudBus");
        GenericMessage<String> message = new GenericMessage("Hello,World");
        messageChannel.send(message);
        System.out.println("send message=" + message.getPayload());
    }

    @Bean
    public RedisBridge redisBridge(RedisConnectionFactory redisConnectionFactory) {
        return new RedisBridge(redisConnectionFactory);
    }

    @Bean
    public RedisBusBridge redisBusBridge(RedisBridge redisBridge, BusProperties busProperties) {
        return new RedisBusBridge(redisBridge, busProperties);
    }

    @Bean
    public RedisMessageChannelBinder redisMessageChannelBinder(RedisConnectionFactory redisConnectionFactory) {

        return new RedisMessageChannelBinder(redisConnectionFactory);
    }
}

package org.geektimes.projects.spring.cloud.bus;

import org.springframework.cloud.bus.BusBridge;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.messaging.support.MessageBuilder;

/**
 * TODO
 *
 * @author TheFool
 * @date 2021/6/8
 *
 */
public class RedisBusBridge implements BusBridge {
    private final RedisBridge redisBridge;

    private final BusProperties properties;

    public RedisBusBridge(RedisBridge redisBridge, BusProperties properties) {
        this.redisBridge = redisBridge;
        this.properties = properties;
    }

    public void send(RemoteApplicationEvent event) {
        this.redisBridge.send(properties.getDestination(), MessageBuilder.withPayload(event).build());
    }
}

package org.geektimes.projects.spring.cloud.bus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.cloud.stream.messaging.DirectWithAttributesChannel;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;

/**

 * @author TheFool
 * @date 2021/6/8
 * @since
 */
public class RedisBridge implements SmartInitializingSingleton {

    private final Log logger = LogFactory.getLog(getClass());

    private final Map<String, RTopic> channelCache;

    private ConfigurableApplicationContext applicationContext;

    private boolean initialized;

    private RedissonClient redisson = null;

    public RedisBridge(BindingServiceProperties bindingServiceProperties, ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.channelCache = new LinkedHashMap<String, RTopic>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, RTopic> eldest) {
                boolean remove = size() > bindingServiceProperties.getDynamicDestinationCacheSize();
                if (remove && logger.isDebugEnabled()) {
                    logger.debug("Removing message channel from cache " + eldest.getKey());
                }
                return remove;
            }
        };
        Config config = new Config();
        config.useSingleServer().setAddress("127.0.0.1:6379");

        redisson = Redisson.create(config);

    }


    public void send(String bindingName, Object data) {
        try {
            this.channelCache.get(bindingName).publish(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void afterSingletonsInstantiated() {
        if (this.initialized) {
            return;
        }
        Map<String, DirectWithAttributesChannel> channels = applicationContext.getBeansOfType(DirectWithAttributesChannel.class);
        for (Map.Entry<String, DirectWithAttributesChannel> channelEntry : channels.entrySet()) {
            if (channelEntry.getValue().getAttribute("type").equals("output")) {
                RTopic topic = redisson.getTopic(channelEntry.getKey(), new JsonJacksonCodec());
                this.channelCache.put(channelEntry.getKey(), topic);
            }
        }
        this.initialized = true;
    }


}

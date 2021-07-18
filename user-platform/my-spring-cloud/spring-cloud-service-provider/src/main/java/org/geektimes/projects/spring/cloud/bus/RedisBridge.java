package org.geektimes.projects.spring.cloud.bus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * TODO
 *
 * @author TheFool
 * @date 2021/6/8
 *
 */
public class RedisBridge implements SmartInitializingSingleton {

    private final Log logger = LogFactory.getLog(getClass());

    private boolean initialized;


    public RedisBridge(RedisConnectionFactory redisConnectionFactory) {


    }


    public void send(String bindingName, Object data) {
        System.out.println("bindingName=" + bindingName+", data="+data);
    }



    @Override
    public void afterSingletonsInstantiated() {

    }


}

package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description OSPropertiesConfigSource
 * @Author TheFool
 * @Date 2021/3/17 23:58
 */
public class  OSPropertiesConfigSource implements ConfigSource {
    private final HashMap<String, String> osPropertiesMap = new HashMap<>();

    public OSPropertiesConfigSource() {
        Map<String, String> getenv = System.getenv();
        this.osPropertiesMap.putAll(getenv);
    }

    @Override
    public Set<String> getPropertyNames() {
        return osPropertiesMap.keySet();
    }

    @Override
    public String getValue(String value) {
        return osPropertiesMap.get(value);
    }

    @Override
    public String getName() {
        return OSPropertiesConfigSource.class.getName();
    }
}

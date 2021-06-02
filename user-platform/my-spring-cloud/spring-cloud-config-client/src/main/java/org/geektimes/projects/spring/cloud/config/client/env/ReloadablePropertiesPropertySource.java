package org.geektimes.projects.spring.cloud.config.client.env;

import org.springframework.core.env.PropertySource;

import java.util.Properties;


public class ReloadablePropertiesPropertySource extends PropertySource<Properties> {

    private Properties source;

    public ReloadablePropertiesPropertySource(String name, Properties source) {
        super(name, source);
        this.source = source;
    }

    public void reload(Properties source) {
        this.source = source;
    }

    @Override
    public Object getProperty(String name) {
        return this.source.get(name);
    }

    @Override
    public boolean containsProperty(String name) {
        return this.source.containsKey(name);
    }


}

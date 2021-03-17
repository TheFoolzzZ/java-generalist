package org.geektimes.configuration.microprofile.config;


import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import org.geektimes.configuration.microprofile.config.converter.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JavaConfig implements Config {

    /**
     * 内部可变的集合，不要直接暴露在外面
     */
    private List<ConfigSource> configSources = new LinkedList<>();

    private Map<Class<?>, Converter<?>> converterMap = new ConcurrentHashMap<>(16);

    private static Comparator<ConfigSource> configSourceComparator = new Comparator<ConfigSource>() {
        @Override
        public int compare(ConfigSource o1, ConfigSource o2) {
            return Integer.compare(o2.getOrdinal(), o1.getOrdinal());
        }
    };

    public JavaConfig() {
        ClassLoader classLoader = getClass().getClassLoader();
        ServiceLoader<ConfigSource> serviceLoader = ServiceLoader.load(ConfigSource.class, classLoader);
        serviceLoader.forEach(configSources::add);
        // 排序
        configSources.sort(configSourceComparator);

        // converter配置
        initConfigConverter();
    }


    private void initConfigConverter() {
        converterMap.put(BigDecimal.class, new BigDecimalConverter());
        converterMap.put(Boolean.class, new BooleanConverter());
        converterMap.put(Byte.class, new ByteConverter());
        converterMap.put(Double.class, new DoubleConverter());
        converterMap.put(Float.class, new FloatConverter());
        converterMap.put(Integer.class, new IntegerConverter());
        converterMap.put(Long.class, new LongConverter());
        converterMap.put(Object.class, new ObjectConverter());
        converterMap.put(Short.class, new ShortConverter());
    }

    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        // 根据配置名称找到对应的值
        String propertyValue = getPropertyValue(propertyName);
        // String 转换成目标类型
        Optional<Converter<T>> converterOptional = getConverter(propertyType);
        return converterOptional.orElseThrow(() -> new RuntimeException(String.format("类型：%s, 不支持！！！", propertyType))).convert(propertyValue);
    }

    @Override
    public ConfigValue getConfigValue(String propertyName) {
        ConfigValue configValue = new ConfigValue() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getValue() {
                return null;
            }

            @Override
            public String getRawValue() {
                return null;
            }

            @Override
            public String getSourceName() {
                return null;
            }

            @Override
            public int getSourceOrdinal() {
                return 0;
            }
        };
        return configValue;
    }

    protected String getPropertyValue(String propertyName) {
        String propertyValue = null;
        for (ConfigSource configSource : configSources) {
            propertyValue = configSource.getValue(propertyName);
            if (propertyValue != null) {
                break;
            }
        }
        return propertyValue;
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        T value = getValue(propertyName, propertyType);
        return Optional.ofNullable(value);
    }

    @Override
    public Iterable<String> getPropertyNames() {
        Set<String> propertyNames = new HashSet<>();
        configSources.forEach(source -> propertyNames.addAll(source.getPropertyNames()));
        return propertyNames;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        return Collections.unmodifiableList(configSources);
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> clazz) {
        return Optional.ofNullable((Converter<T>) converterMap.get(clazz));
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }
}

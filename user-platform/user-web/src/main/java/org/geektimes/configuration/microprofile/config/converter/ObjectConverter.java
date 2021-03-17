package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * @description ObjectConverter
 * @Author chengde.tan
 * @Date 2021/3/17 23:20
 */
public class ObjectConverter implements Converter<Object> {
    @Override
    public Object convert(String value) throws IllegalArgumentException, NullPointerException {
        return value;
    }
}

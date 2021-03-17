package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * @description BooleanConverter
 * @Author chengde.tan
 * @Date 2021/3/17 23:18
 */
public class ShortConverter implements Converter<Short> {
    @Override
    public Short convert(String value) throws IllegalArgumentException, NullPointerException {
        return Short.valueOf(value);
    }
}

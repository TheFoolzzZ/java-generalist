package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * @description BooleanConverter
 * @Author chengde.tan
 * @Date 2021/3/17 23:18
 */
public class BooleanConverter implements Converter<Boolean> {
    @Override
    public Boolean convert(String value) throws IllegalArgumentException, NullPointerException {
        return Boolean.valueOf(value);
    }
}

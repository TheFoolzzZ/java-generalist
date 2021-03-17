package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * @description IntegerConverter
 * @Author chengde.tan
 * @Date 2021/3/17 23:20
 */
public class FloatConverter implements Converter<Float> {
    @Override
    public Float convert(String value) throws IllegalArgumentException, NullPointerException {
        return Float.valueOf(value);
    }
}

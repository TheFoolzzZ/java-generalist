package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * @description ByteConverter
 * @Author chengde.tan
 * @Date 2021/3/17 23:20
 */
public class ByteConverter implements Converter<Byte> {
    @Override
    public Byte convert(String value) throws IllegalArgumentException, NullPointerException {
        return Byte.valueOf(value);
    }
}

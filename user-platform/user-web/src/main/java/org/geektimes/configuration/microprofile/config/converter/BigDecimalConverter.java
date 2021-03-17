package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

import java.math.BigDecimal;

/**
 * @description BigDecimalConverter
 * @Author chengde.tan
 * @Date 2021/3/17 23:20
 */
public class BigDecimalConverter implements Converter<BigDecimal> {
    @Override
    public BigDecimal convert(String value) throws IllegalArgumentException, NullPointerException {
        return new BigDecimal(value);
    }

}

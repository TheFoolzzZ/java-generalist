package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/** DoubleConverter
 * @description IntegerConverter
 * @Author chengde.tan
 * @Date 2021/3/17 23:20
 */
public class DoubleConverter implements Converter<Double> {
    @Override
    public Double convert(String value) throws IllegalArgumentException, NullPointerException {
        return Double.valueOf(value);
    }
}

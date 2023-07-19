package org.ruthvik.config.converters;

import org.aeonbits.owner.Converter;
import org.ruthvik.enums.Environment;

import java.lang.reflect.Method;
import java.util.Map;

public class StringToEnvironmentConverter implements Converter<Environment> {
    @Override
    public Environment convert(Method method, String environment) {
        Map<String, Environment> values = Map.of("PROD", Environment.PROD, "Test", Environment.TEST);
        return values.getOrDefault(environment.toUpperCase(), Environment.PROD);
    }
}
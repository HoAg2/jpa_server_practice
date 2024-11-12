package com.example.practice.jpa_practice.common.converter;

import org.springframework.core.convert.converter.Converter;

public class StringToEmptyConverter implements Converter<String, String> {

    @Override
    public String convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return source;
    }
}
package com.example.practice.jpa_practice.common.base;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public abstract class CustomModelMapper {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ModelMapper modelMapper;

    protected <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    protected <D> D map(Object source, Class<D> destinationType, String typeMapName) {
        return modelMapper.map(source, destinationType, typeMapName);
    }

    protected void map(Object source, Object destination) {
        modelMapper.map(source, destination);
    }

    protected void map(Object source, Object destination, String typeMapName) {
        modelMapper.map(source, destination, typeMapName);
    }

    protected <D> D map(Object source, Type destinationType) {
        return modelMapper.map(source, destinationType);
    }

    protected <D> D map(Object source, Type destinationType, String typeMapName) {
        return modelMapper.map(source, destinationType, typeMapName);
    }

    protected <D, T> List<D> mapAll(Collection<T> sourceList, Class<D> destinationType) {
        return sourceList.stream()
                .map(source -> map(source, destinationType))
                .collect(Collectors.toList());
    }

}

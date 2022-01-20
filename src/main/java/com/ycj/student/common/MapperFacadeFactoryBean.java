package com.ycj.student.common;


import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapperFacadeFactoryBean implements ApplicationContextAware, FactoryBean<MapperFacade> {

    private ApplicationContext applicationContext;
    private MapperFacade facade;


    @Override
    public Class<?> getObjectType() {
        return MapperFacade.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public MapperFacade getObject() throws Exception {
        return facade;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        MapperFactory factory = new DefaultMapperFactory.Builder().build();
        addSpringFieldMappers(factory);
        addSpringMappers(factory);
        addSpringConverter(factory);
        facade = factory.getMapperFacade();
    }

    private void addSpringMappers(MapperFactory factory) {
        @SuppressWarnings("rawtypes")
        final Map<String, Mapper> mappers = applicationContext
                .getBeansOfType(Mapper.class);
        for (final Mapper<?, ?> mapper : mappers.values()) {
            factory.registerMapper(mapper);
        }
    }




    private void addSpringFieldMappers(MapperFactory factory) {
        @SuppressWarnings("rawtypes")
        final Map<String, FieldMapper> fieldMappers = applicationContext
                .getBeansOfType(FieldMapper.class);
        for (final FieldMapper fieldMapper : fieldMappers.values()) {
            fieldMapper.register(factory);
        }
    }

    private void addSpringConverter(MapperFactory factory) {
        @SuppressWarnings("rawtypes")
        final Map<String, Converter> converters = applicationContext
                .getBeansOfType(Converter.class);

        for (final Map.Entry<String,Converter> entry : converters.entrySet()) {
            factory.getConverterFactory().registerConverter(entry.getKey(),entry.getValue());
        }
    }
}

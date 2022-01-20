package com.ycj.student.common;

import ma.glasnost.orika.MapperFactory;

/**
 * Created by superstar on 18/11/29.
 */
public interface FieldMapper {
    void register(MapperFactory factory);
}

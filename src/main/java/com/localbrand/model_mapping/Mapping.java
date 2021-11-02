package com.localbrand.model_mapping;

import com.localbrand.utils.ConvertUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public interface Mapping <DTO, ENTITY> {

    public DTO toDto(ENTITY entity);

    public ENTITY toEntity(DTO dto);

}

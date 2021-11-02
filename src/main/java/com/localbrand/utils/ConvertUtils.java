package com.localbrand.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ConvertUtils {

    private static final Logger log = LoggerFactory.getLogger(ConvertUtils.class);

    private static final String PREFIX_SET = "set";
    private static final String PREFIX_GET = "get";

    public  static <ENTITY, DTO> ENTITY toEntity(DTO dto, Class<ENTITY> entityClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ENTITY entity = converter(dto, entityClass);
        return entity;
    }

    public static <ENTITY, DTO> DTO toDto(ENTITY entity, Class<DTO> dtoClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DTO dto = converter(entity, dtoClass);
        return dto;
    }

    private static <T1,T2> T2 converter(T1 t1, Class<T2> t2Class) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> dtoClass = t1.getClass();
        Constructor<T2> t2Constructor = t2Class.getConstructor();
        T2 t2 = t2Constructor.newInstance();
        Field[] fields = t2Class.getDeclaredFields();

        Arrays.stream(fields).forEach( field -> {
            String originalName = field.getName();
            String name = resolverMethodName(originalName);
            String getMethod = PREFIX_GET + name;
            String setMethod = PREFIX_SET + name;

            try {
                Method dtoMethod = dtoClass.getMethod(getMethod);
                Method entityMethod = t2Class.getMethod(setMethod, field.getType());
                entityMethod.invoke(t2, dtoMethod.invoke(t1));
            } catch (NoSuchMethodException e) {
                log.error("not found method: " + getMethod);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        });
        return t2;
    }

    private static String resolverMethodName(String originalName) {
        String first = originalName.substring(0, 1);
        String rest = originalName.substring(1);
        return String.format("%s%s", first.toUpperCase(), rest);
    }
}

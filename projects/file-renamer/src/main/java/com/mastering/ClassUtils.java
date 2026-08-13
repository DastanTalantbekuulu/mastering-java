package com.mastering;

import com.mastering.csv.Delimiter;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ClassUtils {

    public static String generateCsvHead(Class<?> clazz, Class<? extends Annotation> annotation, String method, Delimiter delimiter) {
        return generateCsvHead(clazz, annotation, getMethod(annotation, method), delimiter);
    }

    public static String generateCsvHead(Class<?> clazz, Class<? extends Annotation> annotation, Method method, Delimiter delimiter) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .map(field -> {
                    try {
                        Annotation anno = field.getAnnotation(annotation);
                        return anno != null ? (String) method.invoke(anno) : field.getName();
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        return field.getName();
                    }
                })
                .collect(Collectors.joining(String.valueOf(delimiter.getValue())));
    }

    public static Method getMethod(Class<? extends Annotation> annotation, String methodName) {
        try {
            return annotation.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <T> Map<String, Class<?>> extractFields(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Class<?>> map = fields.length == 0 ? Collections.emptyMap() : new HashMap<>();
        for (Field field : fields) {
            map.put(field.getName(), field.getType());
        }
        return map;
    }

    public static <T> Map<String, String> extractAnnotationFieldMap(Class<T> clazz, Class<? extends Annotation> annotation, String methodName) {
        Map<String, String> result = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation annot = field.getAnnotation(annotation);
            if (annot != null) {
                try {
                    Method valueMethod = annot.annotationType().getDeclaredMethod(methodName);
                    String value = (String) valueMethod.invoke(annot);
                    result.put(value, field.getName());
                } catch (Exception e) {
                    log.error("Error while extracting field " + field.getName(), e);
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    public static <T> T createObject(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            log.error("createObject() error creating {} class: {}", clazz, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void setField(Object object, String fieldName, String value) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        if (fieldName == null || fieldName.trim().isEmpty()) {
            throw new IllegalArgumentException("Field name cannot be null or empty");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            String fieldType = field.getType().getSimpleName();
            switch (fieldType) {
                case "boolean":
                case "Boolean":
                    if (!value.matches("true|false")) {
                        throw new IllegalArgumentException("Value must be 'true' or 'false' for boolean/Boolean field");
                    }
                    field.set(object, Boolean.parseBoolean(value));
                    break;
                case "int":
                case "Integer":
                    if (!value.matches("-?\\d+")) {
                        throw new IllegalArgumentException("Value must be a valid integer for int/Integer field");
                    }
                    field.set(object, Integer.parseInt(value));
                    break;
                case "long":
                case "Long":
                    if (!value.matches("-?\\d+")) {
                        throw new IllegalArgumentException("Value must be a valid long for long/Long field");
                    }
                    field.set(object, Long.parseLong(value));
                    break;
                case "double":
                case "Double":
                    if (!value.matches("-?\\d*\\.?\\d+")) {
                        throw new IllegalArgumentException("Value must be a valid double for double/Double field");
                    }
                    field.set(object, Double.parseDouble(value));
                    break;
                case "float":
                case "Float":
                    if (!value.matches("-?\\d*\\.?\\d+")) {
                        throw new IllegalArgumentException("Value must be a valid float for float/Float field");
                    }
                    field.set(object, Float.parseFloat(value));
                    break;
                case "short":
                case "Short":
                    if (!value.matches("-?\\d+")) {
                        throw new IllegalArgumentException("Value must be a valid short for short/Short field");
                    }
                    field.set(object, Short.parseShort(value));
                    break;
                case "byte":
                case "Byte":
                    if (!value.matches("-?\\d+")) {
                        throw new IllegalArgumentException("Value must be a valid byte for byte/Byte field");
                    }
                    field.set(object, Byte.parseByte(value));
                    break;
                case "char":
                case "Character":
                    if (value.length() != 1) {
                        throw new IllegalArgumentException("Value must be a single character for char/Character field");
                    }
                    field.set(object, value.charAt(0));
                    break;
                case "String":
                    field.set(object, value);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported field type: " + fieldType);
            }
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("Field '" + fieldName + "' not found", e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("Cannot access field '" + fieldName + "'", e);
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("Invalid number format for value: " + value, e);
        }
    }

    public static <T> T cleanEmptyStrings(T object) {
        if (object == null) return null;

        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(object);
                    if (value != null && value.isBlank()) {
                        field.set(object, null);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to clean field: " + field.getName(), e);
                }
            }
        }
        return object;
    }

    public static <T> int getFieldLength(Class<T> clazz) {
        return clazz.getDeclaredFields().length;
    }

}

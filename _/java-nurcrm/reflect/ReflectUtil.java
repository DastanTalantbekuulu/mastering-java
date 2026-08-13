package kg.nurtelecom.util.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ReflectUtil {

    /**
     * Подсчитывает количество ненулевых нестатических полей в переданном объекте.
     *
     * Он перебирает все объявленные поля,
     * проверяет, являются ли они нестатическими, и определяет, не равны ли их значения null.
     * Если входной объект равен null, метод возвращает 0.
     *
     * @param obj Объект, чьи ненулевые нестатические поля необходимо подсчитать.
     * @return Количество ненулевых нестатических полей в объекте.
     */
    public static int countNonNullNonStaticFields(Object obj) {
        if (obj == null) {
            return 0;
        }
        Class<?> clazz = obj.getClass();
        int nonNullNonStaticCount = 0;
        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    Object fieldValue = field.get(obj);
                    if (Objects.nonNull(fieldValue)) {
                        nonNullNonStaticCount++;
                    }
                } catch (IllegalAccessException e) {
                }
            }
        }
        return nonNullNonStaticCount;
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null && clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    public static <T> T createObject(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}

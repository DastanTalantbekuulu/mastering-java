package kg.nurtelecom.util.comparator.entity;

import kg.nurtelecom.util.comparator.entity.field.DefaultFieldComparator;
import kg.nurtelecom.util.comparator.entity.field.Difference;
import kg.nurtelecom.util.comparator.entity.field.FieldComparator;
import kg.nurtelecom.util.comparator.entity.field.UseComparator;
import kg.nurtelecom.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class BaseEntityComparator<E> implements EntityComparator<E> {

    protected final Class<E> clazz;
    protected final Set<Field> fields;

    public BaseEntityComparator(Class<E> clazz) {
        this(clazz, ReflectUtil.getAllFields(clazz));
    }

    public BaseEntityComparator(Class<E> clazz, List<Field> fields) {
        this(clazz, new HashSet<>(fields));
    }

    public BaseEntityComparator(Class<E> clazz, Set<Field> fields) {
        this.clazz = clazz;
        this.fields = fields;
    }

    @Override
    public List<Difference> compare(E oldObject, E newObject) {
        if (oldObject == null || newObject == null) {
            throw new IllegalArgumentException("Сравниваемые объекты не могут быть null.");
        }
        if (!clazz.equals(oldObject.getClass()) && !oldObject.getClass().equals(newObject.getClass())) {
            throw new IllegalArgumentException("Объекты должны быть одного класса для сравнения.");
        }
        List<Difference> changes = new ArrayList<>();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            Object oldValue = getFieldValue(field, oldObject);
            Object newValue = getFieldValue(field, newObject);
            FieldComparator<Object> comparator = getFieldComparator(field);
            if (!comparator.compare(oldValue, newValue)) {
                changes.add(Difference.builder()
                        .fieldName(field.getName())
                        .oldValue(oldValue)
                        .newValue(newValue)
                        .build());
            }
        }
        return changes;
    }

    protected Object getFieldValue(Field field, E obj) {
        try {
            return fields.contains(field) ? field.get(obj) : null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    protected FieldComparator<Object> getFieldComparator(Field field) {
        try {
            if (field.isAnnotationPresent(UseComparator.class)) {
                UseComparator annotation = Objects.requireNonNull(field.getAnnotation(UseComparator.class));
                Method method = annotation.value().getMethod(annotation.factoryMethod());
                method.setAccessible(true);
                return (FieldComparator<Object>) method.invoke(null);
            }
            return DefaultFieldComparator.me();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

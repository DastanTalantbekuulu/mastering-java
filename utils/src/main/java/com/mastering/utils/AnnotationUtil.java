//package com.mastering.utils;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//import java.util.function.BiConsumer;
//import java.util.stream.Collectors;
//
///**
// * Утилитный класс для работы с аннотациями с использованием рефлексии.
// * <p>
// * Предоставляет статические методы для поиска аннотированных полей, извлечения значений из аннотаций
// * и создания различных отображений (Map) на их основе.
// */
//public final class AnnotationUtil {
//
//    // Приватный конструктор, чтобы предотвратить создание экземпляров утилитарного класса.
//    private AnnotationUtil() {
//    }
//
//
//    /**
//     * Возвращает список констант перечисления (enum), которые аннотированы указанной аннотацией.
//     *
//     * <p><b>Пример:</b></p>
//     * <pre>{@code
//     * // Дано:
//     * public @interface IsActive {}
//     *
//     * public enum Status {
//     *     @IsActive
//     *     ACTIVE,
//     *     INACTIVE,
//     *     @IsActive
//     *     PENDING
//     * }
//     *
//     * // Использование:
//     * List<Status> activeStatuses = getEnumsAnnotatedWith(Status.class, IsActive.class);
//     *
//     * // activeStatuses будет содержать [ACTIVE, PENDING]
//     * }</pre>
//     *
//     * @param enumClass      Класс перечисления для анализа (например, {@code MyEnum.class}).
//     * @param annotationType Класс аннотации, которую необходимо найти.
//     * @param <E>            Тип перечисления.
//     * @param <A>            Тип аннотации.
//     * @return Список аннотированных констант перечисления. Возвращает пустой список, если параметры null или константы не найдены.
//     */
//    public static <E extends Enum<E>, A extends Annotation> List<E> getEnumsAnnotatedWith(
//            Class<E> enumClass,
//            Class<A> annotationType) {
//        if (enumClass == null || annotationType == null) {
//            return Collections.emptyList();
//        }
//        return Arrays.stream(enumClass.getEnumConstants())
//                .filter(enumConstant -> {
//                    try {
//                        Field field = enumClass.getDeclaredField(enumConstant.name());
//                        return field.isAnnotationPresent(annotationType);
//                    } catch (NoSuchFieldException e) {
//                        return false;
//                    }
//                })
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Создает отображение, где ключ — имя поля класса, а значение — строковое значение из аннотации.
//     * Порядок полей в карте сохраняется благодаря {@link LinkedHashMap}.
//     *
//     * <p><b>Пример:</b></p>
//     * <pre>{@code
//     * // Дано:
//     * public @interface Header { String value(); }
//     *
//     * public class MyClass {
//     *     @Header(value = "Китай")
//     *     private String countryRu;
//     *     @Header(value = "Город")
//     *     private String city;
//     * }
//     *
//     * // Использование:
//     * Map<String, String> result = mapFieldNameToValue(MyClass.class, Header.class, "value");
//     *
//     * // result: {"countryRu" -> "Китай", "city" -> "Город"}
//     * }</pre>
//     *
//     * @param clazz      Класс для анализа.
//     * @param annotation Класс аннотации.
//     * @param methodName Имя метода аннотации, значение которого нужно извлечь.
//     * @return {@code Map<String, String>} с отображением "имя поля" -> "значение аннотации".
//     */
//    public static Map<String, String> mapFieldNameToValue(
//            Class<?> clazz,
//            Class<? extends Annotation> annotation,
//            String methodName) {
//
//        Map<String, String> result = new LinkedHashMap<>();
//        processAnnotatedFields(
//                clazz,
//                annotation,
//                methodName,
//                String.class,
//                (field, value) -> result.put(field.getName(), value)
//        );
//        return result;
//    }
//
//    /**
//     * Создает отображение, где ключ — строковое значение из аннотации, а значение — имя поля класса.
//     * Удобно для сопоставления заголовков (например, из Excel) с полями объекта.
//     *
//     * <p><b>Пример:</b></p>
//     * <pre>{@code
//     * // Дано:
//     * public @interface Header { String value(); }
//     *
//     * public class MyClass {
//     *     @Header(value = "Китай")
//     *     private String countryRu;
//     *     @Header(value = "Город")
//     *     private String city;
//     * }
//     *
//     * // Использование:
//     * Map<String, String> result = mapValueToFieldName(MyClass.class, Header.class, "value");
//     *
//     * // result: {"Китай" -> "countryRu", "Город" -> "city"}
//     * }</pre>
//     *
//     * @param clazz      Класс для анализа.
//     * @param annotation Класс аннотации.
//     * @param methodName Имя метода аннотации, значение которого будет ключом в карте.
//     * @return {@code Map<String, String>} с отображением "значение аннотации" -> "имя поля".
//     */
//    public static Map<String, String> mapValueToFieldName(
//            Class<?> clazz,
//            Class<? extends Annotation> annotation,
//            String methodName) {
//
//        Map<String, String> result = new HashMap<>();
//        processAnnotatedFields(
//                clazz,
//                annotation,
//                methodName,
//                String.class,
//                (field, value) -> result.put(value, field.getName())
//        );
//        return result;
//    }
//
//    /**
//     * Создает отображение, где ключ — имя поля класса, а значение — сам объект {@link Field}.
//     * Удобно для быстрого доступа к полям по их именам, минуя повторный поиск.
//     *
//     * <p><b>Пример:</b></p>
//     * <pre>{@code
//     * // Дано:
//     * public @interface Header { String value(); }
//     *
//     * public class MyClass {
//     *     @Header("Страна")
//     *     private String country;
//     *     @Header("Город")
//     *     private String city;
//     * }
//     *
//     * // Использование:
//     * Map<String, Field> result = mapFieldNameToField(MyClass.class, Header.class, "value");
//     *
//     * // result: {"country" -> Field<country>, "city" -> Field<city>}
//     * }</pre>
//     *
//     * @param clazz      Класс для анализа.
//     * @param annotation Класс аннотации, по которой отбираются поля.
//     * @param methodName Имя метода аннотации (используется для фильтрации, но его значение не используется).
//     * @return {@code Map<String, Field>} с отображением "имя поля" -> "поле".
//     */
//    public static Map<String, Field> mapFieldNameToField(
//            Class<?> clazz,
//            Class<? extends Annotation> annotation,
//            String methodName) {
//
//        Map<String, Field> result = new HashMap<>();
//        processAnnotatedFields(
//                clazz,
//                annotation,
//                methodName,
//                String.class,
//                (field, value) -> result.put(field.getName(), field)
//        );
//        return result;
//    }
//
//    /**
//     * Создает отображение, где ключ — строковое значение из аннотации, а значение — сам объект {@link Field}.
//     * Полезно для динамического маппинга и установки значений полей на основе данных из внешних источников.
//     *
//     * <p><b>Пример:</b></p>
//     * <pre>{@code
//     * // Дано:
//     * public @interface Header { String value(); }
//     *
//     * public class MyClass {
//     *     @Header(value = "Страна")
//     *     private String country;
//     *     @Header(value = "Город")
//     *     private String city;
//     * }
//     *
//     * // Использование:
//     * Map<String, Field> result = mapValueToField(MyClass.class, Header.class, "value");
//     *
//     * // result: {"Страна" -> Field<country>, "Город" -> Field<city>}
//     * }</pre>
//     *
//     * @param clazz      Класс для анализа.
//     * @param annotation Класс аннотации.
//     * @param methodName Имя метода аннотации, значение которого будет ключом в карте.
//     * @return {@code Map<String, Field>} с отображением "значение аннотации" -> "поле".
//     */
//    public static Map<String, Field> mapValueToField(
//            Class<?> clazz,
//            Class<? extends Annotation> annotation,
//            String methodName) {
//
//        Map<String, Field> result = new HashMap<>();
//        processAnnotatedFields(
//                clazz,
//                annotation,
//                methodName,
//                String.class,
//                (field, value) -> result.put(value, field)
//        );
//        return result;
//    }
//
//    /**
//     * Создает отображение, где ключ - имя поля, а значение - экземпляр класса,
//     * указанного в методе аннотации.
//     *
//     * <p>Этот метод находит все поля, аннотированные указанной аннотацией,
//     * извлекает из нее {@link Class}, создает его экземпляр с помощью
//     * конструктора по умолчанию (без аргументов) и помещает в карту.</p>
//     *
//     * <p><b>Пример:</b></p>
//     * <pre>{@code
//     * // Дано:
//     * public interface Parser<T> { T parse(String value); }
//     * public class CountryParser implements Parser<String> {
//     *     public String parse(String value) { return "Parsed " + value; }
//     * }
//     *
//     * public @interface ExcelColumn {
//     *     String value();
//     *     Class<? extends Parser> parser();
//     * }
//     *
//     * public class MyData {
//     *     @ExcelColumn(value = "COUNTRY_NAME", parser = CountryParser.class)
//     *     private String country;
//     * }
//     *
//     * // Использование:
//     * Map<String, Parser> parsers = AnnotationUtil.mapMethodToReturn(
//     *     MyData.class,
//     *     ExcelColumn.class,
//     *     "parser",
//     *     Parser.class
//     * );
//     *
//     * // Результат:
//     * // { "country" -> (экземпляр CountryParser) }
//     * }</pre>
//     *
//     * @param clazz          Класс для анализа.
//     * @param annotationType Класс аннотации.
//     * @param methodName     Имя метода в аннотации, который возвращает {@code Class<?>}.
//     * @param baseType       Базовый класс или интерфейс для инстанцируемого объекта (например, {@code Parser.class}).
//     *                       Используется для проверки типов и как тип значения в результирующей карте.
//     * @param <T>            Тип инстанцируемого объекта.
//     * @return {@code Map<String, T>} с отображением "имя поля" -> "экземпляр класса из аннотации".
//     * @throws IllegalArgumentException если параметры некорректны, метод не найден или возвращает не {@code Class}.
//     * @throws RuntimeException         если не удалось создать экземпляр класса (например, отсутствует конструктор без аргументов).
//     */
//    public static <T> Map<String, T> mapMethodToReturn(
//            Class<?> clazz,
//            Class<? extends Annotation> annotationType,
//            String methodName,
//            Class<T> baseType
//    ) {
//        Objects.requireNonNull(clazz, "Класс не может быть null");
//        Objects.requireNonNull(annotationType, "Тип аннотации не может быть null");
//        Objects.requireNonNull(methodName, "Имя метода не может быть null");
//        Objects.requireNonNull(baseType, "Базовый тип не может быть null");
//
//        Map<String, T> resultMap = new HashMap<>();
//        try {
//            Method annotationMethod = annotationType.getDeclaredMethod(methodName);
//            if (!Class.class.isAssignableFrom(annotationMethod.getReturnType())) {
//                throw new IllegalArgumentException(String.format(
//                        "Метод '%s' в аннотации @%s должен возвращать тип Class<?>.",
//                        methodName, annotationType.getSimpleName()
//                ));
//            }
//
//            for (Field field : clazz.getDeclaredFields()) {
//                if (field.isAnnotationPresent(annotationType)) {
//                    Annotation foundAnnotation = field.getAnnotation(annotationType);
//                    // Извлекаем класс из аннотации (например, DefaultParser.class)
//                    Class<?> classToInstantiate = (Class<?>) annotationMethod.invoke(foundAnnotation);
//
//                    // Проверяем, что полученный класс является подтипом ожидаемого базового типа
//                    if (!baseType.isAssignableFrom(classToInstantiate)) {
//                        throw new ClassCastException(String.format(
//                                "Класс %s, указанный в аннотации на поле '%s', не является подтипом %s.",
//                                classToInstantiate.getName(), field.getName(), baseType.getName()
//                        ));
//                    }
//
//                    // Создаем экземпляр и приводим его к базовому типу
//                    T instance = createInstance(classToInstantiate, baseType);
//                    resultMap.put(field.getName(), instance);
//                }
//            }
//        } catch (NoSuchMethodException e) {
//            throw new IllegalArgumentException(String.format(
//                    "Метод '%s' не найден в аннотации @%s.", methodName, annotationType.getSimpleName()), e);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            throw new RuntimeException(String.format(
//                    "Ошибка при вызове метода '%s' аннотации @%s.", methodName, annotationType.getSimpleName()), e);
//        }
//
//        return resultMap;
//    }
//
//    /**
//     * Возвращает множество полей класса, не помеченных аннотацией {@code @Ignore}.
//     * Учитывает {@code @Ignore} как на уровне класса (для игнорирования полей по имени), так и на уровне самих полей.
//     * Полям автоматически устанавливается {@code setAccessible(true)}.
//     *
//     * <p><b>Пример:</b></p>
//     * <pre>{@code
//     * // Дано:
//     * public @interface Ignore { String[] value() default {}; }
//     *
//     * @Ignore("internalId") // Игнорировать поле с именем "internalId"
//     * public class User {
//     *     private String name;
//     *     private String internalId;
//     *
//     *     @Ignore
//     *     private String password;
//     * }
//     *
//     * // Использование:
//     * Set<Field> fields = getNotIgnoredFields(User.class);
//     *
//     * // fields будет содержать только поле "name".
//     * }</pre>
//     *
//     * @param clazz Класс для анализа.
//     * @return {@code Set<Field>} с полями, которые не нужно игнорировать.
//     */
//    public static Set<Field> getNotIgnoredFields(Class<?> clazz) {
//        if (clazz == null) {
//            return Collections.emptySet();
//        }
//
//        Ignore classIgnore = clazz.getAnnotation(Ignore.class);
//        Set<String> ignoredNames = (classIgnore != null && classIgnore.value().length > 0)
//                ? new HashSet<>(Arrays.asList(classIgnore.value()))
//                : Collections.emptySet();
//
//        return Arrays.stream(clazz.getDeclaredFields())
//                .filter(field -> !field.isAnnotationPresent(Ignore.class) && !ignoredNames.contains(field.getName()))
//                .peek(field -> field.setAccessible(true))
//                .collect(Collectors.toSet());
//    }
//
//    /**
//     * Находит первое поле, аннотированное указанной аннотацией, в классе и его родителях.
//     *
//     * <p><b>Пример:</b></p>
//     * <pre>{@code
//     * // Дано:
//     * public @interface Id {}
//     *
//     * public class BaseEntity {
//     *     @Id
//     *     private Long id;
//     * }
//     *
//     * public class User extends BaseEntity {
//     *     private String name;
//     * }
//     *
//     * // Использование:
//     * Field idField = getAnnotatedField(User.class, Id.class);
//     *
//     * // idField будет ссылаться на поле "id" из класса BaseEntity.
//     * }</pre>
//     *
//     * @param clazz      Класс для поиска.
//     * @param annotation Класс аннотации.
//     * @return Найденный объект {@link Field} с установленным {@code setAccessible(true)}.
//     * @throws NotAnnotatedException если поле с такой аннотацией не найдено.
//     */
//    public static Field getAnnotatedField(Class<?> clazz, Class<? extends Annotation> annotation) {
//        if (clazz == null || annotation == null) {
//            throw new IllegalArgumentException("Класс и аннотация не могут быть null");
//        }
//
//        Class<?> currentClass = clazz;
//        while (currentClass != null && currentClass != Object.class) {
//            for (Field field : currentClass.getDeclaredFields()) {
//                if (field.isAnnotationPresent(annotation)) {
//                    field.setAccessible(true);
//                    return field;
//                }
//            }
//            currentClass = currentClass.getSuperclass();
//        }
//        throw new NotAnnotatedException(
//                String.format("Поле с аннотацией @%s не найдено в классе %s и его иерархии",
//                        annotation.getSimpleName(), clazz.getName()));
//    }
//
//    /**
//     * Получает значение поля, аннотированного указанной аннотацией, из объекта.
//     *
//     * <p><b>Пример:</b></p>
//     * <pre>{@code
//     * // Дано:
//     * public @interface PrimaryKey {}
//     *
//     * public class Product {
//     *     @PrimaryKey
//     *     private String sku = "A-123";
//     *     private String name;
//     * }
//     *
//     * // Использование:
//     * Product product = new Product();
//     * Object value = getFieldValue(product, PrimaryKey.class);
//     *
//     * // value будет равно "A-123".
//     * }</pre>
//     *
//     * @param object     Объект, из которого нужно получить значение.
//     * @param annotation Аннотация, которой помечено поле.
//     * @return Значение поля.
//     * @throws NotAnnotatedException если не удается найти поле или получить к нему доступ.
//     */
//    public static Object getFieldValue(Object object, Class<? extends Annotation> annotation) {
//        if (object == null) {
//            return null;
//        }
//        Field field = getAnnotatedField(object.getClass(), annotation);
//        try {
//            return field.get(object);
//        } catch (IllegalAccessException e) {
//            throw new NotAnnotatedException(
//                    "Не удалось получить доступ к полю с аннотацией @" + annotation.getSimpleName(), e);
//        }
//    }
//
//
//    /**
//     * Извлекает значение из указанного метода экземпляра аннотации.
//     *
//     * <p><b>Пример:</b></p>
//     * <pre>{@code
//     * // Дано:
//     * public @interface Column { String name(); int length(); }
//     *
//     * public class User {
//     *     @Column(name = "user_name", length = 50)
//     *     private String name;
//     * }
//     *
//     * // Использование:
//     * Field nameField = User.class.getDeclaredField("name");
//     * Column columnAnnotation = nameField.getAnnotation(Column.class);
//     *
//     * String nameValue = getAnnotationMethodValue(columnAnnotation, "name", String.class); // "user_name"
//     * int lengthValue = getAnnotationMethodValue(columnAnnotation, "length", int.class);   // 50
//     * }</pre>
//     *
//     * @param annotation Экземпляр аннотации, из которого извлекается значение.
//     * @param methodName Имя метода для вызова (например, "value").
//     * @param returnType Ожидаемый тип возвращаемого значения.
//     * @param <T>        Тип возвращаемого значения.
//     * @return Значение, возвращаемое методом аннотации.
//     * @throws IllegalArgumentException если параметры некорректны, метод не найден или тип не совпадает.
//     * @throws RuntimeException         в случае ошибки рефлексивного вызова.
//     */
//    @SuppressWarnings("unchecked")
//    public static <T> T getAnnotationMethodValue(Annotation annotation, String methodName, Class<T> returnType) {
//        if (annotation == null || methodName == null || returnType == null) {
//            throw new IllegalArgumentException("Аннотация, имя метода и тип возвращаемого значения не могут быть null");
//        }
//        try {
//            Method method = annotation.annotationType().getDeclaredMethod(methodName);
//            Object value = method.invoke(annotation);
//
//            if (value == null) {
//                return null;
//            }
//            if (!returnType.isInstance(value) && !returnType.isPrimitive()) {
//                // Дополнительная проверка для примитивов и их оберток
//                if (!isWrapperType(value.getClass(), returnType)) {
//                    throw new ClassCastException(
//                            String.format("Метод %s аннотации %s вернул тип %s, а ожидался %s",
//                                    methodName, annotation.annotationType().getSimpleName(),
//                                    value.getClass().getName(), returnType.getName()));
//                }
//            }
//            return (T) value;
//        } catch (NoSuchMethodException e) {
//            throw new IllegalArgumentException(
//                    String.format("Метод '%s' не найден в аннотации @%s", methodName, annotation.annotationType().getSimpleName()), e);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            throw new RuntimeException(
//                    String.format("Ошибка при вызове метода '%s' аннотации @%s", methodName, annotation.annotationType().getSimpleName()), e);
//        }
//    }
//
//    // =================================================================================================================
//    // ПРИВАТНЫЕ ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ
//    // =================================================================================================================
//
//    /**
//     * Внутренний метод для обработки полей, аннотированных указанной аннотацией.
//     * Устраняет дублирование кода в публичных map-методах.
//     *
//     * @param clazz      Класс для анализа.
//     * @param annotation Класс аннотации.
//     * @param methodName Имя метода в аннотации.
//     * @param valueType  Ожидаемый тип значения, возвращаемого методом аннотации.
//     * @param consumer   Действие, которое нужно выполнить с парой (Field, value).
//     * @param <V>        Тип значения из аннотации.
//     */
//    private static <V> void processAnnotatedFields(
//            Class<?> clazz,
//            Class<? extends Annotation> annotation,
//            String methodName,
//            Class<V> valueType,
//            BiConsumer<Field, V> consumer) {
//
//        if (clazz == null || annotation == null || methodName == null || valueType == null || consumer == null) {
//            throw new IllegalArgumentException("Параметры не могут быть null");
//        }
//
//        try {
//            Method valueMethod = annotation.getDeclaredMethod(methodName);
//            // Проверяем, что тип возвращаемого значения метода совместим с ожидаемым
//            if (!valueType.isAssignableFrom(valueMethod.getReturnType())) {
//                throw new IllegalArgumentException(String.format(
//                        "Метод '%s' аннотации @%s должен возвращать тип, совместимый с %s",
//                        methodName, annotation.getSimpleName(), valueType.getSimpleName()));
//            }
//
//            for (Field field : clazz.getDeclaredFields()) {
//                field.setAccessible(true);
//                Annotation foundAnnotation = field.getAnnotation(annotation);
//                if (foundAnnotation != null) {
//                    // Вызываем метод на ЭКЗЕМПЛЯРЕ аннотации
//                    V value = valueType.cast(valueMethod.invoke(foundAnnotation));
//                    if (value != null) {
//                        consumer.accept(field, value);
//                    }
//                }
//            }
//        } catch (NoSuchMethodException e) {
//            throw new IllegalArgumentException(
//                    String.format("Метод '%s' не найден в аннотации @%s", methodName, annotation.getSimpleName()), e);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            throw new RuntimeException(
//                    String.format("Ошибка при вызове метода '%s' аннотации @%s", methodName, annotation.getSimpleName()), e);
//        }
//    }
//
//    /**
//     * Проверяет, является ли один тип оберткой для другого примитивного типа.
//     */
//    private static boolean isWrapperType(Class<?> wrapper, Class<?> primitive) {
//        try {
//            return wrapper.equals(primitive) || wrapper.getField("TYPE").get(null).equals(primitive);
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//
//    /**
//     * Вспомогательный приватный метод для создания экземпляра класса.
//     */
//    private static <T> T createInstance(Class<?> classToInstantiate, Class<T> baseType) {
//        try {
//            return baseType.cast(classToInstantiate.getDeclaredConstructor().newInstance());
//        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
//                 InvocationTargetException e) {
//            throw new RuntimeException(String.format(
//                    "Не удалось создать экземпляр класса %s. Убедитесь, что у него есть публичный конструктор без аргументов.",
//                    classToInstantiate.getName()), e);
//        } catch (ClassCastException e) {
//            // Эта проверка дублируется, но является дополнительной гарантией
//            throw new RuntimeException(String.format(
//                    "Ошибка приведения типа для класса %s к %s.", classToInstantiate.getName(), baseType.getName()), e);
//        }
//    }
//}
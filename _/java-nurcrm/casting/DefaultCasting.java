package kg.nurtelecom.util.casting;

import kg.nurtelecom.util.parser.BooleanParser;
import kg.nurtelecom.util.parser.Parsers;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class DefaultCasting implements Casting {

    private static DefaultCasting instance;
    private final Map<Class<?>, Function<String, ?>> casterMap;

    private DefaultCasting() {
        casterMap = new HashMap<>();
        casterMap.put(int.class, this::primitiveIntegerValue);
        casterMap.put(Integer.class, this::integerValue);
        casterMap.put(long.class, this::primitiveLongValue);
        casterMap.put(Long.class, this::longValue);
        casterMap.put(double.class, this::primitiveDoubleValue);
        casterMap.put(Double.class, this::doubleValue);
        casterMap.put(float.class, this::primitiveFloatValue);
        casterMap.put(Float.class, this::floatValue);
        casterMap.put(boolean.class, this::primitiveBooleanValue);
        casterMap.put(Boolean.class, this::booleanValue);
        casterMap.put(BigDecimal.class, this::bigDecimalValue);
        casterMap.put(Date.class, this::dateValue);
        casterMap.put(LocalDate.class, this::localDateValue);
        casterMap.put(LocalDateTime.class, this::localDateTimeValue);
        casterMap.put(LocalTime.class, this::localTimeValue);
        casterMap.put(String.class, value -> value);
    }

    public static DefaultCasting getInstance() {
        if (instance == null) {
            instance = new DefaultCasting();
        }
        return instance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T cast(Class<?> clazz, Object value) {
        if (value == null) {
            return null;
        }
        if (clazz.isInstance(value)) {
            return (T) clazz.cast(value);
        }
        return getValueObject(clazz, value.toString());
    }

    @SuppressWarnings("unchecked")
    protected <T> T getValueObject(Class<?> target, String value) {
        Function<String, ?> caster = casterMap.get(target);
        if (caster != null) {
            return (T) caster.apply(value);
        }

        if (target.isEnum()) {
            return (T) enumValue(value, target);
        }
        return (T) value;
    }

    protected <T> T onError(String value, Exception exception, T defaultValue) {
        log.trace("Failed to cast value '{}'. Returning default value '{}'.", new Object[]{value, defaultValue, exception});
        return defaultValue;
    }

    protected Boolean primitiveBooleanValue(String value) {
        try {
            return Parsers.booleans().parse(value);
        } catch (BooleanParser.BooleanParseException bpe) {
            return onError(value, bpe, false);
        }
    }

    protected Boolean booleanValue(String value) {
        try {
            return Parsers.booleans().parse(value);
        } catch (BooleanParser.BooleanParseException bpe) {
            return onError(value, bpe, false);
        }
    }

    protected int primitiveIntegerValue(String value) {
        try {
            return Parsers.integers().parse(value).intValue();
        } catch (NumberFormatException nfe) {
            return onError(value, nfe, 0);
        }
    }

    protected Integer integerValue(String value) {
        try {
            return Parsers.integers().parse(value).intValue();
        } catch (NumberFormatException nfe) {
            return onError(value, nfe, null);
        }
    }

    protected long primitiveLongValue(String value) {
        try {
            return Parsers.longs().parse(value).longValue();
        } catch (NumberFormatException nfe) {
            return onError(value, nfe, 0L);
        }
    }

    protected Long longValue(String value) {
        try {
            return Parsers.longs().parse(value).longValue();
        } catch (NumberFormatException nfe) {
            return onError(value, nfe, null);
        }
    }

    protected double primitiveDoubleValue(String value) {
        try {
            return Parsers.numbers().parse(value).doubleValue();
        } catch (NumberFormatException nfe) {
            return onError(value, nfe, 0d);
        }
    }

    protected Double doubleValue(String value) {
        try {
            return Parsers.numbers().parse(value).doubleValue();
        } catch (NumberFormatException nfe) {
            return onError(value, nfe, null);
        }
    }

    protected float primitiveFloatValue(String value) {
        try {
            return Parsers.numbers().parse(value).floatValue();
        } catch (NumberFormatException nfe) {
            return onError(value, nfe, 0f);
        }
    }

    protected Float floatValue(String value) {
        try {
            return Parsers.numbers().parse(value).floatValue();
        } catch (NumberFormatException nfe) {
            return onError(value, nfe, null);
        }
    }

    protected BigDecimal bigDecimalValue(String value) {
        try {
            return Parsers.bigDecimals().parse(value);
        } catch (NumberFormatException | IllegalStateException e) {
            return onError(value, e, null);
        }
    }

    protected Date dateValue(String value) {
        return new Date(Date.parse(value));
    }

    protected LocalDate localDateValue(String value) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            return onError(value, e, null);
        }
    }

    protected LocalDateTime localDateTimeValue(String value) {
        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException e) {
            return onError(value, e, null);
        }
    }

    protected LocalTime localTimeValue(String value) {
        try {
            return LocalTime.parse(value);
        } catch (DateTimeParseException e) {
            return onError(value, e, null);
        }
    }

    protected Object enumValue(String value, Class<?> type) {
        return Arrays.stream(type.getEnumConstants())
                .filter(o -> ((Enum<?>) o).name().equals(value))
                .findFirst()
                .orElseGet(() -> {
                    IllegalArgumentException e = new IllegalArgumentException(
                            "No enumeration " + type.getSimpleName() + "." + value);
                    return onError(value, e, null);
                });
    }
}

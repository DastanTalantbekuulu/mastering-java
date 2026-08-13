package kg.nurtelecom.util.comparator.entity.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseComparator {
    Class<? extends FieldComparator<?>> value();

    String factoryMethod() default "getInstance";
}

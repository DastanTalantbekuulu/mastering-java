package kg.nurtelecom.registration.api.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kg.nurtelecom.registration.common.enums.Action;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Versioned {
    Class<?> entity();

    String entityId() default "#return.id";

    Action action() default Action.UNKNOWN;

    String data() default "#return";
}

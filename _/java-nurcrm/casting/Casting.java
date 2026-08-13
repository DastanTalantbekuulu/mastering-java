package kg.nurtelecom.util.casting;


@FunctionalInterface
public interface Casting {

    <T> T cast(Class<?> clazz, Object value);

}

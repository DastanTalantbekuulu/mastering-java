package kg.nurtelecom.util.converter;

@FunctionalInterface
public interface Converter<I, R> {

    R convert(I i);
}

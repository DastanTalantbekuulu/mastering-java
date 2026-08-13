package kg.nurtelecom.util.parser;

@FunctionalInterface
public interface Parser<T> {

    T parse(String value);

}
